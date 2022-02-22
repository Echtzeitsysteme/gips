package org.emoflon.roam.core.ilp;

import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.core.RoamGlobalObjective;
import org.emoflon.roam.core.RoamMapping;
import org.emoflon.roam.core.RoamMappingConstraint;
import org.emoflon.roam.core.RoamMappingObjective;
import org.emoflon.roam.core.RoamTypeConstraint;
import org.emoflon.roam.core.RoamTypeObjective;

import gurobi.GRB.DoubleParam;
import gurobi.GRB.IntParam;
import gurobi.GRBEnv;
import gurobi.GRBModel;

public class GurobiSolver extends ILPSolver{

	/**
	 * Gurobi environment (for configuration etc.).
	 */
	private final GRBEnv env;

	/**
	 * Gurobi model.
	 */
	private GRBModel model;

	public GurobiSolver(RoamEngine engine, final ILPSolverConfig config) throws Exception{
		super(engine);
		env = new GRBEnv("Gurobi_ILP.log");
		env.set(DoubleParam.TimeLimit, config.timeLimit());
		env.set(IntParam.Seed, config.randomSeed());
		env.set(IntParam.Presolve, config.enablePresolve() ? 1 : 0);
		if (!config.enableOutput()) {
			env.set(IntParam.OutputFlag, 0);
		}
		model = new GRBModel(env);
		model.set(DoubleParam.TimeLimit, config.timeLimit());
		model.set(IntParam.Seed, config.randomSeed());
	}

	@Override
	public void solve() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateValuesFromSolution() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void translateMapping(RoamMapping mapping) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void translateConstraint(RoamMappingConstraint constraint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void translateConstraint(RoamTypeConstraint constraint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void translateObjective(RoamGlobalObjective objective) {
		// TODO Auto-generated method stub
		
	}


}
