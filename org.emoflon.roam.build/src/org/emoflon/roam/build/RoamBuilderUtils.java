package org.emoflon.roam.build;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.jar.Manifest;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.gt.codegen.EClassifiersManager;
import org.emoflon.ibex.gt.codegen.GTEngineBuilderExtension;
import org.emoflon.ibex.gt.codegen.GTEngineExtension;
import org.emoflon.ibex.gt.codegen.JavaFileGenerator;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint;
import org.gervarro.eclipse.workspace.util.AntPatternCondition;
import org.moflon.core.build.CleanVisitor;
import org.moflon.core.plugins.manifest.ManifestFileUpdater;
import org.moflon.core.utilities.ExtensionsUtil;
import org.moflon.core.utilities.WorkspaceHelper;

public final class RoamBuilderUtils {
	final public static String GEN_FOLDER = "src-gen";
	final public static String API_FOLDER = "api";
	final public static String MATCHES_FOLDER = API_FOLDER + "/matches";
	final public static String RULES_FOLDER = API_FOLDER + "/rules";
	final public static String ROAM_API_FOLDER = API_FOLDER + "/roam";
	final public static String MAPPING_FOLDER = ROAM_API_FOLDER + "/mapping";
	final public static String MAPPER_FOLDER = ROAM_API_FOLDER + "/mapper";
	final public static String CONSTRAINT_FOLDER = ROAM_API_FOLDER + "/constraint";

	/**
	 * Removes generated code in the project matching the deletion pattern
	 * 
	 * @param project to remove the generated code from
	 * @param pattern to match for deletion
	 * @throws CoreException if the request for deletion of files matching the
	 *                       pattern via visitor fails
	 */
	public static void removeGeneratedCode(final IProject project, final String pattern) throws CoreException {
		final CleanVisitor cleanVisitor = new CleanVisitor(project, //
				new AntPatternCondition(new String[] { pattern }) //
		);
		project.accept(cleanVisitor, IResource.DEPTH_INFINITE, IResource.NONE);
	}

	/**
	 * Creates empty folders in the project needed for code generation
	 * 
	 * @param project to create folders needed for code generation
	 * @throws CoreException if the folder generation fails
	 */
	public static void createFolders(IProject project) throws CoreException {
		WorkspaceHelper.createFolderIfNotExists(WorkspaceHelper.getSourceFolder(project), new NullProgressMonitor());
		WorkspaceHelper.createFolderIfNotExists(WorkspaceHelper.getBinFolder(project), new NullProgressMonitor());
		WorkspaceHelper.createFolderIfNotExists(project.getFolder(GEN_FOLDER), new NullProgressMonitor());
		WorkspaceHelper.createFolderIfNotExists(
				getGeneratedProjectFolder(project, API_FOLDER),
				new NullProgressMonitor());
		WorkspaceHelper.createFolderIfNotExists(
				getGeneratedProjectFolder(project, MATCHES_FOLDER),
				new NullProgressMonitor());
		WorkspaceHelper.createFolderIfNotExists(
				getGeneratedProjectFolder(project, RULES_FOLDER),
				new NullProgressMonitor());
		WorkspaceHelper.createFolderIfNotExists(
				getGeneratedProjectFolder(project, ROAM_API_FOLDER),
				new NullProgressMonitor());
		WorkspaceHelper.createFolderIfNotExists(
				getGeneratedProjectFolder(project, MAPPING_FOLDER),
				new NullProgressMonitor());
		WorkspaceHelper.createFolderIfNotExists(
				getGeneratedProjectFolder(project, MAPPER_FOLDER),
				new NullProgressMonitor());
		WorkspaceHelper.createFolderIfNotExists(
				getGeneratedProjectFolder(project, CONSTRAINT_FOLDER),
				new NullProgressMonitor());
	}
	
	public static IFolder getGeneratedProjectFolder(IProject project, String projectFolder) {
		return project.getFolder(GEN_FOLDER + "/" + project.getName().replace(".", "/") + "/" + projectFolder);
	}

	/**
	 * Updates the manifest in the given project with the update function
	 * 
	 * @param project        in which the manifest should be updated
	 * @param updateFunction to determine how the manifest should be updated
	 * @throws CoreException if manifest update fails
	 */
	public static void updateManifest(final IProject project,
			final BiFunction<IProject, Manifest, Boolean> updateFunction) throws CoreException {
		new ManifestFileUpdater().processManifest(project, manifest -> updateFunction.apply(project, manifest));
	}

