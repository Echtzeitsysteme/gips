package org.emoflon.gips.gipsl.validation;

import java.util.HashSet;

import org.eclipse.xtext.validation.Check;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsAndBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBracketBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsImplicationBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsMappingContext;
import org.emoflon.gips.gipsl.gipsl.GipsNotBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsOrBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.gipsl.GlobalContext;

public class GipslConstraintValidator extends GipslValidator {

	/**
	 * Runs all checks for a given constraint.
	 * 
	 * @param constraint Gips constraint to check.
	 */
	@Check
	public void checkConstraint(final GipsConstraint constraint) {
		if (GipslValidator.DISABLE_VALIDATOR) {
			return;
		}

		if (constraint == null) {
			return;
		}

		if (constraint.getExpr() == null) {
			error( //
					String.format(GipslValidatorUtils.CONSTRAINT_EMPTY_MESSAGE), //
					constraint, //
					GipslPackage.Literals.GIPS_CONSTRAINT__EXPR //
			);
			return;
		}

		if (constraint.getExpr().getExpr() == null) {
			error( //
					String.format(GipslValidatorUtils.CONSTRAINT_EMPTY_MESSAGE), //
					constraint, //
					GipslPackage.Literals.GIPS_CONSTRAINT__EXPR //
			);
			return;
		}

		// Trigger validation of boolean expression
		getEvalTypeFromBoolExpr(constraint.getExpr().getExpr());

		// Check if constraint is a literal -> warning
		checkConstraintIsLiteral(constraint);

		// Check if constraint is unique
		checkConstraintUnique(constraint);

		// Check if constraint contains at least one 'self' call (only if context is not
		// global)
		if (!(constraint.getContext() instanceof GlobalContext)) {
			validateConstraintHasSelf(constraint);
		}

		// Validate expression -> Non-linear operations must be constant in ILP time
		validateConstraintDynamic(constraint);

		// Validate that no mapping gets accessed if context is mapping
		validateNoMappingAccessIfMappingContext(constraint);

		// Validation: No "self.isMapped()" in context != mapping
		validateNoIsMappedInContextNotMapping(constraint);
	}

	/**
	 * This method validates that "isMapped()" is only usable if the context is a
	 * mapping.
	 * 
	 * @param constraint Constraint to check "isMapped()" in contexts for.
	 */
	public void validateNoIsMappedInContextNotMapping(final GipsConstraint constraint) {
		if (!(constraint.getContext() instanceof GipsMappingContext)) {
			final GipsBoolExpr expr = constraint.getExpr().getExpr();
			final boolean containsMappingCheckValue = containsMappingCheckValue(expr);
			if (containsMappingCheckValue) {
				error( //
						GipslValidatorUtils.IS_MAPPED_CALL_IN_CONTEXT_FORBIDDEN_MESSAGE, //
						constraint, //
						GipslPackage.Literals.GIPS_CONSTRAINT__EXPR //
				);
			}
		}
	}

	/**
	 * This method ensures that no mappings will be accessed from within the mapping
	 * context. This does not include 'self'.
	 * 
	 * @param constraint Constraint to check mapping in mapping access for.
	 */
	public void validateNoMappingAccessIfMappingContext(final GipsConstraint constraint) {
		if (constraint == null) {
			return;
		}

		// If context is not a mapping, return immediately
		if (getContextType(constraint.getContext()) != GipslValidatorUtils.ContextType.MAPPING) {
			return;
		}

		final GipsBoolExpr expr = constraint.getExpr().getExpr();

		boolean leftMapping = false;
		boolean rightMapping = false;

		if (expr instanceof GipsRelExpr) {
			final GipsRelExpr relExpr = (GipsRelExpr) expr;
			leftMapping = containsMappingsCall(relExpr.getLeft());
			rightMapping = containsMappingsCall(relExpr.getRight());
		} else if (expr instanceof GipsBoolExpr) {
			// Special case: Complete boolean expression is just a literal
			if (expr instanceof GipsBooleanLiteral) {
				return;
			} else if (expr instanceof GipsImplicationBoolExpr impl) {
				leftMapping = containsMappingsCall(impl.getLeft());
				rightMapping = containsMappingsCall(impl.getRight());
			} else if (expr instanceof GipsOrBoolExpr or) {
				leftMapping = containsMappingsCall(or.getLeft());
				rightMapping = containsMappingsCall(or.getRight());
			} else if (expr instanceof GipsAndBoolExpr and) {
				leftMapping = containsMappingsCall(and.getLeft());
				rightMapping = containsMappingsCall(and.getRight());
			} else if (expr instanceof GipsNotBoolExpr not) {
				leftMapping = containsMappingsCall(not.getOperand());
				rightMapping = leftMapping;
			} else if (expr instanceof GipsBracketBoolExpr brack) {
				leftMapping = containsMappingsCall(brack.getOperand());
				rightMapping = leftMapping;
			} else {
				throw new UnsupportedOperationException(GipslValidatorUtils.NOT_IMPLEMENTED_EXCEPTION_MESSAGE);
			}
		} else {
			throw new UnsupportedOperationException(GipslValidatorUtils.NOT_IMPLEMENTED_EXCEPTION_MESSAGE);
		}

		// Generate an error if mappings are referenced
		if (leftMapping || rightMapping) {
			error( //
					GipslValidatorUtils.MAPPING_IN_MAPPING_FORBIDDEN_MESSAGE, //
					constraint, //
					GipslPackage.Literals.GIPS_CONSTRAINT__EXPR //
			);
		}
	}

