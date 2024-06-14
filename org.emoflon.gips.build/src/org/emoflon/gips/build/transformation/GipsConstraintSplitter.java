package org.emoflon.gips.build.transformation;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.gipsl.gipsl.GipsAndBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBool;
import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBracketBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsImplicationBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsNotBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsOrBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
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
		} else if (expr instanceof GipsImplicationBoolExpr implication) {
			parseToString(sb, implication.getLeft());
			switch (implication.getOperator()) {
			case EQUIVALENCE -> {
				sb.append(" <=> ");
			}
			case IMPLICATION -> {
				sb.append(" => ");
			}
			default ->
				throw new UnsupportedOperationException("Unknown boolean operator type: " + implication.getOperator());
			}
			parseToString(sb, implication.getRight());
		} else if (expr instanceof GipsOrBoolExpr orExpression) {
			switch (orExpression.getOperator()) {
			case OR -> {
				parseToString(sb, orExpression.getLeft());
				sb.append(" | ");
				parseToString(sb, orExpression.getRight());
			}
			case XOR -> { // A ^ B = (A & !B) | (!A & B)
				sb.append("(");
				parseToString(sb, orExpression.getLeft());
				sb.append(" & ~(");
				parseToString(sb, orExpression.getRight());
				sb.append("))");

				sb.append(" | ");

				sb.append("( ~(");
				parseToString(sb, orExpression.getLeft());
				sb.append(") & ");
				parseToString(sb, orExpression.getRight());
				sb.append(")");
			}
			default -> {
				throw new UnsupportedOperationException("Unknown boolean operator type: " + orExpression.getOperator());
			}

			}
		} else if (expr instanceof GipsAndBoolExpr andExpression) {
			switch (andExpression.getOperator()) {
			case AND -> {
				parseToString(sb, andExpression.getLeft());
				sb.append(" & ");
				parseToString(sb, andExpression.getRight());
			}
			default -> {
				throw new UnsupportedOperationException(
						"Unknown boolean operator type: " + andExpression.getOperator());
			}

			}
		} else if (expr instanceof GipsNotBoolExpr not) {
			sb.append("~(");
			parseToString(sb, not.getOperand());
			sb.append(")");
		} else if (expr instanceof GipsBracketBoolExpr bracket) {
			sb.append("(");
			parseToString(sb, bracket.getOperand());
			sb.append(")");
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
						AnnotatedConstraintType.DISJUNCTION_OF_LITERALS, result));
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
