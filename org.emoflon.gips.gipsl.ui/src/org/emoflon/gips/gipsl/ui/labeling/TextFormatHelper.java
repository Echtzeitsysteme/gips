package org.emoflon.gips.gipsl.ui.labeling;

public abstract class TextFormatHelper {
	private TextFormatHelper() {

	}

	public static String removeQuotationMarksAtStartAndEnd(String in) {
		if (in == null)
			return null;

		if (in.charAt(0) == '"' && in.charAt(in.length() - 1) == '"')
			return in.substring(1, in.length() - 2);

		return in;
	}

	public static String addQuotationMarksAtStartAndEnd(String in) {
		if (in == null)
			return null;

		if (in.charAt(0) == '"' && in.charAt(in.length() - 1) == '"')
			return in;

		return '"' + in + '"';
	}
}
