package org.emoflon.gips.debugger.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.emoflon.gips.debugger.trace.TraceModelLink;

public interface ITraceRemoteService extends Remote {
	void updateTraceModel(String contextId, TraceModelLink traceLink) throws RemoteException;
}
