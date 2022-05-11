package org.emoflon.gips.core.ilp;

public enum ILPSolverStatus {
	UNBOUNDED("Unbounded"), INF_OR_UNBD("Infeasible or Unbounded"), INFEASIBLE("Infeasible"),
	OPTIMAL("Solved and Optimal"), TIME_OUT("Time out");

	public final String name;

	private ILPSolverStatus(final String name) {
		this.name = name;
	}
}