	/**
	 * Adds/updates Manifest for a project
	 * 
	 * @param project  in which the manifest should be added and/or updated to fit
	 *                 the dependency requirements
	 * @param manifest to change to in the project
	 * @return true if the manifest has updated OR changed dependencies
	 */
	public static boolean processManifestForPackage(IProject project, Manifest manifest) {
		List<String> dependencies = new ArrayList<String>();
//		TODO: Add dependencies on Roam runtime libraries!
		dependencies.addAll(Arrays.asList("org.emoflon.ibex.common", "org.emoflon.ibex.gt", "org.emoflon.roam.core"));
		collectEngineExtensions().forEach(engine -> dependencies.addAll(engine.getDependencies()));
		boolean changedBasics = ManifestFileUpdater.setBasicProperties(manifest, project.getName());
		boolean updatedDependencies = ManifestFileUpdater.updateDependencies(manifest, dependencies);
		return changedBasics || updatedDependencies;
	}

	/**
	 * Saves a given EObject as resource XMI to a given path
	 * 
	 * @param object to be saved
	 * @param path   where the XMI should be saved
	 */
	public static URI saveResource(EObject object, String path) {
		// save for debugging
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		URI pathURI = URI.createURI(path);
		Resource output = rs.createResource(pathURI);
		output.getContents().add(object);
		// create save options to save XMI
		Map<Object, Object> saveOptions = ((XMIResource) output).getDefaultSaveOptions();
		saveOptions.put(XMIResource.OPTION_ENCODING, "UTF-8");
		saveOptions.put(XMIResource.OPTION_USE_XMI_TYPE, Boolean.TRUE);
		saveOptions.put(XMIResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.TRUE);
		saveOptions.put(XMIResource.OPTION_SCHEMA_LOCATION_IMPLEMENTATION, Boolean.TRUE);
		try {
			((XMIResource) output).save(saveOptions);
		} catch (IOException e) {
			e.printStackTrace();
		}
		output.unload();
		return pathURI;
	}

	/**
	 * Builds the eMoflon API for a given IBeX model and
	 * 
	 * @param project   to build the API for
	 * @param ibexModel to build the API for
	 * @throws CoreException if hte API folder does not exist or API generation
	 *                       fails
	 */
	public static RoamAPIData buildEMoflonAPI(IProject project, IBeXModel ibexModel) throws CoreException {
		final Registry packageRegistry = createEPackageRegistry(ibexModel);
		IFolder apiPackage = project
				.getFolder(GEN_FOLDER + "/" + project.getName().replace(".", "/") + "/" + API_FOLDER);
		RoamAPIData apiData = new RoamAPIData(apiPackage);
		ensureFolderExists(apiPackage);
		generateEMoflonAPI(project, apiData, ibexModel, packageRegistry);
		return apiData;
	}
	
	/**
	 * Create a package registry of all used packages in the model.
	 * 
	 * @param model       which includes the ePackages, that should be
	 *                    registered
	 */
	public static Registry createEPackageRegistry(final RoamIntermediateModel model) {
		Registry packageRegistry = new EPackageRegistryImpl();
		findAllEPackages(model.getIbexModel(), packageRegistry);
		model.getConstraints().stream()
			.filter(constr -> constr instanceof TypeConstraint)
			.map(constr -> (TypeConstraint) constr)
			.forEach(constr -> {
				EPackage foreign = constr.getModelType().getType().getEPackage();
				if(!packageRegistry.containsKey(foreign.getNsURI())) {
					packageRegistry.put(foreign.getNsURI(), foreign);
				}
			});
		return packageRegistry;
	}
	
	/**
	 * Create a package registry of all used packages in the model.
	 * 
	 * @param ibexModel       which includes the ePackages, that should be
	 *                        registered
	 */
	public static Registry createEPackageRegistry(final IBeXModel ibexModel) {
		Registry packageRegistry = new EPackageRegistryImpl();
		findAllEPackages(ibexModel, packageRegistry);
		return packageRegistry;
	}

	/**
	 * Searches for all ePackages in an IBeX model and add them to the package
	 * registry
	 * 
	 * @param ibexModel       which includes the ePackages, that should be
	 *                        registered
	 * @param packageRegistry to add ePackage, from model, if not already registered
	 */
	public static void findAllEPackages(final IBeXModel ibexModel, final Registry packageRegistry) {
		ibexModel.getNodeSet().getNodes().forEach(node -> {
			EPackage foreign = node.getType().getEPackage();
			if (!packageRegistry.containsKey(foreign.getNsURI())) {
				packageRegistry.put(foreign.getNsURI(), foreign);
			}
		});
	}

	/**
	 * Checks if the given folder exits, if not the folder is created
	 * 
	 * @param folder to check if present and if not to be created
	 * @return the folder, which needs to be present
	 * @throws CoreException if the folder is not present and cannot be created
	 */
	public static IFolder ensureFolderExists(final IFolder folder) throws CoreException {
		if (!folder.exists()) {
			folder.create(true, true, null);
		}
		return folder;
	}

