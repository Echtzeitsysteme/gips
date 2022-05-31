package org.emoflon.gips.build.transformation;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.gipsl.gipsl.GipsBinaryBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBool;
import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsPatternContext;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.gipsl.gipsl.GipsUnaryBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.formulas.Literal;
import org.logicng.formulas.NAryOperator;
import org.logicng.io.parsers.PropositionalParser;

public class GipsConstraintSplitter {
	protected GipslFactory factory = GipslFactory.eINSTANCE;
	protected final GipsTransformationData data;
	protected final GipsConstraint constraint;

	public GipsConstraintSplitter(final GipsTransformationData data, final GipsConstraint constraint) {
		this.data = data;
		this.constraint = constraint;
	}

	public Collection<GipsAnnotatedConstraint> split() throws Exception {
		Collection<GipsAnnotatedConstraint> constraints = new LinkedHashSet<>();
		if (constraint.getExpr() == null || constraint.getExpr().getExpr() == null) {
			throw new NullPointerException("Constraint can not be split, since its boolean expression is empty!");
		}

		if ((constraint.getContext() instanceof GipsPatternContext)
				&& ((GipsPatternContext) constraint.getContext()).getPattern().getName().contains("vlNotMapped"))
			System.out.println("Bingo");

		StringBuilder expressionBuilder = new StringBuilder();
		parseToString(expressionBuilder, constraint.getExpr().getExpr());
		String expression = expressionBuilder.toString();
		FormulaFactory fFactory = new FormulaFactory();
		PropositionalParser parser = new PropositionalParser(fFactory);
		Formula formula = parser.parse(expression);
		formula = formula.cnf();

		splitAtAND(formula, constraints);
		return constraints;
	}

	protected void parseToString(final StringBuilder sb, final GipsBoolExpr expr) {
		if (expr instanceof GipsBooleanLiteral || expr instanceof GipsRelExpr) {
			sb.append(data.addSymbol(expr));
		} else if (expr instanceof GipsBinaryBoolExpr bin) {
			switch (bin.getOperator()) {
			case AND -> {
				parseToString(sb, bin.getLeft());
				sb.append(" & ");
				parseToString(sb, bin.getRight());
			}
			case OR -> {
				parseToString(sb, bin.getLeft());
				sb.append(" | ");
				parseToString(sb, bin.getRight());
			}
			default -> {
				throw new UnsupportedOperationException("Unknown boolean operator type: " + bin.getOperator());
			}

			}
		} else if (expr instanceof GipsUnaryBoolExpr un) {
			switch (un.getOperator()) {
			case NOT -> {
				sb.append("~(");
				parseToString(sb, un.getOperand());
				sb.append(")");
			}
			default -> {
				throw new UnsupportedOperationException("Unknown boolean operator type: " + un.getOperator());
			}

			}
		} else {
			throw new UnsupportedOperationException("Unknown boolean expression type: " + expr);
		}
	}

	protected void splitAtAND(final Formula formula, Collection<GipsAnnotatedConstraint> constraints) throws Exception {
		if (formula instanceof Literal literal && literal.phase()) {
			GipsBoolExpr expr = data.symbol2Expr().get(literal.name());
			Map<Formula, GipsConstraint> result = new HashMap<>();
			if (expr instanceof GipsBooleanLiteral gipsLit) {
				result.put(literal, transform(gipsLit));
				constraints.add(
						new GipsAnnotatedConstraint(this.constraint, formula, AnnotatedConstraintType.LITERAL, result));
			} else if (expr instanceof GipsRelExpr gipsRel) {
				result.put(literal, transform(gipsRel));
				constraints.add(
						new GipsAnnotatedConstraint(this.constraint, formula, AnnotatedConstraintType.LITERAL, result));
			} else {
				throw new UnsupportedOperationException(
						"Literals in a boolean expression in CNF-form should not be boolean expressions themselves.");
			}
		} else if (formula instanceof Literal literal && !literal.phase()) {
			GipsBoolExpr expr = data.symbol2Expr().get(literal.name());
			Map<Formula, GipsConstraint> result = new HashMap<>();
			if (expr instanceof GipsBooleanLiteral gipsLit) {
				result.put(literal, transform(gipsLit));
				constraints.add(new GipsAnnotatedConstraint(this.constraint, formula,
						AnnotatedConstraintType.NEGATED_LITERAL, result));
			} else if (expr instanceof GipsRelExpr gipsRel) {
				result.put(literal, transform(gipsRel));
				constraints.add(new GipsAnnotatedConstraint(this.constraint, formula,
						AnnotatedConstraintType.NEGATED_LITERAL, result));
			} else {
				throw new UnsupportedOperationException(
						"Literals in a boolean expression in CNF-form should not be boolean expressions themselves.");
			}

		} else if (formula instanceof NAryOperator nAry) {
			switch (nAry.type()) {
			case AND -> {
				for (Formula child : nAry) {
					splitAtAND(child, constraints);
				}
			}
			case OR -> {
				Map<Formula, GipsConstraint> result = new HashMap<>();
				for (Formula child : nAry) {
					if (child instanceof Literal literal && literal.phase()) {
						GipsBoolExpr expr = data.symbol2Expr().get(literal.name());
						if (expr instanceof GipsBooleanLiteral gipsLit) {
							result.put(literal, transform(gipsLit));
						} else if (expr instanceof GipsRelExpr gipsRel) {
							result.put(literal, transform(gipsRel));
						} else {
							throw new UnsupportedOperationException(
									"Literals in a boolean expression in CNF-form should not be boolean expressions themselves.");
						}
					} else if (child instanceof Literal literal && !literal.phase()) {
						GipsBoolExpr expr = data.symbol2Expr().get(literal.name());
						if (expr instanceof GipsBooleanLiteral gipsLit) {
							result.put(literal, transform(gipsLit));
						} else if (expr instanceof GipsRelExpr gipsRel) {
							result.put(literal, transform(gipsRel));
						} else {
							throw new UnsupportedOperationException(
									"Literals in a boolean expression in CNF-form should not be boolean expressions themselves.");
						}
					} else {
						throw new UnsupportedOperationException(
								"Literals in a boolean expression in CNF-form should not be boolean expressions themselves.");
					}
				}

				constraints.add(new GipsAnnotatedConstraint(this.constraint, nAry,
						AnnotatedConstraintType.CONJUCTION_OF_LITERALS, result));
			}
			default -> {
				throw new UnsupportedOperationException("Unknown nAry-boolean operation type: " + nAry.type());
			}

			}
		} else {
			throw new UnsupportedOperationException("Unknown boolean operation type: " + formula);
		}
	}

	GipsConstraint transform(final GipsBooleanLiteral literal) {
		GipsConstraint constraint = factory.createGipsConstraint();
		constraint.setContext(EcoreUtil.copy(this.constraint.getContext()));
		GipsBool boolContainer = factory.createGipsBool();
		boolContainer.setExpr(EcoreUtil.copy(literal));
		constraint.setExpr(boolContainer);
		return constraint;
	}

	GipsConstraint transform(final GipsRelExpr relational) {
		GipsConstraint constraint = factory.createGipsConstraint();
		constraint.setContext(EcoreUtil.copy(this.constraint.getContext()));
		GipsBool boolContainer = factory.createGipsBool();
		boolContainer.setExpr(EcoreUtil.copy(relational));
		constraint.setExpr(boolContainer);
		return constraint;
	}

}
