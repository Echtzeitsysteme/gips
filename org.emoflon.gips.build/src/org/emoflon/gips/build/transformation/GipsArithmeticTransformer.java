package org.emoflon.gips.build.transformation;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EReference;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticNullLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolValue;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingVariablesReference;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.FeatureLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateFactory;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariableValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariablesReference;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamContainsOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamFilterOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamNoOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamSelectOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.SumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.formulas.Literal;
import org.logicng.formulas.NAryOperator;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;
import org.logicng.transformations.dnf.DNFFactorization;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class GipsArithmeticTransformer {
	final protected GipsIntermediateFactory factory;
	final protected ArithmeticExpression root;

	final BiMap<ArithmeticExpression, String> expr2symbol = HashBiMap.create();
	final Set<SumExpression> transformedSums = new HashSet<>();

	public GipsArithmeticTransformer(final GipsIntermediateFactory factory, final ArithmeticExpression root) {
		this.factory = factory;
		this.root = root;
	}

	public ArithmeticExpression transform() throws ParserException {
		
		return null;
	}
	
	protected ArithmeticExpression removeSubtractions(final ArithmeticExpression expression) {
		ArithmeticExpression modified = null;
		if (expression instanceof BinaryArithmeticExpression binaryExpr) {
			BinaryArithmeticExpression mbe = factory.createBinaryArithmeticExpression();
			modified = mbe;
			mbe.setOperator(binaryExpr.getOperator());
			switch (binaryExpr.getOperator()) {
			case ADD -> {
				mbe.setLhs(removeSubtractions(binaryExpr.getLhs()));
				mbe.setRhs(removeSubtractions(binaryExpr.getRhs()));
			}
			case DIVIDE -> {
				mbe.setLhs(removeSubtractions(binaryExpr.getLhs()));
				mbe.setRhs(removeSubtractions(binaryExpr.getRhs()));
			}
			case LOG -> {
				mbe.setLhs(removeSubtractions(binaryExpr.getLhs()));
				mbe.setRhs(removeSubtractions(binaryExpr.getRhs()));
			}
			case MULTIPLY -> {
				mbe.setLhs(removeSubtractions(binaryExpr.getLhs()));
				mbe.setRhs(removeSubtractions(binaryExpr.getRhs()));
			}
			case POW -> {
				mbe.setLhs(removeSubtractions(binaryExpr.getLhs()));
				mbe.setRhs(removeSubtractions(binaryExpr.getRhs()));
			}
			case SUBTRACT -> {
				mbe.setLhs(removeSubtractions(binaryExpr.getLhs()));
				mbe.setOperator(BinaryArithmeticOperator.ADD);
				
				BinaryArithmeticExpression negation = factory.createBinaryArithmeticExpression();
				negation.setOperator(BinaryArithmeticOperator.MULTIPLY);
				DoubleLiteral negOne = factory.createDoubleLiteral();
				negOne.setLiteral(-1.0);
				negation.setLhs(negOne);
				negation.setRhs(removeSubtractions(binaryExpr.getRhs()));
				mbe.setRhs(negation);
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + binaryExpr.getOperator());
			}
			}
		} else if (expression instanceof UnaryArithmeticExpression unaryExpr) {
			UnaryArithmeticExpression mue = factory.createUnaryArithmeticExpression();
			mue.setOperator(unaryExpr.getOperator());
			switch (unaryExpr.getOperator()) {
			case ABSOLUTE -> {
				mue.setExpression(removeSubtractions(unaryExpr.getExpression()));
			}
			case BRACKET -> {
				mue.setExpression(removeSubtractions(unaryExpr.getExpression()));
				
			}
			case COSINE -> {
				mue.setExpression(removeSubtractions(unaryExpr.getExpression()));
			}
			case NEGATE -> {
				mue.setOperator(UnaryArithmeticOperator.BRACKET);
				mue.setExpression(removeSubtractions(unaryExpr.getExpression()));
				
				BinaryArithmeticExpression negation = factory.createBinaryArithmeticExpression();
				negation.setOperator(BinaryArithmeticOperator.MULTIPLY);
				DoubleLiteral negOne = factory.createDoubleLiteral();
				negOne.setLiteral(-1.0);
				negation.setLhs(negOne);
				negation.setRhs(mue);
				
				modified = negation;
			}
			case SINE -> {
				mue.setExpression(removeSubtractions(unaryExpr.getExpression()));
			}
			case SQRT -> {
				mue.setExpression(removeSubtractions(unaryExpr.getExpression()));
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + unaryExpr.getOperator());
			}
			}

		} else if (expression instanceof ArithmeticValue valExpr) {
			// CASE: SUM-Expressions
			if (valExpr.getValue() instanceof SumExpression sum) {
				SumExpression mse = (SumExpression) cloneExpression(sum, null);
				mse.setExpression(removeSubtractions(mse.getExpression()));
				ArithmeticValue val = factory.createArithmeticValue();
				val.setValue(mse);
				modified = val;
			} else {
				ArithmeticValue val = factory.createArithmeticValue();
				val.setValue(cloneExpression(valExpr.getValue(), null));
				modified = val;
			}
		} else {
			// CASE: Literals
			modified = cloneExpression(expression, null);
		}
		
		return modified;
	}
	
	protected ArithmeticExpression expandArithmeticExpressions(final ArithmeticExpression expression) {
		ArithmeticExpression modified = null;
		if (expression instanceof BinaryArithmeticExpression binaryExpr) {
			BinaryArithmeticExpression mbe = factory.createBinaryArithmeticExpression();
			modified = mbe;
			mbe.setOperator(binaryExpr.getOperator());
			switch (binaryExpr.getOperator()) {
			case ADD -> {
				mbe.setLhs(expandArithmeticExpressions(binaryExpr.getLhs()));
				mbe.setRhs(expandArithmeticExpressions(binaryExpr.getRhs()));
			}
			case DIVIDE -> {
				// If both sub-expressions are constant -> do nothing
				mbe.setLhs(cloneExpression(binaryExpr.getLhs(), null));
				mbe.setRhs(cloneExpression(binaryExpr.getRhs(), null));
				// If the divisor contains a variable -> error
				throw new UnsupportedOperationException("Variable may not be part of a divisor.");
				// If the divident contains a varibale 
				// -> transform to multiplication and expand
			}
			case LOG -> {
				// If both sub-expressions are constant -> do nothing
				mbe.setLhs(cloneExpression(binaryExpr.getLhs(), null));
				mbe.setRhs(cloneExpression(binaryExpr.getRhs(), null));
				// If any of the sub-expression contain variables -> error
				throw new UnsupportedOperationException("Variable may not be part of a log-expression.");
			}
			case MULTIPLY -> {
				// If both sub-expressions are constant -> do nothing
				mbe.setLhs(cloneExpression(binaryExpr.getLhs(), null));
				mbe.setRhs(cloneExpression(binaryExpr.getRhs(), null));
				// If both factors of the product contain a variable each -> error
				throw new UnsupportedOperationException("Variables may not be multiplied!");
				// If only one factor of the product contains variables 
				// -> expand
			}
			case POW -> {
				// If both sub-expressions are constant -> do nothing
				mbe.setLhs(cloneExpression(binaryExpr.getLhs(), null));
				mbe.setRhs(cloneExpression(binaryExpr.getRhs(), null));
				// If any of the sub-expression contain variables -> error
				throw new UnsupportedOperationException("Variable may not be part of an exponential expression.");
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + binaryExpr.getOperator());
			}
			}
		} else if (expression instanceof UnaryArithmeticExpression unaryExpr) {
			UnaryArithmeticExpression mue = factory.createUnaryArithmeticExpression();
			mue.setOperator(unaryExpr.getOperator());
			modified = mue;
			switch (unaryExpr.getOperator()) {
			case ABSOLUTE -> {
				// If sub-expression is constant -> do nothing
				mue.setExpression(cloneExpression(unaryExpr.getExpression(), null));
				// If the sub-expression contains a variable -> error
				throw new UnsupportedOperationException("Variable may not be part of an abs-expression.");
			}
			case BRACKET -> {
				// These should have been resolved (remove subtractions + expand muliplications)
				// -> Ignore
				modified = expandArithmeticExpressions(unaryExpr.getExpression());	
			}
			case COSINE -> {
				// If sub-expression is constant -> do nothing
				mue.setExpression(cloneExpression(unaryExpr.getExpression(), null));
				// If the sub-expression contains a variable -> error
				throw new UnsupportedOperationException("Variable may not be part of an cos-expression.");
			}
			case NEGATE -> {
				// This should not be present anymore
				throw new UnsupportedOperationException("Error: Negations have not been resolved prior to expanding arithmetic expressions.");
			}
			case SINE -> {
				// If sub-expression is constant -> do nothing
				mue.setExpression(cloneExpression(unaryExpr.getExpression(), null));
				// If the sub-expression contains a variable -> error
				throw new UnsupportedOperationException("Variable may not be part of an sin-expression.");
			}
			case SQRT -> {
				// If sub-expression is constant -> do nothing
				mue.setExpression(cloneExpression(unaryExpr.getExpression(), null));
				// If the sub-expression contains a variable -> error
				throw new UnsupportedOperationException("Variable may not be part of an sqrt-expression.");
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + unaryExpr.getOperator());
			}
			}

		} else if (expression instanceof ArithmeticValue valExpr) {
			// CASE: SUM-Expressions
			if (valExpr.getValue() instanceof SumExpression sum) {
				SumExpression mse = (SumExpression) cloneExpression(sum, null);
				mse.setExpression(removeSubtractions(mse.getExpression()));
				ArithmeticValue val = factory.createArithmeticValue();
				val.setValue(mse);
				modified = val;
			} else {
				ArithmeticValue val = factory.createArithmeticValue();
				val.setValue(cloneExpression(valExpr.getValue(), null));
				modified = val;
			}
		} else {
			// CASE: Literals
			modified = cloneExpression(expression, null);
		}
		
		return modified;
	}

	protected ArithmeticExpression splitSumExpr(final SumExpression sumExpr) throws ParserException {
		GipsArithmeticTransformer transformer = new GipsArithmeticTransformer(factory, sumExpr.getExpression());
		ArithmeticExpression transformed = transformer.transform();
		transformed = wrapTermsIntoNewSum(transformed, sumExpr);
		return transformed;
	}

	protected ArithmeticExpression wrapTermsIntoNewSum(final ArithmeticExpression transformed,
			final SumExpression sumTemplate) {
		ArithmeticExpression clone = null;
		if (transformed instanceof BinaryArithmeticExpression binary) {
			switch (binary.getOperator()) {
			case ADD:
			case SUBTRACT:
				BinaryArithmeticExpression cb = factory.createBinaryArithmeticExpression();
				clone = cb;
				cb.setOperator(binary.getOperator());
				cb.setLhs(wrapTermsIntoNewSum(binary.getLhs(), sumTemplate));
				cb.setRhs(wrapTermsIntoNewSum(binary.getRhs(), sumTemplate));
				break;
			default:
				SumExpression cs = (SumExpression) cloneExpression(sumTemplate, null);
				transformedSums.add(cs);
				cs.setExpression(cloneExpression(binary, cs));
				ArithmeticValue val = factory.createArithmeticValue();
				val.setValue(cs);
				clone = val;
			}
		} else if (transformed instanceof UnaryArithmeticExpression unary) {
			SumExpression cs = (SumExpression) cloneExpression(sumTemplate, null);
			transformedSums.add(cs);
			cs.setExpression(cloneExpression(unary, cs));
			ArithmeticValue val = factory.createArithmeticValue();
			val.setValue(cs);
			clone = val;
		} else if (transformed instanceof ArithmeticLiteral literal) {
			SumExpression cs = (SumExpression) cloneExpression(sumTemplate, null);
			transformedSums.add(cs);
			cs.setExpression(cloneExpression(literal, cs));
			ArithmeticValue val = factory.createArithmeticValue();
			val.setValue(cs);
			clone = val;
		} else if (transformed instanceof VariableReference ref) {
			SumExpression cs = (SumExpression) cloneExpression(sumTemplate, null);
			transformedSums.add(cs);
			cs.setExpression(cloneExpression(ref, cs));
			ArithmeticValue val = factory.createArithmeticValue();
			val.setValue(cs);
			clone = val;
		} else if (transformed instanceof ArithmeticValue val) {
			SumExpression cs = (SumExpression) cloneExpression(sumTemplate, null);
			transformedSums.add(cs);
			cs.setExpression(cloneExpression(val, cs));
			ArithmeticValue cv = factory.createArithmeticValue();
			cv.setValue(cs);
			clone = cv;
		} else {
			throw new UnsupportedOperationException("Unknown arithmetic expression type: " + transformed);
		}
		return clone;
	}

	protected StreamExpression cloneExpression(final StreamExpression stream, final SumExpression rootSum) {
		StreamExpression clone = factory.createStreamExpression();
		clone.setOperandName(stream.getOperandName());

		if (stream.getCurrent() instanceof StreamNoOperation streamNOOP) {
			StreamNoOperation cloneNOOP = factory.createStreamNoOperation();
			clone.setCurrent(cloneNOOP);
		} else if (stream.getCurrent() instanceof StreamFilterOperation filterOP) {
			StreamFilterOperation cloneFilter = factory.createStreamFilterOperation();
			clone.setCurrent(cloneFilter);
			cloneFilter.setPredicate(cloneExpression(filterOP.getPredicate(), rootSum));
		} else if (stream.getCurrent() instanceof StreamSelectOperation selectOP) {
			StreamSelectOperation cloneSelect = factory.createStreamSelectOperation();
			clone.setCurrent(cloneSelect);
			cloneSelect.setType(selectOP.getType());
		} else if (stream.getCurrent() instanceof StreamContainsOperation containsOP) {
			StreamContainsOperation cloneContains = factory.createStreamContainsOperation();
			clone.setCurrent(cloneContains);
			cloneContains.setExpr(cloneExpression(containsOP.getExpr(), rootSum));
		} else {
			throw new UnsupportedOperationException("Unknown stream operation: " + stream.getCurrent());
		}

		if (stream.getChild() != null)
			clone.setChild(cloneExpression(stream.getChild(), rootSum));

		return clone;
	}

	protected BoolExpression cloneExpression(final BoolExpression bool, final SumExpression rootSum) {
		BoolExpression clone = null;
		if (bool instanceof BoolBinaryExpression binary) {
			clone = factory.createBoolBinaryExpression();
			((BoolBinaryExpression) clone).setOperator(binary.getOperator());
			((BoolBinaryExpression) clone).setLhs(cloneExpression(binary.getLhs(), rootSum));
			((BoolBinaryExpression) clone).setRhs(cloneExpression(binary.getRhs(), rootSum));
		} else if (bool instanceof BoolUnaryExpression unary) {
			clone = factory.createBoolBinaryExpression();
			((BoolUnaryExpression) clone).setOperator(unary.getOperator());
			((BoolUnaryExpression) clone).setExpression(cloneExpression(unary.getExpression(), rootSum));
		} else if (bool instanceof BoolValueExpression val) {
			if (val instanceof BoolValue boolVal) {
				clone = factory.createBoolValue();
				((BoolValue) clone).setValue(cloneExpression(boolVal.getValue(), rootSum));
			} else if (val instanceof BoolLiteral boolLit) {
				clone = factory.createBoolLiteral();
				((BoolLiteral) clone).setLiteral(boolLit.isLiteral());
			} else if (val instanceof RelationalExpression relation) {
				RelationalExpression cr = factory.createRelationalExpression();
				clone = cr;
				cr.setOperator(relation.getOperator());
				cr.setLhs(cloneExpression(relation.getLhs(), rootSum));
				cr.setRhs(cloneExpression(relation.getRhs(), rootSum));
			} else {
				throw new UnsupportedOperationException("Unknown boolean expression: " + bool);
			}
		} else {
			throw new UnsupportedOperationException("Unknown boolean expression: " + bool);
		}

		return clone;
	}

	protected ValueExpression cloneExpression(final ValueExpression value, final SumExpression rootSum) {
		ValueExpression clone = null;
		if (value instanceof MappingSumExpression mapSum) {
			if (rootSum != null)
				throw new UnsupportedOperationException("Nested sum expressions are currently not supported.");

			MappingSumExpression cm = factory.createMappingSumExpression();
			clone = cm;
			cm.setOperandName(mapSum.getOperandName());
			cm.setMapping(mapSum.getMapping());
			cm.setFilter(cloneExpression(mapSum.getFilter(), cm));
			cm.setExpression(cloneExpression(mapSum.getExpression(), cm));
		} else if (value instanceof TypeSumExpression typeSum) {
			if (rootSum != null)
				throw new UnsupportedOperationException("Nested sum expressions are currently not supported.");

			TypeSumExpression ct = factory.createTypeSumExpression();
			clone = ct;
			ct.setOperandName(typeSum.getOperandName());
			ct.setType(typeSum.getType());
			ct.setFilter(cloneExpression(typeSum.getFilter(), ct));
			ct.setExpression(cloneExpression(typeSum.getExpression(), ct));
		} else if (value instanceof PatternSumExpression patternSum) {
			if (rootSum != null)
				throw new UnsupportedOperationException("Nested sum expressions are currently not supported.");

			PatternSumExpression cp = factory.createPatternSumExpression();
			clone = cp;
			cp.setOperandName(patternSum.getOperandName());
			cp.setPattern(patternSum.getPattern());
			cp.setFilter(cloneExpression(patternSum.getFilter(), cp));
			cp.setExpression(cloneExpression(patternSum.getExpression(), cp));
		} else if (value instanceof ContextSumExpression contextSum) {
			if (rootSum != null)
				throw new UnsupportedOperationException("Nested sum expressions are currently not supported.");

			ContextSumExpression cc = factory.createContextSumExpression();
			clone = cc;
			cc.setOperandName(contextSum.getOperandName());
			cc.setContext(contextSum.getContext());
			cc.setFilter(cloneExpression(contextSum.getFilter(), cc));
			cc.setExpression(cloneExpression(contextSum.getExpression(), cc));
		} else if (value instanceof ContextTypeFeatureValue feat) {
			ContextTypeFeatureValue cf = factory.createContextTypeFeatureValue();
			clone = cf;
			cf.setFeatureExpression(cloneExpression(feat.getFeatureExpression(), rootSum));
			cf.setTypeContext(feat.getTypeContext());
		} else if (value instanceof ContextTypeValue val) {
			ContextTypeValue cv = factory.createContextTypeValue();
			clone = cv;
			cv.setTypeContext(val.getTypeContext());
		} else if (value instanceof ContextPatternNodeFeatureValue feat) {
			ContextPatternNodeFeatureValue cf = factory.createContextPatternNodeFeatureValue();
			clone = cf;
			cf.setPatternContext(feat.getPatternContext());
			cf.setNode(feat.getNode());
			cf.setFeatureExpression(cloneExpression(feat.getFeatureExpression(), rootSum));
		} else if (value instanceof ContextMappingNodeFeatureValue feat) {
			ContextMappingNodeFeatureValue cf = factory.createContextMappingNodeFeatureValue();
			clone = cf;
			cf.setMappingContext(feat.getMappingContext());
			cf.setNode(feat.getNode());
			cf.setFeatureExpression(cloneExpression(feat.getFeatureExpression(), rootSum));
		} else if (value instanceof ContextMappingValue val) {
			ContextMappingValue cv = factory.createContextMappingValue();
			clone = cv;
			cv.setMappingContext(val.getMappingContext());
		} else if (value instanceof ContextPatternValue val) {
			ContextPatternValue cv = factory.createContextPatternValue();
			clone = cv;
			cv.setPatternContext(val.getPatternContext());
		} else if (value instanceof ObjectiveFunctionValue val) {
			ObjectiveFunctionValue cv = factory.createObjectiveFunctionValue();
			clone = cv;
			cv.setObjective(val.getObjective());
		} else if (value instanceof IteratorMappingValue val) {
			IteratorMappingValue cv = factory.createIteratorMappingValue();
			clone = cv;
			cv.setMappingContext(val.getMappingContext());
			cv.setStream(rootSum);
		} else if (value instanceof IteratorMappingVariableValue val) {
			IteratorMappingVariableValue cv = factory.createIteratorMappingVariableValue();
			clone = cv;
			cv.setMappingContext(val.getMappingContext());
			cv.setStream(rootSum);
		} else if (value instanceof IteratorMappingFeatureValue val) {
			IteratorMappingFeatureValue cv = factory.createIteratorMappingFeatureValue();
			clone = cv;
			cv.setMappingContext(val.getMappingContext());
			cv.setStream(rootSum);
			cv.setFeatureExpression(cloneExpression(val.getFeatureExpression(), rootSum));
		} else if (value instanceof IteratorMappingNodeFeatureValue val) {
			IteratorMappingNodeFeatureValue cv = factory.createIteratorMappingNodeFeatureValue();
			clone = cv;
			cv.setMappingContext(val.getMappingContext());
			cv.setStream(rootSum);
			cv.setFeatureExpression(cloneExpression(val.getFeatureExpression(), rootSum));
			cv.setNode(val.getNode());
		} else if (value instanceof IteratorMappingNodeValue val) {
			IteratorMappingNodeValue cv = factory.createIteratorMappingNodeValue();
			clone = cv;
			cv.setMappingContext(val.getMappingContext());
			cv.setStream(rootSum);
			cv.setNode(val.getNode());
		} else if (value instanceof IteratorMappingVariablesReference ref) {
			IteratorMappingVariablesReference cv = factory.createIteratorMappingVariablesReference();
			clone = cv;
			cv.setMappingContext(ref.getMappingContext());
			cv.setStream(rootSum);
			cv.setVar(cloneExpression(ref.getVar(), rootSum));
		} else if (value instanceof IteratorTypeValue val) {
			IteratorTypeValue cv = factory.createIteratorTypeValue();
			clone = cv;
			cv.setTypeContext(val.getTypeContext());
			cv.setStream(rootSum);
		} else if (value instanceof IteratorTypeFeatureValue val) {
			IteratorTypeFeatureValue cv = factory.createIteratorTypeFeatureValue();
			clone = cv;
			cv.setTypeContext(val.getTypeContext());
			cv.setStream(rootSum);
			cv.setFeatureExpression(cloneExpression(val.getFeatureExpression(), rootSum));
		} else if (value instanceof IteratorPatternValue val) {
			IteratorPatternValue cv = factory.createIteratorPatternValue();
			clone = cv;
			cv.setPatternContext(val.getPatternContext());
			cv.setStream(rootSum);
		} else if (value instanceof IteratorPatternFeatureValue val) {
			IteratorPatternFeatureValue cv = factory.createIteratorPatternFeatureValue();
			clone = cv;
			cv.setPatternContext(val.getPatternContext());
			cv.setStream(rootSum);
			cv.setFeatureExpression(cloneExpression(val.getFeatureExpression(), rootSum));
		} else if (value instanceof IteratorPatternNodeFeatureValue val) {
			IteratorPatternNodeFeatureValue cv = factory.createIteratorPatternNodeFeatureValue();
			clone = cv;
			cv.setPatternContext(val.getPatternContext());
			cv.setStream(rootSum);
			cv.setFeatureExpression(cloneExpression(val.getFeatureExpression(), rootSum));
			cv.setNode(val.getNode());
		} else if (value instanceof IteratorPatternNodeValue val) {
			IteratorPatternNodeValue cv = factory.createIteratorPatternNodeValue();
			clone = cv;
			cv.setPatternContext(val.getPatternContext());
			cv.setStream(rootSum);
			cv.setNode(val.getNode());
		} else if (value instanceof ContextMappingVariablesReference ref) {
			ContextMappingVariablesReference cv = factory.createContextMappingVariablesReference();
			clone = cv;
			cv.setMappingContext(ref.getMappingContext());
			cv.setVar(cloneExpression(ref.getVar(), rootSum));
		} else if (value instanceof ContextMappingNode node) {
			ContextMappingNode cv = factory.createContextMappingNode();
			clone = cv;
			cv.setMappingContext(node.getMappingContext());
			cv.setNode(node.getNode());
		} else if (value instanceof ContextPatternNode node) {
			ContextPatternNode cv = factory.createContextPatternNode();
			clone = cv;
			cv.setPatternContext(node.getPatternContext());
			cv.setNode(node.getNode());
		} else {
			throw new IllegalArgumentException("Unknown value expression Type: " + value);
		}

		return clone;
	}

	protected FeatureExpression cloneExpression(final FeatureExpression feat, final SumExpression rootSum) {
		FeatureExpression clone = factory.createFeatureExpression();

		FeatureLiteral cl = factory.createFeatureLiteral();
		cl.setFeature(feat.getCurrent().getFeature());
		clone.setCurrent(cl);

		if (feat.getChild() != null)
			clone.setChild(cloneExpression(feat.getChild(), rootSum));

		return clone;
	}

	protected VariableReference cloneExpression(final VariableReference ref, final SumExpression rootSum) {
		VariableReference clone = factory.createVariableReference();
		clone.setVariable(ref.getVariable());

		return clone;
	}

	protected ArithmeticExpression cloneExpression(final ArithmeticExpression expr, final SumExpression rootSum) {
		ArithmeticExpression clone = null;
		if (expr instanceof BinaryArithmeticExpression binary) {
			BinaryArithmeticExpression cb = factory.createBinaryArithmeticExpression();
			clone = cb;
			cb.setOperator(binary.getOperator());
			cb.setLhs(cloneExpression(binary.getLhs(), rootSum));
			cb.setRhs(cloneExpression(binary.getRhs(), rootSum));
		} else if (expr instanceof UnaryArithmeticExpression unary) {
			UnaryArithmeticExpression cu = factory.createUnaryArithmeticExpression();
			clone = cu;
			cu.setOperator(unary.getOperator());
			cu.setExpression(cloneExpression(unary.getExpression(), rootSum));
		} else if (expr instanceof ArithmeticLiteral literal) {
			if (literal instanceof ArithmeticNullLiteral nl) {
				ArithmeticNullLiteral cn = factory.createArithmeticNullLiteral();
				clone = cn;
			} else if (literal instanceof IntegerLiteral il) {
				IntegerLiteral ci = factory.createIntegerLiteral();
				clone = ci;
				ci.setLiteral(il.getLiteral());
			} else if (literal instanceof DoubleLiteral dl) {
				DoubleLiteral cd = factory.createDoubleLiteral();
				clone = cd;
				cd.setLiteral(dl.getLiteral());
			} else {
				throw new UnsupportedOperationException("Unknown arithmetic expression type: " + expr);
			}
		} else if (expr instanceof VariableReference ref) {
			clone = cloneExpression(ref, rootSum);
		} else if (expr instanceof ArithmeticValue val) {
			ArithmeticValue cv = factory.createArithmeticValue();
			cv.setValue(cloneExpression(val.getValue(), rootSum));
			clone = cv;
		} else {
			throw new UnsupportedOperationException("Unknown arithmetic expression type: " + expr);
		}
		return clone;
	}

	protected String addSymbol(final ArithmeticExpression expr) {
		if (expr2symbol.containsKey(expr))
			return expr2symbol.get(expr);

		int count = expr2symbol.size();
		StringBuilder symbol = new StringBuilder(count + "_");

		if (expr instanceof BinaryArithmeticExpression binaryExpr) {
			switch (binaryExpr.getOperator()) {
			case ADD -> {
				symbol.append("ADD");
			}
			case DIVIDE -> {
				symbol.append("DIVIDE");
			}
			case LOG -> {
				symbol.append("LOG");
			}
			case MULTIPLY -> {
				symbol.append("MULTIPLY");
			}
			case POW -> {
				symbol.append("POW");
			}
			case SUBTRACT -> {
				symbol.append("SUBTRACT");
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + binaryExpr.getOperator());
			}
			}
		} else if (expr instanceof UnaryArithmeticExpression unaryExpr) {
			switch (unaryExpr.getOperator()) {
			case ABSOLUTE -> {
				symbol.append("ABS");
			}
			case BRACKET -> {
				symbol.append("BRACKET");
			}
			case COSINE -> {
				symbol.append("COS");
			}
			case NEGATE -> {
				symbol.append("NEGATE");
			}
			case SINE -> {
				symbol.append("SIN");
			}
			case SQRT -> {
				symbol.append("SQRT");
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + unaryExpr.getOperator());
			}
			}

		} else if (expr instanceof ArithmeticValue valExpr) {
			// CASE: SUM-Expressions
			if (valExpr.getValue() instanceof SumExpression sum) {
				symbol.append("SUM");
			} else {
				symbol.append("VAL");
			}
		} else {
			// CASE: Literals
			symbol.append("LIT");
		}

		expr2symbol.put(expr, symbol.toString());

		return symbol.toString();
	}
	

	public static StringBuilder parseToString(final ArithmeticExpression expr) throws ParserException {
		StringBuilder sb = new StringBuilder();
		if (expr instanceof BinaryArithmeticExpression binaryExpr) {
			switch (binaryExpr.getOperator()) {
			case ADD -> {
				sb.append(parseToString(binaryExpr.getLhs()));
				sb.append(" + ");
				sb.append(parseToString(binaryExpr.getRhs()));
			}
			case DIVIDE -> {
				sb.append(parseToString(binaryExpr.getLhs()));
				sb.append(" / (");
				sb.append(parseToString(binaryExpr.getRhs()) + ")");
			}
			case LOG -> {
				// Log-expressions will not be transformed
				sb.append(expr);
			}
			case MULTIPLY -> {
				sb.append(parseToString(binaryExpr.getLhs()));
				sb.append(" * ");
				sb.append(parseToString(binaryExpr.getRhs()));
			}
			case POW -> {
				// Exp-expressions will not be transformed
				sb.append(expr);
			}
			case SUBTRACT -> {
				sb.append(parseToString(binaryExpr.getLhs()));
				sb.append(" - (");
				sb.append(parseToString(binaryExpr.getRhs()) + ")");
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + binaryExpr.getOperator());
			}
			}
		} else if (expr instanceof UnaryArithmeticExpression unaryExpr) {
			switch (unaryExpr.getOperator()) {
			case ABSOLUTE -> {
				// Abs-expressions will not be transformed
				sb.append("ABS_" + expr);
			}
			case BRACKET -> {
				sb.append("(" + parseToString(unaryExpr.getExpression()) + ")");
			}
			case COSINE -> {
				// cos-expressions will not be transformed
				sb.append("COS_" + expr);
			}
			case NEGATE -> {
				sb.append("~(" + parseToString(unaryExpr.getExpression()) + ")");
			}
			case SINE -> {
				// sin-expressions will not be transformed
				sb.append("SIN_" + expr);
			}
			case SQRT -> {
				// sqrt-expressions will not be transformed
				sb.append("SQRT_" + expr);
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + unaryExpr.getOperator());
			}
			}

		} else if (expr instanceof ArithmeticValue valExpr) {
			// CASE: SUM-Expressions
			if (valExpr.getValue() instanceof SumExpression sum) {
				sb.append("SUM_[" + parseToString(sum.getExpression()) + "]");
			} else {
				sb.append("VAL_" + valExpr.getValue().eClass().getName() + "(" + valExpr.getValue().hashCode() + ")");
			}
		} else {
			// CASE: Literals
			sb.append("LIT");
		}

		return sb;
	}
}
