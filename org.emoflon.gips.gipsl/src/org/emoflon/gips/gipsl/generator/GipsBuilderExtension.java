package org.emoflon.roam.roamslang.generator;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.Resource;

public interface RoamBuilderExtension {
	static final String BUILDER_EXTENSON_ID = "org.emoflon.roam.roamslang.RoamBuilderExtension";

	/**
	 * Builds the project.
	 *
	 * @param project the project to build
	 */
	public void build(IProject project, Resource resource);
}
