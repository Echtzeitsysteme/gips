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
}
