package org.emoflon.gips.eclipse.view.model;

import org.eclipse.jface.viewers.ITreeContentProvider;

public interface INode {

	public INode getParent();

	/**
	 * @see ITreeContentProvider#hasChildren(Object)
	 */
	public boolean hasChilds();

	/**
	 * @see ITreeContentProvider#getChildren(Object)
	 */
	public INode[] getChilds();

	/**
	 * Called when no longer needed
	 */
	public void dispose();

}