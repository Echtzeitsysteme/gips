package org.emoflon.gips.gipsl.ui.visualization;

import java.util.Objects;

import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;

import com.google.inject.Inject;

public class PlantUMLPreferences {

	public static class Initializer implements IPreferenceStoreInitializer {
		@Override
		public void initialize(IPreferenceStoreAccess access) {
			access.getWritablePreferenceStore().setDefault(INCLUDE_CONTEXT_NODES, true);
			access.getWritablePreferenceStore().setDefault(INCLUDE_BODY_NODES, true);
			access.getWritablePreferenceStore().setDefault(DEFAULT_FONT, "sans-serif");
		}
	}

	public static final String INCLUDE_CONTEXT_NODES = "includeReferencedNodesByContext";
	public static final String INCLUDE_BODY_NODES = "includeReferencedNodesByBody";
	public static final String DEFAULT_FONT = "defaultFont";

	private IPreferenceStoreAccess preferenceStoreAccess;

	@Inject
	public PlantUMLPreferences(IPreferenceStoreAccess preferenceStoreAccess) {
		this.preferenceStoreAccess = Objects.requireNonNull(preferenceStoreAccess, "preferenceStoreAccess");
	}

	public boolean includeReferencedNodesByContext() {
		return preferenceStoreAccess.getPreferenceStore().getBoolean(INCLUDE_CONTEXT_NODES);
	}

	public void setIncludeReferencedNodesByContext(boolean value) {
		preferenceStoreAccess.getWritablePreferenceStore().setValue(INCLUDE_CONTEXT_NODES, value);
	}

	public boolean includedReferencedNodesByBody() {
		return preferenceStoreAccess.getPreferenceStore().getBoolean(INCLUDE_BODY_NODES);
	}

	public void setIncludedReferencedNodesByBody(boolean value) {
		preferenceStoreAccess.getWritablePreferenceStore().setValue(INCLUDE_BODY_NODES, value);
	}

	public FontData getCustomFont() {
		return PreferenceConverter.getFontData(preferenceStoreAccess.getPreferenceStore(), DEFAULT_FONT);
	}

	public String getFontName() {
		return getCustomFont().getName();
	}

	public int getFontSize() {
		return getCustomFont().getHeight();
	}

	public boolean isCustomFontSet() {
		return !preferenceStoreAccess.getPreferenceStore().isDefault(DEFAULT_FONT);
	}
}
