package org.emoflon.gips.eclipse.trace.resolver;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * The resolver returns a {@link EObject} for a given {@link URI#fragment()
 * fragment path}. To do so, the resolver is constructed based on an
 * {@link EObject} which acts as the root/container of the fragment path.
 *
 * @see ResolveEcore2Id
 * @see EcoreUtil#getEObject(EObject, String)
 */
public class ResolveId2Ecore implements ResolveId2Element<EObject> {

	private final EObject root;

	public ResolveId2Ecore(EObject root) {
		this.root = root;
	}

	@Override
	public EObject resolve(final String id) {
		var element = EcoreUtil.getEObject(root, id);
		return element;
	}

}
