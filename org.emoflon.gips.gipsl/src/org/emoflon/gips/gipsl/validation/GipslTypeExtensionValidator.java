package org.emoflon.gips.gipsl.validation;

import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsTypeExtension;
import org.emoflon.gips.gipsl.gipsl.GipsTypeExtensionVariable;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.gipsl.impl.EditorGTFileImpl;
import org.emoflon.ibex.gt.editor.utils.GTEditorPatternUtils;

public final class GipslTypeExtensionValidator {
	private GipslTypeExtensionValidator() {
	}

	public static void checkTypeExtension(GipsTypeExtension typeExtension) {
		checkTypeExtensionIsUniqueForType(typeExtension);
	}

	public static void checkTypeExtensionIsUniqueForType(final GipsTypeExtension typeExtension) {
		if (typeExtension == null || typeExtension.getRef() == null)
			return;

		EditorGTFile editorFile = GTEditorPatternUtils.getContainer(typeExtension, EditorGTFileImpl.class);
		long count = editorFile.getTypes().stream() //
				.filter(e -> e.getRef() != null) //
				.filter(e -> e.getRef().equals(typeExtension.getRef())) //
				.count();

		if (count != 1) {
			GipslValidator.err( //
					String.format(GipslValidatorUtil.TYPE_EXTENSION_ALREADY_DECLARED,
							((EClass) typeExtension.getRef()).getName(), GipslValidator.getTimes((int) count)), //
					GipslPackage.Literals.GIPS_TYPE_EXTENSION__REF //
			);
		}
	}

	public static void checkTypeExtensionVariable(GipsTypeExtensionVariable variable) {
		checkTypeExtensionVariableHasUniqueName(variable);
		checkTypeExtensionVariableValidAttribute(variable);
		checkTypeExtensionVariableSingleBind(variable);
	}

	public static void checkTypeExtensionVariableHasUniqueName(final GipsTypeExtensionVariable variable) {
		if (variable.getName() == null)
			return;

		final GipsTypeExtension typeExtension = (GipsTypeExtension) variable.eContainer();
		if (typeExtension == null || typeExtension.getVariables() == null || typeExtension.getVariables().isEmpty())
			return;

		Optional<GipsTypeExtensionVariable> otherExtensionVariable = typeExtension.getVariables().stream() //
				.filter(var -> !var.equals(variable)) //
				.filter(var -> variable.getName().equals(var.getName())) //
				.findAny();

		if (otherExtensionVariable.isPresent()) {
			GipslValidator.err( //
					String.format(GipslValidatorUtil.TYPE_EXTENSION_VARIABLE_ALREADY_DECLARED, variable.getName()), //
					GipslPackage.Literals.GIPS_VARIABLE__NAME, //
					GipslValidator.NAME_EXPECT_UNIQUE //
			);
		}
	}

	public static void checkTypeExtensionVariableValidAttribute(final GipsTypeExtensionVariable variable) {
		if (!variable.isBound() || variable.getAttribute() == null)
			return;

		final GipsTypeExtension typeExtension = (GipsTypeExtension) variable.eContainer();
		if (typeExtension == null || typeExtension.getRef() == null)
			return;

		if (!(variable.getAttribute() instanceof EAttribute))
			GipslValidator.err(GipslValidatorUtil.TYPE_EXTENSION_VARIABLE_ATTRIBUTE_INVALID, //
					GipslPackage.Literals.GIPS_TYPE_EXTENSION_VARIABLE__ATTRIBUTE);

		EAttribute attribute = (EAttribute) variable.getAttribute();
		if (attribute.getEAttributeType() == null || !attribute.getEAttributeType().equals(variable.getType()))
			GipslValidator.err(GipslValidatorUtil.TYPE_EXTENSION_VARIABLE_ATTRIBUTE_TYPE_MISSMATCH, //
					GipslPackage.Literals.GIPS_TYPE_EXTENSION_VARIABLE__ATTRIBUTE);
	}

	public static void checkTypeExtensionVariableSingleBind(final GipsTypeExtensionVariable variable) {
		if (!variable.isBound() || variable.getAttribute() == null)
			return;

		final GipsTypeExtension typeExtension = (GipsTypeExtension) variable.eContainer();
		if (typeExtension == null || typeExtension.getVariables() == null || typeExtension.getVariables().isEmpty())
			return;

		Optional<GipsTypeExtensionVariable> otherVariable = typeExtension.getVariables().stream() //
				.filter(var -> !var.equals(variable)) //
				.filter(var -> var.getName() != null) //
				.filter(var -> var.isBound()) //
				.filter(var -> var.getAttribute() != null) //
				.filter(var -> var.getAttribute().equals(variable.getAttribute())) //
				.findAny();

		if (otherVariable.isPresent()) {
			GipslValidator.err( //
					String.format(GipslValidatorUtil.TYPE_EXTENSION_VARIABLE_ATTRIBUTE_ALREADY_BOUND,
							otherVariable.get().getName()), //
					GipslPackage.Literals.GIPS_TYPE_EXTENSION_VARIABLE__ATTRIBUTE);
		}
	}

}
