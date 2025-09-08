package org.emoflon.gips.core.milp.model;

import java.util.List;

import org.emoflon.gips.intermediate.GipsIntermediate.Goal;

/**
 *
 * @author SebastianE
 *
 * @param <T            extends Number> T determines the type of variables. For
 *                      now we only support integer variables.
 * @param terms         : List<MILPWeightedLinearFunction<T>> -> List of linear
 *                      functions, which in turn contain (1) variable-terms,
 *                      i.e., a sum of products, where a variable is multiplied
 *                      with some constant factor, (2) a sum of constant terms
 *                      and (3) a constant value that is multiplied with the
 *                      whole linear function (1 and 2).
 * @param constantTerms : List<MILPConstant<Double>> -> List containing constant
 *                      terms, i.e., a sum of constant values, which is added to
 *                      the sum of weighted linear functions.
 *
 *
 *                      An instance of an MILPLinearFunction represents a sum of
 *                      terms and constants. Mathematical representation of
 *                      MILPNestedLinearFunction: f(MILPWeightedLinearFunctions,
 *                      MILPConstants) = Sum_{func in
 *                      MILPWeightedLinearFunctions} ( func.weight * (Sum_{term
 *                      in func.terms} term.variable * term.constant ) +
 *                      Sum_{cterm in func.constants} ( cterm.constant ) )
 */
public record NestedLinearFunction(List<WeightedLinearFunction> linearFunctions, List<Constant> constants, Goal goal) {

}
