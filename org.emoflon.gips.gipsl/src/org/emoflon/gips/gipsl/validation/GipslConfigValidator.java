package org.emoflon.gips.gipsl.validation;

import java.util.Objects;

import org.emoflon.gips.gipsl.gipsl.GipsConfig;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.gipsl.PresolveType;
import org.emoflon.gips.gipsl.gipsl.SolverType;

public class GipslConfigValidator {

	private GipslConfigValidator() {
	}

	public static void validateConfig(final GipsConfig config) {
		if (GipslValidator.DISABLE_VALIDATOR) {
			return;
		}

		if (config == null) {
			return;
		}

		// Check all contents for presence
		if (config.getSolver() == null) {
			GipslValidator.err("You have to specify an (M)ILP solver.", GipslPackage.Literals.GIPS_CONFIG__SOLVER);
		}
		// ^this might be obsolete

		// If solver is Gurobi and main is set, a home path and a license path must be
		// specified
		if (config.getSolver() == SolverType.GUROBI && config.isEnableLaunchConfig()
				&& (config.getHome() == null || config.getLicense() == null)) {
			GipslValidator.err(
					"You have to specify a home folder and a license file to generate a launch config for Gurobi.",
					GipslPackage.Literals.GIPS_CONFIG__SOLVER);
		}

		// (M)ILP solver's home folder must not be empty (if set)
		if (config.getHome() != null && (config.getHome().isBlank() || config.getHome().equals("\"\""))) {
			GipslValidator.err("Home folder path must not be blank if set.", GipslPackage.Literals.GIPS_CONFIG__HOME);
		}

		// (M)ILP solver's license path must not be empty (if set)
		if (config.getLicense() != null && (config.getLicense().isBlank() || config.getLicense().equals("\"\""))) {
			GipslValidator.err("License file path must not be blank if set.",
					GipslPackage.Literals.GIPS_CONFIG__LICENSE);
		}

		// Main path must not be empty (if enabled)
		if (config.isEnableLaunchConfig() && config.getMainLoc() != null
				&& (config.getMainLoc().isBlank() || config.getMainLoc().equals("\"\""))) {
			GipslValidator.err("Launch config path must not be blank if enabled.",
					GipslPackage.Literals.GIPS_CONFIG__MAIN_LOC);
		}

		// Time limit
		if (config.getTimeLimit() < 0) {
			GipslValidator.err("Time limit must be >= 0.0", GipslPackage.Literals.GIPS_CONFIG__TIME_LIMIT);
		}

		// Random seed
		if (config.getRndSeed() < 0) {
			GipslValidator.err("Random seed must be >= 0.", GipslPackage.Literals.GIPS_CONFIG__RND_SEED);
		} else if (config.getRndSeed() > Integer.MAX_VALUE) {
			GipslValidator.err("Random seed must be <= Integer.MAX_VALUE.",
					GipslPackage.Literals.GIPS_CONFIG__RND_SEED);
		}

		// Tolerance
		if (config.isEnableTolerance() && config.getTolerance() < 1e-9) {
			GipslValidator.err("Tolerance value must be >= 1e-9", GipslPackage.Literals.GIPS_CONFIG__TOLERANCE);
		} else if (config.isEnableTolerance() && config.getTolerance() > 1e-2) {
			GipslValidator.err("Tolerance value must be <= 1e-2", GipslPackage.Literals.GIPS_CONFIG__TOLERANCE);
		}

		// Special case: If solver is GLPK and pre-solving is disabled, generate a
		// warning
		if (config.getSolver() == SolverType.GLPK
				&& (!Objects.nonNull(config.getPresolve()) || config.getPresolve().equals(PresolveType.NONE))) {
			GipslValidator.warn(
					"GLPK needs enabled pre-solving for some problems. "
							+ "It is highly reccommend to enable pre-solving if using the GLPK solver in GIPS.",
					GipslPackage.Literals.GIPS_CONFIG__SOLVER);
		}

		// Presolve cases:
		// GLPK and CPLEX only support "presolve = on" and "presolve = off"
		if (config.getSolver() == SolverType.GLPK && Objects.nonNull(config.getPresolve())
				&& !config.getPresolve().equals(PresolveType.NONE)) {
			GipslValidator.warn("GLPK interprets every presolving setting but 'NONE' as 'enabled'.",
					GipslPackage.Literals.GIPS_CONFIG__PRESOLVE);
		}
		if (config.getSolver() == SolverType.CPLEX && Objects.nonNull(config.getPresolve())
				&& !config.getPresolve().equals(PresolveType.NONE)) {
			GipslValidator.warn("CPLEX interprets every presolving setting but 'NONE' as 'enabled'.",
					GipslPackage.Literals.GIPS_CONFIG__PRESOLVE);
		}

		// Callback cases:
		// All solvers but Gurobi do not respect the callback setting(s). Hence,
		// generate a warning.
		if (config.getSolver() != SolverType.GUROBI && config.isEnableSolverCallback()) {
			GipslValidator.warn("All solvers but Gurobi do not support the custom callback setting.",
					GipslPackage.Literals.GIPS_CONFIG__ENABLE_SOLVER_CALLBACK);
		}
	}

}
