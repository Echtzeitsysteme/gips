package org.emoflon.gips.build.preference;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;

public class PluginPreferenceInitializer extends AbstractPreferenceInitializer {
	@Override
	public void initializeDefaultPreferences() {
		var node = DefaultScope.INSTANCE.getNode(PluginPreferences.STORE_KEY);

		// gipsl maping indexer is enabled
		node.putBoolean(PluginPreferences.PREF_GIPSL_MAPPING_INDEXER, true);
	}
}
