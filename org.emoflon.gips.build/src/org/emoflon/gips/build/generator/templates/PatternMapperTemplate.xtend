package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping
import org.emoflon.gips.build.GipsAPIData

class PatternMapperTemplate extends GeneratorTemplate<PatternMapping> {
	
	new(GipsAPIData data, PatternMapping context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.gipsMapperPkg
		className = data.mapping2mapperClassName.get(context)
		fqn = packageName + "." + className
		filePath = data.gipsMapperPkgPath + "/" + className + ".java"
		imports.add(data.apiPackage+"."+data.apiAbstractClassName)
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.gt.GipsPatternMapper")
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
		
public class «className» extends GipsPatternMapper<«data.mapping2mappingClassName.get(context)», «data.mapping2matchClassName.get(context)», «data.mapping2patternClassName.get(context)»> {
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