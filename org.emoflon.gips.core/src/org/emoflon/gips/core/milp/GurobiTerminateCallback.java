package org.emoflon.gips.core.milp;

import java.util.Objects;

import org.emoflon.gips.core.util.FileUtils;

import com.google.gson.JsonObject;
import com.gurobi.gurobi.GRB;
import com.gurobi.gurobi.GRBCallback;
import com.gurobi.gurobi.GRBException;

public class GurobiTerminateCallback extends GRBCallback {

	private final String callbackFilePath;

	private double bestBoundLimit = -1;
	private boolean bestBoundGeq = true;
	private boolean bestBoundEnabled = false;

	private double bestObjectiveLimit = -1;
	private boolean bestObjectiveLeq = true;
	private boolean bestObjectiveEnabled = false;

	private boolean abortCall = false;

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
		}

	}

	private boolean probeFile() {
		return FileUtils.checkIfFileExists(callbackFilePath);
	}

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
	}

}
