package org.emoflon.gips.debugger.pref;

import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.emoflon.gips.debugger.TracePlugin;

public final class PluginPreferences {
	private PluginPreferences() {

	}

	public static final String STORE_KEY = TracePlugin.PLUGIN_ID;

	public static final String PREF_TRACE_DISPLAY_ACTIVE = "PREF_TRACE_DISPLAY_ACTIVE";
	public static final String PREF_TRACE_RMI = "PREF_TRACE_RMI";
	public static final String PREF_TRACE_RMI_PORT = "PREF_TRACE_RMI_PORT";

	public static ScopedPreferenceStore getPreferenceStore() {
		return TracePlugin.getInstance().getPreferenceStore();
	}

}
