package org.emoflon.gips.debugger.imp.connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.edit.ui.util.EditUIUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;
import org.emoflon.gips.debugger.api.ITraceContext;
import org.emoflon.gips.debugger.trace.resolver.ResolveEcore2Id;
import org.emoflon.gips.debugger.utility.HelperEclipse;

public class GenericXmiEditorTraceConnectionFactory implements IEditorTraceConnectionFactory {

	@Override
	public boolean canConnect(IEditorPart editorPart) {
		return editorPart instanceof EcoreEditor;
	}

	@Override
	public XmiEditorTraceConnection createConnection(IEditorPart editorPart) {
		return new XmiEditorTraceConnection((EcoreEditor) editorPart);
	}

	private static final class XmiEditorTraceConnection extends EditorTraceConnection<EcoreEditor> {

		private String contextId;
		private String modelId;
		private URI modelUri;

		public XmiEditorTraceConnection(EcoreEditor editor) {
			super(editor);
		}

		@Override
		protected void computeContextAndModelId() {
			URI uri = EditUIUtil.getURI(editor.getEditorInput(),
					editor.getEditingDomain().getResourceSet().getURIConverter());

			modelUri = HelperEclipse.toPlatformURI(uri);

			contextId = HelperEclipse.tryAndGetProject(modelUri).getName();
//			modelId = HelperEclipse.toPlatformURI(modelUri).toPlatformString(true);
			modelId = modelUri.trimFileExtension().lastSegment();
		}

		private boolean isSameResourceURI(URI uri) {
//			var relativeUri = HelperEclipse.toPlatformURI(uri);
//			return modelUri.equals(relativeUri);
			return modelId.equals(uri.trimFileExtension().lastSegment());
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
		protected Collection<String> transformSelectionToElementIds(ISelection iSelection) {
			if (iSelection instanceof TreeSelection treeSelection) {
				var elementIds = new ArrayList<String>();
				for (var selection : treeSelection) {
					if (selection instanceof EObject eObject) {
						var resource = eObject.eResource();
						if (resource != null && !isSameResourceURI(resource.getURI())) {
							continue;
						}

						var elementId = ResolveEcore2Id.INSTANCE.resolve(eObject);
						elementIds.add(elementId);
					}
				}
				return elementIds;
			}
			return Collections.emptySet();
		}

		@Override
		protected void removeEditorHighlight() {
//			editor.setSelection(null);
		}

		@Override
		protected void computeEditorHighligt(ITraceContext context, String remoteModelId,
				Collection<String> remoteElementsById) {

			var localElementsById = context.resolveElementsByTrace(remoteModelId, getModelId(), remoteElementsById,
					true);

			highlightElementsByUriFragment(localElementsById);
		}

		protected void highlightElementsByUriFragment(Collection<String> uriFragments) {
			if (uriFragments.isEmpty()) {
				return;
			}

			var resourceSet = editor.getEditingDomain().getResourceSet();
			var targetSelection = new ArrayList<EObject>(uriFragments.size());

			for (var resource : resourceSet.getResources()) {
				if (!isSameResourceURI(resource.getURI())) {
					continue;
				}

				for (var uriFragment : uriFragments) {
					var e = resource.getEObject(uriFragment);
					if (e != null) {
						targetSelection.add(e);
					}
				}

				break;
			}

//			selectionActive = false;
			editor.setSelectionToViewer(targetSelection);
//			selectionActive = true;
		}
	}

}
