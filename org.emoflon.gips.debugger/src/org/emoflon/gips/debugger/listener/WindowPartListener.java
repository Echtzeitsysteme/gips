package org.emoflon.gips.debugger.listener;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;

public final class WindowPartListener implements IWindowListener {

	private final Set<IWorkbenchWindow> windows = new HashSet<>();
	private final IPartListener2 listener;

	public WindowPartListener(IPartListener2 listener) {
		this.listener = Objects.requireNonNull(listener, "listener");
	}

	public IPartListener2 getPartListener() {
		return listener;
	}

	public void removeAllPartListener() {
		synchronized (windows) {
			for (var window : windows) {
				window.getPartService().removePartListener(listener);
			}
			this.windows.clear();
		}
	}

	private void addListener(IWorkbenchWindow window) {
		synchronized (windows) {
			windows.add(window);
			window.getPartService().addPartListener(this.listener);
		}
	}

	private void removeListener(IWorkbenchWindow window) {
		synchronized (windows) {
			windows.remove(window);
			window.getPartService().removePartListener(this.listener);
		}
	}

	@Override
	public void windowActivated(IWorkbenchWindow window) {
		addListener(window);
	}

	@Override
	public void windowOpened(IWorkbenchWindow window) {
		addListener(window);
	}

	@Override
	public void windowClosed(IWorkbenchWindow window) {
		removeListener(window);
	}

	@Override
	public void windowDeactivated(IWorkbenchWindow window) {

	}

}
