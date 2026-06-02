package org.emoflon.gips.core.milp;

import java.io.File;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsGlobalConstraint;
import org.emoflon.gips.core.GipsMapping;
import org.emoflon.gips.core.GipsMappingConstraint;
import org.emoflon.gips.core.GipsObjective;
import org.emoflon.gips.core.GipsTypeConstraint;
import org.emoflon.gips.core.gt.GipsPatternConstraint;
import org.emoflon.gips.core.gt.GipsRuleConstraint;
import org.emoflon.gips.core.milp.model.BinaryVariable;
import org.emoflon.gips.core.milp.model.Constraint;
import org.emoflon.gips.core.milp.model.IntegerVariable;
import org.emoflon.gips.core.milp.model.RealVariable;
import org.emoflon.gips.core.milp.model.Variable;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPSolver.ResultStatus;
import com.google.ortools.linearsolver.MPVariable;

public class OrToolsScipSolver extends Solver {

	private MPSolver solver;
	private final Map<String, Collection<Constraint>> constraints = new HashMap<>();
	private final Map<String, MPVariable> vars = new HashMap<>();
	private GipsObjective objective;
	private String lpPath = null;

	public OrToolsScipSolver(final GipsEngine engine, final SolverConfig config) {
		super(engine, config);
	}

	@Override
	public void init() {
//		Loader.loadNativeLibraries();
		System.load(
				"/media/mkratz/1b932157-ff46-4346-a0a2-bb416fff3fb5/fges/gips-or-tools-mini-eval/ortools-linux-x86-64-9.15.6755/ortools-linux-x86-64/libjniortools.so");
		solver = MPSolver.createSolver("SCIP");
		if (solver == null) {
			throw new RuntimeException("Could not create SCIP solver via Google OR-Tools.");
		}

		if (config.isTimeLimitEnabled()) {
			solver.setTimeLimit((long) (config.getTimeLimit() * 1000)); // seconds to milliseconds
		}

		if (config.isEnableThreadCount()) {
			solver.setNumThreads(config.getThreadCount());
		}

		if (config.isEnableLpOutput()) {
			this.lpPath = config.getLpPath();
		}

		constraints.clear();
		vars.clear();
	}

	@Override
	public void terminate() {
		constraints.clear();
		vars.clear();
		if (solver != null) {
			solver.delete();
			solver = null;
		}
	}

