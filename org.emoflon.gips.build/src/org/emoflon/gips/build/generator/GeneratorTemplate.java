package org.emoflon.gips.build.generator;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.gt.build.template.ExpressionHelper;

public abstract class GeneratorTemplate<CONTEXT> {
	final protected GipsApiData data;
	final protected ExpressionHelper helper;
	final protected CONTEXT context;

	protected String packageName;
	protected String className;
	protected String fqn;
	protected String filePath;

	protected String code;
	protected Set<String> imports = new HashSet<>();

	public GeneratorTemplate(final GipsApiData data, final CONTEXT context) {
		this.data = data;
		this.context = context;
		this.helper = new ExpressionHelper(data, imports);
	}

	public abstract void init();

	public abstract void generate();

	public void writeToFile() throws Exception {
		String path = data.model.getMetaData().getProjectPath() + "/" + filePath;
		File file = new File(path);
		Files.write(file.toPath(), code.getBytes());
	}
}
