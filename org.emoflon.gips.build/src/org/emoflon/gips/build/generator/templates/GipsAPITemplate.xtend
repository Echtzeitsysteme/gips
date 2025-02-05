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
		packageName = data.gipsApiPkg;
		className = data.gipsApiClassName;
		fqn = packageName + "." + className;
		filePath = data.gipsApiPkgPath + "/" + className + ".java"
		imports.add("org.emoflon.gips.core.api.GipsEngineAPI")
		imports.add("org.emoflon.gips.core.GipsObjective")
		imports.add("org.emoflon.gips.core.milp.Solver")
		imports.add("org.emoflon.gips.core.milp.GurobiSolver")
		imports.add("org.emoflon.gips.core.milp.GlpkSolver")
		imports.add("org.emoflon.gips.core.milp.CplexSolver")
		imports.add("org.emoflon.gips.core.milp.SolverConfig")
		imports.add("org.eclipse.emf.ecore.resource.ResourceSet")
		imports.add(data.apiPackage + "." + data.apiAbstractClassName)
		imports.add(data.apiPackage + "." + data.apiClassNames.get(engine))
		imports.add("org.eclipse.emf.common.util.URI");
		if(data.gipsModel.objective !== null) {
			imports.add(data.gipsObjectivePkg+"."+data.objectiveClassName)
		}
		data.gipsModel.mappings
			.map[m | data.mapping2mapperClassName.get(m)]
			.forEach[m | imports.add(data.gipsMapperPkg+"."+m)]
	}
	
	override getConstants() {
		return context.constants.filter[c | c.isGlobal].toList;
	}
	
	override generate() {
		val codeForConstants = generateCodeForConstants();
		code = '''«generatePackageDeclaration()»
		
«generateImports()»
		
public class «data.gipsApiClassNames.get(engine)» extends «data.gipsApiClassName» <«data.apiClassNames.get(engine)»>
	final public static URI INTERMEDIATE_MODEL_URI = URI.createFileURI("«data.project.location.toPortableString»«data.intermediateModelURI.toPlatformString(false)»");
	
	«FOR mapping : data.gipsModel.mappings»
	protected «data.mapping2mapperClassName.get(mapping)» «mapping.name.toFirstLower»;
	«ENDFOR»
	
	public «className»() {
		super(new «data.apiClassNames.get(engine)»());
	}
	
	/**
	 * Initializes the GIPS engine API with the given model URI.
	 * 
	 * @param modelUri Model URI to load.
	 */
	@Override
	public void init(final URI modelUri) {
		super.initInternal(INTERMEDIATE_MODEL_URI, modelUri);
	}
	
	/**
	 * Initializes the GIPS engine API with the given GIPS intermediate model URI
	 * and the model URI.
	 * 
	 * @param gipsModelURI GIPS intermediate model URI to load.
	 * @param modelUri     Model URI to load.
	 */
	@Override
	public void init(final URI gipsModelURI, final URI modelUri) {
		super.initInternal(gipsModelURI, modelUri);
	}
	
	/**
	 * Initializes the GIPS API with a given GIPS intermediate model URI, a model URI,
	 * and the IBeX pattern path to avoid using hard-coded paths in IBeX
	 * internally.
	 * 
	 * @param gipsModelURI    GIPS intermediate model URI to load.
	 * @param modelUri        Model URI to load.
	 * @param ibexPatternPath IBeX pattern path to load.
	 */
	@Override
	public void init(final URI gipsModelURI, final URI modelUri, final URI ibexPatternPath) {
		super.initInternal(gipsModelURI, modelUri, ibexPatternPath);
	}
	
	/**
	 * Initializes the API with a given resource set as model.
	 * 
	 * @param model Resource set as model.
	 */
	@Override
	public void init(final ResourceSet model) {
		super.initInternal(INTERMEDIATE_MODEL_URI, model);
	}
	
	/**
	 * Initializes the GIPS engine API with the given GIPS intermediate model URI
	 * and a resource set as model.
	 * 
	 * @param gipsModelUri GIPS intermediate model URI to load.
	 * @param model        Resource set as model.
	 */
	@Override
	public void init(final URI gipsModelUri, final ResourceSet model) {
		super.initInternal(gipsModelUri, model);
	}
	
	/**
     * Initializes the GIPS engine API with the given GIPS intermediate model URI, a
	 * resource set as model, and the IBeX pattern path to avoid using hard-coded
	 * paths in IBeX.
	 * 
	 * @param gipsModelUri    GIPS intermediate model URI to load.
	 * @param model           Resource set as model.
	 * @param ibexPatternPath IBeX pattern path to load.
	 */
	@Override
	public void init(final URI gipsModelUri, final ResourceSet model, final URI ibexPatternPath) {
		super.initInternal(gipsModelUri, model, ibexPatternPath);
	}
	
	«FOR mapping : data.gipsModel.mappings»
	public «data.mapping2mapperClassName.get(mapping)» get«mapping.name.toFirstUpper»() {
		return «mapping.name.toFirstLower»;
	}
	«ENDFOR»
	
	@Override
	protected void createMappers() {
		«FOR mapping : data.gipsModel.mappings»
		«mapping.name.toFirstLower» = («data.mapping2mapperClassName.get(mapping)») mapperFactory.createMapper(name2Mapping.get("«mapping.name»"));
		addMapper(«mapping.name.toFirstLower»);
		«ENDFOR»
	}
	
	@Override
	protected void initMapperFactory() {
		mapperFactory = new «data.mapperFactoryClassName»(this, eMoflonAPI);
	}
	
	@Override	
	protected void initConstraintFactory() {
		constraintFactory = new «data.constraintFactoryClassName»(this, eMoflonAPI);
	}
	
	@Override	
	protected void initLinearFunctionFactory() {
		functionFactory = new «data.functionFactoryClassName»(this, eMoflonAPI);
	}
	
	@Override
	protected GipsObjective createObjective() {
		«IF data.gipsModel.objective === null»
		// No objective was defined!
		return null;
		«ELSE»
		return new «data.objectiveClassName»(this, gipsModel.getObjective());
		«ENDIF»
	}
	
	@Override
	protected Solver createSolver() {
		Solver solver = null;
		try {
			solver = «solverInit()»;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return solver;
	}
	
	«codeForConstants»
}'''
	}
	
	def String generateCodeForConstants() {
		return'''@Override
protected void updateConstants() {
«FOR constant : data.gipsModel.constants.filter[c | c.isGlobal]»
		addConstantValue("«getConstantName(constant)»", «getCallConstantCalculator(constant)»);
«ENDFOR»
}
			
«FOR constant : data.gipsModel.constants.filter[c | c.isGlobal]»
«getConstantCalculator(constant)»
«ENDFOR»'''
	}
	
	def String solverInit() {
		switch(data.gipsModel.config.solver) {
			case GUROBI: {
				return '''new GurobiSolver(this, solverConfig)'''
			}
			case GLPK: {
				return '''new GlpkSolver(this, solverConfig)'''
			}
			case CPLEX: {
				return '''new CplexSolver(this, solverConfig)'''
			}
		}
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
			imports.add(data.getPackageFQN(expression.type));
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