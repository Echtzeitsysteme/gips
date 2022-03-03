package org.emoflon.roam.core.ilp;

import java.util.List;
/**
 * 
 * @author SebastianE
 *
 * @param <T extends Number> T determines the type of variables. For now we only support integer variables. 
 * @param terms : List<ILPWeightedLinearFunction<T>> -> List of linear functions, which in turn contain (1) variable-terms, i.e., a sum of products, 
 * where a variable is multiplied with some constant factor, (2) a sum of constant terms and (3) a constant value that is multiplied with the whole linear function (1 and 2).
 * @param constantTerms : List<ILPConstant<Double>> -> List containing constant terms, i.e., a sum of constant values, 
 * which is added to the sum of weighted linear functions.
 * 
 * 
 * An instance of an ILPLinearFunction represents a sum of terms and constants. 
 * Mathematical representation of ILPNestedLinearFunction: f(ILPWeightedLinearFunctions, ILPConstants) = 
 * Sum_{func in ILPWeightedLinearFunctions} ( func.weight * (Sum_{term in func.terms} term.variable * term.constant ) 
 * + Sum_{cterm in func.constants} ( cterm.constant ) )  
 */
public record ILPNestedLinearFunction <T extends Number> (List<ILPWeightedLinearFunction<T>> linearFunctions, List<ILPConstant<Double>> constants){

}
