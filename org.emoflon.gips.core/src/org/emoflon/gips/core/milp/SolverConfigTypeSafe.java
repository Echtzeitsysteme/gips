package org.emoflon.gips.core.milp;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.emoflon.gips.intermediate.GipsIntermediate.SolverType;

/**
 * Basically {@link SolverConfigMap} but with static type check via typed
 * {@link ConfigKey keys}
 */
public class SolverConfigTypeSafe {

	// Set Key<T> to private and we'll only be able to use these 'public keys' here.
	public static final ConfigKey<SolverType> KEY_SOLVER_TYPE = new ConfigKey<>(SolverType.GUROBI);
	public static final ConfigKey<Boolean> KEY_TIME_LIMIT_ENABLED = new ConfigKey<>(false);
	public static final ConfigKey<Double> KEY_TIME_LIMIT = new ConfigKey<>(1d, new GreaterThan<>(0d));
	public static final ConfigKey<Boolean> KEY_TIME_LMIMIT_INCLUDES_INIT_TIME = new ConfigKey<>();
	public static final ConfigKey<Boolean> KEY_IS_RANDOM_SEED_ENABLED = new ConfigKey<>();
	public static final ConfigKey<Integer> KEY_RANDOM_SEED = new ConfigKey<>();
	public static final ConfigKey<Boolean> KEY_PRESOLVE_ENABLED = new ConfigKey<>();
	public static final ConfigKey<Boolean> KEY_OUTPUT_ENABLED = new ConfigKey<>();
	public static final ConfigKey<Boolean> KEY_TOLERANCE_ENABLED = new ConfigKey<>();
	public static final ConfigKey<Double> KEY_TOLERANCE = new ConfigKey<>();

	/**
	 * A key with Type T
	 * 
	 * @param <T> type of property
	 */
	public static class ConfigKey<T> {

		// Or like this?
		public static final ConfigKey<Boolean> LP_OUTPUT_ENABLED = new ConfigKey<>(false);
		public static final ConfigKey<String> LP_OUTPUT = new ConfigKey<>();
		public static final ConfigKey<Boolean> THREAD_COUNT_ENABLED = new ConfigKey<>(false);
		public static final ConfigKey<Integer> THREAD_COUNT = new ConfigKey<>();

		private final Validator<T> validator;
		private final T defaultValue;

		private ConfigKey(T defaultValue, Validator<T> validator) {
			this.defaultValue = defaultValue;
			this.validator = validator;
		}

		private ConfigKey() {
			this(null, null);
		}

		private ConfigKey(T defaultValue) {
			this(defaultValue, null);
		}

		private ConfigKey(Validator<T> validator) {
			this(null, validator);
		}
	}

	public static interface Validator<T> {
		/**
		 * Validates the given value.
		 * 
		 * @param value to be validated
		 * @throws IllegalArgumentException if the given value is not valid
		 */
		void validate(T value);
	}

	/**
	 * Validates that a given value is greater than a selected number.
	 */
	public static class GreaterThan<T extends Number & Comparable<T>> implements Validator<T> {
		private final T base;

		public GreaterThan(T base) {
			this.base = Objects.requireNonNull(base);
		}

		@Override
		public void validate(T value) {
			if (base.compareTo(value) <= 0)
				throw new IllegalArgumentException("Value must be greater than " + base);
		}
	}

	public static interface ConfigChangeListener {
		public void onChange(ConfigChangeEvent event);
	}

	public static class ConfigChangeEvent {
		private final SolverConfigTypeSafe source;
		private final Map<ConfigKey<?>, ConfigModification<?>> modifications;

		public ConfigChangeEvent(SolverConfigTypeSafe source, Map<ConfigKey<?>, ConfigModification<?>> modifications) {
			this.source = Objects.requireNonNull(source, "source");
			this.modifications = Objects.requireNonNull(modifications, "modifications");
		}

		public SolverConfigTypeSafe getSource() {
			return source;
		}

		public boolean hasModification(ConfigKey<?> key) {
			return modifications.containsKey(key);
		}

		public <T> ConfigModification<T> getModification(ConfigKey<T> key) {
			return (ConfigModification<T>) modifications.get(key);
		}
	}

	public static class ConfigModification<T> {
		private final T oldValue;
		private final T newValue;
		private final ConfigKey<T> propertyKey;

		public ConfigModification(ConfigKey<T> propertyKey, T oldValue, T newValue) {
			this.propertyKey = Objects.requireNonNull(propertyKey, "propertyKey");
			this.oldValue = oldValue;
			this.newValue = newValue;
		}

		public ConfigKey<T> getPropertyKey() {
			return propertyKey;
		}

		public T getOldValue() {
			return oldValue;
		}

		public T getNewValue() {
			return newValue;
		}
	}

	public static enum CopyMode {
		/**
		 * Only copy values that are not set in the target config. Useful for merging
		 * configs without overwriting any settings on the target config.
		 * 
		 * @see SolverConfigTypeSafe#hasProperty(Key)
		 */
		ONLY_NOT_SET, COPY_ALL
	}

