package org.emoflon.gips.debugger.api;

import java.util.Objects;

public final class ILPTraceKeywords {
	private ILPTraceKeywords() {

	}

	public static final String TYPE_VALUE_DELIMITER = "::";
	public static final String TYPE_CONSTRAINT = "constraint";
	public static final String TYPE_MAPPING = "mapping";
	public static final String TYPE_CONSTRAINT_VAR = "constraint-var";
	public static final String TYPE_FUNCTION = "function";
	public static final String TYPE_FUNCTION_VAR = "function-var";
	public static final String TYPE_OBJECTIVE = "objective";
	public static final String TYPE_VARIABLE = "variable";

	public static String buildElementId(String type, String elementName) {
		Objects.requireNonNull(type);
		elementName = elementName != null ? elementName : "";
		return type + TYPE_VALUE_DELIMITER + elementName;
	}

	public static record TypeValuePair(String type, String value) {

		@Override
		public String toString() {
			return getElementId();
		}

		public String getElementId() {
			return buildElementId(type, value);
		}
	}

	public static TypeValuePair getTypeAndValue(String text) {
		String type = null;
		String value = null;

		var delimiter = text.indexOf(ILPTraceKeywords.TYPE_VALUE_DELIMITER);
		if (delimiter < 0) {
			type = "";
			value = text;
		} else {
			type = text.substring(0, delimiter);
			value = text.substring(delimiter + ILPTraceKeywords.TYPE_VALUE_DELIMITER.length());
		}

		return new TypeValuePair(type, value);
	}
}
