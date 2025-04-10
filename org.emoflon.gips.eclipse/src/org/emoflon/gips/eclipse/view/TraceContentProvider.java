package org.emoflon.gips.eclipse.view;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.emoflon.gips.eclipse.view.model.INode;

final class TraceContentProvider implements ITreeContentProvider {

	public TraceContentProvider() {

	}

	@Override
	public Object[] getElements(Object rootElement) {
		return getChildren(rootElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof INode node)
			return node.getChilds();
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof INode node)
			return node.getParent();
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof INode node)
			return node.hasChilds();
		return false;
	}
}