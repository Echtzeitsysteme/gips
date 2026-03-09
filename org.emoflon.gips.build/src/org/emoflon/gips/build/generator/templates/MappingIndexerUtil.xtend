package org.emoflon.gips.build.generator.templates

import java.util.Collections
import java.util.HashSet
import java.util.LinkedList
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryOperator
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ContextReference
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import org.emoflon.gips.intermediate.GipsIntermediate.MappingReference
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator
import org.emoflon.gips.intermediate.GipsIntermediate.SetFilter
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation
import org.emoflon.gips.intermediate.GipsIntermediate.MemberReference
import org.emoflon.gips.intermediate.GipsIntermediate.NodeExpression
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode

class MappingIndexerUtil {

	@org.eclipse.xtend.lib.annotations.Data
	static class IndexablePair {
		ContextReference left
		ContextReference Right
	}

	private static def boolean isNodeReference(ContextReference reference) {
		if(reference instanceof MemberReference)
			if(reference.member instanceof NodeExpression)
				return reference.member.next === null

		return false
	}

	private static def boolean isNodeOrContextReference(EObject obj) {
		if(obj instanceof ContextReference)
			return isNodeReference(obj) || obj.class.equals(ContextReference)
		return false
	}

	private static def IBeXNode getNode(ContextReference reference) {
		if(reference instanceof MemberReference) {
			if(reference.member instanceof NodeExpression) {
				val nodeExpression = reference.member as NodeExpression
				return nodeExpression.node;
			}
		}

		return null;
	}

	/**
	 * Returns `true` if the given set operation fulfills all conditions for the context of the
	 * respective constraint to be indexed.
	 */
	static def boolean isContextIndexerApplicable(SetOperation expr) {
		if(expr instanceof SetFilter) {
			// Single relational expression
			if(expr.expression instanceof RelationalExpression) {
				var relExpr = expr.expression as RelationalExpression
				return isContextIndexerApplicable(relExpr);
			} // Multiple expressions composed
			else if(expr.expression instanceof BooleanBinaryExpression) {
				var boolBinExpr = expr.expression as BooleanBinaryExpression
				return isContextIndexerApplicable(boolBinExpr);
			}
		}
		return false;
	}

	/**
	 * Returns `true` if the given boolean expression fulfills all conditions for the context of
	 * the respective constraint to be indexed.
	 */
	static def boolean isContextIndexerApplicable(BooleanExpression expr) {
		// Single relational expression only
		if(expr instanceof RelationalExpression) {
			return isContextIndexerApplicable(expr)
		} // Composition of multiple boolean expressions
		else if(expr instanceof BooleanBinaryExpression) {
			return isContextIndexerApplicable(expr)
		}
		return false;
	}

	/**
	 * Returns `true` if the given boolean binary expression fulfills all conditions for the context
	 * of the respective constraint to be indexed.
	 */
	static def boolean isContextIndexerApplicable(BooleanBinaryExpression expr) {
		switch (expr.operator) {
			case AND: {
				var lhs = isContextIndexerApplicable(expr.lhs)
				var rhs = isContextIndexerApplicable(expr.rhs)
				return lhs || rhs
			}
			default: {
				// Do nothing
			}
		}
		return false;
	}

	/**
	 * Returns `true` if the given relational expression fulfills all conditions for the context of
	 * the respective constraint to be indexed.
	 */
	static def boolean isContextIndexerApplicable(RelationalExpression relExpr) {
		// Only if the operator is `EQUAL`
		if(relExpr.operator.equals(RelationalOperator.EQUAL)) {
			if(isNodeOrContextReference(relExpr.lhs) && isNodeOrContextReference(relExpr.rhs)) {
				var lNode = relExpr.lhs as ContextReference
				var rNode = relExpr.rhs as ContextReference

				// One side must be local and one must not be local		
				return (lNode.local && !rNode.local) || (!lNode.local && rNode.local)
			}
		}
		return false;
	}

