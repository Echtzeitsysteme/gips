package org.emoflon.gips.eclipse.service;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.emoflon.gips.eclipse.TracePlugin;
import org.emoflon.gips.eclipse.api.IRemoteEclipseService;
import org.emoflon.gips.eclipse.api.ITraceContext;
import org.emoflon.gips.eclipse.api.ITraceManager;
import org.emoflon.gips.eclipse.pref.PluginPreferences;
import org.emoflon.gips.eclipse.trace.TraceModelLink;

public final class RemoteEclipseService implements IRemoteEclipseService {

	public static enum ServiceStatus {
		RUNNING, PAUSED, ERROR
	}

	private final IPropertyChangeListener preferenceListener = this::onPreferenceChange;

	private Registry registry;
	private int port;

	private ServiceStatus status = ServiceStatus.PAUSED;
	private Exception lastError;

	public RemoteEclipseService() throws RemoteException {
		super();
	}

	public ServiceStatus getServiceStatus() {
		return status;
	}

	public Exception getLastError() {
		return lastError;
	}

	public int getPort() {
		return port;
	}

	synchronized private void setPort(int port) {
		if (port < 0)
			throw new IllegalArgumentException("port must be greater than or equal to 0");
		if (registry != null)
			throw new UnsupportedOperationException("Unable to change port while service is running");

		this.port = port;
	}

	synchronized public void initialize() {
		ScopedPreferenceStore preferences = PluginPreferences.getPreferenceStore();
		preferences.addPropertyChangeListener(preferenceListener);

		setPort(preferences.getInt(PluginPreferences.PREF_TRACE_RMI_PORT));

		boolean startSerivce = preferences.getBoolean(PluginPreferences.PREF_TRACE_RMI);
		if (startSerivce)
			startService();
	}

	synchronized public void dispose() {
		PluginPreferences.getPreferenceStore().removePropertyChangeListener(preferenceListener);
		stopService();
	}

	synchronized private void startService() {
		if (status == ServiceStatus.RUNNING)
			return;

		try {
			// this may fail if someone else is already using that port
			registry = LocateRegistry.createRegistry(port);
			UnicastRemoteObject.exportObject(this, 0);
			registry.bind(IRemoteEclipseService.SERVICE_NAME, this);

			if (lastError != null)
				System.out.println("GIPSL Eclipse Integration available.");

			lastError = null;
			status = ServiceStatus.RUNNING;
		} catch (Exception e) {
			System.err.println("GIPSL Eclipse Integration unavailable:" + e.getLocalizedMessage());
			lastError = e;
			status = ServiceStatus.ERROR;
		}
	}

	synchronized private void stopService() {
		if (status == ServiceStatus.PAUSED)
			return;

		try {
			UnicastRemoteObject.unexportObject(this, true);
		} catch (Exception e) {

		}

		try {
			UnicastRemoteObject.unexportObject(registry, true);
		} catch (Exception e) {

		}

		registry = null;
		status = ServiceStatus.PAUSED;
	}

	private void onPreferenceChange(PropertyChangeEvent event) {
		switch (event.getProperty()) {
		case PluginPreferences.PREF_TRACE_RMI:
			boolean shouldServiceBeRunning = ((Boolean) event.getNewValue()).booleanValue();

			if (shouldServiceBeRunning)
				startService();
			else
				stopService();

			break;
		case PluginPreferences.PREF_TRACE_RMI_PORT:
			int newPort = ((Number) event.getNewValue()).intValue();
			if (this.port == newPort)
				return;

			stopService();
			setPort(newPort);
			startService();
			break;
		}
	}

	// Remote Service Methods

	@Override
	public void updateTraceModel(String contextId, TraceModelLink traceLink) {
		ITraceContext context = ITraceManager.getInstance().getContext(contextId);
		context.updateTraceModel(traceLink);
	}

	@Override
	public void updateModelValues(String contextId, String modelId, Map<String, String> values)
			throws RemoteException {
		ProjectContext context = TracePlugin.getInstance().getContextManager().getContext(contextId);
		context.updateModelValues(modelId, values);
	}

}
