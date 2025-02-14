package org.emoflon.gips.debugger.connector.highlight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.debugger.annotation.AnnotationMarkerData;
import org.emoflon.gips.debugger.api.ILPTraceKeywords;
import org.emoflon.gips.debugger.cplexLp.Variable;
import org.emoflon.gips.debugger.cplexLp.VariableDecleration;
import org.emoflon.gips.debugger.cplexLp.VariableRef;

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
						if (eObject instanceof Variable) {
							if (eObject instanceof VariableDecleration vd) {
								if (vd.getName().startsWith(typeAndValue.value())) {
									result.add(vd);
								}
							} else if (eObject instanceof VariableRef vr) {
								if (vr.getRef().getName().startsWith(typeAndValue.value())) {
									result.add(vr);
								}
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

			var model = (org.emoflon.gips.debugger.cplexLp.Model) resource.getContents().get(0);
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
			var model = (org.emoflon.gips.debugger.cplexLp.Model) resource.getContents().get(0);
			return model.getObjective();
		});
	}

}
