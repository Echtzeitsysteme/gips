package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import org.emoflon.gips.build.GipsAPIData
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.intermediate.GipsIntermediate.Constant
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
import org.emoflon.gips.intermediate.GipsIntermediate.MappingReference
import org.emoflon.gips.intermediate.GipsIntermediate.TypeReference
import org.emoflon.gips.intermediate.GipsIntermediate.PatternReference
import org.emoflon.gips.intermediate.GipsIntermediate.RuleReference
import org.emoflon.gips.intermediate.GipsIntermediate.NodeReference
import org.emoflon.gips.intermediate.GipsIntermediate.AttributeReference
import org.emoflon.gips.intermediate.GipsIntermediate.ContextReference
import org.emoflon.ibex.common.engine.IBeXPMEngineInformation

class GipsAPITemplate extends ProblemGeneratorTemplate<GipsIntermediateModel> {
	
	val IBeXPMEngineInformation engine;
	
	new(GipsAPIData data, GipsIntermediateModel context, IBeXPMEngineInformation engine) {
		super(data, context)
		this.engine = engine;
	}
	
	override init() {
		packageName = data.gipsApiPkg
		className = data.gipsApiClassNames.get(engine)
		fqn = packageName + "." + className
		filePath = data.gipsApiPkgPath + "/" + className + ".java"
		imports.add("org.eclipse.emf.common.util.URI")
		imports.add("org.emoflon.gips.core.api.GipsEngineAPI")
		imports.add("hipe.engine.config.HiPEPathOptions")
		imports.add(data.apiPackage + "." + data.apiAbstractClassName)
		imports.add(data.apiPackage + "." + data.apiClassNames.get(engine))
	}
	
	override getConstants() {
		return context.constants.filter[c | c.isGlobal].toList
	}
	
	override generate() {
		code = '''«generatePackageDeclaration()»
		
«generateImports()»
		
public class «data.gipsApiClassNames.get(engine)» extends «data.gipsApiClassName»<«data.apiClassNames.get(engine)»>{
	
	public «data.gipsApiClassNames.get(engine)»() {
		super(new «data.apiClassNames.get(engine)»());
	}
	
	public «data.gipsApiClassNames.get(engine)»(final String ibexModelPath) {
		super(new «data.apiClassNames.get(engine)»(ibexModelPath));
	}
	
	«IF engine.engineName.contains("HiPE")»
	public «data.gipsApiClassNames.get(engine)»(final String ibexModelPath, final String hipeModelPath, final String hipeEngineClass) {
		super(createMoflonApi(ibexModelPath, hipeModelPath, hipeEngineClass));
	}
	
	public static «data.apiClassNames.get(engine)» createMoflonApi(final String ibexModelPath, final String hipeModelPath, final String hipeEngineClass) {
		URI hipeUri = URI.createFileURI(hipeModelPath);
		HiPEPathOptions hiPEPathOptions = HiPEPathOptions.getInstance();
		hiPEPathOptions.setNetworkPath(hipeUri);
		hiPEPathOptions.setEngineClassName(hipeEngineClass);
		return new «data.apiClassNames.get(engine)»(ibexModelPath);
	}	
	«ENDIF»

	
}'''
	}
	
	override generatePackageDeclaration() {
		return '''package «packageName»;'''
	}
	
	override generateImports() {
		return 
		'''«FOR imp : imports»
import «imp»;
		«ENDFOR»'''
	}
	
	override getVariable(Variable variable) {
		throw new UnsupportedOperationException("Variables cannot be accessed in a constant.")
	}
	
	override getContextParameterType() {
		return ''''''
	}
	
	override getContextParameter() {
		return ''''''
	}
	
	override getCallConstantCalculator(Constant constant) {
		return '''calculate«constant.name.toFirstUpper»()'''
	}
	
	override String getVariableInSet(Variable variable) {
		if(isMappingVariable(variable)) {
			return '''elt'''
		} else {
			return '''getNonMappingVariable(elt, "«variable.name»")'''
		}
	}
	
	override String getAdditionalVariableName(VariableReference varRef) {
		return '''getNonMappingVariable(context, "«varRef.variable.name»").getName()'''
	}
	
	override String generateValueAccess(ValueExpression expression) {
		var instruction = "";
		if(expression instanceof MappingReference) {
			imports.add(data.gipsMappingPkg+"."+data.mapping2mappingClassName.get(expression.mapping))
			instruction = '''getMapper("«expression.mapping.name»").getMappings().values().parallelStream()
			.map(mapping -> («data.mapping2mappingClassName.get(expression.mapping)») mapping)'''
		} else if(expression instanceof TypeReference) {
			imports.add(data.getFQN(expression.type));
			instruction = '''indexer.getObjectsOfType("«expression.type.name»").parallelStream()
						.map(type -> («expression.type.name») type)'''
		} else if(expression instanceof PatternReference) {
			imports.add(data.matchPackage+"."+data.ibex2matchClassName.get(expression.pattern))
			instruction = '''getEMoflonAPI().«expression.pattern.name»().findMatches(false).parallelStream()'''
		} else if(expression instanceof RuleReference) {
			imports.add(data.matchPackage+"."+data.ibex2matchClassName.get(expression.rule))
			instruction = '''getEMoflonAPI().«expression.rule.name»().findMatches(false).parallelStream()'''
		} else if(expression instanceof NodeReference) {
			instruction = getIterator(expression)
			instruction = '''«instruction».get«expression.node.name.toFirstUpper»()'''
			if(expression.attribute !== null) {
				instruction = '''«instruction».«generateAttributeExpression(expression.attribute)»'''
			}
		} else if(expression instanceof AttributeReference) {
			instruction = getIterator(expression)
			instruction = '''«instruction».«generateAttributeExpression(expression.attribute)»'''
		} else if(expression instanceof VariableReference) {
			// CASE: VariableReference -> return a constant 1 since the variable should have already been extracted.
			instruction = '''1.0'''
		} else {
			// CASE: ContextReference
			instruction = getIterator(expression as ContextReference)
		}
		return instruction;
	}
	
}