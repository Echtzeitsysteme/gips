package org.emoflon.gips.eclipse.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.emoflon.gips.eclipse.api.ITraceManager;
import org.emoflon.gips.eclipse.api.TraceModelNotFoundException;
import org.emoflon.gips.eclipse.api.event.ITraceContextListener;
import org.emoflon.gips.eclipse.api.event.ITraceSelectionListener;
import org.emoflon.gips.eclipse.api.event.ITraceUpdateListener;
import org.emoflon.gips.eclipse.api.event.TraceContextEvent;
import org.emoflon.gips.eclipse.api.event.TraceContextEvent.EventType;
import org.emoflon.gips.eclipse.connector.CplexLpEditorTraceConnectionFactory;
import org.emoflon.gips.eclipse.connector.EditorTraceConnectionFactory;
import org.emoflon.gips.eclipse.connector.GenericXmiEditorTraceConnectionFactory;
import org.emoflon.gips.eclipse.connector.GipslEditorTraceConnectionFactory;
import org.emoflon.gips.eclipse.pref.PluginPreferences;
import org.emoflon.gips.eclipse.utility.HelperEclipse;

public class ContextManager implements ITraceManager {

	private final Object syncLock = new Object();
	private final IResourceChangeListener workspaceResourceListener = this::onWorkspaceResourceChange;
	private final IPropertyChangeListener preferenceListener = this::onPreferenceChange;

	private final ListenerList<ITraceContextListener> contextListener = new ListenerList<>();

	private final Map<String, ProjectContext> contextById = new HashMap<>();

	private EditorTracker tracker;
	private boolean visualisationActive;

	public void initialize() {
		synchronized (syncLock) {
			contextById.clear();

			EditorTraceConnectionFactory connectionFactory = new EditorTraceConnectionFactory();
			connectionFactory.addConnectionFactory(new GipslEditorTraceConnectionFactory());
			connectionFactory.addConnectionFactory(new CplexLpEditorTraceConnectionFactory());
			connectionFactory.addConnectionFactory(new GenericXmiEditorTraceConnectionFactory());

			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			workspace.addResourceChangeListener(workspaceResourceListener);

			IPreferenceStore preferences = PluginPreferences.getPreferenceStore();
			preferences.addPropertyChangeListener(preferenceListener);
			visualisationActive = preferences.getBoolean(PluginPreferences.PREF_TRACE_DISPLAY_ACTIVE);

			// Restore any previously saved context
			if (ProjectContext.isCachingEnabled()) {
				IProject[] allProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
				for (var project : allProjects) {
					if (ProjectContext.hasProjectCache(project) && project.isAccessible())
						getOrCreateContext(project.getName(), true);
				}
			}

			tracker = new EditorTracker(connectionFactory);
			tracker.initialize();
		}
	}

	public void dispose() {
		synchronized (syncLock) {
			IPreferenceStore preferences = PluginPreferences.getPreferenceStore();
			preferences.removePropertyChangeListener(preferenceListener);

			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			workspace.removeResourceChangeListener(workspaceResourceListener);

			tracker.dispose();
			tracker = null;

			if (ProjectContext.isCachingEnabled()) {
				for (var context : contextById.values()) {
					context.writeCache();
//					context.dispose();
				}
			}

			contextById.clear();
		}
	}

	private void onPreferenceChange(PropertyChangeEvent event) {
		if (PluginPreferences.PREF_TRACE_DISPLAY_ACTIVE.equals(event.getProperty()))
			visualisationActive = ((Boolean) event.getNewValue()).booleanValue();
	}

	@Override
	@Deprecated
	public void addTraceContextListener(ITraceContextListener listener) {
		addListener(listener);
	}

	@Override
	@Deprecated
	public void removeTraceContextListener(ITraceContextListener listener) {
		removeListener(listener);
	}

	@Override
	public void addListener(ITraceContextListener listener) {
		contextListener.add(Objects.requireNonNull(listener, "listener"));
	}

	@Override
	public void removeListener(ITraceContextListener listener) {
		contextListener.remove(listener);
	}

	@Override
	public boolean isVisualisationActive() {
		return visualisationActive;
	}
	
	public boolean isGIPSLTracingEnabled() {
		return PluginPreferences.getPreferenceStore().getBoolean(PluginPreferences.PREF_GIPSL_TRACING_ENABLED);
	}

	@Override
	public void removeListener(ITraceSelectionListener listener) {
		synchronized (syncLock) {
			for (var context : contextById.values())
				context.removeListener(listener);
		}
	}

	@Override
	public void removeListener(ITraceUpdateListener listener) {
		synchronized (syncLock) {
			for (var context : contextById.values())
				context.removeListener(listener);
		}
	}

	/**
	 * Allows to add or remove an editor from this manager
	 */
	public IEditorTracker getEditorTracker() {
		return tracker;
	}

	@Override
	public ProjectContext getContext(String contextId) {
		return getOrCreateContext(contextId, true);
	}

	@Override
	public boolean doesContextExist(String contextId) {
		return contextById.containsKey(contextId);
	}

	@Override
	public void selectElementsByTraceModel(String contextId, String modelId, Collection<String> selection)
			throws TraceModelNotFoundException {
		var context = getOrCreateContext(contextId, false);
		if (context != null)
			context.selectElementsByTrace(modelId, selection);
	}

	@Override
	public Set<String> getAvailableContextIds() {
		return new HashSet<>(contextById.keySet());
	}

	private void onWorkspaceResourceChange(IResourceChangeEvent event) {
		if (event == null || event.getDelta() == null)
			return;

		try {
			event.getDelta().accept(delta -> {
				var resource = delta.getResource();
				if (resource.getType() == IResource.PROJECT && //
						delta.getKind() == IResourceDelta.CHANGED && //
						isFlagSet(delta.getFlags(), IResourceDelta.OPEN)) {

					onWorkspaceProjectChange(resource.getProject());
				}
				return true;
			});
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private static boolean isFlagSet(int flags, int isSet) {
		return (flags & isSet) != 0;
	}

	private void onWorkspaceProjectChange(IProject project) {
		if (!project.isAccessible())
			removeContext(project.getName());
	}

	private ProjectContext getOrCreateContext(String contextId, boolean createOnDemand) {
		Objects.requireNonNull(contextId, "contextId");
		ProjectContext context = contextById.get(contextId);

		if (createOnDemand && context == null) {
			synchronized (syncLock) {
				context = contextById.get(contextId);
				if (context != null)
					return context;

				var project = HelperEclipse.tryAndGetProject(contextId);
				if (project == null)
					throw new IllegalArgumentException("Unknown project for context id: " + contextId);

				context = new ProjectContext(this, contextId);
				contextById.put(contextId, context);

				if (ProjectContext.isCachingEnabled())
					context.readCacheIfAvailable();
			}

			var event = new TraceContextEvent(this, EventType.CONTEXT_CREATED, contextId);
			for (var listener : contextListener)
				listener.contextChanged(event);
		}

		return context;
	}

	private void removeContext(String contextId) {
		synchronized (syncLock) {
			var context = contextById.remove(contextId);
			if (context != null) {
				if (ProjectContext.isCachingEnabled())
					context.writeCache();
//				context.dispose();

				var event = new TraceContextEvent(this, EventType.CONTEXT_DELETED, contextId);
				for (var listener : contextListener)
					listener.contextChanged(event);
			}
		}
	}

}
