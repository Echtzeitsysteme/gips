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
 * Tests x1 && x2 <=> s1.
 */
public class AndTest {

	/**
	 * Creates a constraint in the form x1 && x2 <=> s1 and returns the value for
	 * s1.
	 * 
	 * @param x1Val Fixed (constant) value for the variable x1.
	 * @param x2Val Fixed (constant) value for the variable x2.
	 * @return Value of the ILP variable s1 after solving.
	 */
	public double calcAndWithIlp(final double x1Val, final double x2Val) {
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
			expr.addTerm(-1.0, x1);
			expr.addTerm(1.0, s1);
			model.addConstr(expr, GRB.LESS_EQUAL, 0, "c1");

			expr = new GRBLinExpr();
			expr.addTerm(-1.0, x2);
			expr.addTerm(1.0, s1);
			model.addConstr(expr, GRB.LESS_EQUAL, 0, "c2");

			expr = new GRBLinExpr();
			expr.addTerm(1.0, x1);
			expr.addTerm(1.0, x2);
			expr.addTerm(-1.0, s1);
			model.addConstr(expr, GRB.LESS_EQUAL, 1, "c3");

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
		assertEquals(0, calcAndWithIlp(0, 0));
	}

	@Test
	public void test_1_1() {
		assertEquals(1, calcAndWithIlp(1, 1));
	}

	@Test
	public void test_0_1() {
		assertEquals(0, calcAndWithIlp(0, 1));
	}

	@Test
	public void test_1_0() {
		assertEquals(0, calcAndWithIlp(1, 0));
	}

}
