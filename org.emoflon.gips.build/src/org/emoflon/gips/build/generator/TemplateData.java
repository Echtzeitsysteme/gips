package org.emoflon.gips.build.generator;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.build.GipsAPIData;
import org.emoflon.gips.eclipse.trace.TraceMap;
import org.emoflon.gips.intermediate.GipsIntermediate.Constant;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.NamedElement;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleMapping;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNamedElement;

public class TemplateData {
	final public GipsIntermediateModel model;
	final public GipsAPIData apiData;
	final public GipsImportManager classToPackage;
	final public TraceMap<EObject, String> traceMap = new TraceMap<>();

	public String gipsApiClassName;
	public String mapperFactoryClassName;
	public String constraintFactoryClassName;
	public String functionFactoryClassName;
	public String objectiveClassName;

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
		functionFactoryClassName = apiData.apiClassNamePrefix + "GipsLinearFunctionFactory";
		model.getMappings().stream().forEach(mapping -> {
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
		context2constants.put(model, new LinkedList<>());
		model.getConstants().stream().filter(c -> c.isGlobal()).forEach(c -> context2constants.get(model).add(c));

		// Add local constants
		model.getConstraints().stream().filter(c -> c.getConstants() != null && !c.getConstants().isEmpty())
				.forEach(c -> {
					context2constants.put(c, new LinkedList<>());
					context2constants.get(c).addAll(c.getConstants());
				});
		model.getFunctions().stream().filter(f -> f.getConstants() != null && !f.getConstants().isEmpty())
				.forEach(f -> {
					context2constants.put(f, new LinkedList<>());
					context2constants.get(f).addAll(f.getConstants());
				});
		if (model.getObjective() != null && model.getObjective().getConstants() != null
				&& !model.getObjective().getConstants().isEmpty()) {
			context2constants.put(model.getObjective(), new LinkedList<>());
			context2constants.get(model.getObjective()).addAll(model.getObjective().getConstants());
		}

		model.getRequiredPatterns().forEach(pattern -> {
			ibex2ibexClassName.put(pattern, firstToUpper(pattern.getName()) + "Pattern");
			ibex2matchClassName.put(pattern, firstToUpper(pattern.getName()) + "Match");
		});
		model.getRequiredRules().forEach(rule -> {
			ibex2ibexClassName.put(rule, firstToUpper(rule.getName()) + "Rule");
			ibex2matchClassName.put(rule, firstToUpper(rule.getName()) + "Match");
		});
		model.getConstraints().stream().forEach(
				constraint -> constraint2constraintClassName.put(constraint, firstToUpper(constraint.getName())));
		model.getFunctions().stream().forEach(fn -> function2functionClassName.put(fn, firstToUpper(fn.getName())));
		if (model.getObjective() == null)
			return;

		objectiveClassName = apiData.apiClassNamePrefix + "GipsObjective";
	}

	public static String firstToUpper(final String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
	}
}
