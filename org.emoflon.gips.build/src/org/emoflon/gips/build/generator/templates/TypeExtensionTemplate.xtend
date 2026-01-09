package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.TypeExtension
import org.emoflon.gips.build.generator.GipsImportManager
import org.emoflon.gips.intermediate.GipsIntermediate.VariableType
import org.emoflon.gips.intermediate.GipsIntermediate.BoundAttributeVariable

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
	
	@Override
	public void setVariableValue(final String valName, final double value) {
		«IF context.addedVariables.isNullOrEmpty»
			throw new UnsupportedOperationException("Extension does not have any additonal variables.");
		«ELSE»
			switch(valName) {
				«FOR variable : context.addedVariables»
					case "«variable.name»" : {
						«GipsImportManager.variableToJavaDataType(variable, imports)» variable = getVariable("«variable.name»");
						«IF variable.type == VariableType.BINARY»
							variable.setValue((int) value);
						«ELSEIF variable.type == VariableType.INTEGER»
							variable.setValue(Math.round((«GipsImportManager.variableToSimpleJavaDataType(variable, imports)») value));
						«ELSE»
							variable.setValue((«GipsImportManager.variableToSimpleJavaDataType(variable, imports)») value);
						«ENDIF»
						break;
					}
				«ENDFOR»
				default: throw new IllegalArgumentException("Extension does not have a variable with the symbolic name <" + valName + ">.");
			}
		«ENDIF»
	}
	
	@Override
	public void applyBoundVariablesToModel(){
		«FOR variable : context.addedVariables»
			«IF variable instanceof BoundAttributeVariable»
				getContext().eSet(getContext().eClass().getEStructuralFeature("«variable.boundToFeature»"), getVariable("«variable.name»").getValue());
			«ENDIF»
		«ENDFOR»
	}
	
}

'''
	}

}
