package org.emoflon.gips.build.generator.templates.constraint

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference

class MappingConstraintTemplate extends ConstraintTemplate<MappingConstraint> {

	new(TemplateData data, MappingConstraint context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.gipsConstraintPkg
		className = data.constraint2constraintClassName.get(context)
		filePath = data.apiData.gipsConstraintPkgPath + "/" + className + ".java"
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
		imports.add(data.apiData.gipsApiPkg + "." + data.gipsApiClassName)
		imports.add(data.apiData.gipsMappingPkg + "." + data.mapping2mappingClassName.get(context.mapping))
	}

	override String generateClassSignature() {
		return '''public class «className» extends GipsMappingConstraint<«data.gipsApiClassName», «data.mapping2mappingClassName.get(context.mapping)»>'''
	}

	override String generateClassConstructors() {
		'''
			public «className»(final «data.gipsApiClassName» engine, final MappingConstraint constraint) {
				super(engine, constraint);
			}
		'''
	}

	override String generateVariableAccess(VariableReference varRef) {
		if(isMappingVariable(varRef)) {
			if(context.mapping.mappingVariable.equals(varRef.variable)) {
				return '''context'''
			}

			throw new UnsupportedOperationException(
				"Foreign mapping context access is not possible within another mapping context.")
		}

		return super.generateVariableAccess(varRef)
	}

	override String getContextParameterType() {
		return data.mapping2mappingClassName.get(context.mapping)
	}

}
