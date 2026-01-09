package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.TypeExtension
import org.emoflon.gips.build.generator.GipsImportManager

class TypeExtensionTemplate extends GeneratorTemplate<TypeExtension> {

	new(TemplateData data, TypeExtension context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.gipsTypeExtensionPkg;
		className = data.typeExtension2extensionClassName.get(context)
		fqn = packageName + "." + className
		filePath = data.apiData.gipsTypeExtensionPkgPath + "/" + className + ".java"
		
		imports.add("org.emoflon.gips.core.GipsTypeExtension")
		imports.add("org.emoflon.gips.core.milp.model.Variable")
		imports.add("org.emoflon.gips.core.milp.model.IntegerVariable")
		imports.add("org.emoflon.gips.core.milp.model.RealVariable")
		imports.add("org.emoflon.gips.core.milp.model.BinaryVariable")
		imports.add("java.util.Map");
		
		imports.addAll(data.classToPackage.getImportsForType(context.extendedType))
	}

	override generate() {
		code = '''
/*
* Generated org.emoflon.gips.build.generator.templates.TypeExtensionTemplate
*/ 			
package «packageName»;

«FOR imp : imports»
	import «imp»;
«ENDFOR»

public class «className» extends GipsTypeExtension<«context.extendedType.name»> {

	public «className»(final «context.extendedType.name» context, Map<String, Variable<?>> milpVariables) {
		super(context, milpVariables);
	}

	«FOR variable : context.addedVariables»	
		public «GipsImportManager.variableToSimpleJavaDataType(variable, imports)» getValueOf«variable.name.toFirstUpper»() {
			«GipsImportManager.variableToJavaDataType(variable, imports)» variable = getVariable("«variable.name»");
			«IF GipsImportManager.variableToSimpleJavaDataType(variable, imports) == "boolean"»
				return variable.getValue() != 0;
			«ELSE»
				return variable.getValue();
			«ENDIF»
		}
	«ENDFOR»
	
}

'''
	}

}
