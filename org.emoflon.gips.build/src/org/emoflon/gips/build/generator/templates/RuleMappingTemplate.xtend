package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives
import org.emoflon.gips.build.generator.GipsImportManager
import org.emoflon.gips.intermediate.GipsIntermediate.VariableType
import org.emoflon.gips.intermediate.GipsIntermediate.RuleMapping

class RuleMappingTemplate extends ClassGeneratorTemplate<RuleMapping> {
	
	IBeXContextPattern pattern;
	
	new(TemplateData data, RuleMapping context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiData.gipsMappingPkg
		className = data.mapping2mappingClassName.get(context)

		filePath = data.apiData.gipsMappingPkgPath + "/" + className + ".java"
		imports.add("org.emoflon.gips.core.gt.GipsGTMapping")
		imports.add("org.emoflon.gips.core.milp.model.Variable")
		imports.add("org.emoflon.gips.core.milp.model.IntegerVariable")
		imports.add("org.emoflon.gips.core.milp.model.RealVariable")
		imports.add("org.emoflon.gips.core.milp.model.BinaryVariable")
		imports.add(data.apiData.rulesPkg+"."+data.mapping2ruleClassName.get(context))
		imports.add(data.apiData.matchesPkg+"."+data.mapping2matchClassName.get(context))
		
		pattern = (context.rule.lhs instanceof IBeXContextPattern) ? context.rule.lhs as IBeXContextPattern : (context.rule.lhs as IBeXContextAlternatives).context
		imports.addAll(data.classToPackage.getImportsForNodeTypes(pattern.signatureNodes))
		imports.add("java.util.List")
		imports.add("java.util.Map")
		imports.add("java.util.Collection")
	}
		
	override generateClassContent() {
		'''		
		public class «className» extends GipsGTMapping<«data.mapping2matchClassName.get(context)», «data.mapping2ruleClassName.get(context)»> {
			
			«IF !context.freeVariables.isNullOrEmpty»
				«FOR v : context.freeVariables»
					protected «GipsImportManager.variableToJavaDataType(v, imports)» «v.name.toFirstLower»;
				«ENDFOR»
			«ENDIF»

			«IF !context.boundVariables.isNullOrEmpty»
				«FOR v : context.boundVariables»
					protected «GipsImportManager.variableToJavaDataType(v, imports)» «v.name.toFirstLower»;
				«ENDFOR»
			«ENDIF»

			public «className»(final String milpVariable, final boolean hasBinaryVariable, final «data.mapping2matchClassName.get(context)» match) {
				super(milpVariable, hasBinaryVariable, match);
				
				«IF !context.freeVariables.isNullOrEmpty»
					«FOR v : context.freeVariables»
						«v.name.toFirstLower» = new «GipsImportManager.variableToJavaDataType(v, imports)»(name + "->«v.name»");
					«ENDFOR»
				«ENDIF»

				«IF !context.boundVariables.isNullOrEmpty»
					«FOR v : context.boundVariables»
						«v.name.toFirstLower» = new «GipsImportManager.variableToJavaDataType(v, imports)»(name + "->«v.name»");
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
			«IF !context.boundVariables.isNullOrEmpty»
				«FOR v : context.boundVariables»
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
			«IF !context.boundVariables.isNullOrEmpty»
				«FOR v : context.boundVariables»
					public void set«v.name.toFirstUpper»(final «GipsImportManager.variableToJavaDataType(v, imports)» «v.name.toFirstLower») {
						this.«v.name.toFirstLower» = «v.name.toFirstLower»;
					}
				
				«ENDFOR»
			«ENDIF»
			«IF !context.freeVariables.isNullOrEmpty»
				«FOR v : context.freeVariables»
					public «GipsImportManager.variableToSimpleJavaDataType(v, imports)» getValueOf«v.name.toFirstUpper»() {
						«IF GipsImportManager.variableToSimpleJavaDataType(v, imports) == "boolean"»
							return «v.name.toFirstLower».getValue() != 0;
						«ELSE»
							return «v.name.toFirstLower».getValue();
						«ENDIF»
					}
					
				«ENDFOR»
			«ENDIF»
			«IF !context.boundVariables.isNullOrEmpty»
				«FOR v : context.boundVariables»
					public «GipsImportManager.variableToSimpleJavaDataType(v, imports)» getValueOf«v.name.toFirstUpper»() {
						«IF GipsImportManager.variableToSimpleJavaDataType(v, imports) == "boolean"»
							return «v.name.toFirstLower».getValue() != 0;
						«ELSE»
							return «v.name.toFirstLower».getValue();
						«ENDIF»
					}
					
				«ENDFOR»
			«ENDIF»
			«IF !context.freeVariables.isNullOrEmpty»
				«FOR v : context.freeVariables»
					public void setValueOf«v.name.toFirstUpper»(final «GipsImportManager.variableToSimpleJavaDataType(v, imports)» «v.name.toFirstLower») {
						«IF GipsImportManager.variableToSimpleJavaDataType(v, imports) == "boolean"»
							this.«v.name.toFirstLower».setValue(«v.name.toFirstLower» ? 1 : 0);
						«ELSE»
							this.«v.name.toFirstLower».setValue(«v.name.toFirstLower»);
						«ENDIF»
					}
					
				«ENDFOR»
			«ENDIF»
			«IF !context.boundVariables.isNullOrEmpty»
				«FOR v : context.boundVariables»
					public void setValueOf«v.name.toFirstUpper»(final «GipsImportManager.variableToSimpleJavaDataType(v, imports)» «v.name.toFirstLower») {
						«IF GipsImportManager.variableToSimpleJavaDataType(v, imports) == "boolean"»
							this.«v.name.toFirstLower».setValue(«v.name.toFirstLower» ? 1 : 0);
						«ELSE»
							this.«v.name.toFirstLower».setValue(«v.name.toFirstLower»);
						«ENDIF»
					}
					
				«ENDFOR»
			«ENDIF»
			@Override
			public boolean hasAdditionalVariables() {
				return «IF !(context.boundVariables.isNullOrEmpty && context.freeVariables.isNullOrEmpty)»true«ELSE»false«ENDIF»;
			}
			
			@Override
			public boolean hasBoundVariables() {
				return «IF !context.boundVariables.isNullOrEmpty»true«ELSE»false«ENDIF»;
			}
			
			@Override
			public boolean hasFreeVariables() {
				return «IF !context.freeVariables.isNullOrEmpty»true«ELSE»false«ENDIF»;
			}
			
			@Override
			public Collection<String> getAdditionalVariableNames() {
				return List.of(«generateAllVariableNames(",\n")»);
			}
			
			@Override
			public Map<String, Variable<?>> getAdditionalVariables() {
				return Map.of(«generateAllVariableEntries(",\n")»);
			}
			
			@Override
			public Collection<String> getFreeVariableNames() {
				return List.of(«generateFreeVariableNames(",\n")»);
			}
			
			@Override
			public Map<String, Variable<?>> getFreeVariables() {
				return Map.of(«generateFreeVariableEntries(",\n")»);
			}
			
			@Override
			public Collection<String> getBoundVariableNames() {
				return List.of(«generateBoundVariableNames(",\n")»);
			}
			
			@Override
			public Map<String, Variable<?>> getBoundVariables() {
				return Map.of(«generateBoundVariableEntries(",\n")»);
			}
			
			@Override
			public void setAdditionalVariableValue(final String valName, final double value) {
				«IF !(context.boundVariables.isNullOrEmpty && context.freeVariables.isNullOrEmpty)»
					switch(valName) {
						«IF !context.freeVariables.isNullOrEmpty»
							«FOR v : context.freeVariables»
								case "«v.name»" : {
									«v.name.toFirstLower».setValue(«IF v.type == VariableType.BINARY»(int) value)«ELSEIF v.type == VariableType.INTEGER»Math.round((«GipsImportManager.variableToSimpleJavaDataType(v, imports)») value))«ELSE»(«GipsImportManager.variableToSimpleJavaDataType(v, imports)») value)«ENDIF»;
									break;
								}
							«ENDFOR»
						«ENDIF»
						«IF !context.boundVariables.isNullOrEmpty»
								«FOR v : context.boundVariables»
								case "«v.name»" : {
									«v.name.toFirstLower».setValue(«IF v.type == VariableType.BINARY»(int) value)«ELSEIF v.type == VariableType.INTEGER»Math.round((«GipsImportManager.variableToSimpleJavaDataType(v, imports)») value))«ELSE»(«GipsImportManager.variableToSimpleJavaDataType(v, imports)») value)«ENDIF»;
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
		}
		'''
	}
	
