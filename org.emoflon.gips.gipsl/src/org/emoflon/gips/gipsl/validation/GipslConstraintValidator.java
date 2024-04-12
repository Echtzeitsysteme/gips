package org.emoflon.gips.gipsl.validation;

import java.util.HashSet;

import org.eclipse.xtext.validation.Check;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsAndBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBracketBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBracketExpr;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsContains;
import org.emoflon.gips.gipsl.gipsl.GipsContextExpr;
import org.emoflon.gips.gipsl.gipsl.GipsExpArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsExpressionOperand;
import org.emoflon.gips.gipsl.gipsl.GipsImplicationBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsLambdaAttributeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsLambdaSelfExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMappingAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsMappingContext;
import org.emoflon.gips.gipsl.gipsl.GipsNotBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsObjectiveExpression;
import org.emoflon.gips.gipsl.gipsl.GipsOrBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsPatternAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsProductArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.gipsl.gipsl.GipsSelect;
import org.emoflon.gips.gipsl.gipsl.GipsStreamArithmetic;
import org.emoflon.gips.gipsl.gipsl.GipsStreamBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamNavigation;
import org.emoflon.gips.gipsl.gipsl.GipsStreamSet;
import org.emoflon.gips.gipsl.gipsl.GipsSumArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsUnaryArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsVariableOperationExpression;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.gipsl.GlobalContext;
import org.emoflon.gips.gipsl.validation.GipslValidatorUtils.ContextType;

public class GipslConstraintValidator {

	private GipslConstraintValidator() {
	}

