package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.GeneratorTemplate
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel
import org.emoflon.roam.build.generator.TemplateData
import java.util.List
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping

class MapperFactoryTemplate extends GeneratorTemplate<RoamIntermediateModel> {
	
	List<Mapping> mappings;
	
	new(TemplateData data, RoamIntermediateModel context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiData.roamApiPkg;
		className = data.mapperFactoryClassName
		fqn = packageName + "." + className;
		filePath = data.apiData.roamApiPkgPath + "/" + className + ".java"
		imports.add(data.apiData.apiPkg + "." + data.apiData.apiClass)
		imports.add("org.emoflon.roam.core.api.RoamMapperFactory")
		imports.add("org.emoflon.roam.core.RoamEngine")
		imports.add("org.emoflon.roam.core.RoamMapper")
		imports.add("org.emoflon.roam.core.RoamMapping")
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.Mapping")
		//TODO: insert mapper imports!
		mappings = context.variables.filter[v | v instanceof Mapping].map[m | m  as Mapping].toList
		mappings.forEach[mapping | imports.add(data.apiData.roamMapperPkg+"."+data.mapping2mapperClassName.get(mapping))]
	}
	
	override generate() {
		code = '''package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends RoamMapperFactory {
	protected «data.apiData.apiClass» emoflonApi;
	public «className»(final RoamEngine engine) {
		super(engine);
		emoflonApi = («data.apiData.apiClass») engine.getEMoflonAPI();
	}
	
	@Override
	public RoamMapper<? extends RoamMapping> createMapper(final Mapping mapping) {
		«IF mappings.isEmpty»
		throw new IllegalArgumentException("Unknown mapping type: "+mapping);
		«ELSE»
		switch(mapping.getName()) {
			«FOR mapping : mappings»
			case "«mapping.name»" -> {
				return new «data.mapping2mapperClassName.get(mapping)»(engine, mapping, emoflonApi.«mapping.rule.name»());
			}
			«ENDFOR»
			default -> {
				throw new IllegalArgumentException("Unknown mapping type: "+mapping);	
			}
		}
		«ENDIF»
			
	}
}'''
	}

	
}