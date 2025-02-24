package org.emoflon.gips.debugger.annotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public final class HelperTraceAnnotation {
	private HelperTraceAnnotation() {

	}

	public static interface CommentMerger {
		String merge(String previousComment, String nextComment);
	}

	/**
	 * Relevant extension points
	 * <ul>
	 * <li>org.eclipse.core.resources.markers
	 * <li>org.eclipse.ui.editors.annotationTypes
	 * <li>org.eclipse.ui.editors.markerAnnotationSpecification
	 * </ul>
	 */
	public static String TRACE_MARKER_ID = "org.emoflon.gips.trace.marker.link";

	public static IMarker createMarker(IResource resource) throws CoreException {
		if (resource.exists() && resource.getProject().isOpen()) {
			return resource.createMarker(TRACE_MARKER_ID);
		}
		return null;
	}

	public static IMarker createMarker(IResource resource, AnnotationMarkerData data) throws CoreException {
		return HelperTraceAnnotation.createMarker(resource, data.offset, data.length, data.comment);
	}

	public static IMarker createMarker(IResource resource, int offset, int length, String comment)
			throws CoreException {

		final var parameters = new HashMap<String, Object>();
		parameters.put(IMarker.CHAR_START, offset);
		parameters.put(IMarker.CHAR_END, offset + length);
		if (comment != null && comment.length() > 0) {
			parameters.put(IMarker.MESSAGE, comment);
		}

		if (resource.exists() && resource.getProject().isOpen()) {
			return resource.createMarker(TRACE_MARKER_ID, parameters);
		}
		return null;
	}

	public static void deleteAllMarkers(IResource resource) throws CoreException {
		if (resource.exists() && resource.getProject().isOpen()) {
			resource.deleteMarkers(HelperTraceAnnotation.TRACE_MARKER_ID, false, IResource.DEPTH_INFINITE);
		}
	}

	public static List<AnnotationMarkerData> mergeMarkers(Collection<AnnotationMarkerData> markers, int delta,
			HelperTraceAnnotation.CommentMerger commentMerger) {

		if (delta < 0) {
			throw new IllegalArgumentException("delta must be greater than or equal to zero");
		}

		if (markers.isEmpty()) {
			return Collections.emptyList();
		}

		if (markers.size() == 1) {
			return Collections.singletonList(markers.iterator().next());
		}

		var sorted = new ArrayList<>(markers);
		sorted.sort((a, b) -> a.offset < b.offset ? -1 : 1);

		var result = new ArrayList<AnnotationMarkerData>();
		result.add(new AnnotationMarkerData(sorted.get(0).offset, sorted.get(0).length, sorted.get(0).comment));

		for (var i = 1; i < sorted.size(); ++i) {
			var lastMarker = result.get(result.size() - 1);
			var nextMarker = sorted.get(i + 1);
			if (nextMarker.offset + delta < lastMarker.offset + lastMarker.length) {
				lastMarker.length = nextMarker.offset + nextMarker.length - lastMarker.offset;
				lastMarker.comment = commentMerger.merge(lastMarker.comment, nextMarker.comment);
			} else {
				result.add(new AnnotationMarkerData(nextMarker.offset, nextMarker.length, nextMarker.comment));
			}
		}

		return result;
	}
}
