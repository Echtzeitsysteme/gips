package org.emoflon.gips.gipsl.formatting2;

import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.internal.SinglelineCodeCommentReplacer;
import org.eclipse.xtext.formatting2.internal.WhitespaceReplacer;
import org.eclipse.xtext.formatting2.regionaccess.IComment;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;
import org.eclipse.xtext.formatting2.regionaccess.internal.TextSegment;

public class GipslSinglelineCodeCommentReplacer extends SinglelineCodeCommentReplacer {

//	private Integer addIndentation;

	public GipslSinglelineCodeCommentReplacer(IComment comment, String prefix) {
		super(comment, prefix);
	}

	@Override
	public ITextReplacerContext createReplacements(ITextReplacerContext context) {
//		if (addIndentation != null) {
//			String indentation = context.getIndentationString(addIndentation);
//			context.addReplacement(getTextSegmentBeforePrefix().replaceWith(indentation));
//		}

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
		leading.getFormatting().setNewLinesDefault(0);
		leading.getFormatting().setNewLinesMin(0);
		leading.getFormatting().setNewLinesMax(2);

		trailing.getFormatting().setNewLinesDefault(1);
		trailing.getFormatting().setNewLinesMin(1);
		trailing.getFormatting().setNewLinesMax(2);

//		var leadingIndentation = leading.getFormatting().getIndentationDecrease();
//		var trailingIndentation = trailing.getFormatting().getIndentationIncrease();
//		this.addIndentation = trailingIndentation != null ? trailingIndentation : leadingIndentation;
	}

	protected ITextSegment getTextSegmentBeforePrefix() {
		IComment comment = getComment();
		return new TextSegment(comment.getTextRegionAccess(), comment.getOffset(), 0);
	}

}
