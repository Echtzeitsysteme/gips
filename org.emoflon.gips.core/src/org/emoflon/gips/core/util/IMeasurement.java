package org.emoflon.gips.core.util;

import java.util.Collection;

public interface IMeasurement {
	final public static double NANOSECONDS = 1E9;
	final public static int MB = 1024 * 1024;

	public double maxDurationSeconds();

	public double minDurationSeconds();

	public double avgDurationSeconds();

	public double totalDurationSeconds();

	public double maxDurationNanoS();

	public double minDurationNanoS();

	public double avgDurationNanoS();

	public double totalDurationNanoS();

	public long avgMemoryMB();

	public long maxMemoryMB();

	public long minMemoryMB();

	public IMeasurement merge(IMeasurement... measurements);

	public Collection<IMeasurement> getMeasurements();
}
