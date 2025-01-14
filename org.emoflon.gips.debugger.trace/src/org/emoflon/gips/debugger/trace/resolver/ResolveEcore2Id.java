package org.emoflon.gips.debugger.trace.resolver;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * The resolver returns the {@link URI#fragment() fragment path} of a given
 * {@link EObject} as the Id of that object. The fragment path is used to
 * identify a specific element within the containing resource.
 *
 * @see ResolveId2Ecore
 * @see EcoreUtil#getURI(EObject)
 */
public final class ResolveEcore2Id implements ResolveElement2Id<EObject> {

	public final static ResolveEcore2Id INSTANCE = new ResolveEcore2Id();
	private EObject root;

	public ResolveEcore2Id() {

	}

	/**
	 * 
	 * @param root optional, can be null. The root is used to create a relative
	 *             fragment path, starting from there.
	 */
	public ResolveEcore2Id(EObject root) {
		this.root = root;
	}

	@Override
	public String resolve(final EObject object) {
		if (root != null) {
			return EcoreUtil.getRelativeURIFragmentPath(root, object);
		} else {
			URI uri = EcoreUtil.getURI(object);
			return uri.fragment();
		}
	}

}
