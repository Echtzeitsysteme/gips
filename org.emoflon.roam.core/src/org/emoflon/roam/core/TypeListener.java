package org.emoflon.roam.core;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;

public class TypeListener extends EContentAdapter {

	final protected TypeIndexer indexer;
	final protected ResourceSet rs;
	private boolean terminated = true;

	public TypeListener(final TypeIndexer indexer, final ResourceSet rs) {
		this.indexer = indexer;
		this.rs = rs;
	}
	
	public void injectListener() {
		terminated = false;
		for (Resource r : rs.getResources()) {
			if (r.getURI().toString().contains("trash.xmi")) {
				continue;
			}
			r.eAdapters().add(this);
		}
	}
	
	public void removeListener() {
		terminated = true;
		for (Resource r : rs.getResources()) {
			if (r.getURI().toString().contains("trash.xmi")) {
				continue;
			}
			r.eAdapters().remove(this);
		}
	}

	// This is a quick fix to prevent class cast exceptions if someone is not using "pure" emf
	@Override
	protected void removeAdapter(Notifier notifier, boolean checkContainer, boolean checkResource) {
		if (!(notifier instanceof InternalEObject)) {
			super.removeAdapter(notifier);
			return;
		}

		super.removeAdapter(notifier, checkContainer, checkResource);
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (terminated)
			return;

		super.notifyChanged(notification);
		
		switch(notification.getEventType()) {
			case Notification.ADD: {
				if(notification.getNewValue() instanceof Resource) {
					indexer.resolveAddResource(notification);
				}else {
					indexer.resolveAdd(notification);
				}
				break;
			}
			case Notification.REMOVE: {
				break;
			}
			case Notification.REMOVING_ADAPTER: {
				indexer.resolveRemoveAdapter(notification);
				break;
			}
			case Notification.RESOLVE: {
				break;
			}
			case Notification.SET: {
				indexer.resolveSet(notification);
				break;
			}
			case Notification.UNSET: {
				break;
			}
			case Notification.MOVE: {
				break;
			}
			case Notification.ADD_MANY: {
				indexer.resolveAddMany(notification);
				break;
			}
			case Notification.REMOVE_MANY: {
				break;
			}
			default: throw new RuntimeException("Notification type id("+notification.getEventType()+") not supported");
			
		}

	}

	@Override
	protected void unsetTarget(Resource target) {
		basicUnsetTarget(target);
		List<EObject> contents = target.getContents();
		for (EObject e : contents) {
			Notifier notifier = e;
			removeAdapter(notifier, true, false);
		}
	}
}
