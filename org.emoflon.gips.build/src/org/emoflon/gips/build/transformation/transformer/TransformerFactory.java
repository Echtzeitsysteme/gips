package org.emoflon.gips.build.transformation.transformer;

import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.intermediate.GipsIntermediate.Context;

public class TransformerFactory {
	final protected GipsTransformationData data;

	public TransformerFactory(final GipsTransformationData data) {
		this.data = data;
	}

	public BooleanExpressionTransformer createBooleanTransformer(final Context localContext) throws Exception {
		return new BooleanExpressionTransformer(data, localContext, this);
	}

	public BooleanExpressionTransformer createBooleanTransformer(final Context localContext, final Context setContext)
			throws Exception {
		return new BooleanExpressionTransformer(data, localContext, setContext, this);
	}

	public RelationalExpressionTransformer createRelationalTransformer(final Context localContext) throws Exception {
		return new RelationalExpressionTransformer(data, localContext, this);
	}

	public RelationalExpressionTransformer createRelationalTransformer(final Context localContext,
			final Context setContext) throws Exception {
		return new RelationalExpressionTransformer(data, localContext, setContext, this);
	}

	public ArithmeticExpressionTransformer createArithmeticTransformer(final Context context) throws Exception {
		return new ArithmeticExpressionTransformer(data, context, this);
	}

	public ArithmeticExpressionTransformer createArithmeticTransformer(final Context localContext,
			final Context setContext) throws Exception {
		return new ArithmeticExpressionTransformer(data, localContext, setContext, this);
	}

	public ArithmeticExpressionTransformer createArithmeticTransformer() throws Exception {
		return new ArithmeticInObjectiveTransformer(data, this);
	}

	public ValueExpressionTransformer createValueTransformer(final Context localContext) throws Exception {
		return new ValueExpressionTransformer(data, localContext, this);
	}

	public ValueExpressionTransformer createValueTransformer(final Context localContext, final Context setContext)
			throws Exception {
		return new ValueExpressionTransformer(data, localContext, setContext, this);
	}

}
