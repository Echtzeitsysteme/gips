package org.emoflon.roam.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.emoflon.ibex.gt.api.GraphTransformationAPI;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.intermediate.RoamIntermediate.Type;

public class TypeIndexer {
	
	final protected GraphTransformationAPI eMoflonAPI;
	final protected RoamIntermediateModel roamModel;
	protected TypeListener listener;
	final protected Map<EClass, Set<EObject>> index = Collections.synchronizedMap(new HashMap<>());
	protected boolean cascadingNotifications = false;
	
	protected TypeIndexer(final GraphTransformationAPI eMoflonAPI, final RoamIntermediateModel roamModel) {
		this.eMoflonAPI = eMoflonAPI;
		this.roamModel = roamModel;
		initIndex();
		injectListener();
	}
	
	public void terminate() {
		listener.removeListener();
	}
	
	public Set<EObject> getObjectsOfType(final EClass type) {
		return index.get(type);
	}
	
	protected EObject putObject(final EClass type, final EObject object) {
		Set<EObject> elts = index.get(type);
		if(elts.add(object))
			return object;
		
		return null;
	}
	
	protected EObject removeObject(final EClass type, final EObject object) {
		Set<EObject> elts = index.get(type);
		if(elts.remove(object))
			return object;
		
		return null;
	}
	
	protected void resolveAdd(Notification notification) {
		EObject node = (EObject) notification.getNewValue();
		if(node == null) {
			return;
		}
		
		if(notification.getFeature() instanceof EReference eref && (eref.isContainer() || eref.isContainment()) && index.keySet().contains(eref.getEType())) {
			node = putObject(node.eClass(), node);
		}
		
		if(node != null && !cascadingNotifications)
			explore(node);
		
	}
	
	protected void resolveAddResource(Notification notification) {
		Resource r = (Resource) notification.getNewValue();
		r.getContents().forEach(node -> {
			if(index.keySet().contains(node.eClass()))
				node = putObject(node.eClass(), node);
			
			if(!cascadingNotifications)
				explore(node);
		});
	}

	protected void resolveAddMany(Notification notification) {
		@SuppressWarnings("unchecked")
		List<EObject> addedNodes = (List<EObject>)notification.getNewValue();
		addedNodes.parallelStream().forEach(node -> {
			if(index.keySet().contains(node.eClass()))
				node = putObject(node.eClass(), node);
			
			if(!cascadingNotifications)
				explore(node);
		});
	}
	
	protected void resolveSet(Notification notification) {
		if(notification instanceof Resource)
			return;
					
		if(notification.getFeature() != null && notification.getFeature() instanceof EReference eRef && (eRef.isContainer() || eRef.isContainment())) {
			if(notification.getNewValue() != null) {
				EObject node = (EObject) notification.getNewValue();
				if(index.keySet().contains(node.eClass()))
					node = putObject(node.eClass(), node);
				
				if(node != null && !cascadingNotifications)
					explore(node);
			}
			
		}
		
	}
	
	protected void resolveRemoveAdapter(Notification notification) {
		EContentAdapter adapter = (EContentAdapter)notification.getOldValue();
		if(adapter == null)
			return;
		
		EObject node = (EObject) notification.getNotifier();
		if(index.keySet().contains(node.eClass())) {
			removeObject(node.eClass(), node);
		}
	}
	
	private void initIndex() {
		roamModel.getVariables().stream()
			.filter(var -> var instanceof Type)
			.map(var -> (Type) var)
			.forEach(type -> {
				index.put(type.getType(), Collections.synchronizedSet(new HashSet<>()));
			});
		
		eMoflonAPI.getModel().getResources().parallelStream()
		.filter(r -> !r.getURI().toString().contains("trash.xmi"))
		.forEach(r -> {
			r.getContents().forEach(node -> {
				if(index.keySet().contains(node.eClass()))
					node = putObject(node.eClass(), node);

				explore(node);
			});
		});
	}
	
	private void injectListener() {
		listener = new TypeListener(this, eMoflonAPI.getModel());
		listener.injectListener();
	}
	
	private void explore(EObject rootObj) {
		if(rootObj == null) 
			return;
		
		Queue<EObject> frontier = getFrontier(rootObj);
		
		while(!frontier.isEmpty()) {
			frontier = frontier.parallelStream().flatMap(child -> {
				if(index.keySet().contains(child.eClass())) {
					putObject(child.eClass(), child);
				}
				return getFrontier(child).parallelStream();
			}).collect(Collectors.toCollection(LinkedList::new));
		}
	}
	
	@SuppressWarnings("unchecked")
	private Queue<EObject> getFrontier(final EObject current) {
		Queue<EObject> frontier = new LinkedList<>();
		current.eClass().getEAllContainments().forEach(ref -> {
			if(ref.getUpperBound() == 1) {
				frontier.add((EObject) current.eGet(ref));
			} else {
				frontier.addAll((Collection<? extends EObject>) current.eGet(ref));
			}
			
		});
		return frontier;
	}

}
