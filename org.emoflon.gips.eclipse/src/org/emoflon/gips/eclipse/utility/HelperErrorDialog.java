package org.emoflon.gips.eclipse.utility;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.emoflon.gips.eclipse.TracePlugin;

public final class HelperErrorDialog {
	private HelperErrorDialog() {
	}

	public static MultiStatus createMultiStatus(String msg, Throwable t) {
		List<Status> childStatuses = new ArrayList<>();
		StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();

		for (StackTraceElement stackTrace : stackTraces) {
			var status = new Status(IStatus.ERROR, TracePlugin.PLUGIN_ID, stackTrace.toString());
			childStatuses.add(status);
		}

		var ms = new MultiStatus(TracePlugin.PLUGIN_ID, IStatus.ERROR, childStatuses.toArray(new Status[] {}),
				t.toString(), t);
		return ms;
	}

}
