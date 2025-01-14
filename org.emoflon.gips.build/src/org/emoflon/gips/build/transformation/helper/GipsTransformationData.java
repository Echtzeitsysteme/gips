package org.emoflon.gips.build.transformation.helper;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingVariable;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNamedElement;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;

public record GipsTransformationData(GipsIntermediateModel model, //
		EditorGTFile gipslFile, //
		Map<String, GipsBooleanExpression> symbol2Expr, //
		Map<GipsBooleanExpression, String> expr2Symbol, //
		Map<EditorPattern, IBeXNamedElement> ePattern2ibex, //
		Map<EditorNode, IBeXNode> eNode2Node, //
		Map<GipsMapping, Mapping> eMapping2Mapping, //
		Map<GipsConstraint, Collection<Constraint>> eConstraint2Constraints, //
		Map<GipsLinearFunction, LinearFunction> eFunction2Function, //
		Map<GipsMappingVariable, Variable> eVariable2Variable, //
		Set<EClass> requiredTypes) {

	public GipsTransformationData(final GipsIntermediateModel model, final EditorGTFile gipsSlangFile) {
		this(model, gipsSlangFile, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
				new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashSet<>());
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

	public IBeXPattern getPattern(final EditorPattern pattern) {
		if (ePattern2ibex.containsKey(pattern)) {
			return (IBeXPattern) ePattern2ibex.get(pattern);
		} else {
			return null;
		}
	}

	public IBeXRule getRule(final EditorPattern rule) {
		if (ePattern2ibex.containsKey(rule)) {
			return (IBeXRule) ePattern2ibex.get(rule);
		} else {
			return null;
		}
	}
}
