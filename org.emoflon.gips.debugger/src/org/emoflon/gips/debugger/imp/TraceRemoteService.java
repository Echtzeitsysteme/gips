package org.emoflon.gips.debugger.imp;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.emoflon.gips.debugger.Activator;
import org.emoflon.gips.debugger.api.ITraceRemoteService;
import org.emoflon.gips.debugger.trace.TraceModelLink;

public final class TraceRemoteService implements ITraceRemoteService {

	private boolean isRunning = false;

	private Registry registry;
	private ITraceRemoteService exportedStub;
	private int port;

	public TraceRemoteService() throws RemoteException {
		super();
		setPort(2842);
	}

	public int getPort() {
		return port;
	}

	private void setPort(int port) {
		// TODO make changeable
		this.port = port;
	}

	synchronized public void initialize() {
		if (exportedStub != null)
			return;

		try { // setup RMI service
			exportedStub = (ITraceRemoteService) UnicastRemoteObject.exportObject(this, 0);

			registry = LocateRegistry.createRegistry(port);
			registry.bind("ITraceRemoteService", exportedStub);

		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}

		isRunning = true;
	}

	synchronized public void dispose() {
		try {
			UnicastRemoteObject.unexportObject(exportedStub, true);
			exportedStub = null;
		} catch (NoSuchObjectException e) {
			// no such object? No problem!
			// e.printStackTrace();
		}

		try {
			registry.unbind("ITraceRemoteService");
			registry = null;
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		isRunning = false;
	}

	@Override
	public void updateTraceModel(String contextId, TraceModelLink traceLink) {
		// TODO Auto-generated method stub
		var context = Activator.getInstance().getTraceManager().getContext(contextId);
		context.updateTraceModel(traceLink);
	}

}
