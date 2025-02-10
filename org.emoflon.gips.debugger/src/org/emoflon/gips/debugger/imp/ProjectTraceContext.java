package org.emoflon.gips.debugger.imp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.preference.IPreferenceStore;
import org.emoflon.gips.debugger.TracePlugin;
import org.emoflon.gips.debugger.api.IModelLink;
import org.emoflon.gips.debugger.api.ITraceContext;
import org.emoflon.gips.debugger.api.TraceModelNotFoundException;
import org.emoflon.gips.debugger.api.event.ITraceSelectionListener;
import org.emoflon.gips.debugger.api.event.ITraceUpdateListener;
import org.emoflon.gips.debugger.api.event.TraceSelectionEvent;
import org.emoflon.gips.debugger.api.event.TraceUpdateEvent;
import org.emoflon.gips.debugger.pref.PluginPreferences;
import org.emoflon.gips.debugger.trace.EcoreReader;
import org.emoflon.gips.debugger.trace.ModelReference;
import org.emoflon.gips.debugger.trace.PathFinder.SearchDirection;
import org.emoflon.gips.debugger.trace.Root;
import org.emoflon.gips.debugger.trace.TraceGraph;
import org.emoflon.gips.debugger.trace.TraceModelLink;
import org.emoflon.gips.debugger.trace.TransformEcore2Graph;
import org.emoflon.gips.debugger.utility.HelperEclipse;

public final class ProjectTraceContext implements ITraceContext {

	public static final String CACHE_FILE_NAME = "trace_cache.bin";

	public static boolean isCacheEnabled(IPreferenceStore store) {
		return store.getBoolean(PluginPreferences.PREF_TRACE_CACHE_ENABLED);
	}

	private static IFolder getCacheLocation(IPreferenceStore store, IProject project) {
		var config = TracePlugin.getInstance().getPreferenceStore();
		var location = config.getString(PluginPreferences.PREF_TRACE_CACHE_LOCATION);
		IPath relativePath = IPath.fromPortableString(location);
		if (relativePath.isAbsolute())
			throw new IllegalArgumentException("Cache location is not relative");

		return project.getFolder(relativePath);
	}

	private static IFile getCacheFile(IPreferenceStore store, IProject project) {
		IFolder cacheFolder = getCacheLocation(store, project);
		return cacheFolder.getFile(ProjectTraceContext.CACHE_FILE_NAME);
	}

	public static boolean hasCache(IPreferenceStore store, IProject project) {
		IFile cacheFile = getCacheFile(store, project); // .exists() check is not reliable because it just returns a
														// cached value by eclipse

		Path osPath = cacheFile.getLocation().toPath();
		return Files.exists(osPath); // we need to ask the OS directly
	}

	private final Object syncLock = new Object();
	private final TraceManager service;
	private final String contextId;
	private TraceGraph graph = new TraceGraph();

	private final ListenerList<ITraceSelectionListener> selectionListener = new ListenerList<>();
	private final ListenerList<ITraceUpdateListener> updateListener = new ListenerList<>();

	private boolean graphDirty = false;

	public ProjectTraceContext(TraceManager service, String contextId) {
		this.service = Objects.requireNonNull(service, "service");
		this.contextId = Objects.requireNonNull(contextId, "contextId");
	}

//	public void initialize() {
//
//	}
//
//	public void dispose() {
//
//	}

