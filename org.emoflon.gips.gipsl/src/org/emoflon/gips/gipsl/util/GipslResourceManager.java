package org.emoflon.gips.gipsl.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.gips.gipsl.gipsl.GipsConstant;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.ibex.common.slimgt.util.SlimGTWorkspaceUtil;
import org.emoflon.ibex.common.slimgt.util.XtextResourceManager;
import org.emoflon.ibex.gt.gtl.gTL.EditorFile;
import org.emoflon.ibex.gt.gtl.gTL.GTLRuleType;
import org.emoflon.ibex.gt.gtl.gTL.SlimRule;
import org.emoflon.ibex.gt.gtl.util.GTLResourceManager;
import org.moflon.core.utilities.LogUtils;

public class GipslResourceManager extends GTLResourceManager {

	public GipslResourceManager() {
		super(new XtextResourceManager());
	}

	public GipslResourceManager(final XtextResourceManager xtextResources) {
		super(xtextResources);
	}

	@Override
	public Collection<EditorFile> loadAllOtherEditorFilesInPackage(final EditorFile ef) {
		Collection<EditorFile> pkgScope = new LinkedList<>();

		IProject currentProject = SlimGTWorkspaceUtil.getCurrentProject(ef.eResource());
		String currentFile = ef.eResource().getURI().toString().replace("platform:/resource/", "")
				.replace(currentProject.getName(), "");
		currentFile = currentProject.getLocation().toPortableString() + currentFile;
		currentFile = currentFile.replace("/", "\\");

		IWorkspace ws = ResourcesPlugin.getWorkspace();
		for (IProject project : ws.getRoot().getProjects()) {
			try {
				if (!(project.hasNature("org.emoflon.ibex.gt.gtl.ui.nature")
						|| project.hasNature("org.emoflon.gips.gipsl.ui.gipsNature")))
					continue;
			} catch (CoreException e) {
				continue;
			}

			Map<String, File> editorFiles = editorFilesInWS.get(project);
			if (editorFiles != null) {
				// This project is already known / watched -> return gtl files
				pkgScope.addAll(editorFiles.values().stream().filter(f -> {
					try {
						String content = Files.readString(f.toPath());
						Matcher m = pkgNamePattern.matcher(content);
						if (m.find() && m.groupCount() == 3) {
							String pkgName = m.group(2);
							return ef.getPackage().getName().equals(pkgName);
						}
						return false;
					} catch (IOException e) {
						return false;
					}
				}).map(f -> {
					URI gtModelUri;
					try {
						gtModelUri = URI.createFileURI(f.getCanonicalPath());
					} catch (IOException e) {
						LogUtils.error(logger, e);
						return Optional.empty();
					}

					URI platformUri = toPlatformURI(project, gtModelUri);

					if (platformUri.toString().equals(ef.eResource().getURI().toString()))
						return Optional.empty();

					Resource resource = xtextResources.loadResource(ef.eResource(), platformUri);
					if (resource == null)
						return Optional.empty();

					if (resource.getContents().isEmpty())
						return Optional.empty();

					EObject gtlModel = resource.getContents().get(0);

					if (gtlModel == null)
						return Optional.empty();

					if (gtlModel instanceof EditorFile otherEditorFile) {
						return Optional.of(otherEditorFile);
					} else {
						return Optional.empty();
					}
				}).filter(opt -> opt.isPresent()).map(opt -> (EditorFile) opt.get())
						.filter(other -> other.getPackage().getName().equals(ef.getPackage().getName()))
						.collect(Collectors.toSet()));

				continue;
			}

			// This is a new or previously unknown IProject -> register file system watcher
			editorFiles = Collections.synchronizedMap(new HashMap<>());
			editorFilesInWS.put(project, editorFiles);
			if (!registerMonitor(project)) {
				continue;
			}

			// Crawl this project initially to get all currently present gtl files
			File projectFile = new File(project.getLocation().toPortableString());
			List<File> gtFiles = new LinkedList<>();
			SlimGTWorkspaceUtil.gatherFilesWithEnding(gtFiles, projectFile, ".gtl", true);
			SlimGTWorkspaceUtil.gatherFilesWithEnding(gtFiles, projectFile, ".gipsl", true);

			for (File gtFile : gtFiles) {
				URI gtModelUri;
				try {
					gtModelUri = URI.createFileURI(gtFile.getCanonicalPath());
				} catch (IOException e) {
					LogUtils.error(logger, e);
					continue;
				}

				String fileString = gtModelUri.toFileString();
				editorFiles.put(gtModelUri.toFileString(), gtFile);

				if (fileString.equals(currentFile))
					continue;

				try {
					String content = Files.readString(gtFile.toPath());
					Matcher m = pkgNamePattern.matcher(content);
					if (m.find() && m.groupCount() == 3) {
						String pkgName = m.group(2);
						if (ef.getPackage().getName() == null || !ef.getPackage().getName().equals(pkgName))
							continue;
					} else {
						continue;
					}
				} catch (IOException e) {
					LogUtils.error(logger, e);
					continue;
				}

				URI platformUri = toPlatformURI(project, gtModelUri);
				Resource resource = xtextResources.loadResource(ef.eResource(), platformUri);
				if (resource == null)
					continue;

				if (resource.getContents().isEmpty())
					continue;

				EObject gtlModel = resource.getContents().get(0);

				if (gtlModel == null)
					continue;

				if (gtlModel instanceof EditorFile otherEditorFile) {
					if (otherEditorFile.getPackage().getName().equals(ef.getPackage().getName())
							&& !fileString.equals(currentFile)) {
						pkgScope.add(otherEditorFile);
					}
				}
			}
		}

		return pkgScope;
	}