	private Map<ConfigKey<?>, Set<ConfigChangeListener>> listenersByKey = new HashMap<>();
	private Map<ConfigKey<?>, Object> mappings = new HashMap<>();

	/**
	 * Enables/Disables {@link #notifyListeners()} method
	 */
	private boolean enableNotifier = true;
	private Map<ConfigKey<?>, ConfigModification<?>> rememberedModifications = new HashMap<>();

	/**
	 * Depending on the selected copy mode, adds the entries of the given config to
	 * this config.
	 * 
	 * @param other config which should be added to this one
	 * @param mode  copy mode
	 */
	public void addConfig(SolverConfigTypeSafe other, CopyMode mode) {
		Collection<ConfigKey<?>> entriesToAdd = switch (mode) {
		case COPY_ALL -> other.mappings.keySet();
		case ONLY_NOT_SET -> other.mappings.keySet().stream().filter(key -> !mappings.containsKey(key)).toList();
		default -> throw new IllegalArgumentException("Unexpected value: " + mode);
		};

		for (ConfigKey<?> key : entriesToAdd) {
			Object newValue = other.mappings.get(key);
			Object previousValue = mappings.put(key, newValue);
			if (!isSameValue(previousValue, newValue))
				addToNextConfigChangeEvent(key, previousValue, newValue);
		}

		notifyListeners();
	}

	public <T> void addListener(ConfigKey<T> key, ConfigChangeListener listener) {
		addListener(key, listener, false);
	}

	public <T> void addListener(ConfigKey<T> key, ConfigChangeListener listener, boolean runListenerImmediately) {
		Objects.requireNonNull(listener, "listener");
		Objects.requireNonNull(key, "key");
		listenersByKey.computeIfAbsent(key, k -> new HashSet<>()).add(listener);

		if (runListenerImmediately) {
			T property = getProperty(key);
			Map<ConfigKey<?>, ConfigModification<?>> modifications = Collections.singletonMap(key,
					new ConfigModification<T>(key, property, property));
			listener.onChange(new ConfigChangeEvent(this, modifications));
		}
	}

	public void removeListener(ConfigChangeListener listener) {
		for (var listeners : listenersByKey.values())
			listeners.remove(listener);
	}

	public void removeListener(ConfigKey<?> key, ConfigChangeListener listener) {
		var listeners = listenersByKey.get(key);
		if (listeners != null) {
			listeners.remove(listener);
			if (listeners.isEmpty())
				listenersByKey.remove(key);
		}
	}

	public <T> T getProperty(ConfigKey<T> key) {
		return getProperty(key, key.defaultValue);
	}

	public <T> boolean hasProperty(ConfigKey<T> key) {
		return mappings.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getProperty(ConfigKey<T> key, T defaultValue) {
		return (T) mappings.getOrDefault(key, defaultValue);
	}

	@SuppressWarnings("unchecked")
	public <T> void setProperty(ConfigKey<T> key, T value) {
		if (key.validator != null)
			key.validator.validate(value);

		T oldValue = (T) mappings.get(key);

		if (isSameValue(oldValue, value))
			return; // Only continue if the value changes

		mappings.put(key, value);
		addToNextConfigChangeEvent(key, oldValue, value);
		notifyListeners();
	}

	private boolean isSameValue(Object currentValue, Object newValue) {
		return currentValue == newValue || currentValue != null && currentValue.equals(newValue);
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

	public void setFromConfig(org.emoflon.gips.intermediate.GipsIntermediate.SolverConfig solverConfig) {
		setPropertiesBatch(config -> {
			if (solverConfig.isEnableLpOutput()) {
				config.setProperty(ConfigKey.LP_OUTPUT_ENABLED, true);
				config.setProperty(ConfigKey.LP_OUTPUT, solverConfig.getLpPath());
			}
		});
	}

//	private <T> void addOrOverwriteConfigModification(Key<T> key, T oldValue, T value) {
//		ConfigModification<T> mod = new ConfigModification<>(key, oldValue, value);
//		rememberedModifications.put(key, mod);
//	}

	/**
	 * Internal only. Type of {@code oldValue} and {@code newValue} already checked
	 * by {@link #setProperty(ConfigKey, Object)}.
	 * 
	 * @param key
	 * @param oldValue
	 * @param newValue
	 */
	@SuppressWarnings("rawtypes")
	private void addToNextConfigChangeEvent(ConfigKey<?> key, Object oldValue, Object newValue) {
		ConfigModification<?> mod = new ConfigModification(key, oldValue, newValue);
		rememberedModifications.put(key, mod);
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

		Map<ConfigKey<?>, ConfigModification<?>> modifications = Collections
				.unmodifiableMap(new HashMap<>(rememberedModifications));
		rememberedModifications.clear();

		ConfigChangeEvent event = new ConfigChangeEvent(this, modifications);

		for (ConfigChangeListener listener : listeners)
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

	public String getLpOutput() {
		return getProperty(ConfigKey.LP_OUTPUT);
	}

}