	public void saveCache() throws CoreException {
		synchronized (syncLock) {
			if (!graphDirty)
				return;

			IProject assignedProject = HelperEclipse.tryAndGetProject(contextId);
			if (assignedProject == null || !assignedProject.isAccessible())
				return;

			IFolder cacheFolder = getCacheLocation(PluginPreferences.getPreferenceStore(), assignedProject);
			if (!cacheFolder.exists())
				cacheFolder.create(true, true, null);

			Path cacheFilePath = cacheFolder.getFile(CACHE_FILE_NAME).getLocation().toPath();

			try (var outputStream = Files.newOutputStream(cacheFilePath, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
					var objectOut = new ObjectOutputStream(outputStream)) {

				objectOut.writeObject(graph);
				graphDirty = false;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void loadCacheIfAvailable() {
		synchronized (syncLock) {
			IProject assignedProject = HelperEclipse.tryAndGetProject(contextId);
			if (assignedProject == null || !assignedProject.isAccessible())
				return;

			IFile cacheFile = getCacheLocation(PluginPreferences.getPreferenceStore(), assignedProject)
					.getFile(CACHE_FILE_NAME);
//			if (!cacheFile.exists()) // not reliable
//				return;

			Path cacheFilePath = cacheFile.getLocation().toPath();

			if (!Files.exists(cacheFilePath))
				return;

			try (var inputStream = Files.newInputStream(cacheFilePath, StandardOpenOption.READ);
					var objectIn = new ObjectInputStream(inputStream)) {

				this.graph = (TraceGraph) objectIn.readObject();
				this.graphDirty = false;
			} catch (NoSuchFileException e) {
				// ignore
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Set<String> getAllModels() {
		return new HashSet<>(graph.getAllModelReferenceIds());
	}

	@Override
	public void addListener(ITraceSelectionListener listener) {
		Objects.requireNonNull(listener, "listener");
		selectionListener.add(listener);
	}

	@Override
	public void removeListener(ITraceSelectionListener listener) {
		selectionListener.remove(listener);
	}

	@Override
	public void addListener(ITraceUpdateListener listener) {
		Objects.requireNonNull(listener, "listener");
		updateListener.add(listener);
	}

	@Override
	public void removeListener(ITraceUpdateListener listener) {
		updateListener.remove(listener);
	}

	@Override
	public void selectElementsByTrace(String modelId, Collection<String> elementIds)
			throws TraceModelNotFoundException {
		Objects.requireNonNull(modelId, "modelId");
		Objects.requireNonNull(elementIds, "elementIds");

		if (!graph.hasModelReference(modelId))
			throw new TraceModelNotFoundException(modelId);

		if (service.isVisualisationActive())
			fireModelSelectionNotification(modelId, elementIds);

	}

	@Override
	public String getContextId() {
		return contextId;
	}

	@Override
	public boolean hasTraceFor(String modelId) {
		return graph.hasModelReference(modelId);
	}

	public void deleteModel(String modelId) {
		Collection<String> fromIds = graph.getSourceModelIds(modelId);
		Collection<String> toIds = graph.getTargetModelIds(modelId);
		if (graph.removeModelReference(modelId)) {
			Collection<String> updated = new HashSet<>();
			updated.add(modelId);
			updated.addAll(fromIds);
			updated.addAll(toIds);
			fireModelUpdateNotification(updated);
		}
	}

	public void deleteAllModels() {
		Collection<String> allIds = getAllModels();
		graph.clear();
		fireModelUpdateNotification(allIds);
	}

	public void deleteModelLink(String srcModel, String dstModel) {
		if (graph.removeTraceLink(srcModel, dstModel))
			fireModelUpdateNotification(Set.of(srcModel, dstModel));
	}

	@Override
	public Set<String> getSourceModels(String modelId) {
		return graph.getSourceModelIds(modelId);
	}

	@Override
	public Set<String> getTargetModels(String modelId) {
		return graph.getTargetModelIds(modelId);
	}

	@Override
	public IModelLink getModelChain(String modelId1, String modelId2) {
		var chain = graph.buildTraceChain(modelId1, modelId2, SearchDirection.AllDirections);
		// TODO: some caching.
		// store chain between 1 and 2
		// recalculate iff transformation chain between 1 and 2 changes (updateTrace)
		// luckily, the graph is tiny.

		return new IModelLink() {
			@Override
			public String getStartModelId() {
				return modelId1;
			}

			@Override
			public String getEndModelId() {
				return modelId2;
			}

			@Override
			public boolean isResolved() {
				return chain.isResolved();
			}

			@Override
			public Collection<String> resolveElements(Collection<String> selectedElementsById) {
				return chain.resolveElementFromStartToEnd(selectedElementsById);
			}
		};
	}

	@Override
	public Collection<String> resolveElementsByTrace(String startModelId, String endModelId,
			Collection<String> elements, boolean bidirectional) {
		var link = getModelChain(startModelId, endModelId);
		if (link.isResolved())
			return link.resolveElements(elements);

		return Collections.emptyList();
	}

	@Override
	public void updateTraceModel(TraceModelLink traceLink) {
		graph.addOrReplaceTraceLink(traceLink);

		var updatedModels = Set.of(traceLink.getSourceModel(), traceLink.getTargetModel());
		fireModelUpdateNotification(updatedModels);
	}

	@Override
	public void loadAndUpdateTraceModel(URI fileURI) {
		var reader = new EcoreReader(fileURI);
		if (reader.doesFileExist()) {
			Root model = reader.loadModel();
			TransformEcore2Graph.addModelToGraph(model, graph);

			var updatedModels = model.getModels().stream().map(ModelReference::getModelId).collect(Collectors.toSet());
			fireModelUpdateNotification(updatedModels);
		}
	}

	private void fireModelSelectionNotification(String modelId, Collection<String> elementIds) {
		Objects.requireNonNull(modelId, "modelId");
		Objects.requireNonNull(elementIds, "elementIds");

		// TODO: run (each) in a separate thread with a cancellation token

		var event = new TraceSelectionEvent(this, modelId, elementIds);
		for (var listener : selectionListener)
			listener.selectedByModel(event);
	}

	/**
	 * TODO: Should only be fired if something has really changed
	 * 
	 * @param updatedModels
	 */
	private void fireModelUpdateNotification(Collection<String> updatedModels) {
		Objects.requireNonNull(updatedModels, "updatedModels");
		graphDirty = true;

		var event = new TraceUpdateEvent(this, updatedModels);
		for (var listener : this.updateListener)
			listener.updatedModels(event);
	}

}
