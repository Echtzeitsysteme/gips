/*
 * generated by Xtext 2.25.0
 */
package org.emoflon.gips.gipsl.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

/**
 * Provides labels for EObjects.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#label-provider
 */
public class GipslLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	public GipslLabelProvider(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	// Labels and icons can be computed like this:

//	String text(Greeting ele) {
//		return "A greeting to " + ele.getName();
//	}
//
//	String image(Greeting ele) {
//		return "Greeting.gif";
//	}
}