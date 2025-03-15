package org.emoflon.gips.eclipse.trace;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A helper class for finding possible paths between two models within the same
 * {@link TraceGraph graph}.
 */
public final class PathFinder {
	private PathFinder() {
	}

	/**
	 * A sequence of transformations leading from a source model to a destination
	 * model.
	 */
	public static record TracePath(List<TracePathLink> path, OverallDirection direction) {
	}

	public static record TracePathLink(String srcModelId, String dstModelId, Direction direction) {
	}

	/**
	 * This describes the overall direction of all transformations in a path.
	 * Possible values are:
	 * <ul>
	 * <li>{@link OverallDirection#Forward}
	 * <li>{@link OverallDirection#Backward}
	 * <li>{@link OverallDirection#Mixed}
	 * </ul>
	 */
	public static enum OverallDirection {
		/**
		 * The path consists only of forward transformations.
		 */
		Forward,

		/**
		 * The path consists only of backward transformations.
		 */
		Backward,

		/**
		 * The path contains forward and backward transformations.
		 */
		Mixed
	}

	/**
	 * This describes the direction of a single transformation. Possible values are:
	 * <ul>
	 * <li>{@link Direction#Forward}
	 * <li>{@link Direction#Backward}
	 * </ul>
	 */
	public static enum Direction {
		/**
		 * This is a forward transformation from src to dst.
		 */
		Forward,
		/**
		 * This is a backward transformation from dst to src.
		 */
		Backward
	}

	public static enum SearchDirection {
		/**
		 * This option considers forward transformations only.
		 */
		OnlyForward,
		/**
		 * This option considers backward transformations only.
		 */
		OnlyBackward,

		AllDirections
	}

	private static final class Helper {

		private final TraceGraph graph;
		private final String srcModelId;
		private final String dstModelId;
		private final SearchDirection searchDirection;
		private final LinkedList<TracePathLink> currentPath = new LinkedList<>();
		private final Set<String> alreadySeen = new HashSet<>();
		private final Set<List<TracePathLink>> allPaths = new HashSet<>();

		public Helper(TraceGraph graph, String srcModelId, String dstModelId, SearchDirection searchDirection) {
			this.graph = Objects.requireNonNull(graph, "graph");
			this.srcModelId = Objects.requireNonNull(srcModelId, "srcModelId");
			this.dstModelId = Objects.requireNonNull(dstModelId, "dstModelId");
			this.searchDirection = searchDirection;

			if (this.srcModelId.equals(dstModelId)) {
				throw new IllegalArgumentException("Unable to compute circular paths");
			}
		}

		public Set<TracePath> findPaths() {
			alreadySeen.add(srcModelId);
			searchNext(srcModelId);

			Set<TracePath> results = new HashSet<>();
			for (List<TracePathLink> path : allPaths) {
				switch (searchDirection) {
				case OnlyForward:
					results.add(new TracePath(path, OverallDirection.Forward));
					break;
				case OnlyBackward:
					results.add(new TracePath(path, OverallDirection.Backward));
					break;
				default:
					var direction = path.get(0).direction;
					var overallDirection = direction == Direction.Forward ? OverallDirection.Forward
							: OverallDirection.Backward;

					for (var pathLink : path) {
						if (direction != pathLink.direction) {
							overallDirection = OverallDirection.Mixed;
							break;
						}
					}

					results.add(new TracePath(path, overallDirection));
				}
			}
			return results;
		}

		private void searchNext(String currentModelId) {
			switch (searchDirection) {
			case OnlyForward:
				searchNext(currentModelId, Direction.Forward);
				break;
			case OnlyBackward:
				searchNext(currentModelId, Direction.Backward);
				break;
			case AllDirections:
				searchNext(currentModelId, Direction.Forward);
				searchNext(currentModelId, Direction.Backward);
				break;
			}
		}

		private void searchNext(String currentModelId, Direction direction) {
			Set<String> nextModelIds = switch (direction) {
			case Forward -> graph.getTargetModelIds(currentModelId);
			case Backward -> graph.getSourceModelIds(currentModelId);
			default -> throw new IllegalArgumentException("Unexpected value: " + direction);
			};

			for (var nextModelId : nextModelIds) {
				if (alreadySeen.contains(nextModelId)) {
					continue; // circles not allowed
				}

				alreadySeen.add(nextModelId);
				switch (direction) {
				case Forward:
					currentPath.add(new TracePathLink(currentModelId, nextModelId, direction));
					break;
				case Backward:
					currentPath.add(new TracePathLink(nextModelId, currentModelId, direction));
					break;
				}

				if (nextModelId.equals(dstModelId)) {
					allPaths.add(new ArrayList<>(currentPath));
				} else {
					searchNext(nextModelId);
				}
				alreadySeen.remove(nextModelId);
				currentPath.removeLast();
			}
		}
	}

	/**
	 * This method returns all paths from the source model to the destination model.
	 * For {@link SearchDirection#OnlyForward}, each path will only consist of
	 * forward transformations only. Otherwise, a path can contain both forward and
	 * backward transformations.
	 *
	 * @param graph
	 * @param srcModelId      all paths start here
	 * @param dstModelId      all paths end here
	 * @param searchDirection search direction
	 * @return
	 */
	public static Set<TracePath> computePaths(TraceGraph graph, String srcModelId, String dstModelId,
			SearchDirection searchDirection) {
		final var helper = new Helper(graph, srcModelId, dstModelId, searchDirection);
		return helper.findPaths();
	}

}
