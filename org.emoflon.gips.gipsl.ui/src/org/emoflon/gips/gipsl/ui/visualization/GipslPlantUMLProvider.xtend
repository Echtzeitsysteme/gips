package org.emoflon.gips.gipsl.ui.visualization

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import org.emoflon.gips.gipsl.ui.visualization.UMLContext.UMLTemplateProvider
import org.emoflon.ibex.gt.editor.gT.EditorPattern
import org.emoflon.ibex.gt.editor.ui.visualization.GTPlantUMLGenerator
import org.eclipse.emf.ecore.EAttribute
import java.util.Collection
import org.emoflon.gips.gipsl.gipsl.GipsConfig
import org.emoflon.gips.gipsl.gipsl.GipsMapping
import org.emoflon.gips.gipsl.gipsl.GipsObjective
import org.eclipse.xtext.EcoreUtil2
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunctionReference
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction
import org.emoflon.gips.gipsl.gipsl.GipsConstraint
import org.emoflon.gips.gipsl.gipsl.GipsLocalContextExpression
import com.google.inject.Inject
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider
import java.util.HashSet
import org.emoflon.ibex.gt.editor.gT.EditorNode
import org.emoflon.ibex.gt.editor.gT.EditorImport
import org.emoflon.gips.gipsl.ui.labeling.TextFormatHelper

class GipslPlantUMLProvider implements UMLTemplateProvider {
		
	@Inject
	@Accessors
	protected DefaultEObjectLabelProvider labelHelper;
	
	@Inject
	@Accessors
	protected PlantUMLPreferences preferences;
	
	def String visualizeNothing() {
		return GTPlantUMLGenerator.visualizeNothing()
	}
	
	def String visualizeCollection(Collection<EObject> collection) {

		val context = createUMLContext()
		collection.forEach[context.addReferencedElement(it)]

		val builder = new StringBuilder();

		builder.append("hide empty members").append('\n')
		builder.append("hide circle").append('\n')
//		builder.append("hide stereotype").append('\n')
		while(context.hasNonGeneratedElements()) {
			val object = context.getNextNonGeneratedElement()
			val elementUML = context.generateUML(object)
			if(elementUML !== null)
				builder.append(elementUML).append('\n')
		}

		for (String additional : context.attachAtEndOfFile) {
			if(additional !== null)
				builder.append(additional).append('\n')
		}

		return builder.toString()
	}
	
	def UMLContext createUMLContext() {
		return new UMLContext(this)
	}
	
	protected def String getNameOf(EObject object) {
		if(object === null)
			return ""
			
		val predefinedName = getDefinedNameOf(object)
		if(predefinedName !== null && !predefinedName.blank)
			return predefinedName

		val label = labelHelper.getText(object)
		if(label !== null && !label.blank)
			return label		

		return object.eClass.name
	}

	protected def <T> distinct(Iterable<? extends T> iterable) {
		iterable.groupBy[it].entrySet.map[it.key]
	}
	
	protected def String createClickableLinktTo(EObject object) {
		val resource = object.eResource
		val uri = resource.URI + '#' + resource.getURIFragment(object)
		return '''[[«uri»]]'''
	}
	
	override String generateReferenceFor(EObject eObject, long referenceCounter, UMLContext context) {
		switch(eObject){
			org.emoflon.ibex.gt.editor.gT.EditorPattern: eObject.name
			org.emoflon.gips.gipsl.gipsl.GipsLinearFunction: eObject.name
			org.emoflon.gips.gipsl.gipsl.GipsConstraint: '''constraint_«referenceCounter»'''
			default: '''ID«referenceCounter»'''
		}
	}
	
	protected def String getDefinedNameOf(EObject eObject) {
		return switch eObject {
			org.emoflon.gips.gipsl.gipsl.GipsObjective: '''Objective'''
			org.emoflon.gips.gipsl.gipsl.GipsLinearFunction: eObject.name
			org.emoflon.gips.gipsl.gipsl.GipsConfig: '''Config'''
			org.emoflon.gips.gipsl.gipsl.GipsMapping: eObject.name
			org.emoflon.gips.gipsl.gipsl.GipsConstraint: '''Constraint'''
			org.emoflon.gips.gipsl.gipsl.Package: '''Package'''
			org.emoflon.ibex.gt.editor.gT.EditorPattern: eObject.name
			org.emoflon.ibex.gt.editor.gT.EditorImport: '''Import'''
			default: null
		}
	}
	
