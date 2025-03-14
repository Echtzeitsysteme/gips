package org.emoflon.gips.eclipse.connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.eclipse.connector.highlight.URIAnotationHighlightStrategy;
import org.emoflon.gips.eclipse.trace.resolver.ResolveEcore2Id;
import org.emoflon.gips.eclipse.utility.HelperEcoreSelection;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;

public final class GipslEditorTraceConnectionFactory extends XtextEditorTraceConnectionFactory {

	private static final String LANGUAGE_NAME = "org.emoflon.gips.gipsl.Gipsl";

	public GipslEditorTraceConnectionFactory() {
		super(LANGUAGE_NAME);
	}

	@Override
	protected GipslEditorTraceConnection createConnection(XtextEditor editor) {
		return new GipslEditorTraceConnection(editor);
	}

	private static final class GipslEditorTraceConnection extends XtextEditorTraceConnection {

		public GipslEditorTraceConnection(XtextEditor editor) {
			super(editor, new URIAnotationHighlightStrategy());
		}

		@Override
		protected Collection<String> transformSelectionToElementIds(ISelection selection) {
			List<EObject> eObjects = HelperEcoreSelection.selectionToEObjects(editor, selection);
			List<EObject> relevantEObjets = new ArrayList<>(eObjects.size());
			for (var eObject : eObjects) {
				var relevantEObject = findRelevantObject(eObject);
				if (relevantEObject != null) {
					relevantEObjets.add(relevantEObject);
				} else {
					relevantEObjets.add(eObject);
				}
			}
			List<String> elementIds = relevantEObjets.stream().map(ResolveEcore2Id.INSTANCE::resolve).toList();
			return elementIds;
		}

		private EObject findRelevantObject(EObject eObject) {
			if (eObject instanceof GipsConstraint) {
				return eObject;
			} else if (eObject instanceof GipsLinearFunction) {
				return eObject;
			} else if (eObject instanceof GipsObjective) {
				return eObject;
			} else if (eObject instanceof GipsMapping) {
				return eObject;
			}

			if (eObject.eContainer() != null)
				return findRelevantObject(eObject.eContainer());

			return null;
		}

	}

}
