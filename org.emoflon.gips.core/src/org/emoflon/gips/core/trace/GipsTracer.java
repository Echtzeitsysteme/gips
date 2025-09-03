package org.emoflon.gips.core.trace;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.GipsConstraint;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsLinearFunction;
import org.emoflon.gips.core.GipsMapper;
import org.emoflon.gips.core.GipsMapping;
import org.emoflon.gips.core.gt.GipsGTMapping;
import org.emoflon.gips.core.milp.model.Constraint;
import org.emoflon.gips.core.milp.model.Term;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.gips.eclipse.api.MILPTraceKeywords;
import org.emoflon.gips.eclipse.trace.TraceMap;
import org.emoflon.ibex.common.operational.IMatch;

public class GipsTracer {

	private EclipseIntegration integration;
	private final TraceMap<EObject, String> intermediateMappings = new TraceMap<>();
	private final TraceMap<EObject, String> inputMappings = new TraceMap<>();
	private final TraceMap<String, EObject> outputMappings = new TraceMap<>();

	public GipsTracer(EclipseIntegration integration) {
		this.integration = Objects.requireNonNull(integration, "integration");
	}

	public void resetTrace() {
		resetIntermediate2LpMapping();
		resetInput2LpMapping();
		resetLp2OutputMapping();
	}

	public TraceMap<EObject, String> getIntermediate2LpMapping() {
		return intermediateMappings;
	}

	public TraceMap<EObject, String> getInput2LpMapping() {
		return inputMappings;
	}

	public TraceMap<String, EObject> getLp2OutputMapping() {
		return outputMappings;
	}

	public void resetIntermediate2LpMapping() {
		intermediateMappings.clear();
	}

	public void resetInput2LpMapping() {
		inputMappings.clear();
	}

	public void resetLp2OutputMapping() {
		outputMappings.clear();
	}

	private void mapInput2LpVariable(IMatch inputMatch, String dst) {
		for (Object matchedObject : inputMatch.getObjects()) {
			if (matchedObject instanceof EObject eObject)
				inputMappings.map(eObject, dst);
		}
	}

	private void mapIntermediate2Lp(final EObject src, final String dst) {
		intermediateMappings.map(src, dst);
	}

	public void mapLpVariable2Output(String mapName, IMatch outputMatch) {
		// method is called from the outside, it is necessary to convert mapName into a
		// valid type-value pair for our graph.
		String variableId = MILPTraceKeywords.buildElementId(MILPTraceKeywords.TYPE_VARIABLE, mapName);

		for (Object matchedObject : outputMatch.getObjects()) {
			if (matchedObject instanceof EObject eObject)
				outputMappings.map(variableId, eObject);
		}
	}

	/**
	 * @see EclipseIntegration#getModelIdForInputModel()
	 */
	public String getInputModelId() {
		return integration.getModelIdForInputModel();
	}

	/**
	 * @see EclipseIntegration#getModelIdForIntermediateModel()
	 */
	public String getIntermediateModelId() {
		return integration.getModelIdForIntermediateModel();
	}

	/**
	 * @see EclipseIntegration#getModelIdForLpModel()
	 */
	public String getLpModelId() {
		return integration.getModelIdForLpModel();
	}

	/**
	 * @see EclipseIntegration#getModelIdForOutputModel()
	 */
	public String getOutputModelId() {
		return integration.getModelIdForOutputModel();
	}

	/**
	 * @deprecated Use {@link GipsEngine#getEclipseIntegrationConfig()} instead
	 */
	@Deprecated
	public void setRMIPort(int port) {
		integration.getConfig().setServicePort(port);
	}

	/**
	 * @see EclipseIntegration#getConfig()
	 * @see EclipseIntegrationConfig#setTracingEnabled(boolean)
	 */
	public void enableTracing(boolean enableTracing) {
		integration.getConfig().setTracingEnabled(enableTracing);
	}

	/**
	 * @see EclipseIntegration#getConfig()
	 * @see EclipseIntegrationConfig#isTracingEnabled()
	 */
	public boolean isTracingEnabled() {
		return integration.getConfig().isTracingEnabled();
	}

	/**
	 * 
	 * @param gipsEngine
	 */
	public void buildIntermediate2LpTraceGraph(GipsEngine gipsEngine) {
		// try to build a bridge between (M)ILP model and (M)ILP text file

		for (GipsMapper<? extends GipsMapping> mapper : gipsEngine.getMappers().values()) {
			for (GipsMapping gipsMapping : mapper.getMappings().values()) {
				String variableId = MILPTraceKeywords.buildElementId(MILPTraceKeywords.TYPE_VARIABLE,
						gipsMapping.getName());

				mapIntermediate2Lp(mapper.getMapping(), variableId);

				if (gipsMapping instanceof GipsGTMapping<?, ?> gipsGTMapping) {
					mapInput2LpVariable(gipsGTMapping.getMatch().toIMatch(), variableId);
				}

				if (gipsMapping.hasAdditionalVariables()) {
					for (String varNames : gipsMapping.getAdditionalVariableNames()) {
						String addVariableId = MILPTraceKeywords.buildElementId(MILPTraceKeywords.TYPE_VARIABLE,
								varNames);
						mapIntermediate2Lp(mapper.getMapping(), addVariableId);
					}
				}
			}
		}

		for (GipsConstraint<?, ?, ?> constraint : gipsEngine.getConstraints().values()) {
			mapIntermediate2Lp(constraint.getIntermediateConstraint(),
					MILPTraceKeywords.buildElementId(MILPTraceKeywords.TYPE_CONSTRAINT, constraint.getName()));

			for (Constraint milpConstraint : constraint.getConstraints()) {
				for (Term milpTerm : milpConstraint.lhsTerms()) {
					mapIntermediate2Lp(constraint.getIntermediateConstraint().getExpression(), MILPTraceKeywords
							.buildElementId(MILPTraceKeywords.TYPE_CONSTRAINT_VAR, milpTerm.variable().getName()));
				}
			}

			for (Variable<?> variables : constraint.getAdditionalVariables()) {
				mapIntermediate2Lp(constraint.getIntermediateConstraint().getExpression(),
						MILPTraceKeywords.buildElementId(MILPTraceKeywords.TYPE_CONSTRAINT_VAR, variables.getName()));
			}
		}

		for (GipsLinearFunction<?, ?, ?> function : gipsEngine.getLinearFunctions().values()) {
			mapIntermediate2Lp(function.getIntermediateLinearFunction(),
					MILPTraceKeywords.buildElementId(MILPTraceKeywords.TYPE_FUNCTION, function.getName()));
			for (Term term : function.getLinearFunctionFunction().terms()) {
				mapIntermediate2Lp(function.getIntermediateLinearFunction(),
						MILPTraceKeywords.buildElementId(MILPTraceKeywords.TYPE_FUNCTION_VAR, term.variable().getName()));
			}
		}

		if (gipsEngine.getObjective() != null) {
			mapIntermediate2Lp(gipsEngine.getObjective().getIntermediateObjective(),
					MILPTraceKeywords.buildElementId(MILPTraceKeywords.TYPE_OBJECTIVE, ""));
		}
	}

}
