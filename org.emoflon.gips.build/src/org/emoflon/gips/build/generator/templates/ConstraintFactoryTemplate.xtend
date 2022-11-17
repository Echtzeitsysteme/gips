package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.GeneratorTemplate
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint
import org.emoflon.gips.build.generator.GipsApiData

class ConstraintFactoryTemplate extends GeneratorTemplate<GipsIntermediateModel> {
	
	new(GipsApiData data, GipsIntermediateModel context) {
		super(data, context)
	}

	override init() {
		packageName = data.gipsApiPkg
		className = data.constraintFactoryClassName
		fqn = packageName + "." + className;
		filePath = data.gipsApiPkgPath + "/" + className + ".java"
		imports.add(data.apiPackage + "." + data.apiAbstractClassName)
		imports.add("org.emoflon.gips.core.api.GipsConstraintFactory")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsConstraint")
		imports.add("org.emoflon.gips.core.GipsTypeConstraint")
		imports.add("org.emoflon.gips.core.GipsMappingConstraint")
		imports.add("org.emoflon.gips.core.GipsGlobalConstraint")
		imports.add("org.emoflon.gips.core.gt.GipsPatternConstraint")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.Constraint")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.GlobalConstraint")
		imports.add(data.gipsApiPkg+"."+data.gipsApiClassName)
		data.constraint2constraintClassName.values.forEach[c | imports.add(data.gipsConstraintPkg+"."+c)]
	}
	
	override generate() {
		code = '''package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends GipsConstraintFactory<«data.gipsApiClassName»<?>, «data.apiAbstractClassName»<?>> {
	public «className»(final «data.gipsApiClassName»<?> engine, final «data.apiAbstractClassName»<?> eMoflonApi) {
		super(engine, eMoflonApi);
	}
	
	@Override
	public GipsConstraint<«data.gipsApiClassName»<?>, ? extends Constraint, ? extends Object> createConstraint(final Constraint constraint) {
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