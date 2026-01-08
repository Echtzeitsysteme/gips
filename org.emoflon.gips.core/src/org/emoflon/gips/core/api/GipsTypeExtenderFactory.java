package org.emoflon.gips.core.api;

import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsTypeExtender;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeExtension;
import org.emoflon.ibex.gt.api.GraphTransformationAPI;

public abstract class GipsTypeExtenderFactory<ENGINE extends GipsEngine, EMOFLON_API extends GraphTransformationAPI> {

	protected final ENGINE engine;
	protected final EMOFLON_API eMoflonApi;

	public GipsTypeExtenderFactory(final ENGINE engine, final EMOFLON_API eMoflonApi) {
		this.engine = engine;
		this.eMoflonApi = eMoflonApi;
	}

	public abstract GipsTypeExtender<?, ?> createTypeExtender(final TypeExtension typeExtension);

}
