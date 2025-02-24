package org.emoflon.gips.debugger.connector.highlight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.emoflon.gips.debugger.annotation.AnnotationAndPosition;

public class URIAnotationHighlightStrategy extends AnnotationBasedHighlightStrategy {

	@Override
	protected List<AnnotationAndPosition> computeNewAnnotations(SubMonitor monitor, XtextEditor editor,
			Collection<String> elementIds) {

		IXtextDocument document = editor.getDocument();
		if (document == null)
			return Collections.emptyList();

		IUnitOfWork<List<AnnotationAndPosition>, XtextResource> work = resource -> {
			List<AnnotationAndPosition> annotations = new ArrayList<>(elementIds.size());

			for (String uriFragment : elementIds) {
				EObject eObject = resource.getEObject(uriFragment);
				addAnnotation(annotations, eObject, null);
			}

			return annotations;
		};

		return document.readOnly(work);
	}

}
