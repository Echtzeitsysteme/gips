package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.GeneratorTemplate
import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective

class ObjectiveFactoryTemplate extends GeneratorTemplate<GipsIntermediateModel> {
	
	new(TemplateData data, GipsIntermediateModel context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.gipsApiPkg
		className = data.objectiveFactoryClassName
		fqn = packageName + "." + className;
		filePath = data.apiData.gipsApiPkgPath + "/" + className + ".java"
		imports.add(data.apiData.apiPkg + "." + data.apiData.apiClass)
		imports.add("org.emoflon.gips.core.api.GipsObjectiveFactory")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsObjective")
		imports.add("org.emoflon.gips.core.GipsTypeObjective")
		imports.add("org.emoflon.gips.core.GipsMappingObjective")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.Objective")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.TypeObjective")
		imports.add(data.apiData.gipsApiPkg+"."+data.gipsApiClassName)
		data.objective2objectiveClassName.values.forEach[o | imports.add(data.apiData.gipsObjectivePkg+"."+o)]
	}
	
	override generate() {
		code = '''package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends GipsObjectiveFactory<«data.gipsApiClassName», «data.apiData.apiClass»> {
	public «className»(final «data.gipsApiClassName» engine, final «data.apiData.apiClass» eMoflonApi) {
		super(engine, eMoflonApi);
	}
	
	@Override
	public GipsObjective<«data.gipsApiClassName», ? extends Objective, ? extends Object> createObjective(final Objective objective) {
		«IF context.objectives.isNullOrEmpty»
		throw new IllegalArgumentException("Unknown objective type: "+objective);
		«ELSE»
		switch(objective.getName()) {
			«FOR objective : context.objectives»
			case "«objective.name»" -> {
				«IF objective instanceof PatternObjective»
				return new «data.objective2objectiveClassName.get(objective)»(engine, («objective.eClass.name»)objective, eMoflonApi.«objective.pattern.name.toFirstLower»());
				«ELSE»
				return new «data.objective2objectiveClassName.get(objective)»(engine, («objective.eClass.name»)objective);
				«ENDIF»
			}
			«ENDFOR»
			default -> {
				throw new IllegalArgumentException("Unknown objective type: "+objective);	
			}
		}
		«ENDIF»
			
	}
}'''
	}

	
}