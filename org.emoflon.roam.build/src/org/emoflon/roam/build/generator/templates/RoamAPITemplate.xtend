package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.roam.build.generator.GeneratorTemplate
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel
import org.emoflon.roam.build.RoamAPIData

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
		imports.add(data.apiData.apiPkg + "." + data.apiData.engineAppClasses.get(RoamAPIData.HIPE_ENGINE_NAME))
		imports.add(data.apiData.apiPkg + "." + data.apiData.apiClass)
		imports.add("import org.eclipse.emf.common.util.URI");
	}
	
	override generate() {
		code = '''package «packageName»
		
		«FOR imp : imports»
		import «imp»
		«ENDFOR»
		
		public class «className» extends RoamEngineAPI <«data.apiData.engineAppClasses.get(RoamAPIData.HIPE_ENGINE_NAME)», «data.apiData.apiClass»>{
			final public static URI INTERMEDIATE_MODEL_URI = URI.createURI("«data.apiData.intermediateModelURI.toPlatformString(false)»");
			public «className»() {
				super(new «data.apiData.engineAppClasses.get(RoamAPIData.HIPE_ENGINE_NAME)»());
			}
			
			@Override
			public void init(final URI modelUri) {
				super.init(INTERMEDIATE_MODEL_URI, modelUri);
			}
			
			@Override
			protected void initMapperFactory() {
				mapperFactory = new «data.mapperFactoryClassName»(roamEngine);
			}
			
			@Override	
			protected void initConstraintFactory() {
				constraintFactory = new «data.constraintFactoryClassName»(roamEngine);
			}
			
			@Override	
			protected void initObjectiveFactory() {
				objectiveFactory = new «data.objectiveFactoryClassName»(roamEngine);
			}
			
		}'''
	}
	
}