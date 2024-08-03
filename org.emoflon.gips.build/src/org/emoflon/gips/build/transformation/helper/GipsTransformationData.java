package org.emoflon.gips.build.transformation.helper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingVariable;
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
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.utils.GTEditorPatternUtils;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;

public record GipsTransformationData(GipsIntermediateModel model, //
		EditorGTFile gipsSlangFile, //
		Map<String, GipsBoolExpr> symbol2Expr, //
		Map<GipsBoolExpr, String> expr2Symbol, //
		Map<EditorPattern, IBeXRule> ePattern2Rule, //
		Map<EditorPattern, IBeXContext> ePattern2Context, //
		Map<EditorPattern, Pattern> ePattern2Pattern, //
		Map<EditorNode, IBeXNode> eNode2Node, //
		Map<GipsMapping, Mapping> eMapping2Mapping, //
		Map<GipsConstraint, Collection<Constraint>> eConstraint2Constraints, //
		Map<GipsStreamExpr, SetOperation> eStream2SetOp, //
		Map<EClass, Type> eType2Type, //
		Map<GipsObjective, Objective> eObjective2Objective, Map<GipsMappingVariable, Variable> eVariable2Variable) {

	public GipsTransformationData(final GipsIntermediateModel model, final EditorGTFile gipsSlangFile) {
		this(model, gipsSlangFile, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
				new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
				new HashMap<>());
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

	public Pattern getPattern(final EditorPattern pattern) {
		Pattern p = ePattern2Pattern.get(pattern);
		if (p == null) {
			p = GipsIntermediateFactory.eINSTANCE.createPattern();
			p.setName(pattern.getName());
			p.setPattern(ePattern2Context.get(pattern));
			p.setIsRule(GTEditorPatternUtils.containsCreatedOrDeletedElements(pattern));
			ePattern2Pattern.put(pattern, p);
		}
		return p;
	}
}