	/**
	 * Checks if the constraint is a literal and, therefore, display a warning.
	 * 
	 * @param constraint Constraint to check.
	 */
	public void checkConstraintIsLiteral(final GipsConstraint constraint) {
		if (constraint == null) {
			return;
		}

		if (constraint.getExpr().getExpr() instanceof GipsBooleanLiteral) {
			final GipsBooleanLiteral lit = (GipsBooleanLiteral) constraint.getExpr().getExpr();
			final String warning = String.valueOf(lit.isLiteral());
			warning( //
					String.format(GipslValidatorUtils.CONSTRAINT_EVAL_LITERAL_MESSAGE, warning), //
					GipslPackage.Literals.GIPS_CONSTRAINT__EXPR //
			);
		}
	}

	/**
	 * Checks if a given constraint is unique, i.e., if there is another constraint
	 * that has the exact same expression.
	 * 
	 * @param constraint Constraint to check uniqueness for.
	 */
	public void checkConstraintUnique(final GipsConstraint constraint) {
		if (constraint == null) {
			return;
		}

		final EditorGTFile file = (EditorGTFile) constraint.eContainer();
		final HashSet<GipsConstraint> others = new HashSet<>();
		for (final GipsConstraint other : file.getConstraints()) {
			if (constraint.equals(other)) {
				// TODO: ^equals() is defined as '==' in this case -.-
				// Therefore, this does not work, yet.
				others.add(other);
			}
		}

		if (others.size() > 1) {
			for (final GipsConstraint other : others) {
				warning( //
						GipslValidatorUtils.CONSTRAINT_DEFINED_MULTIPLE_TIMES_MESSAGE, //
						other, //
						GipslPackage.Literals.GIPS_CONSTRAINT__CONTEXT //
				);
			}
		}
	}

	/**
	 * Validates that a constraint has at least one 'self' reference.
	 * 
	 * @param constraint Constraint to validate.
	 */
	public void validateConstraintHasSelf(final GipsConstraint constraint) {
		if (constraint == null || constraint.getExpr() == null) {
			return;
		}

		final GipsBoolExpr expr = constraint.getExpr().getExpr();
		boolean leftSelf = false;
		boolean rightSelf = false;

		final GipslValidatorUtils.ContextType type = getContextType(constraint.getContext());

		if (expr instanceof GipsRelExpr) {
			final GipsRelExpr relExpr = (GipsRelExpr) expr;
			leftSelf = containsSelf(relExpr.getLeft(), type);
			rightSelf = containsSelf(relExpr.getRight(), type);
		} else if (expr instanceof GipsBoolExpr) {
			if (expr instanceof GipsImplicationBoolExpr impl) {
				leftSelf = containsSelf(impl.getLeft(), type);
				rightSelf = containsSelf(impl.getRight(), type);
			} else if (expr instanceof GipsOrBoolExpr or) {
				leftSelf = containsSelf(or.getLeft(), type);
				rightSelf = containsSelf(or.getRight(), type);
			} else if (expr instanceof GipsAndBoolExpr and) {
				leftSelf = containsSelf(and.getLeft(), type);
				rightSelf = containsSelf(and.getRight(), type);
			} else if (expr instanceof GipsNotBoolExpr not) {
				leftSelf = containsSelf(not.getOperand(), type);
				rightSelf = leftSelf;
			} else if (expr instanceof GipsBracketBoolExpr brack) {
				leftSelf = containsSelf(brack.getOperand(), type);
				rightSelf = leftSelf;
			} else if (expr instanceof GipsBooleanLiteral) {
				leftSelf = rightSelf = false;
			} else {
				throw new UnsupportedOperationException(GipslValidatorUtils.NOT_IMPLEMENTED_EXCEPTION_MESSAGE);
			}
		} else if (expr == null) {
			error( //
					String.format(GipslValidatorUtils.CONSTRAINT_EMPTY_MESSAGE), //
					constraint, //
					GipslPackage.Literals.GIPS_CONSTRAINT__EXPR //
			);
		} else {
			throw new UnsupportedOperationException(GipslValidatorUtils.NOT_IMPLEMENTED_EXCEPTION_MESSAGE);
		}

		// Generate an error if both sides of the constraint does not contain 'self'
		if (!(leftSelf || rightSelf)) {
			error( //
					String.format(GipslValidatorUtils.TYPE_DOES_NOT_CONTAIN_SELF_MESSAGE, "Constraint"), //
					constraint, //
					GipslPackage.Literals.GIPS_CONSTRAINT__EXPR //
			);
		}
	}

