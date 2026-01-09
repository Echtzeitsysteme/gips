package org.emoflon.gips.gipsl.scoping;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsConstantReference;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsJoinBySelectionOperation;
import org.emoflon.gips.gipsl.gipsl.GipsJoinPairSelection;
import org.emoflon.gips.gipsl.gipsl.GipsJoinSingleSelection;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunctionReference;
import org.emoflon.gips.gipsl.gipsl.GipsLocalContextExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMappingVariable;
import org.emoflon.gips.gipsl.gipsl.GipsNodeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsPatternExpression;
import org.emoflon.gips.gipsl.gipsl.GipsRuleExpression;
import org.emoflon.gips.gipsl.gipsl.GipsSetElementExpression;
import org.emoflon.gips.gipsl.gipsl.GipsTransformOperation;
import org.emoflon.gips.gipsl.gipsl.GipsTypeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsTypeExtension;
import org.emoflon.gips.gipsl.gipsl.GipsTypeExtensionVariable;
import org.emoflon.gips.gipsl.gipsl.GipsTypeQuery;
import org.emoflon.gips.gipsl.gipsl.GipsTypeSelect;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.GipsVariable;
import org.emoflon.gips.gipsl.gipsl.GipsVariableReferenceExpression;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.gipsl.ImportedPattern;
import org.emoflon.gips.gipsl.gipsl.impl.EditorGTFileImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsConstantReferenceImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsConstraintImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsLinearFunctionImpl;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.utils.GTEditorModelUtils;
import org.emoflon.ibex.gt.editor.utils.GTEditorPatternUtils;

public final class GipslScopeContextUtil {

	public static boolean isPatternImportUri(final EObject context, final EReference reference) {
		return context instanceof ImportedPattern && reference == GipslPackage.Literals.IMPORTED_PATTERN__FILE;
	}

	public static boolean isPatternImportPattern(final EObject context, final EReference reference) {
		return context instanceof ImportedPattern && reference == GipslPackage.Literals.IMPORTED_PATTERN__PATTERN;
	}

	public static boolean isGipsMapping(final EObject context, final EReference reference) {
		return context instanceof GipsMapping;
	}

	public static boolean isGipsMappingVariableType(final EObject context, final EReference reference) {
		return context instanceof GipsMappingVariable && reference == GipslPackage.Literals.GIPS_VARIABLE__TYPE;
	}

	public static boolean isGipsMappingVariableParameter(final EObject context, final EReference reference) {
		return context instanceof GipsMappingVariable
				&& reference == GipslPackage.Literals.GIPS_MAPPING_VARIABLE__PARAMETER;
	}

	public static boolean isGipsConstantReference(final EObject context, final EReference reference) {
		return context instanceof GipsConstantReference
				&& reference == GipslPackage.Literals.GIPS_CONSTANT_REFERENCE__CONSTANT;
	}

	public static boolean isGipsVariableReferenceExpression(final EObject context, final EReference reference) {
		return context instanceof GipsVariableReferenceExpression
				&& (reference == GipslPackage.Literals.GIPS_VARIABLE_REFERENCE_EXPRESSION__VARIABLE);
	}

	public static boolean isGipsTypeExtension(final EObject context, final EReference reference) {
		return context instanceof GipsTypeExtension;
	}

	public static boolean isGipsTypeExtensionVariableType(final EObject context, final EReference reference) {
		return context instanceof GipsTypeExtensionVariable && reference == GipslPackage.Literals.GIPS_VARIABLE__TYPE;
	}

	public static boolean isGipsConstraintContext(final EObject context, final EReference reference) {
		return context instanceof GipsConstraint && reference == GipslPackage.Literals.GIPS_CONSTRAINT__CONTEXT;
	}

	public static boolean isGipsLinearFunctionContext(final EObject context, final EReference reference) {
		return context instanceof GipsLinearFunction
				&& reference == GipslPackage.Literals.GIPS_LINEAR_FUNCTION__CONTEXT;
	}

	public static boolean isGipsLinearFunctionReference(final EObject context, final EReference reference) {
		return context instanceof GipsLinearFunctionReference
				&& reference == GipslPackage.Literals.GIPS_LINEAR_FUNCTION_REFERENCE__FUNCTION;
	}

	public static boolean isGipsMappingExpression(final EObject context, final EReference reference) {
		return context instanceof GipsMappingExpression
				&& reference == GipslPackage.Literals.GIPS_MAPPING_EXPRESSION__MAPPING;
	}

