package org.emoflon.gips.build.preference;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PluginPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	public PluginPreferencePage() {
		super(FieldEditorPreferencePage.GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(PluginPreferences.getPreferenceStore());
		setDescription("GIPSL Build configuration");
	}

	@Override
	protected void createFieldEditors() {
		var mappingIndexer = new BooleanFieldEditor(PluginPreferences.PREF_GIPSL_MAPPING_INDEXER, "Mapping &Indexer",
				getFieldEditorParent());
		mappingIndexer.getDescriptionControl(getFieldEditorParent()).setToolTipText(
				"The indexer can build a look-up table for a trivial filter expression (e.g. node equals another node), which greatly accelerates the build process when such expressions are used with a large model.");
		addField(mappingIndexer);

		var bigMValue = new DoubleFieldEditor(PluginPreferences.PREF_GIPSL_BIGM,
				"GIPSL Big M value (for shortcut operators)", getFieldEditorParent());
		bigMValue.getLabelControl(getFieldEditorParent()).setToolTipText(
				"This value is used in the Big M method, which is applied by some implication shortcut operations.");
		bigMValue.setValidRange(1, Double.MAX_VALUE);
		addField(bigMValue);
	}
}
