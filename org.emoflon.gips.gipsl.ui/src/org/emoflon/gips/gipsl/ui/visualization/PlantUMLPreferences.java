package org.emoflon.gips.gipsl.ui.visualization;

import java.util.Objects;

import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;

import com.google.inject.Inject;

public class PlantUMLPreferences {

	public static class Initializer implements IPreferenceStoreInitializer {
		@Override
		public void initialize(IPreferenceStoreAccess access) {
			access.getWritablePreferenceStore().setDefault(INCLUDE_CONTEXT_NODES, true);
			access.getWritablePreferenceStore().setDefault(INCLUDE_BODY_NODES, true);
		}
	}

	public static final String INCLUDE_CONTEXT_NODES = "includeReferencedNodesByContext";
	public static final String INCLUDE_BODY_NODES = "includeReferencedNodesByBody";

	private IPreferenceStoreAccess preferenceStoreAccess;

	@Inject
	public PlantUMLPreferences(IPreferenceStoreAccess preferenceStoreAccess) {
		this.preferenceStoreAccess = Objects.requireNonNull(preferenceStoreAccess, "preferenceStoreAccess");
	}

	public boolean addReferencedNodesByContext() {
		return preferenceStoreAccess.getPreferenceStore().getBoolean(INCLUDE_CONTEXT_NODES);
	}

	public void setaddReferencedNodesByContext(boolean value) {
		preferenceStoreAccess.getWritablePreferenceStore().setValue(INCLUDE_CONTEXT_NODES, value);
	}

	public boolean addReferencedNodesByMappingReferences() {
		return preferenceStoreAccess.getPreferenceStore().getBoolean(INCLUDE_BODY_NODES);
	}

	public void setAddReferencedNodesByMappingReferences(boolean value) {
		preferenceStoreAccess.getWritablePreferenceStore().setValue(INCLUDE_BODY_NODES, value);
	}
}