	public static boolean isGipsTypeExpression(final EObject context, final EReference reference) {
		return context instanceof GipsTypeExpression && reference == GipslPackage.Literals.GIPS_TYPE_EXPRESSION__TYPE;
	}

	public static boolean isGipsPatternExpression(final EObject context, final EReference reference) {
		return context instanceof GipsPatternExpression
				&& reference == GipslPackage.Literals.GIPS_PATTERN_EXPRESSION__PATTERN;
	}

	public static boolean isGipsRuleExpression(final EObject context, final EReference reference) {
		return context instanceof GipsRuleExpression && reference == GipslPackage.Literals.GIPS_RULE_EXPRESSION__RULE;
	}

	public static boolean isGipsNodeExpression(final EObject context, final EReference reference) {
		return context instanceof GipsNodeExpression && reference == GipslPackage.Literals.GIPS_NODE_EXPRESSION__NODE;
	}

	public static boolean isGipsAttributeExpression(final EObject context, final EReference reference) {
		return context instanceof GipsAttributeExpression
				&& reference == GipslPackage.Literals.GIPS_ATTRIBUTE_LITERAL__LITERAL;
	}

	public static boolean isGipsAttributeLiteral(final EObject context, final EReference reference) {
		return context instanceof GipsAttributeLiteral
				&& reference == GipslPackage.Literals.GIPS_ATTRIBUTE_LITERAL__LITERAL;
	}

	public static boolean isGipsTypeSelect(final EObject context, final EReference reference) {
		return context instanceof GipsTypeSelect && reference == GipslPackage.Literals.GIPS_TYPE_SELECT__TYPE;
	}

	public static boolean isGipsTypeQuery(final EObject context, final EReference reference) {
		return context instanceof GipsTypeQuery && reference == GipslPackage.Literals.GIPS_TYPE_QUERY__TYPE;
	}

	public static boolean isGipsJoinBySelection(final EObject context, final EReference reference) {
		return context instanceof GipsJoinBySelectionOperation;
	}

	public static boolean isGipsJoinSingleSelection(final EObject context, final EReference reference) {
		return context instanceof GipsJoinSingleSelection
				&& reference == org.emoflon.gips.gipsl.gipsl.GipslPackage.Literals.GIPS_JOIN_SINGLE_SELECTION__NODE;
	}

	public static boolean isGipsJoinPairSelection(final EObject context, final EReference reference) {
		return context instanceof GipsJoinPairSelection
				&& (reference == GipslPackage.Literals.GIPS_JOIN_PAIR_SELECTION__LEFT_NODE
						|| reference == GipslPackage.Literals.GIPS_JOIN_PAIR_SELECTION__RIGHT_NODE);
	}

	public static Object getContainer(EObject node, Set<Class<?>> classes) {
		if (node == null)
			return null;

		EObject current = node;
		do {
			current = current.eContainer();
		} while (current != null && !(classes.contains(current.getClass())));

		return current;
	}

	public static Collection<EClass> getClasses(final EObject context) {
		EditorGTFile gtFile = GTEditorPatternUtils.getContainer(context, EditorGTFileImpl.class);
		final Set<EClass> classes = new HashSet<>();
		// Local imports
		gtFile.getImports().forEach(i -> {
			GTEditorModelUtils.loadEcoreModel(i.getName())
					.ifPresent(m -> classes.addAll(GTEditorModelUtils.getElements(m, EClass.class)));
		});
		// Transitive imports
		Map<String, ImportedPattern> representativePatterns = new HashMap<>();
		gtFile.getImportedPattern().forEach(ip -> {
			if (!representativePatterns.containsKey(ip.getFile()))
				representativePatterns.put(ip.getFile(), ip);
		});
		representativePatterns.values().forEach(ip -> {

			XtextResourceSet rs = new XtextResourceSet();
			URI gtModelUri = URI.createFileURI(ip.getFile().replace("\"", ""));

			Resource resource = null;
			try {
				resource = rs.getResource(gtModelUri, true);
			} catch (Exception e) {
				return;
			}

			EcoreUtil2.resolveLazyCrossReferences(resource, () -> false);
			EObject gtModel = resource.getContents().get(0);
			if (gtModel instanceof org.emoflon.ibex.gt.editor.gT.EditorGTFile otherGtFile) {
				otherGtFile.getImports().forEach(i -> {
					GTEditorModelUtils.loadEcoreModel(i.getName())
							.ifPresent(m -> classes.addAll(GTEditorModelUtils.getElements(m, EClass.class)));
				});
			} else if (gtModel instanceof EditorGTFile otherGipsFile) {
				otherGipsFile.getImports().forEach(i -> {
					GTEditorModelUtils.loadEcoreModel(i.getName())
							.ifPresent(m -> classes.addAll(GTEditorModelUtils.getElements(m, EClass.class)));
				});
			} else {
				return;
			}

		});
		return classes;
	}

