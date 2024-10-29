package org.emoflon.gips.core.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Observer {

	private static Observer instance;
	protected Map<String, Map<String, Measurement>> measurements = Collections.synchronizedMap(new LinkedHashMap<>());
	private String currentSeries;

	public static synchronized Observer getInstance() {
		if (instance == null) {
			instance = new Observer();
		}
		return instance;
	}

	public synchronized void setCurrentSeries(final String currentSeries) {
		this.currentSeries = currentSeries;
	}

	public String getCurrentSeries() {
		return currentSeries;
	}

	public Map<String, Measurement> getMeasurements(String series) {
		return measurements.get(series);
	}

	public <T> T observe(final String entry, Supplier<T> function) {
		return observe(currentSeries, entry, function);
	}

	public void observe(final String entry, Runnable function) {
		observe(currentSeries, entry, function);
	}

	public <T> T observe(final String series, final String entry, Supplier<T> function) {
		Measurement m = new Measurement();
		m.start();
		T result = function.get();
		m.stop();
		addMeasurement(series, entry, m);
		return result;
	}

	public void observe(final String series, final String entry, Runnable function) {
		Measurement m = new Measurement();
		m.start();
		function.run();
		m.stop();
		addMeasurement(series, entry, m);
	}

	protected void addMeasurement(final String series, final String entry, Measurement m) {
		Map<String, Measurement> mSeries = measurements.get(series);
		if (mSeries == null) {
			mSeries = Collections.synchronizedMap(new LinkedHashMap<>());
			measurements.put(series, mSeries);
		}
		mSeries.put(entry, m);
	}
}
