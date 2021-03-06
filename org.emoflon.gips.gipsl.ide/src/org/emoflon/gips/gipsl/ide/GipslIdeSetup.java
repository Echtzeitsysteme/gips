/*
 * generated by Xtext 2.25.0
 */
package org.emoflon.gips.gipsl.ide;

import org.eclipse.xtext.util.Modules2;
import org.emoflon.gips.gipsl.GipslRuntimeModule;
import org.emoflon.gips.gipsl.GipslStandaloneSetup;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class GipslIdeSetup extends GipslStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new GipslRuntimeModule(), new GipslIdeModule()));
	}

}
