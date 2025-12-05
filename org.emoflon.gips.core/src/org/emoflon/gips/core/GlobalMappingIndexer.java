package org.emoflon.gips.core;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class holds all instances of the class `MappingIndexer` used by the GIPS
 * framework.
 */
public class GlobalMappingIndexer {

	/**
	 * (Singleton) Instance of this class.
	 */
	private static GlobalMappingIndexer instance;

	/**
	 * Private constructor to only allow the creation of one global mapping indexer
	 * instance.
	 */
	private GlobalMappingIndexer() {
	}

	/**
	 * Returns the singleton instance of this class. If there is no instance, a new
	 * one will be created.
	 * 
	 * @return Singleton instance of this class.
	 */
	public static synchronized GlobalMappingIndexer getInstance() {
		if (instance == null) {
			instance = new GlobalMappingIndexer();
		}
		return instance;
	}

	/**
	 * Internal data structure (map) that maps an instance of a `GipsMapper` to a
	 * single mapping indexer instance.
	 */
	private final Map<GipsMapper<?>, MappingIndexer> mapper2indexer = Collections
			.synchronizedMap(new LinkedHashMap<>());

	/**
	 * Terminate the global mapping indexer, i.e., terminate all instance of
	 * `MappingIndexer` and, therefore, clear all lookup data structure.
	 */
	public void terminate() {
		mapper2indexer.forEach((mapper, indexer) -> {
			indexer.terminate();
		});
		mapper2indexer.clear();
	}

	/**
	 * Returns the single `MappingIndexer` instance that corresponds to the given
	 * `GipsMapper`.
	 * 
	 * @param mapper `GipsMapper` to return the single `MappingIndexer` instance
	 *               for.
	 * @return Single instance of `MappingIndexer` that corresponds to the given
	 *         `GipsMapper`.
	 */
	public MappingIndexer getIndexer(final GipsMapper<?> mapper) {
		Objects.requireNonNull(mapper);
		return mapper2indexer.get(mapper);
	}

	/**
	 * Create a new instance of the class `MappingIndexer` corresponding to the
	 * given `GipsMapper` if and only if there is not only a respective instance of
	 * `MappingIndexer` registered for the given `GipsMapper`.
	 * 
	 * @param mapper `GipsMapper` to create a new `MappingIndexer` for.
	 */
	public synchronized void createIndexer(final GipsMapper<?> mapper) {
		Objects.requireNonNull(mapper);
		if (!mapper2indexer.containsKey(mapper)) {
			mapper2indexer.put(mapper, new MappingIndexer());
		}
	}

}
