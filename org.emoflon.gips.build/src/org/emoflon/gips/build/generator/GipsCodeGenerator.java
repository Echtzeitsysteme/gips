package org.emoflon.gips.build.generator;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.gips.build.generator.templates.ConstraintFactoryTemplate;
import org.emoflon.gips.build.generator.templates.GTMapperTemplate;
import org.emoflon.gips.build.generator.templates.GTMappingTemplate;
import org.emoflon.gips.build.generator.templates.GipsAPITemplate;
import org.emoflon.gips.build.generator.templates.GipsAbstractAPITemplate;
import org.emoflon.gips.build.generator.templates.GlobalConstraintTemplate;
import org.emoflon.gips.build.generator.templates.GlobalObjectiveTemplate;
import org.emoflon.gips.build.generator.templates.LaunchFileTemplate;
import org.emoflon.gips.build.generator.templates.MapperFactoryTemplate;
import org.emoflon.gips.build.generator.templates.MappingConstraintTemplate;
import org.emoflon.gips.build.generator.templates.MappingObjectiveTemplate;
import org.emoflon.gips.build.generator.templates.ObjectiveFactoryTemplate;
import org.emoflon.gips.build.generator.templates.PatternConstraintTemplate;
import org.emoflon.gips.build.generator.templates.PatternMapperTemplate;
import org.emoflon.gips.build.generator.templates.PatternMappingTemplate;
import org.emoflon.gips.build.generator.templates.PatternObjectiveTemplate;
import org.emoflon.gips.build.generator.templates.TypeConstraintTemplate;
import org.emoflon.gips.build.generator.templates.TypeObjectiveTemplate;
import org.emoflon.gips.intermediate.GipsIntermediate.GTMapping;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeObjective;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage;
import org.moflon.core.utilities.LogUtils;

public class GipsCodeGenerator {
	private Logger logger = Logger.getLogger(GipsCodeGenerator.class);

	final protected GipsApiData data;
	protected List<GeneratorTemplate<?>> templates = Collections.synchronizedList(new LinkedList<>());

	public GipsCodeGenerator(final GipsApiData apiData) {
		this.data = apiData;
	}

	public void generate() {
		templates.add(new GipsAbstractAPITemplate(data, data.gipsModel));
		data.gipsApiClassNames.keySet().forEach(engine -> {
			templates.add(new GipsAPITemplate(data, engine));
		});
		templates.add(new MapperFactoryTemplate(data, data.gipsModel));
		templates.add(new ConstraintFactoryTemplate(data, data.gipsModel));
		templates.add(new ObjectiveFactoryTemplate(data, data.gipsModel));
		data.gipsModel.getVariables().parallelStream().filter(mapping -> mapping instanceof Mapping)
				.map(mapping -> (Mapping) mapping).forEach(mapping -> {
					if (mapping instanceof GTMapping gtMapping) {
						templates.add(new GTMappingTemplate(data, gtMapping));
						templates.add(new GTMapperTemplate(data, gtMapping));
					} else {
						PatternMapping pmMapping = (PatternMapping) mapping;
						templates.add(new PatternMappingTemplate(data, pmMapping));
						templates.add(new PatternMapperTemplate(data, pmMapping));
					}

				});
		data.gipsModel.getConstraints().parallelStream().forEach(constraint -> {
			if (constraint instanceof MappingConstraint mappingConstraint) {
				templates.add(new MappingConstraintTemplate(data, mappingConstraint));
			} else if (constraint instanceof PatternConstraint patternConstraint) {
				templates.add(new PatternConstraintTemplate(data, patternConstraint));
			} else if (constraint instanceof TypeConstraint typeConstraint) {
				templates.add(new TypeConstraintTemplate(data, typeConstraint));
			} else {
				GlobalConstraint globalConstraint = (GlobalConstraint) constraint;
				templates.add(new GlobalConstraintTemplate(data, globalConstraint));
			}
		});
		data.gipsModel.getObjectives().parallelStream().forEach(objective -> {
			if (objective instanceof MappingObjective mappingObjective) {
				templates.add(new MappingObjectiveTemplate(data, mappingObjective));
			} else if (objective instanceof PatternObjective patternObjective) {
				templates.add(new PatternObjectiveTemplate(data, patternObjective));
			} else {
				TypeObjective typeObjective = (TypeObjective) objective;
				templates.add(new TypeObjectiveTemplate(data, typeObjective));
			}
		});
		if (data.gipsModel.getGlobalObjective() != null) {
			templates.add(new GlobalObjectiveTemplate(data, data.gipsModel.getGlobalObjective()));
		}
		templates.parallelStream().forEach(template -> {
			try {
				template.init();
				template.generate();
				template.writeToFile();
			} catch (Exception e) {
				LogUtils.error(logger, e);
			}
		});
		if (data.gipsModel.getConfig().isBuildLaunchConfig()) {
			GeneratorTemplate<?> launchTemplate = new LaunchFileTemplate(data, data.gipsModel);
			try {
				launchTemplate.init();
				launchTemplate.generate();
				launchTemplate.writeToFile();
			} catch (Exception e) {
				LogUtils.error(logger, e);
			}

		}
	}

	public void saveModel() {
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		rs.getPackageRegistry().put(IBeXGTModelPackage.eINSTANCE.getNsURI(), IBeXGTModelPackage.eINSTANCE);
		rs.getPackageRegistry().put(GipsIntermediatePackage.eINSTANCE.getNsURI(), GipsIntermediatePackage.eINSTANCE);
		URI uri = URI.createFileURI(data.model.getMetaData().getProjectPath() + "/" + data.gipsModelPath);
		Resource r = rs.createResource(uri);
		r.getContents().add(data.model);
		try {
			r.save(null);
		} catch (IOException e) {
			LogUtils.error(logger, e);
		}
	}
}
