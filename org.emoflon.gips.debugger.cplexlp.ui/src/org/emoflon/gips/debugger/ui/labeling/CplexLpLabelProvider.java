/*
 * generated by Xtext 2.33.0
 */
package org.emoflon.gips.debugger.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;
import org.emoflon.gips.debugger.cplexLp.ConstraintExpression;
import org.emoflon.gips.debugger.cplexLp.EqualsToBoundExpression;
import org.emoflon.gips.debugger.cplexLp.FreeBoundExpression;
import org.emoflon.gips.debugger.cplexLp.Infinity;
import org.emoflon.gips.debugger.cplexLp.InfinityLiteral;
import org.emoflon.gips.debugger.cplexLp.LinearExpression;
import org.emoflon.gips.debugger.cplexLp.LinearOperators;
import org.emoflon.gips.debugger.cplexLp.LinearTerm;
import org.emoflon.gips.debugger.cplexLp.LowerBoundExpression;
import org.emoflon.gips.debugger.cplexLp.NumberLiteral;
import org.emoflon.gips.debugger.cplexLp.SectionBinaryValue;
import org.emoflon.gips.debugger.cplexLp.SectionBound;
import org.emoflon.gips.debugger.cplexLp.SectionConstraint;
import org.emoflon.gips.debugger.cplexLp.SectionIntegerValue;
import org.emoflon.gips.debugger.cplexLp.SectionObjective;
import org.emoflon.gips.debugger.cplexLp.UpperAndLowerBoundExpression;
import org.emoflon.gips.debugger.cplexLp.UpperBoundExpression;
import org.emoflon.gips.debugger.cplexLp.VariableDecleration;
import org.emoflon.gips.debugger.cplexLp.VariableRef;

import com.google.inject.Inject;

/**
 * Provides labels for EObjects.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#label-provider
 */
public class CplexLpLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	public CplexLpLabelProvider(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	String text(SectionObjective element) {
		return "Goal: " + element.getGoal();
	}

	String text(SectionConstraint element) {
		return "Constraints";
	}

	String text(SectionBound element) {
		return "Bounds";
	}

	String text(SectionBinaryValue element) {
		return "Binaries";
	}

	String text(SectionIntegerValue element) {
		return "Generals";
	}

	String text(ConstraintExpression element) {
		var builder = new StringBuilder();
		if (element.getName() != null) {
			builder.append(element.getName());
		} else {
			builder.append("constraint");
		}
		return builder.toString();
	}

	String text(UpperAndLowerBoundExpression element) {
		var builder = new StringBuilder();
		builder.append(doGetText(element.getLowerBound()));
		builder.append(" <= ");
		builder.append(doGetText(element.getVariable()));
		builder.append(" <= ");
		builder.append(doGetText(element.getUpperBound()));
		return builder.toString();
	}

	String text(UpperBoundExpression element) {
		var builder = new StringBuilder();
		builder.append(doGetText(element.getVariable()));
		builder.append(" <= ");
		builder.append(doGetText(element.getUpperBound()));
		return builder.toString();
	}

	String text(LowerBoundExpression element) {
		var builder = new StringBuilder();
		builder.append(doGetText(element.getLowerBound()));
		builder.append(" <= ");
		builder.append(doGetText(element.getVariable()));
		return builder.toString();
	}

	String text(FreeBoundExpression element) {
		var builder = new StringBuilder();
		builder.append(doGetText(element.getVariable()));
		builder.append(" free");
		return builder.toString();
	}

	String text(EqualsToBoundExpression element) {
		var builder = new StringBuilder();
		builder.append(doGetText(element.getVariable()));
		builder.append(" = ");
		builder.append(doGetText(element.getBound()));
		return builder.toString();
	}

	String text(LinearExpression element) {
		var builder = new StringBuilder();
		for (var e : element.getTerms()) {
			builder.append(text(e));
			if (builder.length() > 80) {
				builder.setLength(76);
				builder.append(" ...");
				break;
			}
		}
		return builder.toString();
	}

	String text(LinearTerm element) {
		var builder = new StringBuilder();
		if (element.getOperator() != null) {
			builder.append(text(element.getOperator()));
		}
		if (element.getCoefficient() != null) {
			builder.append(doGetText(element.getCoefficient()));
		}
		if (element.getCoefficient() != null && element.getVariable() != null) {
			builder.append("*");
		}
		if (element.getVariable() != null) {
			builder.append(doGetText(element.getVariable()));
		}
		return builder.toString();
	}

	String text(NumberLiteral element) {
		return element.getValue();
	}

	String text(InfinityLiteral element) {
		return text(element.getValue());
	}

	String text(VariableDecleration element) {
		return element.getName();
	}

	String text(VariableRef element) {
		return element.getRef().getName();
	}

	String text(Infinity element) {
		return element.toString();
	}

	String text(LinearOperators element) {
		return element.toString();
	}

}