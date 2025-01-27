package org.emoflon.gips.debugger.pref;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.emoflon.gips.debugger.TracePlugin;

/**
 * GIPS (Trace) preference page
 * 
 * unused yet
 */
public class PluginPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	public PluginPreferencePage() {
		super(FieldEditorPreferencePage.GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(TracePlugin.getInstance().getPreferenceStore());
		setDescription("GIPS trace configuration");
	}

	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(PluginPreferences.PREF_TRACE_DISPLAY_ACTIVE, "Trace &Visualisation",
				getFieldEditorParent()));

		var editorRMI = new BooleanFieldEditor(PluginPreferences.PREF_TRACE_RMI, "Trace &RMI enabled",
				getFieldEditorParent());
		addField(editorRMI);

		var editorRMIPort = new IntegerFieldEditor(PluginPreferences.PREF_TRACE_RMI_PORT, "Trace RMI &Port",
				getFieldEditorParent());
		editorRMIPort.setValidRange(1023, 65535);
		editorRMIPort.setEnabled(false, getFieldEditorParent());
		addField(editorRMIPort);

	}

}
