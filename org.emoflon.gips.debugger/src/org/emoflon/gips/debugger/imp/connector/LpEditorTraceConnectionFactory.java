package org.emoflon.gips.debugger.imp.connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.debugger.api.ILPTraceKeywords;
import org.emoflon.gips.debugger.api.ITraceContext;
import org.emoflon.gips.debugger.cplexLp.ConstraintExpression;
import org.emoflon.gips.debugger.cplexLp.LinearTerm;
import org.emoflon.gips.debugger.cplexLp.NumberLiteral;
import org.emoflon.gips.debugger.cplexLp.ObjectiveExpression;
import org.emoflon.gips.debugger.cplexLp.SectionObjective;
import org.emoflon.gips.debugger.cplexLp.Variable;
import org.emoflon.gips.debugger.cplexLp.VariableDecleration;
import org.emoflon.gips.debugger.cplexLp.VariableRef;
import org.emoflon.gips.debugger.imp.HelperEcoreSelection;
import org.emoflon.gips.debugger.marker.AnnotationMarkerData;
import org.emoflon.gips.debugger.utility.HelperEObjects;
import org.emoflon.gips.debugger.utility.HelperEclipse;

public final class LpEditorTraceConnectionFactory extends XtextEditorTraceConnectionFactory {

	private static final String LANGUAGE_NAME = "org.emoflon.gips.debugger.CplexLp";

	public LpEditorTraceConnectionFactory() {
		super(LANGUAGE_NAME);
	}

	@Override
	protected LpEditorTraceConnection createConnection(XtextEditor editor) {
		return new LpEditorTraceConnection(editor);
	}

	private static final class LpEditorTraceConnection extends XtextEditorTraceConnection {

		// private TraceMap<String, EObject> traceMap;
		private String contextId;
		private String modelId;

		public LpEditorTraceConnection(XtextEditor editor) {
			super(editor);
		}

		@Override
		protected void computeContextAndModelId() {
			var uri = editor.getDocument().getResourceURI();
			contextId = HelperEclipse.tryAndGetProject(uri).getName();
//			modelId = StaticModelIds.MODEL_ID_ILP;
//			modelId = HelperEclipse.toPlatformURI(uri).toPlatformString(true);
			modelId = uri.lastSegment();

//			traceMap = new TraceMap<>();
		}

		@Override
		protected String getContextId() {
			return contextId;
		}

		@Override
		protected String getModelId() {
			return modelId;
		}

		@Override
		protected Collection<String> transformSelectionToElementIds(ISelection selection) {
			var eObjects = HelperEcoreSelection.selectionToEObjects(editor, selection);
			var elementIds = new ArrayList<String>(eObjects.size());
			for (var eObject : eObjects) {
				if (eObject instanceof NumberLiteral) {
					var variable = ((LinearTerm) eObject.eContainer()).getVariable();
					if (variable == null) {
						// TODO
					} else {
						eObject = variable;
					}
				}

				if (eObject instanceof Variable variable) {
					String variableName = null;
					EObject parent = null;

					if (variable instanceof VariableDecleration variableDecleration) {
						variableName = variableDecleration.getName();
					}

					if (variable instanceof VariableRef variableRef) {
						variableName = variableRef.getRef().getName();
						parent = HelperEObjects.getParentOfType(variableRef, ConstraintExpression.class,
								ObjectiveExpression.class);
					}

					if (variableName == null) {
						continue;
					}

					if (parent instanceof ConstraintExpression) {
						elementIds.add(buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT_VAR, variableName));
					} else if (parent instanceof ObjectiveExpression) {
						elementIds.add(buildElementId(ILPTraceKeywords.TYPE_OBJECTIVE_VAR, variableName));
					} else {
						elementIds.add(buildElementId(ILPTraceKeywords.TYPE_VARIABLE, variableName));
					}

					var delimiter = variableName.indexOf("#");
					var shortVariableName = delimiter < 0 ? variableName : variableName.substring(0, delimiter);
					elementIds.add(buildElementId(ILPTraceKeywords.TYPE_MAPPING, shortVariableName));

					continue;
				}

				if (eObject instanceof ConstraintExpression constraint) {
					String name = constraint.getName();
					var delimiter = name.indexOf("_");
					if (delimiter >= 0) {
						name = name.substring(0, delimiter);
					}
					name = buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT, name);
					elementIds.add(name);
				}

