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
 * @see EcoreUtil#getURI(EObject)
 */
public final class ResolveEcore2Id implements ResolveElement2Id<EObject> {

	public final static ResolveEcore2Id INSTANCE = new ResolveEcore2Id();

	public ResolveEcore2Id() {

	}

	@Override
	public String resolve(final EObject object) {
		URI uri = EcoreUtil.getURI(object);
		return uri.fragment();
	}

}
