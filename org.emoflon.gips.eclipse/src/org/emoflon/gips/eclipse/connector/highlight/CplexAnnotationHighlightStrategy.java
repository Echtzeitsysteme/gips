package org.emoflon.gips.eclipse.connector.highlight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.emoflon.gips.eclipse.annotation.AnnotationAndPosition;
import org.emoflon.gips.eclipse.api.ILPTraceKeywords;
import org.emoflon.gips.eclipse.cplexLp.Model;
import org.emoflon.gips.eclipse.cplexLp.Variable;

public class CplexAnnotationHighlightStrategy extends AnnotationBasedHighlightStrategy {

	@Override
	protected List<AnnotationAndPosition> computeNewAnnotations(SubMonitor monitor, XtextEditor editor,
			Collection<String> elementIds) {

		IXtextDocument document = editor.getDocument();
		if (document == null)
			return Collections.emptyList();

		IUnitOfWork<List<AnnotationAndPosition>, XtextResource> work = resource -> {

			var workRemaining = elementIds.size();
			monitor.setWorkRemaining(workRemaining);

			List<AnnotationAndPosition> result = new ArrayList<>(elementIds.size() + 1);

			for (var elementId : elementIds) {
				if (monitor.isCanceled()) {
					monitor.done();
					break;
				}

				ILPTraceKeywords.TypeValuePair typeAndValue = ILPTraceKeywords.getTypeAndValue(elementId);

				switch (typeAndValue.type()) {
				case ILPTraceKeywords.TYPE_CONSTRAINT: {
					var eObjects = getConstraintsWhichStartWith(resource, typeAndValue.value() + "_");
					addAnnotations(result, eObjects, null);
					break;
				}
				case ILPTraceKeywords.TYPE_FUNCTION: {
					String variables = elementIds.stream() //
							.filter(e -> e.startsWith(ILPTraceKeywords.TYPE_FUNCTION_VAR)) //
							.map(e -> e.substring(ILPTraceKeywords.TYPE_FUNCTION_VAR.length()
									+ ILPTraceKeywords.TYPE_VALUE_DELIMITER.length())) //
							.collect(Collectors.joining(", "));
					var eObject = getGlobalObjective(resource);
					addAnnotation(result, eObject, "Variables: " + variables);
					break;
				}
				case ILPTraceKeywords.TYPE_OBJECTIVE: {
					var eObject = getGlobalObjective(resource);
					addAnnotation(result, eObject, null);
					break;
				}
//				case ILPTraceKeywords.TYPE_MAPPING: {
//					var eObjects = getMappings(resource, typeAndValue.value());
//					addAnnotations(result, eObjects, "Created by: " + typeAndValue.value());
//					break;
//				}
				default: {
					var eObjects = getMappings(resource, typeAndValue.value());
					addAnnotations(result, eObjects, "Created by: " + typeAndValue.value());
					break;
				}
				}

				monitor.setWorkRemaining(--workRemaining);
			}

			return result;
		};

		return document.readOnly(work);
	}

	private Collection<EObject> getConstraintsWhichStartWith(XtextResource resource, String constraintName) {
		final var result = new LinkedList<EObject>();

		var model = (Model) resource.getContents().get(0);
		for (var constraint : model.getConstraint().getStatements()) {
			if (constraint.getName() != null && constraint.getName().startsWith(constraintName))
				result.add(constraint);
		}

		return result;
	}

	private EObject getGlobalObjective(XtextResource resource) {
		var model = (Model) resource.getContents().get(0);
		return model.getObjective();
	}

	private Collection<EObject> getMappings(XtextResource resource, String name) {
		final var result = new LinkedList<EObject>();

		var iterator = resource.getAllContents();
		while (iterator.hasNext()) {
			var eObject = iterator.next();
			if (eObject instanceof Variable variable) {
				if (variable.getRef().getName().startsWith(name)) {
					result.add(variable);
				}
			}
		}

		return result;
	}

}
