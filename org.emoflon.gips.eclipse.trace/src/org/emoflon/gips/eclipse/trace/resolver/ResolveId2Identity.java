package org.emoflon.gips.eclipse.trace.resolver;

public final class ResolveId2Identity implements ResolveId2Element<String> {
	public static final ResolveId2Identity INSTANCE = new ResolveId2Identity();

	@Override
	public String resolve(String id) {
		return id;
	}
}
