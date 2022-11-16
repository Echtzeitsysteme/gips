package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.GeneratorTemplate
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping
import org.emoflon.gips.build.generator.GipsApiData

class PatternMapperTemplate extends GeneratorTemplate<PatternMapping> {
	
	new(GipsApiData data, PatternMapping context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.gipsMapperPkg
		className = data.mapping2mapperClassName.get(context)
		fqn = packageName + "." + className
		filePath = data.gipsMapperPkgPath + "/" + className + ".java"
		imports.add(data.apiPackage+"."+data.apiAbstractClassName)
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.gt.PatternMapper")
		imports.add(data.gipsMappingPkg+"."+data.mapping2mappingClassName.get(context))
		imports.add(data.rulePackage+"."+data.mapping2patternClassName.get(context))
		imports.add(data.matchPackage+"."+data.mapping2matchClassName.get(context))
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