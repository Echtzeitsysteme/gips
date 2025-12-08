package org.emoflon.gips.gipsl.ui.visualization

import java.util.Set
import java.util.Map
import java.util.Objects
import org.eclipse.emf.ecore.EObject
import java.util.HashMap
import java.util.HashSet
import java.util.LinkedList
import java.util.Collection
import org.eclipse.xtend.lib.annotations.Accessors

class UMLContext {
	
	static interface UMLTemplateProvider{
		
		def String generateReferenceFor(EObject eObject, long referenceCounter, UMLContext context)
		
		def String generateUML(EObject eObject, UMLContext context)
		
	}
			
	UMLTemplateProvider provider
	
	Map<EObject, String> objectToReference = new HashMap
	Map<String, EObject> referenceToObject = new HashMap
	long referenceCounter = 0
	
	Set<EObject> generatedObjects = new HashSet
	Set<EObject> referencedButNotGenerated = new HashSet
	
	@Accessors(PUBLIC_GETTER)
	Collection<String> attachAtEndOfFile = new LinkedList
	
	new (UMLTemplateProvider provider){
		this.provider = Objects.requireNonNull(provider, "provider")
	}
	
	def String getOrCreateUMLReference(EObject eObject) {
		return objectToReference.computeIfAbsent(eObject, [
			addReferencedElement(eObject)				
			val reference = provider.generateReferenceFor(eObject, referenceCounter++, this)
			if(referenceToObject.containsKey(reference))
				throw new IllegalStateException("Reference not unique: " + reference)
			referenceToObject.put(reference, eObject)	
			return reference
		])
	}
	
	def String setUMLReference(EObject eObject, String reference){		
		if(referenceToObject.containsKey(reference)){
			if(referenceToObject.get(reference).equals(eObject))
				return reference	
			throw new IllegalStateException("Reference not unique: " + reference)
		}
		
		if(objectToReference.containsKey(eObject)){
			var oldReference = objectToReference.get(eObject)
			referenceToObject.remove(oldReference)			
		}
			
		referenceToObject.put(reference, eObject)
		objectToReference.put(eObject, reference)
		return reference
	}
	
	def boolean hasUMLReference(EObject eObject){
		return objectToReference.containsKey(eObject)
	}
	
	def boolean hasUMLReference(String reference){
		return referenceToObject.containsKey(reference)
	}
	
	/**
	 * @return true If the element has not yet been generated
	 */
	def boolean addReferencedElement(EObject eObject) {
		if(eObject === null)
			return false;
		
		if(!generatedObjects.contains(eObject)){
			referencedButNotGenerated.add(eObject)
			return true	
		}
		
		return false
	}
	
	def void addGeneratedElement(EObject eObject){
		if(eObject === null)
			return;
		
		referencedButNotGenerated.remove(eObject)
		generatedObjects.add(eObject)
	}
	
	def String generateUML(EObject eObject){
		if(eObject === null)
			return null
		val source = provider.generateUML(eObject, this)
		addGeneratedElement(eObject)
		return source
	}
	
	def boolean hasNonGeneratedElements(){
		return !referencedButNotGenerated.empty
	}
	
	def EObject getNextNonGeneratedElement(){
		return referencedButNotGenerated.iterator.next
	}
		
}