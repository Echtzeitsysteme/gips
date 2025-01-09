package org.emoflon.gips.core.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Lists;

public class MultiMeasurement implements IMeasurement {

	protected List<IMeasurement> measurements = Collections.synchronizedList(new LinkedList<>());

	public MultiMeasurement(IMeasurement first, IMeasurement... rest) {
		for (IMeasurement measurement : Lists.asList(first, rest)) {
			switch (measurement) {
			case SingleMeasurement sm -> {
				this.measurements.add(sm);
			}
			case MultiMeasurement mm -> {
				this.measurements.addAll(mm.measurements);
			}
			default -> {
				throw new IllegalArgumentException("Unknown measurement type: " + measurement);
			}
			}
		}
	}

	public MultiMeasurement(IMeasurement... measurements) {
		for (IMeasurement measurement : measurements) {
			switch (measurement) {
			case SingleMeasurement sm -> {
				this.measurements.add(sm);
			}
			case MultiMeasurement mm -> {
				this.measurements.addAll(mm.measurements);
			}
			default -> {
				throw new IllegalArgumentException("Unknown measurement type: " + measurement);
			}
			}
		}
	}

	@Override
	public double avgDurationSeconds() {
		return measurements.stream().map(m -> m.avgDurationSeconds()).reduce(0.0, (sum, m) -> sum + m)
				/ measurements.size();
	}

	@Override
	public double avgDurationNanoS() {
		return measurements.stream().map(m -> m.avgDurationNanoS()).reduce(0.0, (sum, m) -> sum + m)
				/ measurements.size();
	}

	@Override
	public long avgMemoryMB() {
		return measurements.stream().map(m -> m.avgMemoryMB()).reduce(0l, (sum, m) -> sum + m) / measurements.size();
	}

	@Override
	public synchronized IMeasurement merge(IMeasurement... measurements) {
		return new MultiMeasurement(this, measurements);
	}

	@Override
	public double maxDurationSeconds() {
		return measurements.stream().map(m -> m.avgDurationSeconds()).max(Comparator.naturalOrder()).get();
	}

	@Override
	public double minDurationSeconds() {
		return measurements.stream().map(m -> m.avgDurationSeconds()).min(Comparator.naturalOrder()).get();
	}

	@Override
	public double maxDurationNanoS() {
		return measurements.stream().map(m -> m.avgDurationNanoS()).max(Comparator.naturalOrder()).get();
	}

	@Override
	public double minDurationNanoS() {
		return measurements.stream().map(m -> m.avgDurationNanoS()).min(Comparator.naturalOrder()).get();
	}

	@Override
	public long maxMemoryMB() {
		return measurements.stream().map(m -> m.avgMemoryMB()).max(Comparator.naturalOrder()).get();
	}

	@Override
	public long minMemoryMB() {
		return measurements.stream().map(m -> m.avgMemoryMB()).min(Comparator.naturalOrder()).get();
	}

	@Override
	public Collection<IMeasurement> getMeasurements() {
		return measurements;
	}

	@Override
	public double totalDurationSeconds() {
		return measurements.stream().map(m -> m.avgDurationSeconds()).reduce(0.0, (sum, m) -> sum + m);
	}

	@Override
	public double totalDurationNanoS() {
		return measurements.stream().map(m -> m.avgDurationNanoS()).reduce(0.0, (sum, m) -> sum + m);
	}

}
