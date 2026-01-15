package org.emoflon.gips.gipsl.validation;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.gips.gipsl.gipsl.GipsJoinAllOperation;
import org.emoflon.gips.gipsl.gipsl.GipsJoinBySelectionOperation;
import org.emoflon.gips.gipsl.gipsl.GipsJoinPairSelection;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingExpression;
import org.emoflon.gips.gipsl.gipsl.GipsPatternExpression;
import org.emoflon.gips.gipsl.gipsl.GipsRuleExpression;
import org.emoflon.gips.gipsl.gipsl.GipsTypeExpression;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.scoping.GipslScopeContextUtil;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;

public class GipslJoinValidator {
	private GipslJoinValidator() {
	}

	public static void checkJoinOperation(final GipsJoinBySelectionOperation operation) {
		if (operation == null)
			return;

		if (operation.getPairJoin().isEmpty() && operation.getSingleJoin() == null) {
			GipslValidator.err( //
					GipslValidatorUtil.SET_JOIN_EMPTY, //
					operation, //
					GipslPackage.Literals.GIPS_JOIN_BY_SELECTION_OPERATION__PAIR_JOIN //
			);
			return;
		}

		if (operation.getSingleJoin() != null) {
			checkJoinSingleSelection(operation);
		} else {
			checkJoinPairSelection(operation);
		}
	}

	private static void checkJoinSingleSelection(GipsJoinBySelectionOperation operation) {

		EObject setContext = GipslScopeContextUtil.getSetContext(operation);
		EditorPattern setEditorPattern = GipslScopeContextUtil.getPatternOrRuleOf(setContext);
		Collection<EditorNode> setNodes = GipslScopeContextUtil.getNonCreatedEditorNodes(setEditorPattern);

		EObject localContext = GipslScopeContextUtil.getLocalContext(operation);
		EditorPattern localEditorPattern = GipslScopeContextUtil.getPatternOrRuleOf(localContext);
		Collection<EditorNode> localNodes = GipslScopeContextUtil.getNonCreatedEditorNodes(localEditorPattern);

		EditorNode node = operation.getSingleJoin().getNode();
		EClass comparedNodeClass = null;

		if (setContext instanceof GipsTypeExpression eType) {
			comparedNodeClass = eType.getType();
			if (!localNodes.contains(node)) {
				GipslValidator.err( //
						String.format(GipslValidatorUtil.SET_JOIN_RIGHT_NODE_REF_ERROR), //
						operation, //
						GipslPackage.Literals.GIPS_JOIN_BY_SELECTION_OPERATION__SINGLE_JOIN //
				);
			}
		} else if (localContext instanceof EClass eClass) {
			comparedNodeClass = eClass;
			if (!setNodes.contains(node)) {
				GipslValidator.err( //
						String.format(GipslValidatorUtil.SET_JOIN_LEFT_NODE_REF_ERROR), //
						operation, //
						GipslPackage.Literals.GIPS_JOIN_BY_SELECTION_OPERATION__SINGLE_JOIN //
				);
			}
		} else {
			GipslValidator.err( //
					String.format(
							"Single node comparison is only applicable for context or set type EClass. Use \"(set node,context node)\" to specify nodes."), //
					operation, //
					GipslPackage.Literals.GIPS_JOIN_BY_SELECTION_OPERATION__SINGLE_JOIN //
			);

			return;
		}

		if (node != null) {
			// check if nodes are compatible
			EClass nodeClass = node.getType();
			if (nodeClass == null || comparedNodeClass == null) {
				// Unable to test compatibility
			} else {
				if (!(nodeClass.isSuperTypeOf(comparedNodeClass) || comparedNodeClass.isSuperTypeOf(nodeClass))) {
					GipslValidator.warn( //
							String.format(GipslValidatorUtil.SET_JOIN_MISSMATCHING_TYPE_ERROR, //
									nodeClass.getName(), //
									comparedNodeClass.getName()), //
							operation, //
							GipslPackage.Literals.GIPS_JOIN_BY_SELECTION_OPERATION__SINGLE_JOIN //
					);
				}
			}
		}
	}

