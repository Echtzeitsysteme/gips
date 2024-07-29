package org.emoflon.gips.build.transformation.tests;

import com.gurobi.gurobi.GRB;
import com.gurobi.gurobi.GRBEnv;
import com.gurobi.gurobi.GRBException;
import com.gurobi.gurobi.GRBLinExpr;
import com.gurobi.gurobi.GRBModel;
import com.gurobi.gurobi.GRBVar;

/**
 * This class models the GIPS ILP transformations (e.g., f(x) >= c) directly
 * with Gurobi. It can be used to debug transformations and/or test
 * simplifications.
 */
public class GipsIlpTransformations {

	/**
	 * Big M: a large constant as upper and lower bounds.
	 */
	final static double M = 10_000;

	/**
	 * Epsilon: a small constant to differentiate values.
	 */
	final static double eps = 0.001;

	/**
	 * The Gurobi solver environment.
	 */
	private static GRBEnv env = null;

	/**
	 * The Gurobi solver model.
	 */
	private static GRBModel model = null;

	/**
	 * This method only includes static methods, therefore, this constructor forbids
	 * the initialization of an object of this class.
	 */
	private GipsIlpTransformations() {
	}

	/**
	 * Initializes a new Gurobi environment and model.
	 * 
	 * @throws GRBException Throws an exception if the initialization fails.
	 */
	private static void initGurobi() throws GRBException {
		// Create empty environment, set options, and start
		env = new GRBEnv(true);
		env.start();

		// Create empty model
		model = new GRBModel(env);
	}

	/**
	 * Disposes the Gurobi environment and model.
	 * 
	 * @throws GRBException Throws an exception if the disposal fails.
	 */
	private static void disposeGurobi() throws GRBException {
		// Dispose of model and environment
		model.dispose();
		env.dispose();
	}

	/**
	 * f(x) = c
	 * 
	 * @param xVal Specific constant value for the ILP variable x
	 * @param c    Constant
	 * @return True if the ILP solver's problem finds f(x) = c.
	 */
	public static boolean calcEqual(final double xVal, final double c) {
		boolean ret = false;
		try {
			initGurobi();

			// Create variables
			final GRBVar x = model.addVar(-200.0, 200.0, 0, GRB.INTEGER, "x");
			final GRBVar s1 = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s1");
			final GRBVar s2 = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s2");
			final GRBVar s3 = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s3");

			// Add constraint x = $xVal
			GRBLinExpr expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			model.addConstr(expr, GRB.EQUAL, xVal, "cx");

			// Add constraint (1)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(M, s1);
			model.addConstr(expr, GRB.GREATER_EQUAL, c + eps, "c1");

			// Add constraint (2)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(M, s1);
			model.addConstr(expr, GRB.LESS_EQUAL, M + c, "c2");

			// Add constraint (3)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(-M, s2);
			model.addConstr(expr, GRB.GREATER_EQUAL, c - M, "c3");

			// Add constraint (4)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(-M, s2);
			model.addConstr(expr, GRB.LESS_EQUAL, c - eps, "c4");

			//
			// s3 = true, iff s1 = true and s2 = true
			//

			expr = new GRBLinExpr();
			expr.addTerm(-1, s1);
			expr.addTerm(1, s3);
			model.addConstr(expr, GRB.LESS_EQUAL, 0, "c5");

			expr = new GRBLinExpr();
			expr.addTerm(-1, s2);
			expr.addTerm(1, s3);
			model.addConstr(expr, GRB.LESS_EQUAL, 0, "c6");

			expr = new GRBLinExpr();
			expr.addTerm(1, s1);
			expr.addTerm(1, s2);
			expr.addTerm(-1, s3);
			model.addConstr(expr, GRB.LESS_EQUAL, 1, "c7");

			// Optimize model
			model.optimize();

			System.out.println(x.get(GRB.StringAttr.VarName) + " " + x.get(GRB.DoubleAttr.X));
			System.out.println(s3.get(GRB.StringAttr.VarName) + " " + s3.get(GRB.DoubleAttr.X));

			ret = (s3.get(GRB.DoubleAttr.X) == 1);

			disposeGurobi();
		} catch (final GRBException e) {
			System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
		}

		return ret;
	}

