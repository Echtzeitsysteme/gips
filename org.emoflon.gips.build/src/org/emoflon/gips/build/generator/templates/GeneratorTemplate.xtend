package org.emoflon.gips.build.generator.templates

import java.util.Set
import java.util.HashSet
import org.eclipse.emf.ecore.EObject
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import java.nio.file.Files
import java.io.File
import org.emoflon.gips.build.GipsAPIData
import org.emoflon.gips.build.generator.GipsExpressionHelper

abstract class GeneratorTemplate <CONTEXT extends EObject>{
	final protected GipsAPIData data;
	final protected CONTEXT context;
	final protected GipsExpressionHelper exprHelper;

	protected String packageName;
	protected String className;
	protected String fqn;
	protected String filePath;

	protected String code;
	protected Set<String> imports = new HashSet();

	new (GipsAPIData data, CONTEXT context) {
		this.data = data;
		this.context = context;
		this.exprHelper = new GipsExpressionHelper(data, imports);
	}

	def void init();

	def void generate();

	def void writeToFile() throws Exception {
		val path = data.project.getLocation().toPortableString() + "/" + filePath;
		val file = new File(path);
		Files.write(file.toPath(), code.getBytes());
	}

	def boolean isMappingVariable(Variable variable) {
		return data.mapping2mappingClassName.keySet().stream().filter[m | m.getMappingVariable().equals(variable)]
				.findAny().isPresent();
	}
}