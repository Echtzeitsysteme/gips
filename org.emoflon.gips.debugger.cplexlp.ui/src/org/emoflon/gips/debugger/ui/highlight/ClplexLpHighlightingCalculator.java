package org.emoflon.gips.debugger.ui.highlight;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ide.editor.syntaxcoloring.HighlightingStyles;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.CancelIndicator;
import org.emoflon.gips.debugger.services.CplexLpGrammarAccess;

import com.google.inject.Inject;

public class ClplexLpHighlightingCalculator extends DefaultSemanticHighlightingCalculator {

	public static class ClplexLpHighlightingStyles implements HighlightingStyles {
		public static String KEYWORD_CONSTRAINT = "Constraint";
	}

	@Inject
	private CplexLpGrammarAccess grammarAccess;

	@Override
	protected boolean highlightElement(EObject object, IHighlightedPositionAcceptor acceptor,
			CancelIndicator cancelIndicator) {
		if (object instanceof org.emoflon.gips.debugger.cplexLp.ConstraintExpression) {
			Keyword colonKey = grammarAccess.getConstraintExpressionAccess().getColonKeyword_0_1();
			ICompositeNode textNode = NodeModelUtils.findActualNodeFor(object);
			for (ILeafNode node : textNode.getLeafNodes()) {
				if (colonKey == node.getGrammarElement()) {
					var start = textNode.getOffset();
					var length = node.getEndOffset() - start;
					acceptor.addPosition(start, length, ClplexLpHighlightingStyles.KEYWORD_CONSTRAINT);
				}
			}
		}
		return super.highlightElement(object, acceptor, cancelIndicator);
	}

}
