package org.emoflon.gips.build.transformation.transformer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.GipsMappingAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsPatternAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamArithmetic;
import org.emoflon.gips.gipsl.gipsl.GipsStreamExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeAttributeExpr;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariableValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Pattern;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.SumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Type;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;

public class SumExpressionTransformer<T extends EObject> extends TransformationContext<T> {

	protected SumExpressionTransformer(GipsTransformationData data, T context, TransformerFactory factory) {
		super(data, context, factory);
	}

	public SumExpression transform(GipsMappingAttributeExpr eGipsMapping) throws Exception {
		MappingSumExpression mapSum = factory.createMappingSumExpression();
		Mapping mapping = data.eMapping2Mapping().get(eGipsMapping.getMapping());
		GipsStreamExpr streamExpr = eGipsMapping.getExpr();
		data.eStream2SetOp().put(streamExpr, mapSum);
		mapSum.setMapping(mapping);
		mapSum.setReturnType(EcorePackage.Literals.EINT);
		// Simple expression: Just add all filtered (!) mapping variable values v={0,1}
		ArithmeticValue val = factory.createArithmeticValue();
		IteratorMappingVariableValue itr = factory.createIteratorMappingVariableValue();
//		TODO: Is this next line necessary?
		itr.setMappingContext(mapping);
		itr.setStream(mapSum);
		val.setValue(itr);
		val.setReturnType(EcorePackage.Literals.EINT);
		mapSum.setExpression(val);
		// Create filter expression
		StreamExpressionTransformer transformer = transformerFactory.createStreamTransformer(mapSum);
		mapSum.setFilter(transformer.transform(streamExpr));
		// Check for valid ilp semantics
		ArithmeticExpressionType filterExpressionType = GipsTransformationUtils
				.isConstantExpression(mapSum.getExpression());
//		TODO: rethink this check...
//		if(filterExpressionType == ArithmeticExpressionType.variableVector || filterExpressionType == ArithmeticExpressionType.variableValue)
//			throw new UnsupportedOperationException("Variable simultaneous value access in filter and arithmetic expression is forbidden.");

		return mapSum;
	}

	public SumExpression transform(final GipsMappingAttributeExpr eGipsMapping,
			final GipsStreamArithmetic streamArithmetic) throws Exception {
		MappingSumExpression mapSum = factory.createMappingSumExpression();
		Mapping mapping = data.eMapping2Mapping().get(eGipsMapping.getMapping());
		GipsStreamExpr streamExpr = eGipsMapping.getExpr();
		data.eStream2SetOp().put(streamExpr, mapSum);
		mapSum.setMapping(mapping);
		mapSum.setReturnType(EcorePackage.Literals.EDOUBLE);

		StreamExpressionTransformer streamTransformer = transformerFactory.createStreamTransformer(mapSum);
		mapSum.setFilter(streamTransformer.transform(streamExpr));
		ArithmeticExpressionType filterExpressionType = GipsTransformationUtils
				.isConstantExpression(mapSum.getFilter());

		ArithmeticExpressionTransformer arithmeticTransformer = transformerFactory.createArithmeticTransformer(mapSum);
		if (streamArithmetic.getLambda().getExpr() instanceof GipsRelExpr relExpr && relExpr.getRight() == null) {
			mapSum.setExpression(arithmeticTransformer.transform(relExpr.getLeft()));

			ArithmeticExpressionType arithmeticExpressionType = GipsTransformationUtils
					.isConstantExpression(mapSum.getExpression());
//			TODO: rethink this check...
//			if((filterExpressionType == ArithmeticExpressionType.variableVector || filterExpressionType == ArithmeticExpressionType.variableValue) &&
//					(arithmeticExpressionType == ArithmeticExpressionType.variableVector || arithmeticExpressionType == ArithmeticExpressionType.variableValue)) {
//				throw new UnsupportedOperationException("Variable simultaneous value access in filter and arithmetic expression is forbidden.");
//			}
			return mapSum;
		} else {
			throw new IllegalArgumentException(
					"A sum stream expression may only contain arithmetic expressions or arithmetic values.");
		}

	}

