package org.emoflon.gips.gipsl.generator;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.Resource;

public interface GipsBuilderExtension {
	static final String BUILDER_EXTENSON_ID = "org.emoflon.gips.gipsl.GipsBuilderExtension";

	/**
	 * Builds the project.
	 *
	 * @param project the project to build
	 */
	public void build(IProject project, Resource resource);
}
