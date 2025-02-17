package org.emoflon.gips.build.generator.templates.constraint

import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint
import org.emoflon.gips.intermediate.GipsIntermediate.Constant
import org.emoflon.gips.build.GipsAPIData

class GlobalConstraintTemplate extends ConstraintTemplate<Constraint> {
	
	new(GipsAPIData data, Constraint context) {
		super(data, context)
	}
	
		override init() {
		packageName = data.gipsConstraintPkg
		className = data.constraint2constraintClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.gipsConstraintPkgPath + "/" + className + ".java"
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
		imports.add(data.apiPackage + "." + data.apiAbstractClassName)
		imports.add(data.gipsApiPkg+"."+data.gipsApiClassName)
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
		return '''public class «className» <MOFLON_API extends «data.apiAbstractClassName»<?>> extends GipsGlobalConstraint<«data.gipsApiClassName»<MOFLON_API>> '''
	}
	
	override String generateClassConstructor() {
		return '''
public «className»(final «data.gipsApiClassName»<MOFLON_API> engine, final org.emoflon.gips.intermediate.GipsIntermediate.Constraint constraint) {
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
	
	override getCallConstantCalculator(Constant constant) {
		return '''calculate«constant.name.toFirstUpper»()'''
	}

	override String getContextParameterType() {
		return ""
	}
	
	override String getContextParameter() {
		return ""
	}
	
	override String getParametersForVoidBuilder() {
		return '''final List<Term> terms'''
	}
	
	override String getCallParametersForVoidBuilder() {
		return '''terms'''
	}
	
	override getAdditionalVariableName(VariableReference varRef) {
		return '''engine.getNonMappingVariable(constraint, "«varRef.variable.name»").getName()'''
	}

}