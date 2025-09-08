package org.emoflon.gips.core.milp.model;

/**
 *
 * @author SebastianE
 *
 * @param <T     extends Number> T determines the type of variables. For now we
 *               only support integer variables.
 * @param terms  : MILPLinearFunction<T> -> -> List containing (1) terms, i.e.,
 *               a sum of products, where a variable is multiplied with some
 *               constant factor., and (2) a sum of constant terms.
 * @param weight : double -> A constant value that is multiplied with the linear
 *               function.
 *
 *
 *               An instance of an MILPWeightedLinearFunction represents a sum
 *               of terms and constants. Mathematical representation of weighted
 *               MILPLinearFunction: f(linearFunction, weight) = weight *
 *               (Sum_{term in linearFunction.terms} ( term.variable *
 *               term.constant ) + Sum_{cterm in linearFunction.constants} (
 *               cterm.constant ) )
 */
public record WeightedLinearFunction(LinearFunction linearFunction, double weight) {

}