	public static Collection<EDataType> getDatatypes(final EObject context) {
		EditorGTFile gtFile = GTEditorPatternUtils.getContainer(context, EditorGTFileImpl.class);
		final Set<EDataType> types = new HashSet<>();
		// Local imports
		gtFile.getImports().forEach(i -> {
			GTEditorModelUtils.loadEcoreModel(i.getName())
					.ifPresent(m -> types.addAll(GTEditorModelUtils.getElements(m, EDataType.class)));
		});
		// Transitive imports
		Map<String, ImportedPattern> representativePatterns = new HashMap<>();
		gtFile.getImportedPattern().forEach(ip -> {
			if (!representativePatterns.containsKey(ip.getFile()))
				representativePatterns.put(ip.getFile(), ip);
		});
		representativePatterns.values().forEach(ip -> {

			XtextResourceSet rs = new XtextResourceSet();
			URI gtModelUri = URI.createFileURI(ip.getFile().replace("\"", ""));

			Resource resource = null;
			try {
				resource = rs.getResource(gtModelUri, true);
			} catch (Exception e) {
				return;
			}

			EcoreUtil2.resolveLazyCrossReferences(resource, () -> false);
			EObject gtModel = resource.getContents().get(0);
			if (gtModel instanceof org.emoflon.ibex.gt.editor.gT.EditorGTFile otherGtFile) {
				otherGtFile.getImports().forEach(i -> {
					GTEditorModelUtils.loadEcoreModel(i.getName())
							.ifPresent(m -> types.addAll(GTEditorModelUtils.getElements(m, EDataType.class)));
				});
			} else if (gtModel instanceof EditorGTFile otherGipsFile) {
				otherGipsFile.getImports().forEach(i -> {
					GTEditorModelUtils.loadEcoreModel(i.getName())
							.ifPresent(m -> types.addAll(GTEditorModelUtils.getElements(m, EDataType.class)));
				});
			} else {
				return;
			}
		});
		return types;
	}

	public static Collection<EEnum> getEnums(final EObject context) {
		EditorGTFile gtFile = GTEditorPatternUtils.getContainer(context, EditorGTFileImpl.class);
		final Set<EEnum> types = new HashSet<>();
		gtFile.getImports().forEach(i -> {
			GTEditorModelUtils.loadEcoreModel(i.getName())
					.ifPresent(m -> types.addAll(GTEditorModelUtils.getElements(m, EEnum.class)));
		});
		// Transitive imports
		Map<String, ImportedPattern> representativePatterns = new HashMap<>();
		gtFile.getImportedPattern().forEach(ip -> {
			if (!representativePatterns.containsKey(ip.getFile()))
				representativePatterns.put(ip.getFile(), ip);
		});
		representativePatterns.values().forEach(ip -> {

			XtextResourceSet rs = new XtextResourceSet();
			URI gtModelUri = URI.createFileURI(ip.getFile().replace("\"", ""));

			Resource resource = null;
			try {
				resource = rs.getResource(gtModelUri, true);
			} catch (Exception e) {
				return;
			}

			EcoreUtil2.resolveLazyCrossReferences(resource, () -> false);
			EObject gtModel = resource.getContents().get(0);
			if (gtModel instanceof org.emoflon.ibex.gt.editor.gT.EditorGTFile otherGtFile) {
				otherGtFile.getImports().forEach(i -> {
					GTEditorModelUtils.loadEcoreModel(i.getName())
							.ifPresent(m -> types.addAll(GTEditorModelUtils.getElements(m, EEnum.class)));
				});
			} else if (gtModel instanceof EditorGTFile otherGipsFile) {
				otherGipsFile.getImports().forEach(i -> {
					GTEditorModelUtils.loadEcoreModel(i.getName())
							.ifPresent(m -> types.addAll(GTEditorModelUtils.getElements(m, EEnum.class)));
				});
			} else {
				return;
			}
		});
		return types;
	}

