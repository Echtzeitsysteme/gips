package org.emoflon.gips.gipsl.validation;

import org.eclipse.xtext.validation.Check;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsImplicationBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsOrBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.gipsl.gipsl.GipsRelOperator;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.gipsl.SolverType;

/**
 * This class is a validator for the operators used in the relational
 * expressions in GIPSL. In particular, some of the open-source solvers (like
 * GLPK) can not handle our big-M-based transformation and, therefore, we want
 * to display a warning if some of the operators (e.g., OR) are used together
 * with a affected solver.
 */
public class GipslOperatorValidator {

	/**
	 * Checks if a warning is needed for the given implication of equivalence
	 * Boolean expression.
	 * 
	 * @param expr Implication or equivalence Boolean expression to check.
	 */
	@Check
	public static void checkImpl(final GipsImplicationBoolExpr expr) {
		checkAndWarn(expr);
	}

	/**
	 * Checks if a warning is needed for the given or expression.
	 * 
	 * @param expr Or Boolean expression to check.
	 */
	@Check
	public static void checkOr(final GipsOrBoolExpr expr) {
		checkAndWarn(expr);
	}

	/**
	 * Checks if a warning is needed for the given relational Boolean expression.
	 * 
	 * @param expr Relational Boolean expression to check.
	 */
	@Check
	public static void checkRel(final GipsRelExpr expr) {
		if (!(expr.getOperator() == GipsRelOperator.EQUAL //
				|| expr.getOperator() == GipsRelOperator.SMALLER_OR_EQUAL //
				|| expr.getOperator() == GipsRelOperator.GREATER_OR_EQUAL)) {
			checkAndWarn(expr);
		}
	}

	//
	// Utilities
	//

	/**
	 * Determines if a warning must be displayed for the given Boolean expression.
	 * 
	 * @param expr Boolean expression to check and potentially generate a warning
	 *             for.
	 */
	private static void checkAndWarn(final GipsBoolExpr expr) {
		if (GipslValidator.DISABLE_VALIDATOR) {
			return;
		}

		final SolverType solver = getSolverFromExpr(expr);
		if (warningForSolver(solver)) {
			generateSolverWarning(solver, expr);
		}
	}

	/**
	 * This method extracts the used solver type of a GIPSL configuration based on a
	 * Boolean expression. It traverses the tree up until there is no EContainer
	 * left (i.e., it must be the EditorGTFile) and reads the configured solver from
	 * the config block of the EditorGTFile.
	 * 
	 * @param expr Boolean expression that is used as a starting point to get the
	 *             SolverType form.
	 * @return SolverType found in the EditorGTFile containing the given Boolean
	 *         expression.
	 */
	private static SolverType getSolverFromExpr(final GipsBoolExpr expr) {
		// find root element (= EditorGTFile)
		int safety = 0;
		var container = expr.eContainer();
		while (container.eContainer() != null && safety < 1000) {
			container = container.eContainer();
			safety++;
		}

		// get configured solver type from EditorGTFile
		final EditorGTFile gipslFile = (EditorGTFile) container;
		return gipslFile.getConfig().getSolver();
	}

	/**
	 * Generates a warning for the given SolverType and the given Boolean
	 * expression.
	 * 
	 * @param solverType SolverType used in the warning.
	 * @param expr       Boolean expression on whose operator the warning must
	 *                   appear.
	 */
	private static void generateSolverWarning(final SolverType solverType, final GipsBoolExpr expr) {
		if (expr instanceof GipsImplicationBoolExpr implExpr) {
			GipslValidator.warn( //
					"The solver " + solverType.getName() + " might not work correctly with the "
							+ implExpr.getOperator() + " operator.", //
					GipslPackage.Literals.GIPS_IMPLICATION_BOOL_EXPR__OPERATOR //
			);
		} else if (expr instanceof GipsOrBoolExpr orExpr) {
			GipslValidator.warn( //
					"The solver " + solverType.getName() + " might not work correctly with the "
							+ orExpr.getOperator().getName() + " operator.", //
					GipslPackage.Literals.GIPS_OR_BOOL_EXPR__OPERATOR //
			);
		} else if (expr instanceof GipsRelExpr relExpr) {
			GipslValidator.warn( //
					"The solver " + solverType.getName() + " might not work correctly with the "
							+ relExpr.getOperator().getLiteral() + " operator.", //
					GipslPackage.Literals.GIPS_REL_EXPR__OPERATOR //
			);
		}
	}

	/**
	 * This method specifies which solver is affected by the operators that this
	 * class checks for.
	 * 
	 * @param solverType SolverType to check for.
	 * @return True if a warning should be displayed for the given SolverType.
	 */
	private static boolean warningForSolver(final SolverType solverType) {
		if (solverType == SolverType.GLPK) {
			return true;
		}
		return false;
	}

}
