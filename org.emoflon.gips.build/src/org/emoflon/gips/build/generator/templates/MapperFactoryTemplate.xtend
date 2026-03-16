package org.emoflon.gips.build.generator.templates

import java.util.Collection
import java.util.LinkedList
import java.util.List
import org.emoflon.gips.build.generator.GipsImportManager
import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping
import org.emoflon.gips.intermediate.GipsIntermediate.RuleMapping
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXParameter
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern

class MapperFactoryTemplate extends ClassGeneratorTemplate<GipsIntermediateModel> {

	List<Mapping> mappings;

	new(TemplateData data, GipsIntermediateModel context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.gipsApiPkg;
		className = data.mapperFactoryClassName

		filePath = data.apiData.gipsApiPkgPath + "/" + className + ".java"
		imports.add(data.apiData.apiPkg + "." + data.apiData.apiClass)
		imports.add("org.emoflon.gips.core.api.GipsMapperFactory")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsMapper")
		imports.add("org.emoflon.gips.core.GipsMapping")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.Mapping")
		// TODO: insert mapper imports!
		mappings = context.mappings
		mappings.forEach [ mapping |
			imports.add(data.apiData.gipsMapperPkg + "." + data.mapping2mapperClassName.get(mapping))
		]
	}

	def Collection<IBeXParameter> contextToParameter(IBeXPattern context) {
		val params = new LinkedList<IBeXParameter>
		if(context instanceof IBeXContextAlternatives) {
			if(context.context.parameters !== null) {
				params.addAll(context.context.parameters)
			}
		} else {
			val c = context as IBeXContextPattern
			if(c.parameters !== null) {
				params.addAll(c.parameters)
			}
		}
		return params
	}

	override generateClassContent() {
		'''
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
							«FOR mapping : mappings.filter[m | m instanceof RuleMapping].map[m | m as RuleMapping]»
								case "«mapping.name»" -> {
									return new «data.mapping2mapperClassName.get(mapping)»(engine, mapping, eMoflonApi.«mapping.rule.name»(«FOR param:mapping.rule.parameters SEPARATOR ", "»«GipsImportManager.parameterToJavaDefaultValue(param)»«ENDFOR»));
								}
							«ENDFOR»
							
							«FOR mapping : mappings.filter[m | m instanceof PatternMapping].map[m | m as PatternMapping]»
								case "«mapping.name»" -> {
									return new «data.mapping2mapperClassName.get(mapping)»(engine, mapping, eMoflonApi.«mapping.pattern.name»(«FOR param:contextToParameter(mapping.pattern) SEPARATOR ", "»«GipsImportManager.parameterToJavaDefaultValue(param)»«ENDFOR»));
								}
							«ENDFOR»
							
							default -> {
								throw new IllegalArgumentException("Unknown mapping type: "+mapping);	
							}
						}
					«ENDIF»
					
				}
			}
		'''
	}

}
