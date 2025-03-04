package org.emoflon.gips.core.milp;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Basically {@link SolverConfigMap} but with static type check via typed
 * {@link Key keys}
 */
public class SolverConfigTypeSafe {

	// Set Key<T> to private and we'll only be able to use these 'public keys' here.
	public static final Key<Boolean> KEY_TIME_LIMIT_ENABLED = new Key<>();
	public static final Key<Double> KEY_TIME_LIMIT = new Key<>();
	public static final Key<Boolean> KEY_TIME_LMIMIT_INCLUDES_INIT_TIME = new Key<>();
	public static final Key<Boolean> KEY_IS_RANDOM_SEED_ENABLED = new Key<>();
	public static final Key<Integer> KEY_RANDOM_SEED = new Key<>();
	public static final Key<Boolean> KEY_PRESOLVE_ENABLED = new Key<>();
	public static final Key<Boolean> KEY_OUTPUT_ENABLED = new Key<>();
	public static final Key<Boolean> KEY_TOLERANCE_ENABLED = new Key<>();
	public static final Key<Double> KEY_TOLERANCE = new Key<>();
	public static final Key<Boolean> KEY_LP_OUTPUT_ENABLED = new Key<>();
	public static final Key<String> KEY_LP_OUTPUT = new Key<>();
	public static final Key<Boolean> KEY_THREAD_COUNT_ENABLED = new Key<>();
	public static final Key<Integer> KEY_THREAD_COUNT = new Key<>();

	/**
	 * A key with Type T
	 * 
	 * @param <T> type of property
	 */
	private static class Key<T> {

	}

	public static interface ConfigChangeListener {
		public void onChange(ConfigChangeEvent event);
	}

	public static class ConfigChangeEvent {
		private final SolverConfigTypeSafe source;
		private final Map<Key<?>, ConfigModification<?>> modifications;

		public ConfigChangeEvent(SolverConfigTypeSafe source, Map<Key<?>, ConfigModification<?>> modifications) {
			this.source = Objects.requireNonNull(source, "source");
			this.modifications = Objects.requireNonNull(modifications, "modifications");
		}

		public SolverConfigTypeSafe getSource() {
			return source;
		}

		public boolean hasModification(Key<?> key) {
			return modifications.containsKey(key);
		}

		public <T> ConfigModification<T> getModification(Key<T> key) {
			return (ConfigModification<T>) modifications.get(key);
		}
	}

	public static class ConfigModification<T> {
		private final T oldValue;
		private final T newValue;
		private final Key<T> propertyKey;

		public ConfigModification(Key<T> propertyKey, T oldValue, T newValue) {
			this.propertyKey = Objects.requireNonNull(propertyKey, "propertyKey");
			this.oldValue = oldValue;
			this.newValue = newValue;
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

	private Map<Key<?>, Set<ConfigChangeListener>> listenersByKey = new HashMap<>();
	private Map<Key<?>, Object> mappings = new HashMap<>();

	/**
	 * Enables/Disables {@link #notifyListeners()} method
	 */
	private boolean enableNotifier = true;
	private Map<Key<?>, ConfigModification<?>> rememberedModifications = new HashMap<>();

	public <T> void addListener(Key<T> key, ConfigChangeListener listener) {
		addListener(key, listener, false);
	}

	public <T> void addListener(Key<T> key, ConfigChangeListener listener, boolean runListenerImmediately) {
		Objects.requireNonNull(listener, "listener");
		Objects.requireNonNull(key, "key");
		listenersByKey.computeIfAbsent(key, k -> new HashSet<>()).add(listener);

		if (runListenerImmediately) {
			T property = getProperty(key);
			Map<Key<?>, ConfigModification<?>> modifications = Collections.singletonMap(key,
					new ConfigModification<T>(key, property, property));
			listener.onChange(new ConfigChangeEvent(this, modifications));
		}
	}

	public void removeListener(ConfigChangeListener listener) {
		for (var listeners : listenersByKey.values())
			listeners.remove(listener);
	}

	public void removeListener(Key<?> key, ConfigChangeListener listener) {
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
			return; // Only continue if the value changes

		mappings.put(key, value);
		notifyListeners(key, oldValue, value);
	}

	/**
	 * Allows multiple setters to be called without triggering all listeners for
	 * each setter. This method will notify all listeners afterwards.
	 * 
	 * @param batch
	 */
	public void setPropertiesBatch(Consumer<SolverConfigTypeSafe> batch) {
		enableNotifier = false;
		batch.accept(this);
		enableNotifier = true;
		notifyListeners();
	}

	private <T> void notifyListeners(Key<T> key, T oldValue, T value) {
		ConfigModification<T> mod = new ConfigModification<>(key, oldValue, value);
		rememberedModifications.put(key, mod);
		notifyListeners();
	}

	private void notifyListeners() {
		if (!enableNotifier || rememberedModifications.isEmpty() || listenersByKey.isEmpty())
			return;

		Set<ConfigChangeListener> listeners = rememberedModifications.keySet().stream() //
				.map(key -> listenersByKey.get(key)) //
				.filter(e -> e != null) //
				.flatMap(Set::stream) //
				.collect(Collectors.toCollection(HashSet::new)); // try to remove duplicates

		if (listeners.isEmpty())
			return;

		Map<Key<?>, ConfigModification<?>> modifications = Collections
				.unmodifiableMap(new HashMap<>(rememberedModifications));
		rememberedModifications.clear();

		ConfigChangeEvent event = new ConfigChangeEvent(this, modifications);

		for (var listener : listeners)
			listener.onChange(event);
	}

	// region for specific properties
	//

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
