package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.roam.build.generator.GeneratorTemplate
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel

class RoamAPITemplate extends GeneratorTemplate<RoamIntermediateModel> {
	
	new(TemplateData data, RoamIntermediateModel context) {
		super(data, context)
	}
	
	override init(String rootPkgPath, String rootFQPackageName) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override generate() {
		code = ''''''
	}
	
}