package org.emoflon.gips.build.generator;

import java.util.HashMap;
import java.util.Map;

import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.GTMapping;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;
import org.emoflon.gips.intermediate.GipsIntermediate.Pattern;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping;
import org.emoflon.ibex.gt.build.template.IBeXGTApiData;

public class GipsApiData extends IBeXGTApiData {

	final public GipsIntermediateModel gipsModel;

	final public String gipsApiClassName;
	final public String mapperFactoryClassName;
	final public String constraintFactoryClassName;
	final public String objectiveFactoryClassName;
	final public String globalObjectiveClassName;

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

	final public Map<Mapping, String> mapping2mappingClassName = new HashMap<>();
	final public Map<Mapping, String> mapping2mapperClassName = new HashMap<>();
	final public Map<GTMapping, String> mapping2ruleClassName = new HashMap<>();
	final public Map<PatternMapping, String> mapping2patternClassName = new HashMap<>();
	final public Map<Mapping, String> mapping2matchClassName = new HashMap<>();
	final public Map<Pattern, String> pattern2matchClassName = new HashMap<>();
	final public Map<Pattern, String> pattern2patternClassName = new HashMap<>();

	final public Map<Constraint, String> constraint2constraintClassName = new HashMap<>();
	final public Map<Objective, String> objective2objectiveClassName = new HashMap<>();

	public GipsApiData(final GipsIntermediateModel gipsModel) {
		super(gipsModel.getIbexModel());
		this.gipsModel = gipsModel;

		gipsApiClassName = apiPrefix + "GipsAPI";
		mapperFactoryClassName = apiPrefix + "GipsMapperFactory";
		constraintFactoryClassName = apiPrefix + "GipsConstraintFactory";
		objectiveFactoryClassName = apiPrefix + "GipsObjectiveFactory";
		if (gipsModel.getGlobalObjective() != null)
			globalObjectiveClassName = apiPrefix + "GipsGlobalObjective";
		else
			globalObjectiveClassName = null;

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

		init2();
	}

	protected void init2() {

		gipsModel.getVariables().stream().filter(var -> var instanceof Mapping).map(var -> (Mapping) var)
				.forEach(mapping -> {
					mapping2mapperClassName.put(mapping, firstToUpper(mapping.getName()) + "Mapper");
					mapping2mappingClassName.put(mapping, firstToUpper(mapping.getName()) + "Mapping");
					if (mapping instanceof GTMapping gtMapping) {
						mapping2ruleClassName.put(gtMapping, firstToUpper(gtMapping.getRule().getName()) + "Rule");
						mapping2matchClassName.put(gtMapping, firstToUpper(gtMapping.getRule().getName()) + "Match");
					} else {
						PatternMapping pmMapping = (PatternMapping) mapping;
						mapping2patternClassName.put(pmMapping,
								firstToUpper(pmMapping.getPattern().getName()) + "Pattern");
						mapping2matchClassName.put(pmMapping, firstToUpper(pmMapping.getPattern().getName()) + "Match");
					}

				});
		gipsModel.getVariables().stream().filter(var -> var instanceof Pattern).map(var -> (Pattern) var)
				.forEach(pattern -> {
					if (pattern.isIsRule()) {
						pattern2patternClassName.put(pattern, firstToUpper(pattern.getPattern().getName()) + "Rule");
					} else {
						pattern2patternClassName.put(pattern, firstToUpper(pattern.getPattern().getName()) + "Pattern");
					}
					pattern2matchClassName.put(pattern, firstToUpper(pattern.getPattern().getName()) + "Match");
				});
		gipsModel.getConstraints().stream().forEach(
				constraint -> constraint2constraintClassName.put(constraint, firstToUpper(constraint.getName())));
		gipsModel.getObjectives().stream()
				.forEach(objective -> objective2objectiveClassName.put(objective, firstToUpper(objective.getName())));

	}

}
