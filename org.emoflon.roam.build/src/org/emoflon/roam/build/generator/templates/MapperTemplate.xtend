package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.GeneratorTemplate
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping
import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives

class MapperTemplate extends GeneratorTemplate<Mapping> {
	
	IBeXContextPattern pattern;
	
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
		
		pattern = (context.rule.lhs instanceof IBeXContextPattern) ? context.rule.lhs as IBeXContextPattern : (context.rule.lhs as IBeXContextAlternatives).context
	}
	
	override generate() {
		code = '''package «packageName»;
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public class «className» extends GTMapper<«data.mapping2mappingClassName.get(context)», «data.mapping2matchClassName.get(context)», «data.mapping2ruleClassName.get(context)»> {
	public «className»(final RoamEngine engine, final Mapping mapping) {
		super(engine, mapping);
	}
	
	@Override
	protected void init() {
		((«data.apiData.apiClass»)engine.getEMoflonAPI()).«context.rule.name»().subscribeAppearing(this::addMapping);
		((«data.apiData.apiClass»)engine.getEMoflonAPI()).«context.rule.name»().subscribeDisappearing(this::removeMapping);
	}
	
	@Override
	protected «data.mapping2mappingClassName.get(context)» convertMatch(final String ilpVariable, final «data.mapping2matchClassName.get(context)» match) {
		return new «data.mapping2mappingClassName.get(context)»(ilpVariable, match);
	}
	
	@Override
	protected void terminate() {
		((«data.apiData.apiClass»)engine.getEMoflonAPI()).«context.rule.name»().unsubscribeAppearing(this::addMapping);
		((«data.apiData.apiClass»)engine.getEMoflonAPI()).«context.rule.name»().unsubscribeDisappearing(this::removeMapping);
	}
}'''
	}
	
}