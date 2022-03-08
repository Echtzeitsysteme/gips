package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.roam.build.generator.GeneratorTemplate
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel
import org.emoflon.roam.build.RoamAPIData
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping

class RoamAPITemplate extends GeneratorTemplate<RoamIntermediateModel> {
	
	new(TemplateData data, RoamIntermediateModel context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiData.roamApiPkg;
		className = data.roamApiClassName;
		fqn = packageName + "." + className;
		filePath = data.apiData.roamApiPkgPath + "/" + className + ".java"
		imports.add("org.emoflon.roam.core.api.RoamEngineAPI")
		imports.add("org.emoflon.roam.core.RoamGlobalObjective")
		imports.add("org.emoflon.roam.core.ilp.ILPSolver")
		imports.add("org.emoflon.roam.core.ilp.GurobiSolver")
		imports.add("org.emoflon.roam.core.ilp.ILPSolverConfig")
		imports.add(data.apiData.apiPkg + "." + data.apiData.engineAppClasses.get(RoamAPIData.HIPE_ENGINE_NAME))
		imports.add(data.apiData.apiPkg + "." + data.apiData.apiClass)
		imports.add("org.eclipse.emf.common.util.URI");
		if(data.model.globalObjective !== null) {
			imports.add(data.apiData.roamObjectivePkg+"."+data.globalObjectiveClassName)
		}
		data.model.variables
			.filter[v | v instanceof Mapping]
			.map[m | data.mapping2mapperClassName.get(m)]
			.forEach[m | imports.add(data.apiData.roamMapperPkg+"."+m)]
	}
	
	override generate() {
		code = '''package «packageName»;
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public class «className» extends RoamEngineAPI <«data.apiData.engineAppClasses.get(RoamAPIData.HIPE_ENGINE_NAME)», «data.apiData.apiClass»>{
	final public static URI INTERMEDIATE_MODEL_URI = URI.createURI("«data.apiData.intermediateModelURI.toPlatformString(false)»");
	
	«FOR mapping : data.model.variables.filter[v | v instanceof Mapping]»
	protected «data.mapping2mapperClassName.get(mapping)» «mapping.name.toFirstLower»;
	«ENDFOR»
	
	public «className»() {
		super(new «data.apiData.engineAppClasses.get(RoamAPIData.HIPE_ENGINE_NAME)»());
	}
	
	@Override
	public void init(final URI modelUri) {
		super.init(INTERMEDIATE_MODEL_URI, modelUri);
	}
	
	«FOR mapping : data.model.variables.filter[v | v instanceof Mapping]»
	public «data.mapping2mapperClassName.get(mapping)» get«mapping.name.toFirstUpper»() {
		return «mapping.name.toFirstLower»;
	}
	«ENDFOR»
	
	@Override
	protected void createMappers() {
		«FOR mapping : data.model.variables.filter[v | v instanceof Mapping]»
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
	protected RoamGlobalObjective createGlobalObjective() {
		«IF data.model.globalObjective === null»
		// No global objective was defined!
		return null;
		«ELSE»
		return new «data.globalObjectiveClassName»(this, roamModel.getGlobalObjective());
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
		switch(data.model.config.solver) {
			case GUROBI: {
				return '''new GurobiSolver(this, solverConfig)'''
			}
		}
	}
	
}