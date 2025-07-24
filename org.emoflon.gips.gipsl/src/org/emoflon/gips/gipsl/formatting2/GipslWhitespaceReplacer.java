package org.emoflon.gips.gipsl.formatting2;

import org.eclipse.xtext.formatting2.IHiddenRegionFormatting;
import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.internal.WhitespaceReplacer;
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegion;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;

public class GipslWhitespaceReplacer extends WhitespaceReplacer {

	public GipslWhitespaceReplacer(ITextSegment whitespace, IHiddenRegionFormatting formatting) {
		super(whitespace, formatting);
	}

	@Override
	protected int computeNewLineCount(ITextReplacerContext context) {
		Integer newLineDefault = getFormatting().getNewLineDefault();
		Integer newLineMin = getFormatting().getNewLineMin();
		Integer newLineMax = getFormatting().getNewLineMax();
		if (newLineMin != null || newLineDefault != null || newLineMax != null) {
			if (getRegion() instanceof IHiddenRegion && ((IHiddenRegion) getRegion()).isUndefined()) {
				if (newLineDefault != null)
					return newLineDefault;
				if (newLineMin != null)
					return newLineMin;
				if (newLineMax != null)
					return newLineMax;
			} else {
				// FIX: The original method did not take into account any new lines before this
				// region when calculating the upper bound. This resulted in the disappearance
				// of new lines if the previous region had trailing new lines.
				int lineCount = getRegion().getLineCount() - 1 + trailingNewLinesOfPreviousRegion();
				if (newLineMin != null && newLineMin > lineCount)
					lineCount = newLineMin;
				if (newLineMax != null && newLineMax < lineCount)
					lineCount = newLineMax;
				return lineCount;
			}
		}
		return 0;
	}
}
