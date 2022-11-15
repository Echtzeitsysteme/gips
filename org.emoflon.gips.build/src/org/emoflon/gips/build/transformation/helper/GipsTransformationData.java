package org.emoflon.gips.build.transformation.helper;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.emoflon.gips.gipsl.gipsl.EditorFile;
import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamExpr;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateFactory;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;
import org.emoflon.gips.intermediate.GipsIntermediate.Pattern;
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.Type;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.gt.gtl.gTL.GTLRuleType;
import org.emoflon.ibex.gt.gtl.gTL.SlimRule;
import org.emoflon.ibex.gt.gtl.gTL.SlimRuleNode;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule;

public record GipsTransformationData(GipsIntermediateModel model, //
		EditorFile gipsSlangFile, //
		Map<String, GipsBoolExpr> symbol2Expr, //
		Map<GipsBoolExpr, String> expr2Symbol, //
		Map<SlimRule, GTRule> ePattern2Rule, //
		Map<SlimRule, GTPattern> ePattern2Context, //
		Map<SlimRule, Pattern> ePattern2Pattern, //
		Map<SlimRuleNode, IBeXNode> eNode2Node, //
		Map<GipsMapping, Mapping> eMapping2Mapping, //
		Map<GipsConstraint, Constraint> eConstraint2Constraint, //
		Map<GipsStreamExpr, SetOperation> eStream2SetOp, //
		Map<EClass, Type> eType2Type, //
		Map<GipsObjective, Objective> eObjective2Objective) {

	public GipsTransformationData(final GipsIntermediateModel model, final EditorFile gipsSlangFile) {
		this(model, gipsSlangFile, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
				new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
	}

	public String addSymbol(final GipsBoolExpr expr) {
		if (expr2Symbol.containsKey(expr))
			return expr2Symbol.get(expr);

		int count = symbol2Expr.size();
		String symbol = count + "_";

		if (expr instanceof GipsBooleanLiteral lit) {
			symbol += "LIT";
		} else if (expr instanceof GipsRelExpr rel && rel.getRight() != null) {
			symbol += "REL_EXPR";
		} else if (expr instanceof GipsRelExpr rel && rel.getRight() == null) {
			symbol += "EXPR";
		} else {
			throw new UnsupportedOperationException("Only boolean terminal expressions can be translated into symbols");
		}

		expr2Symbol.put(expr, symbol);
		symbol2Expr.put(symbol, expr);

		return symbol;
	}

	public Type getType(final EClass eType) {
		Type type = eType2Type.get(eType);
		if (type == null) {
			type = GipsIntermediateFactory.eINSTANCE.createType();
			type.setName(eType.getName());
			type.setType(eType);
			type.setLowerBound(0.0);
			type.setUpperBound(1.0);
			eType2Type.put(eType, type);
		}
		return type;
	}

	public Pattern getPattern(final SlimRule pattern) {
		Pattern p = ePattern2Pattern.get(pattern);
		if (p == null) {
			p = GipsIntermediateFactory.eINSTANCE.createPattern();
			p.setName(pattern.getName());
			p.setPattern(ePattern2Context.get(pattern));
			p.setIsRule(pattern.getType() == GTLRuleType.RULE);
			ePattern2Pattern.put(pattern, p);
		}
		return p;
	}
}
