//package org.emoflon.gips.gipsl.ui.visualization
//
//import java.util.HashSet
//import java.util.Set
//import org.eclipse.emf.common.util.EList
//import org.emoflon.ibex.gt.editor.gT.EditorCondition
//import org.emoflon.ibex.gt.editor.gT.EditorNode
//import org.emoflon.ibex.gt.editor.gT.EditorOperator
//import org.emoflon.ibex.gt.editor.gT.EditorPattern
//import org.emoflon.ibex.gt.editor.gT.EditorReference
//import org.emoflon.ibex.gt.editor.utils.GTConditionHelper
//import org.emoflon.ibex.gt.editor.utils.GTEditorPatternUtils
//import org.emoflon.ibex.gt.editor.utils.GTFlattener
//import org.emoflon.ibex.gt.editor.gT.EditorAttributeConstraint
//import org.emoflon.ibex.gt.editor.gT.EditorAttributeAssignment
//import java.util.AbstractMap.SimpleEntry
//import java.util.Map.Entry
//import org.eclipse.xtext.EcoreUtil2
//import java.util.HashMap
//import org.emoflon.ibex.gt.editor.gT.EditorCountExpression
//import java.util.Map
//import org.emoflon.ibex.gt.editor.gT.StochasticFunction
//import org.emoflon.ibex.gt.editor.gT.ArithmeticExpression
//import org.emoflon.ibex.gt.editor.ui.visualization.GTVisualizationUtils
//import org.apache.commons.lang3.StringUtils
//import org.moflon.core.utilities.EcoreUtils
//
///**
// * Utility methods to generate PlantUML code.
// */
//class GipsPlantUMLGenerator {
//	static val ContextColor = 'Black'
//	static val CreateColor = 'DarkGreen'
//	static val DeleteColor = 'Crimson'
//	static val LocalNodeColor = 'Gray'
//	static val AtrConstrColor = 'White'
//	static int MAX_STR_LENGTH = 100
//
//	/**
//	 * Returns the PlantUML code for the visualization of an empty file.
//	 */
//	static def String visualizeNothing() {
//		'''
//			title There is nothing to visualize yet.
//		'''
//	}
//
//	/**
//	 * Returns the PlantUML code for the visualization of the given pattern.
//	 */
//	static def String visualizeSelectedPattern(EditorPattern pattern) {
//		if(pattern.name === null || pattern.name == "")
//			throw new RuntimeException("GT-Visualization: Pattern name is null!")
//			
//		val flattenedPattern = new GTFlattener(pattern).getFlattenedPattern
//		'''
//			«commonLayoutSettings»
//			
//			skinparam class {
//				«patternLayoutContent()»
//			}
//			
//			«visualizePattern(flattenedPattern, "", false)»
//			
//		'''
//	}
//	
//	static def String visualizePattern(EditorPattern pattern, String namespace, boolean isSupport) {
//		if(pattern.name === null || "".equals(pattern.name) )
//			throw new RuntimeException("GT-Visualization: Pattern name is null!")
//		val hasNamespace = !"".equals(namespace)
//		val flattenedPattern = new GTFlattener(pattern).getFlattenedPattern
//		val nodeNamesInFlattenedPattern = flattenedPattern.nodes.map[it.name]
//		'''
//			namespace «pattern.name»«IF isSupport» #EEEEEE«ELSE» #FFFFFF«ENDIF»{
//				«visualizeGraph(flattenedPattern)»
//				«visualizeAttributeConstraints(flattenedPattern)»
//				«visualizeRate(flattenedPattern)»
//				«IF !pattern.parameters.isEmpty»
//				class Parameter <<Conditions>> {
//				«FOR param : pattern.parameters»
//					# «param.name» : «param.type.name»
//				«ENDFOR»
//				}
//				«ENDIF»
//				
//				«IF !pattern.conditions.isEmpty»
//				class Conditions <<Conditions>> {
//					# «GTVisualizationUtils.getConditionString(pattern)»
//				}
//				«ENDIF»
//			}
//			
//			«FOR p : getConditionPatterns(pattern)»
//				«IF !p.eIsProxy»
//				«val flattenedConditionPattern = new GTFlattener(p).getFlattenedPattern»
//					«visualizePattern(flattenedConditionPattern,"",true)»
//				«FOR node : flattenedConditionPattern.nodes»
//					«IF nodeNamesInFlattenedPattern.contains(node.name)»
//						"«IF hasNamespace»«namespace».«ENDIF»«pattern.name».«nodeName(flattenedPattern, node.name)»" #.[#335bb0].# "«IF hasNamespace»«namespace».«ENDIF»«p.name».«nodeName(node)»"
//					«ENDIF»
//				«ENDFOR»
//				«ENDIF»
//			«ENDFOR»
//			
//			«visualizeCountInvocation(flattenedPattern, namespace)»
//
//		'''
//	}
//		
//	/**
//	 * Visualizes the nodes and edges. 
//	 */
//	static def String visualizeGraph(EditorPattern pattern) {
//		val injectivityConstraints = new HashSet<Entry<EditorNode,EditorNode>>
//		pattern.nodes
//		.filter[node | node.operator != EditorOperator.CREATE]
//		.forEach[node | {
//			pattern.nodes
//			.filter[other | other.operator != EditorOperator.CREATE]
//			.filter[other | EcoreUtils.areTypesCompatible(node.type, other.type)]
//			.filter[other | node != other]
//			.forEach[other | {
//				val fwd = new SimpleEntry(node, other)
//				val bwd = new SimpleEntry(other, node)
//				if(!injectivityConstraints.contains(fwd) && !injectivityConstraints.contains(bwd))
//					injectivityConstraints.add(fwd)
//			}]
//		}]
//		'''
//			«FOR node : pattern.nodes»
//				class "«nodeName(node)»" <<«nodeSkin(node)»>> {
//					«FOR attr: node.attributes»
//						«org.emoflon.gips.gipsl.ui.visualization.GipsPlantUMLGenerator.attributeAssignment(attr)»
//					«ENDFOR»
//				}
//			«ENDFOR»
//			
//			«FOR node : pattern.nodes»
//				«FOR reference : node.references»
//					"«nodeName(node)»" -[#«referenceColor(reference)»]-> "«nodeName(reference.target)»": «referenceLabel(reference)»
//				«ENDFOR»
//			«ENDFOR»
//			
//			«FOR nodes : injectivityConstraints»
//				"«nodeName(nodes.key)»" #-[#335bb0]-# "«nodeName(nodes.value)»" : = !=
//			«ENDFOR»
//		'''
//	}
//	
//	/**
//	 * Visualizes attribute constraints. 
//	 */
//	static def String visualizeAttributeConstraints(EditorPattern pattern) {
//		if(pattern.attributeConstraints.isEmpty)
//			return ""
//		
//		return '''
//			class "Attribute Constraints" <<Conditions>>{
//			«FOR constraint : pattern.attributeConstraints»
//				«attributeConstraint(constraint)»
//			«ENDFOR»
//			}
//		'''
//	}
//	
//	def static String visualizeCountInvocation(EditorPattern pattern, String namespace) {
//			val countExpressions = new HashMap
//			val hasNamespace = !"".equals(namespace)
//			pattern.attributeConstraints.forEach[ac | {
//				GTVisualizationUtils.accumulateCountExpr(ac.lhs, countExpressions)
//				GTVisualizationUtils.accumulateCountExpr(ac.rhs, countExpressions)
//			}]
//			pattern.nodes.flatMap[node | node.attributes].forEach[attribute | {
//				GTVisualizationUtils.accumulateCountExpr(attribute.value, countExpressions)
//			}]
//			if(pattern.stochastic) {
//				if(pattern.probability instanceof StochasticFunction) {
//					val sf = pattern.probability as StochasticFunction
//					GTVisualizationUtils.accumulateCountExpr(sf.functionExpression, countExpressions)
//					if(sf.parameter !== null)
//						GTVisualizationUtils.accumulateCountExpr(sf.parameter, countExpressions)
//				} else {
//					val ae = pattern.probability as ArithmeticExpression
//					GTVisualizationUtils.accumulateCountExpr(ae, countExpressions)
//				}
//			}
//			val mappings = new HashMap<EditorCountExpression, Map<EditorNode, EditorNode>>
//			countExpressions.values.forEach[cExpr | {
//				val map = new HashMap<EditorNode, EditorNode>
//				mappings.put(cExpr, map)
//				val flattenedPattern = new GTFlattener(cExpr.invokedPatten).getFlattenedPattern
//				flattenedPattern.nodes.forEach[other | {
//					pattern.nodes.forEach[node | {
//						if(node.name.equals(other.name)) {
//							map.put(node, other)
//						}
//					}]
//				}]
//			}]
//			return '''
//			«FOR countExpr : countExpressions.values»
//				«visualizePattern(countExpr.invokedPatten,"",true)»
//				«FOR mapping : mappings.get(countExpr).entrySet»
//				"«IF hasNamespace»«namespace».«ENDIF»«pattern.name».«nodeName(mapping.key)»" #.[#335bb0].# "«IF hasNamespace»«namespace».«ENDIF»«countExpr.invokedPatten.name».«nodeName(mapping.value)»"
//				«ENDFOR»
//			«ENDFOR»
//			'''
//	}
//	
//	/**
//	 * Visualizes rule rate. 
//	 */
//	def static visualizeRate(EditorPattern pattern) {
//		if(!pattern.stochastic || pattern.probability === null)
//			return ""
//		return '''
//			class "Rule Probability" <<Conditions>>{
//				# P(..) = «GTVisualizationUtils.toString(pattern.probability)»
//			}
//		'''
//	}
//
//	/**
//	 * Prints the name and type name of the node.
//	 */
//	static def String nodeName(EditorNode node) {
//		if (node === null) {
//			return '?'
//		}
//		val type = if(node.type === null) '?' else node.type.name
//		'''«node.name»: «type»'''
//	}
//
//	/**
//	 * Prints the node with the given name in the pattern.
//	 */
//	static def String nodeName(EditorPattern pattern, String name) {
//		return nodeName(pattern.nodes.findFirst[it.name == name])
//	}
//
//	/**
//	 * Prints the skin name for the node.
//	 */
//	static def String nodeSkin(EditorNode node) {
//		if(node.local) {
//			return "LOCAL_NODE" 
//		} else {
//			return node.operator.getName
//		}
//	}
//
//	/**
//	 * Prints the attribute constraint.
//	 */
//	static def String attributeConstraint(EditorAttributeConstraint attr) {
//		val operator = '#'
//		val relation = if(attr.relation === null) '?' else attr.relation.toString
//		'''«operator» «StringUtils.abbreviateMiddle(GTVisualizationUtils.toString(attr.lhs), "...", MAX_STR_LENGTH)» «relation» «StringUtils.abbreviateMiddle(GTVisualizationUtils.toString(attr.rhs), "...", MAX_STR_LENGTH)»'''
//	}
//	
//	/**
//	 * Prints the attribute constraint.
//	 */
//	static def String attributeAssignment(EditorAttributeAssignment attr) {
//		val operator = '+'
//		val name = if(attr.attribute === null || attr.attribute.name === null) '?' else attr.attribute.name
//		val relation = ':='
//		'''«operator» «name» «relation» «StringUtils.abbreviateMiddle(GTVisualizationUtils.toString(attr.value), "...", MAX_STR_LENGTH)»'''
//	}
//
//	/**
//	 * Prints the color for the reference.
//	 */
//	static def String referenceColor(EditorReference reference) {
//		if (reference.operator == EditorOperator.CONTEXT) {
//			'''«ContextColor»'''
//		} else if (reference.operator === EditorOperator.CREATE) {
//			'''«CreateColor»'''
//		} else if (reference.operator === EditorOperator.DELETE) {
//			'''«DeleteColor»'''
//		} else {
//			''
//		}
//	}
//
//	/**
//	 * Prints the reference label in the color of the reference operator.
//	 */
//	static def String referenceLabel(EditorReference reference) {
//		val type = if(reference === null || reference.type === null) '?' else reference.type.name
//		'''<color:«referenceColor(reference)»>«type»'''
//	}
//
//	/**
//	 * Extracts the patterns from the conditions of the pattern.
//	 */
//	static def Set<EditorPattern> getConditionPatterns(EditorPattern pattern) {
//		val patterns = new HashSet
//		for (c : pattern.conditions) {
//			patterns.addAll(getConditionPatterns(c))
//		}
//		return patterns
//	}
//
//	/**
//	 * Extracts the patterns from a list of simple conditions.
//	 */
//	static def Set<EditorPattern> getConditionPatterns(EditorCondition condition) {
//		val patterns = new HashSet
//		for (c : new GTConditionHelper(condition).getApplicationConditions()) {
//			if(c.pattern.eIsProxy) {
//				EcoreUtil2.resolveLazyCrossReferences(c.eResource, null);
//			}
//			patterns.add(c.pattern)
//		}
//		return patterns
//	}
//
//	/**
//	 * Returns the PlantUML code for the visualization of the refinement hierarchy of the given patterns.
//	 */
//	static def String visualizePatternHierarchy(EList<EditorPattern> patterns) {
//		val allPatterns = new HashSet(patterns)
//		for (p : patterns.filter[p | p.name !== null]) {
//			for (s : GTEditorPatternUtils.getAllSuperPatterns(p)) {
//				if (!allPatterns.contains(s)) {
//					allPatterns.add(s)
//				}
//			}
//		}
//
//		'''
//			«commonLayoutSettings»
//			
//			left to right direction
//			
//			skinparam class {
//				BackgroundColor White
//				BorderColor Black
//				ArrowColor Black
//			}
//			
//			«FOR pattern : allPatterns.filter[p | p.name !== null]»
//				«IF pattern.abstract»abstract «ENDIF»class "«pattern.name»" «link(pattern)»
//			«ENDFOR»
//			
//			«FOR pattern : allPatterns.filter[p | p.name !== null]»
//				«FOR sup: pattern.superPatterns»
//					«IF sup.name !== null»
//						"«pattern.name»" --|> "«sup.name»"
//					«ENDIF»
//				«ENDFOR»
//			«ENDFOR»
//			
//			center footer
//				= Pattern Refinement Hierarchy
//			end footer
//		'''
//	}
//
//	/**
//	 * Prints the link to the pattern.
//	 */
//	static def link(EditorPattern pattern) {
//		val resource = pattern.eResource
//		val uri = resource.URI + '#' + resource.getURIFragment(pattern)
//		'''[[«uri»]]'''
//	}
//
//	/**
//	 * Print the common settings for all visualizations.
//	 */
//	static def String commonLayoutSettings() {
//		'''
//			hide empty members
//			hide circle
//			hide stereotype
//			
//			skinparam padding 2
//			skinparam shadowing false
//		'''
//	}
//	
//	static def String patternLayoutContent() {
//		'''
//		HeaderBackgroundColor<<CONTEXT>> «ContextColor»
//		HeaderBackgroundColor<<CREATE>> «CreateColor»
//		HeaderBackgroundColor<<DELETE>> «DeleteColor»
//		HeaderBackgroundColor<<ATR_CONSTR>> «AtrConstrColor»
//		HeaderBackgroundColor<<LOCAL_NODE>> «LocalNodeColor»
//		HeaderBackgroundColor<<Conditions>> «AtrConstrColor»
//		BorderColor<<CONTEXT>> «ContextColor»
//		BorderColor<<CREATE>> «CreateColor»
//		BorderColor<<DELETE>> «DeleteColor»
//		BorderColor<<ATR_CONSTR>> «ContextColor»
//		BorderColor<<LOCAL_NODE>> «ContextColor»
//		BorderColor<<Conditions>> «ContextColor»
//		FontColor<<ATR_CONSTR>> «ContextColor»
//		FontColor<<Conditions>> «ContextColor»
//		FontColor White
//		FontStyle<<Conditions>> bold
//		'''
//	}
//}
