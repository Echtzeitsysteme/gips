package org.emoflon.roam.core.ilp;

import java.util.List;

/**
 *
 * @author SebastianE
 *
 * @param <T            extends Number> T determines the type of variables. For
 *                      now we only support integer variables.
 * @param terms         : List<ILPTerm<T, Double>> -> List containing terms,
 *                      i.e., a sum of products, where a variable is multiplied
 *                      with some constant factor.
 * @param constantTerms : List<ILPConstant<Double>> -> List containing constant
 *                      terms, i.e., a sum of constant values, which is added to
 *                      the sum of products represented by the terms parameter.
 *
 *
 *                      An instance of an ILPLinearFunction represents a sum of
 *                      terms and constants. Mathematical representation of
 *                      ILPLinearFunction: f(terms, constantTerms) = Sum_{term
 *                      in terms} ( term.variable * term.constant ) + Sum_{cterm
 *                      in constantTerms} ( cterm.constant )
 */
public record ILPLinearFunction<T extends Number> (List<ILPTerm<T, Double>> terms,
		List<ILPConstant<Double>> constantTerms) {

}