	public SumExpression transform(final GipsTypeAttributeExpr eGipsType, final GipsStreamArithmetic streamArithmetic)
			throws Exception {
		TypeSumExpression typeSum = factory.createTypeSumExpression();
		Type type = data.getType(eGipsType.getType());
		GipsStreamExpr streamExpr = eGipsType.getExpr();
		data.eStream2SetOp().put(streamExpr, typeSum);
		typeSum.setType(type);
		typeSum.setReturnType(EcorePackage.Literals.EDOUBLE);

		StreamExpressionTransformer streamTransformer = transformerFactory.createStreamTransformer(typeSum);
		typeSum.setFilter(streamTransformer.transform(streamExpr));
		ArithmeticExpressionType filterExpressionType = GipsTransformationUtils
				.isConstantExpression(typeSum.getFilter());

		ArithmeticExpressionTransformer arithmeticTransformer = transformerFactory.createArithmeticTransformer(typeSum);
		if (streamArithmetic.getLambda().getExpr() instanceof GipsRelExpr relExpr && relExpr.getRight() == null) {
			typeSum.setExpression(arithmeticTransformer.transform(relExpr.getLeft()));

			ArithmeticExpressionType arithmeticExpressionType = GipsTransformationUtils
					.isConstantExpression(typeSum.getExpression());
//			TODO: rethink this check...
//			if((filterExpressionType == ArithmeticExpressionType.variableVector || filterExpressionType == ArithmeticExpressionType.variableValue) &&
//					(arithmeticExpressionType == ArithmeticExpressionType.variableVector || arithmeticExpressionType == ArithmeticExpressionType.variableValue)) {
//				throw new UnsupportedOperationException("Variable simultaneous value access in filter and arithmetic expression is forbidden.");
//			}
			return typeSum;
		} else {
			throw new IllegalArgumentException(
					"A sum stream expression may only contain arithmetic expressions or arithmetic values.");
		}

	}

	public SumExpression transform(final GipsTypeAttributeExpr eGipsType) throws Exception {
		TypeSumExpression typeSum = factory.createTypeSumExpression();
		Type type = data.getType(eGipsType.getType());
		GipsStreamExpr streamExpr = eGipsType.getExpr();
		data.eStream2SetOp().put(streamExpr, typeSum);
		typeSum.setType(type);
		typeSum.setReturnType(EcorePackage.Literals.EINT);
		// Simple expression: Just count all filtered (!) objects
		DoubleLiteral lit = factory.createDoubleLiteral();
		lit.setLiteral(1);
		typeSum.setExpression(lit);
		// Create filter expression

		StreamExpressionTransformer streamTransformer = transformerFactory.createStreamTransformer(typeSum);
		typeSum.setFilter(streamTransformer.transform(streamExpr));
		ArithmeticExpressionType filterExpressionType = GipsTransformationUtils
				.isConstantExpression(typeSum.getFilter());

		return typeSum;
	}

