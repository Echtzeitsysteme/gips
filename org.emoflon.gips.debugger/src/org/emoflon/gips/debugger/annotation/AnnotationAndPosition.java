package org.emoflon.gips.debugger.annotation;

import java.util.Objects;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;

public final class AnnotationAndPosition {
	public final Annotation annotation;
	public final Position position;

	public AnnotationAndPosition(Annotation annotation, Position position) {
		this.annotation = Objects.requireNonNull(annotation, "annotation");
		this.position = Objects.requireNonNull(position, "position");
	}

	public Annotation getAnnotation() {
		return annotation;
	}

	public Position getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return "AnnotationAndPosition [annotation=" + annotation + ", position=" + position + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(annotation, position);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnnotationAndPosition other = (AnnotationAndPosition) obj;
		return Objects.equals(annotation, other.annotation) && Objects.equals(position, other.position);
	}
}