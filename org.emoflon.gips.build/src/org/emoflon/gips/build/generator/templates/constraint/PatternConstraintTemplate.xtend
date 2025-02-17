package org.emoflon.gips.build.generator.templates.constraint

import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.build.generator.templates.constraint.ConstraintTemplate
import org.emoflon.gips.build.GipsAPIData

class PatternConstraintTemplate extends ConstraintTemplate<PatternConstraint> {

	new(GipsAPIData data, PatternConstraint context) {
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
		imports.add("org.emoflon.gips.core.gt.GipsPatternConstraint")
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constraint")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint")
		imports.add(data.gipsApiPkg+"."+data.gipsApiClassName)
		imports.add(data.apiPackage + "." + data.apiAbstractClassName)
		imports.add(data.matchPackage+"."+data.ibex2matchClassName.get(context.pattern))
		imports.add(data.rulePackage+"."+data.ibex2ibexClassName.get(context.pattern))
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
		return '''public class «className» <MOFLON_API extends «data.apiAbstractClassName»<?>> extends GipsPatternConstraint<«data.gipsApiClassName»<MOFLON_API>, «data.ibex2matchClassName.get(context.pattern)», «data.ibex2ibexClassName.get(context.pattern)»>'''
	}
	
	override String generateClassConstructor() {
		return '''
public «className»(final «data.gipsApiClassName»<MOFLON_API> engine, final PatternConstraint constraint, final «data.ibex2ibexClassName.get(context.pattern)» pattern) {
	super(engine, constraint, pattern);
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
		return data.ibex2matchClassName.get(context.pattern)
	}
}