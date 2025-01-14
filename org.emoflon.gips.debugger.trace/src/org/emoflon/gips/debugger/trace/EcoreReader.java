package org.emoflon.gips.debugger.trace;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * Helper class for loading XMI files from disk
 */
public final class EcoreReader {

	private final URI modelURI;
	private ResourceSetImpl resourceSet;

	public EcoreReader(URI modelURI) {
		this.modelURI = Objects.requireNonNull(modelURI);
		initializeResourceSet();
	}

	public EcoreReader(IPath path) {
		if (path.isAbsolute()) {
			this.modelURI = URI.createFileURI(path.toString());
		} else {
			this.modelURI = URI.createPlatformResourceURI(path.toString(), true);
		}
		initializeResourceSet();
	}

	private void initializeResourceSet() {
		this.resourceSet = new ResourceSetImpl();
//		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(TraceModelPackage.eINSTANCE.getNsURI(), TraceModelPackage.eINSTANCE);

	}

	public boolean doesFileExist() {
		return resourceSet.getURIConverter().exists(modelURI, null);
	}

	public long getFileTimeStamp() {
		var requestedAttributes = Set.of(URIConverter.ATTRIBUTE_TIME_STAMP);
		var options = Collections.singletonMap(URIConverter.OPTION_REQUESTED_ATTRIBUTES, requestedAttributes);
		var attributes = resourceSet.getURIConverter().getAttributes(modelURI, options);
		var timeStamp = (Long) attributes.get(URIConverter.ATTRIBUTE_TIME_STAMP);
		return timeStamp == null ? -1 : timeStamp;
	}

	public Root loadModel() {
		var resource = resourceSet.getResource(modelURI, true);
		return (Root) resource.getContents().get(0);
	}

}
