package org.emoflon.gips.debugger.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;
import org.emoflon.gips.debugger.api.IEditorTracker;
import org.emoflon.gips.debugger.imp.connector.EditorTraceConnectionFactory;
import org.emoflon.gips.debugger.imp.connector.IEditorTraceConnection;
import org.emoflon.gips.debugger.imp.connector.IEditorTraceConnectionFactory;
import org.emoflon.gips.debugger.listener.WorkbenchPartWatcher;

/**
 * This class is used to monitor the workbench for any editor, which can be
 * connected to the {@link TraceManager}. To do so it uses an
 * {@link IEditorTraceConnectionFactory}.
 * 
 * @see EditorTraceConnectionFactory
 */
final class EditorTracker implements IEditorTracker {

	private final IEditorTraceConnectionFactory editorConnectionFactory;
	private final Map<IEditorPart, IEditorTraceConnection> registeredEditors = new HashMap<>();
	private final WorkbenchPartWatcher partWatcher = new WorkbenchPartWatcher(new IPartListener2() {
		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
			if (partRef instanceof IEditorReference && partRef.getPart(false) instanceof IEditorPart editor) {
				registerEditor(editor);
			}
		}

		@Override
		public void partClosed(IWorkbenchPartReference partRef) {
			if (partRef instanceof IEditorReference && partRef.getPart(false) instanceof IEditorPart editor) {
				unregisterEditor(editor);
			}
		}
	});

	public EditorTracker(IEditorTraceConnectionFactory editorConnectionFactory) {
		this.editorConnectionFactory = Objects.requireNonNull(editorConnectionFactory, "editorConnectionFactory");
	}

	public void initialize() {
		partWatcher.start();
	}

	public void dispose() {
		partWatcher.stop();
		unregisterAllEditors();
	}

	@Override
	public boolean registerEditor(IEditorPart editor) {
		Objects.requireNonNull(editor, "editor");

		synchronized (registeredEditors) {
			if (registeredEditors.containsKey(editor)) {
				return true;
			}

			if (editorConnectionFactory.canConnect(editor)) {
				var connection = editorConnectionFactory.createConnection(editor);
				connection.connect();
				registeredEditors.put(editor, connection);
				return true;
			}
		}

		return false;
	}

	@Override
	public void unregisterEditor(IEditorPart editor) {
		synchronized (registeredEditors) {
			var connection = registeredEditors.get(editor);
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public void unregisterAllEditors() {
		synchronized (registeredEditors) {
			for (var connection : registeredEditors.values()) {
				connection.disconnect();
			}
			registeredEditors.clear();
		}
	}

}
