package org.emoflon.gips.core.milp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Basically {@link SolverConfigMap} but with static type check via typed
 * {@link Key keys}
 */
public class SolverConfigTypeSafe {

	// Set Key<T> to private and we'll only be able to use these 'public keys' here.
	public static final Key<Boolean> KEY_TIME_LIMIT_ENABLED = new Key<>();
	public static final Key<Double> KEY_TIME_LIMIT = new Key<>();

	/**
	 * A key with Type T
	 * 
	 * @param <T> type of property
	 */
	private static class Key<T> {

	}

	public static interface ConfigChangeListener<T> {
		public void onChange(ConfigChangeEvent<T> event);
	}

	public static class ConfigChangeEvent<T> {
		private final SolverConfigTypeSafe source;
		private final T oldValue;
		private final T newValue;
		private final Key<T> propertyKey;

		public ConfigChangeEvent(SolverConfigTypeSafe source, Key<T> propertyKey, T oldValue, T newValue) {
			this.source = Objects.requireNonNull(source, "source");
			this.propertyKey = Objects.requireNonNull(propertyKey, "propertyKey");
			this.oldValue = oldValue;
			this.newValue = newValue;
		}

		public SolverConfigTypeSafe getSource() {
			return source;
		}

		public Key<T> getPropertyKey() {
			return propertyKey;
		}

		public T getOldValue() {
			return oldValue;
		}

		public T getNewValue() {
			return newValue;
		}
	}

	private Map<Key<?>, Set<ConfigChangeListener<?>>> listenersByKey = new HashMap<>();
	private Map<Key<?>, Object> mappings = new HashMap<>();

	public <T> void addListener(Key<T> key, ConfigChangeListener<T> listener) {
		addListener(key, listener, false);
	}

	public <T> void addListener(Key<T> key, ConfigChangeListener<T> listener, boolean runListenerImmediately) {
		Objects.requireNonNull(listener, "listener");
		Objects.requireNonNull(key, "key");
		listenersByKey.computeIfAbsent(key, (k) -> new HashSet<>()).add(listener);

		if (runListenerImmediately) {
			var property = getProperty(key);
			listener.onChange(new ConfigChangeEvent<T>(this, key, property, property));
		}
	}

	public void removeListener(ConfigChangeListener<?> listener) {
		for (var listeners : listenersByKey.values())
			listeners.remove(listener);
	}

	public void removeListener(Key<?> key, ConfigChangeListener<?> listener) {
		var listeners = listenersByKey.get(key);
		if (listeners != null) {
			listeners.remove(listener);
			if (listeners.isEmpty())
				listenersByKey.remove(key);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getProperty(Key<T> key) {
		return (T) mappings.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getProperty(Key<T> key, T defaultValue) {
		return (T) mappings.getOrDefault(key, defaultValue);
	}

	@SuppressWarnings("unchecked")
	public <T> void setProperty(Key<T> key, T value) {
		Objects.requireNonNull(key, "key");
		T oldValue = (T) mappings.get(key);

		if (oldValue == value || oldValue != null && oldValue.equals(value))
			return;

		mappings.put(key, value);
		notifyListeners(key, oldValue, value);
	}

	@SuppressWarnings("unchecked")
	private <T> void notifyListeners(Key<T> key, T oldValue, T value) {
		ConfigChangeEvent<T> event = new ConfigChangeEvent<>(this, key, oldValue, value);
		for (var listeners : listenersByKey.values())
			for (ConfigChangeListener<?> listener : listeners)
				((ConfigChangeListener<T>) listener).onChange(event);
	}

	// region for specific properties

	public boolean isTimeLimitEnabled() {
		return getProperty(KEY_TIME_LIMIT_ENABLED, Boolean.FALSE);
	}

	public void setEnableTimeLimit(boolean value) {
		setProperty(KEY_TIME_LIMIT_ENABLED, value);
	}

	public double getTimeLimit() {
		return getProperty(KEY_TIME_LIMIT, 0d);
	}

	public void setTimeLimit(double value) {
		if (value <= 0)
			throw new IllegalArgumentException("Given new time limit is smaller or equal to 0.");
		setProperty(KEY_TIME_LIMIT, value);
	}

}
