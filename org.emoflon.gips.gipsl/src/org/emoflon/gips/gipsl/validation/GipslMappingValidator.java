package org.emoflon.gips.gipsl.validation;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingVariable;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.gipsl.impl.EditorGTFileImpl;
import org.emoflon.gips.gipsl.scoping.GipslScopeContextUtil;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.utils.GTEditorPatternUtils;

public class GipslMappingValidator {

	private GipslMappingValidator() {
	}

	/**
	 * Runs checks for all Gips mappings.
	 * 
	 * @param mapping Input Gips mapping to check.
	 */
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

		if (GipslValidatorUtil.INVALID_NAMES.contains(mapping.getName())) {
			GipslValidator.err( //
					String.format(GipslValidatorUtil.MAPPING_NAME_FORBIDDEN_MESSAGE, mapping.getName()), //
					GipslPackage.Literals.GIPS_MAPPING__NAME, //
					GipslValidator.NAME_EXPECT_UNIQUE //
			);
		} else {
			// The mapping name should be lowerCamelCase.
			if (mapping.getName().contains("_")) {
				GipslValidator.warn( //
						String.format(GipslValidatorUtil.MAPPING_NAME_CONTAINS_UNDERSCORES_MESSAGE, mapping.getName()), //
						GipslPackage.Literals.GIPS_MAPPING__NAME, //
						GipslValidatorUtil.NAME_BLOCKED);
			} else {
				// The mapping name should start with a lower case character.
				if (!Character.isLowerCase(mapping.getName().charAt(0))) {
					GipslValidator.warn( //
							String.format(GipslValidatorUtil.MAPPING_NAME_STARTS_WITH_LOWER_CASE_MESSAGE,
									mapping.getName()), //
							GipslPackage.Literals.GIPS_MAPPING__NAME, GipslValidator.NAME_EXPECT_LOWER_CASE //
					);
				}
			}
		}
	}

	/**
	 * Checks the uniqueness of the name of a given Gips mapping. -> Rule, Pattern,
	 * Mapping and Type names must be unique.
	 *
	 * @param mapping Gips mapping to check uniqueness of the name for.
	 */
	public static void checkMappingNameUnique(final GipsMapping mapping) {
		if (mapping == null || mapping.getName() == null) {
			return;
		}

		long count = GipslScopeContextUtil.getAllEditorPatterns(mapping).stream()
				.filter(p -> p != null && p.getName() != null).filter(p -> p.getName().equals(mapping.getName()))
				.count();

		count += GipslScopeContextUtil.getClasses(mapping).stream()
				.filter(cls -> cls.getName().equals(mapping.getName())).count();

		EditorGTFile editorFile = GTEditorPatternUtils.getContainer(mapping, EditorGTFileImpl.class);
		count += editorFile.getMappings().stream().filter(m -> m != null && m.getName() != null)
				.filter(m -> m.getName().equals(mapping.getName())).count();

		if (count != 1) {
			GipslValidator.err( //
					String.format(GipslValidatorUtil.MAPPING_NAME_MULTIPLE_DECLARATIONS_MESSAGE, mapping.getName(),
							GipslValidator.getTimes((int) count)), //
					GipslPackage.Literals.GIPS_MAPPING__NAME, //
					GipslValidator.NAME_EXPECT_UNIQUE //
			);
		}
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
			GipslValidator.err(String.format(GipslValidatorUtil.RULE_IS_ABSTRACT, mapping.getName()),
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
						String.format(GipslValidatorUtil.RULE_HAS_MULTIPLE_MAPPINGS, m.getPattern().getName()), //
						GipslPackage.Literals.GIPS_MAPPING__PATTERN //
				);
			}
		});
	}

	/**
	 * Checks if a mapping is either unconstrained or not used in an objective and
	 * throws a warning accordingly.
	 * 
	 * TODO: Ensure that a mapping is actually used in the expression inside a
	 * constraint. Having a mapping as context does not ensure that mapping
	 * variables are subject to constraints.
	 * 
	 * @param mapping Gips mapping to be checked.
	 */
	public static void checkMappingUnused(final GipsMapping mapping) {
		final EditorGTFile container = GTEditorPatternUtils.getContainer(mapping, EditorGTFileImpl.class);
		Set<GipsMapping> mappings = container.getConstraints().stream()
				.flatMap(c -> GipslValidatorUtil.extractMappings(c.getExpression()).stream())
				.collect(Collectors.toSet());

		boolean usedAsContext = container.getConstraints().stream().filter(c -> c.getContext() != null)
				.filter(c -> (c.getContext() instanceof GipsMapping)) //
				.map(c -> (GipsMapping) c.getContext()) //
				.filter(m -> m.equals(mapping)).findAny().isPresent(); //

		if (!mappings.contains(mapping)) {
			GipslValidator.warn( //
					String.format(GipslValidatorUtil.MAPPING_W_O_CONSTRAINTS_MESSAGE, mapping.getName()), //
					GipslPackage.Literals.GIPS_MAPPING__NAME);
		}

		mappings = container.getFunctions().stream()
				.flatMap(f -> GipslValidatorUtil.extractMappings(f.getExpression()).stream())
				.collect(Collectors.toSet());

		if (!mappings.contains(mapping)) {
			GipslValidator.warn( //
					String.format(GipslValidatorUtil.MAPPING_W_O_CONSTRAINTS_AND_OBJECTIVE_MESSAGE, mapping.getName()), //
					GipslPackage.Literals.GIPS_MAPPING__NAME);
		}
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
					String.format(GipslValidatorUtil.MAPPING_VARIABLE_NAME_MULTIPLE_DECLARATIONS_MESSAGE,
							mappingVariable.getName()), //
					GipslPackage.Literals.GIPS_MAPPING_VARIABLE__NAME, //
					GipslValidator.NAME_EXPECT_UNIQUE //
			);
		}
	}
}
