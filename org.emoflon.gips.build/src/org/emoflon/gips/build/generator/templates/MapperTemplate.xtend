package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.GeneratorTemplate
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping
import org.emoflon.roam.build.generator.TemplateData

class MapperTemplate extends GeneratorTemplate<Mapping> {
	
	new(TemplateData data, Mapping context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiData.roamMapperPkg
		className = data.mapping2mapperClassName.get(context)
		fqn = packageName + "." + className
		filePath = data.apiData.roamMapperPkgPath + "/" + className + ".java"
		imports.add(data.apiData.apiPkg+"."+data.apiData.apiClass)
		imports.add("org.emoflon.roam.core.RoamEngine")
		imports.add("org.emoflon.roam.core.gt.GTMapper")
		imports.add(data.apiData.roamMappingPkg+"."+data.mapping2mappingClassName.get(context))
		imports.add(data.apiData.rulesPkg+"."+data.mapping2ruleClassName.get(context))
		imports.add(data.apiData.matchesPkg+"."+data.mapping2matchClassName.get(context))
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.Mapping")
	}
	
	override generate() {
		code = '''package «packageName»;
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public class «className» extends GTMapper<«data.mapping2mappingClassName.get(context)», «data.mapping2matchClassName.get(context)», «data.mapping2ruleClassName.get(context)»> {
	public «className»(final RoamEngine engine, final Mapping mapping, final «data.mapping2ruleClassName.get(context)» rule) {
		super(engine, mapping, rule);
	}
	
	@Override
	protected «data.mapping2mappingClassName.get(context)» convertMatch(final String ilpVariable, final «data.mapping2matchClassName.get(context)» match) {
		return new «data.mapping2mappingClassName.get(context)»(ilpVariable, match);
	}
}'''
	}
	
}