	public static List<EditorPattern> getAllEditorPatterns(final EObject context) {
		EditorGTFile gtFile = GTEditorPatternUtils.getContainer(context, EditorGTFileImpl.class);
		List<EditorPattern> patterns = new LinkedList<>();
		patterns.addAll(gtFile.getPatterns());
		gtFile.getImportedPattern().forEach(p -> patterns.add(p.getPattern()));

		return patterns;
	}

	public static List<EditorPattern> getPatterns(final EObject context) {
		EditorGTFile gtFile = GTEditorPatternUtils.getContainer(context, EditorGTFileImpl.class);
		List<EditorPattern> patterns = new LinkedList<>();
		patterns.addAll(gtFile.getPatterns());
		gtFile.getImportedPattern().forEach(p -> patterns.add(p.getPattern()));
		return patterns.stream().filter(p -> !GTEditorPatternUtils.containsCreatedOrDeletedElements(p))
				.collect(Collectors.toList());
	}

	public static List<EditorPattern> getRules(final EObject context) {
		EditorGTFile gtFile = GTEditorPatternUtils.getContainer(context, EditorGTFileImpl.class);
		List<EditorPattern> patterns = new LinkedList<>();
		patterns.addAll(gtFile.getPatterns());
		gtFile.getImportedPattern().forEach(p -> patterns.add(p.getPattern()));
		return patterns.stream().filter(p -> GTEditorPatternUtils.containsCreatedOrDeletedElements(p))
				.collect(Collectors.toList());
	}

	public static EditorPattern getPatternOrRuleOf(final EObject context) {
		return switch (context) {
		case EditorPattern pattern -> pattern;
		case GipsPatternExpression pattern -> pattern.getPattern();
		case GipsMapping mapping -> mapping.getPattern();
		case GipsMappingExpression mapping when mapping.getMapping() != null -> mapping.getMapping().getPattern();
		case GipsRuleExpression rule -> rule.getRule();
		case null, default -> null;
		};
	}

	public static Set<EditorNode> getNonCreatedEditorNodes(final EditorPattern pattern) {
		if (pattern == null)
			return Collections.emptySet();
		return pattern.getNodes().stream() //
				.filter(n -> n.getOperator() != EditorOperator.CREATE) //
				.collect(Collectors.toSet());
	}

	public static EObject getLocalContext(final EObject expression) {
		EObject root = (EObject) GipslScopeContextUtil.getContainer(expression,
				Set.of(GipsConstraintImpl.class, GipsLinearFunctionImpl.class, GipsConstantReferenceImpl.class));

		if (root instanceof GipsConstraint constraint) {
			return constraint.getContext();
		} else if (root instanceof GipsLinearFunction function) {
			return function.getContext();
		} else if (root instanceof GipsConstantReference reference && reference.getConstant() != null
				&& reference.getConstant().getExpression() != null) {
			return getLocalContext(reference.getConstant().getExpression());
		} else {
			return null;
		}
	}

	public static boolean hasLocalContext(final EObject expression) {
		EObject root = (EObject) GipslScopeContextUtil.getContainer(expression,
				Set.of(GipsConstraintImpl.class, GipsLinearFunctionImpl.class));

		if (root instanceof GipsConstraint constraint) {
			return constraint.getContext() != null;
		} else if (root instanceof GipsLinearFunction function) {
			return function.getContext() != null;
		} else {
			return false;
		}
	}

	public static EObject getSetContext(final EObject expression) {
		if (expression instanceof GipsTypeSelect select && select.getType() != null) {
			return select.getType();
		} else if (expression instanceof GipsTransformOperation transform && transform.getExpression() != null) {
			return transform.getExpression();
		} else if (expression.eContainer() instanceof GipsValueExpression root
				&& !(root.getValue() instanceof GipsSetElementExpression)) {
			return root.getValue();
		} else if (expression instanceof GipsValueExpression root
				&& !(root.getValue() instanceof GipsSetElementExpression)) {
			return root.getValue();
		} else if (expression.eContainer() instanceof GipsConstantReference reference && reference.getConstant() != null
				&& reference.getConstant().getExpression() != null) {
			return getSetContext(reference.getConstant().getExpression());
		} else if (expression.eContainer() == null) {
			return null;
		} else {
			return getSetContext(expression.eContainer());
		}
	}

