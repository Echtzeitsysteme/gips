package org.emoflon.gips.build.generator.templates.function

import org.emoflon.gips.intermediate.GipsIntermediate.MappingFunction
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.build.GipsAPIData

class MappingFunctionTemplate extends LinearFunctionTemplate<MappingFunction> {

	new(GipsAPIData data, MappingFunction context) {
		super(data, context)
	}

	override init() {
		packageName = data.gipsObjectivePkg
		className = data.function2functionClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.gipsObjectivePkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsMappingLinearFunction")
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constant")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.MappingFunction")
		imports.add(data.gipsApiPkg+"."+data.gipsApiClassName)
		imports.add(data.gipsMappingPkg+"."+data.mapping2mappingClassName.get(context.mapping))
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
public class «className» extends GipsMappingLinearFunction<«data.gipsApiClassName», «data.mapping2mappingClassName.get(context.mapping)»>{
	public «className»(final «data.gipsApiClassName» engine, final MappingFunction function) {
		super(engine, function);
	}
	
	«generateObjective(context.expression)»
	
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
	
	«getConstantCalculators(context.constants)»
}'''
	}
	
	override String getVariable(Variable variable) {
		if(isMappingVariable(variable) && context.mapping.mappingVariable.equals(variable)) {
			return '''context'''
		} else if(!isMappingVariable(variable)) {
			return '''engine.getNonMappingVariable(context, "«variable.name»")'''
		} else {
			throw new UnsupportedOperationException("Foreign mapping context access is not possible within another mapping context.")
		}
	}
	
	override String getContextParameterType() {
		return data.mapping2mappingClassName.get(context.mapping)
	}
	
}