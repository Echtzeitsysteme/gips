package org.emoflon.gips.debugger.ui.highlight;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.emoflon.gips.debugger.ui.highlight.ClplexLpHighlightingCalculator.ClplexLpHighlightingStyles;

public class ClplexLpHighlightingConfiguration extends DefaultHighlightingConfiguration {

	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		// externalize
		acceptor.acceptDefaultHighlighting(ClplexLpHighlightingStyles.KEYWORD_CONSTRAINT, "Constraints",
				constraintTextStyle());
		super.configure(acceptor);
	}

	public TextStyle constraintTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 141, 137));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

}
