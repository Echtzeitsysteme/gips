package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.GeneratorTemplate
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping
import org.emoflon.gips.build.generator.GipsApiData

class GipsAbstractAPITemplate extends GeneratorTemplate<GipsIntermediateModel> {
	
	new(GipsApiData data, GipsIntermediateModel context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.gipsApiPkg;
		className = data.gipsApiClassName;
		fqn = packageName + "." + className;
		filePath = data.gipsApiPkgPath + "/" + className + ".java"
		imports.add("org.emoflon.gips.core.api.GipsEngineAPI")
		imports.add("org.emoflon.gips.core.GipsGlobalObjective")
		imports.add("org.emoflon.gips.core.ilp.ILPSolver")
		imports.add("org.emoflon.gips.core.ilp.GurobiSolver")
		imports.add("org.emoflon.gips.core.ilp.GlpkSolver")
		imports.add("org.emoflon.gips.core.ilp.CplexSolver")
		imports.add("org.emoflon.gips.core.ilp.ILPSolverConfig")
		imports.add(data.apiPackage + "." + data.apiAbstractClassName)
		imports.add("org.eclipse.emf.common.util.URI");
		if(data.gipsModel.globalObjective !== null) {
			imports.add(data.gipsObjectivePkg+"."+data.globalObjectiveClassName)
		}
		data.gipsModel.variables
			.filter[v | v instanceof Mapping]
			.map[m | data.mapping2mapperClassName.get(m)]
			.forEach[m | imports.add(data.gipsMapperPkg+"."+m)]
	}
	
	override generate() {
		code = '''package «packageName»;
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public abstract class «className» <MOFLON_API extends «data.apiAbstractClassName»<?>> extends GipsEngineAPI <MOFLON_API>{
	final public static URI INTERMEDIATE_MODEL_URI = URI.createFileURI("«data.model.metaData.projectPath»/«data.gipsModelPath»");
	
	«FOR mapping : data.gipsModel.variables.filter[v | v instanceof Mapping]»
	protected «data.mapping2mapperClassName.get(mapping)» «mapping.name.toFirstLower»;
	«ENDFOR»
	
	public «className»(final MOFLON_API emoflonApi) {
		super(emoflonApi);
	}
	
	@Override
	public void init(final URI modelUri) {
		super.initInternal(INTERMEDIATE_MODEL_URI, modelUri);
	}
	
	@Override
	public void init(final URI gipsModelURI, final URI modelUri) {
		super.initInternal(gipsModelURI, modelUri);
	}
	
	«FOR mapping : data.gipsModel.variables.filter[v | v instanceof Mapping]»
	public «data.mapping2mapperClassName.get(mapping)» get«mapping.name.toFirstUpper»() {
		return «mapping.name.toFirstLower»;
	}
	«ENDFOR»
	
	@Override
	protected void createMappers() {
		«FOR mapping : data.gipsModel.variables.filter[v | v instanceof Mapping]»
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
	protected void initObjectiveFactory() {
		objectiveFactory = new «data.objectiveFactoryClassName»(this, eMoflonAPI);
	}
	
	@Override
	protected GipsGlobalObjective createGlobalObjective() {
		«IF data.gipsModel.globalObjective === null»
		// No global objective was defined!
		return null;
		«ELSE»
		return new «data.globalObjectiveClassName»(this, gipsModel.getGlobalObjective());
		«ENDIF»
	}
	
	@Override
	protected ILPSolver createSolver() {
		ILPSolver solver = null;
		try {
			solver = «solverInit()»;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return solver;
	}
}'''
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
	
}