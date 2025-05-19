package org.emoflon.gips.eclipse.scoping;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.linking.impl.LinkingHelper;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.util.IResourceScopeCache;

import com.google.inject.Inject;

public class DynamicVariableDeclarationLocator {

	@Inject
	private IResourceScopeCache resourceCache = IResourceScopeCache.NullImpl.INSTANCE;

	@Inject
	private LinkingHelper linkingHelper;

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	public DynamicVariableDeclarationLocator() {

	}

	public IEObjectDescription findDeclarationForReference(EObject context, EReference reference) {
		EClass targetType = reference.getEReferenceType();
		if (targetType == null)
			return null;

		if (!EcoreUtil2.isAssignableFrom(targetType, context.eClass()))
			return null;

		String crossReference = linkingHelper.getCrossRefNodeAsString(NodeModelUtils.getNode(context), false);

		IEObjectDescription declaration = resourceCache.get(crossReference, context.eResource(), () -> {
			TreeIterator<EObject> iterator = EcoreUtil.getAllContents(context.eResource(), false);
			EObject target = context;

			while (iterator.hasNext()) {
				EObject next = iterator.next();
				if (!EcoreUtil2.isAssignableFrom(targetType, next.eClass()))
					continue;

				if (context == next) {
					break;
				} else {
					ICompositeNode anotherNode = NodeModelUtils.getNode(next);
					String anotherReference = linkingHelper.getCrossRefNodeAsString(anotherNode, false);
					if (crossReference.equals(anotherReference)) {
						target = next;
						break;
					}
				}
			}

			EStructuralFeature nameFeature = target.eClass().getEStructuralFeature("name");
			if (nameFeature != null) {
				target.eSet(nameFeature, crossReference);
			}

			QualifiedName qualifiedName = qualifiedNameConverter.toQualifiedName(crossReference);
			return EObjectDescription.create(qualifiedName, target);
		});

		return declaration;
	}

}
