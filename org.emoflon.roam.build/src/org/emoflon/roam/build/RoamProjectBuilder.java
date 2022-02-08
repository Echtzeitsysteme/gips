package org.emoflon.roam.build;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.gt.codegen.EClassifiersManager;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel;
import org.emoflon.roam.build.generator.RoamCodeGenerator;
import org.emoflon.roam.build.transformation.RoamToIntermediate;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.roamslang.generator.RoamBuilderExtension;
import org.emoflon.roam.roamslang.roamSLang.EditorGTFile;
import org.moflon.core.utilities.LogUtils;

public class RoamProjectBuilder implements RoamBuilderExtension {
	
	private static Logger logger = Logger.getLogger(RoamProjectBuilder.class);

	@Override
	public void build(IProject project, Resource resource) {
		LogUtils.info(logger, "RoamProjectBuilder: Building project <"+project.getName()+">");
		// clean old code and create folders
		try {
			LogUtils.info(logger, "RoamProjectBuilder: cleaning old code...");
			RoamBuilderUtils.removeGeneratedCode(project, RoamBuilderUtils.GEN_FOLDER+"/**");
			RoamBuilderUtils.createFolders(project);
		} catch (CoreException e) {
			LogUtils.error(logger, e.getMessage());
			return;
		}
		
		LogUtils.info(logger, "RoamProjectBuilder: transforming RoamSLang models...");
		// create intermediate Roam model and ibex patterns
		EditorGTFile roamSlangFile = (EditorGTFile) resource.getContents().get(0);
		// use transformer to create intermediate roam model out of the roamSlang file
		RoamToIntermediate transformer = new RoamToIntermediate(roamSlangFile);
		RoamIntermediateModel model = null;
		try {
			model = transformer.transform();
		} catch (Exception e) {
			LogUtils.error(logger, e.getMessage());
			return;
		}
		model.setName(resource.getURI().trimFileExtension().lastSegment());
		IBeXModel ibexModel = model.getIbexModel();

		LogUtils.info(logger, "RoamProjectBuilder: building eMoflon-API...");
		RoamAPIData roamApiData = null;
		// build emoflon API and update Manifest
		if (ibexModel != null) {
			try {
				roamApiData = RoamBuilderUtils.buildEMoflonAPI(project, ibexModel);
				roamApiData.project = project;
				RoamBuilderUtils.updateManifest(project, RoamBuilderUtils::processManifestForPackage);
			} catch (CoreException e) {
				LogUtils.error(logger, e.getMessage());
				return;
			}
		}

		// save ibex-patterns and gt-rules for the hipe engine builder
		RoamBuilderUtils.saveResource(EcoreUtil.copy(ibexModel), roamApiData.apiPackageFolder.getLocation() + "/ibex-patterns.xmi");
		RoamBuilderUtils.saveResource(model, roamApiData.apiPackageFolder.getLocation() + "/roam-model.xmi");
		roamApiData.intermediateModelURI = URI.createPlatformResourceURI(roamApiData.apiPackageFolder.getProjectRelativePath() + "/roam-model.xmi", true);

		// build HiPE engine code
		if (ibexModel != null && !ibexModel.getPatternSet().getContextPatterns().isEmpty()) {
			IFolder packagePath = project.getFolder(project.getName().replace(".", "/"));
			RoamBuilderUtils.collectEngineBuilderExtensions()
					.forEach(ext -> ext.run(project, packagePath.getProjectRelativePath(), ibexModel));
		}
		// build Roam API
		LogUtils.info(logger, "RoamProjectBuilder: building Roam-API...");
		// set output folders
		roamApiData.setRoamApiPackage(RoamBuilderUtils.getGeneratedProjectFolder(project, RoamBuilderUtils.ROAM_API_FOLDER));
		roamApiData.setRoamMappingPackage(RoamBuilderUtils.getGeneratedProjectFolder(project, RoamBuilderUtils.MAPPING_FOLDER));
		roamApiData.setRoamMapperPackage(RoamBuilderUtils.getGeneratedProjectFolder(project, RoamBuilderUtils.MAPPER_FOLDER));
		roamApiData.setRoamConstraintPackage(RoamBuilderUtils.getGeneratedProjectFolder(project, RoamBuilderUtils.CONSTRAINT_FOLDER));
		// get package dependencies
		EClassifiersManager manager = RoamBuilderUtils.createEClassifierManager(RoamBuilderUtils.createEPackageRegistry(model));
		// generate files
		RoamCodeGenerator roamGen = new RoamCodeGenerator(model, roamApiData, manager);
		LogUtils.info(logger, "RoamProjectBuilder: Done!");
	}
	
	

}
