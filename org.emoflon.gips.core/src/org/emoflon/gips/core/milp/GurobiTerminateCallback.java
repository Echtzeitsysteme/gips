package org.emoflon.gips.core.milp;

import java.util.Objects;

import org.emoflon.gips.core.util.FileUtils;

import com.google.gson.JsonObject;
import com.gurobi.gurobi.GRB;
import com.gurobi.gurobi.GRBCallback;
import com.gurobi.gurobi.GRBException;

/**
 * This class is a custom Gurobi callback that reads a JSON file from a
 * pre-defined path and, based on the contents of this file, determines, if the
 * Gurobi optimization process should be cancelled.
 * 
 * JSON file format: { "abort": false, "bestBoundGeq": true, "bestBound":
 * 1000000, "bestObjectiveLeq": true, "bestObjective": -1, "gap": 0.8 }
 */
public class GurobiTerminateCallback extends GRBCallback {

	/**
	 * File path of the JSON callback file.
	 */
	private final String callbackFilePath;

	/*
	 * Best bound.
	 */
	private double bestBoundLimit = -1;
	private boolean bestBoundGeq = true;
	private boolean bestBoundEnabled = false;

	/*
	 * Best objective.
	 */
	private double bestObjectiveLimit = -1;
	private boolean bestObjectiveLeq = true;
	private boolean bestObjectiveEnabled = false;

	/*
	 * Abort regardless of state.
	 */
	private boolean abortCall = false;

	/*
	 * MIP gap.
	 */
	private double gapLimit = 0;
	private boolean gapEnabled = false;

	/**
	 * Creates a new instance of the custom callback. The given file path will be
	 * used to check for a JSON file.
	 * 
	 * @param callbackFilePath Callback file path.
	 */
	public GurobiTerminateCallback(final String callbackFilePath) {
		Objects.requireNonNull(callbackFilePath);
		this.callbackFilePath = callbackFilePath;
	}

	@Override
	protected void callback() {
		// If there is not callback file available, simply return
		if (!probeFile()) {
			return;
		}

		// Load parameters from file
		loadFile();

		// Check if optimization should be aborted
		if (abortCall) {
			abort();
		}

		// All other abortion calls will be handled in MIP solve phase
		if (where == GRB.CB_MIPSOL) {
			// Check if best bound should trigger the abortion
			if (bestBoundEnabled) {
				try {
					final double bestBound = getDoubleInfo(GRB.CB_MIPSOL_OBJBND);

					if (bestBoundGeq) {
						if (bestBound >= this.bestBoundLimit) {
							abort();
						}
					} else {
						if (bestBound <= this.bestBoundLimit) {
							abort();
						}
					}
				} catch (final GRBException e) {
					e.printStackTrace();
					// TODO
				}
			}

			// Check if best objective should trigger the abortion
			if (bestObjectiveEnabled) {
				try {
					final double bestObj = getDoubleInfo(GRB.CB_MIPSOL_OBJBST);

					if (bestObjectiveLeq) {
						if (bestObj <= this.bestObjectiveLimit) {
							abort();
						}
					} else {
						if (bestObj >= this.bestObjectiveLimit) {
							abort();
						}
					}
				} catch (final GRBException e) {
					e.printStackTrace();
					// TODO
				}
			}

			// Check if gap should trigger the abortion
			if (gapEnabled) {
				try {
					// Calculate the MIP gap
					// Reference:
					// https://support.gurobi.com/hc/en-us/articles/8265539575953-What-is-the-MIPGap
					final double bestBound = getDoubleInfo(GRB.CB_MIPSOL_OBJBND);
					final double bestObj = getDoubleInfo(GRB.CB_MIPSOL_OBJBST);
					final double gap = Math.abs(bestObj - bestBound) / Math.abs(bestObj);

					if (gap <= gapLimit) {
						abort();
					}
				} catch (final GRBException e) {
					e.printStackTrace();
					// TODO
				}
			}
		}

	}

	/**
	 * Returns true if the configured file path points to a file.
	 * 
	 * @return True if the configured file path points to a file.
	 */
	private boolean probeFile() {
		return FileUtils.checkIfFileExists(callbackFilePath);
	}

	/**
	 * Loads the JSON file from the configured file path and sets the parameters of
	 * this callback accordingly.
	 */
	private void loadFile() {
		final JsonObject json = FileUtils.readFileToJson(this.callbackFilePath);

		// Abort
		if (json.has("abort")) {
			this.abortCall = json.getAsJsonPrimitive("abort").getAsBoolean();
		}

		// Best bound
		if (json.has("bestBoundGeq")) {
			this.bestBoundGeq = json.getAsJsonPrimitive("bestBoundGeq").getAsBoolean();
		}
		if (json.has("bestBound")) {
			this.bestBoundLimit = json.getAsJsonPrimitive("bestBound").getAsDouble();
			this.bestBoundEnabled = true;
		}

		// Best objective
		if (json.has("bestObjectiveLeq")) {
			this.bestObjectiveLeq = json.getAsJsonPrimitive("bestObjectiveLeq").getAsBoolean();
		}
		if (json.has("bestObjective")) {
			this.bestObjectiveLimit = json.getAsJsonPrimitive("bestObjective").getAsDouble();
			this.bestObjectiveEnabled = true;
		}

		// Gap
		if (json.has("gap")) {
			this.gapLimit = json.getAsJsonPrimitive("gap").getAsDouble();
			this.gapEnabled = true;
		}
	}

}
