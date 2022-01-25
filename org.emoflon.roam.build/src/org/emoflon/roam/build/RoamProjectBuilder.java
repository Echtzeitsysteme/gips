package org.emoflon.roam.build;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;
import org.emoflon.roam.build.transformation.RoamToIntermediate;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.roamslang.generator.RoamBuilderExtension;
import org.emoflon.roam.roamslang.roamSLang.EditorGTFile;

public class RoamProjectBuilder implements RoamBuilderExtension {

	@Override
	public void build(IProject project, Resource resource) {
		// clean old code and create folders
		try {
			RoamBuilderUtils.removeGeneratedCode(project, RoamBuilderUtils.GEN_FOLDER+"/**");
			RoamBuilderUtils.createFolders(project);
		} catch (CoreException e) {
			e.printStackTrace();
			return;
		}

		// create intermediate grapel model and ibex patterns
		EditorGTFile roamSlangFile = (EditorGTFile) resource.getContents().get(0);
		// use transformer to create intermediate roam model out of the roamSlang file
		RoamToIntermediate transformer = new RoamToIntermediate();
		RoamIntermediateModel model = transformer.transform(roamSlangFile);
		model.setName(resource.getURI().trimFileExtension().lastSegment());
		IBeXModel ibexModel = model.getIbexModel();

		// build emoflon API and update Manifest
		if (ibexModel != null) {
			try {
				RoamBuilderUtils.buildEMoflonAPI(project, ibexModel);
				RoamBuilderUtils.updateManifest(project, RoamBuilderUtils::processManifestForPackage);
			} catch (CoreException e) {
				e.printStackTrace();
				return;
			}
		}

		// save ibex-patterns and gt-rules for the hipe engine builder
		IFolder apiPackage = project.getFolder("src-gen/" + project.getName().replace(".", "/") + "/api");
		RoamBuilderUtils.saveResource(EcoreUtil.copy(ibexModel), apiPackage.getFullPath() + "/ibex-patterns.xmi");
		RoamBuilderUtils.saveResource(model, resource.getURI().trimFileExtension() + "_intermediate.xmi");

		// build HiPE engine code
		if (ibexModel != null && !ibexModel.getPatternSet().getContextPatterns().isEmpty()) {
			IFolder packagePath = project.getFolder(project.getName().replace(".", "/"));
			RoamBuilderUtils.collectEngineBuilderExtensions()
					.forEach(ext -> ext.run(project, packagePath.getProjectRelativePath(), ibexModel));
		}

		// build Roam API
//		TODO:...
	}
	
	

}
