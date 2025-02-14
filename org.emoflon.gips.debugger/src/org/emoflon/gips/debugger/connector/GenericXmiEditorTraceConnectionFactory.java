package org.emoflon.gips.debugger.connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
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

		public XmiEditorTraceConnection(EcoreEditor editor) {
			this(editor, new EcoreHighlightStrategy());
		}

		private XmiEditorTraceConnection(EcoreEditor editor, EcoreHighlightStrategy highlightStrategy) {
			super(editor, highlightStrategy);
		}

		@Override
		protected void computeContextAndModelId() {
			URI uri = EditUIUtil.getURI(editor.getEditorInput(),
					editor.getEditingDomain().getResourceSet().getURIConverter());

			URI modelURI = HelperEclipse.toPlatformURI(uri);
			IProject project = HelperEclipse.tryAndGetProject(modelURI);
			IPath relativeFilePath = IPath.fromOSString(modelURI.toPlatformString(true)).removeFirstSegments(1);

			getHighlightStrategy().setModelURI(modelURI);

			setContextId(project.getName());
			setModelId(relativeFilePath.toString());
		}

		@Override
		public EcoreHighlightStrategy getHighlightStrategy() {
			return (EcoreHighlightStrategy) super.getHighlightStrategy();
		}

		@Override
		protected Collection<String> transformSelectionToElementIds(ISelection iSelection) {
			if (iSelection instanceof TreeSelection treeSelection) {
				EcoreHighlightStrategy strategy = getHighlightStrategy();
				Collection<String> elementIds = new ArrayList<String>();

				for (Object selection : treeSelection) {
					if (selection instanceof EObject eObject) {
						Resource resource = eObject.eResource();
						if (resource != null && !strategy.isSameResourceURI(resource.getURI()))
							continue;

						String elementId = ResolveEcore2Id.INSTANCE.resolve(eObject);
						elementIds.add(elementId);
					}
				}
				return elementIds;
			}
			return Collections.emptySet();
		}
	}

	private static final class EcoreHighlightStrategy implements IHighlightStrategy<EcoreEditor> {

		private URI modelURI;

		@Override
		public IStatus removeEditorHighlights(EcoreEditor editor, SubMonitor monitor) {
			return Status.OK_STATUS;
		}

		@Override
		public IStatus computeEditorHighlights(EcoreEditor editor, ITraceContext context, String localModelId,
				String remoteModelId, Collection<String> remoteElementIds, SubMonitor monitor) {

			Collection<String> localElementIds = context.resolveElementsByTrace(remoteModelId, localModelId,
					remoteElementIds, true);
			highlightElementsByUriFragment(editor, localElementIds);

			return Status.OK_STATUS;
		}

		private void highlightElementsByUriFragment(EcoreEditor editor, Collection<String> uriFragments) {
			if (uriFragments.isEmpty())
				return;

			ResourceSet resourceSet = editor.getEditingDomain().getResourceSet();
			Collection<EObject> targetSelection = new ArrayList<EObject>(uriFragments.size());

			for (Resource resource : resourceSet.getResources()) {
				if (!isSameResourceURI(resource.getURI()))
					continue;

				for (String uriFragment : uriFragments) {
					EObject e = resource.getEObject(uriFragment);
					if (e != null)
						targetSelection.add(e);
				}

				break;
			}

			editor.setSelectionToViewer(targetSelection);
		}

		public boolean isSameResourceURI(URI uri) {
			URI relativeUri = HelperEclipse.toPlatformURI(uri);
			return getModelURI().equals(relativeUri);
		}

		public URI getModelURI() {
			return modelURI;
		}

		public void setModelURI(URI modelURI) {
			this.modelURI = modelURI;
		}
	}

}
