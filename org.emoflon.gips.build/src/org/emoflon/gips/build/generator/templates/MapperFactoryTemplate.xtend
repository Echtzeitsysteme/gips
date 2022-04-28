package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.GeneratorTemplate
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import org.emoflon.gips.build.generator.TemplateData
import java.util.List
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping

class MapperFactoryTemplate extends GeneratorTemplate<GipsIntermediateModel> {
	
	List<Mapping> mappings;
	
	new(TemplateData data, GipsIntermediateModel context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiData.gipsApiPkg;
		className = data.mapperFactoryClassName
		fqn = packageName + "." + className;
		filePath = data.apiData.gipsApiPkgPath + "/" + className + ".java"
		imports.add(data.apiData.apiPkg + "." + data.apiData.apiClass)
		imports.add("org.emoflon.gips.core.api.GipsMapperFactory")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsMapper")
		imports.add("org.emoflon.gips.core.GipsMapping")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.Mapping")
		//TODO: insert mapper imports!
		mappings = context.variables.filter[v | v instanceof Mapping].map[m | m  as Mapping].toList
		mappings.forEach[mapping | imports.add(data.apiData.gipsMapperPkg+"."+data.mapping2mapperClassName.get(mapping))]
	}
	
	override generate() {
		code = '''package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends GipsMapperFactory<«data.apiData.apiClass»> {
	public «className»(final GipsEngine engine, final «data.apiData.apiClass» eMoflonApi) {
		super(engine, eMoflonApi);
	}
	
	@Override
	public GipsMapper<? extends GipsMapping> createMapper(final Mapping mapping) {
		«IF mappings.isEmpty»
		throw new IllegalArgumentException("Unknown mapping type: "+mapping);
		«ELSE»
		switch(mapping.getName()) {
			«FOR mapping : mappings»
			case "«mapping.name»" -> {
				return new «data.mapping2mapperClassName.get(mapping)»(engine, mapping, eMoflonApi.«mapping.rule.name.toFirstLower»());
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