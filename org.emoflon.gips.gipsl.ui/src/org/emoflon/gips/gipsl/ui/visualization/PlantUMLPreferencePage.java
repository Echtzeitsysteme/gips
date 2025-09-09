package org.emoflon.gips.gipsl.ui.visualization;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.xtext.ui.editor.preferences.AbstractPreferencePage;

public class PlantUMLPreferencePage extends AbstractPreferencePage implements IWorkbenchPreferencePage {

	public PlantUMLPreferencePage() {
	}

	@Override
	protected void createFieldEditors() {
		{
			Group detailGroup = new Group(getFieldEditorParent(), SWT.SHADOW_IN);
			detailGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
			detailGroup.setLayout(new GridLayout());
			detailGroup.setText("Details");

			Composite composite = new Composite(detailGroup, SWT.NONE);
			addField(new BooleanFieldEditor(PlantUMLPreferences.INCLUDE_CONTEXT_NODES,
					"Show which node references are being used in a given context.", composite));
			addField(new BooleanFieldEditor(PlantUMLPreferences.INCLUDE_BODY_NODES,
					"Show which node references are being used in an expression.", composite));
			detailGroup.pack();
		}

	}

}