	public SumExpression transform(final GipsPatternAttributeExpr eGipsPattern,
			final GipsStreamArithmetic streamArithmetic) throws Exception {
		PatternSumExpression patternSum = factory.createPatternSumExpression();
		Pattern pattern = data.getPattern(eGipsPattern.getPattern());
		GipsStreamExpr streamExpr = eGipsPattern.getExpr();
		data.eStream2SetOp().put(streamExpr, patternSum);
		patternSum.setPattern(pattern);
		patternSum.setReturnType(EcorePackage.Literals.EDOUBLE);

		StreamExpressionTransformer streamTransformer = transformerFactory.createStreamTransformer(patternSum);
		patternSum.setFilter(streamTransformer.transform(streamExpr));
		ArithmeticExpressionType filterExpressionType = GipsTransformationUtils
				.isConstantExpression(patternSum.getFilter());

		ArithmeticExpressionTransformer arithmeticTransformer = transformerFactory
				.createArithmeticTransformer(patternSum);
		if (streamArithmetic.getLambda().getExpr() instanceof GipsRelExpr relExpr && relExpr.getRight() == null) {
			patternSum.setExpression(arithmeticTransformer.transform(relExpr.getLeft()));

			ArithmeticExpressionType arithmeticExpressionType = GipsTransformationUtils
					.isConstantExpression(patternSum.getExpression());
//			TODO: rethink this check...
//			if((filterExpressionType == ArithmeticExpressionType.variableVector || filterExpressionType == ArithmeticExpressionType.variableValue) &&
//					(arithmeticExpressionType == ArithmeticExpressionType.variableVector || arithmeticExpressionType == ArithmeticExpressionType.variableValue)) {
//				throw new UnsupportedOperationException("Variable simultaneous value access in filter and arithmetic expression is forbidden.");
//			}
			return patternSum;
		} else {
			throw new IllegalArgumentException(
					"A sum stream expression may only contain arithmetic expressions or arithmetic values.");
		}

	}

	public SumExpression transform(final GipsPatternAttributeExpr eGipsPattern) throws Exception {
		PatternSumExpression patternSum = factory.createPatternSumExpression();
		Pattern pattern = data.getPattern(eGipsPattern.getPattern());
		GipsStreamExpr streamExpr = eGipsPattern.getExpr();
		data.eStream2SetOp().put(streamExpr, patternSum);
		patternSum.setPattern(pattern);
		patternSum.setReturnType(EcorePackage.Literals.EDOUBLE);

		// Simple expression: Just count all filtered (!) objects
		DoubleLiteral lit = factory.createDoubleLiteral();
		lit.setLiteral(1);
		patternSum.setExpression(lit);
		// Create filter expression

		StreamExpressionTransformer streamTransformer = transformerFactory.createStreamTransformer(patternSum);
		patternSum.setFilter(streamTransformer.transform(streamExpr));
		ArithmeticExpressionType filterExpressionType = GipsTransformationUtils
				.isConstantExpression(patternSum.getFilter());

		return patternSum;
	}

	public SumExpression transform(final Type typeContext, final FeatureExpression fe, GipsStreamExpr streamExpr)
			throws Exception {
		ContextSumExpression typeSum = factory.createContextSumExpression();
		data.eStream2SetOp().put(streamExpr, typeSum);
		typeSum.setContext(typeContext);
		typeSum.setFeature(fe);
		typeSum.setReturnType(EcorePackage.Literals.EINT);
		// Simple expression: Just count all filtered (!) objects
		DoubleLiteral lit = factory.createDoubleLiteral();
		lit.setLiteral(1);
		typeSum.setExpression(lit);
		// Create filter expression
		StreamExpressionTransformer transformer = transformerFactory.createStreamTransformer(typeSum);
		typeSum.setFilter(transformer.transform(streamExpr));
		// Check for valid ilp semantics
		ArithmeticExpressionType filterExpressionType = GipsTransformationUtils
				.isConstantExpression(typeSum.getExpression());
//		TODO: rethink this check...
//		if(filterExpressionType == ArithmeticExpressionType.variableVector || filterExpressionType == ArithmeticExpressionType.variableValue)
//			throw new UnsupportedOperationException("Variable simultaneous value access in filter and arithmetic expression is forbidden.");

		return typeSum;
	}