	/**
	 * f(x) != c
	 * 
	 * @param xVal Specific constant value for the ILP variable x
	 * @param c    Constant
	 * @return True if the ILP solver's problem finds f(x) != c.
	 */
	public static boolean calcNotEqual(final double xVal, final double c) {
		boolean ret = false;
		try {
			initGurobi();

			// Create variables
			final GRBVar x = model.addVar(-200.0, 200.0, 0, GRB.INTEGER, "x");
			final GRBVar s1 = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s1");
			final GRBVar s2 = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s2");
			final GRBVar s3 = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s3");

			// Add constraint x = $xVal
			GRBLinExpr expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			model.addConstr(expr, GRB.EQUAL, xVal, "cx");

			// Add constraint (1)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(-M, s1);
			model.addConstr(expr, GRB.GREATER_EQUAL, c + eps - M, "c1");

			// Add constraint (2)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(-M, s1);
			model.addConstr(expr, GRB.LESS_EQUAL, c, "c2");

			// Add constraint (3)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(M, s2);
			model.addConstr(expr, GRB.GREATER_EQUAL, c, "c3");

			// Add constraint (4)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(M, s2);
			model.addConstr(expr, GRB.LESS_EQUAL, M + c - eps, "c4");

			// Add constraint (5)
			expr = new GRBLinExpr();
			expr.addTerm(1, s1);
			expr.addTerm(1, s2);
			expr.addTerm(-1, s3);
			model.addConstr(expr, GRB.EQUAL, 0, "c5");

			// Optimize model
			model.optimize();

			System.out.println(x.get(GRB.StringAttr.VarName) + " " + x.get(GRB.DoubleAttr.X));
			System.out.println(s3.get(GRB.StringAttr.VarName) + " " + s3.get(GRB.DoubleAttr.X));

			ret = (s3.get(GRB.DoubleAttr.X) == 1);

			disposeGurobi();
		} catch (final GRBException e) {
			System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
		}

		return ret;
	}

	/**
	 * f(x) <= c
	 * 
	 * @param xVal Specific constant value for the ILP variable x
	 * @param c    Constant
	 * @return True if the ILP solver's problem finds f(x) <= c.
	 */
	public static boolean calcLessEqual(final double xVal, final double c) {
		boolean ret = false;
		try {
			initGurobi();

			// Create variables
			final GRBVar x = model.addVar(-200.0, 200.0, 0, GRB.INTEGER, "x");
			final GRBVar s1 = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s1");

			// Add constraint x = $xVal
			GRBLinExpr expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			model.addConstr(expr, GRB.EQUAL, xVal, "cx");

			// Add constraint (1)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(M, s1);
			model.addConstr(expr, GRB.GREATER_EQUAL, c + eps, "c1");

			// Add constraint (2)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(M, s1);
			model.addConstr(expr, GRB.LESS_EQUAL, M + c, "c2");

			// Optimize model
			model.optimize();

			System.out.println(x.get(GRB.StringAttr.VarName) + " " + x.get(GRB.DoubleAttr.X));
			System.out.println(s1.get(GRB.StringAttr.VarName) + " " + s1.get(GRB.DoubleAttr.X));

			ret = (s1.get(GRB.DoubleAttr.X) == 1);

			disposeGurobi();
		} catch (final GRBException e) {
			System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
		}

		return ret;
	}

