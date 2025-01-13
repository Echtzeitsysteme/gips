package org.emoflon.gips.core.milp;

import org.emoflon.gips.core.validation.GipsConstraintValidationLog;

public record SolverOutput(SolverStatus status, double objectiveValue, GipsConstraintValidationLog validationLog,
		int solutionCount, ProblemStatistics stats) {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ILP problem status <");
		sb.append(status.name);
		sb.append(">\n Objective function value:  ");
		sb.append(objectiveValue);
		sb.append("\n Pre-solve validation log:\n");
		sb.append(validationLog.toString());
		return sb.toString();
	}

}
