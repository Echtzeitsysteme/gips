package org.emoflon.gips.debugger.utility;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.emoflon.gips.debugger.Activator;

public final class HelperErrorDialog {
	private HelperErrorDialog() {
	}

	public static MultiStatus createMultiStatus(String msg, Throwable t) {
		List<Status> childStatuses = new ArrayList<>();
		StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();

		for (StackTraceElement stackTrace : stackTraces) {
			var status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, stackTrace.toString());
			childStatuses.add(status);
		}

		var ms = new MultiStatus(Activator.PLUGIN_ID, IStatus.ERROR, childStatuses.toArray(new Status[] {}),
				t.toString(), t);
		return ms;
	}

}
