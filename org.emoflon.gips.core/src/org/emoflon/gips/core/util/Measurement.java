package org.emoflon.gips.core.util;

public class Measurement {
	final public static double NANOSECONDS = 1E9;
	final public static int MB = 1024 * 1024;

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

	public double durationSeconds() {
		return (end - begin) / NANOSECONDS;
	}

	public double durationNanoS() {
		return end - begin;
	}

	public long memoryMB() {
		return memory;
	}
}
