package org.emoflon.gips.eclipse.pref;

import java.nio.file.Path;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.emoflon.gips.eclipse.api.IRemoteEclipseService;

public final class PluginPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		var node = DefaultScope.INSTANCE.getNode(PluginPreferences.STORE_KEY);

		// gipsl->intermediate tracing is enabled
		node.putBoolean(PluginPreferences.PREF_GIPSL_TRACING_ENABLED, true);

		// trace visualization is disabled
		node.putBoolean(PluginPreferences.PREF_TRACE_DISPLAY_ACTIVE, false);

		// code mining is disabled
		node.putBoolean(PluginPreferences.PREF_CODE_MINING_ENABLED, false);

		// RMI Service is running
		node.putBoolean(PluginPreferences.PREF_TRACE_RMI, true);
		node.putInt(PluginPreferences.PREF_TRACE_RMI_PORT, IRemoteEclipseService.DEFAULT_PORT);

		// trace caching is enabled
		node.putBoolean(PluginPreferences.PREF_TRACE_CACHE_ENABLED, true);
		node.put(PluginPreferences.PREF_TRACE_CACHE_LOCATION,
				IPath.fromPath(Path.of("trace")).makeRelative().toPortableString());
		node.putInt(PluginPreferences.PREF_TRACE_CACHE_MAX_TIME, 24);
	}

}