				if (eObject instanceof SectionObjective) {
					elementIds.add(buildElementId(ILPTraceKeywords.TYPE_GLOBAL_OBJECTIVE, ""));
					continue;
				}
			}
			return elementIds;
		}

		private static String buildElementId(String type, String elementName) {
			return ILPTraceKeywords.buildElementId(type, elementName);
		}

		@Override
		protected void computeEditorHighligt(ITraceContext context, String modelId,
				Collection<String> selectedElementsById) {

//			if (StaticModelIds.MODEL_ID_ILP_AST.equals(modelId)) {
//				return;
//			}

			removeEditorHighlight();
			List<AnnotationMarkerData> highlightMarkers = new ArrayList<>();

			var chain = context.getModelChain(modelId, getModelId());
			var localElements = chain.resolveElements(selectedElementsById);
			for (var localElement : localElements) {

//				var cache = traceMap.getTargets(localElement);
//				if (!cache.isEmpty()) {
//					highlightElements(cache);
//					continue;
//				}

				var typeAndValue = getTypeAndValue(localElement);

				switch (typeAndValue.type) {
				case ILPTraceKeywords.TYPE_CONSTRAINT: {
					var eObjects = getConstraintsWhichStartWith(typeAndValue.value + "_");
					var markers = convertEObjectsToMarkers(eObjects, "Created by: " + typeAndValue.value);
					highlightMarkers.addAll(markers);
//					traceMap.mapOneToMany(localElement, eObjects);
					break;
				}
				case ILPTraceKeywords.TYPE_OBJECTIVE: {
					var variables = localElements.stream()
							.filter(e -> e.startsWith(ILPTraceKeywords.TYPE_OBJECTIVE_VAR))
							.map(e -> e.substring(ILPTraceKeywords.TYPE_OBJECTIVE_VAR.length()
									+ ILPTraceKeywords.TYPE_VALUE_DELIMITER.length()));
					var eObject = getGlobalObjective();
//					traceMap.map(localElement, eObject);
					var marker = convertEObjectToMarker(eObject);
					marker.comment = "Variables: " + variables.collect(Collectors.joining(", "));
					highlightMarkers.add(marker);
					break;
				}
				case ILPTraceKeywords.TYPE_GLOBAL_OBJECTIVE: {
					var eObject = getGlobalObjective();
//					traceMap.map(localElement, eObject);
					var marker = convertEObjectToMarker(eObject);
					highlightMarkers.add(marker);
					break;
				}
				case ILPTraceKeywords.TYPE_MAPPING: {
					var eObjects = editor.getDocument().readOnly(resource -> {
						var result = new LinkedList<EObject>();
						var iterator = resource.getAllContents();
						while (iterator.hasNext()) {
							var eObject = iterator.next();
							if (eObject instanceof Variable) {
								if (eObject instanceof VariableDecleration vd) {
									if (vd.getName().startsWith(typeAndValue.value)) {
										result.add(vd);
									}
								} else if (eObject instanceof VariableRef vr) {
									if (vr.getRef().getName().startsWith(typeAndValue.value)) {
										result.add(vr);
									}
								}
							}
						}
						return result;
					});
					var markers = convertEObjectsToMarkers(eObjects, "Created by: " + typeAndValue.value);
					highlightMarkers.addAll(markers);
					break;
				}
				}
			}

			if (!highlightMarkers.isEmpty()) {
				revealFirstMarker(highlightMarkers);
				addHighlightMarkers(highlightMarkers);
			}
		}

		private record TypeValuePair(String type, String value) {
		};

		private TypeValuePair getTypeAndValue(String text) {
			String type = null;
			String value = null;

			var delimiter = text.indexOf(ILPTraceKeywords.TYPE_VALUE_DELIMITER);
			if (delimiter < 0) {
				type = "";
				value = text;
			} else {
				type = text.substring(0, delimiter);
				value = text.substring(delimiter + ILPTraceKeywords.TYPE_VALUE_DELIMITER.length());
			}

			return new TypeValuePair(type, value);
		}

		private Collection<EObject> getConstraintsWhichStartWith(String constraintName) {
			return editor.getDocument().readOnly(resource -> {
				final var result = new LinkedList<EObject>();

				var model = (org.emoflon.gips.debugger.cplexLp.Model) resource.getContents().get(0);
				for (var constraint : model.getConstraint().getStatements()) {
					if (constraint.getName() != null && constraint.getName().startsWith(constraintName)) {
						result.add(constraint);
					}
				}

				return result;
			});
		}

		private EObject getGlobalObjective() {
			return editor.getDocument().readOnly(resource -> {
				var model = (org.emoflon.gips.debugger.cplexLp.Model) resource.getContents().get(0);
				return model.getObjective();
			});
		}

	}

}
