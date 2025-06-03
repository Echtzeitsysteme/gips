package org.emoflon.gips.eclipse.trace.resolver;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * The resolver returns the {@link URI#fragment() fragment path} of a given
 * {@link EObject} as the Id of that object. The fragment path is used to
 * identify a specific element within the containing resource.
 *
 * @see ResolveId2Ecore
 * @see EcoreUtil#getRelativeURIFragmentPath(EObject, EObject)
 */
public final class ResolveEcoreByRoot2Id implements ResolveElement2Id<EObject> {

	private final EObject root;

	/**
	 * @param root optional, can be null. The root is used to create a relative
	 *             fragment path, starting from there.
	 */
	public ResolveEcoreByRoot2Id(EObject root) {
		this.root = root;
	}

	@Override
	public String resolve(final EObject object) {
		String relPath = EcoreUtil.getRelativeURIFragmentPath(root, object);
		if (relPath.isEmpty()) { // object == root
			return "/";
		} else {
			return "//" + relPath;
		}
	}

}
