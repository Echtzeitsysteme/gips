package org.emoflon.gips.gipsl.formatting2;

import org.eclipse.xtext.formatting2.internal.SinglelineDocCommentReplacer;
import org.eclipse.xtext.formatting2.internal.WhitespaceReplacer;
import org.eclipse.xtext.formatting2.regionaccess.IComment;

public class GipslSinglelineDocCommentReplacer extends SinglelineDocCommentReplacer {

	public GipslSinglelineDocCommentReplacer(IComment comment, String prefix) {
		super(comment, prefix);
	}

	@Override
	public void configureWhitespace(WhitespaceReplacer leading, WhitespaceReplacer trailing) {
		leading.getFormatting().setNewLinesMin(1);
		leading.getFormatting().setNewLinesMax(2);
		leading.getFormatting().setNewLinesDefault(1);

		trailing.getFormatting().setNewLinesMin(1);
		trailing.getFormatting().setNewLinesMax(2);
		trailing.getFormatting().setNewLinesDefault(1);
	}

}
