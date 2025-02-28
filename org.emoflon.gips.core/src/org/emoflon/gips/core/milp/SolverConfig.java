package org.emoflon.gips.core.milp;

import java.util.ArrayList;
import java.util.List;

/**
 * Mutable container to hold some config specific properties. Provides basic
 * listener support to notify listeners about 'something changed'
 * 
 * @see SolverConfigMap
 */
public class SolverConfig {

	public static interface ConfigChangeListener {
		public void onChange();
	}

	// basic listener support
	private List<ConfigChangeListener> listeners = new ArrayList<>(10);

	private boolean timeLimitEnabled;
	private double timeLimit;
	private boolean timeLimitIncludeInitTime;
	private boolean rndSeedEnabled;
	private int randomSeed;
	private boolean enablePresolve;
	private boolean enableOutput;
	private boolean enableTolerance;
	private double tolerance;
	private boolean lpOutput;
	private String lpPath;
	private boolean threadCountEnabled;
	private int threadCount;

	private void notifyListeners() {
		for (var listener : this.listeners)
			listener.onChange();
	}

	public boolean isTimeLimitEnabled() {
		return timeLimitEnabled;
	}

	public void setEnableTimeLimit(boolean timeLimitEnabled) {
		this.timeLimitEnabled = timeLimitEnabled;
		notifyListeners();
	}

	public double getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(double newValue) {
		if (newValue <= 0)
			throw new IllegalArgumentException("Given new time limit is smaller or equal to 0.");

		var oldValue = this.timeLimit;
		this.timeLimit = newValue;
		if (oldValue != newValue) // if(this.timeLimit != (this.timeLimit = newValue))
			notifyListeners();
	}

	public boolean isTimeLimitIncludeInitTime() {
		return timeLimitIncludeInitTime;
	}

	public void setTimeLimitIncludeInitTime(boolean newValue) {
		var oldValue = this.timeLimitIncludeInitTime;
		this.timeLimitIncludeInitTime = newValue;
		if (oldValue != newValue)
			notifyListeners();
	}

	public boolean isRndSeedEnabled() {
		return rndSeedEnabled;
	}

	public void setEnabledRandomSeed(boolean newValue) {
		var oldValue = this.rndSeedEnabled;
		this.rndSeedEnabled = newValue;
		if (oldValue != newValue)
			notifyListeners();
	}

	public int getRandomSeed() {
		return randomSeed;
	}

	public void setRandomSeed(int newValue) {
		var oldValue = this.randomSeed;
		this.randomSeed = newValue;
		if (oldValue != newValue)
			notifyListeners();
	}

	public boolean isEnablePresolve() {
		return enablePresolve;
	}

	public void setEnablePresolve(boolean newValue) {
		var oldValue = this.enablePresolve;
		this.enablePresolve = newValue;
		if (oldValue != newValue)
			notifyListeners();
	}

	public boolean isEnableOutput() {
		return enableOutput;
	}

	public void setEnableOutput(boolean newValue) {
		var oldValue = this.enableOutput;
		this.enableOutput = newValue;
		if (oldValue != newValue)
			notifyListeners();
	}

	public boolean isEnableTolerance() {
		return enableTolerance;
	}

	public void setEnableTolerance(boolean newValue) {
		var oldValue = this.enableTolerance;
		this.enableTolerance = newValue;
		if (oldValue != newValue)
			notifyListeners();
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double newValue) {
		var oldValue = this.tolerance;
		this.tolerance = newValue;
		if (oldValue != newValue)
			notifyListeners();
	}

	public boolean isLpOutput() {
		return lpOutput;
	}

	public void setLpOutput(boolean newValue) {
		var oldValue = this.lpOutput;
		this.lpOutput = newValue;
		if (oldValue != newValue)
			notifyListeners();
	}

	public String getLpPath() {
		return lpPath;
	}

	public void setLpPath(String newValue) {
		var oldValue = this.lpPath;
		this.lpPath = newValue;
		if (oldValue != newValue)
			notifyListeners();
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int newValue) {
		if (newValue <= 0)
			throw new IllegalArgumentException("Given number of ILP solver threads is smaller or equal to 0.");

		var oldValue = this.threadCount;
		this.threadCount = newValue;
		if (oldValue != newValue)
			notifyListeners();
	}

	public void setEnableThreadCount(boolean newValue) {
		var oldValue = this.threadCountEnabled;
		this.threadCountEnabled = newValue;
		if (oldValue != newValue)
			notifyListeners();
	}

	public boolean isEnableThreadCount() {
		return threadCountEnabled;
	}

}
