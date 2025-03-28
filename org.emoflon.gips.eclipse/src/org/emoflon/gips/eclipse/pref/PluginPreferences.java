package org.emoflon.gips.eclipse.pref;

import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.emoflon.gips.eclipse.TracePlugin;

public final class PluginPreferences {
	private PluginPreferences() {

	}

	public static final String STORE_KEY = TracePlugin.PLUGIN_ID;

	public static final String PREF_TRACE_DISPLAY_ACTIVE = "PREF_TRACE_DISPLAY_ACTIVE";
	public static final String PREF_TRACE_RMI = "PREF_TRACE_RMI";
	public static final String PREF_TRACE_RMI_PORT = "PREF_TRACE_RMI_PORT";

	/**
	 * Enables/Disables code mining for cplex editor
	 * <ul>
	 * <li><bold>Type:</bold> {@link java.lang.Boolean}
	 * <li>Can not be null
	 * </ul>
	 */
	public static final String PREF_CODE_MINING_ENABLED = "PREF_CODE_MINING_ENABLED";

	/**
	 * Enableds/Disables trace caching.
	 * <ul>
	 * <li><bold>Type:</bold> {@link java.lang.Boolean}
	 * <li>Can not be null
	 * </ul>
	 */
	public static final String PREF_TRACE_CACHE_ENABLED = "PREF_TRACE_CACHE_ENABLED";

	/**
	 * Trace cache location. The location is a relative path within any project.
	 * <ul>
	 * <li><bold>Type:</bold> {@link java.lang.String}
	 * <li>Can not be null
	 * <li>Can not be empty
	 * </ul>
	 */
	public static final String PREF_TRACE_CACHE_LOCATION = "PREF_TRACE_CACHE_LOCATION";

	/**
	 * To avoid unnecessary memory consumption, data that has not been used for x
	 * hours is removed from the cache.
	 * <ul>
	 * <li><bold>Type:</bold> {@link java.lang.Integer}
	 * <li>Can not be null
	 * <li>Can not be less than 0
	 * </ul>
	 */
	public static final String PREF_TRACE_CACHE_MAX_TIME = "PREF_TRACE_CACHE_MAX_TIME";

	public static ScopedPreferenceStore getPreferenceStore() {
		return TracePlugin.getInstance().getPreferenceStore();
	}

}
