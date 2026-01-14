package org.emoflon.gips.core.util;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

public class StreamUtils {

	public static <T> Stream<T> toStream(final Collection<T> collection, boolean parallel) {
		Objects.requireNonNull(collection);
		return parallel ? collection.parallelStream() : collection.stream();
	}

}
