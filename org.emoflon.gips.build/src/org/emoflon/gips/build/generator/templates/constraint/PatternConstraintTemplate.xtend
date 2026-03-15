package org.emoflon.gips.build.generator.templates.constraint

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference

class PatternConstraintTemplate extends ConstraintTemplate<PatternConstraint> {

	new(TemplateData data, PatternConstraint context) {
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
		imports.add("org.emoflon.gips.core.gt.GipsPatternConstraint")
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constraint")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint")
		imports.add(data.apiData.gipsApiPkg + "." + data.gipsApiClassName)
		imports.add(data.apiData.matchesPkg + "." + data.ibex2matchClassName.get(context.pattern))
		imports.add(data.apiData.rulesPkg + "." + data.ibex2ibexClassName.get(context.pattern))
	}

	override String generateClassSignature() {
		return '''public class «className» extends GipsPatternConstraint<«data.gipsApiClassName», «data.ibex2matchClassName.get(context.pattern)», «data.ibex2ibexClassName.get(context.pattern)»>'''
	}

	override String generateClassConstructors() {
		'''
			public «className»(final «data.gipsApiClassName» engine, final PatternConstraint constraint, final «data.ibex2ibexClassName.get(context.pattern)» pattern) {
				super(engine, constraint, pattern);
			}
		'''
	}

	override String generateVariableAccess(VariableReference varRef) {
		if(isMappingVariable(varRef))
			throw new UnsupportedOperationException("Mapping context access is not possible within a pattern context.")

		return super.generateVariableAccess(varRef)
	}

	override String getContextParameterType() {
		return data.ibex2matchClassName.get(context.pattern)
	}
}
