package org.emoflon.gips.debugger.imp.connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.concurrent.CancelableUnitOfWork;
import org.emoflon.gips.debugger.annotation.HelperTraceAnnotation;
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
import org.emoflon.gips.debugger.utility.HelperEObjects;

public class Lp2EditorTraceConnectionFactory extends XtextEditorTraceConnectionFactory {

	private static final String LANGUAGE_NAME = "org.emoflon.gips.debugger.CplexLp";

	public Lp2EditorTraceConnectionFactory() {
		super(LANGUAGE_NAME);
	}

	@Override
	protected Lp2EditorTraceConnection createConnection(XtextEditor editor) {
		return new Lp2EditorTraceConnection(editor);
	}

	private static final class Lp2EditorTraceConnection extends XtextEditorTraceConnection {

		private final AnnotationJob annotationJob;

		public Lp2EditorTraceConnection(XtextEditor editor) {
			super(editor);
			annotationJob = new AnnotationJob();
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

					if (variable instanceof VariableDecleration variableDecleration)
						variableName = variableDecleration.getName();

					if (variable instanceof VariableRef variableRef) {
						variableName = variableRef.getRef().getName();
						parent = HelperEObjects.getParentOfType(variableRef, ConstraintExpression.class,
								ObjectiveExpression.class);
					}

					if (variableName == null)
						continue;

					if (parent instanceof ConstraintExpression) {
						elementIds.add(buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT_VAR, variableName));
					} else if (parent instanceof ObjectiveExpression) {
						elementIds.add(buildElementId(ILPTraceKeywords.TYPE_FUNCTION, variableName));
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
					if (delimiter >= 0)
						name = name.substring(0, delimiter);
					name = buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT, name);
					elementIds.add(name);
				}

