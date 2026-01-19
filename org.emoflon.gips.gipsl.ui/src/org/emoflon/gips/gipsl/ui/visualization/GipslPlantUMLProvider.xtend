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
import org.emoflon.gips.gipsl.gipsl.GipsTypeExtension
import org.eclipse.xtext.EcoreUtil2
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunctionReference
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction
import org.emoflon.gips.gipsl.gipsl.GipsConstraint
import org.emoflon.gips.gipsl.gipsl.GipsLocalContextExpression
import com.google.inject.Inject
import java.util.HashSet
import org.emoflon.ibex.gt.editor.gT.EditorNode
import org.emoflon.ibex.gt.editor.gT.EditorImport
import org.emoflon.gips.gipsl.ui.labeling.TextFormatHelper
import org.eclipse.jface.viewers.ILabelProvider
import java.util.LinkedList
import org.eclipse.emf.ecore.EClass

class GipslPlantUMLProvider implements UMLTemplateProvider {
		
	@Inject(optional=true)
	@Accessors
	protected ILabelProvider labelHelper;
	
	@Inject
	@Accessors
	protected PlantUMLPreferences preferences;
	
	def String visualizeNothing() {
		return GTPlantUMLGenerator.visualizeNothing()
	}
	
	def String visualizeCollection(Collection<EObject> collection) {

		val context = createUMLContext()
		collection.forEach[context.addReferencedElement(it)]

		val umlContent = new LinkedList<String>();
		while(context.hasNonGeneratedElements()) {
			val object = context.getNextNonGeneratedElement()
			val elementUML = context.generateUML(object)
			if(elementUML !== null)
				umlContent.add(elementUML)
		}

		for (String additionalLines : context.attachAtEndOfFile) {
			if(additionalLines !== null)
				umlContent.add(additionalLines)
		}

		'''
			«plantUMLStyle»
			
			«FOR element : umlContent»
				«element»
			«ENDFOR»			
		'''
	}
	
	def UMLContext createUMLContext() {
		return new UMLContext(this)
	}
	
	protected def boolean isNullOrEmpty(String str){
		return str === null ? true : str.blank
	}
	
	protected def String getNameOf(EObject object) {
		if(object === null)
			return ""
			
		val predefinedName = getDefinedNameOf(object)
		if(predefinedName !== null && !predefinedName.blank)
			return predefinedName

		if(labelHelper !== null){
			val label = labelHelper.getText(object)
			if(label !== null && !label.blank)
				return label
		}	

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
			org.emoflon.gips.gipsl.gipsl.GipsTypeExtension: eObject.ref.name
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
			org.emoflon.gips.gipsl.gipsl.GipsTypeExtension: true
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
			# «feature.name» : «feature.EType.name»«IF text!==null» := «text»«ENDIF»
		'''
	}
	
	// *************************************************************************************** //
	
	def String plantUMLStyle(){
		'''
			hide empty members
			hide circle
			
			skinparam {
				«IF preferences.isCustomFontSet»
					defaultFontName «preferences.fontName»
					defaultFontSize «preferences.fontSize»
				«ENDIF»
			}
		'''
	}
	
	dispatch def String generateUML(EObject eObject, UMLContext generator) {
		'''
			class "«getNameOf(eObject)»" as «generator.getOrCreateUMLReference(eObject)» <<«eObject.class.simpleName»>>
		'''
	}
	
	dispatch def String generateUML(EClass eClass, UMLContext generator){
		'''
			class "«getNameOf(eClass)»" as «generator.getOrCreateUMLReference(eClass)» <<«eClass.eClass.name»>> {
				«FOR attribute : eClass.EAllAttributes»
					+ «attribute.name» : «attribute.EType.name»«IF attribute.many»[]«ENDIF»
				«ENDFOR»
				«FOR references : eClass.EAllReferences»
					+ «references.name» : «references.EType.name»«IF references.many»[]«ENDIF»
				«ENDFOR»
			}
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

		if(mapping.pattern !== null)
			generator.attachAtEndOfFile.add('''«ref» --> «generator.getOrCreateUMLReference(mapping.pattern)» : pattern''')
		
		'''
			class "«getNameOf(mapping)»" as «ref» <<«mapping.eClass.name»>> {
				«FOR variable : mapping.variables»
					+ «variable.name»«IF variable.bound» (bound)«ENDIF» : «variable.type.name» 
				«ENDFOR»
			}
		'''
	}
	
