package org.emoflon.gips.build.generator;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;

public abstract class GeneratorTemplate<CONTEXT extends EObject> {
	final protected TemplateData data;
	final protected CONTEXT context;

	protected String packageName;
	protected String className;
	protected String fqn;
	protected String filePath;

	protected String code;
	protected Set<String> imports = new HashSet<>();

	public GeneratorTemplate(final TemplateData data, final CONTEXT context) {
		this.data = data;
		this.context = context;
	}

	public abstract void init();

	public abstract void generate();

	public void writeToFile() throws Exception {
		String path = data.apiData.project.getLocation().toPortableString() + "/" + filePath;
		File file = new File(path);
		Files.write(file.toPath(), code.getBytes());
	}

	public boolean isMappingVariable(final Variable variable) {
		return data.mapping2mappingClassName.keySet().stream().filter(m -> m.getMappingVariable().equals(variable))
				.findAny().isPresent();
	}
}