	public static boolean isMappingValueReferenced(final GipsMapping mapping) {
		final EditorGTFile gtFile = GTEditorPatternUtils.getContainer(mapping, EditorGTFileImpl.class);
		final Collection<EObject> toBeScanned = new HashSet<>();
		toBeScanned.addAll(gtFile.getConstraints());
		toBeScanned.addAll(gtFile.getFunctions());
		toBeScanned.add(gtFile.getObjective());
		toBeScanned.remove(null);

		TreeIterator<EObject> iterator = EcoreUtil.getAllProperContents(toBeScanned, true);

		while (iterator.hasNext()) {
			EObject next = iterator.next();
			if (next instanceof GipsSetElementExpression expression) {
				iterator.prune();

				if (expression.getExpression() instanceof GipsVariableReferenceExpression varReferenceExpression) {
					if (varReferenceExpression.isIsMappingValue()) {
						EObject setContext = GipslScopeContextUtil.getSetContext(expression);
						if (setContext instanceof GipsMappingExpression mappingExpression) {
							if (mapping.equals(mappingExpression.getMapping())) {
								return true; // mapping.value is used at least once (as element.value)
							}
						}
					}
				}
			} else if (next instanceof GipsLocalContextExpression expression) {
				iterator.prune();

				if (expression.getExpression() instanceof GipsVariableReferenceExpression varReferenceExpression) {
					if (varReferenceExpression.isIsMappingValue()) {
						EObject localContext = GipslScopeContextUtil.getLocalContext(expression);
						if (localContext instanceof GipsMapping localMapping) {
							if (mapping.equals(localMapping)) {
								return true; // mapping.value is used at least once (as context.value)
							}
						}
					}
				}
			}
		}

		return false;
	}

	public static boolean isVariableReferenced(final GipsVariable variable) {
		final EditorGTFile gtFile = GTEditorPatternUtils.getContainer(variable, EditorGTFileImpl.class);
		final Collection<EObject> toBeScanned = new HashSet<>();
		toBeScanned.addAll(gtFile.getConstraints());
		toBeScanned.addAll(gtFile.getFunctions());
		toBeScanned.add(gtFile.getObjective());
		toBeScanned.remove(null);

		TreeIterator<EObject> iterator = EcoreUtil.getAllProperContents(toBeScanned, true);

		while (iterator.hasNext()) {
			EObject next = iterator.next();
			if (next instanceof GipsVariableReferenceExpression varReferenceExpression) {
				if (varReferenceExpression.isIsGenericValue()) {
					if (variable.equals(varReferenceExpression.getVariable()))
						return true;
				}
			}
		}

		return false;
	}

	public static GipsTypeExtension getTypeExtensionForType(EObject expression, EClass type) {
		final EditorGTFile gtFile = GTEditorPatternUtils.getContainer(expression, EditorGTFileImpl.class);
		Optional<GipsTypeExtension> extension = gtFile.getTypes().stream() //
				.filter(e -> e.getRef().equals(type)) //
				.findAny();
		return extension.isPresent() ? extension.get() : null;
	}

	public static void gatherFilesWithEnding(Collection<File> gtFiles, File root, String ending, boolean ignoreBin) {
		if (root.isDirectory() && root.exists()) {
			if (ignoreBin && root.getName().equals("bin"))
				return;
			for (File subFile : root.listFiles()) {
				gatherFilesWithEnding(gtFiles, subFile, ending, ignoreBin);
			}
			return;
		} else if (!root.isDirectory() && root.exists()) {
			if (root.getName().endsWith(ending)) {
				gtFiles.add(root);
				return;
			}
		} else {
			return;
		}
	}

	public static synchronized IProject getCurrentProject(final Resource resource) {
		IProject project = null;
		IWorkbenchWindow window = null;

		if (Display.getCurrent() == null && resource != null) {
			String platformString = resource.getURI().toPlatformString(true);
			project = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(platformString)).getProject();
		} else {
			window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		}

		if (window != null) {
			IWorkbenchPage activePage = window.getActivePage();
			IEditorPart activeEditor = activePage.getActiveEditor();

			if (activeEditor != null) {
				IEditorInput input = activeEditor.getEditorInput();

				project = input.getAdapter(IProject.class);
				if (project == null) {
					IResource otherResource = input.getAdapter(IResource.class);
					if (resource != null) {
						project = otherResource.getProject();
					}
				}
			}
		}

		return project;
	}

}
