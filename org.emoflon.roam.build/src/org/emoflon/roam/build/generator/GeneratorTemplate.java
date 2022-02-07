package org.emoflon.roam.build.generator;

import org.eclipse.emf.ecore.EObject;

public abstract class GeneratorTemplate <CONTEXT extends EObject>{
	final protected TemplateData data;
	final protected CONTEXT context;
	protected String filePath;
	protected String packageName;
	protected String className;
	protected String fqn;
	protected String code;
	
	
	public GeneratorTemplate(final TemplateData data, final CONTEXT context) {
		this.data = data;
		this.context = context;
	}
	
	public abstract void init(final String rootPkgPath, final String rootFQPackageName);
	
	public abstract void generate();
	
	public void writeToFile() {
		
	}
}
