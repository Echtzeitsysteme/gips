package org.emoflon.gips.debugger.service;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.emoflon.gips.debugger.api.ITraceContext;
import org.emoflon.gips.debugger.api.ITraceManager;
import org.emoflon.gips.debugger.api.ITraceRemoteService;
import org.emoflon.gips.debugger.pref.PluginPreferences;
import org.emoflon.gips.debugger.trace.TraceModelLink;

public final class TraceRemoteService implements ITraceRemoteService {

	private final IPropertyChangeListener preferenceListener = this::onPreferenceChange;
	private boolean isRunning = false;
	private Registry registry;
	private ITraceRemoteService exportedStub;
	private int port;

	public TraceRemoteService() throws RemoteException {
		super();
	}

	public int getPort() {
		return port;
	}

	synchronized private void setPort(int port) {
		if (port < 0)
			throw new IllegalArgumentException("port must be greater than or equal to 0");
		if (isRunning)
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
		if (isRunning)
			return; // nothing to do here

		try { // setup RMI service
			exportedStub = (ITraceRemoteService) UnicastRemoteObject.exportObject(this, 0);
			registry = LocateRegistry.createRegistry(port);
			registry.bind(ITraceRemoteService.SERVICE_NAME, exportedStub);
		} catch (AccessException e) {
			throw new IllegalStateException("Unable to start service. Access denied", e);
		} catch (AlreadyBoundException e) {
			throw new IllegalStateException("An instance of this service is already running", e);
		} catch (RemoteException e) {
			throw new IllegalStateException(e);
		}

		isRunning = true;
	}

	synchronized private void stopService() {
		if (!isRunning)
			return; // nothing to do here

		try {
			UnicastRemoteObject.unexportObject(exportedStub, true);
			exportedStub = null;
		} catch (NoSuchObjectException e) {
			// no such object? No problem!
		}

		try {
			registry.unbind(ITraceRemoteService.SERVICE_NAME);
			registry = null;
		} catch (AccessException e) {
			throw new IllegalStateException("Unable to shutdown service. Access denied", e);
		} catch (NotBoundException e) {
			// service already unbound? Strange but okay.
		} catch (RemoteException e) {
			throw new IllegalStateException(e);
		}

		isRunning = false;
	}

	private void onPreferenceChange(PropertyChangeEvent event) {
		switch (event.getProperty()) {
		case PluginPreferences.PREF_TRACE_RMI:
			boolean shouldServiceBeRunning = ((Boolean) event.getNewValue()).booleanValue();
			if (this.isRunning == shouldServiceBeRunning)
				return;

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

	@Override
	public void updateTraceModel(String contextId, TraceModelLink traceLink) {
		ITraceContext context = ITraceManager.getInstance().getContext(contextId);
		context.updateTraceModel(traceLink);
	}

}