	def boolean hasUMLRepresentation(EObject eObject) {
		return switch eObject {
			org.emoflon.gips.gipsl.gipsl.GipsObjective: true
			org.emoflon.gips.gipsl.gipsl.GipsLinearFunction: true
			org.emoflon.gips.gipsl.gipsl.GipsConfig: true
			org.emoflon.gips.gipsl.gipsl.GipsMapping: true
			org.emoflon.gips.gipsl.gipsl.GipsConstraint: true
			org.emoflon.gips.gipsl.gipsl.Package: true
			org.emoflon.ibex.gt.editor.gT.EditorPattern: true
			org.emoflon.ibex.gt.editor.gT.EditorImport: true
			default: false
		}
	}
	
	protected def String generateUMLForAttribute(EObject object, EAttribute feature) {
		var String text = null
		var value = object.eGet(feature)
		if(value !== null) {
//			text = labelProvider.getText(value)
			if(text === null)
				text = value.toString
		} else if(object.eIsSet(feature)) {
			text = '<null>'
		}

		'''
			+ **«feature.name»** : «feature.EType.name» «IF text!==null» = «text» «ENDIF»
		'''
	}
	
	// *************************************************************************************** //
	
	dispatch def String generateUML(EObject eObject, UMLContext generator) {
		'''
			class "«getNameOf(eObject)»" as «generator.getOrCreateUMLReference(eObject)» <<«eObject.class.simpleName»>>
		'''
	}

	dispatch def String generateUML(EditorPattern pattern, UMLContext generator) {
		return GTPlantUMLGenerator.visualizePattern(pattern, "", false)
	}
	
	dispatch def String generateUML(GipsConfig config, UMLContext generator) {
		'''
			class "«getNameOf(config)»" <<«config.eClass.name»>> «createClickableLinktTo(config)» {
				«FOR eFeature : config.eClass.EAllStructuralFeatures.filter[e | e instanceof EAttribute]»
					«generateUMLForAttribute(config, eFeature as EAttribute)»
				«ENDFOR»
			}
		'''
	}
	
	dispatch def String generateUML(org.emoflon.gips.gipsl.gipsl.Package gipsPackage, UMLContext generator) {
		'''
			class "«getNameOf(gipsPackage)»" <<«gipsPackage.eClass.name»>> «createClickableLinktTo(gipsPackage)» {
				+ link = «TextFormatHelper.removeQuotationMarksAtStartAndEnd(gipsPackage.name)»
			}
		'''
	}
	
	dispatch def String generateUML(EditorImport editorImport, UMLContext generator) {
		'''
			class "«getNameOf(editorImport)»" <<«editorImport.eClass.name»>> «createClickableLinktTo(editorImport)» {
				+ link = «TextFormatHelper.removeQuotationMarksAtStartAndEnd(editorImport.name)» 
			}
		'''
	}
	
	dispatch def String generateUML(GipsMapping mapping, UMLContext generator) {
		val ref = generator.getOrCreateUMLReference(mapping)

		generator.attachAtEndOfFile.add('''«ref» --> «generator.getOrCreateUMLReference(mapping.pattern)» : pattern''')
		
		'''
			class "«getNameOf(mapping)»" as «ref» <<«mapping.eClass.name»>> {
				«FOR variable : mapping.variables»
					+ «variable.name»«IF variable.bound» (bound)«ENDIF» : «variable.type.name» 
				«ENDFOR»
			}
		'''
	}
	
