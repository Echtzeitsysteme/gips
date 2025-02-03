package org.emoflon.gips.build.transformation.helper;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsConstant;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Constant;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;

public record GipsTransformationData(GipsIntermediateModel model, //
		EditorGTFile gipslFile, //
		Map<String, GipsBooleanExpression> symbol2Expr, //
		Map<GipsBooleanExpression, String> expr2Symbol, //
		Map<String, IBeXPattern> ePattern2pattern, //
		Map<String, IBeXRule> ePattern2rule, //
		Map<EditorNode, IBeXNode> eNode2Node, //
		Map<GipsMapping, Mapping> eMapping2Mapping, //
		Map<EObject, Map<String, Constant>> eContext2Constants,
		Map<GipsConstraint, Collection<Constraint>> eConstraint2Constraints, //
		Map<GipsLinearFunction, LinearFunction> eFunction2Function, //
		Map<EObject, Variable> eVariable2Variable, //
		Set<EClass> requiredTypes) {

	public GipsTransformationData(final GipsIntermediateModel model, final EditorGTFile gipsSlangFile) {
		this(model, gipsSlangFile, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
				new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashSet<>());
	}

	public String addSymbol(final GipsBooleanExpression expr) {
		if (expr2Symbol.containsKey(expr))
			return expr2Symbol.get(expr);

		int count = symbol2Expr.size();
		String symbol = count + "_";

		if (expr instanceof GipsBooleanLiteral) {
			symbol += "LIT";
		} else if (expr instanceof GipsRelationalExpression) {
			symbol += "REL_EXPR";
		} else if (expr instanceof GipsValueExpression) {
			symbol += "EXPR";
		} else {
			throw new UnsupportedOperationException("Only boolean terminal expressions can be translated into symbols");
		}

		expr2Symbol.put(expr, symbol);
		symbol2Expr.put(symbol, expr);

		return symbol;
	}

	public void addConstant(final EObject context, final GipsConstant eConstant, final Constant constant) {
		Map<String, Constant> constants = eContext2Constants.get(context);
		if (constants == null) {
			constants = new HashMap<>();
			eContext2Constants.put(context, constants);
		}

		constants.put(eConstant.getName(), constant);
	}

	public void addConstant(final EObject context, final String eConstant, final Constant constant) {
		Map<String, Constant> constants = eContext2Constants.get(context);
		if (constants == null) {
			constants = new HashMap<>();
			eContext2Constants.put(context, constants);
		}

		constants.put(eConstant, constant);
	}

	public Constant getConstant(final EObject context, final GipsConstant constant) {
		Map<String, Constant> constants = eContext2Constants.get(context);
		if (constants == null)
			return null;

		return constants.get(constant.getName());
	}

	public void addPattern(final EditorPattern ePattern, final IBeXPattern pattern) {
		ePattern2pattern.put(ePattern.getName(), pattern);
	}

	public IBeXPattern getPattern(final EditorPattern pattern) {
		return ePattern2pattern.get(pattern.getName());
	}

	public void addRule(final EditorPattern eRule, final IBeXRule rule) {
		ePattern2rule.put(eRule.getName(), rule);
	}

	public IBeXRule getRule(final EditorPattern rule) {
		return ePattern2rule.get(rule.getName());
	}
}
