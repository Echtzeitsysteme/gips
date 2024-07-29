package org.emoflon.gips.build.transformation.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.gurobi.gurobi.GRB;
import com.gurobi.gurobi.GRBEnv;
import com.gurobi.gurobi.GRBException;
import com.gurobi.gurobi.GRBLinExpr;
import com.gurobi.gurobi.GRBModel;
import com.gurobi.gurobi.GRBVar;

/**
 * Tests (x2 = x1) <=> s1
 */
public class EqualVarTest {

	/**
	 * Big M: a large constant as upper and lower bounds.
	 */
	private final static double M = 10_000;

	/**
	 * Epsilon: a small constant to differentiate values.
	 */
	private final static double eps = 0.001;

	/**
	 * Creates a constraint in the form (x2 = x1) <=> s1 and returns the value for
	 * s1.
	 * 
	 * @param x1Val Fixed (constant) value for the variable x1.
	 * @param x2Val Fixed (constant) value for the variable x2.
	 * @return Value of the ILP variable s1 after solving.
	 */
	public double calcEquivalenceWithIlp(final double x1Val, final double x2Val) {
		double s1Val = -1;
		try {
			// Create empty environment, set options, and start
			final GRBEnv env = new GRBEnv(true);
			env.start();

			// Create empty model
			final GRBModel model = new GRBModel(env);

			// Create variables
			final GRBVar x1 = model.addVar(-200.0, 200.0, 0, GRB.INTEGER, "x1");
			final GRBVar x2 = model.addVar(-200.0, 200.0, 0, GRB.INTEGER, "x2");
			final GRBVar s1 = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s1");
			final GRBVar s1a = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s1a");
			final GRBVar s1b = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s1b");

			// Add constraint x1 = $x1Val
			GRBLinExpr expr = new GRBLinExpr();
			expr.addTerm(1.0, x1);
			model.addConstr(expr, GRB.EQUAL, x1Val, "cx1");

			// Add constraint x2 = $x2Val
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x2);
			model.addConstr(expr, GRB.EQUAL, x2Val, "cx2");

			// Add constraint(s), every block is a line of my notes
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x2);
			expr.addTerm(-1.0, x1);
			expr.addTerm(-M, s1a);
			model.addConstr(expr, GRB.GREATER_EQUAL, -M, "c1");

			expr = new GRBLinExpr();
			expr.addTerm(1.0, x2);
			expr.addTerm(-1.0, x1);
			expr.addTerm(-M, s1a);
			model.addConstr(expr, GRB.LESS_EQUAL, -eps, "c2");
			
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x2);
			expr.addTerm(-1.0, x1);
			expr.addTerm(M, s1b);
			model.addConstr(expr, GRB.GREATER_EQUAL, eps, "c3");
			
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x2);
			expr.addTerm(-1.0, x1);
			expr.addTerm(M, s1b);
			model.addConstr(expr, GRB.LESS_EQUAL, M, "c4");
			
			expr = new GRBLinExpr();
			expr.addTerm(-1.0, s1a);
			expr.addTerm(1.0, s1);
			model.addConstr(expr, GRB.LESS_EQUAL, 0, "c5");

			expr = new GRBLinExpr();
			expr.addTerm(-1.0, s1b);
			expr.addTerm(1.0, s1);
			model.addConstr(expr, GRB.LESS_EQUAL, 0, "c6");
			
			expr = new GRBLinExpr();
			expr.addTerm(1.0, s1a);
			expr.addTerm(1.0, s1b);
			expr.addTerm(-1.0, s1);
			model.addConstr(expr, GRB.LESS_EQUAL, 1, "c7");
			
			// Optimize model
			model.optimize();

			s1Val = s1.get(GRB.DoubleAttr.X);

			// Dispose of model and environment
			model.dispose();
			env.dispose();
		} catch (final GRBException e) {
			System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
		}

		return s1Val;
	}

	@Test
	public void test_0_0() {
		assertEquals(1, calcEquivalenceWithIlp(0, 0));
	}

	@Test
	public void test_1_1() {
		assertEquals(1, calcEquivalenceWithIlp(1, 1));
	}

	@Test
	public void test_0_1() {
		assertEquals(0, calcEquivalenceWithIlp(0, 1));
	}

	@Test
	public void test_1_0() {
		assertEquals(0, calcEquivalenceWithIlp(1, 0));
	}

	@Test
	public void test_127_130() {
		assertEquals(0, calcEquivalenceWithIlp(127, 130));
	}

	@Test
	public void test_130_130() {
		assertEquals(1, calcEquivalenceWithIlp(130, 130));
	}

	@Test
	public void test_130_127() {
		assertEquals(0, calcEquivalenceWithIlp(130, 127));
	}

}
