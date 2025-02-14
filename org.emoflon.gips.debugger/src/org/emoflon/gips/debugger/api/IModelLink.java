package org.emoflon.gips.debugger.api;

import java.util.Collection;

public interface IModelLink {

	String getStartModelId();

	String getEndModelId();

	boolean isResolved();

	Collection<String> resolveElementsFromSrcToDst(Collection<String> elementIds);

}
