package org.emoflon.gips.eclipse.state;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.linking.impl.LinkingHelper;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.DerivedStateAwareResource;
import org.eclipse.xtext.resource.IDerivedStateComputer;
import org.emoflon.gips.eclipse.cplexLp.Variable;

import com.google.inject.Inject;

public class DynamicVariableDeclarationComputer implements IDerivedStateComputer {

	private final Set<String> alreadyLinked = new HashSet<>();

	@Inject
	private LinkingHelper linkingHelper;

	@Override
	public void installDerivedState(DerivedStateAwareResource resource, boolean preLinkingPhase) {
		resource.getAllContents().forEachRemaining(this::updateQualifiedName);
	}

	@Override
	public void discardDerivedState(DerivedStateAwareResource resource) {
		resource.getAllContents().forEachRemaining(this::removeQualifiedName);
		alreadyLinked.clear();
	}

	private boolean isObjectRelevant(EObject obj) {
		return !obj.eIsProxy() && obj instanceof Variable;
	}

	private void updateQualifiedName(EObject eObject) {
		if (!isObjectRelevant(eObject))
			return;

		String crossRef = linkingHelper.getCrossRefNodeAsString(NodeModelUtils.getNode(eObject), true);
		if (!alreadyLinked.contains(crossRef)) {
			((Variable) eObject).setName(crossRef);
			alreadyLinked.add(crossRef);
		}
	}

	private void removeQualifiedName(EObject eObject) {
		if (!isObjectRelevant(eObject))
			return;

		((Variable) eObject).setName(null);
	}

	public void setLinkingHelper(LinkingHelper linkingHelper) {
		this.linkingHelper = Objects.requireNonNull(linkingHelper, "linkingHelper");
	}

}
