package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.GeneratorTemplate
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping
import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives

class MappingTemplate extends GeneratorTemplate<Mapping> {
	
	IBeXContextPattern pattern;
	
	new(TemplateData data, Mapping context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiData.roamMappingPkg
		className = data.mapping2mappingClassName.get(context)
		fqn = packageName + "." + className
		filePath = data.apiData.roamMappingPkgPath + "/" + className + ".java"
		imports.add("org.emoflon.roam.core.gt.GTMapping")
		imports.add(data.apiData.rulesPkg+"."+data.mapping2ruleClassName.get(context))
		imports.add(data.apiData.matchesPkg+"."+data.mapping2matchClassName.get(context))
		
		pattern = (context.rule.lhs instanceof IBeXContextPattern) ? context.rule.lhs as IBeXContextPattern : (context.rule.lhs as IBeXContextAlternatives).context
		imports.addAll(data.classToPackage.getImportsForNodeTypes(pattern.signatureNodes))
	}
	
	override generate() {
		code = '''package «packageName»;
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public class «className» extends GTMapping<«data.mapping2matchClassName.get(context)», «data.mapping2ruleClassName.get(context)»> {
	public «className»(final String ilpVariable, final «data.mapping2matchClassName.get(context)» match) {
		super(ilpVariable, match);
	}

	«FOR node : pattern.signatureNodes»
	public «node.type.name» get«node.name.toFirstUpper»() {
		return match.get«node.name.toFirstUpper»();
	}
	«ENDFOR»
}'''
	}

}