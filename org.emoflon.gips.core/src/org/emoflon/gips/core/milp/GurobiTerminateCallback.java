package org.emoflon.gips.core.milp;

import java.util.Objects;

import com.gurobi.gurobi.GRB;
import com.gurobi.gurobi.GRBCallback;

public class GurobiTerminateCallback extends GRBCallback {

	final String callbackFilePath;

	public GurobiTerminateCallback(final String callbackFilePath) {
		Objects.requireNonNull(callbackFilePath);
		this.callbackFilePath = callbackFilePath;
	}

	@Override
	protected void callback() {
		// TODO

		if (where == GRB.CB_MIP) {

			abort();
		}

	}

}
