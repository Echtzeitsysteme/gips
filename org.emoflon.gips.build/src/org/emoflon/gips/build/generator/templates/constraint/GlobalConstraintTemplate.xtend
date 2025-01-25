package org.emoflon.gips.build.generator.templates.constraint

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint

class GlobalConstraintTemplate extends ConstraintTemplate<Constraint> {
	
	new(TemplateData data, Constraint context) {
		super(data, context)
	}
	
		override init() {
		packageName = data.apiData.gipsConstraintPkg
		className = data.constraint2constraintClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.apiData.gipsConstraintPkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("java.util.Collections");
		imports.add("org.eclipse.emf.ecore.EClass")
		imports.add("org.eclipse.emf.ecore.EObject")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsMapping")
		imports.add("org.emoflon.gips.core.GipsGlobalConstraint")
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constraint")
		imports.add(data.apiData.gipsApiPkg+"."+data.gipsApiClassName)
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
		return '''public class «className» extends GipsGlobalConstraint<«data.gipsApiClassName»> '''
	}
	
	override String generateClassConstructor() {
		return '''
public «className»(final «data.gipsApiClassName» engine, final org.emoflon.gips.intermediate.GipsIntermediate.Constraint constraint) {
	super(engine, constraint);
}
'''
	}
	
	override getVariable(Variable variable) {
		if(!isMappingVariable(variable)) {
			return '''engine.getNonMappingVariable(context, "«variable.name»")'''
		} else {
			throw new UnsupportedOperationException("Mapping context access is not possible within a global context.")
		}
	}

	override String getContextParameterType() {
		return ""
	}
	
	override String getContextParameter() {
		return ""
	}
	
	override getAdditionalVariableName(VariableReference varRef) {
		return '''engine.getNonMappingVariable(constraint, "«varRef.variable.name»").getName()'''
	}

}