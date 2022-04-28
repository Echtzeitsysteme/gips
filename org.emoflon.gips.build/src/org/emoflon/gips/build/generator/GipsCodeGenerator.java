package org.emoflon.roam.build.generator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.emoflon.roam.build.RoamAPIData;
import org.emoflon.roam.build.generator.templates.ConstraintFactoryTemplate;
import org.emoflon.roam.build.generator.templates.GlobalObjectiveTemplate;
import org.emoflon.roam.build.generator.templates.LaunchFileTemplate;
import org.emoflon.roam.build.generator.templates.MapperFactoryTemplate;
import org.emoflon.roam.build.generator.templates.MapperTemplate;
import org.emoflon.roam.build.generator.templates.MappingConstraintTemplate;
import org.emoflon.roam.build.generator.templates.MappingObjectiveTemplate;
import org.emoflon.roam.build.generator.templates.MappingTemplate;
import org.emoflon.roam.build.generator.templates.ObjectiveFactoryTemplate;
import org.emoflon.roam.build.generator.templates.PatternConstraintTemplate;
import org.emoflon.roam.build.generator.templates.PatternObjectiveTemplate;
import org.emoflon.roam.build.generator.templates.RoamAPITemplate;
import org.emoflon.roam.build.generator.templates.TypeConstraintTemplate;
import org.emoflon.roam.build.generator.templates.TypeObjectiveTemplate;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.PatternConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.PatternObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeObjective;

public class RoamCodeGenerator {

	final protected TemplateData data;
	protected List<GeneratorTemplate<?>> templates = Collections.synchronizedList(new LinkedList<>());

	public RoamCodeGenerator(final RoamIntermediateModel model, final RoamAPIData apiData,
			final RoamImportManager classToPackage) {
		data = new TemplateData(model, apiData, classToPackage);
	}

	public void generate() {
		templates.add(new RoamAPITemplate(data, data.model));
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
