package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.GeneratorTemplate
import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel

class ObjectiveFactoryTemplate extends GeneratorTemplate<RoamIntermediateModel> {
	
	new(TemplateData data, RoamIntermediateModel context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.roamApiPkg
		className = data.objectiveFactoryClassName
		fqn = packageName + "." + className;
		filePath = data.apiData.roamApiPkgPath + "/" + className + ".java"
		imports.add("org.emoflon.roam.core.api.RoamObjectiveFactory")
		imports.add("org.emoflon.roam.core.RoamEngine")
		imports.add("org.emoflon.roam.core.RoamObjective")
		imports.add("org.emoflon.roam.core.RoamTypeObjective")
		imports.add("org.emoflon.roam.core.RoamMappingObjective")
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.Objective")
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.PatternObjective")
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective")
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.TypeObjective")
		data.objective2objectiveClassName.values.forEach[o | imports.add(data.apiData.roamObjectivePkg+"."+o)]
	}
	
	override generate() {
		code = '''package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends RoamObjectiveFactory {
	public «className»(final RoamEngine engine) {
		super(engine);
	}
	
	@Override
	public RoamObjective<? extends Objective, ? extends Object, ? extends Number> createObjective(final Objective objective) {
		«IF context.objectives.isNullOrEmpty»
		throw new IllegalArgumentException("Unknown objective type: "+objective);
		«ELSE»
		switch(objective.getName()) {
			«FOR objective : context.objectives»
			case "«objective.name»" -> {
				return new «data.objective2objectiveClassName.get(objective)»(engine, («objective.eClass.name»)objective);
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