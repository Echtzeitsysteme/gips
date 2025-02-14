package org.emoflon.gips.debugger.connector.highlight;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.debugger.annotation.HelperTraceAnnotation;
import org.emoflon.gips.debugger.api.ITraceContext;
import org.emoflon.gips.debugger.connector.IHighlightStrategy;

public abstract class AnnotationBasedHighlightStrategy implements IHighlightStrategy<XtextEditor> {

	@Override
	public IStatus computeEditorHighlights(XtextEditor editor, ITraceContext context, String localModelId,
			String remoteModelId, Collection<String> remoteElementIds, SubMonitor monitor) {
		final var jobRuntime = System.nanoTime();
		SubMonitor subMonitor = SubMonitor.convert(monitor, 3);

		subMonitor.split(1);
		Collection<String> elementIds = context.getModelChain(remoteModelId, localModelId)
				.resolveElementsFromSrcToDst(remoteElementIds);

		Map<Annotation, Position> annotationsToAdd = computeAnnotationMapping(subMonitor.split(1), editor, elementIds);
		Annotation[] annotationsToRemove = getExistingAnnotations(getAnnotationModel(editor));

		if (subMonitor.isCanceled())
			return Status.CANCEL_STATUS;

		if (!subMonitor.isCanceled()) {
			updateEditorUI(monitor, editor, annotationsToAdd, annotationsToRemove);

			System.out.println(
					"JOB DONE in: " + String.format("%.4g Seconds", (System.nanoTime() - jobRuntime) / 100_000_000d));

			return subMonitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS;
		}

		return Status.OK_STATUS;
	}

	@Override
	public IStatus removeEditorHighlights(XtextEditor editor, SubMonitor monitor) {
		Annotation[] annotationsToRemove = getExistingAnnotations(getAnnotationModel(editor));
		updateEditorUI(monitor, editor, Collections.emptyMap(), annotationsToRemove);
		return monitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS;
	}

	private static void updateEditorUI(SubMonitor monitor, ITextEditor editor,
			Map<Annotation, Position> annotationsToAdd, Annotation[] annotationsToRemove) {

		Display.getDefault().syncExec(() -> {
			if (monitor.isCanceled())
				return;

			IAnnotationModel annotationModel = getAnnotationModel(editor);
			if (annotationModel == null) {
				monitor.done();
				return;
			}

			if (annotationModel instanceof IAnnotationModelExtension annotationModelExtension) {
				annotationModelExtension.replaceAnnotations(annotationsToRemove, annotationsToAdd);
			} else {
				for (Annotation annotation : annotationsToRemove) {
					if (monitor.isCanceled())
						return;
					annotationModel.removeAnnotation(annotation);
				}

				for (Entry<Annotation, Position> annotation : annotationsToAdd.entrySet()) {
					if (monitor.isCanceled())
						return;
					annotationModel.addAnnotation(annotation.getKey(), annotation.getValue());
				}
			}
		});

	}

	private static Annotation[] getExistingAnnotations(IAnnotationModel annotationModel) {
		Set<Annotation> result = new HashSet<>();
		Iterator<Annotation> annotationIter = annotationModel.getAnnotationIterator();

		while (annotationIter.hasNext()) {
			Annotation annotation = annotationIter.next();
			if (hasAnnotationType(annotation.getType()))
				result.add(annotation);
		}

		return result.toArray(s -> new Annotation[s]);
	}

	private static IAnnotationModel getAnnotationModel(ITextEditor editor) {
		if (editor == null)
			return null;

		IEditorInput editorInput = editor.getEditorInput();
		IDocumentProvider documentProvider = editor.getDocumentProvider();
		if (editorInput != null && documentProvider != null)
			return documentProvider.getAnnotationModel(editorInput);

		return null;
	}

	private static boolean hasAnnotationType(String annotationType) {
		return HelperTraceAnnotation.TRACE_MARKER_ID.equals(annotationType);
	}

	protected static void addAnnotations(Map<Annotation, Position> annotations, Collection<EObject> addThese,
			String comment) {
		for (EObject eObject : addThese)
			addAnnotation(annotations, eObject, comment);
	}

	protected static void addAnnotation(Map<Annotation, Position> annotations, EObject eObject, String comment) {
		ICompositeNode textNode = NodeModelUtils.findActualNodeFor(eObject);
		Annotation annotation = new Annotation(HelperTraceAnnotation.TRACE_MARKER_ID, false, comment);
		Position position = new Position(textNode.getOffset(), textNode.getLength());
		annotations.put(annotation, position);
	}

	protected abstract Map<Annotation, Position> computeAnnotationMapping(SubMonitor monitor, XtextEditor editor,
			Collection<String> elementIds);

}
