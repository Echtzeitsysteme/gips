package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.GeneratorTemplate
import org.emoflon.gips.intermediate.GipsIntermediate.GTMapping
import org.emoflon.gips.build.generator.GipsApiData

class GTMapperTemplate extends GeneratorTemplate<GTMapping> {
	
	new(GipsApiData data, GTMapping context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.gipsMapperPkg
		className = data.mapping2mapperClassName.get(context)
		fqn = packageName + "." + className
		filePath = data.gipsMapperPkgPath + "/" + className + ".java"
		imports.add(data.apiPackage+"."+data.apiAbstractClassName)
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.gt.GTMapper")
		imports.add(data.gipsMappingPkg+"."+data.mapping2mappingClassName.get(context))
		imports.add(data.rulePackage+"."+data.mapping2ruleClassName.get(context))
		imports.add(data.matchPackage+"."+data.mapping2matchClassName.get(context))
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.Mapping")
	}
	
	override generate() {
		code = '''package «packageName»;
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public class «className» extends GTMapper<«data.mapping2mappingClassName.get(context)», «data.mapping2matchClassName.get(context)», «data.mapping2ruleClassName.get(context)»> {
	public «className»(final GipsEngine engine, final Mapping mapping, final «data.mapping2ruleClassName.get(context)» rule) {
		super(engine, mapping, rule);
	}
	
	@Override
	protected «data.mapping2mappingClassName.get(context)» convertMatch(final String ilpVariable, final «data.mapping2matchClassName.get(context)» match) {
		return new «data.mapping2mappingClassName.get(context)»(ilpVariable, match);
	}
}'''
	}
	
}