	@Override
	public Collection<URI> getAllEditorFileURIsInWorkspaceNotInPackage(final EditorFile requester) {
		Collection<URI> allURIs = new LinkedList<>();

		IProject currentProject = SlimGTWorkspaceUtil.getCurrentProject(requester.eResource());
		String currentFile = requester.eResource().getURI().toString().replace("platform:/resource/", "")
				.replace(currentProject.getName(), "");
		currentFile = currentProject.getLocation().toPortableString() + currentFile;
		currentFile = currentFile.replace("/", "\\");

		IWorkspace ws = ResourcesPlugin.getWorkspace();
		for (IProject project : ws.getRoot().getProjects()) {
			try {
				if (!(project.hasNature("org.emoflon.ibex.gt.gtl.ui.nature")
						|| project.hasNature("org.emoflon.gips.gipsl.ui.gipsNature")))
					continue;
			} catch (CoreException e) {
				continue;
			}

			Map<String, File> editorFiles = editorFilesInWS.get(project);
			if (editorFiles != null) {
				// This project is already known / watched -> return gtl files
				allURIs.addAll(editorFiles.values().stream().filter(f -> {
					try {
						String content = Files.readString(f.toPath());
						Matcher m = pkgNamePattern.matcher(content);
						if (m.find() && m.groupCount() == 3) {
							String pkgName = m.group(2);
							return !requester.getPackage().getName().equals(pkgName);
						}
						return false;
					} catch (IOException e) {
						return false;
					}
				}).map(f -> {
					URI gtModelUri;
					try {
						gtModelUri = URI.createFileURI(f.getCanonicalPath());
					} catch (IOException e) {
						LogUtils.error(logger, e);
						return Optional.empty();
					}

					URI platformUri = toPlatformURI(project, gtModelUri);

					if (platformUri.toString().equals(requester.eResource().getURI().toString()))
						return Optional.empty();

					return Optional.of(platformUri);
				}).filter(opt -> opt.isPresent()).map(opt -> (URI) opt.get()).collect(Collectors.toSet()));

				continue;
			}

			// This is a new or previously unknown IProject -> register file system watcher
			editorFiles = Collections.synchronizedMap(new HashMap<>());
			editorFilesInWS.put(project, editorFiles);
			// This is a new or previously unknown IProject -> register file system watcher
			editorFiles = Collections.synchronizedMap(new HashMap<>());
			editorFilesInWS.put(project, editorFiles);
			if (!registerMonitor(project)) {
				continue;
			}

			// Crawl this project initially to get all currently present gtl files
			File projectFile = new File(project.getLocation().toPortableString());
			List<File> gtFiles = new LinkedList<>();
			SlimGTWorkspaceUtil.gatherFilesWithEnding(gtFiles, projectFile, ".gtl", true);
			SlimGTWorkspaceUtil.gatherFilesWithEnding(gtFiles, projectFile, ".gipsl", true);

			for (File gtFile : gtFiles) {
				URI gtModelUri;
				try {
					gtModelUri = URI.createFileURI(gtFile.getCanonicalPath());
				} catch (IOException e) {
					LogUtils.error(logger, e);
					continue;
				}

				String fileString = gtModelUri.toFileString();

				editorFiles.put(gtModelUri.toFileString(), gtFile);

				if (fileString.equals(currentFile))
					continue;

				try {
					String content = Files.readString(gtFile.toPath());
					Matcher m = pkgNamePattern.matcher(content);
					if (m.find() && m.groupCount() == 3) {
						String pkgName = m.group(2);
						if (requester.getPackage().getName().equals(pkgName))
							continue;
					} else {
						continue;
					}
				} catch (IOException e) {
					LogUtils.error(logger, e);
					continue;
				}

				URI platformUri = toPlatformURI(project, gtModelUri);
				allURIs.add(platformUri);
			}
		}

		return allURIs;
	}

	@Override
	public Collection<SlimRule> getAllRulesInScope(EditorFile ef) {
		Set<SlimRule> ruleSet = new HashSet<>();

		// Add directly imported patterns
		ef.getImportedPatterns().stream().filter(pi -> !pi.isImportingAll())
				.forEach(pi -> ruleSet.add(pi.getPattern()));
		ruleSet.addAll(ef.getRules());

		// Add imported patterns by wildcard
		ef.getImportedPatterns().stream().filter(pi -> pi.isImportingAll()).forEach(pi -> {
			Optional<EditorFile> optEditorFile = loadGTLModelByImport(pi);
			if (optEditorFile.isPresent()) {
				ruleSet.addAll(optEditorFile.get().getRules());
			}
		});

		// Add patterns in package name
		Collection<EditorFile> scope = loadAllOtherEditorFilesInPackage(ef);
		scope.forEach(other -> ruleSet.addAll(other.getRules()));

		return ruleSet;
	}

