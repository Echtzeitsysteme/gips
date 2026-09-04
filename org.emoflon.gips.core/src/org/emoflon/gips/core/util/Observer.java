package org.emoflon.gips.core.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class Observer implements Cloneable {

	protected final Map<ObservableStage, Map<String, IMeasurement>> stageMeasurements = new ConcurrentHashMap<>();

	public Observer() {

	}

	protected Observer(Map<ObservableStage, Map<String, IMeasurement>> stageMeasurements) {
		stageMeasurements.forEach((stage, map) -> {
			this.stageMeasurements.put(stage, new ConcurrentHashMap<>(map));
		});
	}

	public void reset() {
		stageMeasurements.clear();
	}

	public void resetStage(ObservableStage stage) {
		stageMeasurements.remove(stage);
	}

	/**
	 * Receives another Observer object (e.g., from another Thread) and merges the
	 * other entries with the own entries.
	 * 
	 * @param other Other observer to be merged.
	 */
	public synchronized void merge(final Observer other) {
		Objects.requireNonNull(other);
		other.stageMeasurements.forEach((stage, stageMeasurements) -> {
			this.stageMeasurements.compute(stage, (stageKey, oldStageMeasurements) -> {
				if (oldStageMeasurements == null)
					return stageMeasurements;

				stageMeasurements.forEach((entry, measurement) -> {
					oldStageMeasurements.compute(entry, (entryKey, oldMeasurement) -> {
						return oldMeasurement == null ? measurement : oldMeasurement.merge(measurement);
					});
				});

				return oldStageMeasurements;
			});
		});
	}

	public Map<ObservableStage, Map<String, IMeasurement>> getAllMeasurements() {
		return stageMeasurements;
	}

	public Map<String, IMeasurement> getStageMeasurements(ObservableStage stage) {
		return stageMeasurements.getOrDefault(stage, Collections.emptyMap());
	}

	public Map<String, IMeasurement> mergeAllStages() {
		var result = new HashMap<String, IMeasurement>();
		for (var key : ObservableStage.values()) {
			var stage = stageMeasurements.get(key);
			if (stage != null)
				result.putAll(stage);
		}
		return result;
	}

	/**
	 * Measures the execution of {@code function} and stores the result under the
	 * {@code entry} key. Any measurement previously created under the same key will
	 * be overwritten. See {@link SingleMeasurement#start()} for details about what
	 * is measured.
	 * 
	 * @param <T>      return type
	 * @param entry    under which the measurement is saved
	 * @param function to be measured
	 * @return the return value of {@code function}
	 * @see SingleMeasurement
	 */
	public <T> T singleMeasurement(ObservableStage stage, String entry, Supplier<T> function) {
		SingleMeasurement measurement = new SingleMeasurement();
		measurement.start();

		try {
			return function.get();
		} finally {
			measurement.stop();
			saveSingleMeasurement(stage, entry, measurement);
		}
	}

	/**
	 * Measures the execution of {@code function} and stores the result under the
	 * {@code entry} key. Any measurement previously created under the same key will
	 * be overwritten. See {@link SingleMeasurement#start()} for details about what
	 * is measured.
	 * 
	 * @param entry    under which the measurement is saved
	 * @param function to be measured
	 * @see SingleMeasurement
	 */
	public void singleMeasurement(ObservableStage stage, String entry, Runnable function) {
		SingleMeasurement measurement = new SingleMeasurement();
		measurement.start();

		try {
			function.run();
		} finally {
			measurement.stop();
			saveSingleMeasurement(stage, entry, measurement);
		}
	}

	public <T> T multiMeasurement(ObservableStage stage, String entry, Supplier<T> function) {
		SingleMeasurement measurement = new SingleMeasurement();
		measurement.start();

		try {
			return function.get();
		} finally {
			measurement.stop();
			saveMultiMeasurement(stage, entry, measurement);
		}
	}

	public void multiMeasurement(ObservableStage stage, String entry, Runnable function) {
		SingleMeasurement measurement = new SingleMeasurement();
		measurement.start();

		try {
			function.run();
		} finally {
			measurement.stop();
			saveMultiMeasurement(stage, entry, measurement);
		}
	}

	private void saveSingleMeasurement(ObservableStage phase, String name, IMeasurement measurement) {
		Map<String, IMeasurement> measurements = stageMeasurements.computeIfAbsent(phase,
				k -> new ConcurrentHashMap<>());

		measurements.put(name, measurement);
	}

	private void saveMultiMeasurement(ObservableStage phase, String name, IMeasurement measurement) {
		Map<String, IMeasurement> measurements = stageMeasurements.computeIfAbsent(phase,
				k -> new ConcurrentHashMap<>());

		measurements.compute(name, (key, old) -> old == null ? measurement : old.merge(measurement));
	}

	public Observer clone() {
		return new Observer(stageMeasurements);
	}

}
