package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.GeneratorTemplate
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import java.util.List
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping
import org.emoflon.gips.intermediate.GipsIntermediate.GTMapping
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping
import org.emoflon.gips.build.generator.GipsApiData

class MapperFactoryTemplate extends GeneratorTemplate<GipsIntermediateModel> {
	
	List<Mapping> mappings;
	
	new(GipsApiData data, GipsIntermediateModel context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.gipsApiPkg;
		className = data.mapperFactoryClassName
		fqn = packageName + "." + className;
		filePath = data.gipsApiPkgPath + "/" + className + ".java"
		imports.add(data.apiPackage + "." + data.apiAbstractClassName)
		imports.add("org.emoflon.gips.core.api.GipsMapperFactory")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsMapper")
		imports.add("org.emoflon.gips.core.GipsMapping")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.Mapping")
		//TODO: insert mapper imports!
		mappings = context.variables.filter[v | v instanceof Mapping].map[m | m  as Mapping].toList
		mappings.forEach[mapping | imports.add(data.gipsMapperPkg+"."+data.mapping2mapperClassName.get(mapping))]
	}
	
	override generate() {
		code = '''package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends GipsMapperFactory<«data.apiAbstractClassName»<?>> {
	public «className»(final GipsEngine engine, final «data.apiAbstractClassName»<?> eMoflonApi) {
		super(engine, eMoflonApi);
	}
	
	@Override
	public GipsMapper<? extends GipsMapping> createMapper(final Mapping mapping) {
		«IF mappings.isEmpty»
		throw new IllegalArgumentException("Unknown mapping type: "+mapping);
		«ELSE»
		switch(mapping.getName()) {
			«FOR mapping : mappings.filter[m | m instanceof GTMapping].map[m | m as GTMapping]»
			case "«mapping.name»" -> {
				return new «data.mapping2mapperClassName.get(mapping)»(engine, mapping, eMoflonApi.«mapping.rule.name.toFirstLower»());
			}
			«ENDFOR»
			«FOR mapping : mappings.filter[m | m instanceof PatternMapping].map[m | m as PatternMapping]»
			case "«mapping.name»" -> {
				return new «data.mapping2mapperClassName.get(mapping)»(engine, mapping, eMoflonApi.«mapping.pattern.name.toFirstLower»());
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