	/**
	 * Runs all checks for a given constraint.
	 * 
	 * @param constraint Gips constraint to check.
	 */
	@Check
	public static void checkConstraint(final GipsConstraint constraint) {
		if (GipslValidator.DISABLE_VALIDATOR) {
			return;
		}

		if (constraint == null) {
			return;
		}

		if (constraint.getExpr() == null) {
			GipslValidator.err( //
					String.format(GipslValidatorUtils.CONSTRAINT_EMPTY_MESSAGE), //
					constraint, //
					GipslPackage.Literals.GIPS_CONSTRAINT__EXPR //
			);
			return;
		}

		if (constraint.getExpr().getExpr() == null) {
			GipslValidator.err( //
					String.format(GipslValidatorUtils.CONSTRAINT_EMPTY_MESSAGE), //
					constraint, //
					GipslPackage.Literals.GIPS_CONSTRAINT__EXPR //
			);
			return;
		}

		// Trigger validation of boolean expression
		GipslValidator.getEvalTypeFromBoolExpr(constraint.getExpr().getExpr());

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
	public static void validateNoIsMappedInContextNotMapping(final GipsConstraint constraint) {
		if (!(constraint.getContext() instanceof GipsMappingContext)) {
			final GipsBoolExpr expr = constraint.getExpr().getExpr();
			final boolean containsMappingCheckValue = containsMappingCheckValue(expr);
			if (containsMappingCheckValue) {
				GipslValidator.err( //
						GipslValidatorUtils.IS_MAPPED_CALL_IN_CONTEXT_FORBIDDEN_MESSAGE, //
						constraint, //
						GipslPackage.Literals.GIPS_CONSTRAINT__EXPR //
				);
			}
		}
	}

	/**
	 * Returns true if the given boolean expression contains an isMapped call.
	 * 
	 * @param expr Arithmetic expression to check.
	 * @return True if the given arithmetic expression contains an isMapped call.
	 */
	public static boolean containsMappingCheckValue(final GipsArithmeticExpr expr) {
		if (expr == null) {
			return false;
		}

		if (expr instanceof GipsBracketExpr bracketExpr) {
			return containsMappingCheckValue(bracketExpr.getOperand());
		} else if (expr instanceof GipsExpArithmeticExpr expExpr) {
			return containsMappingCheckValue(expExpr.getLeft()) || containsMappingCheckValue(expExpr.getRight());
		} else if (expr instanceof GipsExpressionOperand exprOp) {
			if (exprOp instanceof GipsArithmeticLiteral) {
				return false;
			} else if (exprOp instanceof GipsAttributeExpr) {
				if (exprOp instanceof GipsContextExpr conExpr) {
					// Streams can be ignored
					return conExpr.getExpr() instanceof GipsVariableOperationExpression;
				} else if (exprOp instanceof GipsLambdaAttributeExpression) {
					// A GipsLambdaAttributeExpression can not contain an isMapped call
					return false;
				} else if (exprOp instanceof GipsMappingAttributeExpr) {
					// Streams can be ignored
					return false;
				} else if (exprOp instanceof GipsPatternAttributeExpr patternExpr) {
					// Streams can be ignored
					return false;
				} else if (exprOp instanceof GipsTypeAttributeExpr typeExpr) {
					// Streams can be ignored
					return false;
				} else if (exprOp instanceof GipsObjectiveExpression) {
					return false;
				}
			}
		} else if (expr instanceof GipsProductArithmeticExpr prodExpr) {
			return containsMappingCheckValue(prodExpr.getLeft()) || containsMappingCheckValue(prodExpr.getRight());
		} else if (expr instanceof GipsSumArithmeticExpr sumExpr) {
			return containsMappingCheckValue(sumExpr.getLeft()) || containsMappingCheckValue(sumExpr.getRight());
		} else if (expr instanceof GipsUnaryArithmeticExpr unExpr) {
			return containsMappingCheckValue(unExpr.getOperand());
		}

		throw new UnsupportedOperationException(GipslValidatorUtils.NOT_IMPLEMENTED_EXCEPTION_MESSAGE);
	}

	/**
	 * Returns true if the given boolean expression contains an isMapped call.
	 * 
	 * @param expr Boolean expression to check.
	 * @return True if the given boolean expression contains an isMapped call.
	 */
	public static boolean containsMappingCheckValue(final GipsBoolExpr expr) {
		if (expr == null) {
			return false;
		}

		if (expr instanceof GipsAndBoolExpr andExpr) {
			return containsMappingCheckValue(andExpr.getLeft()) || containsMappingCheckValue(andExpr.getRight());
		} else if (expr instanceof GipsBooleanLiteral) {
			return false;
		} else if (expr instanceof GipsBracketBoolExpr brackExpr) {
			return containsMappingCheckValue(brackExpr.getOperand());
		} else if (expr instanceof GipsImplicationBoolExpr implExpr) {
			return containsMappingCheckValue(implExpr.getLeft()) || containsMappingCheckValue(implExpr.getRight());
		} else if (expr instanceof GipsNotBoolExpr notExpr) {
			return containsMappingCheckValue(notExpr.getOperand());
		} else if (expr instanceof GipsOrBoolExpr orExpr) {
			return containsMappingCheckValue(orExpr.getLeft()) || containsMappingCheckValue(orExpr.getRight());
		} else if (expr instanceof GipsRelExpr relExpr) {
			return containsMappingCheckValue(relExpr.getLeft()) || containsMappingCheckValue(relExpr.getRight());
		}

		throw new UnsupportedOperationException(GipslValidatorUtils.NOT_IMPLEMENTED_EXCEPTION_MESSAGE);
	}

	/**
	 * Returns true if the given boolean expression contains a mapping call.
	 * 
	 * @param expr Boolean expression to check.
	 * @return True if the given boolean expression contains a mapping call.
	 */
	public static boolean containsMappingsCall(final GipsBoolExpr expr) {
		if (expr == null) {
			return false;
		}

		if (expr instanceof GipsImplicationBoolExpr impl) {
			return containsMappingsCall(impl.getLeft()) || containsMappingsCall(impl.getRight());
		} else if (expr instanceof GipsOrBoolExpr or) {
			return containsMappingsCall(or.getLeft()) || containsMappingsCall(or.getRight());
		} else if (expr instanceof GipsAndBoolExpr and) {
			return containsMappingsCall(and.getLeft()) || containsMappingsCall(and.getRight());
		} else if (expr instanceof GipsNotBoolExpr not) {
			return containsMappingsCall(not.getOperand());
		} else if (expr instanceof GipsBracketBoolExpr brack) {
			return containsMappingsCall(brack.getOperand());
		} else if (expr instanceof GipsBooleanLiteral) {
			return false;
		} else if (expr instanceof GipsRelExpr relExpr) {
			return containsMappingsCall(relExpr.getLeft()) || containsMappingsCall(relExpr.getRight());
		} else {
			throw new UnsupportedOperationException(GipslValidatorUtils.NOT_IMPLEMENTED_EXCEPTION_MESSAGE);
		}
	}

	/**
	 * Returns true if the given arithmetic expression contains a mapping call.
	 * 
	 * @param expr Arithmetic expression to check.
	 * @return True if the given arithmetic expression contains a mapping call.
	 */
	public static boolean containsMappingsCall(final GipsArithmeticExpr expr) {
		if (expr == null) {
			return false;
		}

		if (expr instanceof GipsBracketExpr bracketExpr) {
			return containsMappingsCall(bracketExpr.getOperand());
		} else if (expr instanceof GipsExpArithmeticExpr expExpr) {
			return containsMappingsCall(expExpr.getLeft()) || containsMappingsCall(expExpr.getRight());
		} else if (expr instanceof GipsExpressionOperand exprOp) {
			if (exprOp instanceof GipsArithmeticLiteral) {
				return false;
			} else if (exprOp instanceof GipsAttributeExpr) {
				if (exprOp instanceof GipsContextExpr conExpr) {
					if (streamContainsMappingsCall(conExpr.getStream())) {
						return true;
					}
					// Access of type 'self.value()' in context 'mapping' is allowed because the
					// transformation takes this into account
					return false;
				} else if (exprOp instanceof GipsLambdaAttributeExpression) {
					// A GipsLambdaAttributeExpression can not contain a mappings call
					return false;
				} else if (exprOp instanceof GipsLambdaSelfExpression) {
					// A GipsLambdaSelfExpression can not contain a mappings call
					return false;
				} else if (exprOp instanceof GipsMappingAttributeExpr) {
					// A GipsMappingAttributeExpr always contains a mappings call
					return true;
				} else if (exprOp instanceof GipsPatternAttributeExpr patternExpr) {
					return streamContainsMappingsCall(patternExpr.getExpr());
				} else if (exprOp instanceof GipsTypeAttributeExpr typeExpr) {
					return streamContainsMappingsCall(typeExpr.getExpr());
				}
			}
		} else if (expr instanceof GipsProductArithmeticExpr prodExpr) {
			return containsMappingsCall(prodExpr.getLeft()) || containsMappingsCall(prodExpr.getRight());
		} else if (expr instanceof GipsSumArithmeticExpr sumExpr) {
			return containsMappingsCall(sumExpr.getLeft()) || containsMappingsCall(sumExpr.getRight());
		} else if (expr instanceof GipsUnaryArithmeticExpr unExpr) {
			return containsMappingsCall(unExpr.getOperand());
		}

		throw new UnsupportedOperationException(GipslValidatorUtils.NOT_IMPLEMENTED_EXCEPTION_MESSAGE);
	}

	/**
	 * Returns true if the given stream expression contains a mapping call.
	 * 
	 * @param expr Stream expression to check.
	 * @return True if the given stream expression contains a mapping call.
	 */
	public static boolean streamContainsMappingsCall(final GipsStreamExpr expr) {
		if (expr == null) {
			return false;
		}

		if (expr instanceof GipsSelect) {
			return false;
		} else if (expr instanceof GipsStreamArithmetic arithExpr) {
			return containsMappingsCall(arithExpr.getLambda().getExpr());
		} else if (expr instanceof GipsStreamBoolExpr) {
			return false;
		} else if (expr instanceof GipsStreamNavigation nav) {
			return streamContainsMappingsCall(nav.getLeft()) || streamContainsMappingsCall(nav.getRight());
		} else if (expr instanceof GipsStreamSet set) {
			return containsMappingsCall(set.getLambda().getExpr());
		} else if (expr instanceof GipsContains contains) {
			return containsMappingsCall(contains.getExpr());
		}

		throw new UnsupportedOperationException(GipslValidatorUtils.NOT_IMPLEMENTED_EXCEPTION_MESSAGE);
	}

	/**
	 * This method ensures that no mappings will be accessed from within the mapping
	 * context. This does not include 'self'.
	 * 
	 * @param constraint Constraint to check mapping in mapping access for.
	 */
	public static void validateNoMappingAccessIfMappingContext(final GipsConstraint constraint) {
		if (constraint == null) {
			return;
		}

		// If context is not a mapping, return immediately
		if (GipslValidator.getContextType(constraint.getContext()) != GipslValidatorUtils.ContextType.MAPPING) {
			return;
		}

		final GipsBoolExpr expr = constraint.getExpr().getExpr();

		boolean leftMapping = false;
		boolean rightMapping = false;

		if (expr instanceof GipsRelExpr relExpr) {
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
			GipslValidator.err( //
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
	public static void checkConstraintIsLiteral(final GipsConstraint constraint) {
		if (constraint == null) {
			return;
		}

		if (constraint.getExpr().getExpr() instanceof GipsBooleanLiteral lit) {
			final String warning = String.valueOf(lit.isLiteral());
			GipslValidator.warn( //
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
	public static void checkConstraintUnique(final GipsConstraint constraint) {
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
				GipslValidator.warn( //
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
	public static void validateConstraintHasSelf(final GipsConstraint constraint) {
		if (constraint == null || constraint.getExpr() == null) {
			return;
		}

		final GipsBoolExpr expr = constraint.getExpr().getExpr();
		boolean leftSelf = false;
		boolean rightSelf = false;

		final GipslValidatorUtils.ContextType type = GipslValidator.getContextType(constraint.getContext());
		if (type == ContextType.GLOBAL) {
			return;
		}

		if (expr instanceof GipsRelExpr relExpr) {
			leftSelf = GipslValidator.containsSelf(relExpr.getLeft(), type);
			rightSelf = GipslValidator.containsSelf(relExpr.getRight(), type);
		} else if (expr instanceof GipsBoolExpr) {
			if (expr instanceof GipsImplicationBoolExpr impl) {
				leftSelf = GipslValidator.containsSelf(impl.getLeft(), type);
				rightSelf = GipslValidator.containsSelf(impl.getRight(), type);
			} else if (expr instanceof GipsOrBoolExpr or) {
				leftSelf = GipslValidator.containsSelf(or.getLeft(), type);
				rightSelf = GipslValidator.containsSelf(or.getRight(), type);
			} else if (expr instanceof GipsAndBoolExpr and) {
				leftSelf = GipslValidator.containsSelf(and.getLeft(), type);
				rightSelf = GipslValidator.containsSelf(and.getRight(), type);
			} else if (expr instanceof GipsNotBoolExpr not) {
				leftSelf = GipslValidator.containsSelf(not.getOperand(), type);
				rightSelf = leftSelf;
			} else if (expr instanceof GipsBracketBoolExpr brack) {
				leftSelf = GipslValidator.containsSelf(brack.getOperand(), type);
				rightSelf = leftSelf;
			} else if (expr instanceof GipsBooleanLiteral) {
				leftSelf = rightSelf = false;
			} else {
				throw new UnsupportedOperationException(GipslValidatorUtils.NOT_IMPLEMENTED_EXCEPTION_MESSAGE);
			}
		} else if (expr == null) {
			GipslValidator.err( //
					String.format(GipslValidatorUtils.CONSTRAINT_EMPTY_MESSAGE), //
					constraint, //
					GipslPackage.Literals.GIPS_CONSTRAINT__EXPR //
			);
		} else {
			throw new UnsupportedOperationException(GipslValidatorUtils.NOT_IMPLEMENTED_EXCEPTION_MESSAGE);
		}

		// Generate an error if both sides of the constraint does not contain 'self'
		if (!(leftSelf || rightSelf)) {
			GipslValidator.err( //
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
	public static void validateConstraintDynamic(final GipsConstraint constraint) {
		if (constraint == null || constraint.getExpr() == null || constraint.getExpr().getExpr() == null) {
			return;
		}

		final GipsBoolExpr expr = constraint.getExpr().getExpr();

		boolean leftDynamic = false;
		boolean rightDynamic = false;

		if (expr instanceof GipsRelExpr relExpr) {
			leftDynamic = GipslValidator.validateArithExprDynamic(relExpr.getLeft());
			rightDynamic = GipslValidator.validateArithExprDynamic(relExpr.getRight());
		} else if (expr instanceof GipsBoolExpr) {
			if (expr instanceof GipsImplicationBoolExpr impl) {
				leftDynamic = GipslValidator.validateBoolExprDynamic(impl.getLeft());
				rightDynamic = GipslValidator.validateBoolExprDynamic(impl.getRight());
			} else if (expr instanceof GipsOrBoolExpr or) {
				leftDynamic = GipslValidator.validateBoolExprDynamic(or.getLeft());
				rightDynamic = GipslValidator.validateBoolExprDynamic(or.getRight());
			} else if (expr instanceof GipsAndBoolExpr and) {
				leftDynamic = GipslValidator.validateBoolExprDynamic(and.getLeft());
				rightDynamic = GipslValidator.validateBoolExprDynamic(and.getRight());
			} else if (expr instanceof GipsNotBoolExpr not) {
				leftDynamic = GipslValidator.validateBoolExprDynamic(not.getOperand());
				rightDynamic = leftDynamic;
			} else if (expr instanceof GipsBracketBoolExpr brack) {
				leftDynamic = GipslValidator.validateBoolExprDynamic(brack.getOperand());
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
			GipslValidator.warn( //
					GipslValidatorUtils.CONSTRAINT_HAS_TWO_CONSTANT_SIDES_MESSAGE, //
					constraint, //
					GipslPackage.Literals.GIPS_CONSTRAINT__EXPR //
			);
		}
	}

}