	dispatch def String generateUML(GipsTypeExtension typeExt, UMLContext generator){
		val ref = generator.getOrCreateUMLReference(typeExt)
		
		generator.attachAtEndOfFile.add('''«ref» --> «generator.getOrCreateUMLReference(typeExt.ref)» : extends''')
		
		'''
			class "«getNameOf(typeExt)»" as «ref» <<«typeExt.eClass.name»>> {
				«FOR variable : typeExt.variables»
					+ «variable.name» : «variable.type.name» «IF variable.bound» **boundTo** «variable.attribute.name»«ENDIF» 
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
		
		if(linearFunction.context !== null)
			generator.attachAtEndOfFile.add('''«ref».context --> «generator.getOrCreateUMLReference(linearFunction.context)» : context''')
		nodeCollector.mappings.forEach[mapping |
			if(!isNullOrEmpty(mapping.name))
				generator.attachAtEndOfFile.add('''«ref».«mapping.name» --> «generator.getOrCreateUMLReference(mapping)» : mapping''')
		]
		
		nodeCollector.usedTypeExtensions.forEach[typeExtension |
			generator.attachAtEndOfFile.add('''«ref».«typeExtension.ref.name» --> «generator.getOrCreateUMLReference(typeExtension)» : type''')
		]
		
		val editorNodes = new HashSet<EditorNode>()
		if(preferences.includeReferencedNodesByContext)
			editorNodes.addAll(nodeCollector.contextToEditorNodes);
		if(preferences.includedReferencedNodesByBody)
			editorNodes.addAll(nodeCollector.mappingToEditorNodes.values.flatten);
		
		'''
			namespace «getNameOf(linearFunction)» {
				«IF linearFunction.context !== null»
					class "«getNameOf(linearFunction.context)»" as «ref».context <<GipsContext>> {}
				«ENDIF»				
				class "Function" as «ref».body <<«linearFunction.eClass.name»>> {}
				
				«IF !linearFunction.constants.empty»
					class "Constants" as «ref».constants {
						«FOR constant : linearFunction.constants»
							+ «constant.name» «IF !nodeCollector.usedConstants.contains(constant)»(**unused**)«ENDIF»
						«ENDFOR»	
					}
				«ENDIF»
				
				«FOR node : editorNodes»
					«IF !isNullOrEmpty(node.name)»
						class "«node.name»" as «ref».«node.name»
					«ENDIF»
				«ENDFOR»
				
				«IF preferences.includeReferencedNodesByContext»
					«FOR node : nodeCollector.contextToEditorNodes»	
						«IF !isNullOrEmpty(node.name)»
							«ref».context --> «ref».«node.name» : uses
						«ENDIF»
					«ENDFOR»
				«ENDIF»
				
				«FOR mapping : nodeCollector.mappings»
					«IF !isNullOrEmpty(mapping.name)»
						class "«mapping.name»" as «ref».«mapping.name» <<«mapping.eClass.name»>>
						«ref».body --> «ref».«mapping.name» : uses
						
						«IF preferences.includedReferencedNodesByBody»
							«FOR node : nodeCollector.mappingToEditorNodes.get(mapping)»
								«IF !isNullOrEmpty(node.name)»
									«ref».«mapping.name» --> «ref».«node.name» : uses
								«ENDIF»
							«ENDFOR»
						«ENDIF»
					«ENDIF»
				«ENDFOR»
				
				«FOR typeExtension : nodeCollector.usedTypeExtensions»
					class "«typeExtension.ref.name»" as «ref».«typeExtension.ref.name» <<«typeExtension.eClass.name»>>
					«ref».body --> «ref».«typeExtension.ref.name» : uses
				«ENDFOR»
				
				«IF linearFunction.context !== null && linearFunction.expression !== null»
					«IF !EcoreUtil2.getAllContentsOfType(linearFunction.expression, typeof(GipsLocalContextExpression)).empty»
						«ref».body --> «ref».context : uses
					«ELSE»
						«ref».body --> «ref».context : **unused**
					«ENDIF»
				«ENDIF»
			}
		'''
	}
	
	dispatch def String generateUML(GipsConstraint constraint, UMLContext generator) {
		val nodeCollector = new GipslNodeCollector(constraint)		 
		val ref = generator.getOrCreateUMLReference(constraint)
		
		if(constraint.context !== null)
			generator.attachAtEndOfFile.add('''«ref».context --> «generator.getOrCreateUMLReference(constraint.context)» : context''')
		nodeCollector.mappings.forEach[mapping |
			if(!isNullOrEmpty(mapping.name))
				generator.attachAtEndOfFile.add('''«ref».«mapping.name» --> «generator.getOrCreateUMLReference(mapping)» : mapping''')
		]
		
		nodeCollector.usedTypeExtensions.forEach[typeExtension |
			generator.attachAtEndOfFile.add('''«ref».«typeExtension.ref.name» --> «generator.getOrCreateUMLReference(typeExtension)» : type''')
		]
		
		val editorNodes = new HashSet<EditorNode>()
		if(preferences.includeReferencedNodesByContext)
			editorNodes.addAll(nodeCollector.contextToEditorNodes);
		if(preferences.includedReferencedNodesByBody)
			editorNodes.addAll(nodeCollector.mappingToEditorNodes.values.flatten);
		
		'''
			namespace «ref» {
				«IF constraint.context !== null»
					class "«getNameOf(constraint.context)»" as «ref».context <<GipsContext>> {}
				«ENDIF»				
				class "Constraint" as «ref».body <<«constraint.eClass.name»>> {}				
				
				«IF !constraint.constants.empty»
					class "Constants" as «ref».constants {
						«FOR constant : constraint.constants»
							+ «constant.name» «IF !nodeCollector.usedConstants.contains(constant)»(**unused**)«ENDIF»
						«ENDFOR»	
					}
				«ENDIF»
				
				«FOR node : editorNodes»
					«IF !isNullOrEmpty(node.name)»
						class "«node.name»" as «ref».«node.name»
					«ENDIF»
				«ENDFOR»
				
				«IF preferences.includeReferencedNodesByContext»
					«FOR node : nodeCollector.contextToEditorNodes»	
						«IF !isNullOrEmpty(node.name)»
							«ref».context --> «ref».«node.name» : uses
						«ENDIF»
					«ENDFOR»
				«ENDIF»
				
				«FOR mapping : nodeCollector.mappings»
					«IF !isNullOrEmpty(mapping.name)»
						class "«mapping.name»" as «ref».«mapping.name» <<«mapping.eClass.name»>>
						«ref».body --> «ref».«mapping.name» : uses
						
						«IF preferences.includedReferencedNodesByBody»
							«FOR node : nodeCollector.mappingToEditorNodes.get(mapping)»
								«IF !isNullOrEmpty(node.name)»
									«ref».«mapping.name» --> «ref».«node.name» : uses
								«ENDIF»
							«ENDFOR»
						«ENDIF»
					«ENDIF»
				«ENDFOR»
				
				«FOR typeExtension : nodeCollector.usedTypeExtensions»
					class "«typeExtension.ref.name»" as «ref».«typeExtension.ref.name» <<«typeExtension.eClass.name»>>
					«ref».body --> «ref».«typeExtension.ref.name» : uses
				«ENDFOR»
				
				«IF constraint.context !== null && constraint.expression !== null»
					«IF !EcoreUtil2.getAllContentsOfType(constraint.expression, typeof(GipsLocalContextExpression)).empty»
						«ref».body --> «ref».context : uses
					«ELSE»
						«ref».body --> «ref».context : **unused**
					«ENDIF»
				«ENDIF»
			}
		'''
	}
	
}