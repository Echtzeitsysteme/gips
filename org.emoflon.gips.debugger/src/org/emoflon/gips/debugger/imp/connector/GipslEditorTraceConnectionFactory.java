package org.emoflon.gips.debugger.imp.connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.debugger.api.ITraceContext;
import org.emoflon.gips.debugger.imp.HelperEcoreSelection;
import org.emoflon.gips.debugger.trace.resolver.ResolveEcore2Id;
import org.emoflon.gips.debugger.utility.HelperEclipse;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsGlobalObjective;
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

		private String contextId;
		private String modelId;

		public GipslEditorTraceConnection(XtextEditor editor) {
			super(editor);
		}

		@Override
		protected void computeContextAndModelId() {
			var uri = editor.getDocument().getResourceURI();
			contextId = HelperEclipse.tryAndGetProject(uri).getName();
//			modelId = StaticModelIds.MODEL_ID_GIPSL;
			// modelId = HelperEclipse.toPlatformURI(uri).toPlatformString(true); //
			modelId = uri.trimFileExtension().lastSegment();
		}

		@Override
		protected String getContextId() {
			return contextId;
		}

		@Override
		protected String getModelId() {
			return modelId;
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
			} else if (eObject instanceof GipsObjective) {
				return eObject;
			} else if (eObject instanceof GipsGlobalObjective) {
				return eObject;
			} else if (eObject instanceof GipsMapping) {
				return eObject;
			}

			if (eObject.eContainer() != null) {
				return findRelevantObject(eObject.eContainer());
			}

			return null;
		}

		@Override
		protected void computeEditorHighligt(ITraceContext context, String remoteModelId,
				Collection<String> remoteElementsById) {

			var localElementsById = context.resolveElementsByTrace(remoteModelId, getModelId(), remoteElementsById,
					true);

			removeEditorHighlight();

			if (localElementsById.isEmpty()) {
				return;
			}

			var markers = editor.getDocument().readOnly(xtextResource -> {
				var eObjects = convertUriFragmentsToEObjects(xtextResource, localElementsById);
				return convertEObjectsToMarkers(eObjects);
			});

			revealFirstMarker(markers);
			addHighlightMarkers(markers);
		}

	}

}
