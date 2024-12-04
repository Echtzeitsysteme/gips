package org.emoflon.gips.core.util;

import java.util.Collection;
import java.util.List;

public class SingleMeasurement implements IMeasurement {

	private Runtime runtime = Runtime.getRuntime();
	private double begin;
	private double end;
	private long memory;

	public double start() {
		begin = System.nanoTime();
		return begin;
	}

	public double stop() {
		end = System.nanoTime();
		memory = (runtime.totalMemory() - runtime.freeMemory()) / MB;
		return end;
	}

	@Override
	public IMeasurement merge(IMeasurement... measurements) {
		return new MultiMeasurement(this, measurements);
	}

	@Override
	public double maxDurationSeconds() {
		return avgDurationSeconds();
	}

	@Override
	public double minDurationSeconds() {
		return avgDurationSeconds();
	}

	@Override
	public double avgDurationSeconds() {
		return (end - begin) / NANOSECONDS;
	}

	@Override
	public double maxDurationNanoS() {
		return avgDurationNanoS();
	}

	@Override
	public double totalDurationSeconds() {
		return avgDurationSeconds();
	}

	@Override
	public double totalDurationNanoS() {
		return avgDurationNanoS();
	}

	@Override
	public double minDurationNanoS() {
		return avgDurationNanoS();
	}

	@Override
	public double avgDurationNanoS() {
		return end - begin;
	}

	@Override
	public long avgMemoryMB() {
		return memory;
	}

	@Override
	public long minMemoryMB() {
		return avgMemoryMB();
	}

	@Override
	public long maxMemoryMB() {
		return avgMemoryMB();
	}

	@Override
	public Collection<IMeasurement> getMeasurements() {
		return List.of(this);
	}

}
