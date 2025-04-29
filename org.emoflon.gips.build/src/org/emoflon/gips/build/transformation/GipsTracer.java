package org.emoflon.gips.build.transformation;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.eclipse.api.ITraceManager;
import org.emoflon.gips.eclipse.trace.TraceMap;

public class GipsTracer {

	final private TraceMap<EObject, EObject> trace = new TraceMap<>();
	final private boolean isTracingEnabled = ITraceManager.getInstance().isGIPSLTracingEnabled();

	public TraceMap<EObject, EObject> getMap() {
		return trace;
	}

	public void map(EObject source, EObject target) {
		if (isTracingEnabled) {
			trace.map(source, target);
		}
	}

	public void mapOneToMany(EObject source, Collection<? extends EObject> targets) {
		if (isTracingEnabled) {
			trace.mapOneToMany(source, targets);
		}
	}

}
