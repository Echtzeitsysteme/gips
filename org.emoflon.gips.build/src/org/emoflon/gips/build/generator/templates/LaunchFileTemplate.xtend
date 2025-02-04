package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import org.emoflon.gips.build.generator.TemplateData

class LaunchFileTemplate extends GeneratorTemplate<GipsIntermediateModel> {
	
	new(TemplateData data, GipsIntermediateModel context) {
		super(data, context)
	}
	
	override init() {
		filePath = data.apiData.gipsApiPkgPath + "/" + data.apiData.apiClassNamePrefix + "Launcher.launch"
	}
	
	override generate() {
		code = '''<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<launchConfiguration type="org.eclipse.jdt.launching.localJavaApplication">
	<listAttribute key="org.eclipse.debug.core.MAPPED_RESOURCE_PATHS">
		<listEntry value="/«data.apiData.project.name»"/>
	</listAttribute>
	<listAttribute key="org.eclipse.debug.core.MAPPED_RESOURCE_TYPES">
		<listEntry value="4"/>
	</listAttribute>
	<mapAttribute key="org.eclipse.debug.core.environmentVariables">
	    <mapEntry key="GRB_LICENSE_FILE" value="«context.config.solverLicenseFile»"/>
	    <mapEntry key="GUROBI_HOME" value="«context.config.solverHomeDir»"/>
	    <mapEntry key="LD_LIBRARY_PATH" value="«context.config.solverHomeDir»/lib/"/>
	    <mapEntry key="PATH" value="«context.config.solverHomeDir»/bin/"/>
	</mapAttribute>
	<booleanAttribute key="org.eclipse.jdt.launching.ATTR_ATTR_USE_ARGFILE" value="false"/>
	<booleanAttribute key="org.eclipse.jdt.launching.ATTR_SHOW_CODEDETAILS_IN_EXCEPTION_MESSAGES" value="true"/>
	<booleanAttribute key="org.eclipse.jdt.launching.ATTR_USE_CLASSPATH_ONLY_JAR" value="false"/>
	<booleanAttribute key="org.eclipse.jdt.launching.ATTR_USE_START_ON_FIRST_THREAD" value="true"/>
	<stringAttribute key="org.eclipse.jdt.launching.MAIN_TYPE" value="«context.config.mainFile»"/>
	<stringAttribute key="org.eclipse.jdt.launching.MODULE_NAME" value=""/>
	<stringAttribute key="org.eclipse.jdt.launching.PROJECT_ATTR" value="«data.apiData.project.name»"/>
</launchConfiguration>'''
	}
	
}