package org.emoflon.gips.build;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.emoflon.gips.build.generator.GipsApiData;
import org.emoflon.gips.build.generator.GipsCodeGenerator;
import org.emoflon.gips.build.transformation.GipsToIntermediate;
import org.emoflon.gips.gipsl.gipsl.EditorFile;
import org.emoflon.gips.gipsl.ui.nature.GIPSNature;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.ibex.gt.build.IBeXGtPackageBuilder;
import org.moflon.core.utilities.LogUtils;

public class GipsProjectBuilder extends IBeXGtPackageBuilder {

	private static Logger logger = Logger.getLogger(GipsProjectBuilder.class);

	final public static String GIPS_FOLDER = "gips";
	final public static String MAPPING_FOLDER = "mapping";
	final public static String MAPPER_FOLDER = "mapper";
	final public static String CONSTRAINT_FOLDER = "constraint";
	final public static String OBJECTIVE_FOLDER = "objective";

	protected File gipsPath;
	protected File mappingPath;
	protected File mapperPath;
	protected File constraintPath;
	protected File objectivePath;

	@Override
	public boolean hasProperNature(IProject project) {
		try {
			return null != project.getNature(GIPSNature.NATURE_ID);
		} catch (CoreException e) {
			LogUtils.error(logger, e);
			return false;
		}
	}

	@Override
	protected void createFoldersInPackage() {
		super.createFoldersInPackage();
		gipsPath = new File(pkgPath.getPath() + "/" + GIPS_FOLDER);
		mappingPath = new File(gipsPath.getPath() + "/" + MAPPING_FOLDER);
		mapperPath = new File(gipsPath.getPath() + "/" + MAPPER_FOLDER);
		constraintPath = new File(gipsPath.getPath() + "/" + CONSTRAINT_FOLDER);
		objectivePath = new File(gipsPath.getPath() + "/" + OBJECTIVE_FOLDER);

		gipsPath.mkdir();
		mappingPath.mkdir();
		mapperPath.mkdir();
		constraintPath.mkdir();
		objectivePath.mkdir();
	}

	@Override
	protected List<String> createDependencies() {
		List<String> deps = new LinkedList<>();
		deps.addAll(super.createDependencies());
		deps.add("org.emoflon.gips.core");
		return deps;
	}

	@Override
	protected List<String> createExports() {
		List<String> exps = new LinkedList<>();
		exps.addAll(super.createExports());
		exps.add(pkg + "." + GIPS_FOLDER);
		return exps;
	}

	@Override
	protected void generateDependingPackages() {
		GipsToIntermediate transformer = new GipsToIntermediate((EditorFile) editorFile, gtModel);
		GipsIntermediateModel model = null;
		try {
			model = transformer.transform();
		} catch (Exception e) {
			LogUtils.error(logger, e.toString());
			return;
		}
		GipsApiData apiData = new GipsApiData(model);
		GipsCodeGenerator generator = new GipsCodeGenerator(apiData);
		generator.generate();
		generator.saveModel();
	}

}
