package org.emoflon.gips.build.generator.templates.constraint

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.RuleConstraint
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference

class RuleConstraintTemplate extends ConstraintTemplate<RuleConstraint> {

	new(TemplateData data, RuleConstraint context) {
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
		imports.add("org.emoflon.gips.core.gt.GipsRuleConstraint")
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constraint")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.RuleConstraint")
		imports.add(data.apiData.gipsApiPkg + "." + data.gipsApiClassName)
		imports.add(data.apiData.matchesPkg + "." + data.ibex2matchClassName.get(context.rule))
		imports.add(data.apiData.rulesPkg + "." + data.ibex2ibexClassName.get(context.rule))
	}

	override String generateClassSignature() {
		return '''public class «className» extends GipsRuleConstraint<«data.gipsApiClassName», «data.ibex2matchClassName.get(context.rule)», «data.ibex2ibexClassName.get(context.rule)»>'''
	}

	override String generateClassConstructors() {
		'''
			public «className»(final «data.gipsApiClassName» engine, final RuleConstraint constraint, final «data.ibex2ibexClassName.get(context.rule)» rule) {
				super(engine, constraint, rule);
			}
		'''
	}

	override String generateVariableAccess(VariableReference varRef) {
		if(isMappingVariable(varRef))
			throw new UnsupportedOperationException("Mapping context access is not possible within a pattern context.")

		return super.generateVariableAccess(varRef)
	}

	override String getContextParameterType() {
		return data.ibex2matchClassName.get(context.rule)
	}
}
