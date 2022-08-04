package org.emoflon.gips.core.config;

import org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType;

/**
 * Global GIPS configuration that overrides local configurations in GIPSL files.
 */
public class GipsGlobalConfig {

	private GipsGlobalConfig() {
	}

	/**
	 * If true, the ILP solver type from this class will be used.
	 */
	public static boolean overrideIlpSolver = false;

	/**
	 * ILP solver type to set override to.
	 */
	public static ILPSolverType solverType = ILPSolverType.GUROBI;

}
