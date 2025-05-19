package org.emoflon.gips.eclipse.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.util.Strings;

public class CplexQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider
		implements IQualifiedNameProvider {

	@Override
	protected QualifiedName computeFullyQualifiedNameFromNameAttribute(EObject obj) {
		String name = getResolver().apply(obj);
		if (Strings.isEmpty(name))
			return null;

		QualifiedName qualifiedName = getConverter().toQualifiedName(name);
		return qualifiedName;
	}

}