	public SumExpression transform(final Type typeContext, final FeatureExpression fe, final GipsStreamExpr streamExpr,
			final GipsStreamArithmetic streamArithmetic) throws Exception {
		ContextSumExpression typeSum = factory.createContextSumExpression();
		data.eStream2SetOp().put(streamExpr, typeSum);
		typeSum.setContext(typeContext);
		typeSum.setFeature(fe);
		typeSum.setReturnType(EcorePackage.Literals.EDOUBLE);

		// Create filter expression
		StreamExpressionTransformer transformer = transformerFactory.createStreamTransformer(typeSum);
		typeSum.setFilter(transformer.transform(streamExpr));
//		// Check for valid ilp semantics
//		ArithmeticExpressionType filterExpressionType = GipsTransformationUtils
//				.isConstantExpression(typeSum.getExpression());

		ArithmeticExpressionTransformer arithmeticTransformer = transformerFactory.createArithmeticTransformer(typeSum);
		if (streamArithmetic.getLambda().getExpr() instanceof GipsRelExpr relExpr && relExpr.getRight() == null) {
			typeSum.setExpression(arithmeticTransformer.transform(relExpr.getLeft()));

			ArithmeticExpressionType arithmeticExpressionType = GipsTransformationUtils
					.isConstantExpression(typeSum.getExpression());
//			TODO: rethink this check...
//			if((filterExpressionType == ArithmeticExpressionType.variableVector || filterExpressionType == ArithmeticExpressionType.variableValue) &&
//					(arithmeticExpressionType == ArithmeticExpressionType.variableVector || arithmeticExpressionType == ArithmeticExpressionType.variableValue)) {
//				throw new UnsupportedOperationException("Variable simultaneous value access in filter and arithmetic expression is forbidden.");
//			}
			return typeSum;
		} else {
			throw new IllegalArgumentException(
					"A sum stream expression may only contain arithmetic expressions or arithmetic values.");
		}

	}

	public SumExpression transform(final Pattern patternContext, final IBeXNode node, final FeatureExpression fe,
			final GipsStreamExpr streamExpr) throws Exception {
		ContextSumExpression patternSum = factory.createContextSumExpression();
		data.eStream2SetOp().put(streamExpr, patternSum);
		patternSum.setContext(patternContext);
		patternSum.setNode(node);
		patternSum.setFeature(fe);
		patternSum.setReturnType(EcorePackage.Literals.EINT);
		// Simple expression: Just count all filtered (!) objects
		DoubleLiteral lit = factory.createDoubleLiteral();
		lit.setLiteral(1);
		patternSum.setExpression(lit);
		// Create filter expression
		StreamExpressionTransformer transformer = transformerFactory.createStreamTransformer(patternSum);
		patternSum.setFilter(transformer.transform(streamExpr));
		// Check for valid ilp semantics
		ArithmeticExpressionType filterExpressionType = GipsTransformationUtils
				.isConstantExpression(patternSum.getExpression());
//		TODO: rethink this check...
//		if(filterExpressionType == ArithmeticExpressionType.variableVector || filterExpressionType == ArithmeticExpressionType.variableValue)
//			throw new UnsupportedOperationException("Variable simultaneous value access in filter and arithmetic expression is forbidden.");

		return patternSum;
	}

	public SumExpression transform(final Pattern patternContext, final IBeXNode node, final FeatureExpression fe,
			final GipsStreamExpr streamExpr, final GipsStreamArithmetic streamArithmetic) throws Exception {
		ContextSumExpression patternSum = factory.createContextSumExpression();
		data.eStream2SetOp().put(streamExpr, patternSum);
		patternSum.setContext(patternContext);
		patternSum.setNode(node);
		patternSum.setFeature(fe);
		patternSum.setReturnType(EcorePackage.Literals.EDOUBLE);

		// Create filter expression
		StreamExpressionTransformer transformer = transformerFactory.createStreamTransformer(patternSum);
		patternSum.setFilter(transformer.transform(streamExpr));
		// Check for valid ilp semantics
		ArithmeticExpressionType filterExpressionType = GipsTransformationUtils
				.isConstantExpression(patternSum.getExpression());

		ArithmeticExpressionTransformer arithmeticTransformer = transformerFactory
				.createArithmeticTransformer(patternSum);
		if (streamArithmetic.getLambda().getExpr() instanceof GipsRelExpr relExpr && relExpr.getRight() == null) {
			patternSum.setExpression(arithmeticTransformer.transform(relExpr.getLeft()));

			ArithmeticExpressionType arithmeticExpressionType = GipsTransformationUtils
					.isConstantExpression(patternSum.getExpression());
//			TODO: rethink this check...
//			if((filterExpressionType == ArithmeticExpressionType.variableVector || filterExpressionType == ArithmeticExpressionType.variableValue) &&
//					(arithmeticExpressionType == ArithmeticExpressionType.variableVector || arithmeticExpressionType == ArithmeticExpressionType.variableValue)) {
//				throw new UnsupportedOperationException("Variable simultaneous value access in filter and arithmetic expression is forbidden.");
//			}
			return patternSum;
		} else {
			throw new IllegalArgumentException(
					"A sum stream expression may only contain arithmetic expressions or arithmetic values.");
		}
	}

