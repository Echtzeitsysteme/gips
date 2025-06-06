package org.emoflon.gips.gipsl.ui.outline;

import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.SortOutlineContribution.DefaultComparator;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;

public class DefaultOutlineComparator extends DefaultComparator {

	@Override
	public int getCategory(IOutlineNode node) {
		if (node instanceof EObjectNode eNode)
			return eNode.getEClass().getClassifierID();

		return Integer.MIN_VALUE;
	}

}
