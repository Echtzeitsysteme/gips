package org.emoflon.gips.gipsl.ui.visualization

import org.emoflon.ibex.gt.editor.ui.visualization.GTPlantUMLGenerator
import org.emoflon.ibex.gt.editor.gT.EditorPattern
import java.util.Collection
import org.eclipse.emf.ecore.EObject
import org.emoflon.gips.gipsl.gipsl.EditorGTFile
import java.util.LinkedList
import org.eclipse.jface.viewers.ILabelProvider
import com.google.inject.Inject
import org.eclipse.xtend.lib.annotations.Accessors
import org.emoflon.gips.gipsl.gipsl.GipsConfig
import org.emoflon.gips.gipsl.gipsl.GipslPackage
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EAttribute
import org.emoflon.gips.gipsl.gipsl.GipsConstraint
import java.util.Objects
import java.util.HashSet
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EClass
import java.util.function.Function
import java.util.Queue
import java.util.ArrayList
import org.emoflon.gips.gipsl.gipsl.GipsMapping
import java.util.Collections
import org.emoflon.gips.gipsl.gipsl.GipsSetExpression
import org.emoflon.gips.gipsl.gipsl.GipsMappingExpression
import org.eclipse.xtext.EcoreUtil2
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction
import org.emoflon.gips.gipsl.gipsl.GipsObjective
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunctionReference
import org.moflon.core.utilities.EcoreUtils
import org.eclipse.emf.ecore.util.FeatureMap

class PlantUMLTemplateProvider {

	static class UMLContext {
		Collection<EObject> generated = new HashSet<EObject>()
		Collection<EObject> pending = new HashSet<EObject>()
		Map<EObject, String> refs = new HashMap<EObject, String>()

		PlantUMLTemplateProvider provider

		@Accessors
		Collection<String> appendAtEnd = new LinkedList<String>()

		int idCounter = 0

		@Accessors
		boolean includedReferencedElements = true

		new(PlantUMLTemplateProvider provider) {
			this.provider = Objects.requireNonNull(provider, "provider")
		}

		def String getUMLRef(EObject object) {
			return refs.computeIfAbsent(object, [ key |
				queueElementForGenerationIfNeeded(object)
				val ref = computeNextID(object)
				if(refs.containsKey(ref))
					throw new IllegalStateException("ID already in use: " + ref)
				return ref
			])
		}

		def protected String computeNextID(EObject object) {
//			val uuid = java.util.UUID.randomUUID()
//			return uuid.toString.replace('-','')			
			return '''ID«idCounter++»'''
		}

		def String generateUML(EObject eObject) {
			if(eObject === null)
				return null

			val code = provider.generateUML(eObject, this, includedReferencedElements)
			markElementAsGenerated(eObject)

			return code
		}

		def markElementAsGenerated(EObject object) {
			if(generated.add(object))
				pending.remove(object)
		}

		def queueElementForGenerationIfNeeded(EObject object) {
			if(!generated.contains(object))
				pending.add(object)
		}

		def queueElementForGenerationIfNeeded(Collection<EObject> objects) {
			for (object : objects)
				queueElementForGenerationIfNeeded(object)
		}

		def anyElementPending() {
			return pending.empty
		}

		def getNextPendingElement() {
			return pending.iterator.next
		}

	}

	private static abstract class PrintHelper {
		private Class<?> matches;

		def String getNameOf(EObject obj);

		def boolean isMatch(EObject obj);
	}

	@Inject
	@Accessors
	private ILabelProvider labelProvider;

	new() {
	}

	def String visualizeNothing() {
		return GTPlantUMLGenerator.visualizeNothing()
	}

	def String visualizeOverview(EditorGTFile editorGTFile) {
		val objects = new LinkedList<EObject>();
		objects.addAll(editorGTFile.eContents)
//		objects.add(editorGTFile.config)	
//		objects.addAll(editorGTFile.constraints);
		'''		
			«FOR object : objects»
				«IF hasUMLRepresentation(object)»
					class "«getNameOf(object)»" «getLinkTo(object)»
				«ENDIF»
			«ENDFOR»					
		'''
	}

	def String visualizeCollection(Collection<EObject> collection) {

		val context = createUMLContext()
		context.queueElementForGenerationIfNeeded(collection)

		val builder = new StringBuilder();

		builder.append("hide empty members").append('\n')
		builder.append("hide circle").append('\n')
//		builder.append("hide stereotype").append('\n')
		while(!context.anyElementPending()) {
			val object = context.getNextPendingElement()
			val elementUML = context.generateUML(object)
			if(elementUML !== null)
				builder.append(elementUML).append('\n')
		}

		for (String additional : context.appendAtEnd) {
			if(additional !== null)
				builder.append(additional).append('\n')
		}

		return builder.toString()
	}

	def PlantUMLTemplateProvider.UMLContext createUMLContext() {
		return new UMLContext(this)
	}

	protected def dispatch String generateUML(EObject eObject, PlantUMLTemplateProvider.UMLContext generator,
		boolean includeReferences) {
		'''
			class "«getNameOf(eObject)»" as «generator.getUMLRef(eObject)» <<«eObject.class.simpleName»>>
		'''
	}

	protected def dispatch String generateUML(EditorPattern pattern, PlantUMLTemplateProvider.UMLContext generator,
		boolean includeReferences) {
		return GTPlantUMLGenerator.visualizePattern(pattern, "", false)
	}

	protected def dispatch String generateUML(GipsConfig config, PlantUMLTemplateProvider.UMLContext generator,
		boolean includeReferences) {
		return '''
			«»
			class "Config" <<Config>> «getLinkTo(config)» {
				«FOR eFeature : config.eClass.EAllStructuralFeatures.filter[e | e instanceof EAttribute]»
					«attributeToUML(config, eFeature as EAttribute)»
				«ENDFOR»
			}
		'''
	}

