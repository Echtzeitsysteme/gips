package org.emoflon.gips.eclipse.trace;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.emoflon.gips.eclipse.trace.resolver.ResolveElement2Id;

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
public class TraceMap<S, T> implements Serializable {

	private static final long serialVersionUID = 7587509919729026599L;

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

		synchronized (map1) {
			synchronized (map2) {
				for (final var entryPoint : map1.getAllSources()) {
					exitPoints.clear();

					final var intermediates = map1.getTargets(entryPoint);
					for (final var intermediate : intermediates) {
						final var result = map2.getTargets(intermediate);
						exitPoints.addAll(result);
					}

					compressedMapping.mapOneToMany(entryPoint, exitPoints);
				}
			}
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

		synchronized (map1) {
			synchronized (map2) {
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
			}
		}

		return merged;
	}

	public static <S, T> TraceMap<String, String> normalize(final TraceMap<S, T> map,
			final ResolveElement2Id<? super S> srcResolver, final ResolveElement2Id<? super T> dstResolver) {

		final var newMap = new TraceMap<String, String>();

		synchronized (map) {
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
		}

		return newMap;
	}

	private final Map<S, Set<T>> forward = new ConcurrentHashMap<>();
	private final Map<T, Set<S>> backward = new ConcurrentHashMap<>();
	private S sourceDefault;

	public void setDefaultSource(final S source) {
		this.sourceDefault = Objects.requireNonNull(source, "source");
	}

	public synchronized void clearDefaultSource() {
		this.sourceDefault = null;
	}

	/**
	 * Maps a single element {@code source} to a single element {@code target}.
	 * 
	 * @param source start element of a transformation
	 * @param target end element of a transformation
	 */
	public synchronized void map(final S source, final T target) {
		forward.computeIfAbsent(Objects.requireNonNull(source, "source"), key -> ConcurrentHashMap.newKeySet())
				.add(target);
		backward.computeIfAbsent(Objects.requireNonNull(target, "target"), key -> ConcurrentHashMap.newKeySet())
				.add(source);
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
		var source = Objects.requireNonNull(this.sourceDefault, "Default source not set");
		map(source, target);
	}

	public synchronized void removeMapping(S source, T target) {
		var forwardMapping = forward.get(source);
		if (forwardMapping != null) {
			forwardMapping.remove(target);
		}

		var backwardMapping = backward.get(target);
		if (backwardMapping != null) {
			backwardMapping.remove(source);
		}
	}

	/**
	 * Returns an unmodifiable view of sources for the given target. Any changes to
	 * this map may be reflected in the returned collection.
	 * 
	 * @return
	 */
	public Set<S> getSources(final T target) {
		final Set<S> sources = backward.get(target);
		if (sources == null) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(sources);
	}

	/**
	 * Returns an unmodifiable view of targets for the given source. Any changes to
	 * this map may be reflected in the returned collection.
	 * 
	 * @return
	 */
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

	/**
	 * Returns an unmodifiable view of all sources within this map. Any changes to
	 * this map will be reflected directly in the returned collection.
	 * 
	 * @return
	 */
	public Set<S> getAllSources() {
		return Collections.unmodifiableSet(forward.keySet());
	}

	/**
	 * Returns an unmodifiable view of all targets within this map. Any changes to
	 * this map will be reflected directly in the returned collection.
	 * 
	 * @return
	 */
	public Set<T> getAllTargets() {
		return Collections.unmodifiableSet(backward.keySet());
	}

	public synchronized void clear() {
		forward.clear();
		backward.clear();
		clearDefaultSource();
	}

	@Override
	public synchronized TraceMap<S, T> clone() {
		final var copy = new TraceMap<S, T>();
		for (var key : forward.keySet()) {
			copy.mapOneToMany(key, forward.get(key));
		}
		return copy;
	}

}
