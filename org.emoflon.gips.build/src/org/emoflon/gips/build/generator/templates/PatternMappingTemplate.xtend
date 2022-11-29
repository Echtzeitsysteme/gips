package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.GeneratorTemplate
import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping
import org.emoflon.gips.build.generator.GipsImportManager

class PatternMappingTemplate extends GeneratorTemplate<PatternMapping> {
	
	IBeXContextPattern pattern;
	
	new(TemplateData data, PatternMapping context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiData.gipsMappingPkg
		className = data.mapping2mappingClassName.get(context)
		fqn = packageName + "." + className
		filePath = data.apiData.gipsMappingPkgPath + "/" + className + ".java"
		imports.add("org.emoflon.gips.core.gt.GTMapping")
		imports.add(data.apiData.rulesPkg+"."+data.mapping2patternClassName.get(context))
		imports.add(data.apiData.matchesPkg+"."+data.mapping2matchClassName.get(context))
		
		pattern = (context.pattern instanceof IBeXContextPattern) ? context.pattern as IBeXContextPattern : (context.pattern as IBeXContextAlternatives).context
		imports.addAll(data.classToPackage.getImportsForNodeTypes(pattern.signatureNodes))
		imports.add("java.util.List")
		imports.add("java.util.Map")
		imports.add("java.util.Collection")
	}
	
	override generate() {
		code = '''package «packageName»;
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public class «className» extends GTMapping<«data.mapping2matchClassName.get(context)», «data.mapping2patternClassName.get(context)»> {
	«IF !context.freeVariables.isNullOrEmpty»
	«FOR v : context.freeVariables»
	protected «GipsImportManager.variableToJavaDataType(v, imports)» «v.name.toFirstLower»;
	«ENDFOR»
	«ENDIF»
	public «className»(final String ilpVariable, final «data.mapping2matchClassName.get(context)» match) {
		super(ilpVariable, match);
		«IF !context.freeVariables.isNullOrEmpty»
		«FOR v : context.freeVariables»
		«v.name.toFirstLower» = new «GipsImportManager.variableToJavaDataType(v, imports)»(name + "_«v.name»");
		«ENDFOR»
		«ENDIF»
	}

	«FOR node : pattern.signatureNodes»
	public «node.type.name» get«node.name.toFirstUpper»() {
		return match.get«node.name.toFirstUpper»();
	}
	«ENDFOR»

	«IF !context.freeVariables.isNullOrEmpty»
	«FOR v : context.freeVariables»
	public «GipsImportManager.variableToJavaDataType(v, imports)» get«v.name.toFirstUpper»() {
		return «v.name.toFirstLower»;
	}
	
	«ENDFOR»
	«ENDIF»
	«IF !context.freeVariables.isNullOrEmpty»
	«FOR v : context.freeVariables»
	public void set«v.name.toFirstUpper»(final «GipsImportManager.variableToJavaDataType(v, imports)» «v.name.toFirstLower») {
		this.«v.name.toFirstLower» = «v.name.toFirstLower»;
	}
	
	«ENDFOR»
	«ENDIF»
	«IF !context.freeVariables.isNullOrEmpty»
	«FOR v : context.freeVariables»
	public «GipsImportManager.variableToSimpleJavaDataType(v, imports)» getValueOf«v.name.toFirstUpper»() {
		return «v.name.toFirstLower».getValue();
	}
	
	«ENDFOR»
	«ENDIF»
	«IF !context.freeVariables.isNullOrEmpty»
	«FOR v : context.freeVariables»
	public void setValueOf«v.name.toFirstUpper»(final «GipsImportManager.variableToSimpleJavaDataType(v, imports)» «v.name.toFirstLower») {
		this.«v.name.toFirstLower».setValue(«v.name.toFirstLower»);
	}
	
	«ENDFOR»
	«ENDIF»
	@Override
	public boolean hasAdditionalVariables() {
		return «IF !(context.freeVariables.isNullOrEmpty)»true«ELSE»false«ENDIF»;
	}
	
	@Override
	public boolean hasBoundVariables() {
		return false;
	}
	
	@Override
	public boolean hasFreeVariables() {
		return «IF !context.freeVariables.isNullOrEmpty»true«ELSE»false«ENDIF»;
	}
	
	@Override
	public Collection<String> getAdditionalVariableNames() {
		return List.of(«getAllVariableNames(",\n")»);
	}
	
	@Override
	public Map<String, ILPVariable<?>> getAdditionalVariables() {
		return Map.of(«getAllVariableEntries(",\n")»);
	}
	
	@Override
	public Collection<String> getFreeVariableNames() {
		return List.of(«getAllVariableNames(",\n")»);
	}
	
	@Override
	public Map<String, ILPVariable<?>> getFreeVariables() {
		return Map.of(«getAllVariableEntries(",\n")»);
	}
	
	@Override
	public Collection<String> getBoundVariableNames() {
		return List.of();
	}
	
	@Override
	public Map<String, ILPVariable<?>> getBoundVariables() {
		return Map.of();
	}
	
	@Override
	public void setAdditionalVariableValue(final String valName, final double value) {
		«IF !(context.boundVariables.isNullOrEmpty && context.freeVariables.isNullOrEmpty)»
		switch(valName) {
			«IF !context.freeVariables.isNullOrEmpty»
			«FOR v : context.freeVariables»
			case "«v.name»" : {
				«v.name.toFirstLower».setValue((«GipsImportManager.variableToSimpleJavaDataType(v, imports)») value);
				break;
			}
			«ENDFOR»
			«ENDIF»
			case default: throw new IllegalArgumentException("This mapping <" + name + "> does not have a variable with the symbolic name <" + valname + ">.");
		}
		«ELSE»
		throw new UnsupportedOperationException("This mapping <" + name + "> does not have any additonal variables.");
		«ENDIF»
	}
}'''
	}
	
	def String getAllVariableNames(String separator) {
		return '''«IF !context.freeVariables.isNullOrEmpty»«FOR v : context.freeVariables SEPARATOR separator»"«v.name»"«ENDFOR»«ENDIF»'''
	}
	
	def String getAllVariableEntries(String separator) {
		return '''«IF !context.freeVariables.isNullOrEmpty»«FOR v : context.freeVariables SEPARATOR separator»"«v.name»", «v.name.toFirstLower»«ENDFOR»«ENDIF»'''
	}

}