	/**
	 * This method converts a given list of context node names to a getter string.
	 * 
	 * Example input: "{a, b, cde}"
	 * Example output: "getA(), getB(), getCde()"
	 */
	static def String generateContextNodeAccessToGetterCalls(List<String> contextNodeAccesses) {
		var foundGetters = ''''''
		for (var i = 0; i < contextNodeAccesses.size; i++) {
			var candidate = contextNodeAccesses.get(i).toFirstUpper
			foundGetters += '''context.get«candidate»()'''
			if(i < contextNodeAccesses.size - 1) {
				foundGetters += ''', '''
			}
		}
		return foundGetters
	}

	static def Set<String> findIndexableSetNodes(MappingReference mappingReference) {
		val results = new HashSet<String>()

		val toBeScanned = new LinkedList<EObject>()
		val root = EcoreUtil.getRootContainer(mappingReference, true) as GipsIntermediateModel
		toBeScanned.addAll(root.constraints)
		toBeScanned.addAll(root.functions)
		toBeScanned.addAll(root.objective)

		val iterator = EcoreUtil.getAllProperContents(toBeScanned, true)
		while(iterator.hasNext) {
			val eObject = iterator.next;
			if(eObject instanceof MappingReference) {
				iterator.prune

				if(eObject.mapping === mappingReference.mapping && eObject.setExpression.setOperation !== null) {
					val references = findIndexablePairs(eObject.setExpression.setOperation)
					results.addAll(getSetNodeNames(references))
				}
			}
		}

		return results
	}

	static def List<String> getSetNodeNames(List<IndexablePair> indexablePairs) {
		val results = new LinkedList<String>()

		// we only care for non-local nodes (-> element.node.x) when the other side is  local! (-> context.node.y)
		for (pair : indexablePairs) {
			var IBeXNode node = null
			if(pair.left.local && !pair.Right.local)
				node = getNode(pair.Right)
			else if(!pair.left.local && pair.Right.local)
				node = getNode(pair.left)

			if(node !== null)
				results.add(node.name)
		}

		return results
	}

	static def List<String> getContextNodeNames(List<IndexablePair> indexablePairs) {
		val results = new LinkedList<String>()

		// we only care for local nodes (-> context.node.x) when the other side is not local! (-> element.node.y)
		for (pair : indexablePairs) {
			var IBeXNode node = null
			if(pair.left.local && !pair.Right.local)
				node = getNode(pair.left)
			else if(!pair.left.local && pair.Right.local)
				node = getNode(pair.Right)

			if(node !== null)
				results.add(node.name)
		}

		return results
	}

	/**
	 * Returns a list of RelationalExpressions which can be indexed.
	 * The requirements to be included are:
	 * <ul>
	 * <li> the expression must be a conjunction
	 * <li> the relational operator must be `EQUAL`
	 * <li> both sides must either be a context reference or reference a node
	 * <li> both respective sides must not have an attribute access
	 * </ul>
	 * 
	 * Therefore, this method only captures expressions like `filter(element.nodes.a == context.nodes.b)`.
	 * 
	 */
	static def dispatch List<IndexablePair> findIndexablePairs(EObject expr) {
		return Collections.emptyList
	}

	static def dispatch List<IndexablePair> findIndexablePairs(SetFilter expr) {
		val results = new LinkedList<IndexablePair>()

		results.addAll(findIndexablePairs(expr.expression))
		if(expr.next !== null)
			results.addAll(findIndexablePairs(expr.next))

		return results
	}

	static def dispatch List<IndexablePair> findIndexablePairs(BooleanBinaryExpression expr) {
		val results = new LinkedList<IndexablePair>()

		if(expr.operator == BooleanBinaryOperator.AND) {
			results.addAll(findIndexablePairs(expr.lhs))
			results.addAll(findIndexablePairs(expr.rhs))
		}

		return results
	}

	static def dispatch List<IndexablePair> findIndexablePairs(RelationalExpression expr) {
		val results = new LinkedList<IndexablePair>()

		if(expr.operator === RelationalOperator.EQUAL) {
			var isMatch = isNodeOrContextReference(expr.lhs) && isNodeOrContextReference(expr.rhs)
			if(isMatch)
				results.add(new IndexablePair(expr.lhs as ContextReference, expr.rhs as ContextReference))
		}

		return results
	}

}
