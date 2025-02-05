package org.emoflon.gips.build.generator.templates.constraint

import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.intermediate.GipsIntermediate.RuleConstraint
import org.emoflon.gips.build.GipsAPIData

class RuleConstraintTemplate extends ConstraintTemplate<RuleConstraint> {

	new(GipsAPIData data, RuleConstraint context) {
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
		imports.add("org.emoflon.gips.core.gt.GipsRuleConstraint")
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constraint")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.RuleConstraint")
		imports.add(data.gipsApiPkg+"."+data.gipsApiClassName)
		imports.add(data.matchPackage+"."+data.ibex2matchClassName.get(context.rule))
		imports.add(data.rulePackage+"."+data.ibex2ibexClassName.get(context.rule))
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
		return '''public class «className» extends GipsRuleConstraint<«data.gipsApiClassName», «data.ibex2matchClassName.get(context.rule)», «data.ibex2ibexClassName.get(context.rule)»>'''
	}
	
	override String generateClassConstructor() {
		return '''
public «className»(final «data.gipsApiClassName» engine, final RuleConstraint constraint, final «data.ibex2ibexClassName.get(context.rule)» rule) {
	super(engine, constraint, rule);
}
'''
	}
	
	override String getVariable(Variable variable) {
		if(!isMappingVariable(variable)) {
			return '''engine.getNonMappingVariable(context, "«variable.name»")'''
		} else {
			throw new UnsupportedOperationException("Mapping context access is not possible within a pattern context.")
		}
	}
	
	override String getContextParameterType() {
		return data.ibex2matchClassName.get(context.rule)
	}
}