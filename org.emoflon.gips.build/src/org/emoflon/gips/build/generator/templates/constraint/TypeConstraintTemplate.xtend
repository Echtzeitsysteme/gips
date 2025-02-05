package org.emoflon.gips.build.generator.templates.constraint

import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.build.generator.templates.constraint.ConstraintTemplate
import org.emoflon.gips.build.GipsAPIData

class TypeConstraintTemplate extends ConstraintTemplate<TypeConstraint> {
	
	new(GipsAPIData data, TypeConstraint context) {
		super(data, context)
	}
	
		override init() {
		packageName = data.gipsConstraintPkg
		className = data.constraint2constraintClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.gipsConstraintPkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("java.util.Collections")
		imports.add("org.eclipse.emf.ecore.EClass")
		imports.add("org.eclipse.emf.ecore.EObject")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsMapping")
		imports.add("org.emoflon.gips.core.GipsTypeConstraint")
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constraint")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint")
		imports.add(data.gipsApiPkg+"."+data.gipsApiClassName)
		imports.add(data.getFQN(context.type))
	}
	
	override String generatePackageDeclaration() {
		return '''package «packageName»;'''
	}
	
	override String generateImports() {
		return '''«FOR imp : imports»
import «imp»;
«ENDFOR»'''
	}
	
	override String generateClassSignature() {
		return '''public class «className» extends GipsTypeConstraint<«data.gipsApiClassName», «context.type.name»>'''
	}
	
	override String generateClassConstructor() {
		return '''
public «className»(final «data.gipsApiClassName» engine, final TypeConstraint constraint) {
	super(engine, constraint);
}
'''
	}
	
	override getVariable(Variable variable) {
		if(!isMappingVariable(variable)) {
			return '''engine.getNonMappingVariable(context, "«variable.name»")'''
		} else {
			throw new UnsupportedOperationException("Mapping context access is not possible within a type context.")
		}
	}

	override String getContextParameterType() {
		return context.type.name
	}
	
}