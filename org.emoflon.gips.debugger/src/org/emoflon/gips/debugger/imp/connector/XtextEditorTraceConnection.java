package org.emoflon.gips.debugger.imp.connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.debugger.marker.AnnotationMarkerData;
import org.emoflon.gips.debugger.marker.HelperAnnotationTracerMarker;

public abstract class XtextEditorTraceConnection extends EditorTraceConnection<XtextEditor> {

	public XtextEditorTraceConnection(XtextEditor editor) {
		super(editor);
	}

	@Override
	protected void removeEditorHighlight() {
		try {
			HelperAnnotationTracerMarker.deleteAllMarkers(editor.getResource());
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	protected void setHighlightElements(Collection<EObject> eObjects) {
		var markers = convertEObjectsToMarkers(eObjects);
		revealFirstMarker(markers);
		addHighlightMarkers(markers);
	}

	protected void addHighlightMarkers(List<AnnotationMarkerData> markers) {
		final var editorResource = editor.getResource();
		try {
			for (final var marker : markers) {
				HelperAnnotationTracerMarker.createMarker(editorResource, marker.offset, marker.length, marker.comment);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void revealFirstMarker(List<AnnotationMarkerData> markers) {
		if (!markers.isEmpty()) {
			revealMarker(markers.get(0));
		}
	}

	protected void revealMarker(AnnotationMarkerData marker) {
		Objects.requireNonNull(marker);
		Display.getDefault().asyncExec(() -> {
			editor.reveal(marker.offset, marker.length);
		});
	}

	protected AnnotationMarkerData convertEObjectToMarker(EObject eObject) {
		var markers = convertEObjectsToMarkers(Collections.singleton(eObject));
		return markers.get(0);
	}

	protected List<AnnotationMarkerData> convertEObjectsToMarkers(Collection<EObject> eObjects) {
		return convertEObjectsToMarkers(eObjects, null);
	}

	protected List<AnnotationMarkerData> convertEObjectsToMarkers(Collection<EObject> eObjects, String comment) {
		var markers = new ArrayList<AnnotationMarkerData>(eObjects.size());
		for (var eObject : eObjects) {
			var textNode = NodeModelUtils.findActualNodeFor(eObject);
			markers.add(new AnnotationMarkerData(textNode.getOffset(), textNode.getLength(), comment));
		}
		return markers;
	}

	protected List<EObject> convertUriFragmentsToEObjects(XtextResource resource, Collection<String> uriFragments) {
		var eObjects = new ArrayList<EObject>(uriFragments.size());
		for (var uriFragment : uriFragments) {
			var eObject = resource.getEObject(uriFragment);
			eObjects.add(eObject);
		}
		return eObjects;
	}
}
