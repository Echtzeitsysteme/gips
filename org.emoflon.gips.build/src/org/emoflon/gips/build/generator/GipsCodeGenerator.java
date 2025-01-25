package org.emoflon.gips.build.generator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.emoflon.gips.build.GipsAPIData;
import org.emoflon.gips.build.generator.templates.GeneratorTemplate;
import org.emoflon.gips.build.generator.templates.GipsAPITemplate;
import org.emoflon.gips.build.generator.templates.LaunchFileTemplate;
import org.emoflon.gips.build.generator.templates.MapperFactoryTemplate;
import org.emoflon.gips.build.generator.templates.ObjectiveTemplate;
import org.emoflon.gips.build.generator.templates.PatternMapperTemplate;
import org.emoflon.gips.build.generator.templates.PatternMappingTemplate;
import org.emoflon.gips.build.generator.templates.RuleMapperTemplate;
import org.emoflon.gips.build.generator.templates.RuleMappingTemplate;
import org.emoflon.gips.build.generator.templates.constraint.ConstraintFactoryTemplate;
import org.emoflon.gips.build.generator.templates.constraint.GlobalConstraintTemplate;
import org.emoflon.gips.build.generator.templates.constraint.MappingConstraintTemplate;
import org.emoflon.gips.build.generator.templates.constraint.PatternConstraintTemplate;
import org.emoflon.gips.build.generator.templates.constraint.RuleConstraintTemplate;
import org.emoflon.gips.build.generator.templates.constraint.TypeConstraintTemplate;
import org.emoflon.gips.build.generator.templates.function.FunctionFactoryTemplate;
import org.emoflon.gips.build.generator.templates.function.MappingFunctionTemplate;
import org.emoflon.gips.build.generator.templates.function.PatternFunctionTemplate;
import org.emoflon.gips.build.generator.templates.function.RuleFunctionTemplate;
import org.emoflon.gips.build.generator.templates.function.TypeFunctionTemplate;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleMapping;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeFunction;

public class GipsCodeGenerator {

	final protected TemplateData data;
	protected List<GeneratorTemplate<?>> templates = Collections.synchronizedList(new LinkedList<>());

	public GipsCodeGenerator(final GipsIntermediateModel model, final GipsAPIData apiData,
			final GipsImportManager classToPackage) {
		data = new TemplateData(model, apiData, classToPackage);
	}

	public void generate() {
		templates.add(new GipsAPITemplate(data, data.model));
		templates.add(new MapperFactoryTemplate(data, data.model));
		templates.add(new ConstraintFactoryTemplate(data, data.model));
		templates.add(new FunctionFactoryTemplate(data, data.model));
		data.model.getMappings().parallelStream().forEach(mapping -> {
			if (mapping instanceof RuleMapping gtMapping) {
				templates.add(new RuleMappingTemplate(data, gtMapping));
				templates.add(new RuleMapperTemplate(data, gtMapping));
			} else {
				PatternMapping pmMapping = (PatternMapping) mapping;
				templates.add(new PatternMappingTemplate(data, pmMapping));
				templates.add(new PatternMapperTemplate(data, pmMapping));
			}

		});
		data.model.getConstraints().parallelStream().forEach(constraint -> {
			if (constraint instanceof MappingConstraint mappingConstraint) {
				templates.add(new MappingConstraintTemplate(data, mappingConstraint));
			} else if (constraint instanceof PatternConstraint patternConstraint) {
				templates.add(new PatternConstraintTemplate(data, patternConstraint));
			} else if (constraint instanceof RuleConstraint ruleConstraint) {
				templates.add(new RuleConstraintTemplate(data, ruleConstraint));
			} else if (constraint instanceof TypeConstraint typeConstraint) {
				templates.add(new TypeConstraintTemplate(data, typeConstraint));
			} else {
				templates.add(new GlobalConstraintTemplate(data, constraint));
			}
		});
		data.model.getFunctions().parallelStream().forEach(fn -> {
			if (fn instanceof MappingFunction mappingFn) {
				templates.add(new MappingFunctionTemplate(data, mappingFn));
			} else if (fn instanceof PatternFunction patternFn) {
				templates.add(new PatternFunctionTemplate(data, patternFn));
			} else if (fn instanceof RuleFunction ruleFn) {
				templates.add(new RuleFunctionTemplate(data, ruleFn));
			} else {
				TypeFunction typeFn = (TypeFunction) fn;
				templates.add(new TypeFunctionTemplate(data, typeFn));
			}
		});
		if (data.model.getObjective() != null) {
			templates.add(new ObjectiveTemplate(data, data.model.getObjective()));
		}
		templates.parallelStream().forEach(template -> {
			try {
				template.init();
				template.generate();
				template.writeToFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		if (data.model.getConfig().isBuildLaunchConfig()) {
			GeneratorTemplate<?> launchTemplate = new LaunchFileTemplate(data, data.model);
			try {
				launchTemplate.init();
				launchTemplate.generate();
				launchTemplate.writeToFile();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
