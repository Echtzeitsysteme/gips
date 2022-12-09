package org.emoflon.gips.gipsl.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.xtext.validation.Check;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingContext;
import org.emoflon.gips.gipsl.gipsl.GipsMappingVariable;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.scoping.GipslScopeContextUtil;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;

public class GipslMappingValidator {

	private GipslMappingValidator() {
	}

	/**
	 * Runs checks for all Gips mappings.
	 * 
	 * @param mapping Input Gips mapping to check.
	 */
	@Check
	public static void checkMapping(final GipsMapping mapping) {
		if (GipslValidator.DISABLE_VALIDATOR) {
			return;
		}

		if (mapping == null) {
			return;
		}

		checkMappingNameValid(mapping);
		checkMappingNameUnique(mapping);
		checkRuleNotAbstract(mapping);
		checkAtMostOneMappingPerRule(mapping);
		checkMappingUnused(mapping);
	}

	@Check
	public static void checkMappingVariable(final GipsMappingVariable mappingVariable) {
		if (GipslValidator.DISABLE_VALIDATOR) {
			return;
		}

		if (mappingVariable == null) {
			return;
		}
		checkMappingVariableNameUnique(mappingVariable);
	}

	/**
	 * Checks that the given mapping does not use an abstract rule/pattern.
	 * 
	 * @param mapping Mapping to check abstract rule violation for.
	 */
	public static void checkRuleNotAbstract(final GipsMapping mapping) {
		if (mapping == null || mapping.getPattern() == null) {
			return;
		}

		if (mapping.getPattern().isAbstract()) {
			GipslValidator.err(String.format(GipslValidatorUtils.RULE_IS_ABSTRACT, mapping.getName()),
					GipslPackage.Literals.GIPS_MAPPING__PATTERN);
		}
	}

	/**
	 * Checks that each rule has at most one mapping. (This method must be called
	 * from within the global mapping check to allow the annotation of the found
	 * mappings with errors.)
	 * 
	 * @param mapping Mapping to start checking for.
	 */
	public static void checkAtMostOneMappingPerRule(final GipsMapping mapping) {
		if (mapping == null || mapping.getName() == null) {
			return;
		}

		final EditorGTFile container = (EditorGTFile) mapping.eContainer();
		final Set<EditorPattern> foundPatterns = new HashSet<>();

		container.getMappings().forEach(m -> {
			final boolean alreadyUsed = !foundPatterns.add(m.getPattern());
			if (alreadyUsed) {
				GipslValidator.err( //
						String.format(GipslValidatorUtils.RULE_HAS_MULTIPLE_MAPPINGS, m.getPattern().getName()), //
						GipslPackage.Literals.GIPS_MAPPING__PATTERN //
				);
			}
		});
	}