	// TODO: Das andere was ich gemerkt habe ist auch, dass du keine mapping.value()
	// ausdrücke in den Arithmetischen Ausdrück innerhalb von sum() haben darfst,
	// wenn du bereits in der Filterfunktion auf mappings zugreifst.
	/**
	 * Validates constraints regarding their dynamic parts. Currently, the following
	 * rule set is implemented: Forbidden input for non-linear mathematical
	 * functions (abs, sin, cos, sqrt, pow): self.isMapped() (only for context =
	 * mapping? -> currently no ...), mappings.xy->count/sum + exists/notExists
	 * 
	 * @param constraint Constraint to check dynamic elements for.
	 */
	public void validateConstraintDynamic(final GipsConstraint constraint) {
		if (constraint == null || constraint.getExpr() == null || constraint.getExpr().getExpr() == null) {
			return;
		}

		final GipsBoolExpr expr = constraint.getExpr().getExpr();

		boolean leftDynamic = false;
		boolean rightDynamic = false;

		if (expr instanceof GipsRelExpr) {
			final GipsRelExpr relExpr = (GipsRelExpr) expr;
			leftDynamic = validateArithExprDynamic(relExpr.getLeft());
			rightDynamic = validateArithExprDynamic(relExpr.getRight());
		} else if (expr instanceof GipsBoolExpr) {
			if (expr instanceof GipsImplicationBoolExpr impl) {
				leftDynamic = validateBoolExprDynamic(impl.getLeft());
				rightDynamic = validateBoolExprDynamic(impl.getRight());
			} else if (expr instanceof GipsOrBoolExpr or) {
				leftDynamic = validateBoolExprDynamic(or.getLeft());
				rightDynamic = validateBoolExprDynamic(or.getRight());
			} else if (expr instanceof GipsAndBoolExpr and) {
				leftDynamic = validateBoolExprDynamic(and.getLeft());
				rightDynamic = validateBoolExprDynamic(and.getRight());
			} else if (expr instanceof GipsNotBoolExpr not) {
				leftDynamic = validateBoolExprDynamic(not.getOperand());
				rightDynamic = leftDynamic;
			} else if (expr instanceof GipsBracketBoolExpr brack) {
				leftDynamic = validateBoolExprDynamic(brack.getOperand());
				rightDynamic = leftDynamic;
			} else if (expr instanceof GipsBooleanLiteral) {
				// Special case: Complete boolean expression is just a literal
				return;
			} else {
				throw new UnsupportedOperationException(GipslValidatorUtils.NOT_IMPLEMENTED_EXCEPTION_MESSAGE);
			}
		} else {
			throw new UnsupportedOperationException(GipslValidatorUtils.NOT_IMPLEMENTED_EXCEPTION_MESSAGE);
		}

		// Generate a warning if both sides of the constraint are constant
		if (!leftDynamic && !rightDynamic) {
			warning( //
					GipslValidatorUtils.CONSTRAINT_HAS_TWO_CONSTANT_SIDES_MESSAGE, //
					constraint, //
					GipslPackage.Literals.GIPS_CONSTRAINT__EXPR //
			);
		}
	}

}
