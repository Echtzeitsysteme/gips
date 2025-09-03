package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.RuleMapping

class RuleMapperTemplate extends GeneratorTemplate<RuleMapping> {
	
	new(TemplateData data, RuleMapping context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiData.gipsMapperPkg
		className = data.mapping2mapperClassName.get(context)
		fqn = packageName + "." + className
		filePath = data.apiData.gipsMapperPkgPath + "/" + className + ".java"
		imports.add(data.apiData.apiPkg+"."+data.apiData.apiClass)
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.gt.GipsRuleMapper")
		imports.add(data.apiData.gipsMappingPkg+"."+data.mapping2mappingClassName.get(context))
		imports.add(data.apiData.rulesPkg+"."+data.mapping2ruleClassName.get(context))
		imports.add(data.apiData.matchesPkg+"."+data.mapping2matchClassName.get(context))
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.Mapping")
	}
	
	override generate() {
		code = '''package «packageName»;
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public class «className» extends GipsRuleMapper<«data.mapping2mappingClassName.get(context)», «data.mapping2matchClassName.get(context)», «data.mapping2ruleClassName.get(context)»> {
	public «className»(final GipsEngine engine, final Mapping mapping, final «data.mapping2ruleClassName.get(context)» rule) {
		super(engine, mapping, rule);
	}
	
	@Override
	protected «data.mapping2mappingClassName.get(context)» convertMatch(final String milpVariable, final «data.mapping2matchClassName.get(context)» match) {
		return new «data.mapping2mappingClassName.get(context)»(milpVariable, match);
	}
}'''
	}
	
}