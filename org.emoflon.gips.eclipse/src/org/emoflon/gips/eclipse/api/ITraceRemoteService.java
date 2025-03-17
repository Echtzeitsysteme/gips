package org.emoflon.gips.eclipse.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.emoflon.gips.eclipse.trace.TraceModelLink;

/**
 * RMI service interface. This interface provides methods to access a single,
 * locally running instance of this plugin and can be used to exchange
 * information.
 * <p>
 * If available (see preferences), this service can be accessed as follows:
 * 
 * <pre>
 * var service = (ITraceRemoteService) LocateRegistry.getRegistry(port).lookup("ITraceRemoteService");
 * </pre>
 * 
 */
public interface ITraceRemoteService extends Remote {

	public static final String SERVICE_NAME = "ITraceRemoteService";
	public static final int DEFAULT_PORT = 2842;

	/**
	 * Updates the trace data of a given Eclipse project.
	 * 
	 * @param contextId Eclipse project name this trace belongs to
	 * @param traceLink trace between two models
	 * @throws RemoteException
	 */
	void updateTraceModel(String contextId, TraceModelLink traceLink) throws RemoteException;
}
