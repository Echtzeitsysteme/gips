package org.emoflon.gips.eclipse.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import org.emoflon.gips.eclipse.trace.TraceModelLink;

/**
 * RMI service interface. This interface provides methods to access a single,
 * locally running instance of this plugin within Eclipse and can be used to
 * exchange information.
 * <p>
 * If available (see preferences), this service can be accessed as follows:
 * 
 * <pre>
 * var service = (IRemoteService) LocateRegistry.getRegistry(port).lookup(IRemoteService.SERVICE_NAME);
 * </pre>
 * 
 */
public interface IRemoteEclipseService extends Remote {

	public static final String SERVICE_NAME = "IRemoteEclipseService";
	public static final int DEFAULT_PORT = 2842;

	/**
	 * Updates the trace data of a given Eclipse project.
	 * 
	 * @param contextId Eclipse project name this trace belongs to
	 * @param traceLink trace between two models
	 * @throws RemoteException
	 */
	void updateTraceModel(String contextId, TraceModelLink traceLink) throws RemoteException;

	TraceModelLink getTraceModel(String contextId, String srcModel, String dstModel) throws RemoteException;

	void updateModelValues(String contextId, String lpModelId, Map<String, String> values) throws RemoteException;
}
