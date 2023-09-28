package org.emoflon.gips.core.ilp;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsGlobalConstraint;
import org.emoflon.gips.core.GipsGlobalObjective;
import org.emoflon.gips.core.GipsMapping;
import org.emoflon.gips.core.GipsMappingConstraint;
import org.emoflon.gips.core.GipsTypeConstraint;
import org.emoflon.gips.core.gt.GipsPatternConstraint;
import org.emoflon.ilp.Problem;
import org.emoflon.ilp.Variable;

public class EmoflonIlpSolver extends ILPSolver {
	
	// TODO
	final Problem problem = null;

	/**
	 * ILP solver configuration.
	 */
	final private ILPSolverConfig config;
	
	public EmoflonIlpSolver(final GipsEngine engine, final ILPSolverConfig config) {
		super(engine);
		this.config = config;
//		init();
	}

	@Override
	public ILPSolverOutput solve() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateValuesFromSolution() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void translateMapping(final GipsMapping mapping) {
		// TODO Auto-generated method stub
		// Add a binary variable for the mapping
		createOrGetBinVar(mapping);
	}
	
	private void createOrGetBinVar(final ILPBinaryVariable variable) {
		// TODO
	}
	
	private Variable getVar(final String name) {
		return problem.getVariables().get(name);
	}

	@Override
	protected void translateConstraint(GipsMappingConstraint<?, ? extends EObject> constraint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void translateConstraint(GipsPatternConstraint<?, ?, ?> constraint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void translateConstraint(GipsTypeConstraint<?, ? extends EObject> constraint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void translateConstraint(GipsGlobalConstraint<?> constraint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void translateObjective(GipsGlobalObjective objective) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
