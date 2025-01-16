package org.emoflon.gips.debugger.imp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.emoflon.gips.debugger.api.ITraceContext;
import org.emoflon.gips.debugger.api.ITraceManager;
import org.emoflon.gips.debugger.api.IEditorTracker;
import org.emoflon.gips.debugger.api.ITraceSelectionListener;
import org.emoflon.gips.debugger.api.ITraceUpdateListener;
import org.emoflon.gips.debugger.api.TraceModelNotFoundException;
import org.emoflon.gips.debugger.imp.connector.EditorTraceConnectionFactory;
import org.emoflon.gips.debugger.imp.connector.GenericXmiEditorTraceConnectionFactory;
import org.emoflon.gips.debugger.imp.connector.GipslEditorTraceConnectionFactory;
import org.emoflon.gips.debugger.imp.connector.LpEditorTraceConnectionFactory;

public final class TraceManager implements ITraceManager {

	private final Object syncLock = new Object();
	private final IResourceChangeListener workspaceResourceListener = this::onWorkspaceResourceChange;

	private final Map<String, ProjectTraceContext> contextById = new HashMap<>();

	private EditorTracker tracker;
	private boolean visualisationActive;

	public void initialize() {
		contextById.clear();

		var connectionFactory = new EditorTraceConnectionFactory();
		connectionFactory.addConnectionFactory(new GipslEditorTraceConnectionFactory());
		connectionFactory.addConnectionFactory(new LpEditorTraceConnectionFactory());
		connectionFactory.addConnectionFactory(new GenericXmiEditorTraceConnectionFactory());

		tracker = new EditorTracker(connectionFactory);
		tracker.initialize();

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.addResourceChangeListener(workspaceResourceListener);
	}

	public void dispose() {
		contextById.clear();

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.removeResourceChangeListener(workspaceResourceListener);

		tracker.dispose();
		tracker = null;
	}

	@Override
	public boolean isVisualisationActive() {
		return visualisationActive;
	}

	@Override
	public void setVisualisation(boolean active) {
		visualisationActive = active;
	}

	@Override
	public boolean toggleVisualisation() {
		visualisationActive = !visualisationActive;
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
			for (var context : contextById.values()) {
				context.removeListener(listener);
			}
		}
	}

	@Override
	public void removeListener(String contextId, ITraceSelectionListener listener) {
		var context = getOrCreateContext(contextId, false);
		if (context != null) {
			context.addListener(listener);
		}
	}

	@Override
	public void addListener(String contextId, ITraceUpdateListener listener) {
		var context = getOrCreateContext(contextId, true);
		context.addListener(listener);
	}

	@Override
	public void removeListener(ITraceUpdateListener listener) {
		synchronized (syncLock) {
			for (var context : contextById.values()) {
				context.removeListener(listener);
			}
		}
	}

	@Override
	public void removeListener(String contextId, ITraceUpdateListener listener) {
		var context = getOrCreateContext(contextId, false);
		if (context != null) {
			context.addListener(listener);
		}
	}

	@Override
	public IEditorTracker getEditorTracker() {
		return tracker;
	}

	@Override
	public ITraceContext getContext(String contextId) {
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
		if (context != null) {
			context.selectElementsByTrace(modelId, selection);
		}
	}

	private void onWorkspaceResourceChange(IResourceChangeEvent event) {
		if (event == null || event.getDelta() == null) {
			return;
		}

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
		if (!project.exists()) {
			removeContext(project.getName());
		}
	}

	private ProjectTraceContext getOrCreateContext(String contextId, boolean createOnDemand) {
		Objects.requireNonNull(contextId, "contextId");

		// TODO: check against workspace projects and only allow context creation for
		// existing projects

		synchronized (syncLock) {
			var context = contextById.get(contextId);
			if (context == null && createOnDemand) {
				context = new ProjectTraceContext(this, contextId);
				contextById.put(contextId, context);
			}
			return context;
		}
	}

	private void removeContext(String contextId) {
		synchronized (syncLock) {
			contextById.remove(contextId);
		}
	}

}
