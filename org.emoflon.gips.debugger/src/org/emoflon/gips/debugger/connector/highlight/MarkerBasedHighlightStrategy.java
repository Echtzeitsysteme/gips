package org.emoflon.gips.debugger.connector.highlight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.debugger.annotation.AnnotationMarkerData;
import org.emoflon.gips.debugger.annotation.HelperTraceAnnotation;
import org.emoflon.gips.debugger.api.ITraceContext;
import org.emoflon.gips.debugger.connector.IHighlightStrategy;

public class MarkerBasedHighlightStrategy implements IHighlightStrategy<XtextEditor> {

	@Override
	public IStatus computeEditorHighlights(XtextEditor editor, ITraceContext context, String localModelId,
			String remoteModelId, Collection<String> remoteElementIds, SubMonitor monitor) {

		Collection<String> localElementIds = context.resolveElementsByTrace(remoteModelId, localModelId,
				remoteElementIds, true);

		removeEditorHighlights(editor, monitor);

		if (localElementIds.isEmpty())
			return Status.OK_STATUS;

		List<AnnotationMarkerData> markers = computeMarker(editor, localElementIds, monitor);
		revealFirstMarker(editor, markers);
		addHighlightMarkers(editor, markers);

		return Status.OK_STATUS;
	}

	@Override
	public IStatus removeEditorHighlights(XtextEditor editor, SubMonitor monitor) {
		try {
			HelperTraceAnnotation.deleteAllMarkers(editor.getResource());
		} catch (CoreException e) {
			e.printStackTrace();
			return Status.error(e.getMessage());
		}
		return Status.OK_STATUS;
	}

	protected List<AnnotationMarkerData> computeMarker(XtextEditor editor, Collection<String> localElementIds,
			SubMonitor monitor) {
		return editor.getDocument().readOnly(xtextResource -> {
			List<EObject> eObjects = convertUriFragmentsToEObjects(xtextResource, localElementIds);
			return convertEObjectsToMarkers(eObjects);
		});
	}

	public static void setHighlightElements(XtextEditor editor, Collection<EObject> eObjects) {
		List<AnnotationMarkerData> markers = convertEObjectsToMarkers(eObjects);
		revealFirstMarker(editor, markers);
		addHighlightMarkers(editor, markers);
	}

	public static void addHighlightMarkers(XtextEditor editor, List<AnnotationMarkerData> markers) {
		final IResource editorResource = editor.getResource();
		try {
			for (final AnnotationMarkerData marker : markers)
				HelperTraceAnnotation.createMarker(editorResource, marker.offset, marker.length, marker.comment);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void revealFirstMarker(XtextEditor editor, List<AnnotationMarkerData> markers) {
		if (!markers.isEmpty())
			revealMarker(editor, markers.get(0));
	}

	public static void revealMarker(XtextEditor editor, AnnotationMarkerData marker) {
		Objects.requireNonNull(marker);
		Display.getDefault().asyncExec(() -> {
			editor.reveal(marker.offset, marker.length);
		});
	}

	public static AnnotationMarkerData convertEObjectToMarker(EObject eObject) {
		List<AnnotationMarkerData> markers = convertEObjectsToMarkers(Collections.singleton(eObject));
		return markers.get(0);
	}

	public static List<AnnotationMarkerData> convertEObjectsToMarkers(Collection<EObject> eObjects) {
		return convertEObjectsToMarkers(eObjects, null);
	}

	public static List<AnnotationMarkerData> convertEObjectsToMarkers(Collection<EObject> eObjects, String comment) {
		List<AnnotationMarkerData> markers = new ArrayList<AnnotationMarkerData>(eObjects.size());
		for (EObject eObject : eObjects) {
			ICompositeNode textNode = NodeModelUtils.findActualNodeFor(eObject);
			markers.add(new AnnotationMarkerData(textNode.getOffset(), textNode.getLength(), comment));
		}
		return markers;
	}

	public static List<EObject> convertUriFragmentsToEObjects(XtextResource resource, Collection<String> uriFragments) {
		List<EObject> eObjects = new ArrayList<EObject>(uriFragments.size());
		for (String uriFragment : uriFragments) {
			EObject eObject = resource.getEObject(uriFragment);
			eObjects.add(eObject);
		}
		return eObjects;
	}

}
