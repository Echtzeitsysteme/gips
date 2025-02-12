package org.emoflon.gips.debugger.imp;

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
import org.emoflon.gips.debugger.api.IEditorTracker;
import org.emoflon.gips.debugger.api.ITraceManager;
import org.emoflon.gips.debugger.api.TraceModelNotFoundException;
import org.emoflon.gips.debugger.api.event.ITraceManagerListener;
import org.emoflon.gips.debugger.api.event.ITraceSelectionListener;
import org.emoflon.gips.debugger.api.event.ITraceUpdateListener;
import org.emoflon.gips.debugger.api.event.TraceManagerEvent;
import org.emoflon.gips.debugger.api.event.TraceManagerEvent.EventType;
import org.emoflon.gips.debugger.imp.connector.EditorTraceConnectionFactory;
import org.emoflon.gips.debugger.imp.connector.GenericXmiEditorTraceConnectionFactory;
import org.emoflon.gips.debugger.imp.connector.GipslEditorTraceConnectionFactory;
import org.emoflon.gips.debugger.imp.connector.LpEditorTraceConnectionFactory;
import org.emoflon.gips.debugger.pref.PluginPreferences;
import org.emoflon.gips.debugger.utility.HelperEclipse;

public class TraceManager implements ITraceManager {

	private final Object syncLock = new Object();
	private final IResourceChangeListener workspaceResourceListener = this::onWorkspaceResourceChange;
	private final IPropertyChangeListener preferenceListener = this::onPreferenceChange;

	private final ListenerList<ITraceManagerListener> contextListener = new ListenerList<>();

	private final Map<String, ProjectTraceContext> contextById = new HashMap<>();

	private EditorTracker tracker;
	private boolean visualisationActive;

	public void initialize() {
		synchronized (syncLock) {
			contextById.clear();

			EditorTraceConnectionFactory connectionFactory = new EditorTraceConnectionFactory();
			connectionFactory.addConnectionFactory(new GipslEditorTraceConnectionFactory());
			connectionFactory.addConnectionFactory(new LpEditorTraceConnectionFactory());
			connectionFactory.addConnectionFactory(new GenericXmiEditorTraceConnectionFactory());

			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			workspace.addResourceChangeListener(workspaceResourceListener);

			IPreferenceStore preferences = PluginPreferences.getPreferenceStore();
			preferences.addPropertyChangeListener(preferenceListener);
			visualisationActive = preferences.getBoolean(PluginPreferences.PREF_TRACE_DISPLAY_ACTIVE);

			// Restore any previously saved context
			if (ProjectTraceContext.isCacheEnabled(preferences)) {
				IProject[] allProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
				for (var project : allProjects) {
					if (ProjectTraceContext.hasCache(preferences, project))
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

			if (ProjectTraceContext.isCacheEnabled(preferences)) {
				for (var context : contextById.values()) {
					try {
						context.saveCache();
					} catch (CoreException e) {
						e.printStackTrace();
					}
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
	public void addTraceManagerListener(ITraceManagerListener listener) {
		contextListener.add(Objects.requireNonNull(listener, "listener"));
	}

	@Override
	public void removeTraceManagerListener(ITraceManagerListener listener) {
		contextListener.remove(listener);
	}

	@Override
	public boolean isVisualisationActive() {
		return visualisationActive;
	}

	@Override
	public void addListener(String contextId, ITraceSelectionListener listener) {
		var context = getOrCreateContext(contextId, true);
		context.addListener(listener);
	}

	@Override
	public void removeListener(ITraceSelectionListener listener) {
		synchronized (syncLock) {
			for (var context : contextById.values())
				context.removeListener(listener);
		}
	}

	@Override
	public void removeListener(String contextId, ITraceSelectionListener listener) {
		var context = getOrCreateContext(contextId, false);
		if (context != null)
			context.addListener(listener);
	}

	@Override
	public void addListener(String contextId, ITraceUpdateListener listener) {
		var context = getOrCreateContext(contextId, true);
		context.addListener(listener);
	}

	@Override
	public void removeListener(ITraceUpdateListener listener) {
		synchronized (syncLock) {
			for (var context : contextById.values())
				context.removeListener(listener);
		}
	}

	@Override
	public void removeListener(String contextId, ITraceUpdateListener listener) {
		var context = getOrCreateContext(contextId, false);
		if (context != null)
			context.addListener(listener);
	}

	@Override
	public IEditorTracker getEditorTracker() {
		return tracker;
	}

	@Override
	public ProjectTraceContext getContext(String contextId) {
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
		if (!project.exists())
			removeContext(project.getName());
	}

	private ProjectTraceContext getOrCreateContext(String contextId, boolean createOnDemand) {
		Objects.requireNonNull(contextId, "contextId");
		ProjectTraceContext context = contextById.get(contextId);

		if (createOnDemand && context == null) {
			synchronized (syncLock) {
				context = contextById.get(contextId);
				if (context != null)
					return context;

				var project = HelperEclipse.tryAndGetProject(contextId);
				if (project == null)
					throw new IllegalArgumentException("Unknown project for context id: " + contextId);

				context = new ProjectTraceContext(this, contextId);
				contextById.put(contextId, context);

				if (ProjectTraceContext.isCacheEnabled(PluginPreferences.getPreferenceStore()))
					context.loadCacheIfAvailable();
			}

			var event = new TraceManagerEvent(this, EventType.NEW, contextId);
			for (var listener : contextListener)
				listener.contextChanged(event);
		}

		return context;
	}

	private void removeContext(String contextId) {
		synchronized (syncLock) {
			var context = contextById.remove(contextId);
			if (context != null) {
//				context.dispose();

				var event = new TraceManagerEvent(this, EventType.DELETED, contextId);
				for (var listener : contextListener)
					listener.contextChanged(event);
			}
		}
	}

}
