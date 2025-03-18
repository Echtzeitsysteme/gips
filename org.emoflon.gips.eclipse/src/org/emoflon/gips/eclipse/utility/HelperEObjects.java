package org.emoflon.gips.eclipse.utility;

import org.eclipse.emf.ecore.EObject;

public final class HelperEObjects {
	private HelperEObjects() {

	}

	public static boolean hasParentOfType(EObject child, Class<?>... parentTypes) {
		return getParentOfType(child, parentTypes) != null;
	}

	public static EObject getParentOfType(EObject child, Class<?>... parentTypes) {
		var target = child;
		while (target != null) {
			for (var type : parentTypes) {
				if (type.isInstance(target)) {
					return target;
				}
			}

			target = target.eContainer();
		}
		return null;
	}
}
