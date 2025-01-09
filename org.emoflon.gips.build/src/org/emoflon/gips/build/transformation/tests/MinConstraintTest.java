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
 * Tests x2 = min(x1, c).
 */
public class MinConstraintTest {

	/**
	 * Big M: a large constant as upper and lower bounds.
	 */
	private final static double M = 10_000;

	/**
	 * Epsilon: a small constant to differentiate values.
	 */
	private final static double eps = 0.001;

	/**
	 * Creates a constraint in the form x2 = min(x1,c) and returns the value for x2.
	 * 
	 * @param x1Val Fixed (constant) value for the variable x1.
	 * @param c     Constant for the constraint expression.
	 * @return Value of the ILP variable x2 after solving.
	 */
	public double calcMinWithIlp(final double x1Val, final double c) {
		double x2Val = -1;
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
			final GRBVar s2 = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s2");
			final GRBVar s2a = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s2a");
			final GRBVar s2b = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s2b");
			final GRBVar s3 = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s3");
			final GRBVar s4 = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s4");
			final GRBVar s4a = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s4a");
			final GRBVar s4b = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s4b");

			// Add constraint x = $xVal
			GRBLinExpr expr = new GRBLinExpr();
			expr.addTerm(1.0, x1);
			model.addConstr(expr, GRB.EQUAL, x1Val, "cx");

			// Add constraint (a)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x1);
			expr.addTerm(-M, s1);
			model.addConstr(expr, GRB.GREATER_EQUAL, c - M, "a");

			// Add constraint (b)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x1);
			expr.addTerm(-M, s1);
			model.addConstr(expr, GRB.LESS_EQUAL, c - eps, "b");

			// Add constraints (c) to (d)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x2);
			expr.addTerm(-1.0, x1);
			expr.addTerm(M, s2a);
			model.addConstr(expr, GRB.GREATER_EQUAL, eps, "cd1");

			expr = new GRBLinExpr();
			expr.addTerm(1.0, x2);
			expr.addTerm(-1.0, x1);
			expr.addTerm(M, s2a);
			model.addConstr(expr, GRB.LESS_EQUAL, M, "cd2");

			expr = new GRBLinExpr();
			expr.addTerm(1.0, x2);
			expr.addTerm(-1.0, x1);
			expr.addTerm(-M, s2b);
			model.addConstr(expr, GRB.GREATER_EQUAL, -M, "cd3");

			expr = new GRBLinExpr();
			expr.addTerm(1.0, x2);
			expr.addTerm(-1.0, x1);
			expr.addTerm(-M, s2b);
			model.addConstr(expr, GRB.LESS_EQUAL, -eps, "cd4");

			expr = new GRBLinExpr();
			expr.addTerm(-1.0, s2a);
			expr.addTerm(1.0, s2);
			model.addConstr(expr, GRB.LESS_EQUAL, 0, "cd5");

			expr = new GRBLinExpr();
			expr.addTerm(-1.0, s2b);
			expr.addTerm(1.0, s2);
			model.addConstr(expr, GRB.LESS_EQUAL, 0, "cd6");

			expr = new GRBLinExpr();
			expr.addTerm(1.0, s2a);
			expr.addTerm(1.0, s2b);
			expr.addTerm(-1.0, s2);
			model.addConstr(expr, GRB.LESS_EQUAL, 1, "cd7");

			// Add constraint (e)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x1);
			expr.addTerm(M, s3);
			model.addConstr(expr, GRB.GREATER_EQUAL, c, "e");

			// Add constraint (f)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x1);
			expr.addTerm(M, s3);
			model.addConstr(expr, GRB.LESS_EQUAL, M + c - eps, "f");

			// Add constraints (g) to (h)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x2);
			expr.addTerm(M, s4a);
			model.addConstr(expr, GRB.GREATER_EQUAL, c + eps, "gh1");

			expr = new GRBLinExpr();
			expr.addTerm(1.0, x2);
			expr.addTerm(M, s4a);
			model.addConstr(expr, GRB.LESS_EQUAL, M + c, "gh2");

			expr = new GRBLinExpr();
			expr.addTerm(1.0, x2);
			expr.addTerm(-M, s4b);
			model.addConstr(expr, GRB.GREATER_EQUAL, c - M, "gh3");

			expr = new GRBLinExpr();
			expr.addTerm(1.0, x2);
			expr.addTerm(-M, s4b);
			model.addConstr(expr, GRB.LESS_EQUAL, c - eps, "gh4");

			expr = new GRBLinExpr();
			expr.addTerm(-1.0, s4a);
			expr.addTerm(1.0, s4);
			model.addConstr(expr, GRB.LESS_EQUAL, 0, "gh5");

			expr = new GRBLinExpr();
			expr.addTerm(-1.0, s4b);
			expr.addTerm(1.0, s4);
			model.addConstr(expr, GRB.LESS_EQUAL, 0, "gh6");

			expr = new GRBLinExpr();
			expr.addTerm(1.0, s4a);
			expr.addTerm(1.0, s4b);
			expr.addTerm(-1.0, s4);
			model.addConstr(expr, GRB.LESS_EQUAL, 1, "gh7");

			// Add constraint (i)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, s1);
			expr.addTerm(1.0, s2);
			model.addConstr(expr, GRB.GREATER_EQUAL, 1, "i");

			// Add constraint (j)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, s3);
			expr.addTerm(1.0, s4);
			model.addConstr(expr, GRB.GREATER_EQUAL, 1, "j");

			// Optimize model
			model.optimize();

			x2Val = x2.get(GRB.DoubleAttr.X);

			// Dispose of model and environment
			model.dispose();
			env.dispose();
		} catch (final GRBException e) {
			System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
		}

		return x2Val;
	}

	@Test
	public void test_0_0() {
		assertEquals(0, calcMinWithIlp(0, 0));
	}

	@Test
	public void test_1_1() {
		assertEquals(1, calcMinWithIlp(1, 1));
	}

	@Test
	public void test_0_1() {
		assertEquals(0, calcMinWithIlp(0, 1));
	}

	@Test
	public void test_1_0() {
		assertEquals(0, calcMinWithIlp(1, 0));
	}

	@Test
	public void test_127_130() {
		assertEquals(127, calcMinWithIlp(127, 130));
	}

	@Test
	public void test_130_130() {
		assertEquals(130, calcMinWithIlp(130, 130));
	}

	@Test
	public void test_130_127() {
		assertEquals(127, calcMinWithIlp(130, 127));
	}

	@Test
	public void test_data() {
		final double[][] data = TestCaseGenerator.generateTestData();
		for (int i = 0; i < data.length; i++) {
			double a = data[i][0];
			double b = data[i][1];
			assertEquals(Math.min(a, b), calcMinWithIlp(a, b));
		}
	}

}
