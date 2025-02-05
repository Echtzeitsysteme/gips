package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping
import org.emoflon.gips.build.GipsAPIData
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern

class PatternMappingTemplate extends GeneratorTemplate<PatternMapping> {
	
	GTPattern pattern;
	
	new(GipsAPIData data, PatternMapping context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.gipsMappingPkg
		className = data.mapping2mappingClassName.get(context)
		fqn = packageName + "." + className
		filePath = data.gipsMappingPkgPath + "/" + className + ".java"
		imports.add("org.emoflon.gips.core.gt.GipsGTMapping")
		imports.add("org.emoflon.gips.core.milp.model.Variable")
		imports.add("org.emoflon.gips.core.milp.model.IntegerVariable")
		imports.add("org.emoflon.gips.core.milp.model.RealVariable")
		imports.add("org.emoflon.gips.core.milp.model.BinaryVariable")
		imports.add(data.rulePackage+"."+data.mapping2patternClassName.get(context))
		imports.add(data.matchPackage+"."+data.mapping2matchClassName.get(context))
		
		pattern = context.pattern
		pattern.signatureNodes.forEach[sn | imports.add(data.getFQN(sn.type))]
		imports.add("java.util.List")
		imports.add("java.util.Map")
		imports.add("java.util.Collection")
	}
	
	override generate() {
		code = '''package «packageName»;
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public class «className» extends GipsGTMapping<«data.mapping2matchClassName.get(context)», «data.mapping2patternClassName.get(context)»> {
	«IF !context.freeVariables.isNullOrEmpty»
	«FOR v : context.freeVariables»
	protected «exprHelper.variableToJavaDataType(v)» «v.name.toFirstLower»;
	«ENDFOR»
	«ENDIF»
	public «className»(final String ilpVariable, final «data.mapping2matchClassName.get(context)» match) {
		super(ilpVariable, match);
		«IF !context.freeVariables.isNullOrEmpty»
		«FOR v : context.freeVariables»
		«v.name.toFirstLower» = new «exprHelper.variableToJavaDataType(v)»(name + "->«v.name»");
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
	public «exprHelper.variableToJavaDataType(v)» get«v.name.toFirstUpper»() {
		return «v.name.toFirstLower»;
	}
	
	«ENDFOR»
	«ENDIF»
	«IF !context.freeVariables.isNullOrEmpty»
	«FOR v : context.freeVariables»
	public void set«v.name.toFirstUpper»(final «exprHelper.variableToJavaDataType(v)» «v.name.toFirstLower») {
		this.«v.name.toFirstLower» = «v.name.toFirstLower»;
	}
	
	«ENDFOR»
	«ENDIF»
	«IF !context.freeVariables.isNullOrEmpty»
	«FOR v : context.freeVariables»
	public «exprHelper.variableToSimpleJavaDataType(v)» getValueOf«v.name.toFirstUpper»() {
		return «v.name.toFirstLower».getValue();
	}
	
	«ENDFOR»
	«ENDIF»
	«IF !context.freeVariables.isNullOrEmpty»
	«FOR v : context.freeVariables»
	public void setValueOf«v.name.toFirstUpper»(final «exprHelper.variableToSimpleJavaDataType(v)» «v.name.toFirstLower») {
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
	public Map<String, Variable<?>> getAdditionalVariables() {
		return Map.of(«getAllVariableEntries(",\n")»);
	}
	
	@Override
	public Collection<String> getFreeVariableNames() {
		return List.of(«getAllVariableNames(",\n")»);
	}
	
	@Override
	public Map<String, Variable<?>> getFreeVariables() {
		return Map.of(«getAllVariableEntries(",\n")»);
	}
	
	@Override
	public Collection<String> getBoundVariableNames() {
		return List.of();
	}
	
	@Override
	public Map<String, Variable<?>> getBoundVariables() {
		return Map.of();
	}
	
	@Override
	public void setAdditionalVariableValue(final String valName, final double value) {
		«IF !(context.boundVariables.isNullOrEmpty && context.freeVariables.isNullOrEmpty)»
		switch(valName) {
			«IF !context.freeVariables.isNullOrEmpty»
			«FOR v : context.freeVariables»
			case "«v.name»" : {
				«v.name.toFirstLower».setValue((«exprHelper.variableToSimpleJavaDataType(v)») value);
				break;
			}
			«ENDFOR»
			«ENDIF»
			default: throw new IllegalArgumentException("This mapping <" + name + "> does not have a variable with the symbolic name <" + valName + ">.");
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