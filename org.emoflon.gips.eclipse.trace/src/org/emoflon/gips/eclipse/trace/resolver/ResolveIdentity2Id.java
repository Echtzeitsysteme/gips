package org.emoflon.gips.eclipse.trace.resolver;

public final class ResolveIdentity2Id implements ResolveElement2Id<String> {

	public final static ResolveIdentity2Id INSTANCE = new ResolveIdentity2Id();

	@Override
	public String resolve(final String object) {
		return object.toString();
	}

}
