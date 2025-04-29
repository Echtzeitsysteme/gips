package org.emoflon.gips.core.trace;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.GipsConstraint;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsLinearFunction;
import org.emoflon.gips.core.GipsMapper;
import org.emoflon.gips.core.milp.model.Constraint;
import org.emoflon.gips.core.milp.model.Term;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.gips.eclipse.api.ILPTraceKeywords;
import org.emoflon.gips.eclipse.trace.TraceMap;

public class Intermediate2IlpTracer {

	private EclipseIntegration integration;
	private final TraceMap<EObject, String> mappings = new TraceMap<>();

	public Intermediate2IlpTracer(EclipseIntegration integration) {
		this.integration = Objects.requireNonNull(integration, "integration");
	}

	public void resetTrace() {
		mappings.clear();
	}

	public TraceMap<EObject, String> getMapping() {
		return mappings;
	}

	public void map(final EObject src, final String dst) {
		if (isTracingEnabled())
			mappings.map(src, dst);
	}

	public String getIntermediateModelId() {
		return integration.getModelIdForIntermediateModel();
	}

	/**
	 * Returns the lp model id. This model id may be available after the ilp build
	 * stage.
	 * 
	 * @return lp model id or null, if not available
	 */
	public String getLpModelId() {
		return integration.getModelIdForLpModel();
	}

	/**
	 * @deprecated Use {@link GipsEngine#getEclipseIntegrationConfig()} instead
	 */
	@Deprecated
	public void setRMIPort(int port) {
		integration.getConfig().setServicePort(port);
	}

	public void enableTracing(boolean enableTracing) {
		integration.getConfig().setTracingEnabled(enableTracing);
	}

	public boolean isTracingEnabled() {
		return integration.getConfig().isTracingEnabled();
	}

	public void buildTraceGraph(GipsEngine gipsEngine) {
		// try to build a bridge between ILP model and ILP text file

		for (GipsMapper<?> mapper : gipsEngine.getMappers().values()) {
			map(mapper.getMapping(), ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_MAPPING, mapper.getName()));
		}

		for (GipsConstraint<?, ?, ?> constraint : gipsEngine.getConstraints().values()) {
			map(constraint.getIntermediateConstraint(),
					ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT, constraint.getName()));
			for (Constraint ilpConstraint : constraint.getConstraints()) {
				for (Term ilpTerm : ilpConstraint.lhsTerms()) {
					map(constraint.getIntermediateConstraint().getExpression(), ILPTraceKeywords
							.buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT_VAR, ilpTerm.variable().getName()));
				}
			}

			for (Variable<?> variables : constraint.getAdditionalVariables()) {
				map(constraint.getIntermediateConstraint().getExpression(),
						ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT_VAR, variables.getName()));
			}
		}

		for (GipsLinearFunction<?, ?, ?> function : gipsEngine.getLinearFunctions().values()) {
			map(function.getIntermediateLinearFunction(),
					ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_FUNCTION, function.getName()));
			for (Term term : function.getLinearFunctionFunction().terms()) {
				map(function.getIntermediateLinearFunction(),
						ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_FUNCTION_VAR, term.variable().getName()));
			}
		}

		if (gipsEngine.getObjective() != null) {
			map(gipsEngine.getObjective().getIntermediateObjective(),
					ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_OBJECTIVE, ""));
		}
	}

}