				if (eObject instanceof SectionObjective) {
					elementIds.add(buildElementId(ILPTraceKeywords.TYPE_OBJECTIVE, ""));
					continue;
				}
			}
			return elementIds;
		}

		private static String buildElementId(String type, String elementName) {
			return ILPTraceKeywords.buildElementId(type, elementName);
		}

		@Override
		protected void computeEditorHighligt(ITraceContext context, String remoteModelId,
				Collection<String> remoteElementIds) {
			// TODO Auto-generated method stub

			this.annotationJob.cancel();
			this.annotationJob.setup(editor, context, remoteModelId, remoteElementIds);
			this.annotationJob.setSystem(false);
			this.annotationJob.setPriority(Job.DECORATE);
			this.annotationJob.schedule();
		}

		@Override
		protected void removeEditorHighlight() {

		}

		private static record TypeValuePair(String type, String value) {
		};

		private static TypeValuePair getTypeAndValue(String text) {
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

		private final class AnnotationJob extends Job {

			private XtextEditor editor;
			private Collection<String> remoteElementIds;
			private ITraceContext context;
			private String remoteModelId;

			public AnnotationJob() {
				super("GIPS trace visualisation task");
			}

			public void setup(ITextEditor editor, ITraceContext context, String remoteModelId,
					Collection<String> remoteElementIds) {
				this.editor = Objects.requireNonNull(editor, "editor");
				this.context = Objects.requireNonNull(context, "context");
				this.remoteModelId = Objects.requireNonNull(remoteModelId, "remoteModelId");
				this.remoteElementIds = Objects.requireNonNull(remoteElementIds, "remoteElementIds");
			}

			@Override
			protected IStatus run(IProgressMonitor eclipseMonitor) {
				final SubMonitor subMonitor = SubMonitor.convert(eclipseMonitor, 3);

				subMonitor.split(1);
				var chain = context.getModelChain(remoteModelId, getModelId());
				var elementIds = chain.resolveElements(remoteElementIds);

				// TODO Auto-generated method stub
				final XtextEditor selectedEditor = editor;
				var annotationsToAdd = computeAnnotationMapping(editor, elementIds, subMonitor.split(1));
				var annotationsToRemove = getExistingAnnotations(getAnnotationModel(selectedEditor));

				if (subMonitor.isCanceled())
					return Status.CANCEL_STATUS;

				if (!subMonitor.isCanceled()) {
					Display.getDefault().asyncExec(() -> {
						if (subMonitor.isCanceled())
							return;

						var annotationModel = getAnnotationModel(selectedEditor);
						if (annotationModel == null){
							subMonitor.done();
							return;
						}

						if (annotationModel instanceof IAnnotationModelExtension aModel) {
							aModel.replaceAnnotations(annotationsToRemove, annotationsToAdd);
						} else {
							for (var annotation : annotationsToRemove) {
								if (subMonitor.isCanceled())
									return;
								annotationModel.removeAnnotation(annotation);
							}

							for (var annotation : annotationsToAdd.entrySet()) {
								if (subMonitor.isCanceled())
									return;
								annotationModel.addAnnotation(annotation.getKey(), annotation.getValue());
							}
						}

						// TODO Auto-generated method stub
					});
					return Status.OK_STATUS;
				}

				return Status.OK_STATUS;
			}

			private Map<Annotation, Position> computeAnnotationMapping(XtextEditor editor,
					Collection<String> elementIds, SubMonitor monitor) {

				IXtextDocument document = editor.getDocument();
				if (document == null)
					return Collections.emptyMap();

				Map<Annotation, Position> annotations = document
						.readOnly(new CancelableUnitOfWork<Map<Annotation, Position>, XtextResource>() {
							@Override
							public Map<Annotation, Position> exec(XtextResource state, CancelIndicator cancelIndicator)
									throws Exception {

								var workRemaining = elementIds.size();
								monitor.setWorkRemaining(workRemaining);

								Map<Annotation, Position> result = new HashMap<>(elementIds.size() + 1);

								for (var elementId : elementIds) {
									if (monitor.isCanceled()) {
										monitor.done();
										break;
									}

									TypeValuePair typeAndValue = getTypeAndValue(elementId);

									switch (typeAndValue.type) {
									case ILPTraceKeywords.TYPE_CONSTRAINT: {
										var eObjects = getConstraintsWhichStartWith(state, typeAndValue.value + "_");
										addAnnotations(result, eObjects, null);
										break;
									}
									case ILPTraceKeywords.TYPE_OBJECTIVE: {
										String variables = elementIds.stream() //
												.filter(e -> e.startsWith(ILPTraceKeywords.TYPE_OBJECTIVE_VAR)) //
												.map(e -> e.substring(ILPTraceKeywords.TYPE_OBJECTIVE_VAR.length()
														+ ILPTraceKeywords.TYPE_VALUE_DELIMITER.length())) //
												.collect(Collectors.joining(", "));
										var eObject = getGlobalObjective(state);
										addAnnotation(result, eObject, "Variables: " + variables);
										break;
									}
									case ILPTraceKeywords.TYPE_GLOBAL_OBJECTIVE: {
										var eObject = getGlobalObjective(state);
										addAnnotation(result, eObject, null);
										break;
									}
									case ILPTraceKeywords.TYPE_MAPPING: {
										var eObjects = getMappings(state, typeAndValue.value);
										addAnnotations(result, eObjects, "Created by: " + typeAndValue.value);
										break;
									}
									}

									monitor.setWorkRemaining(--workRemaining);
								}

								return result;
							}
						});

				return annotations;
			}

			private Annotation[] getExistingAnnotations(IAnnotationModel annotationModel) {
				Set<Annotation> result = new HashSet<>();
				Iterator<Annotation> annotationIter = annotationModel.getAnnotationIterator();

				while (annotationIter.hasNext()) {
					Annotation annotation = annotationIter.next();
					if (HelperTraceAnnotation.TRACE_MARKER_ID.equals(annotation.getType()))
						result.add(annotation);
				}

				return result.toArray(s -> new Annotation[s]);
			}

			private IAnnotationModel getAnnotationModel(ITextEditor editor) {
				if (editor == null)
					return null;

				IEditorInput editorInput = editor.getEditorInput();
				IDocumentProvider documentProvider = editor.getDocumentProvider();
				if (editorInput != null && documentProvider != null)
					return documentProvider.getAnnotationModel(editorInput);

				return null;
			}

			private static void addAnnotations(Map<Annotation, Position> annotations, Collection<EObject> addThese,
					String comment) {
				for (var eObject : addThese)
					addAnnotation(annotations, eObject, comment);
			}

			private static void addAnnotation(Map<Annotation, Position> annotations, EObject eObject, String comment) {
				var textNode = NodeModelUtils.findActualNodeFor(eObject);
				var annotation = new Annotation(HelperTraceAnnotation.TRACE_MARKER_ID, false, comment);
				var position = new Position(textNode.getOffset(), textNode.getLength());
				annotations.put(annotation, position);
			}

			private static Collection<EObject> getConstraintsWhichStartWith(XtextResource resource,
					String constraintName) {
				final var result = new LinkedList<EObject>();

				var model = (org.emoflon.gips.debugger.cplexLp.Model) resource.getContents().get(0);
				for (var constraint : model.getConstraint().getStatements()) {
					if (constraint.getName() != null && constraint.getName().startsWith(constraintName))
						result.add(constraint);
				}

				return result;
			}

			private static EObject getGlobalObjective(XtextResource resource) {
				var model = (org.emoflon.gips.debugger.cplexLp.Model) resource.getContents().get(0);
				return model.getObjective();
			}

			private static Collection<EObject> getMappings(XtextResource resource, String name) {
				final var result = new LinkedList<EObject>();

				var iterator = resource.getAllContents();
				while (iterator.hasNext()) {
					var eObject = iterator.next();
					if (eObject instanceof Variable) {
						if (eObject instanceof VariableDecleration vd) {
							if (vd.getName().startsWith(name)) {
								result.add(vd);
							}
						} else if (eObject instanceof VariableRef vr) {
							if (vr.getRef().getName().startsWith(name)) {
								result.add(vr);
							}
						}
					}
				}

				return result;
			}
		}

	}

}
