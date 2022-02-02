package org.emoflon.roam.build.transformation;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.roam.roamslang.roamSLang.RoamBinaryBoolExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamBool;
import org.emoflon.roam.roamslang.roamSLang.RoamBoolExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamBooleanLiteral;
import org.emoflon.roam.roamslang.roamSLang.RoamConstraint;
import org.emoflon.roam.roamslang.roamSLang.RoamRelExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamSLangFactory;
import org.emoflon.roam.roamslang.roamSLang.RoamUnaryBoolExpr;

public class RoamConstraintSplitter {
	protected RoamSLangFactory factory = RoamSLangFactory.eINSTANCE;
	protected final RoamTransformationData data;
	
	public RoamConstraintSplitter(final RoamTransformationData data) {
		this.data = data;
	}
	
	public Collection<RoamConstraint> split(final RoamConstraint constraint) throws Exception {
		Collection<RoamConstraint> constraints = new LinkedHashSet<>();
		if(constraint.getExpr() == null || constraint.getExpr().getExpr() == null) {
			throw new NullPointerException("Constraint can not be split, since its boolean expression is empty!");
		}
		
		splitAtAND(constraint, constraint.getExpr().getExpr(), constraints);
		return constraints;
	}
	
	protected void splitAtAND(final RoamConstraint parent, final RoamBoolExpr expr, Collection<RoamConstraint> constraints) {
		if(expr instanceof RoamBooleanLiteral || expr instanceof RoamRelExpr) {
			RoamConstraint constraint = factory.createRoamConstraint();
			constraint.setContext(EcoreUtil.copy(parent.getContext()));
			RoamBool boolContainer = factory.createRoamBool();
			boolContainer.setExpr(EcoreUtil.copy(expr));
			constraint.setExpr(boolContainer);
			constraints.add(constraint);
		} else if(expr instanceof RoamBinaryBoolExpr bin) {
			splitAtAND(parent, bin.getLeft(), constraints);
			splitAtAND(parent, bin.getRight(), constraints);
		} else {
			splitAtAND(parent, ((RoamUnaryBoolExpr)expr).getOperand(), constraints);
		}
	}
	
	protected void normalizeConstraint(final RoamConstraint constraint) throws Exception {
		//TODO: This method should transform boolean expressions into a canonical conjunctive normal form, e.g., x & y & !z & k
		// -> For now just make sure that there are no "OR" and "NOT" operations used in the boolean expressions
		if(hasForbiddenOperations(constraint.getExpr().getExpr())) {
			throw new UnsupportedOperationException("Constraint contains unsupported boolean operators.");
		}
		
	}
	
	protected boolean hasForbiddenOperations(final RoamBoolExpr expr) {
		if(expr instanceof RoamBooleanLiteral || expr instanceof RoamRelExpr) {
			return false;
		} else if(expr instanceof RoamBinaryBoolExpr bin) {
			switch(bin.getOperator()) {
			case AND:
				return hasForbiddenOperations(bin.getLeft()) || hasForbiddenOperations(bin.getRight());
			case OR:
				return true;
			default :
				return true;
			}
		} else {
			// Since the only currently available unary operator is NOT -> always return true
			return true;
		}
	}
}
