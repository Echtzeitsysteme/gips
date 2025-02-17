package org.emoflon.gips.build.generator.templates.constraint

import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.build.generator.templates.constraint.ConstraintTemplate
import org.emoflon.gips.build.GipsAPIData

class MappingConstraintTemplate extends ConstraintTemplate<MappingConstraint> {

	new(GipsAPIData data, MappingConstraint context) {
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
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsMappingConstraint")
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constraint")
		if(context.isDepending) {
			imports.add("org.emoflon.gips.core.milp.model.BinaryVariable");
			imports.add("org.emoflon.gips.core.milp.model.RealVariable");
		}
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint")
		imports.add(data.gipsApiPkg+"."+data.gipsApiClassName)
		imports.add(data.apiPackage + "." + data.apiAbstractClassName)
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
	
	override String generateClassSignature() {
		return '''public class «className» <MOFLON_API extends «data.apiAbstractClassName»<?>> extends GipsMappingConstraint<«data.gipsApiClassName»<MOFLON_API>, «data.mapping2mappingClassName.get(context.mapping)»>'''
	}
	
	override String generateClassConstructor() {
		return '''
public «className»(final «data.gipsApiClassName»<MOFLON_API> engine, final MappingConstraint constraint) {
	super(engine, constraint);
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