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
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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

	private final ListenerList<ITraceSelectionListener> traceSelectionListener = new ListenerList<>();
	private final ListenerList<ITraceUpdateListener> traceUpdateListener = new ListenerList<>();

	private TraceGraph graph = new TraceGraph();
	private Map<String, Map<String, String>> modelValues = new ConcurrentHashMap<>();
	private boolean anyDataDirty = false;

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
			if (!anyDataDirty)
				return;

			try {
				Path cacheFilePath = getFileCache(true);
				if (cacheFilePath == null)
					return;

				try (var outputStream = Files.newOutputStream(cacheFilePath, StandardOpenOption.CREATE,
						StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
						var objectOut = new ObjectOutputStream(outputStream)) {

					objectOut.writeObject(graph);
					objectOut.writeObject(modelValues);
					anyDataDirty = false;
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

	@SuppressWarnings("unchecked")
	public void readCacheIfAvailable() {
		synchronized (syncLock) {
			try {
				Path cacheFilePath = getFileCache(false);
				if (cacheFilePath == null || !Files.exists(cacheFilePath))
					return;

				try (var inputStream = Files.newInputStream(cacheFilePath, StandardOpenOption.READ);
						var objectIn = new ObjectInputStream(inputStream)) {

					graph = (TraceGraph) objectIn.readObject();
					if (inputStream.available() > 0) {
						var modelValues = (Map<String, Map<String, String>>) objectIn.readObject();
						this.modelValues = new ConcurrentHashMap<>(modelValues);
					}

					anyDataDirty = false;

				} catch (NoSuchFileException e) {
					// ignore
				} catch (ClassNotFoundException | ObjectStreamException e) { // cache is invalid/corrupted
					e.printStackTrace();
					anyDataDirty = true; // trigger rewrite on close
//					deleteCache();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Set<String> getAllModels() {
		Set<String> allModels = new HashSet<>(graph.getAllModelReferenceIds());
		allModels.addAll(modelValues.keySet());
		return allModels;
	}

	@Override
	public void addListener(ITraceSelectionListener listener) {
		Objects.requireNonNull(listener, "listener");
		traceSelectionListener.add(listener);
	}

	@Override
	public void removeListener(ITraceSelectionListener listener) {
		traceSelectionListener.remove(listener);
	}

	@Override
	public void addListener(ITraceUpdateListener listener) {
		Objects.requireNonNull(listener, "listener");
		traceUpdateListener.add(listener);
	}

	@Override
	public void removeListener(ITraceUpdateListener listener) {
		traceUpdateListener.remove(listener);
	}

	@Override
	public boolean hasTraceFor(String modelId) {
		return graph.hasModelReference(modelId);
	}

	@Override
	public boolean hasModelValuesFor(String modelId) {
		return modelValues.containsKey(modelId) && !modelValues.get(modelId).isEmpty();
	}

	@Override
	public void selectElementsByTrace(String modelId, Collection<String> elementIds)
			throws TraceModelNotFoundException {
		Objects.requireNonNull(modelId, "modelId");
		Objects.requireNonNull(elementIds, "elementIds");

		if (!graph.hasModelReference(modelId))
			throw new TraceModelNotFoundException(modelId);

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

	public void deleteModel(String modelId) {
		boolean modelRemoved;
		boolean valuesRemoved;

		Collection<String> updateTraceModels = new HashSet<>();
		updateTraceModels.add(modelId);

		synchronized (syncLock) {
			updateTraceModels.addAll(graph.getSourceModelIds(modelId));
			updateTraceModels.addAll(graph.getTargetModelIds(modelId));
			modelRemoved = graph.removeModelReference(modelId);
			valuesRemoved = modelValues.remove(modelId) != null;
		}

		if (modelRemoved) {
			fireModelUpdateNotification(TraceUpdateEvent.EventType.TRACE, updateTraceModels);
		}

		if (valuesRemoved) {
			fireModelUpdateNotification(TraceUpdateEvent.EventType.VALUES, modelId);
		}
	}

	public void deleteAllModels() {
		Collection<String> removedTraces;
		Collection<String> removedValues;

		synchronized (syncLock) {
			removedTraces = new HashSet<>(graph.getAllModelReferenceIds());
			graph.clear();

			removedValues = new HashSet<>(modelValues.keySet());
			modelValues.clear();
		}

		fireModelUpdateNotification(TraceUpdateEvent.EventType.TRACE, removedTraces);
		fireModelUpdateNotification(TraceUpdateEvent.EventType.VALUES, removedValues);
	}

	public TraceModelLink getModelLink(String srcModel, String dstModel) {
		return graph.getLink(srcModel, dstModel);
	}

	public void deleteModelLink(String srcModel, String dstModel) {
		if (graph.removeTraceLink(srcModel, dstModel))
			fireModelUpdateNotification(TraceUpdateEvent.EventType.TRACE, Set.of(srcModel, dstModel));
	}

	@Override
	public Set<String> getSourceModels(String modelId) {
		return new HashSet<>(graph.getSourceModelIds(modelId));
	}

	@Override
	public Set<String> getTargetModels(String modelId) {
		return new HashSet<>(graph.getTargetModelIds(modelId));
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
		fireModelUpdateNotification(TraceUpdateEvent.EventType.TRACE, updatedModels);
	}

	@Override
	public void loadAndUpdateTraceModel(URI fileURI) {
		var reader = new EcoreReader(fileURI);
		if (reader.doesFileExist()) {
			Root model = reader.loadModel();
			TransformEcore2Graph.addModelToGraph(model, graph);

			var updatedModels = model.getModels().stream().map(ModelReference::getModelId).collect(Collectors.toSet());
			fireModelUpdateNotification(TraceUpdateEvent.EventType.TRACE, updatedModels);
		}
	}

	public void updateModelValues(String modelId, Map<String, String> values) {
		Objects.requireNonNull(modelId, "modelId");

		if (values != null) {
			// remove any empty key or value
			values = values.entrySet().stream() //
					.filter(e -> e.getKey() != null && !e.getKey().isBlank()) //
					.filter(e -> e.getValue() != null && !e.getValue().isBlank()) //
					.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		}

		if (values == null || values.isEmpty()) {
			this.modelValues.remove(modelId);
		} else {
			this.modelValues.put(modelId, Collections.unmodifiableMap(values));
		}

		fireModelUpdateNotification(TraceUpdateEvent.EventType.VALUES, modelId);
	}

	public void deleteModelValues(String modelId) {
		updateModelValues(modelId, null);
	}

	@Override
	public Map<String, String> getModelValues(String modelId) {
		return this.modelValues.getOrDefault(modelId, Collections.emptyMap());
	}

	private void fireModelSelectionNotification(String modelId, Collection<String> elementIds) {
		var event = new TraceSelectionEvent(this, modelId, elementIds);
		for (var listener : traceSelectionListener)
			listener.selectedByModel(event);
	}

	private void fireModelUpdateNotification(TraceUpdateEvent.EventType eventType, String modelId) {
		Objects.requireNonNull(modelId, "modelId");
		fireModelUpdateNotification(eventType, Collections.singleton(modelId));
	}

	private void fireModelUpdateNotification(TraceUpdateEvent.EventType eventType, Collection<String> modelIds) {
		if (modelIds.isEmpty())
			return;

		anyDataDirty = true;
		var event = new TraceUpdateEvent(this, eventType, modelIds);
		for (var listener : this.traceUpdateListener)
			listener.updatedModels(event);
	}

}