	def String generateAllVariableNames(String separator) {
		return '''«IF !context.freeVariables.isNullOrEmpty»«FOR v : context.freeVariables SEPARATOR separator»"«v.name»"«ENDFOR»«ENDIF»«IF !context.freeVariables.nullOrEmpty && !context.boundVariables.isNullOrEmpty»«separator»«ENDIF»«IF !context.boundVariables.isNullOrEmpty»«FOR v : context.boundVariables SEPARATOR separator»"«v.name»"«ENDFOR»«ENDIF»'''
	}
	
	def String generateAllVariableEntries(String separator) {
		return '''«IF !context.freeVariables.isNullOrEmpty»«FOR v : context.freeVariables SEPARATOR separator»"«v.name»", «v.name.toFirstLower»«ENDFOR»«ENDIF»«IF !context.freeVariables.nullOrEmpty && !context.boundVariables.isNullOrEmpty»«separator»«ENDIF»«IF !context.boundVariables.isNullOrEmpty»«FOR v : context.boundVariables SEPARATOR separator»"«v.name»", «v.name.toFirstLower»«ENDFOR»«ENDIF»'''
	}
	
	def String generateFreeVariableNames(String separator) {
		return '''«IF !context.freeVariables.isNullOrEmpty»«FOR v : context.freeVariables SEPARATOR separator»"«v.name»"«ENDFOR»«ENDIF»'''
	}
	
	def String generateFreeVariableEntries(String separator) {
		return '''«IF !context.freeVariables.isNullOrEmpty»«FOR v : context.freeVariables SEPARATOR separator»"«v.name»", «v.name.toFirstLower»«ENDFOR»«ENDIF»'''
	}
	
	def String generateBoundVariableNames(String separator) {
		return '''«IF !context.boundVariables.isNullOrEmpty»«FOR v : context.boundVariables SEPARATOR separator»"«v.name»"«ENDFOR»«ENDIF»'''
	}
	
	def String generateBoundVariableEntries(String separator) {
		return '''«IF !context.boundVariables.isNullOrEmpty»«FOR v : context.boundVariables SEPARATOR separator»"«v.name»", «v.name.toFirstLower»«ENDFOR»«ENDIF»'''
	}
	


}