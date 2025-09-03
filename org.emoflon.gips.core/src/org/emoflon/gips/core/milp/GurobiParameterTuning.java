package org.emoflon.gips.core.milp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.emoflon.gips.core.util.FileUtils;

import com.google.gson.JsonObject;
import com.gurobi.gurobi.GRBException;
import com.gurobi.gurobi.GRBModel;

/**
 * Gurobi parameter tuning tool that can read a JSON file from the given path
 * and apply the configured parameters to the given Gurobi model object.
 * 
 * JSON file format: { "intparams": [ ["OutputFlag", "1"], ["TimeLimit", "30"]
 * ], "doubleparams": [ ["NoRelHeurWork", "60"] ] }
 *
 * @author Maximilian Kratz (maximilian.kratz@es.tu-darmstadt.de)
 */
public class GurobiParameterTuning {

	/**
	 * Only instance of this class.
	 */
	private static GurobiParameterTuning instance;

	/**
	 * Private constructor since this class should only be used via its singleton
	 * pattern.
	 */
	private GurobiParameterTuning() {
	}

	/**
	 * Returns the singleton instance of this class. If it was not initialized
	 * before, initialize it.
	 * 
	 * @return Singleton instance of this class.
	 */
	public static GurobiParameterTuning getInstance() {
		if (instance == null) {
			instance = new GurobiParameterTuning();
		}
		return instance;
	}

	/**
	 * Loads the JSON parameter file from the given parameters file path and applies
	 * all parameters to the given Gurobi model. If there is no file present at the
	 * given path, the method will simply return. If a specific parameter could not
	 * be set up with Gurobi, the method will print a warning to the console and
	 * continue.
	 * 
	 * @param model              Gurobi model to set parameters for.
	 * @param parametersFilePath Parameters file path to load the JSON file from.
	 */
	public void setParameters(final GRBModel model, final String parametersFilePath) {
		Objects.requireNonNull(model);
		Objects.requireNonNull(parametersFilePath);

		if (!probeFile(parametersFilePath)) {
			return;
		}

		final Map<String, String> params = loadFile(parametersFilePath);

		params.forEach((key, value) -> {
			try {
				model.set(key, value);
			} catch (final GRBException e) {
				System.out.println("Gurobi parameter tuning: " + "Setup of parameter <" + key + "> with value <" + value
						+ "> failed." + "Continuing.");
			}
		});
	}

	/**
	 * Returns true if there is a file present on the given parameters file path.
	 * 
	 * @param parametersFilePath File path to check file existence for.
	 * @return Returns true if there is a file present on the given parameters file
	 *         path.
	 */
	private boolean probeFile(final String parametersFilePath) {
		Objects.requireNonNull(parametersFilePath);
		return FileUtils.checkIfFileExists(parametersFilePath);
	}

	/**
	 * Loads the JSON file from the given parameters file path and reads its integer
	 * and double parameters for Gurobi.
	 * 
	 * @param parametersFilePath Parameters file path to load the file from.
	 * @return Loaded Gurobi parameters.
	 */
	private Map<String, String> loadFile(final String parametersFilePath) {
		Objects.requireNonNull(parametersFilePath);
		final JsonObject json = FileUtils.readFileToJson(parametersFilePath);

		final Map<String, String> parameters = new HashMap<String, String>();

		// Integer parameters
		if (json.has("intparams")) {
			final var intparamsJson = json.getAsJsonArray("intparams");

			intparamsJson.asList().forEach(intparam -> {
				final String key = intparam.getAsJsonArray().get(0).getAsString();
				final String value = intparam.getAsJsonArray().get(1).getAsString();
				parameters.put(key, value);
			});
		}

		// Double parameters
		if (json.has("doubleparams")) {
			final var doubleparamsJson = json.getAsJsonArray("doubleparams");

			doubleparamsJson.asList().forEach(doubleparam -> {
				final String key = doubleparam.getAsJsonArray().get(0).getAsString();
				final String value = doubleparam.getAsJsonArray().get(1).getAsString();
				parameters.put(key, value);
			});
		}

		return parameters;
	}

}