	/**
	 * Generates the API for the given parameters
	 * 
	 * @param project         in/for which the API should be generated
	 * @param apiPackage      the API package folder
	 * @param ibexModel       the model, which the API needs to include
	 * @param packageRegistry including the ePackages for API generation
	 * @throws CoreException if API generation fails due to missing folders/ invalid
	 *                       generation paths
	 */
	public static void generateEMoflonAPI(final IProject project, final RoamAPIData apiData, final IBeXModel ibexModel,
			final Registry packageRegistry) throws CoreException {
		JavaFileGenerator generator = new JavaFileGenerator(getClassNamePrefix(project), project.getName(),
				createEClassifierManager(packageRegistry));
		// ensure generation folders exist
		IFolder matchesPackage = ensureFolderExists(apiData.apiPackageFolder.getFolder("matches"));
		IFolder rulesPackage = ensureFolderExists(apiData.apiPackageFolder.getFolder("rules"));
		IFolder probabilitiesPackage = ensureFolderExists(apiData.apiPackageFolder.getFolder("probabilities"));
		// Store in API-Data
		apiData.setMatchesPkg(matchesPackage);
		apiData.setRulesPkg(rulesPackage);
		apiData.setProbabilitiesPkg(probabilitiesPackage);
		
		// generate code for rules
		Set<IBeXPattern> ruleContextPatterns = new HashSet<>();
		ibexModel.getRuleSet().getRules().forEach(ibexRule -> {
			generator.generateMatchClass(matchesPackage, ibexRule);
			generator.generateRuleClass(rulesPackage, ibexRule);
			generator.generateProbabilityClass(probabilitiesPackage, ibexRule);
			ruleContextPatterns.add(ibexRule.getLhs());
		});

		// generate code for patterns
		ibexModel.getPatternSet().getContextPatterns().stream()
				.filter(pattern -> !ruleContextPatterns.contains(pattern))
				.filter(pattern -> !pattern.getName().contains("CONDITION")).forEach(pattern -> {
					generator.generateMatchClass(matchesPackage, pattern);
					generator.generatePatternClass(rulesPackage, pattern);
				});

		// generate the Java IBEX APP and API for the model
		generator.generateAPIClass(apiData.apiPackageFolder, ibexModel, String.format("%s/%s/%s/api/ibex-patterns.xmi",
				project.getName(), "src-gen", project.getName().replace(".", "/")));
		generator.generateAppClass(apiData.apiPackageFolder);
		collectEngineExtensions().forEach(e -> generator.generateAppClassForEngine(apiData.apiPackageFolder, e));
		
		apiData.apiClassNamePrefix = getClassNamePrefix(project);
		apiData.apiClass = getClassNamePrefix(project)+"API";
		apiData.appClass = getClassNamePrefix(project)+"App";
		collectEngineExtensions().forEach(engineExt -> {
			apiData.engineAppClasses.put(engineExt.getEngineName(), getClassNamePrefix(project)+engineExt.getEngineName()+"App");
		});
	}

	/**
	 * Gets the class name prefix for a given project
	 * 
	 * @param project of which the class name prefix should be determined
	 * @return the class name prefix for the project
	 */
	public static String getClassNamePrefix(final IProject project) {
		URI projectNameAsURI = URI.createFileURI(project.getName().replace(".", "/"));
		String prefix = projectNameAsURI.lastSegment();
		return Character.toUpperCase(prefix.charAt(0)) + prefix.substring(1);
	}

	/**
	 * Creates a classifier manager for the packages in the package registry
	 * 
	 * @param packageRegistry the package registry, which includes the EPackages,
	 *                        that should be present in the EClassifier manager
	 * @return an EClassifier manager for the EPackages in the package registry
	 */
	public static EClassifiersManager createEClassifierManager(final Registry packageRegistry) {
		EClassifiersManager eClassifiersManager = new EClassifiersManager(new HashMap<>());
		packageRegistry.values().stream().filter(x -> (x instanceof EPackage)).forEach(obj -> {
			EPackage epackage = (EPackage) obj;
			eClassifiersManager.loadMetaModelClasses(epackage.eResource());
		});
		return eClassifiersManager;
	}

	/**
	 * Collects all engine extensions with the GTEngineExtension builder extension
	 * ID
	 * 
	 * @return all engine extension with the GTEngine extension ID
	 */
	public static Collection<GTEngineExtension> collectEngineExtensions() {
		return ExtensionsUtil.collectExtensions(GTEngineExtension.BUILDER_EXTENSON_ID, "class",
				GTEngineExtension.class);
	}

	/**
	 * @return all engine builder extensions with the GTEngineBuilder extension ID
	 */
	public static Collection<GTEngineBuilderExtension> collectEngineBuilderExtensions() {
		return ExtensionsUtil.collectExtensions(GTEngineBuilderExtension.BUILDER_EXTENSON_ID, "class",
				GTEngineBuilderExtension.class);
	}
}
