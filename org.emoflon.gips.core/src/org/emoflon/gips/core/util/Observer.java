package org.emoflon.gips.core.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Observer {

	private static ThreadLocal<Observer> instance = ThreadLocal.withInitial(() -> new Observer());
	protected Map<String, Map<String, IMeasurement>> measurements = Collections.synchronizedMap(new LinkedHashMap<>());
	private String currentSeries;

	public static synchronized Observer getInstance() {
		return instance.get();
	}

	public synchronized void setCurrentSeries(final String currentSeries) {
		this.currentSeries = currentSeries;
	}

	public String getCurrentSeries() {
		return currentSeries;
	}

	public Map<String, IMeasurement> getMeasurements(String series) {
		return measurements.get(series);
	}

	public <T> T observe(final String entry, Supplier<T> function) {
		return observe(currentSeries, entry, function);
	}

	public void observe(final String entry, Runnable function) {
		observe(currentSeries, entry, function);
	}

	public <T> T observe(final String series, final String entry, Supplier<T> function) {
		SingleMeasurement m = new SingleMeasurement();
		m.start();
		T result = function.get();
		m.stop();
		addMeasurement(series, entry, m);
		return result;
	}

	public void observe(final String series, final String entry, Runnable function) {
		SingleMeasurement m = new SingleMeasurement();
		m.start();
		function.run();
		m.stop();
		addMeasurement(series, entry, m);
	}

	protected void addMeasurement(final String series, final String entry, IMeasurement m) {
		Map<String, IMeasurement> mSeries = measurements.get(series);
		if (mSeries == null) {
			mSeries = Collections.synchronizedMap(new LinkedHashMap<>());
			measurements.put(series, mSeries);
		}
		mSeries.compute(entry, (key, old) -> old == null ? m : old.merge(m));
	}
}
