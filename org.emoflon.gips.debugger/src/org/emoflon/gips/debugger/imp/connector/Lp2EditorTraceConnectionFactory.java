package org.emoflon.gips.debugger.imp.connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

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

		private final class AnnotationJob extends Job {

			private ITextEditor editor;
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
			protected IStatus run(IProgressMonitor monitor) {
				final SubMonitor progress = SubMonitor.convert(monitor, 3);

				progress.split(1);
				var chain = context.getModelChain(remoteModelId, getModelId());
				var elementIds = chain.resolveElements(remoteElementIds);

				// TODO Auto-generated method stub
				final ITextEditor selectedEditor = editor;
				var annotationsToAdd = computeAnnotationMapping(editor, elementIds, progress.split(1));
				Annotation[] annotationsToRemove = new Annotation[0];

				if (!progress.isCanceled()) {
					Display.getDefault().asyncExec(() -> {
						if (progress.isCanceled())
							return;

						var annotationModel = getAnnotationModel(selectedEditor);
						if (annotationModel == null)
							return;

						if (annotationModel instanceof IAnnotationModelExtension aModel) {

							aModel.replaceAnnotations(annotationsToRemove, annotationsToAdd);
						} else {

						}

						// TODO Auto-generated method stub
					});
					return Status.OK_STATUS;
				}

				return Status.CANCEL_STATUS;
			}

			private Map<Annotation, Position> computeAnnotationMapping(ITextEditor editor,
					Collection<String> elementIds, SubMonitor monitor) {
				// TODO Auto-generated method stub
				return null;
			}

			protected IAnnotationModel getAnnotationModel(ITextEditor editor) {
				if (editor != null) {
					IEditorInput editorInput = editor.getEditorInput();
					IDocumentProvider documentProvider = editor.getDocumentProvider();
					if (editorInput != null && documentProvider != null) {
						return documentProvider.getAnnotationModel(editorInput);
					}
				}
				return null;
			}
		}

	}

}
