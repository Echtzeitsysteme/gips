package org.emoflon.roam.build;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.roam.roamslang.generator.RoamBuilderExtension;

public class RoamProjectBuilder implements RoamBuilderExtension {

	@Override
	public void build(IProject project, Resource resource) {
		// TODO Auto-generated method stub
		System.out.println("RoamProjectBuilder reporting in!");
	}

}
