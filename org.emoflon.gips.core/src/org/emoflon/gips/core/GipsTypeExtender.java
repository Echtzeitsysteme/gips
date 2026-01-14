package org.emoflon.gips.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.core.milp.model.BinaryVariable;
import org.emoflon.gips.core.milp.model.IntegerVariable;
import org.emoflon.gips.core.milp.model.RealVariable;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeExtension;
import org.emoflon.ibex.gt.api.GraphTransformationAPI;

public abstract class GipsTypeExtender<CONTEXT extends EObject, EXTENSION extends GipsTypeExtension<CONTEXT>> {

	final private GipsEngine engine;
	final private GraphTransformationAPI eMoflonApi;
	final private TypeIndexer indexer;
	final protected TypeExtension typeExtension;

	final protected Map<CONTEXT, EXTENSION> extendedContexts = Collections.synchronizedMap(new HashMap<>());

	protected long contextIdx = 0;

	public GipsTypeExtender(GipsEngine engine, GraphTransformationAPI eMoflonApi, TypeExtension typeExtension) {
		this.engine = engine;
		this.eMoflonApi = eMoflonApi;
		this.indexer = engine.getIndexer();
		this.typeExtension = Objects.requireNonNull(typeExtension);

	}

	public EClass getExtendedClass() {
		return typeExtension.getExtendedType();
	}

	public Collection<EXTENSION> getExtensions() {
		return extendedContexts.values();
	}

	public Collection<String> getVariableNames() {
		return typeExtension.getAddedVariables().stream().map(e -> e.getName()).toList();
	}

	public String getName() {
		return typeExtension.getName();
	}

	public void clear() {
		contextIdx = 0;
		extendedContexts.clear();
	}

	public void applyAllBoundVariablesToModel() {
		for (var extension : extendedContexts.values())
			extension.applyBoundVariablesToModel();
	}

	@SuppressWarnings("unchecked")
	public void calculateExtensions() {
		for (EObject context : indexer.getObjectsOfType(getExtendedClass())) {
			Map<String, Variable<?>> milpVariables = Collections.synchronizedMap(new HashMap<>());
			for (var variable : typeExtension.getAddedVariables()) {
				Variable<?> milpVar = buildVariable(variable, (CONTEXT) context);
				milpVariables.put(variable.getName(), milpVar);
				engine.addNonMappingVariable((CONTEXT) context, variable, milpVar);
			}
			extendedContexts.put((CONTEXT) context, buildTypeExtension((CONTEXT) context, milpVariables));
			++contextIdx;
		}
	}

	protected abstract EXTENSION buildTypeExtension(CONTEXT context, Map<String, Variable<?>> milpVariables);

	private Variable<?> buildVariable(final org.emoflon.gips.intermediate.GipsIntermediate.Variable variable,
			final CONTEXT context) {

		return switch (variable.getType()) {

		case BINARY -> {
			BinaryVariable var = new BinaryVariable(buildVariableName(variable, context));
			var.setLowerBound((int) variable.getLowerBound());
			var.setUpperBound((int) variable.getUpperBound());
			yield var;
		}

		case INTEGER -> {
			IntegerVariable var = new IntegerVariable(buildVariableName(variable, context));
			var.setLowerBound((int) variable.getLowerBound());
			var.setUpperBound((int) variable.getUpperBound());
			yield var;
		}

		case REAL -> {
			RealVariable var = new RealVariable(buildVariableName(variable, context));
			var.setLowerBound(variable.getLowerBound());
			var.setUpperBound(variable.getUpperBound());
			yield var;
		}

		default -> {
			throw new IllegalArgumentException("Unknown (M)ILP variable type: " + variable.getType());
		}
		};
	}

	protected String buildVariableName(final org.emoflon.gips.intermediate.GipsIntermediate.Variable variable,
			final CONTEXT context) {

		String id = EcoreUtil.getID(context);
		if (id == null)
			id = context.eClass().getName() + "#" + contextIdx;

		return id + "->" + variable.getName();
	}

}
