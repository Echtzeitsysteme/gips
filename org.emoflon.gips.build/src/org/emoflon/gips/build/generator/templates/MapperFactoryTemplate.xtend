package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel

import java.util.List
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping
import org.emoflon.gips.intermediate.GipsIntermediate.RuleMapping
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping
import java.util.Collection
import org.emoflon.gips.build.GipsAPIData
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameter
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule

class MapperFactoryTemplate extends GeneratorTemplate<GipsIntermediateModel> {
	
	List<Mapping> mappings;
	
	new(GipsAPIData data, GipsIntermediateModel context) {
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
		mappings = context.mappings
		mappings.forEach[mapping | imports.add(data.gipsMapperPkg+"."+data.mapping2mapperClassName.get(mapping))]
	}
	
	override generate() {
		code = '''package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends GipsMapperFactory<«data.apiAbstractClassName»> {
	public «className»(final GipsEngine engine, final «data.apiAbstractClassName» eMoflonApi) {
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
				return new «data.mapping2mapperClassName.get(mapping)»(engine, mapping, eMoflonApi.«mapping.rule.name.toFirstLower»(«FOR param:mapping.rule.parameters SEPARATOR ", "»«exprHelper.EDataType2ExactJava(param.type)»«ENDFOR»));
			}
			«ENDFOR»
			«FOR mapping : mappings.filter[m | m instanceof PatternMapping].map[m | m as PatternMapping]»
			case "«mapping.name»" -> {
				return new «data.mapping2mapperClassName.get(mapping)»(engine, mapping, eMoflonApi.«mapping.pattern.name.toFirstLower»(«FOR param:contextToParameter(mapping.pattern) SEPARATOR ", "»«exprHelper.EDataType2ExactJava(param.type)»«ENDFOR»));
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

	def Collection<GTParameter> contextToParameter(GTPattern context) {
		return context.parameters
	}
	
	def Collection<GTParameter> contextToParameter(GTRule context) {
		return context.parameters
	}
	
}