	protected def dispatch String generateUML(GipsMapping mapping, PlantUMLTemplateProvider.UMLContext context,
		boolean includeReferences) {
		val ref = getReferenceTo(mapping, context)

//		context.appendAtEnd.add('''«ref» --> «context.getUMLRef(mapping.pattern)» : pattern''')
//		context.source.appendNow('''''')
//		context.source.appendAtEnd('''«ref» --> «context.getUMLRef(mapping.pattern)» : pattern''')
		context.queueElementForGenerationIfNeeded(mapping.pattern)

		return '''
			class "«getNameOf(mapping)»" as «ref» <<Mapping>> {
				«FOR variable : mapping.variables»
					+ «variable.name»«IF variable.bound» (bound)«ENDIF» : «variable.type» 
				«ENDFOR»
			}
			
			«ref» --> «getReferenceTo(mapping.pattern, context)» : pattern
		'''
	}

	protected def dispatch String generateUML(GipsLinearFunction eObject, PlantUMLTemplateProvider.UMLContext context,
		boolean includeReferences) {
			
		val ref = getReferenceTo(eObject, context)
		val contextRef = getReferenceTo(eObject.context, context)
		
		'''
			class "«getNameOf(eObject)»" as «ref» <<Function>>
			«ref» --> «contextRef» : context
		'''
	}

	protected def dispatch String generateUML(GipsObjective eObject, PlantUMLTemplateProvider.UMLContext generator,
		boolean includeReferences) {
		val ref = generator.getUMLRef(eObject)
		val Collection<? extends EObject> linearFunctions = EcoreUtil2.eAllContents(eObject.expression)
			.filter[it instanceof GipsLinearFunctionReference]
			.map[it as GipsLinearFunctionReference]
			.map[it.function]
			.toList

		'''
			class "«getNameOf(eObject)»" as «ref» <<Objective>>
			«FOR linearFunction : linearFunctions»
				«ref» --> «generator.getUMLRef(linearFunction)» : mapping
			«ENDFOR»		
		'''
	}

	protected def dispatch String generateUML(GipsConstraint constraint, PlantUMLTemplateProvider.UMLContext context,
		boolean includeReferences) {

		val ref = getReferenceTo(constraint, context)		
		val contextRef = getReferenceTo(constraint.context, context)
		
		val mappings = EcoreUtil2.eAllContents(constraint.expression)
			.filter[it instanceof GipsMappingExpression]
			.map[it as GipsMappingExpression]
			.map[it.mapping]
			.distinct
			
		'''
			class "constraint («ref»)" as «ref» <<Constraint>> {
				«FOR constant : constraint.constants»
					+ «constant.name»
				«ENDFOR»
			}
			
			«IF includeReferences»
				«ref» --> «contextRef» : context
				«FOR mapping : mappings»
					«ref» --> «getReferenceTo(mapping, context)» : mapping
				«ENDFOR»
			«ENDIF»
		'''
	}



	protected def String attributeToUML(EObject object, EAttribute feature) {
		var String text = null
		var value = object.eGet(feature)
		if(value !== null) {
			text = labelProvider.getText(value)
			if(text === null)
				text = value.toString
		} else if(object.eIsSet(feature)) {
			text = '<null>'
		}

		'''
			+ **«feature.name»** := «text»
		'''
	}
	
	protected def String getLinkTo(EObject object) {
		val resource = object.eResource
		val uri = resource.URI + '#' + resource.getURIFragment(object)
		return '''[[«uri»]]'''
	}

	def boolean hasUMLRepresentation(EObject eObject) {
		return switch eObject {
			org.emoflon.gips.gipsl.gipsl.GipsObjective: true
			org.emoflon.gips.gipsl.gipsl.GipsLinearFunction: true
			org.emoflon.gips.gipsl.gipsl.GipsConfig: true
			org.emoflon.gips.gipsl.gipsl.GipsMapping: true
			org.emoflon.gips.gipsl.gipsl.GipsConstraint: true
			org.emoflon.ibex.gt.editor.gT.EditorPattern: true
			default: false
		}
	}
	
	protected def String getReferenceTo(EObject eObject, UMLContext context){
		context.queueElementForGenerationIfNeeded(eObject)
		switch(eObject){
			org.emoflon.ibex.gt.editor.gT.EditorPattern: getNameOf(eObject)
			default: context.getUMLRef(eObject)
		}
	}

	protected def String getNameOf(EObject object) {
		if(object === null)
			return ""

		val labelFeature = getLabelFeature(object.eClass)
		if(labelFeature !== null) {
			val label = object.eGet(labelFeature, true)?.toString
			if(label !== null)
				return label
		}

		val name = labelProvider.getText(object)
		if(name === null || name.empty)
			return object.eClass.name
		return name.replace(':', '-').replace(' ', '')
	}
	
	protected def EStructuralFeature getLabelFeature(EClass eClass) {
		var EAttribute result = null;
		for (EAttribute eAttribute : eClass.getEAllAttributes()) {
			if(!eAttribute.isMany() && eAttribute.getEType().getInstanceClass() != typeof(FeatureMap.Entry)) {
				if("name".equalsIgnoreCase(eAttribute.getName())) {
					return eAttribute;
				} else if(result === null) {
					result = eAttribute;
				} else if(eAttribute.getEAttributeType().getInstanceClass() == typeof(String) &&
					result.getEAttributeType().getInstanceClass() != typeof(String)) {
					result = eAttribute;
				}
			}
		}
		return result;
	}



	private static def <T> distinct(Iterable<? extends T> iterable) {
		iterable.groupBy[it].entrySet.map[it.key]
	}
}
