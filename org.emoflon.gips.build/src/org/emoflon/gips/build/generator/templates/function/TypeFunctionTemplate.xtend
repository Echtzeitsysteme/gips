package org.emoflon.gips.build.generator.templates.function

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.TypeFunction
import org.emoflon.gips.intermediate.GipsIntermediate.Variable

class TypeFunctionTemplate extends LinearFunctionTemplate<TypeFunction> {
	
	new(TemplateData data, TypeFunction context) {
		super(data, context)
	}
	
		override init() {
		packageName = data.apiData.gipsObjectivePkg
		className = data.function2functionClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.apiData.gipsObjectivePkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("org.eclipse.emf.ecore.EClass")
		imports.add("org.eclipse.emf.ecore.EObject")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsMapping")
		imports.add("org.emoflon.gips.core.GipsTypeLinearFunction")
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constant")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.TypeFunction")
		imports.add(data.apiData.gipsApiPkg+"."+data.gipsApiClassName)
		imports.add(data.classToPackage.getImportsForType(context.type))
	}
	
	override String generatePackageDeclaration() {
		return '''package «packageName»;'''
	}
	
	override String generateImports() {
		return '''«FOR imp : imports»
import «imp»;
«ENDFOR»'''
	}
	
	override String generateClassContent() {
		return '''
public class «className» extends GipsTypeLinearFunction<«data.gipsApiClassName», «context.type.name»> {
	public «className»(final «data.gipsApiClassName» engine, final TypeFunction function) {
		super(engine, function);
	}
	
	«generateObjective(context.expression)»
		
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
	
	«getConstantCalculators(context.constants)»
}'''
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