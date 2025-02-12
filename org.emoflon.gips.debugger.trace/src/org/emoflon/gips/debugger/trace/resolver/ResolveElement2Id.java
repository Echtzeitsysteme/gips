package org.emoflon.gips.debugger.trace.resolver;

/**
 * A general purpose interface to handle the mapping of a given element of type T to a unique id.
 * For every instance of this interface, there needs to be a matching instance of {@link ResolveId2Element}, which can map the id back to the (functionally) same element.
 */
public interface ResolveElement2Id<T> {

	String resolve(T object);

}
