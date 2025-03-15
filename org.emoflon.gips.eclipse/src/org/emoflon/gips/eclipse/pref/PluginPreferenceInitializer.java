package org.emoflon.gips.eclipse.pref;

import java.nio.file.Path;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;

public final class PluginPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		var node = DefaultScope.INSTANCE.getNode(PluginPreferences.STORE_KEY);

		// trace visualization is deactivated
		node.putBoolean(PluginPreferences.PREF_TRACE_DISPLAY_ACTIVE, false);

		// RMI Service
		node.putBoolean(PluginPreferences.PREF_TRACE_RMI, true);
		node.putInt(PluginPreferences.PREF_TRACE_RMI_PORT, 2842);

		// trace storage
		node.putBoolean(PluginPreferences.PREF_TRACE_CACHE_ENABLED, true);
		node.put(PluginPreferences.PREF_TRACE_CACHE_LOCATION,
				IPath.fromPath(Path.of("trace")).makeRelative().toPortableString());
		node.putInt(PluginPreferences.PREF_TRACE_CACHE_MAX_TIME, 24);
	}

}
