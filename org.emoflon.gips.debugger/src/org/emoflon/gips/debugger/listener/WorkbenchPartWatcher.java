package org.emoflon.gips.debugger.listener;

import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public final class WorkbenchPartWatcher {

	private final WindowPartListener listener;

	public WorkbenchPartWatcher(IPartListener2 delegate) {
		this.listener = new WindowPartListener(delegate);
	}

	/**
	 * Starts monitoring the {@link IWorkbench workbench} for any
	 * {@link IWorkbenchWindow windows} and adds the given part listener to them.
	 * This includes already existing windows and windows which where created after
	 * calling this method.
	 * 
	 * 
	 */
	public void start() {
		stop();

		var workbench = PlatformUI.getWorkbench();
		workbench.addWindowListener(listener);

		// call part listener on all opened parts
		for (var window : workbench.getWorkbenchWindows()) {
			listener.windowOpened(window);

			for (var page : window.getPages()) {
				for (var reference : page.getEditorReferences()) {
					listener.getPartListener().partOpened(reference);
				}

				for (var reference : page.getViewReferences()) {
					listener.getPartListener().partOpened(reference);
				}
			}
		}

		var activeWindow = workbench.getActiveWorkbenchWindow();
		if (activeWindow != null) {
			listener.windowActivated(activeWindow);

			var activePage = activeWindow.getActivePage();
			if (activePage != null) {
				var activeRef = activePage.getActivePartReference();
				if (activeRef != null) {
					listener.getPartListener().partActivated(activeRef);
				}
			}
		}
	}

	/**
	 * Stops monitoring and removes all listeners.
	 */
	public void stop() {
		var workbench = PlatformUI.getWorkbench();
		workbench.removeWindowListener(listener);
		listener.removeAllPartListener();
	}
}
