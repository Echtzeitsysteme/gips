package org.emoflon.gips.debugger.marker;

public final class AnnotationMarkerData {

	public int offset;
	public int length;

	public String comment;

	public AnnotationMarkerData(int offset, int length) {
		this(offset, length, null);
	}

	public AnnotationMarkerData(int offset, int length, String comment) {
		this.offset = offset;
		this.length = length;
		this.comment = comment;
	}

}