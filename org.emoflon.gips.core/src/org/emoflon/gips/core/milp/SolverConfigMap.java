package org.emoflon.gips.core.milp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Replaces fields with a map. A property is now accessed via a string key. It's
 * now possible to tell the listener exactly which property has changed.
 * 
 * @see SolverConfig
 * @see SolverConfigTypeSafe
 */
public class SolverConfigMap {

	// keys
	public static final String KEY_TIME_LIMIT_ENABLED = "KEY_TIME_LIMIT_ENABLED";
	public static final String KEY_TIME_LIMIT = "KEY_TIME_LIMIT";

	public static interface ConfigChangeListener {
		public void onChange(ConfigChangeEvent event);
	}

	public static class ConfigChangeEvent {
		private final SolverConfigMap source;
		private final Object oldValue;
		private final Object newValue;
		private final String propertyKey;

		public ConfigChangeEvent(SolverConfigMap source, String propertyKey, Object oldValue, Object newValue) {
			this.source = Objects.requireNonNull(source, "source");
			this.propertyKey = Objects.requireNonNull(propertyKey, "propertyKey");
			this.oldValue = oldValue;
			this.newValue = newValue;
		}

		public SolverConfigMap getSource() {
			return source;
		}

		public String getPropertyKey() {
			return propertyKey;
		}

		public Object getOldValue() {
			return oldValue;
		}

		public Object getNewValue() {
			return newValue;
		}
	}

	private List<ConfigChangeListener> listeners = new ArrayList<>(10);
	private Map<String, Object> mappings = new HashMap<>();

	public void addListener(ConfigChangeListener listener) {
		Objects.requireNonNull(listener, "listener");
		if (listeners.contains(listener))
			return;
		listeners.add(listener);
	}

	public void removeListener(ConfigChangeListener listener) {
		listeners.remove(listener);
	}

	@SuppressWarnings("unchecked")
	public <T> T getProperty(String key) {
		return (T) mappings.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getProperty(String key, T defaultValue) {
		return (T) mappings.getOrDefault(key, defaultValue);
	}

	public void setProperty(String key, Object value) {
		Objects.requireNonNull(key, "key");
		Object oldValue = mappings.get(key);

		if (oldValue == value || oldValue != null && oldValue.equals(value))
			return;

		mappings.put(key, value);
		notifyListeners(key, oldValue, value);
	}

	private void notifyListeners(String key, Object oldValue, Object value) {
		ConfigChangeEvent event = new ConfigChangeEvent(this, key, oldValue, value);
		for (var listener : this.listeners)
			listener.onChange(event);
	}

	// region for specific properties

	public boolean isTimeLimitEnabled() {
		return getProperty(KEY_TIME_LIMIT_ENABLED, Boolean.FALSE);
	}

//	public boolean isTimeLimitEnabled() {
//		return getTimeLimit() > 0;
//	}

	public void setEnableTimeLimit(boolean value) {
		setProperty(KEY_TIME_LIMIT_ENABLED, value);
	}

	public double getTimeLimit() {
		return getProperty(KEY_TIME_LIMIT, 0);
	}

	public void setTimeLimit(double value) {
		if (value <= 0)
			throw new IllegalArgumentException("Given new time limit is smaller or equal to 0.");
		setProperty(KEY_TIME_LIMIT, value);
	}

}
