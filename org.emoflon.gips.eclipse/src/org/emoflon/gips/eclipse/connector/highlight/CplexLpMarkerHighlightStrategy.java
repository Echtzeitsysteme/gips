package org.emoflon.gips.eclipse.connector.highlight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.eclipse.annotation.AnnotationMarkerData;
import org.emoflon.gips.eclipse.api.ILPTraceKeywords;
import org.emoflon.gips.eclipse.cplexLp.Model;
import org.emoflon.gips.eclipse.cplexLp.Variable;

public class CplexLpMarkerHighlightStrategy extends MarkerBasedHighlightStrategy {

	@Override
	protected List<AnnotationMarkerData> computeMarker(XtextEditor editor, Collection<String> localElementIds,
			SubMonitor monitor) {

		List<AnnotationMarkerData> highlightMarkers = new ArrayList<>();

		for (var localElement : localElementIds) {

//			var cache = traceMap.getTargets(localElement);
//			if (!cache.isEmpty()) {
//				highlightElements(cache);
//				continue;
//			}

			var typeAndValue = ILPTraceKeywords.getTypeAndValue(localElement);

			switch (typeAndValue.type()) {
			case ILPTraceKeywords.TYPE_CONSTRAINT: {
				var eObjects = getConstraintsWhichStartWith(editor, typeAndValue.value() + "_");
				var markers = convertEObjectsToMarkers(eObjects, "Created by: " + typeAndValue.value());
				highlightMarkers.addAll(markers);
//				traceMap.mapOneToMany(localElement, eObjects);
				break;
			}
			case ILPTraceKeywords.TYPE_FUNCTION: {
				var variables = localElementIds.stream().filter(e -> e.startsWith(ILPTraceKeywords.TYPE_FUNCTION_VAR))
						.map(e -> e.substring(ILPTraceKeywords.TYPE_FUNCTION_VAR.length()
								+ ILPTraceKeywords.TYPE_VALUE_DELIMITER.length()));
				var eObject = getGlobalObjective(editor);
//				traceMap.map(localElement, eObject);
				var marker = convertEObjectToMarker(eObject);
				marker.comment = "Variables: " + variables.collect(Collectors.joining(", "));
				highlightMarkers.add(marker);
				break;
			}
			case ILPTraceKeywords.TYPE_OBJECTIVE: {
				var eObject = getGlobalObjective(editor);
//				traceMap.map(localElement, eObject);
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
						if (eObject instanceof Variable variable) {
							if (variable.getRef().getName().startsWith(typeAndValue.value())) {
								result.add(variable);
							}
						}
					}
					return result;
				});
				var markers = convertEObjectsToMarkers(eObjects, "Created by: " + typeAndValue.value());
				highlightMarkers.addAll(markers);
				break;
			}
			}
		}

		return highlightMarkers;
	}

	protected static Collection<EObject> getConstraintsWhichStartWith(XtextEditor editor, String constraintName) {
		return editor.getDocument().readOnly(resource -> {
			final var result = new LinkedList<EObject>();

			var model = (Model) resource.getContents().get(0);
			for (var constraint : model.getConstraint().getStatements()) {
				if (constraint.getName() != null && constraint.getName().startsWith(constraintName)) {
					result.add(constraint);
				}
			}

			return result;
		});
	}

	protected static EObject getGlobalObjective(XtextEditor editor) {
		return editor.getDocument().readOnly(resource -> {
			var model = (Model) resource.getContents().get(0);
			return model.getObjective();
		});
	}

}
