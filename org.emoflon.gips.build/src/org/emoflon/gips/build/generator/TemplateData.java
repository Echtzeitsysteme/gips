package org.emoflon.gips.build.generator;

import java.util.HashMap;
import java.util.Map;

import org.emoflon.gips.build.GipsAPIData;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;
import org.emoflon.gips.intermediate.GipsIntermediate.Pattern;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;

public class TemplateData {
	final public GipsIntermediateModel model;
	final public GipsAPIData apiData;
	final public GipsImportManager classToPackage;

	public String gipsApiClassName;
	public String mapperFactoryClassName;
	public String constraintFactoryClassName;
	public String objectiveFactoryClassName;
	public String globalObjectiveClassName;

	final public Map<Mapping, String> mapping2mappingClassName = new HashMap<>();
	final public Map<Mapping, String> mapping2mapperClassName = new HashMap<>();
	final public Map<Mapping, String> mapping2ruleClassName = new HashMap<>();
	final public Map<Mapping, String> mapping2matchClassName = new HashMap<>();
	final public Map<Pattern, String> pattern2matchClassName = new HashMap<>();
	final public Map<Pattern, String> pattern2patternClassName = new HashMap<>();

	final public Map<Constraint, String> constraint2constraintClassName = new HashMap<>();
	final public Map<Objective, String> objective2objectiveClassName = new HashMap<>();

	public TemplateData(final GipsIntermediateModel model, final GipsAPIData apiData,
			final GipsImportManager classToPackage) {
		this.model = model;
		this.apiData = apiData;
		this.classToPackage = classToPackage;
		init();
	}

	private void init() {
		gipsApiClassName = apiData.apiClassNamePrefix + "GipsAPI";
		mapperFactoryClassName = apiData.apiClassNamePrefix + "GipsMapperFactory";
		constraintFactoryClassName = apiData.apiClassNamePrefix + "GipsConstraintFactory";
		objectiveFactoryClassName = apiData.apiClassNamePrefix + "GipsObjectiveFactory";
		model.getVariables().stream().filter(var -> var instanceof Mapping).map(var -> (Mapping) var)
				.forEach(mapping -> {
					mapping2mapperClassName.put(mapping, firstToUpper(mapping.getName()) + "Mapper");
					mapping2mappingClassName.put(mapping, firstToUpper(mapping.getName()) + "Mapping");
					mapping2ruleClassName.put(mapping, firstToUpper(mapping.getRule().getName()) + "Rule");
					mapping2matchClassName.put(mapping, firstToUpper(mapping.getRule().getName()) + "Match");
				});
		model.getVariables().stream().filter(var -> var instanceof Pattern).map(var -> (Pattern) var)
				.forEach(pattern -> {
					if (pattern.isIsRule()) {
						pattern2patternClassName.put(pattern, firstToUpper(pattern.getPattern().getName()) + "Rule");
					} else {
						pattern2patternClassName.put(pattern, firstToUpper(pattern.getPattern().getName()) + "Pattern");
					}
					pattern2matchClassName.put(pattern, firstToUpper(pattern.getPattern().getName()) + "Match");
				});
		model.getConstraints().stream().forEach(
				constraint -> constraint2constraintClassName.put(constraint, firstToUpper(constraint.getName())));
		model.getObjectives().stream()
				.forEach(objective -> objective2objectiveClassName.put(objective, firstToUpper(objective.getName())));
		if (model.getGlobalObjective() == null)
			return;

		globalObjectiveClassName = apiData.apiClassNamePrefix + "GipsGlobalObjective";
	}

	public static String firstToUpper(final String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
	}
}
