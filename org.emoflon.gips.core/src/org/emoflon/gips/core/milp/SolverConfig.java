package org.emoflon.gips.core.milp;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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

	/**
	 * Enables/Disables {@link #notifyListeners()} method
	 */
	private boolean enableNotifier = true;
	private boolean aValueChanged = false;

	private boolean timeLimitEnabled;
	private double timeLimit;
	private boolean timeLimitIncludeInitTime;
	private boolean randomSeedEnabled;
	private int randomSeed;
	private SolverPresolve presolve;
	private boolean enableOutput;
	private boolean enableTolerance;
	private double tolerance;
	private boolean lpOutput;
	private String lpPath;
	private boolean threadCountEnabled;
	private int threadCount;

	/**
	 * To be called after any changes have been made via the setters. This method
	 * will notify listeners if any config setting has changed since it's last call.
	 */
	private void notifyListeners() {
		if (enableNotifier && aValueChanged) {
			for (var listener : this.listeners)
				listener.onChange();
			aValueChanged = false;
		}
	}

	/**
	 * Allows multiple setters to be called without triggering all listeners for
	 * each setter. This method will notify all listeners afterwards.
	 * 
	 * @param batch
	 */
	public void batchSetter(Consumer<SolverConfig> batch) {
		enableNotifier = false;
		batch.accept(this);
		enableNotifier = true;
		notifyListeners();
	}

	public boolean isTimeLimitEnabled() {
		return timeLimitEnabled;
	}

	public void setEnableTimeLimit(boolean newValue) {
		var oldValue = this.timeLimitEnabled;
		this.timeLimitEnabled = newValue;

		aValueChanged |= oldValue != newValue;
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

		aValueChanged |= oldValue != newValue;
		notifyListeners();
	}

	public boolean isTimeLimitIncludeInitTime() {
		return timeLimitIncludeInitTime;
	}

	public void setTimeLimitIncludeInitTime(boolean newValue) {
		var oldValue = this.timeLimitIncludeInitTime;
		this.timeLimitIncludeInitTime = newValue;

		aValueChanged |= oldValue != newValue;
		notifyListeners();
	}

	public boolean isRandomSeedEnabled() {
		return randomSeedEnabled;
	}

	public void setEnabledRandomSeed(boolean newValue) {
		var oldValue = this.randomSeedEnabled;
		this.randomSeedEnabled = newValue;

		aValueChanged |= oldValue != newValue;
		notifyListeners();
	}

	public int getRandomSeed() {
		return randomSeed;
	}

	public void setRandomSeed(int newValue) {
		var oldValue = this.randomSeed;
		this.randomSeed = newValue;

		aValueChanged |= oldValue != newValue;
		notifyListeners();
	}

	public boolean isEnablePresolve() {
		return !presolve.equals(SolverPresolve.NONE);
	}

	public void setPresolveAuto() {
		setPresolve(SolverPresolve.AUTO);
	}

	public void setPresolveNone() {
		setPresolve(SolverPresolve.NONE);
	}

	public void setPresolve(final SolverPresolve newValue) {
		if (newValue.equals(SolverPresolve.NOT_CONFIGURED)) {
			setPresolve(SolverPresolve.AUTO);
		} else {
			var oldValue = this.presolve;
			this.presolve = newValue;

			aValueChanged |= oldValue != newValue;
			notifyListeners();
		}
	}

	public void setPresolve(final org.emoflon.gips.intermediate.GipsIntermediate.SolverPresolve solverPresolve) {
		setPresolve(convertPresolve(solverPresolve));
	}

	public SolverPresolve convertPresolve(
			final org.emoflon.gips.intermediate.GipsIntermediate.SolverPresolve intermediatePresolve) {
		switch (intermediatePresolve) {
		case NOT_CONFIGURED:
			return SolverPresolve.NOT_CONFIGURED;
		case AUTO:
			return SolverPresolve.AUTO;
		case NONE:
			return SolverPresolve.NONE;
		case CONSERVATIVE:
			return SolverPresolve.CONSERVATIVE;
		case AGGRESSIVE:
			return SolverPresolve.AGGRESSIVE;
		default:
			throw new IllegalArgumentException();
		}
	}

	public SolverPresolve getPresolve() {
		return presolve;
	}

	public boolean isEnableOutput() {
		return enableOutput;
	}

	public void setEnableOutput(boolean newValue) {
		var oldValue = this.enableOutput;
		this.enableOutput = newValue;

		aValueChanged |= oldValue != newValue;
		notifyListeners();
	}

	public boolean isEnableTolerance() {
		return enableTolerance;
	}

	public void setEnableTolerance(boolean newValue) {
		var oldValue = this.enableTolerance;
		this.enableTolerance = newValue;

		aValueChanged |= oldValue != newValue;
		notifyListeners();
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double newValue) {
		var oldValue = this.tolerance;
		this.tolerance = newValue;

		aValueChanged |= oldValue != newValue;
		notifyListeners();
	}

	public boolean isEnableLpOutput() {
		return lpOutput;
	}

	public void setEnableLpOutput(boolean newValue) {
		var oldValue = this.lpOutput;
		this.lpOutput = newValue;

		aValueChanged |= oldValue != newValue;
		notifyListeners();
	}

	public String getLpPath() {
		return lpPath;
	}

	public void setLpPath(String newValue) {
		var oldValue = this.lpPath;
		this.lpPath = newValue;

		aValueChanged |= oldValue != newValue;
		notifyListeners();
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(final int newValue) {
		if (newValue < 0)
			throw new IllegalArgumentException("Given number of MILP solver threads is smaller than 0.");

		var oldValue = this.threadCount;
		this.threadCount = newValue;

		aValueChanged |= oldValue != newValue;
		notifyListeners();
	}

	public void setEnableThreadCount(boolean newValue) {
		var oldValue = this.threadCountEnabled;
		this.threadCountEnabled = newValue;

		aValueChanged |= oldValue != newValue;
		notifyListeners();
	}

	public boolean isEnableThreadCount() {
		return threadCountEnabled;
	}

	public enum SolverPresolve {
		AUTO, NONE, CONSERVATIVE, AGGRESSIVE, NOT_CONFIGURED;
	}

}