	/**
	 * Checks for validity of a mapping name. The name must not be on the list of
	 * invalid names, the name should be in lowerCamelCase, and the name should
	 * start with a lower case character.
	 * 
	 * @param mapping Gips mapping to check.
	 */
	public static void checkMappingNameValid(final GipsMapping mapping) {
		if (mapping == null || mapping.getName() == null) {
			return;
		}

		if (GipslValidatorUtils.INVALID_NAMES.contains(mapping.getName())) {
			GipslValidator.err( //
					String.format(GipslValidatorUtils.MAPPING_NAME_FORBIDDEN_MESSAGE, mapping.getName()), //
					GipslPackage.Literals.GIPS_MAPPING__NAME, //
					GipslValidator.NAME_EXPECT_UNIQUE //
			);
		} else {
			// The mapping name should be lowerCamelCase.
			if (mapping.getName().contains("_")) {
				GipslValidator.warn( //
						String.format(GipslValidatorUtils.MAPPING_NAME_CONTAINS_UNDERSCORES_MESSAGE, mapping.getName()), //
						GipslPackage.Literals.GIPS_MAPPING__NAME, //
						GipslValidatorUtils.NAME_BLOCKED);
			} else {
				// The mapping name should start with a lower case character.
				if (!Character.isLowerCase(mapping.getName().charAt(0))) {
					GipslValidator.warn( //
							String.format(GipslValidatorUtils.MAPPING_NAME_STARTS_WITH_LOWER_CASE_MESSAGE,
									mapping.getName()), //
							GipslPackage.Literals.GIPS_MAPPING__NAME, GipslValidator.NAME_EXPECT_LOWER_CASE //
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
	public static void checkMappingNameUnique(final GipsMapping mapping) {
		if (mapping == null || mapping.getName() == null) {
			return;
		}

		final EditorGTFile container = (EditorGTFile) mapping.eContainer();
		final long count = container.getMappings().stream()
				.filter(m -> m.getName() != null && m.getName().equals(mapping.getName())).count();
		if (count != 1) {
			GipslValidator.err( //
					String.format(GipslValidatorUtils.MAPPING_NAME_MULTIPLE_DECLARATIONS_MESSAGE, mapping.getName(),
							GipslValidator.getTimes((int) count)), //
					GipslPackage.Literals.GIPS_MAPPING__NAME, //
					GipslValidator.NAME_EXPECT_UNIQUE //
			);
		}
	}

	/**
	 * Checks if a mapping is either unconstrained or not used in an objective and
	 * throws a warning accordingly.
	 * 
	 * @param mapping Gips mapping to be checked.
	 */
	public static void checkMappingUnused(final GipsMapping mapping) {
		final EditorGTFile container = (EditorGTFile) mapping.eContainer();
		boolean usedAsContext = container.getConstraints().stream().filter(c -> c.getContext() != null)
				.filter(c -> (c.getContext() instanceof GipsMappingContext))
				.map(c -> (GipsMappingContext) c.getContext()).filter(mc -> mc.getMapping().equals(mapping)).findAny()
				.isPresent();
		if (usedAsContext)
			return;

		final List<GipsConstraint> otherConstraints = container.getConstraints().stream()
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

		for (final GipsConstraint constraint : otherConstraints) {
			Set<GipsMapping> mappings = GipslScopeContextUtil.extractMappings(constraint.getExpr().getExpr());
			if (mappings.contains(mapping))
				return;
		}

		GipslValidator.warn( //
				String.format(GipslValidatorUtils.MAPPING_W_O_CONSTRAINTS_MESSAGE, mapping.getName()), //
				GipslPackage.Literals.GIPS_MAPPING__NAME);

		usedAsContext = container.getObjectives().stream().filter(c -> c.getContext() != null)
				.filter(c -> (c.getContext() instanceof GipsMappingContext))
				.map(c -> (GipsMappingContext) c.getContext()).filter(mc -> mc.getMapping().equals(mapping)).findAny()
				.isPresent();
		if (usedAsContext)
			return;

		final List<GipsObjective> otherObjectives = container.getObjectives().stream()
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

		for (final GipsObjective objective : otherObjectives) {
			final Set<GipsMapping> mappings = GipslScopeContextUtil.extractMappings(objective.getExpr());
			if (mappings.contains(mapping))
				return;
		}

		GipslValidator.warn( //
				String.format(GipslValidatorUtils.MAPPING_W_O_CONSTRAINTS_AND_OBJECTIVE_MESSAGE, mapping.getName()), //
				GipslPackage.Literals.GIPS_MAPPING__NAME);
	}

	public static void checkMappingVariableNameUnique(final GipsMappingVariable mappingVariable) {
		if (mappingVariable.getName() == null)
			return;

		final GipsMapping mapping = (GipsMapping) mappingVariable.eContainer();
		if (mapping == null || mapping.getVariables() == null || mapping.getVariables().isEmpty())
			return;

		Optional<GipsMappingVariable> other = mapping.getVariables().stream()
				.filter(var -> !var.equals(mappingVariable)).filter(var -> var.getName() != null)
				.filter(var -> var.getName().equals(mappingVariable.getName())).findAny();

		if (other.isPresent()) {
			GipslValidator.err( //
					String.format(GipslValidatorUtils.MAPPING_VARIABLE_NAME_MULTIPLE_DECLARATIONS_MESSAGE,
							mappingVariable.getName()), //
					GipslPackage.Literals.GIPS_MAPPING_VARIABLE__NAME, //
					GipslValidator.NAME_EXPECT_UNIQUE //
			);
		}
	}
}