	@Override
	protected boolean registerMonitor(IProject project) {
		FileAlterationObserver observer = observers.get(project.getLocation().toPortableString());
		if (observer == null) {
			observer = new FileAlterationObserver(project.getLocation().toPortableString());
			observers.put(project.getLocation().toPortableString(), observer);
		}

		FileAlterationMonitor monitor = monitors.get(project.getLocation().toPortableString());
		if (monitor == null) {
			monitor = new FileAlterationMonitor(1000);
			monitors.put(project.getLocation().toPortableString(), monitor);
		} else {
			try {
				monitor.stop();
			} catch (Exception e) {
			}
		}

		FileAlterationListener listener = listeners.get(project.getLocation().toPortableString());
		if (listener != null) {
			monitor.removeObserver(observer);
			observer.removeListener(listener);
			listeners.remove(project.getLocation().toPortableString());
		}

		listener = new FileAlterationListenerAdaptor() {
			@Override
			public void onFileCreate(File file) {
				if (file.isFile() && (file.getName().endsWith(".gtl") || file.getName().endsWith(".gipsl"))) {
					Map<String, File> editorFiles = editorFilesInWS.get(project);
					try {
						editorFiles.put(file.getCanonicalPath(), file);
					} catch (IOException e) {
						LogUtils.error(logger, e);
						return;
					}
				}
			}

			@Override
			public void onFileDelete(File file) {
				if (file.isFile() && (file.getName().endsWith(".gtl") || file.getName().endsWith(".gipsl"))) {
					Map<String, File> editorFiles = editorFilesInWS.get(project);
					try {
						editorFiles.remove(file.getCanonicalPath());
					} catch (IOException e) {
						LogUtils.error(logger, e);
						return;
					}
				}
			}

			@Override
			public void onFileChange(File file) {
				// do nothing...
			}
		};
		listeners.put(project.getLocation().toPortableString(), listener);

		observer.addListener(listener);
		monitor.addObserver(observer);
		try {
			monitor.start();
		} catch (Exception e) {
			LogUtils.error(logger, e);
			return false;
		}

		return true;
	}

	public Collection<GipsConstant> getAllConstantsInScope(final org.emoflon.gips.gipsl.gipsl.EditorFile file) {
		Set<GipsConstant> constantSet = new HashSet<>();
		constantSet.addAll(file.getConstants());

		// Add patterns in package name
		loadAllOtherEditorFilesInPackage(file).stream()
				.filter(f -> (f instanceof org.emoflon.gips.gipsl.gipsl.EditorFile))
				.map(f -> (org.emoflon.gips.gipsl.gipsl.EditorFile) f)
				.forEach(f -> constantSet.addAll(f.getConstants()));

		return constantSet;
	}

	public Collection<GipsMapping> getAllMappingsInScope(final org.emoflon.gips.gipsl.gipsl.EditorFile file) {
		Set<GipsMapping> mappingSet = new HashSet<>();
		mappingSet.addAll(file.getMappings());

		// Add patterns in package name
		loadAllOtherEditorFilesInPackage(file).stream()
				.filter(f -> (f instanceof org.emoflon.gips.gipsl.gipsl.EditorFile))
				.map(f -> (org.emoflon.gips.gipsl.gipsl.EditorFile) f).forEach(f -> mappingSet.addAll(f.getMappings()));

		return mappingSet;
	}

	public Collection<GipsLinearFunction> getAllFunctionsInScope(final org.emoflon.gips.gipsl.gipsl.EditorFile file) {
		Set<GipsLinearFunction> functionSet = new HashSet<>();
		functionSet.addAll(file.getFunctions());

		// Add patterns in package name
		loadAllOtherEditorFilesInPackage(file).stream()
				.filter(f -> (f instanceof org.emoflon.gips.gipsl.gipsl.EditorFile))
				.map(f -> (org.emoflon.gips.gipsl.gipsl.EditorFile) f)
				.forEach(f -> functionSet.addAll(f.getFunctions()));

		return functionSet;
	}

	public Collection<SlimRule> getAllTrueRulesInScope(final org.emoflon.gips.gipsl.gipsl.EditorFile file) {
		return getAllRulesInScope(file).stream().filter(r -> r.getType() == GTLRuleType.RULE)
				.collect(Collectors.toList());
	}

	public Collection<SlimRule> getAllTruePatternsInScope(final org.emoflon.gips.gipsl.gipsl.EditorFile file) {
		return getAllRulesInScope(file).stream().filter(r -> r.getType() == GTLRuleType.PATTERN)
				.collect(Collectors.toList());
	}
}