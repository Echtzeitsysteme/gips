package org.emoflon.gips.debugger.pref;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.emoflon.gips.debugger.TracePlugin;

/**
 * GIPS Trace preference page
 */
public class PluginPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	public PluginPreferencePage() {
		super(FieldEditorPreferencePage.GRID);
	}

	// TODO: The layout can't be changed with FieldEditors, so to make it prettier
	// or do anything more complex, the page needs to be reimplemented without
	// FieldEditors.
	// https://github.com/eclipse-jdt/eclipse.jdt.ui/blob/master/org.eclipse.jdt.ui/ui/org/eclipse/jdt/internal/ui/preferences/JavaBasePreferencePage.java

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(TracePlugin.getInstance().getPreferenceStore());
		setDescription("GIPS trace configuration");
	}

	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(PluginPreferences.PREF_TRACE_DISPLAY_ACTIVE, "Trace &visualisation",
				getFieldEditorParent()));

		{
			var configGroup = new Group(getFieldEditorParent(), SWT.NULL);
			configGroup.setLayout(new GridLayout());
			configGroup.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false, 2, 1));
			configGroup.setText("Trace RMI Service");

			var editorRMI = new BooleanFieldEditor(PluginPreferences.PREF_TRACE_RMI, "&RMI enabled", configGroup);
			addField(editorRMI);

			var editorRMIPort = new IntegerFieldEditor(PluginPreferences.PREF_TRACE_RMI_PORT, "RMI &port", configGroup);
			editorRMIPort.setValidRange(1023, 65535);
			editorRMIPort.setEnabled(false, configGroup);
			addField(editorRMIPort);
		}

		// horizontal line
//		new Label(getFieldEditorParent(), SWT.SEPARATOR | SWT.HORIZONTAL)
//				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
//		new Label(getFieldEditorParent(), SWT.NONE).setText("My Group Title");

		{
			var configGroup = new Group(getFieldEditorParent(), SWT.NULL);
			configGroup.setLayout(new GridLayout());
			configGroup.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false, 2, 1));
			configGroup.setText("Trace storage");

			var editorTraceStore = new BooleanFieldEditor(PluginPreferences.PREF_TRACE_CACHE_ENABLED,
					"&Save/Load enabled", configGroup);
			addField(editorTraceStore);

			var editorTraceStorePath = new StringFieldEditor(PluginPreferences.PREF_TRACE_CACHE_LOCATION,
					"Project &relative path:", configGroup);
			editorTraceStorePath.setEmptyStringAllowed(false);
			editorTraceStorePath.setPropertyChangeListener(null);
			addField(editorTraceStorePath);

//			var editorTraceStoreMaxTime = new IntegerFieldEditor(PluginPreferences.PREF_TRACE_CACHE_MAX_TIME,
//					"Max &cache time (hours)", configGroup);
//			editorTraceStoreMaxTime.setValidRange(0, Integer.MAX_VALUE);
//			addField(editorTraceStoreMaxTime);
		}
	}

}
