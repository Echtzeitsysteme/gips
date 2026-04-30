package org.emoflon.gips.build.preference;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class PluginPreferences {

	private PluginPreferences() {
	}

	private static Object lock = new Object();
	private static ScopedPreferenceStore preferenceStore;

	public static ScopedPreferenceStore getPreferenceStore() {
		if (preferenceStore == null) {
			synchronized (lock) {
				if (preferenceStore == null) {
					preferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, PluginPreferences.STORE_KEY);
					preferenceStore.setSearchContexts(
							new IScopeContext[] { InstanceScope.INSTANCE, ConfigurationScope.INSTANCE });
				}
			}
		}
		return preferenceStore;
	}

	public static final String STORE_KEY = "org.emoflon.gips.build";

	/**
	 * Enables/Disables mapping indexer
	 * <ul>
	 * <li><bold>Type:</bold> {@link java.lang.Boolean}
	 * <li>Can not be null
	 * </ul>
	 */
	public static final String PREF_GIPSL_MAPPING_INDEXER = "PREF_GIPSL_MAPPING_INDEXER";

	public static final String PREF_GIPSL_BIGM = "PREF_GIPSL_BIGM";

	public static boolean isMappingIndexerEnabled() {
		return getPreferenceStore().getBoolean(PREF_GIPSL_MAPPING_INDEXER);
	}

	public static boolean isMappingIndexerDisabled() {
		return !isMappingIndexerEnabled();
	}

	public static double getBigMValue() {
		return getPreferenceStore().getDouble(PREF_GIPSL_BIGM);
	}
}
