package org.emoflon.gips.build.generator.templates

import org.eclipse.emf.ecore.EObject
import org.emoflon.gips.build.generator.TemplateData
import java.nio.file.Files
import java.nio.file.Path

abstract class FileGeneratorTemplate <CONTEXT extends EObject>  {
	final protected TemplateData data;
	final protected CONTEXT context;
	
	protected String filePath;
	protected String fileContent;
	
	new(TemplateData data, CONTEXT context) {
		this.data = data;
		this.context = context;
	}
	
	def void init() 
	
	def String generateFileContent()

	final def void generate() {
		fileContent = generateFileContent()
	}
	 	
	final def void writeToFile() throws Exception {
		val path = Path.of(data.apiData.project.getLocation().toOSString).resolve(filePath)
		Files.write(path, fileContent.getBytes())		
		data.traceMap.map(context, filePath)
	}
}