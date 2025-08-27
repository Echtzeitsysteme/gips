package org.emoflon.gips.core.milp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.emoflon.gips.core.util.FileUtils;

import com.google.gson.JsonObject;
import com.gurobi.gurobi.GRBException;
import com.gurobi.gurobi.GRBModel;

public class GurobiParameterTuning {

	private static GurobiParameterTuning instance;

	private GurobiParameterTuning() {
	}

	public static GurobiParameterTuning getInstance() {
		if (instance == null) {
			instance = new GurobiParameterTuning();
		}
		return instance;
	}

	public void setParameters(final GRBModel model, final String parametersFilePath) {
		Objects.requireNonNull(model);
		Objects.requireNonNull(parametersFilePath);

		if (!probeFile(parametersFilePath)) {
			return;
		}

		final Params params = loadFile(parametersFilePath);

		// Integer parameters
		params.getIntParams().keySet().forEach(key -> {
			try {
				model.set(key, params.getIntParams().get(key).toString());
			} catch (final GRBException e) {
				e.printStackTrace();
			}
		});

		// Double parameters
		params.getDoubleParams().keySet().forEach(key -> {
			try {
				model.set(key, params.getDoubleParams().get(key).toString());
			} catch (final GRBException e) {
				e.printStackTrace();
			}
		});
	}

	private boolean probeFile(final String parametersFilePath) {
		Objects.requireNonNull(parametersFilePath);
		return FileUtils.checkIfFileExists(parametersFilePath);
	}

	private Params loadFile(final String parametersFilePath) {
		Objects.requireNonNull(parametersFilePath);
		final JsonObject json = FileUtils.readFileToJson(parametersFilePath);

		final Params params = new Params();

		// Integer parameters
		if (json.has("intparams")) {
			final var intparamsJson = json.getAsJsonArray("intparams");

			intparamsJson.asList().forEach(intparam -> {
				final String key = intparam.getAsJsonArray().get(0).getAsString();
				final int value = Integer.valueOf(intparam.getAsJsonArray().get(1).getAsString());
				params.addIntParam(key, value);
			});
		}

		// Double parameters
		if (json.has("doubleparams")) {
			final var doubleparamsJson = json.getAsJsonArray("doubleparams");

			doubleparamsJson.asList().forEach(doubleparam -> {
				final String key = doubleparam.getAsJsonArray().get(0).getAsString();
				final double value = Double.valueOf(doubleparam.getAsJsonArray().get(1).getAsString());
				params.addDoubleParam(key, value);
			});
		}

		return params;
	}

	private class Params {
		final Map<String, Integer> intparams = new HashMap<String, Integer>();
		final Map<String, Double> doubleparams = new HashMap<String, Double>();

		public void addIntParam(final String key, final int value) {
			Objects.requireNonNull(key);
			intparams.put(key, value);
		}

		public void addDoubleParam(final String key, final double value) {
			Objects.requireNonNull(key);
			doubleparams.put(key, value);
		}

		public Map<String, Integer> getIntParams() {
			return intparams;
		}

		public Map<String, Double> getDoubleParams() {
			return doubleparams;
		}
	}

}
