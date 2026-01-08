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
					String.format("Type '%s' already declared '%s'.", ((EClass) typeExtension.getRef()).getName(),
							GipslValidator.getTimes((int) count)), //
					GipslPackage.Literals.GIPS_TYPE_EXTENSION__REF, //
					GipslValidator.NAME_EXPECT_UNIQUE //
			);
		}
	}

	public static void checkTypeExtensionVariableHasUniqueName(final GipsTypeExtensionVariable variable) {
		if (variable.getName() == null)
			return;

		final GipsTypeExtension typeExtension = (GipsTypeExtension) variable.eContainer();
		if (typeExtension == null || typeExtension.getVariables() == null || typeExtension.getVariables().isEmpty())
			return;

		Optional<GipsTypeExtensionVariable> otherExtensionVariable = typeExtension.getVariables().stream() //
				.filter(var -> !var.equals(variable)) //
				.filter(var -> var.getName() != null) //
				.filter(var -> var.getName().equals(variable.getName())) //
				.findAny();

		if (otherExtensionVariable.isPresent()) { // TODO
			GipslValidator.err( //
					String.format("Variable name '%s' must not be declared more than once.", variable.getName()), //
					GipslPackage.Literals.GIPS_VARIABLE__NAME, //
					GipslValidator.NAME_EXPECT_UNIQUE //
			);
		}

		if (typeExtension.getRef() == null)
			return;

		Optional<EAttribute> otherOriginalVariable = ((EClass) typeExtension.getRef()).getEAllAttributes().stream() //
				.filter(var -> var.getName() != null) //
				.filter(var -> var.getName().equalsIgnoreCase(variable.getName())) //
				.findAny();

		if (otherOriginalVariable.isPresent()) { // TODO
			GipslValidator.err( //
					String.format("Variable name '%s' already declared on type.", variable.getName()), //
					GipslPackage.Literals.GIPS_VARIABLE__NAME, //
					GipslValidator.NAME_EXPECT_UNIQUE //
			);
		}
	}

}
