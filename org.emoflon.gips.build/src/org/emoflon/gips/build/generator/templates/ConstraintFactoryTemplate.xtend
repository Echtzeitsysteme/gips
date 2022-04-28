package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.GeneratorTemplate
import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel
import org.emoflon.roam.intermediate.RoamIntermediate.PatternConstraint

class ConstraintFactoryTemplate extends GeneratorTemplate<RoamIntermediateModel> {
	
	new(TemplateData data, RoamIntermediateModel context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.roamApiPkg
		className = data.constraintFactoryClassName
		fqn = packageName + "." + className;
		filePath = data.apiData.roamApiPkgPath + "/" + className + ".java"
		imports.add(data.apiData.apiPkg + "." + data.apiData.apiClass)
		imports.add("org.emoflon.roam.core.api.RoamConstraintFactory")
		imports.add("org.emoflon.roam.core.RoamEngine")
		imports.add("org.emoflon.roam.core.RoamConstraint")
		imports.add("org.emoflon.roam.core.RoamTypeConstraint")
		imports.add("org.emoflon.roam.core.RoamMappingConstraint")
		imports.add("org.emoflon.roam.core.gt.RoamPatternConstraint")
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.Constraint")
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.PatternConstraint")
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint")
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint")
		data.constraint2constraintClassName.values.forEach[c | imports.add(data.apiData.roamConstraintPkg+"."+c)]
	}
	
	override generate() {
		code = '''package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends RoamConstraintFactory<«data.apiData.apiClass»> {
	public «className»(final RoamEngine engine, final «data.apiData.apiClass» eMoflonApi) {
		super(engine, eMoflonApi);
	}
	
	@Override
	public RoamConstraint<? extends Constraint, ? extends Object, ? extends Number> createConstraint(final Constraint constraint) {
		«IF context.constraints.isNullOrEmpty»
		throw new IllegalArgumentException("Unknown constraint type: "+constraint);
		«ELSE»
		switch(constraint.getName()) {
			«FOR constraint : context.constraints»
			case "«constraint.name»" -> {
			«IF constraint instanceof PatternConstraint»
				return new «data.constraint2constraintClassName.get(constraint)»(engine, («constraint.eClass.name»)constraint, eMoflonApi.«constraint.pattern.name.toFirstLower»());
			«ELSE»
				return new «data.constraint2constraintClassName.get(constraint)»(engine, («constraint.eClass.name»)constraint);
			«ENDIF»
			}
			«ENDFOR»
			default -> {
				throw new IllegalArgumentException("Unknown constraint type: "+constraint);	
			}
		}
		«ENDIF»
			
	}
}'''
	}

	
}