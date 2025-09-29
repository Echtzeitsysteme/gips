package org.emoflon.gips.gipsl.ui.visualization;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FontFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.xtext.ui.editor.preferences.AbstractPreferencePage;

public class PlantUMLPreferencePage extends AbstractPreferencePage implements IWorkbenchPreferencePage {

	public PlantUMLPreferencePage() {
		setDescription("GIPSL to PlantUML configuration");
	}

	@Override
	protected void createFieldEditors() {

		{ // general
			Group group = new Group(getFieldEditorParent(), SWT.SHADOW_IN);
			group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
			group.setLayout(new GridLayout());
			group.setText("General");

			Composite composite = new Composite(group, SWT.NONE);
			addField(new FontFieldEditor(PlantUMLPreferences.DEFAULT_FONT, "Font Type and size", composite));
			group.pack();
		}

		{ // details
			Group group = new Group(getFieldEditorParent(), SWT.SHADOW_IN);
			group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
			group.setLayout(new GridLayout());
			group.setText("Details");

			Composite composite = new Composite(group, SWT.NONE);
			addField(new BooleanFieldEditor(PlantUMLPreferences.INCLUDE_CONTEXT_NODES,
					"Show which node references are being used in a given context.", composite));
			addField(new BooleanFieldEditor(PlantUMLPreferences.INCLUDE_BODY_NODES,
					"Show which node references are being used in an expression.", composite));
			group.pack();
		}
	}

}
