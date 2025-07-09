package org.emoflon.gips.gipsl.formatting2;

import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.internal.SinglelineCodeCommentReplacer;
import org.eclipse.xtext.formatting2.internal.WhitespaceReplacer;
import org.eclipse.xtext.formatting2.regionaccess.IComment;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;

public class GipslSinglelineCodeCommentReplacer extends SinglelineCodeCommentReplacer {

	public GipslSinglelineCodeCommentReplacer(IComment comment, String prefix) {
		super(comment, prefix);
	}

	@Override
	public ITextReplacerContext createReplacements(ITextReplacerContext context) {
		ITextSegment firstSpace = getFirstSpace();
		if (firstSpace != null) {
			if (hasEmptyBody())
				context.addReplacement(firstSpace.replaceWith(""));
			else
				context.addReplacement(firstSpace.replaceWith(" "));
		}
		return context;
	}

	@Override
	public void configureWhitespace(WhitespaceReplacer leading, WhitespaceReplacer trailing) {
		leading.getFormatting().setNoIndentation(true);
		leading.getFormatting().setNewLinesMin(1);
		leading.getFormatting().setNewLinesMax(2);
		leading.getFormatting().setNewLinesDefault(1);

		trailing.getFormatting().setNewLinesMin(1);
		trailing.getFormatting().setNewLinesMax(2);
		trailing.getFormatting().setNewLinesDefault(1);
	}

}
