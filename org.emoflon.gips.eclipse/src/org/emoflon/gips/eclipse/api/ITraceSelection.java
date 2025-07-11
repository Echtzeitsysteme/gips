package org.emoflon.gips.eclipse.api;

import java.util.Collection;

public interface ITraceSelection {

	/**
	 * Returns the elements that have been selected.
	 * 
	 * @return a collection of IDs, never null
	 */
	Collection<String> getElementIds();

	/**
	 * Returns the model containing the selected elements.
	 * 
	 * @return a model id, never null
	 */
	String getModelId();

	/**
	 * Returns the context containing the model.
	 * 
	 * @return a {@link ITraceContext}, never null
	 */
	ITraceContext getContext();

}
