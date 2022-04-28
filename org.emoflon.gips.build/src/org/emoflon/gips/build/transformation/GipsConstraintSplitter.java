package org.emoflon.gips.build.transformation;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.gipsl.gipsl.GipsBinaryBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBool;
import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.emoflon.gips.gipsl.gipsl.GipsUnaryBoolExpr;

public class GipsConstraintSplitter {
	protected GipslFactory factory = GipslFactory.eINSTANCE;
	protected final GipsTransformationData data;

	public GipsConstraintSplitter(final GipsTransformationData data) {
		this.data = data;
	}

	public Collection<GipsConstraint> split(final GipsConstraint constraint) throws Exception {
		Collection<GipsConstraint> constraints = new LinkedHashSet<>();
		if (constraint.getExpr() == null || constraint.getExpr().getExpr() == null) {
			throw new NullPointerException("Constraint can not be split, since its boolean expression is empty!");
		}

		splitAtAND(constraint, constraint.getExpr().getExpr(), constraints);
		return constraints;
	}

	protected void splitAtAND(final GipsConstraint parent, final GipsBoolExpr expr,
			Collection<GipsConstraint> constraints) {
		if (expr instanceof GipsBooleanLiteral || expr instanceof GipsRelExpr) {
			GipsConstraint constraint = factory.createGipsConstraint();
			constraint.setContext(EcoreUtil.copy(parent.getContext()));
			GipsBool boolContainer = factory.createGipsBool();
			boolContainer.setExpr(EcoreUtil.copy(expr));
			constraint.setExpr(boolContainer);
			constraints.add(constraint);
		} else if (expr instanceof GipsBinaryBoolExpr bin) {
			splitAtAND(parent, bin.getLeft(), constraints);
			splitAtAND(parent, bin.getRight(), constraints);
		} else {
			splitAtAND(parent, ((GipsUnaryBoolExpr) expr).getOperand(), constraints);
		}
	}

	protected void normalizeConstraint(final GipsConstraint constraint) throws Exception {
		// TODO: This method should transform boolean expressions into a canonical
		// conjunctive normal form, e.g., x & y & !z & k
		// -> For now just make sure that there are no "OR" and "NOT" operations used in
		// the boolean expressions
		if (hasForbiddenOperations(constraint.getExpr().getExpr())) {
			throw new UnsupportedOperationException("Constraint contains unsupported boolean operators.");
		}

	}

	protected boolean hasForbiddenOperations(final GipsBoolExpr expr) {
		if (expr instanceof GipsBooleanLiteral || expr instanceof GipsRelExpr) {
			return false;
		} else if (expr instanceof GipsBinaryBoolExpr bin) {
			switch (bin.getOperator()) {
			case AND:
				return hasForbiddenOperations(bin.getLeft()) || hasForbiddenOperations(bin.getRight());
			case OR:
				return true;
			default:
				return true;
			}
		} else {
			// Since the only currently available unary operator is NOT -> always return
			// true
			return true;
		}
	}
}
