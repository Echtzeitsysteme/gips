package org.emoflon.gips.build.generator.templates.constraint

import org.emoflon.gips.build.generator.templates.GeneratorTemplate
import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint
import org.emoflon.gips.build.generator.GipsImportManager
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern
import org.emoflon.gips.intermediate.GipsIntermediate.RuleConstraint

class ConstraintFactoryTemplate extends GeneratorTemplate<GipsIntermediateModel> {
	
	new(TemplateData data, GipsIntermediateModel context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.gipsApiPkg
		className = data.constraintFactoryClassName
		fqn = packageName + "." + className;
		filePath = data.apiData.gipsApiPkgPath + "/" + className + ".java"
		imports.add(data.apiData.apiPkg + "." + data.apiData.apiClass)
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
		imports.add(data.apiData.gipsApiPkg+"."+data.gipsApiClassName)
		data.constraint2constraintClassName.values.forEach[c | imports.add(data.apiData.gipsConstraintPkg+"."+c)]
	}
	
	override generate() {
		code = '''package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends GipsConstraintFactory<«data.gipsApiClassName», «data.apiData.apiClass»> {
	public «className»(final «data.gipsApiClassName» engine, final «data.apiData.apiClass» eMoflonApi) {
		super(engine, eMoflonApi);
	}
	
	@Override
	public GipsConstraint<«data.gipsApiClassName», ? extends Constraint, ? extends Object> createConstraint(final Constraint constraint) {
		«IF context.constraints.isNullOrEmpty»
		throw new IllegalArgumentException("Unknown constraint type: "+constraint);
		«ELSE»
		switch(constraint.getName()) {
			«FOR constraint : context.constraints»
			case "«constraint.name»" -> {
			«IF constraint instanceof PatternConstraint»
				return new «data.constraint2constraintClassName.get(constraint)»(engine, («constraint.eClass.name»)constraint, eMoflonApi.«constraint.pattern.name.toFirstLower»(«FOR param: getPattern(constraint.pattern).parameters SEPARATOR ", "»«GipsImportManager.parameterToJavaDefaultValue(param)»«ENDFOR»));
			«ELSEIF constraint instanceof RuleConstraint»
			    return new «data.constraint2constraintClassName.get(constraint)»(engine, («constraint.eClass.name»)constraint, eMoflonApi.«constraint.rule.name.toFirstLower»(«FOR param: constraint.rule.parameters SEPARATOR ", "»«GipsImportManager.parameterToJavaDefaultValue(param)»«ENDFOR»));
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

	def IBeXContextPattern getPattern(IBeXPattern pattern) {
		if(pattern instanceof IBeXContextAlternatives) {
			return pattern.context
		} else {
			return pattern as IBeXContextPattern
		}
	} 
	
}