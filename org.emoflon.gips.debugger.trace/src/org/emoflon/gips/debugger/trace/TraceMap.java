package org.emoflon.gips.debugger.trace;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.emoflon.gips.debugger.trace.resolver.ResolveElement2Id;

/**
 * This class is used to record/map a transformation between two models. Its
 * implementation is based on {@link HashMap}s and {@link HashSet}s.
 * 
 * <p>
 * <strong>This implementation is not synchronized</strong>
 * 
 * @param <S> the (general) type of source elements
 * @param <T> the (general) type of target elements
 */
public class TraceMap<S, T> {

	/**
	 * This method can be used to reduce two {@link TraceMap} into a new one. The
	 * new {@link TraceMap} consist only of source elements from {@code map1} and
	 * target elements from {@code map2}.
	 * 
	 * <p>
	 * A source element of {@code map1} is only included if at least one of its
	 * associated target elements is a source element of {@code map2} with a
	 * non-null target element.
	 * 
	 * @param <S>  type of source elements of {@code map1}
	 * @param <I>  type of intermediate elements (target of {@code map1} and source
	 *             of {@code map2})
	 * @param <T>  type of target elements of {@code map2}
	 * @param map1 start
	 * @param map2 end
	 * @return a new {@link TraceMap} which directly maps source elements from
	 *         {@code map1} to target elements from {@code map2}
	 */
	public static <S, I, T> TraceMap<S, T> condense(final TraceMap<S, I> map1, final TraceMap<? super I, T> map2) {

		final var compressedMapping = new TraceMap<S, T>();
		final var exitPoints = new HashSet<T>();

		for (final var entryPoint : map1.getAllSources()) {
			exitPoints.clear();

			final var intermediates = map1.getTargets(entryPoint);
			for (final var intermediate : intermediates) {
				final var result = map2.getTargets(intermediate);
				exitPoints.addAll(result);
			}

			compressedMapping.mapOneToMany(entryPoint, exitPoints);
		}

		return compressedMapping;
	}

	/**
	 * This method merges two {@link TraceMap}s into a new one.
	 * 
	 * @param <S>
	 * @param <T>
	 * @param map1
	 * @param map2
	 * @return a new {@link TraceMap}
	 */
	public static <S, T> TraceMap<S, T> merge(TraceMap<S, T> map1, TraceMap<S, ? extends T> map2) {
		final var merged = new TraceMap<S, T>();
		final var exitPoints = new HashSet<T>();

		for (var entryPoint : map1.getAllSources()) {
			exitPoints.clear();

			var exitPointsMap1 = map1.getTargets(entryPoint);
			exitPoints.addAll(exitPointsMap1);

			var exitPointsMap2 = map2.getTargets(entryPoint);
			exitPoints.addAll(exitPointsMap2);

			merged.mapOneToMany(entryPoint, exitPoints);
		}

		for (var entryPoint : map2.getAllSources()) {
			if (map1.hasSource(entryPoint)) {
				continue;
			}

			var exitPoints2 = map2.getTargets(entryPoint);
			merged.mapOneToMany(entryPoint, exitPoints2);
		}

		return merged;
	}

	public static <S, T> TraceMap<String, String> normalize(final TraceMap<S, T> map,
			final ResolveElement2Id<? super S> srcResolver, final ResolveElement2Id<? super T> dstResolver) {

		final var newMap = new TraceMap<String, String>();

		for (final var source : map.getAllSources()) {
			final String srcId = srcResolver.resolve(source);
			if (srcId == null) {
				throw new NullPointerException(); // TODO
			}

			for (final var target : map.getTargets(source)) {
				final String dstId = dstResolver.resolve(target);
				if (dstId == null) {
					throw new NullPointerException(); // TODO
				}
				newMap.map(srcId, dstId);
			}
		}

		return newMap;
	}

	private final Map<S, Set<T>> forward = new HashMap<>();
	private final Map<T, Set<S>> backward = new HashMap<>();
	private S sourceDefault;

	public void setDefaultSource(final S source) {
		this.sourceDefault = source;
	}

	public void clearDefaultSource() {
		this.sourceDefault = null;
	}

	/**
	 * Maps a single element {@code source} to a single element {@code target}.
	 * 
	 * @param source start element of a transformation
	 * @param target end element of a transformation
	 */
	public void map(final S source, final T target) {
		forward.computeIfAbsent(source, key -> new HashSet<T>()).add(target);
		backward.computeIfAbsent(target, key -> new HashSet<S>()).add(source);
	}

	/**
	 * Maps a single element {@code source} to one or many elements {@code targets}.
	 * 
	 * @param source
	 * @param targets
	 */
	public void mapOneToMany(final S source, final Collection<? extends T> targets) {
		for (var target : targets) {
			map(source, target);
		}
	}

	public void mapManyToOne(final Collection<S> sources, final T target) {
		for (var source : sources) {
			map(source, target);
		}
	}

	public void mapManyToMany(final Collection<S> sources, final Collection<T> targets) {
		for (var source : sources) {
			for (var target : targets) {
				map(source, target);
			}
		}
	}

	public void mapWithDefaultSource(T target) {
		if (this.sourceDefault == null) {
			throw new IllegalStateException("Default source not set");
		}
		map(this.sourceDefault, target);
	}

	public void removeMapping(S source, T target) {
		var forwardMapping = forward.get(source);
		if (forwardMapping != null) {
			forwardMapping.remove(target);
		}

		var backwardMapping = backward.get(target);
		if (backwardMapping != null) {
			backwardMapping.remove(source);
		}
	}

	public Set<S> getSources(final T target) {
		final Set<S> sources = backward.get(target);
		if (sources == null) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(sources);
	}

	public Set<T> getTargets(final S source) {
		final Set<T> targets = forward.get(source);
		if (targets == null) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(targets);
	}

	public boolean hasSource(final S source) {
		return getTargets(source).isEmpty();
	}

	public boolean hasTarget(final T target) {
		return getSources(target).isEmpty();
	}

	public Set<S> getAllSources() {
		return Collections.unmodifiableSet(forward.keySet());
	}

	public Set<T> getAllTargets() {
		return Collections.unmodifiableSet(backward.keySet());
	}

	public void clear() {
		forward.clear();
		backward.clear();
		sourceDefault = null;
	}

	@Override
	public TraceMap<S, T> clone() {
		final var copy = new TraceMap<S, T>();
		for (var key : forward.keySet()) {
			copy.mapOneToMany(key, forward.get(key));
		}
		return copy;
	}

}