	@Override
	public SolverOutput solve() {
		setupConstraints();
		setupObjectiveFunction();

		// Export LP file if configured
		if (this.lpPath != null) {
			var lpData = solver.exportModelAsLpFormat();
			if (lpData != null && !lpData.isEmpty()) {
				try {
					var file = new File(this.lpPath);
					var parent = file.getParentFile();
					if (parent != null && !parent.exists()) {
						parent.mkdirs();
					}
					Files.writeString(file.toPath(), lpData);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// Adjust time limit if including initialization time
		if (config.isTimeLimitIncludeInitTime() && engine.getInitTimeInSeconds() != 0) {
			var oldTimeLimit = config.getTimeLimit();
			var newTimeLimit = oldTimeLimit - engine.getInitTimeInSeconds();
			if (newTimeLimit <= 0) {
				return new SolverOutput(SolverStatus.TIME_OUT, 0, null, 0, null);
			}
			config.setTimeLimit(newTimeLimit);
			if (config.isTimeLimitEnabled()) {
				if (config.isEnableOutput()) {
					System.out.println(
							"=> Debug output: Overwrite specified OR-Tools time limit with: " + config.getTimeLimit());
				}
				solver.setTimeLimit((long) (config.getTimeLimit() * 1000));
			}
		}

		// Solving
		var resultStatus = solver.solve();

		// Determine status
		SolverStatus status = null;
		var objVal = 0.0;
		var solCounter = -1;

		if (resultStatus == ResultStatus.OPTIMAL) {
			status = SolverStatus.OPTIMAL;
			objVal = solver.objective().value();
			solCounter = 1;
		} else if (resultStatus == ResultStatus.FEASIBLE) {
			status = SolverStatus.FEASIBLE;
			objVal = solver.objective().value();
			solCounter = 1;
		} else if (resultStatus == ResultStatus.INFEASIBLE) {
			status = SolverStatus.INFEASIBLE;
			solCounter = 0;
		} else if (resultStatus == ResultStatus.UNBOUNDED) {
			status = SolverStatus.UNBOUNDED;
			solCounter = 0;
		} else if (resultStatus == ResultStatus.ABNORMAL) {
			status = SolverStatus.INF_OR_UNBD;
			solCounter = 0;
		} else {
			status = SolverStatus.TIME_OUT;
			solCounter = 0;
		}

		return new SolverOutput(status, objVal, engine.getValidationLog(), solCounter,
				new ProblemStatistics(
						engine.getMappers().values().stream().map(m -> m.getMappings().size()).reduce(0,
								(sum, val) -> sum + val),
						vars.size(),
						constraints.values().stream().map(cons -> cons.size()).reduce(0, (sum, val) -> sum + val)));
	}

	@Override
	public void updateValuesFromSolution() {
		for (var key : engine.getMappers().keySet()) {
			var mapper = engine.getMapper(key);
			for (var k : mapper.getMappings().keySet()) {
				var mapping = mapper.getMapping(k);
				var varName = mapping.getName();

				if (mapping.hasBinaryVariable()) {
					var result = vars.containsKey(varName) ? vars.get(varName).solutionValue() : 0.0;
					mapping.setValue((int) Math.round(result));
				}

				if (mapping.hasAdditionalVariables()) {
					for (var var : mapping.getAdditionalVariables().entrySet()) {
						var name = var.getValue().getName();
						var mappingVarResult = vars.containsKey(name) ? vars.get(name).solutionValue() : 0.0;
						mapping.setAdditionalVariableValue(var.getKey(), mappingVarResult);
					}
				}
			}
		}

		for (var extender : engine.getTypeExtensions().values()) {
			for (var extension : extender.getExtensions()) {
				for (var variable : extension.getVariables().entrySet()) {
					var name = variable.getValue().getName();
					var var = vars.get(name);
					if (var != null) {
						var result = var.solutionValue();
						extension.setVariableValue(variable.getKey(), result);
					}
				}
			}
		}

		if (engine.getEclipseIntegration().getConfig().isSolutionValuesSynchronizationEnabled()) {
			engine.getEclipseIntegration().storeSolutionValues(this.vars.entrySet().stream()
					.collect(Collectors.toMap(Entry::getKey, e -> Math.round(e.getValue().solutionValue()))));
		}
	}

	@Override
	protected void translateMapping(final GipsMapping mapping) {
		if (mapping.hasBinaryVariable()) {
			createBinVarIfNotExists(mapping.getName(), mapping.getLowerBound(), mapping.getUpperBound());
		}
		if (mapping.hasAdditionalVariables()) {
			createAdditionalVars(mapping.getAdditionalVariables().values());
		}
	}

	@Override
	protected void translateConstraint(final GipsMappingConstraint<?, ? extends EObject> constraint) {
		createAdditionalVars(constraint.getAdditionalVariables());
		var collectedCnstr = new HashSet<Constraint>();
		collectedCnstr.addAll(constraint.getConstraints());
		collectedCnstr.addAll(constraint.getAdditionalConstraints());
		constraints.put(constraint.getName(), collectedCnstr);
	}

	@Override
	protected void translateConstraint(final GipsPatternConstraint<?, ?, ?> constraint) {
		createAdditionalVars(constraint.getAdditionalVariables());
		var collectedCnstr = new HashSet<Constraint>();
		collectedCnstr.addAll(constraint.getConstraints());
		collectedCnstr.addAll(constraint.getAdditionalConstraints());
		constraints.put(constraint.getName(), collectedCnstr);
	}

	@Override
	protected void translateConstraint(final GipsRuleConstraint<?, ?, ?> constraint) {
		createAdditionalVars(constraint.getAdditionalVariables());
		var collectedCnstr = new HashSet<Constraint>();
		collectedCnstr.addAll(constraint.getConstraints());
		collectedCnstr.addAll(constraint.getAdditionalConstraints());
		constraints.put(constraint.getName(), collectedCnstr);
	}

	@Override
	protected void translateConstraint(final GipsTypeConstraint<?, ? extends EObject> constraint) {
		createAdditionalVars(constraint.getAdditionalVariables());
		var collectedCnstr = new HashSet<Constraint>();
		collectedCnstr.addAll(constraint.getConstraints());
		collectedCnstr.addAll(constraint.getAdditionalConstraints());
		constraints.put(constraint.getName(), collectedCnstr);
	}

	@Override
	protected void translateConstraint(final GipsGlobalConstraint<?> constraint) {
		createAdditionalVars(constraint.getAdditionalVariables());
		var collectedCnstr = new HashSet<Constraint>();
		collectedCnstr.addAll(constraint.getConstraints());
		collectedCnstr.addAll(constraint.getAdditionalConstraints());
		constraints.put(constraint.getName(), collectedCnstr);
	}

	@Override
	protected void translateObjective(final GipsObjective objective) {
		this.objective = objective;
	}

	private void setupConstraints() {
		var numRows = 0;
		for (var col : constraints.values()) {
			numRows += col.size();
		}

		if (numRows == 0) {
			return;
		}

		for (var name : constraints.keySet()) {
			var cnstrIt = constraints.get(name).iterator();
			var localCnstrCounter = 0;

			while (cnstrIt.hasNext()) {
				var cnstr = cnstrIt.next();
				if (cnstr == null) {
					continue;
				}

				var lb = Double.NEGATIVE_INFINITY;
				var ub = Double.POSITIVE_INFINITY;

				switch (cnstr.operator()) {
				case EQUAL -> {
					lb = cnstr.rhsConstantTerm();
					ub = cnstr.rhsConstantTerm();
				}
				case LESS_OR_EQUAL -> {
					ub = cnstr.rhsConstantTerm();
				}
				case GREATER_OR_EQUAL -> {
					lb = cnstr.rhsConstantTerm();
				}
				default -> throw new UnsupportedOperationException("Unsupported operator.");
				}

				var constraintName = name + "_" + localCnstrCounter;
				var mpCnstr = solver.makeConstraint(lb, ub, constraintName);

				for (var act : cnstr.lhsTerms()) {
					var var = vars.get(act.variable().getName());
					if (var != null) {
						mpCnstr.setCoefficient(var, act.weight());
					}
				}

				localCnstrCounter++;
			}
		}
	}

	private void setupObjectiveFunction() {
		if (objective == null) {
			return;
		}

		var nestFunc = objective.getObjectiveFunction();
		var mpObj = solver.objective();

		switch (nestFunc.goal()) {
		case MAX -> mpObj.setMaximization();
		case MIN -> mpObj.setMinimization();
		}

		var constSum = 0.0;
		for (var c : nestFunc.constants()) {
			constSum += c.weight();
		}

		var objCoeffs = new HashMap<String, Double>();

		for (var lf : nestFunc.linearFunctions()) {
			lf.linearFunction().terms().forEach(t -> {
				var name = t.variable().getName();
				var weight = t.weight() * lf.weight();
				objCoeffs.put(name, objCoeffs.getOrDefault(name, 0.0) + weight);
			});

			for (var c : lf.linearFunction().constantTerms()) {
				constSum += (c.weight() * lf.weight());
			}
		}

		for (var entry : objCoeffs.entrySet()) {
			var var = vars.get(entry.getKey());
			if (var != null) {
				mpObj.setCoefficient(var, entry.getValue());
			}
		}

		mpObj.setOffset(constSum);
	}

	protected void createAdditionalVars(final Collection<Variable<?>> variables) {
		for (var variable : variables) {
			if (variable instanceof BinaryVariable binVar) {
				createBinVarIfNotExists(binVar.name, variable.getLowerBound(), variable.getUpperBound());
			} else if (variable instanceof IntegerVariable intVar) {
				createIntVarIfNotExists(intVar.name, variable.getLowerBound(), variable.getUpperBound());
			} else if (variable instanceof RealVariable realVar) {
				createDblVarIfNotExists(realVar.name, variable.getLowerBound(), variable.getUpperBound());
			} else {
				throw new IllegalArgumentException("Unsupported variable type: " + variable.getClass().getSimpleName());
			}
		}
	}

	private void createBinVarIfNotExists(final String name, final Number lb, final Number ub) {
		if (vars.containsKey(name)) {
			return;
		}
		var v = solver.makeBoolVar(name);
		v.setBounds(lb.doubleValue(), ub.doubleValue());
		vars.put(name, v);
	}

	private void createIntVarIfNotExists(final String name, final Number lb, final Number ub) {
		if (vars.containsKey(name)) {
			return;
		}
		var v = solver.makeIntVar(lb.doubleValue(), ub.doubleValue(), name);
		vars.put(name, v);
	}

	private void createDblVarIfNotExists(final String name, final Number lb, final Number ub) {
		if (vars.containsKey(name)) {
			return;
		}
		var v = solver.makeNumVar(lb.doubleValue(), ub.doubleValue(), name);
		vars.put(name, v);
	}

	@Override
	public void reset() {
		init();
	}

}
