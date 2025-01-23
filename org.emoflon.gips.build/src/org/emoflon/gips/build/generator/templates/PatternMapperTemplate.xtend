package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping

class PatternMapperTemplate extends GeneratorTemplate<PatternMapping> {
	
	new(TemplateData data, PatternMapping context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiData.gipsMapperPkg
		className = data.mapping2mapperClassName.get(context)
		fqn = packageName + "." + className
		filePath = data.apiData.gipsMapperPkgPath + "/" + className + ".java"
		imports.add(data.apiData.apiPkg+"."+data.apiData.apiClass)
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.gt.PatternMapper")
		imports.add(data.apiData.gipsMappingPkg+"."+data.mapping2mappingClassName.get(context))
		imports.add(data.apiData.rulesPkg+"."+data.mapping2patternClassName.get(context))
		imports.add(data.apiData.matchesPkg+"."+data.mapping2matchClassName.get(context))
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.Mapping")
	}
	
	override generate() {
		code = '''package «packageName»;
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public class «className» extends PatternMapper<«data.mapping2mappingClassName.get(context)», «data.mapping2matchClassName.get(context)», «data.mapping2patternClassName.get(context)»> {
	public «className»(final GipsEngine engine, final Mapping mapping, final «data.mapping2patternClassName.get(context)» pattern) {
		super(engine, mapping, pattern);
	}
	
	@Override
	protected «data.mapping2mappingClassName.get(context)» convertMatch(final String ilpVariable, final «data.mapping2matchClassName.get(context)» match) {
		return new «data.mapping2mappingClassName.get(context)»(ilpVariable, match);
	}
}'''
	}
	
}