package org.emoflon.gips.build.generator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.emoflon.gips.build.GipsAPIData;
import org.emoflon.gips.build.generator.templates.ConstraintFactoryTemplate;
import org.emoflon.gips.build.generator.templates.GipsAPITemplate;
import org.emoflon.gips.build.generator.templates.GlobalObjectiveTemplate;
import org.emoflon.gips.build.generator.templates.LaunchFileTemplate;
import org.emoflon.gips.build.generator.templates.MapperFactoryTemplate;
import org.emoflon.gips.build.generator.templates.MapperTemplate;
import org.emoflon.gips.build.generator.templates.MappingConstraintTemplate;
import org.emoflon.gips.build.generator.templates.MappingObjectiveTemplate;
import org.emoflon.gips.build.generator.templates.MappingTemplate;
import org.emoflon.gips.build.generator.templates.ObjectiveFactoryTemplate;
import org.emoflon.gips.build.generator.templates.PatternConstraintTemplate;
import org.emoflon.gips.build.generator.templates.PatternObjectiveTemplate;
import org.emoflon.gips.build.generator.templates.TypeConstraintTemplate;
import org.emoflon.gips.build.generator.templates.TypeObjectiveTemplate;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeObjective;

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
		templates.add(new ObjectiveFactoryTemplate(data, data.model));
		data.model.getVariables().parallelStream().filter(mapping -> mapping instanceof Mapping)
				.map(mapping -> (Mapping) mapping).forEach(mapping -> {
					templates.add(new MappingTemplate(data, mapping));
					templates.add(new MapperTemplate(data, mapping));
				});
		data.model.getConstraints().parallelStream().forEach(constraint -> {
			if (constraint instanceof MappingConstraint mappingConstraint) {
				templates.add(new MappingConstraintTemplate(data, mappingConstraint));
			} else if (constraint instanceof PatternConstraint patternConstraint) {
				templates.add(new PatternConstraintTemplate(data, patternConstraint));
			} else {
				TypeConstraint typeConstraint = (TypeConstraint) constraint;
				templates.add(new TypeConstraintTemplate(data, typeConstraint));
			}
		});
		data.model.getObjectives().parallelStream().forEach(objective -> {
			if (objective instanceof MappingObjective mappingObjective) {
				templates.add(new MappingObjectiveTemplate(data, mappingObjective));
			} else if (objective instanceof PatternObjective patternObjective) {
				templates.add(new PatternObjectiveTemplate(data, patternObjective));
			} else {
				TypeObjective typeObjective = (TypeObjective) objective;
				templates.add(new TypeObjectiveTemplate(data, typeObjective));
			}
		});
		if (data.model.getGlobalObjective() != null) {
			templates.add(new GlobalObjectiveTemplate(data, data.model.getGlobalObjective()));
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
