package org.emoflon.gips.debugger.connector.highlight;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.emoflon.gips.debugger.annotation.AnnotationAndPosition;
import org.emoflon.gips.debugger.annotation.HelperTraceAnnotation;
import org.emoflon.gips.debugger.api.ITraceContext;
import org.emoflon.gips.debugger.connector.IHighlightStrategy;

public abstract class AnnotationBasedHighlightStrategy implements IHighlightStrategy<XtextEditor> {

	@Override
	public IStatus computeEditorHighlights(XtextEditor editor, ITraceContext context, String localModelId,
			String remoteModelId, Collection<String> remoteElementIds, SubMonitor monitor) {

		monitor = SubMonitor.convert(monitor, 3);

		monitor.split(1);
		Collection<String> elementIds = context.getModelChain(remoteModelId, localModelId)
				.resolveElementsFromSrcToDst(remoteElementIds);

		Collection<AnnotationAndPosition> annotationsToAdd = computeNewAnnotations(monitor.split(1), editor,
				elementIds);

		Collection<Annotation> annotationsToRemove = getExistingAnnotations(getAnnotationModel(editor));

		updateEditorUI(monitor.split(1), editor, annotationsToAdd, annotationsToRemove);

		return monitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS;
	}

	@Override
	public IStatus removeEditorHighlights(XtextEditor editor, SubMonitor monitor) {
		monitor = SubMonitor.convert(monitor, 2);

		monitor.split(1);
		Collection<Annotation> annotationsToRemove = getExistingAnnotations(getAnnotationModel(editor));

		updateEditorUI(monitor.split(1), editor, Collections.emptyList(), annotationsToRemove);
		return monitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS;
	}

	private static void updateEditorUI(SubMonitor monitor, ITextEditor editor,
			Collection<AnnotationAndPosition> annotationsToAdd, Collection<Annotation> annotationsToRemove) {

		Position revealPosition = findFirstPosition(annotationsToAdd);

		Display.getDefault().syncExec(() -> {
			if (monitor.isCanceled())
				return;

			if (revealPosition != null && editor instanceof XtextEditor xtextEditor)
				xtextEditor.reveal(revealPosition.offset, revealPosition.length);

			IAnnotationModel annotationModel = getAnnotationModel(editor);
			if (annotationModel == null) {
				monitor.done();
				return;
			}

			if (annotationModel instanceof IAnnotationModelExtension annotationModelExtension) {

				Map<Annotation, Position> add = annotationsToAdd.parallelStream()
						.collect(Collectors.toMap(e -> e.annotation, e -> e.position));
				Annotation[] remove = annotationsToRemove.toArray(s -> new Annotation[s]);

				annotationModelExtension.replaceAnnotations(remove, add);
			} else {
				for (Annotation annotation : annotationsToRemove) {
					if (monitor.isCanceled())
						return;
					annotationModel.removeAnnotation(annotation);
				}

				for (AnnotationAndPosition annotation : annotationsToAdd) {
					if (monitor.isCanceled())
						return;
					annotationModel.addAnnotation(annotation.annotation, annotation.position);
				}
			}
		});

	}

	private static Position findFirstPosition(Collection<AnnotationAndPosition> annotations) {
		if (annotations.isEmpty())
			return null;

		return annotations.parallelStream().min((a, b) -> a.position.offset - b.position.offset).map(e -> e.position)
				.orElse(null);
	}

	private static Collection<Annotation> getExistingAnnotations(IAnnotationModel annotationModel) {
		Set<Annotation> result = new HashSet<>();
		Iterator<Annotation> annotationIter = annotationModel.getAnnotationIterator();

		while (annotationIter.hasNext()) {
			Annotation annotation = annotationIter.next();
			if (hasAnnotationType(annotation.getType()))
				result.add(annotation);
		}

		return result;
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

	protected static void addAnnotations(List<AnnotationAndPosition> annotations, Collection<EObject> addThese,
			String comment) {
		for (EObject eObject : addThese)
			addAnnotation(annotations, eObject, comment);
	}

	protected static void addAnnotation(List<AnnotationAndPosition> annotations, EObject eObject, String comment) {
		ICompositeNode textNode = NodeModelUtils.findActualNodeFor(eObject);
		Annotation annotation = new Annotation(HelperTraceAnnotation.TRACE_MARKER_ID, false, comment);
		Position position = new Position(textNode.getOffset(), textNode.getLength());
		annotations.add(new AnnotationAndPosition(annotation, position));
	}

	protected abstract List<AnnotationAndPosition> computeNewAnnotations(SubMonitor monitor, XtextEditor editor,
			Collection<String> elementIds);

}
