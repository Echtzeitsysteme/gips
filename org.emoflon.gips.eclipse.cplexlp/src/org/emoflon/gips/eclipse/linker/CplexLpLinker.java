package org.emoflon.gips.eclipse.linker;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.linking.ILinkingService;
import org.eclipse.xtext.linking.impl.DefaultLinkingService;
import org.eclipse.xtext.nodemodel.INode;

public class CplexLpLinker extends DefaultLinkingService implements ILinkingService {

	public static String TMP_RESOURCE = "CplexLP Dummy Resource";

	private Resource getTmpResource(EObject context) {
		var uri = URI.createURI(TMP_RESOURCE);
		var rs = context.eResource().getResourceSet();
		var resource = rs.getResource(uri, false);
		if (resource == null) {
			resource = rs.createResource(uri);
		}
		return resource;
	}

	@Override
	public List<EObject> getLinkedObjects(EObject context, EReference ref, INode node) {
		var result = super.getLinkedObjects(context, ref, node);
//		if (result.isEmpty() && context instanceof Variable && ref == CplexLpPackage.Literals.VARIABLE_REF
//				&& ((Variable) context).getName() == null) {
//			var baseVariable = CplexLpFactory.eINSTANCE.createVariableDecleration();
//			baseVariable.setName(node.getText().trim());
//
//			var tmpResource = getTmpResource(context);
//			tmpResource.getContents().add(baseVariable);
//			result = Collections.singletonList(baseVariable);
//		}
		return result;
	}

}
