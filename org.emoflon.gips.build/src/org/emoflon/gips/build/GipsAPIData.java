package org.emoflon.gips.build;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.emoflon.gips.intermediate.GipsIntermediate.Constant;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.NamedElement;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleMapping;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement;
import org.emoflon.ibex.common.engine.IBeXPMEngineInformation;
import org.emoflon.ibex.gt.build.template.IBeXGTApiData;

public class GipsAPIData extends IBeXGTApiData {
	final public GipsIntermediateModel gipsModel;

	public IProject project;

	final public String gipsApiClassName;
	final public String mapperFactoryClassName;
	final public String constraintFactoryClassName;
	final public String functionFactoryClassName;
	final public String objectiveClassName;

	final public String gipsApiPkg;
	final public String gipsApiPkgPath;
	final public String gipsModelPath;
	final public String gipsMappingPkg;
	final public String gipsMappingPkgPath;
	final public String gipsMapperPkg;
	final public String gipsMapperPkgPath;
	final public String gipsConstraintPkg;
	final public String gipsConstraintPkgPath;
	final public String gipsObjectivePkg;
	final public String gipsObjectivePkgPath;

	public URI intermediateModelURI;

	final public Map<IBeXPMEngineInformation, String> gipsApiClassNames = new LinkedHashMap<>();

	final public Map<Mapping, String> mapping2mappingClassName = new HashMap<>();
	final public Map<Mapping, String> mapping2mapperClassName = new HashMap<>();
	final public Map<RuleMapping, String> mapping2ruleClassName = new HashMap<>();
	final public Map<PatternMapping, String> mapping2patternClassName = new HashMap<>();
	final public Map<Mapping, String> mapping2matchClassName = new HashMap<>();
	final public Map<IBeXNamedElement, String> ibex2matchClassName = new HashMap<>();
	final public Map<IBeXNamedElement, String> ibex2ibexClassName = new HashMap<>();
	final public Map<NamedElement, Collection<Constant>> context2constants = new HashMap<>();

	final public Map<Constraint, String> constraint2constraintClassName = new HashMap<>();
	final public Map<LinearFunction, String> function2functionClassName = new HashMap<>();

	public GipsAPIData(final IProject project, final GipsIntermediateModel gipsModel) {
		super(gipsModel.getIbexModel());
		this.project = project;
		this.gipsModel = gipsModel;

		gipsApiClassName = apiPrefix + "GipsApi";
		mapperFactoryClassName = apiPrefix + "GipsMapperFactory";
		constraintFactoryClassName = apiPrefix + "GipsConstraintFactory";
		functionFactoryClassName = apiPrefix + "GipsLinearFunctionFactory";
		if (gipsModel.getObjective() != null)
			objectiveClassName = apiPrefix + "GipsObjective";
		else
			objectiveClassName = null;

		gipsApiPkg = model.getMetaData().getPackage() + ".gips";
		gipsApiPkgPath = model.getMetaData().getPackagePath().replace("src", "src-gen") + "/gips";
		gipsModelPath = gipsApiPkgPath + "/gips_model.xmi";

		gipsMappingPkg = gipsApiPkg + ".mapping";
		gipsMapperPkg = gipsApiPkg + ".mapper";
		gipsConstraintPkg = gipsApiPkg + ".constraint";
		gipsObjectivePkg = gipsApiPkg + ".objective";

		gipsMappingPkgPath = gipsApiPkgPath + "/mapping";
		gipsMapperPkgPath = gipsApiPkgPath + "/mapper";
		gipsConstraintPkgPath = gipsApiPkgPath + "/constraint";
		gipsObjectivePkgPath = gipsApiPkgPath + "/objective";

		engines.forEach((extName, ext) -> gipsApiClassNames.put(ext, apiPrefix + firstToUpper(extName) + "GipsApi"));

		intermediateModelURI = URI.createPlatformResourceURI(gipsApiPkgPath + "/gips/gips-model.xmi", true);

		init2();
	}

	protected void init2() {
		gipsModel.getMappings().stream().forEach(mapping -> {
			mapping2mapperClassName.put(mapping, firstToUpper(mapping.getName()) + "Mapper");
			mapping2mappingClassName.put(mapping, firstToUpper(mapping.getName()) + "Mapping");
			if (mapping instanceof RuleMapping gtMapping) {
				mapping2ruleClassName.put(gtMapping, firstToUpper(gtMapping.getRule().getName()) + "Rule");
				mapping2matchClassName.put(gtMapping, firstToUpper(gtMapping.getRule().getName()) + "Match");
			} else {
				PatternMapping pmMapping = (PatternMapping) mapping;
				mapping2patternClassName.put(pmMapping, firstToUpper(pmMapping.getPattern().getName()) + "Pattern");
				mapping2matchClassName.put(pmMapping, firstToUpper(pmMapping.getPattern().getName()) + "Match");
			}
		});

		// Add global constants
		context2constants.put(gipsModel, new LinkedList<>());
		gipsModel.getConstants().stream().filter(c -> c.isGlobal())
				.forEach(c -> context2constants.get(gipsModel).add(c));

		// Add local constants
		gipsModel.getConstraints().stream().filter(c -> c.getConstants() != null && !c.getConstants().isEmpty())
				.forEach(c -> {
					context2constants.put(c, new LinkedList<>());
					context2constants.get(c).addAll(c.getConstants());
				});
		gipsModel.getFunctions().stream().filter(f -> f.getConstants() != null && !f.getConstants().isEmpty())
				.forEach(f -> {
					context2constants.put(f, new LinkedList<>());
					context2constants.get(f).addAll(f.getConstants());
				});
		if (gipsModel.getObjective() != null && gipsModel.getObjective().getConstants() != null
				&& !gipsModel.getObjective().getConstants().isEmpty()) {
			context2constants.put(gipsModel.getObjective(), new LinkedList<>());
			context2constants.get(gipsModel.getObjective()).addAll(gipsModel.getObjective().getConstants());
		}

		gipsModel.getRequiredPatterns().forEach(pattern -> {
			ibex2ibexClassName.put(pattern, firstToUpper(pattern.getName()) + "Pattern");
			ibex2matchClassName.put(pattern, firstToUpper(pattern.getName()) + "Match");
		});
		gipsModel.getRequiredRules().forEach(rule -> {
			ibex2ibexClassName.put(rule, firstToUpper(rule.getName()) + "Rule");
			ibex2matchClassName.put(rule, firstToUpper(rule.getName()) + "Match");
		});
		gipsModel.getConstraints().stream().forEach(
				constraint -> constraint2constraintClassName.put(constraint, firstToUpper(constraint.getName())));
		gipsModel.getFunctions().stream().forEach(fn -> function2functionClassName.put(fn, firstToUpper(fn.getName())));
	}

}
