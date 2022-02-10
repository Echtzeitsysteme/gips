package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.GeneratorTemplate
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint
import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint

class MappingConstraintTemplate extends GeneratorTemplate<MappingConstraint> {

new(TemplateData data, MappingConstraint context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.roamConstraintPkg
		className = context.name.toFirstUpper+"MappingConstraint"
		fqn = packageName + "." + className;
		filePath = data.apiData.roamConstraintPkgPath + "/" + className + ".java"
		imports.add("org.emoflon.roam.core.RoamEngine")
		imports.add("org.emoflon.roam.core.RoamMapping")
		imports.add("org.emoflon.roam.core.RoamMappingConstraint")
	}
	
	override generate() {
		code = '''package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends RoamMappingConstraint {
	public «className»(final RoamEngine engine, final MappingConstraint constraint) {
		super(engine, constraint);
	}
	
	@Override
	protected Integer buildConstantTerm(final RoamMapping context) {
		
	}
	
	@Override
	protected List<ILPTerm<Integer, Integer>> buildVariableTerms(final RoamMapping context) {
		
	}
	
}'''
	}
	
}