	public SumExpression transform(final Mapping mappingContext, final IBeXNode node, final FeatureExpression fe,
			final GipsStreamExpr streamExpr) throws Exception {
		ContextSumExpression mappingSum = factory.createContextSumExpression();
		data.eStream2SetOp().put(streamExpr, mappingSum);
		mappingSum.setContext(mappingContext);
		mappingSum.setNode(node);
		mappingSum.setFeature(fe);
		mappingSum.setReturnType(EcorePackage.Literals.EINT);
		// Simple expression: Just count all filtered (!) objects
		DoubleLiteral lit = factory.createDoubleLiteral();
		lit.setLiteral(1);
		mappingSum.setExpression(lit);
		// Create filter expression
		StreamExpressionTransformer transformer = transformerFactory.createStreamTransformer(mappingSum);
		mappingSum.setFilter(transformer.transform(streamExpr));
		// Check for valid ilp semantics
		ArithmeticExpressionType filterExpressionType = GipsTransformationUtils
				.isConstantExpression(mappingSum.getExpression());
//		TODO: rethink this check...
//		if(filterExpressionType == ArithmeticExpressionType.variableVector || filterExpressionType == ArithmeticExpressionType.variableValue)
//			throw new UnsupportedOperationException("Variable simultaneous value access in filter and arithmetic expression is forbidden.");

		return mappingSum;
	}

	public SumExpression transform(final Mapping mappingContext, final IBeXNode node, final FeatureExpression fe,
			final GipsStreamExpr streamExpr, final GipsStreamArithmetic streamArithmetic) throws Exception {
		ContextSumExpression mappingSum = factory.createContextSumExpression();
		data.eStream2SetOp().put(streamExpr, mappingSum);
		mappingSum.setContext(mappingContext);
		mappingSum.setNode(node);
		mappingSum.setFeature(fe);
		mappingSum.setReturnType(EcorePackage.Literals.EDOUBLE);

		// Create filter expression
		StreamExpressionTransformer transformer = transformerFactory.createStreamTransformer(mappingSum);
		mappingSum.setFilter(transformer.transform(streamExpr));
		// Check for valid ilp semantics
		ArithmeticExpressionType filterExpressionType = GipsTransformationUtils
				.isConstantExpression(mappingSum.getExpression());

		ArithmeticExpressionTransformer arithmeticTransformer = transformerFactory
				.createArithmeticTransformer(mappingSum);
		if (streamArithmetic.getLambda().getExpr() instanceof GipsRelExpr relExpr && relExpr.getRight() == null) {
			mappingSum.setExpression(arithmeticTransformer.transform(relExpr.getLeft()));

			ArithmeticExpressionType arithmeticExpressionType = GipsTransformationUtils
					.isConstantExpression(mappingSum.getExpression());
//			TODO: rethink this check...
//			if((filterExpressionType == ArithmeticExpressionType.variableVector || filterExpressionType == ArithmeticExpressionType.variableValue) &&
//					(arithmeticExpressionType == ArithmeticExpressionType.variableVector || arithmeticExpressionType == ArithmeticExpressionType.variableValue)) {
//				throw new UnsupportedOperationException("Variable simultaneous value access in filter and arithmetic expression is forbidden.");
//			}
			return mappingSum;
		} else {
			throw new IllegalArgumentException(
					"A sum stream expression may only contain arithmetic expressions or arithmetic values.");
		}
	}

}
