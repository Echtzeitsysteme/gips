package org.emoflon.gips.core.milp;

public enum SolverStatus {
	UNBOUNDED("Unbounded"), INF_OR_UNBD("Infeasible or Unbounded"), INFEASIBLE("Infeasible"),
	OPTIMAL("Solved and Optimal"), TIME_OUT("Time out"), FEASIBLE("Feasible"), ABORT("Aborted");

	public final String name;

	private SolverStatus(final String name) {
		this.name = name;
	}
}