	private static void checkJoinPairSelection(GipsJoinBySelectionOperation operation) {
		for (GipsJoinPairSelection selection : operation.getPairJoin()) {
			EObject leftNode = selection.getLeftNode();
			EObject rightNode = selection.getRightNode();

			if (leftNode != null && rightNode != null) {
				// check if nodes are compatible
				EClass leftNodeClass = getReferencedType(leftNode);
				EClass rightNodeClass = getReferencedType(rightNode);
				if (leftNodeClass == null || rightNodeClass == null) {
					// Unable to test compatibility
				} else {
					if (!(leftNodeClass.isSuperTypeOf(rightNodeClass) || rightNodeClass.isSuperTypeOf(leftNodeClass))) {
						GipslValidator.warn( //
								String.format(GipslValidatorUtil.SET_JOIN_MISSMATCHING_TYPE_ERROR, //
										leftNodeClass.getName(), //
										rightNodeClass.getName()), //
								selection, //
								null //
						);
					}
				}
			}
		}
	}

	private static EClass getReferencedType(EObject object) {
		if (object.eIsProxy())
			return null;

		return switch (object) {
		case EditorNode node -> node.getType();
		case EClass eClass -> eClass;
		case EReference reference -> (EClass) reference.getEType();
		default -> null;
		};
	}

	public static void checkJoinOperation(final GipsJoinAllOperation operation) {
		if (operation == null)
			return;

		EObject localContext = GipslScopeContextUtil.getLocalContext(operation);
		EObject setContext = GipslScopeContextUtil.getSetContext(operation);

		if (setContext instanceof GipsMappingExpression mappingExpression
				&& localContext instanceof GipsMapping mapping) {
			if (mappingExpression.getMapping().equals(mapping)) {
				return; // okay!
			} else {
				GipslValidator.warn( //
						String.format(GipslValidatorUtil.SET_JOIN_ALL_INVALID_TYPES), //
						operation, //
						null //
				);
				return;
			}
		} else if (setContext instanceof GipsRuleExpression ruleExpression
				&& localContext instanceof EditorPattern pattern) {
			if (ruleExpression.getRule().equals(pattern)) {
				return; // okay!
			} else {
				GipslValidator.warn( //
						String.format(GipslValidatorUtil.SET_JOIN_ALL_INVALID_TYPES), //
						operation, //
						null //
				);
				return;
			}
		} else if (setContext instanceof GipsPatternExpression patternExpression
				&& localContext instanceof EditorPattern pattern) {
			if (patternExpression.getPattern().equals(pattern)) {
				return; // okay!
			} else {
				GipslValidator.warn( //
						String.format(GipslValidatorUtil.SET_JOIN_ALL_INVALID_TYPES), //
						operation, //
						null //
				);
				return;
			}
		} else if (setContext instanceof GipsTypeExpression typeExpression && localContext instanceof EClass type) {
			if (typeExpression.getType().equals(type)) {
				return; // okay!
			} else {
				GipslValidator.warn( //
						String.format(GipslValidatorUtil.SET_JOIN_ALL_INVALID_TYPES), //
						operation, //
						null //
				);
				return;
			}
		}

		EObject localPattern = GipslScopeContextUtil.getPatternOrRuleOf(localContext);
		EObject setPattern = GipslScopeContextUtil.getPatternOrRuleOf(setContext);

		if (localPattern == null) {
			GipslValidator.err( //
					String.format(GipslValidatorUtil.SET_JOIN_ALL_MISSING_PATTERN, "Context"), //
					operation, //
					null //
			);
		} else if (setPattern == null) {
			GipslValidator.err( //
					String.format(GipslValidatorUtil.SET_JOIN_ALL_MISSING_PATTERN, "Set"), //
					operation, //
					null //
			);
		} else if (!localPattern.equals(setPattern)) {
			GipslValidator.err( //
					String.format(GipslValidatorUtil.SET_JOIN_ALL_MISMATCH_PATTERN), //
					operation, //
					null //
			);
		}
	}

}
