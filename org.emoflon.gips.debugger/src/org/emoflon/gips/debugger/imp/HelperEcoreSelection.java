package org.emoflon.gips.debugger.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.XtextEditor;

public final class HelperEcoreSelection {
	private HelperEcoreSelection() {

	}

	public static List<EObject> selectionToEObjects(XtextEditor editor) {
		return selectionToEObjects(editor, editor.getSelectionProvider().getSelection());
	}

	public static List<EObject> selectionToEObjects(XtextEditor editor, ISelection selection) {
		if (selection.isEmpty()) {
			return Collections.emptyList();
		}

		if (selection instanceof IStructuredSelection structuredSelection) {
			var selectedElements = structuredSelection.toList();
			var result = new ArrayList<EObject>(selectedElements.size());

			for (var element : selectedElements) {
				if (element instanceof EObject eElement) {
					result.add(eElement);
				}
			}
			return result;
		}

		if (selection instanceof ITextSelection textSelection) {
			var selectedObject = editor.getDocument().readOnly(resource -> {
				var parseResult = resource.getParseResult();
				var rootNode = parseResult.getRootNode();
				var currentNode = NodeModelUtils.findLeafNodeAtOffset(rootNode, textSelection.getOffset());
				var eObject = NodeModelUtils.findActualSemanticObjectFor(currentNode);
				return eObject;
			});

			if (selectedObject == null) {
				return Collections.emptyList();
			}
			return Collections.singletonList(selectedObject);
		}

		return Collections.emptyList();
	}

}
