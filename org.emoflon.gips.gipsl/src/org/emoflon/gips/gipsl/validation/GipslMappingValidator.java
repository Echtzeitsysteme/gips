package org.emoflon.gips.gipsl.validation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.xtext.validation.Check;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingContext;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.scoping.GipslScopeContextUtil;

public class GipslMappingValidator extends AbstractGipslValidator {

	/**
	 * Runs checks for all Gips mappings.
	 * 
	 * @param mapping Input Gips mapping to check.
	 */
	@Check
	public void checkMapping(final GipsMapping mapping) {
		if (GipslValidator.DISABLE_VALIDATOR) {
			return;
		}

		if (mapping == null) {
			return;
		}

		checkMappingNameValid(mapping);
		checkMappingNameUnique(mapping);
		checkMappingUnused(mapping);
	}

	/**
	 * Checks for validity of a mapping name. The name must not be on the list of
	 * invalid names, the name should be in lowerCamelCase, and the name should
	 * start with a lower case character.
	 * 
	 * @param mapping Gips mapping to check.
	 */
	public void checkMappingNameValid(final GipsMapping mapping) {
		if (mapping == null || mapping.getName() == null) {
			return;
		}

		if (GipslValidatorUtils.INVALID_NAMES.contains(mapping.getName())) {
			error( //
					String.format(GipslValidatorUtils.MAPPING_NAME_FORBIDDEN_MESSAGE, mapping.getName()), //
					GipslPackage.Literals.GIPS_MAPPING__NAME, //
					NAME_EXPECT_UNIQUE //
			);
		} else {
			// The mapping name should be lowerCamelCase.
			if (mapping.getName().contains("_")) {
				warning( //
						String.format(GipslValidatorUtils.MAPPING_NAME_CONTAINS_UNDERSCORES_MESSAGE, mapping.getName()), //
						GipslPackage.Literals.GIPS_MAPPING__NAME, //
						GipslValidatorUtils.NAME_BLOCKED);
			} else {
				// The mapping name should start with a lower case character.
				if (!Character.isLowerCase(mapping.getName().charAt(0))) {
					warning( //
							String.format(GipslValidatorUtils.MAPPING_NAME_STARTS_WITH_LOWER_CASE_MESSAGE,
									mapping.getName()), //
							GipslPackage.Literals.GIPS_MAPPING__NAME, NAME_EXPECT_LOWER_CASE //
					);
				}
			}
		}
	}

	/**
	 * Checks the uniqueness of the name of a given Gips mapping.
	 * 
	 * @param mapping Gips mapping to check uniqueness of the name for.
	 */
	public void checkMappingNameUnique(final GipsMapping mapping) {
		if (mapping == null || mapping.getName() == null) {
			return;
		}

		final EditorGTFile container = (EditorGTFile) mapping.eContainer();
		final long count = container.getMappings().stream()
				.filter(m -> m.getName() != null && m.getName().equals(mapping.getName())).count();
		if (count != 1) {
			error( //
					String.format(GipslValidatorUtils.MAPPING_NAME_MULTIPLE_DECLARATIONS_MESSAGE, mapping.getName(),
							getTimes((int) count)), //
					GipslPackage.Literals.GIPS_MAPPING__NAME, //
					NAME_EXPECT_UNIQUE //
			);
		}
	}

	/**
	 * Checks if a mapping is either unconstrained or not used in an objective and
	 * throws a warning accordingly.
	 * 
	 * @param mapping Gips mapping to be checked.
	 */
	public void checkMappingUnused(final GipsMapping mapping) {
		final EditorGTFile container = (EditorGTFile) mapping.eContainer();
		boolean usedAsContext = container.getConstraints().stream().filter(c -> c.getContext() != null)
				.filter(c -> (c.getContext() instanceof GipsMappingContext))
				.map(c -> (GipsMappingContext) c.getContext()).filter(mc -> mc.getMapping().equals(mapping)).findAny()
				.isPresent();
		if (usedAsContext)
			return;

		List<GipsConstraint> otherConstraints = container.getConstraints().stream()
				.filter(c -> c.getContext() != null && c.getExpr() != null && c.getExpr().getExpr() != null)
				.filter(c -> {
					if (c.getContext() instanceof GipsMappingContext mapContext
							&& !mapContext.getMapping().equals(mapping)) {
						return true;
					} else if (!(c.getContext() instanceof GipsMappingContext)) {
						return true;
					} else {
						return false;
					}
				}).collect(Collectors.toList());

		for (GipsConstraint constraint : otherConstraints) {
			Set<GipsMapping> mappings = GipslScopeContextUtil.extractMappings(constraint.getExpr().getExpr());
			if (mappings.contains(mapping))
				return;
		}

		warning( //
				String.format(GipslValidatorUtils.MAPPING_W_O_CONSTRAINTS_MESSAGE, mapping.getName()), //
				GipslPackage.Literals.GIPS_MAPPING__NAME);

		usedAsContext = container.getObjectives().stream().filter(c -> c.getContext() != null)
				.filter(c -> (c.getContext() instanceof GipsMappingContext))
				.map(c -> (GipsMappingContext) c.getContext()).filter(mc -> mc.getMapping().equals(mapping)).findAny()
				.isPresent();
		if (usedAsContext)
			return;

		List<GipsObjective> otherObjectives = container.getObjectives().stream()
				.filter(c -> c.getContext() != null && c.getExpr() != null).filter(c -> {
					if (c.getContext() instanceof GipsMappingContext mapContext
							&& !mapContext.getMapping().equals(mapping)) {
						return true;
					} else if (!(c.getContext() instanceof GipsMappingContext)) {
						return true;
					} else {
						return false;
					}
				}).collect(Collectors.toList());

		for (GipsObjective objective : otherObjectives) {
			Set<GipsMapping> mappings = GipslScopeContextUtil.extractMappings(objective.getExpr());
			if (mappings.contains(mapping))
				return;
		}

		warning( //
				String.format(GipslValidatorUtils.MAPPING_W_O_CONSTRAINTS_AND_OBJECTIVE_MESSAGE, mapping.getName()), //
				GipslPackage.Literals.GIPS_MAPPING__NAME);
	}

}
