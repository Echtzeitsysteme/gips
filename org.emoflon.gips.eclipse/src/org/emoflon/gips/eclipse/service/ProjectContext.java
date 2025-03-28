package org.emoflon.gips.eclipse.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.emf.common.util.URI;
import org.emoflon.gips.eclipse.api.IModelLink;
import org.emoflon.gips.eclipse.api.ITraceContext;
import org.emoflon.gips.eclipse.api.ITraceManager;
import org.emoflon.gips.eclipse.api.TraceModelNotFoundException;
import org.emoflon.gips.eclipse.api.event.ITraceSelectionListener;
import org.emoflon.gips.eclipse.api.event.ITraceUpdateListener;
import org.emoflon.gips.eclipse.api.event.TraceSelectionEvent;
import org.emoflon.gips.eclipse.api.event.TraceUpdateEvent;
import org.emoflon.gips.eclipse.pref.PluginPreferences;
import org.emoflon.gips.eclipse.trace.EcoreReader;
import org.emoflon.gips.eclipse.trace.ModelReference;
import org.emoflon.gips.eclipse.trace.PathFinder.SearchDirection;
import org.emoflon.gips.eclipse.trace.Root;
import org.emoflon.gips.eclipse.trace.TraceGraph;
import org.emoflon.gips.eclipse.trace.TraceModelLink;
import org.emoflon.gips.eclipse.trace.TransformEcore2Graph;
import org.emoflon.gips.eclipse.utility.HelperEclipse;

public final class ProjectContext implements ITraceContext {

	private static final String CACHE_FILE_NAME = "trace_cache.bin";

	public static boolean isCachingEnabled() {
		return PluginPreferences.getPreferenceStore().getBoolean(PluginPreferences.PREF_TRACE_CACHE_ENABLED);
	}

	private static Path getProjectCache(IProject project) {
		String location = PluginPreferences.getPreferenceStore().getString(PluginPreferences.PREF_TRACE_CACHE_LOCATION);
		IPath relativePath = IPath.fromPortableString(location);
		if (relativePath.isAbsolute())
			throw new IllegalArgumentException("Cache location is not relative");

		IFolder folder = project.getFolder(relativePath);
		return folder.getFile(CACHE_FILE_NAME).getLocation().toPath();
	}

	public static boolean hasProjectCache(IProject project) {
		return Files.exists(getProjectCache(project)); // we need to ask the OS directly
	}

	private final Object syncLock = new Object();
	private final ContextManager manager;
	private final String contextId;
	private TraceGraph graph = new TraceGraph();

	private final ListenerList<ITraceSelectionListener> selectionListener = new ListenerList<>();
	private final ListenerList<ITraceUpdateListener> updateListener = new ListenerList<>();

	private boolean graphDirty = false;

	public ProjectContext(ContextManager manager, String contextId) {
		this.manager = Objects.requireNonNull(manager, "manager");
		this.contextId = Objects.requireNonNull(contextId, "contextId");
	}

	/**
	 * Returns the {@link IProject} associated with this context. The name of the
	 * project is the same as the {@link #getContextId() id} of this context. The
	 * project may be null or inaccessible.
	 * 
	 * @return the project or null
	 */
	public IProject getAssociatedProject() {
		return HelperEclipse.tryAndGetProject(contextId);
	}

//	public void initialize() {
//
//	}
//
//	public void dispose() {
//
//	}

	/**
	 * @param createParentFolders
	 * @return a path to the cache file
	 * @throws IOException if an I/O error occurs
	 */
	private Path getFileCache(boolean createParentFolders) throws IOException {
		IProject project = getAssociatedProject();
		if (project == null || !project.isAccessible())
			return null;

		Path cacheFilePath = getProjectCache(project);
		if (createParentFolders && !Files.exists(cacheFilePath.getParent()))
			Files.createDirectories(cacheFilePath.getParent());

		return cacheFilePath;
	}

	public void writeCache() {
		synchronized (syncLock) {
			if (!graphDirty)
				return;

			try {
				Path cacheFilePath = getFileCache(true);
				if (cacheFilePath == null)
					return;

				try (var outputStream = Files.newOutputStream(cacheFilePath, StandardOpenOption.CREATE,
						StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
						var objectOut = new ObjectOutputStream(outputStream)) {

					objectOut.writeObject(graph);
					graphDirty = false;
				}
			} catch (IOException e) {
				e.printStackTrace(); // log to console, can't do anything else
			}
		}
	}

	public void deleteCache() {
		try {
			Path cacheFilePath = getFileCache(false);
			if (Files.exists(cacheFilePath))
				Files.delete(cacheFilePath);
		} catch (NoSuchFileException e) {
			// ignore
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readCacheIfAvailable() {
		synchronized (syncLock) {
			try {
				Path cacheFilePath = getFileCache(false);
				if (cacheFilePath == null || !Files.exists(cacheFilePath))
					return;

				try (var inputStream = Files.newInputStream(cacheFilePath, StandardOpenOption.READ);
						var objectIn = new ObjectInputStream(inputStream)) {

					graph = (TraceGraph) objectIn.readObject();
					graphDirty = false;

				} catch (NoSuchFileException e) {
					// ignore
				} catch (ClassNotFoundException | ObjectStreamException e) { // cache is invalid/corrupted
					e.printStackTrace();
					graphDirty = true; // trigger rewrite on close
//					deleteCache();
				}

			} catch (IOException e) {
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

		if (manager.isVisualisationActive())
			fireModelSelectionNotification(modelId, elementIds);

	}

	@Override
	public ITraceManager getTraceManager() {
		return getContextManager();
	}

	public ContextManager getContextManager() {
		return this.manager;
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

	public TraceModelLink getModelLink(String srcModel, String dstModel) {
		return graph.getLink(srcModel, dstModel);
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
	public IModelLink getModelChain(String startModelId, String endModelId) {
		var chain = graph.buildTraceChain(startModelId, endModelId, SearchDirection.AllDirections);
		// TODO: some caching.
		// store chain between 1 and 2
		// recalculate iff transformation chain between 1 and 2 changes (updateTrace)
		// luckily, the graph is tiny.

		return new IModelLink() {
			@Override
			public String getStartModelId() {
				return startModelId;
			}

			@Override
			public String getEndModelId() {
				return endModelId;
			}

			@Override
			public boolean isResolved() {
				return chain.isResolved();
			}

			@Override
			public Collection<String> resolveElementsFromSrcToDst(Collection<String> selectedElementsById) {
				return chain.resolveElementFromStartToEnd(selectedElementsById);
			}
		};
	}

	@Override
	public Collection<String> resolveElementsByTrace(String startModelId, String endModelId,
			Collection<String> elements, boolean bidirectional) {
		var link = getModelChain(startModelId, endModelId);
		if (link.isResolved())
			return link.resolveElementsFromSrcToDst(elements);

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

	public void updateMILPVariableValues(String lpModelId, Map<String, Number> values) {
		// TODO Auto-generated method stub

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
