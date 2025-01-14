package org.emoflon.gips.build;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.build.generator.GipsCodeGenerator;
import org.emoflon.gips.build.generator.GipsImportManager;
import org.emoflon.gips.build.transformation.GipsToIntermediate;
import org.emoflon.gips.debugger.api.ITraceContext;
import org.emoflon.gips.debugger.api.ITraceManager;
import org.emoflon.gips.debugger.trace.TraceMap;
import org.emoflon.gips.debugger.trace.TraceModelLink;
import org.emoflon.gips.debugger.trace.resolver.ResolveEcore2Id;
import org.emoflon.gips.gipsl.generator.GipsBuilderExtension;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel;
import org.moflon.core.utilities.LogUtils;

public class GipsProjectBuilder implements GipsBuilderExtension {

	private static Logger logger = Logger.getLogger(GipsProjectBuilder.class);

	@Override
	public void build(IProject project, Resource resource) {
		LogUtils.info(logger, "GipsProjectBuilder: Building project <" + project.getName() + ">");
		// clean old code and create folders
		try {
			LogUtils.info(logger, "GipsProjectBuilder: cleaning old code...");
			GipsBuilderUtils.removeGeneratedCode(project, GipsBuilderUtils.GEN_FOLDER + "/**");
			GipsBuilderUtils.createFolders(project);
		} catch (CoreException e) {
			LogUtils.error(logger, e.toString());
			e.printStackTrace();
			return;
		}

		LogUtils.info(logger, "GipsProjectBuilder: transforming Gipsl models...");
		// create intermediate Gips model and ibex patterns
		EditorGTFile gipsSlangFile = (EditorGTFile) resource.getContents().get(0);
		// use transformer to create intermediate gips model out of the gipsSlang file
		GipsToIntermediate transformer = new GipsToIntermediate(gipsSlangFile);
		GipsIntermediateModel model = null;
		try {
			model = transformer.transform();
		} catch (Exception e) {
			LogUtils.error(logger, e.toString());
			e.printStackTrace();
			return;
		}
		model.setName(resource.getURI().trimFileExtension().lastSegment());
		IBeXModel ibexModel = model.getIbexModel();

		LogUtils.info(logger, "GipsProjectBuilder: building eMoflon-API...");
		GipsAPIData gipsApiData = null;
		// build emoflon API and update Manifest
		if (ibexModel != null) {
			try {
				gipsApiData = GipsBuilderUtils.buildEMoflonAPI(project, ibexModel);
				gipsApiData.project = project;
				GipsBuilderUtils.updateManifest(project, GipsBuilderUtils::processManifestForPackage);
			} catch (CoreException e) {
				LogUtils.error(logger, e.toString());
				e.printStackTrace();
				return;
			}
		}

		// save ibex-patterns and gt-rules for the hipe engine builder
		GipsBuilderUtils.saveResource(EcoreUtil.copy(ibexModel),
				gipsApiData.apiPackageFolder.getLocation() + "/ibex-patterns.xmi");
		GipsBuilderUtils.saveResource(model, gipsApiData.apiPackageFolder.getLocation() + "/gips/gips-model.xmi");
		gipsApiData.intermediateModelURI = URI.createPlatformResourceURI(
				gipsApiData.apiPackageFolder.getProjectRelativePath() + "/gips/gips-model.xmi", true);
		// FIXME; createPlatformResourceURI requires a path of the form
		// "/project-name/path", but getProjectRelativePath() does not include
		// "project-name"
		// TODO: See if this can be easily fixed.

		// build HiPE engine code
		if (ibexModel != null && !ibexModel.getPatternSet().getContextPatterns().isEmpty()) {
			IFolder packagePath = project.getFolder(project.getName().replace(".", "/"));
			GipsBuilderUtils.collectEngineBuilderExtensions()
					.forEach(ext -> ext.run(project, packagePath.getProjectRelativePath(), ibexModel));
		}
		// build Gips API
		LogUtils.info(logger, "GipsProjectBuilder: building Gips-API...");
		// set output folders
		gipsApiData.setGipsApiPackage(
				GipsBuilderUtils.getGeneratedProjectFolder(project, GipsBuilderUtils.GIPS_API_FOLDER));
		gipsApiData.setGipsMappingPackage(
				GipsBuilderUtils.getGeneratedProjectFolder(project, GipsBuilderUtils.MAPPING_FOLDER));
		gipsApiData.setGipsMapperPackage(
				GipsBuilderUtils.getGeneratedProjectFolder(project, GipsBuilderUtils.MAPPER_FOLDER));
		gipsApiData.setGipsConstraintPackage(
				GipsBuilderUtils.getGeneratedProjectFolder(project, GipsBuilderUtils.CONSTRAINT_FOLDER));
		gipsApiData.setGipsObjectivePackage(
				GipsBuilderUtils.getGeneratedProjectFolder(project, GipsBuilderUtils.OBJECTIVE_FOLDER));
		// get package dependencies
		GipsImportManager manager = GipsBuilderUtils
				.createGipsImportManager(GipsBuilderUtils.createEPackageRegistry(model));
		// generate files
		GipsCodeGenerator gipsGen = new GipsCodeGenerator(model, gipsApiData, manager);
		gipsGen.generate();
		LogUtils.info(logger, "GipsProjectBuilder: Done!");

		updateTrace(project, gipsApiData, gipsSlangFile, transformer);

		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			LogUtils.error(logger, e.toString());
			e.printStackTrace();
		}
	}

	// TODO Move this go GipsBuilderUtils
	private void updateTrace(final IProject project, final GipsAPIData gipsApiData, final EditorGTFile gipsSlangFile,
			final GipsToIntermediate transformer) {

		ITraceContext traceContext = ITraceManager.getInstance().getContext(project.getName());

		// TODO: this part needs to go
		// Maybe some app-2-app com
		try {
			var runtimeTrace = project.getLocation().append("traces").append("gips2ilp-trace.xmi");
			traceContext.loadAndUpdateTraceModel(URI.createFileURI(runtimeTrace.toString()));
		} catch (Exception e) {
			LogUtils.error(logger, e.toString());
		}

		try {
			var gipsl2gipsMppings = TraceMap.normalize(transformer.getTrace(), ResolveEcore2Id.INSTANCE,
					ResolveEcore2Id.INSTANCE);

			// file name as model id
			var gipslModelId = gipsSlangFile.eResource().getURI().trimFileExtension().lastSegment();
			var gipsModelId = gipsApiData.intermediateModelURI.trimFileExtension().lastSegment();
			traceContext.updateTraceModel(new TraceModelLink(gipslModelId, gipsModelId, gipsl2gipsMppings));

			// for fun
//			var traceGraph = new TraceGraph();
//			traceGraph.addOrReplaceTraceLink(new TraceModelLink(gipslModelId, gipsModelId, gipsl2gipsMppings));
//			var traceModel = HelperGraph2Ecore.buildModelFromGraph(traceGraph);
//			GipsBuilderUtils.saveResource(traceModel,
//					project.getLocation().append("traces").append("gipsl2gips-trace.xmi").toOSString());

		} catch (Exception e) {
			LogUtils.error(logger, e.toString());
		}
	}
}
