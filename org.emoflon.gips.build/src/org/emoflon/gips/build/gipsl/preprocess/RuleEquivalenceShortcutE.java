package org.emoflon.gips.build.gipsl.preprocess;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.gipsl.generator.GeneratorUtil;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsHelpVariables;
import org.emoflon.gips.gipsl.gipsl.GipsLocalContextExpression;
import org.emoflon.gips.gipsl.gipsl.GipsProductOperator;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipsSumOperator;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.GipsVariableReferenceExpression;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.emoflon.gips.gipsl.gipsl.ImplicationOperator;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.gipsl.impl.GipsConstraintImpl;
import org.emoflon.gips.gipsl.scoping.GipslScopeContextUtil;
import org.emoflon.gips.gipsl.special.pattern.EquivalenceShortcutE;

/**
 * 
 * Input:
 * <ul>
 * <li>A == 1 <-> B == (1|0) (& C == (1|0) & ... )
 * </ul>
 * 
 * With:
 * <ul>
 * <li>M >= 1
 * <li>A in [0, M]
 * <li>B in [0, 1]
 * </ul>
 * 
 * Output:
 * <ul>
 * <li>(1-B) <= A
 * <li>A <= (1-B) * M
 * </ul>
 * 
 */
public class RuleEquivalenceShortcutE implements PreprocessorRule {

	private final EquivalenceShortcutE pattern = new EquivalenceShortcutE();

	public GipsBooleanExpression tryRule(GipslFactory factory, GipsBooleanExpression expression) {
		if (!pattern.matchPattern(expression))
			return null;

		List<GipsArithmeticExpression> longSide = new LinkedList<>();
		List<GipsBooleanExpression> conjuncts = new LinkedList<>();

		for (var match : pattern.getOtherNodes()) {
			if (!match.equalsZero) {
				// case: X == 1
				longSide.add(match.node); // store X
			} else {
				// case: X == 0

				// replace X == 0 with substitution S == 1
				// with S == 1 <-> X == 0

				// Define S
				GipsHelpVariables helperVariable = factory.createGipsHelpVariables();
				GipsConstraint constraint = GipslScopeContextUtil.getContainer(expression, GipsConstraintImpl.class);
				String varName = String.format("_SUBSTITUTION_PLACEHOLDER_%d", constraint.getHelpVariables().size());

				helperVariable.setName(varName);
				helperVariable.setType(EcorePackage.Literals.EBOOLEAN);
				constraint.getHelpVariables().add(helperVariable);

				// Use S instead of X

				GipsVariableReferenceExpression helperVarRef = factory.createGipsVariableReferenceExpression();
				helperVarRef.setIsGenericValue(true);
				helperVarRef.setVariable(helperVariable);

				GipsLocalContextExpression localContext = factory.createGipsLocalContextExpression();
				localContext.setExpression(helperVarRef);

				GipsValueExpression helperExpression = factory.createGipsValueExpression();
				helperExpression.setValue(localContext);

				longSide.add(helperExpression);

				// Now add S == 1 <-> X == 0

				// S == 1
				GipsRelationalExpression subEquals1 = factory.createGipsRelationalExpression();
				subEquals1.setOperator(RelationalOperator.EQUAL);
				subEquals1.setLeft(helperExpression);
				subEquals1.setRight(GeneratorUtil.createIntegerLiteral(factory, 1));

				// X == 0
				GipsRelationalExpression xEquals0 = factory.createGipsRelationalExpression();
				xEquals0.setOperator(RelationalOperator.EQUAL);
				xEquals0.setLeft(EcoreUtil.copy(match.node));
				xEquals0.setRight(GeneratorUtil.createIntegerLiteral(factory, 0));

				// S == 1 <-> X == 0
				GipsBooleanImplication implication = factory.createGipsBooleanImplication();
				implication.setOperator(ImplicationOperator.EQUIVALENCE);
				implication.setLeft(subEquals1);
				implication.setRight(xEquals0);

				// include in new expression
				conjuncts.add(implication);
			}
		}

		if (longSide.size() == 1) {
			var relational = factory.createGipsRelationalExpression();
			relational.setOperator(RelationalOperator.EQUAL);
			relational.setLeft(EcoreUtil.copy(pattern.getNodeA().node));
			relational.setRight(EcoreUtil.copy(longSide.getFirst()));
			conjuncts.add(relational);

		} else {
			// B + C + ... + (1-n) <= A
			{
				List<GipsArithmeticExpression> summands = new ArrayList<>(longSide.size() + 1);
				for (var element : longSide)
					summands.add(EcoreUtil.copy(element));

				var minusN = GeneratorUtil.createIntegerLiteral(factory, 1 - longSide.size());
				summands.add(minusN);

				// B + C + ... + (1-n)
				var sum = GeneratorUtil.sum(factory, GipsSumOperator.PLUS, summands);

				// B + C + ... + (1-n) <= A
				var relational = factory.createGipsRelationalExpression();
				relational.setOperator(RelationalOperator.SMALLER_OR_EQUAL);
				relational.setLeft(sum);
				relational.setRight(EcoreUtil.copy(pattern.getNodeA().node));
				conjuncts.add(relational);
			}

			// n * A <= B + C + ...
			{
				// B + C + ...
				List<GipsArithmeticExpression> summands = new ArrayList<>(longSide.size());
				for (var element : longSide)
					summands.add(EcoreUtil.copy(element));

				var sum = GeneratorUtil.sum(factory, GipsSumOperator.PLUS, summands);

				// n
				var n = GeneratorUtil.createIntegerLiteral(factory, longSide.size());

				// n * A
				var nProduct = factory.createGipsArithmeticProduct();
				nProduct.setOperator(GipsProductOperator.MULT);
				nProduct.setLeft(EcoreUtil.copy(pattern.getNodeA().node));
				nProduct.setRight(n);

				// n * A <= B + C + ...
				var relational = factory.createGipsRelationalExpression();
				relational.setOperator(RelationalOperator.SMALLER_OR_EQUAL);
				relational.setLeft(nProduct);
				relational.setRight(sum);
				conjuncts.add(relational);
			}
		}

		return GeneratorUtil.conjunction(factory, conjuncts);
	}

}
