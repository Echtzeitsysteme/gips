package org.emoflon.gips.build.generator.templates.constraint

import org.emoflon.gips.build.generator.templates.GeneratorTemplate
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint
import org.emoflon.gips.intermediate.GipsIntermediate.RuleConstraint
import org.emoflon.gips.build.GipsAPIData

class ConstraintFactoryTemplate extends GeneratorTemplate<GipsIntermediateModel> {
	
	new(GipsAPIData data, GipsIntermediateModel context) {
		super(data, context)
	}

	override init() {
		packageName = data.gipsApiPkg
		className = data.constraintFactoryClassName
		fqn = packageName + "." + className;
		filePath = data.gipsApiPkgPath + "/" + className + ".java"
		imports.add(data.gipsApiPkg+"."+data.gipsApiClassName)
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
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.RuleConstraint")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.Constraint")
		data.constraint2constraintClassName.values.forEach[c | imports.add(data.gipsConstraintPkg+"."+c)]
	}
	
	override generate() {
		code = '''package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» <MOFLON_API extends «data.apiAbstractClassName»<?>> extends GipsConstraintFactory<«data.gipsApiClassName»<MOFLON_API>, MOFLON_API> {
	public «className»(final «data.gipsApiClassName»<MOFLON_API> engine, final MOFLON_API eMoflonApi) {
		super(engine, eMoflonApi);
	}
	
	@Override
	public GipsConstraint<«data.gipsApiClassName»<MOFLON_API>, ? extends Constraint, ? extends Object> createConstraint(final Constraint constraint) {
		«IF context.constraints.isNullOrEmpty»
		throw new IllegalArgumentException("Unknown constraint type: "+constraint);
		«ELSE»
		switch(constraint.getName()) {
			«FOR constraint : context.constraints»
			case "«constraint.name»" -> {
			«IF constraint instanceof PatternConstraint»
				return new «data.constraint2constraintClassName.get(constraint)»<MOFLON_API>(engine, («constraint.eClass.name»)constraint, eMoflonApi.«constraint.pattern.name.toFirstLower»(«FOR param: constraint.pattern.parameters SEPARATOR ", "»«exprHelper.parameterToJavaDefaultValue(param)»«ENDFOR»));
			«ELSEIF constraint instanceof RuleConstraint»
			    return new «data.constraint2constraintClassName.get(constraint)»<MOFLON_API>(engine, («constraint.eClass.name»)constraint, eMoflonApi.«constraint.rule.name.toFirstLower»(«FOR param: constraint.rule.parameters SEPARATOR ", "»«exprHelper.parameterToJavaDefaultValue(param)»«ENDFOR»));
			«ELSE»
				return new «data.constraint2constraintClassName.get(constraint)»<MOFLON_API>(engine, («constraint.eClass.name»)constraint);
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