	dispatch def String generateUML(GipsObjective objective, UMLContext generator) {
		val ref = generator.getOrCreateUMLReference(objective)
		
		val Collection<? extends EObject> linearFunctions = EcoreUtil2.eAllContents(objective.expression)
			.filter[it instanceof GipsLinearFunctionReference]
			.map[it as GipsLinearFunctionReference]
			.map[it.function]
			.toList
			
		linearFunctions.forEach[
			generator.attachAtEndOfFile.add('''«ref» --> «generator.getOrCreateUMLReference(it)» : function''')
		]

		'''
			class "«getNameOf(objective)»" as «ref» <<«objective.eClass.name»>> {
				+ «objective.goal.class.simpleName» := «objective.goal.getName»
			}
		'''
	}
	
	
	dispatch def String generateUML(GipsLinearFunction linearFunction, UMLContext generator) {
		val nodeCollector = new GipslNodeCollector(linearFunction)
		val ref = generator.getOrCreateUMLReference(linearFunction)
		
		generator.attachAtEndOfFile.add('''«ref».context --> «generator.getOrCreateUMLReference(linearFunction.context)» : context''')
		nodeCollector.mappings.forEach[mapping |
			generator.attachAtEndOfFile.add('''«ref».«mapping.name» --> «generator.getOrCreateUMLReference(mapping)» : mapping''')
		]
		
		val editorNodes = new HashSet<EditorNode>()
		if(preferences.includeReferencedNodesByContext)
			editorNodes.addAll(nodeCollector.contextToEditorNodes);
		if(preferences.includedReferencedNodesByBody)
			editorNodes.addAll(nodeCollector.mappingToEditorNodes.values.flatten);
		
		'''
			namespace «getNameOf(linearFunction)» {
				class "«getNameOf(linearFunction.context)»" as «ref».context <<GipsContext>> {}
				class "Function" as «ref».body <<«linearFunction.eClass.name»>> {}
				
				«IF !linearFunction.constants.empty»
					class "Constants" as «ref».constants {
						«FOR constant : linearFunction.constants»
							+ «constant.name» «IF !nodeCollector.usedConstants.contains(constant)»(**unused**)«ENDIF»
						«ENDFOR»	
					}
				«ENDIF»
				
				«FOR node : editorNodes»
					class "«node.name»" as «ref».«node.name»
				«ENDFOR»
				
				«IF preferences.includeReferencedNodesByContext»
					«FOR node : nodeCollector.contextToEditorNodes»					
						«ref».context --> «ref».«node.name» : uses
					«ENDFOR»
				«ENDIF»
				
				«FOR mapping : nodeCollector.mappings»
					class "«mapping.name»" as «ref».«mapping.name» <<«mapping.eClass.name»>>
					«ref».body --> «ref».«mapping.name» : uses
					
					«IF preferences.includedReferencedNodesByBody»		
						«FOR node : nodeCollector.mappingToEditorNodes.get(mapping)»
							«ref».«mapping.name» --> «ref».«node.name» : uses
						«ENDFOR»
					«ENDIF»
				«ENDFOR»
				
				«IF !EcoreUtil2.getAllContentsOfType(linearFunction.expression, typeof(GipsLocalContextExpression)).empty»
					«ref».body --> «ref».context : uses
				«ELSE»
					«ref».body --> «ref».context : **unused**
				«ENDIF»
			}
		'''
	}
	
	dispatch def String generateUML(GipsConstraint constraint, UMLContext generator) {
		val nodeCollector = new GipslNodeCollector(constraint)		 
		val ref = generator.getOrCreateUMLReference(constraint)
				
		generator.attachAtEndOfFile.add('''«ref».context --> «generator.getOrCreateUMLReference(constraint.context)» : context''')
		nodeCollector.mappings.forEach[mapping |
			generator.attachAtEndOfFile.add('''«ref».«mapping.name» --> «generator.getOrCreateUMLReference(mapping)» : mapping''')
		]
		
		val editorNodes = new HashSet<EditorNode>()
		if(preferences.includeReferencedNodesByContext)
			editorNodes.addAll(nodeCollector.contextToEditorNodes);
		if(preferences.includedReferencedNodesByBody)
			editorNodes.addAll(nodeCollector.mappingToEditorNodes.values.flatten);
		
		'''
			namespace «ref» {
				class "«getNameOf(constraint.context)»" as «ref».context <<GipsContext>> {}
				class "Constraint" as «ref».body <<«constraint.eClass.name»>> {}				
				
				«IF !constraint.constants.empty»
					class "Constants" as «ref».constants {
						«FOR constant : constraint.constants»
							+ «constant.name» «IF !nodeCollector.usedConstants.contains(constant)»(**unused**)«ENDIF»
						«ENDFOR»	
					}
				«ENDIF»
				
				«FOR node : editorNodes»
					class "«node.name»" as «ref».«node.name»
				«ENDFOR»
				
				«IF preferences.includeReferencedNodesByContext»
					«FOR node : nodeCollector.contextToEditorNodes»					
						«ref».context --> «ref».«node.name» : uses
					«ENDFOR»
				«ENDIF»
				
				«FOR mapping : nodeCollector.mappings»
					class "«mapping.name»" as «ref».«mapping.name» <<«mapping.eClass.name»>>
					«ref».body --> «ref».«mapping.name» : uses
					
					«IF preferences.includedReferencedNodesByBody»
						«FOR node : nodeCollector.mappingToEditorNodes.get(mapping)»
							«ref».«mapping.name» --> «ref».«node.name» : uses
						«ENDFOR»
					«ENDIF»
				«ENDFOR»
				
				«IF !EcoreUtil2.getAllContentsOfType(constraint.expression, typeof(GipsLocalContextExpression)).empty»
					«ref».body --> «ref».context : uses
				«ELSE»
					«ref».body --> «ref».context : **unused**
				«ENDIF»
			}
		'''
	}
	
}