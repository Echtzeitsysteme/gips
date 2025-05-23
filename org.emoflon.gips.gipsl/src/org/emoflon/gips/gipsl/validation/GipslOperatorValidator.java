package org.emoflon.gips.gipsl.validation;

import org.eclipse.xtext.validation.Check;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanDisjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.gipsl.SolverType;
import org.emoflon.gips.gipsl.gipsl.impl.EditorGTFileImpl;
import org.emoflon.ibex.gt.editor.utils.GTEditorPatternUtils;

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
	public static void checkBooleanImplication(final GipsBooleanImplication expr) {
		checkAndWarn(expr);
	}

	/**
	 * Checks if a warning is needed for the given or expression.
	 * 
	 * @param expr Or Boolean expression to check.
	 */
	public static void checkBooleanDisjunction(final GipsBooleanDisjunction expr) {
		checkAndWarn(expr);
	}

	/**
	 * Checks if a warning is needed for the given relational Boolean expression.
	 * 
	 * @param expr Relational Boolean expression to check.
	 */
	@Check
	public static void checkRelationalExpression(final GipsRelationalExpression expr) {
		if (!(expr.getOperator() == RelationalOperator.EQUAL //
				|| expr.getOperator() == RelationalOperator.SMALLER_OR_EQUAL //
				|| expr.getOperator() == RelationalOperator.GREATER_OR_EQUAL)) {
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
	private static void checkAndWarn(final GipsBooleanExpression expr) {
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
	private static SolverType getSolverFromExpr(final GipsBooleanExpression expr) {
		EditorGTFile editorFile = GTEditorPatternUtils.getContainer(expr, EditorGTFileImpl.class);
		return editorFile.getConfig().getSolver();
	}

	/**
	 * Generates a warning for the given SolverType and the given Boolean
	 * expression.
	 * 
	 * @param solverType SolverType used in the warning.
	 * @param expr       Boolean expression on whose operator the warning must
	 *                   appear.
	 */
	private static void generateSolverWarning(final SolverType solverType, final GipsBooleanExpression expr) {
		if (expr instanceof GipsBooleanImplication implExpr) {
			GipslValidator.warn( //
					generateWarningString(solverType, implExpr.getOperator().getName()), //
					GipslPackage.Literals.GIPS_BOOLEAN_IMPLICATION__OPERATOR //
			);
		} else if (expr instanceof GipsBooleanDisjunction orExpr) {
			GipslValidator.warn( //
					generateWarningString(solverType, orExpr.getOperator().getName()), //
					GipslPackage.Literals.GIPS_BOOLEAN_DISJUNCTION__OPERATOR //
			);
		} else if (expr instanceof GipsRelationalExpression relExpr) {
			GipslValidator.warn( //
					generateWarningString(solverType, relExpr.getOperator().getName()), //
					GipslPackage.Literals.GIPS_RELATIONAL_EXPRESSION__OPERATOR //
			);
		}
	}

	/**
	 * Generates a warning String for the given SolverType and operator name.
	 * 
	 * @param solverType   SolverType to include in the warning String.
	 * @param operatorName Operator name to include in the warning String.
	 * @return Warning String for the given SolverType and operator name.
	 */
	private static String generateWarningString(final SolverType solverType, final String operatorName) {
		return "The solver " + solverType.getName() + " might lack the necessary precistion to support the operator "
				+ operatorName + " correctly. Use with caution.";
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