	/**
	 * f(x) < c
	 * 
	 * @param xVal Specific constant value for the ILP variable x
	 * @param c    Constant
	 * @return True if the ILP solver's problem finds f(x) < c.
	 */
	public static boolean calcLess(final double xVal, final double c) {
		boolean ret = false;
		try {
			initGurobi();

			// Create variables
			final GRBVar x = model.addVar(-200.0, 200.0, 0, GRB.INTEGER, "x");
			final GRBVar s1 = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s1");

			// Add constraint x = $xVal
			GRBLinExpr expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			model.addConstr(expr, GRB.EQUAL, xVal, "cx");

			// Add constraint (1)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(M, s1);
			model.addConstr(expr, GRB.GREATER_EQUAL, c, "c1");

			// Add constraint (2)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(M, s1);
			model.addConstr(expr, GRB.LESS_EQUAL, M + c - eps, "c2");

			// Optimize model
			model.optimize();

			System.out.println(x.get(GRB.StringAttr.VarName) + " " + x.get(GRB.DoubleAttr.X));
			System.out.println(s1.get(GRB.StringAttr.VarName) + " " + s1.get(GRB.DoubleAttr.X));

			ret = (s1.get(GRB.DoubleAttr.X) == 1);

			disposeGurobi();
		} catch (final GRBException e) {
			System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
		}

		return ret;
	}

	/**
	 * f(x) >= c
	 * 
	 * @param xVal Specific constant value for the ILP variable x
	 * @param c    Constant
	 * @return True if the ILP solver's problem finds f(x) >= c.
	 */
	public static boolean calcGreaterEqual(final double xVal, final double c) {
		boolean ret = false;
		try {
			initGurobi();

			// Create variables
			final GRBVar x = model.addVar(-200.0, 200.0, 0, GRB.INTEGER, "x");
			final GRBVar s1 = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s1");

			// Add constraint x = $xVal
			GRBLinExpr expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			model.addConstr(expr, GRB.EQUAL, xVal, "cx");

			// Add constraint (1)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(-M, s1);
			model.addConstr(expr, GRB.GREATER_EQUAL, c - M, "c1");

			// Add constraint (2)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(-M, s1);
			model.addConstr(expr, GRB.LESS_EQUAL, c - eps, "c2");

			// Optimize model
			model.optimize();

			System.out.println(x.get(GRB.StringAttr.VarName) + " " + x.get(GRB.DoubleAttr.X));
			System.out.println(s1.get(GRB.StringAttr.VarName) + " " + s1.get(GRB.DoubleAttr.X));

			ret = (s1.get(GRB.DoubleAttr.X) == 1);

			disposeGurobi();
		} catch (final GRBException e) {
			System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
		}

		return ret;
	}

	/**
	 * f(x) > c
	 * 
	 * @param xVal Specific constant value for the ILP variable x
	 * @param c    Constant
	 * @return True if the ILP solver's problem finds f(x) > c.
	 */
	public static boolean calcGreater(final double xVal, final double c) {
		boolean ret = false;
		try {
			initGurobi();

			// Create variables
			final GRBVar x = model.addVar(-200.0, 200.0, 0, GRB.INTEGER, "x");
			final GRBVar s1 = model.addVar(0.0, 1.0, 0, GRB.BINARY, "s1");

			// Add constraint x = $xVal
			GRBLinExpr expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			model.addConstr(expr, GRB.EQUAL, xVal, "cx");

			// Add constraint (1)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(-M, s1);
			model.addConstr(expr, GRB.GREATER_EQUAL, c + eps - M, "c1");

			// Add constraint (2)
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x);
			expr.addTerm(-M, s1);
			model.addConstr(expr, GRB.LESS_EQUAL, c, "c2");

			// Optimize model
			model.optimize();

			System.out.println(x.get(GRB.StringAttr.VarName) + " " + x.get(GRB.DoubleAttr.X));
			System.out.println(s1.get(GRB.StringAttr.VarName) + " " + s1.get(GRB.DoubleAttr.X));

			ret = (s1.get(GRB.DoubleAttr.X) == 1);

			disposeGurobi();
		} catch (final GRBException e) {
			System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
		}

		return ret;
	}

}
