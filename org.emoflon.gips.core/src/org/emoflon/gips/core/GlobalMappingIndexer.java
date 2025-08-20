package org.emoflon.gips.core;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class GlobalMappingIndexer {

	private static GlobalMappingIndexer instance;

	private GlobalMappingIndexer() {
	}

	public static synchronized GlobalMappingIndexer getInstance() {
		if (instance == null) {
			instance = new GlobalMappingIndexer();
		}
		return instance;
	}

	final protected Map<GipsMapper<?>, MappingIndexer> mapper2indexer = Collections
			.synchronizedMap(new LinkedHashMap<>());

	public void terminate() {
		mapper2indexer.forEach((mapper, indexer) -> {
			indexer.terminate();
		});
		mapper2indexer.clear();
	}

	public MappingIndexer getIndexer(final GipsMapper<?> mapper) {
		return mapper2indexer.get(mapper);
	}

	public void createIndexer(final GipsMapper<?> mapper) {
		if (!mapper2indexer.containsKey(mapper)) {
			mapper2indexer.put(mapper, new MappingIndexer());
		}
	}

}
