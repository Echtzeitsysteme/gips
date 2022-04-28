package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.build.generator.GeneratorTemplate
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import org.emoflon.gips.build.GipsAPIData
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping

class GipsAPITemplate extends GeneratorTemplate<GipsIntermediateModel> {
	
	new(TemplateData data, GipsIntermediateModel context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiData.gipsApiPkg;
		className = data.gipsApiClassName;
		fqn = packageName + "." + className;
		filePath = data.apiData.gipsApiPkgPath + "/" + className + ".java"
		imports.add("org.emoflon.gips.core.api.GipsEngineAPI")
		imports.add("org.emoflon.gips.core.GipsGlobalObjective")
		imports.add("org.emoflon.gips.core.ilp.ILPSolver")
		imports.add("org.emoflon.gips.core.ilp.GurobiSolver")
		imports.add("org.emoflon.gips.core.ilp.ILPSolverConfig")
		imports.add(data.apiData.apiPkg + "." + data.apiData.engineAppClasses.get(GipsAPIData.HIPE_ENGINE_NAME))
		imports.add(data.apiData.apiPkg + "." + data.apiData.apiClass)
		imports.add("org.eclipse.emf.common.util.URI");
		if(data.model.globalObjective !== null) {
			imports.add(data.apiData.gipsObjectivePkg+"."+data.globalObjectiveClassName)
		}
		data.model.variables
			.filter[v | v instanceof Mapping]
			.map[m | data.mapping2mapperClassName.get(m)]
			.forEach[m | imports.add(data.apiData.gipsMapperPkg+"."+m)]
	}
	
	override generate() {
		code = '''package «packageName»;
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public class «className» extends GipsEngineAPI <«data.apiData.engineAppClasses.get(GipsAPIData.HIPE_ENGINE_NAME)», «data.apiData.apiClass»>{
	final public static URI INTERMEDIATE_MODEL_URI = URI.createFileURI("«data.apiData.project.location.toPortableString»«data.apiData.intermediateModelURI.toPlatformString(false)»");
	
	«FOR mapping : data.model.variables.filter[v | v instanceof Mapping]»
	protected «data.mapping2mapperClassName.get(mapping)» «mapping.name.toFirstLower»;
	«ENDFOR»
	
	public «className»() {
		super(new «data.apiData.engineAppClasses.get(GipsAPIData.HIPE_ENGINE_NAME)»());
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
	protected GipsGlobalObjective createGlobalObjective() {
		«IF data.model.globalObjective === null»
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
		switch(data.model.config.solver) {
			case GUROBI: {
				return '''new GurobiSolver(this, solverConfig)'''
			}
		}
	}
	
}