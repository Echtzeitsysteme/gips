package org.emoflon.gips.core.ilp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsGlobalConstraint;
import org.emoflon.gips.core.GipsGlobalObjective;
import org.emoflon.gips.core.GipsMapper;
import org.emoflon.gips.core.GipsMapping;
import org.emoflon.gips.core.GipsMappingConstraint;
import org.emoflon.gips.core.GipsTypeConstraint;
import org.emoflon.gips.core.gt.GipsPatternConstraint;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPSolver.ResultStatus;
import com.google.ortools.linearsolver.MPVariable;

public class OrSolver extends ILPSolver {

	private final MPSolver model;
	private MPObjective obj;

	private final Map<String, MPVariable> vars;

	public OrSolver(final GipsEngine engine, final ILPSolverConfig config) {
		super(engine);
		vars = new HashMap<>();

		model = MPSolver.createSolver("SCIP");
		if (model == null) {
			throw new RuntimeException("Could not create solver SCIP.");
		}
		this.obj = model.objective();

		if (config.timeLimitEnabled()) {
			model.setTimeLimit((long) (config.timeLimit() * 1000));
		}
		if (config.enableOutput()) {
			model.enableOutput();
		}

		// TODO: Tolerance
		// TODO: Presolve
	}

	@Override
	public ILPSolverOutput solve() {
		final ResultStatus resultStatus = model.solve();

		ILPSolverStatus status = null;
		if (resultStatus == ResultStatus.OPTIMAL) {
			status = ILPSolverStatus.OPTIMAL;
		} else if (resultStatus == ResultStatus.INFEASIBLE) {
			status = ILPSolverStatus.INFEASIBLE;
		} else if (resultStatus == ResultStatus.UNBOUNDED) {
			status = ILPSolverStatus.UNBOUNDED;
		} else if (resultStatus == ResultStatus.NOT_SOLVED) {
			status = ILPSolverStatus.INF_OR_UNBD;
		} else if (resultStatus == ResultStatus.FEASIBLE) {
			status = ILPSolverStatus.TIME_OUT;
		}

		return new ILPSolverOutput(status, obj.value(), engine.getValidationLog());
	}

	@Override
	public void updateValuesFromSolution() {
		// Iterate over all mappers
		for (final String key : engine.getMappers().keySet()) {
			final GipsMapper<?> mapper = engine.getMapper(key);
			// Iterate over all mappings of each mapper
			for (final String k : mapper.getMappings().keySet()) {
				// Get corresponding ILP variable name
				final String varName = mapper.getMapping(k).ilpVariable;
				double result = vars.get(varName).solutionValue();
				// Save result value in specific mapping
				mapper.getMapping(k).setValue((int) result);
			}
		}
	}

	@Override
	protected void translateMapping(final GipsMapping mapping) {
		vars.put(mapping.ilpVariable, model.makeBoolVar(mapping.ilpVariable));
	}

	@Override
	protected void translateConstraint(final GipsMappingConstraint<?, ? extends EObject> constraint) {
		addIlpConstraintsToOr(constraint.getConstraints(), constraint.getName());
	}

	@Override
	protected void translateConstraint(final GipsPatternConstraint<?, ?, ?> constraint) {
		addIlpConstraintsToOr(constraint.getConstraints(), constraint.getName());
	}

	@Override
	protected void translateConstraint(final GipsTypeConstraint<?, ? extends EObject> constraint) {
		addIlpConstraintsToOr(constraint.getConstraints(), constraint.getName());
	}

	@Override
	protected void translateConstraint(final GipsGlobalConstraint<?> constraint) {
		addIlpConstraintsToOr(constraint.getConstraints(), constraint.getName());
	}

	@Override
	protected void translateObjective(final GipsGlobalObjective objective) {
		final ILPNestedLinearFunction<?> nestFunc = objective.getObjectiveFunction();
		double constantSum = 0;

		// Add all constants
		for (final ILPConstant<Double> c : nestFunc.constants()) {
			constantSum += c.weight();
		}

		// For each linear function
		for (final ILPWeightedLinearFunction<?> lf : nestFunc.linearFunctions()) {
			// Linear function contains terms
			lf.linearFunction().terms().forEach(t -> {
				final MPVariable var = vars.get(t.variable().getName());
				final double prevCoef = obj.getCoefficient(var);

				obj.setCoefficient(var, prevCoef + (t.weight() * lf.weight()));
			});

			// Linear function contains constant terms
			for (final ILPConstant<Double> c : lf.linearFunction().constantTerms()) {
				constantSum += (c.weight() * lf.weight());
			}
		}

		obj.setOffset(constantSum);

		// Set goal
		switch (nestFunc.goal()) {
		case MAX -> {
			obj.setMaximization();
		}
		case MIN -> {
			obj.setMinimization();
		}
		}
	}

	private void addIlpConstraintsToOr(final Collection<ILPConstraint<Integer>> constraints, final String name) {
		final Iterator<ILPConstraint<Integer>> cnstrsIt = constraints.iterator();
		while (cnstrsIt.hasNext()) {
			final ILPConstraint<Integer> curr = cnstrsIt.next();
			final MPConstraint cnstr = model.makeConstraint(name);

			for (int i = 0; i < curr.lhsTerms().size(); i++) {
				final ILPTerm<Integer, Double> term = curr.lhsTerms().get(i);
				cnstr.setCoefficient(vars.get(term.variable().getName()), term.weight());
			}

			final double rhs = curr.rhsConstantTerm();

			switch (curr.operator()) {
			case EQUAL -> {
				cnstr.setBounds(rhs, rhs);
			}
			case GREATER_OR_EQUAL -> {
				cnstr.setBounds(rhs, Double.POSITIVE_INFINITY);
			}
			case LESS_OR_EQUAL -> {
				cnstr.setBounds(Double.NEGATIVE_INFINITY, rhs);
			}
			default -> {
				throw new UnsupportedOperationException("Operator did not match.");
			}
			}
		}
	}

}
