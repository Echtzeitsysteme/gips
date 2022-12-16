package org.emoflon.gips.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.Type;
import org.emoflon.ibex.gt.api.GraphTransformationAPI;

public class TypeIndexer {

	final protected GraphTransformationAPI eMoflonAPI;
	final protected GipsIntermediateModel gipsModel;
	protected TypeListener listener;
	final protected Map<EClass, Set<EClass>> class2subclass = Collections.synchronizedMap(new LinkedHashMap<>());
	final protected Map<EClass, Set<EObject>> index = Collections.synchronizedMap(new LinkedHashMap<>());
	final protected Map<String, EClass> typeByName = Collections.synchronizedMap(new LinkedHashMap<>());
	protected boolean cascadingNotifications = false;

	public TypeIndexer(final GraphTransformationAPI eMoflonAPI, final GipsIntermediateModel gipsModel) {
		this.eMoflonAPI = eMoflonAPI;
		this.gipsModel = gipsModel;
		initIndex();
		injectListener();
	}

	public void terminate() {
		listener.removeListener();
	}

	public Set<EObject> getObjectsOfType(final EClass type) {
		Set<EObject> query = Collections.synchronizedSet(new LinkedHashSet<>());
		query.addAll(index.get(type));
		if(!class2subclass.containsKey(type))
			return query;
		
		for(EClass cls : class2subclass.get(type)) {
			query.addAll(index.get(cls));
		}
		return query;
	}

	public Set<EObject> getObjectsOfType(final String type) {
		return getObjectsOfType(typeByName.get(type));
	}

	protected EObject putObject(final EClass type, final EObject object) {
		Set<EObject> elts = index.get(type);
		if (elts.add(object))
			return object;

		return null;
	}

	protected EObject removeObject(final EClass type, final EObject object) {
		Set<EObject> elts = index.get(type);
		if (elts.remove(object))
			return object;

		return null;
	}

	protected void resolveAdd(Notification notification) {
		EObject node = (EObject) notification.getNewValue();
		if (node == null) {
			return;
		}

		if (notification.getFeature() instanceof EReference eref && (eref.isContainer() || eref.isContainment())
				&& index.keySet().contains(eref.getEType())) {
			node = putObject(node.eClass(), node);
		}

		if (node != null && !cascadingNotifications)
			explore(node);

	}

	protected void resolveAddResource(Notification notification) {
		Resource r = (Resource) notification.getNewValue();
		r.getContents().forEach(node -> {
			if (index.keySet().contains(node.eClass()))
				node = putObject(node.eClass(), node);

			if (!cascadingNotifications)
				explore(node);
		});
	}

	protected void resolveAddMany(Notification notification) {
		@SuppressWarnings("unchecked")
		List<EObject> addedNodes = (List<EObject>) notification.getNewValue();
		addedNodes.parallelStream().forEach(node -> {
			if (index.keySet().contains(node.eClass()))
				node = putObject(node.eClass(), node);

			if (!cascadingNotifications)
				explore(node);
		});
	}

	protected void resolveSet(Notification notification) {
		if (notification instanceof Resource)
			return;

		if (notification.getFeature() != null && notification.getFeature() instanceof EReference eRef
				&& (eRef.isContainer() || eRef.isContainment())) {
			if (notification.getNewValue() != null) {
				EObject node = (EObject) notification.getNewValue();
				if (index.keySet().contains(node.eClass()))
					node = putObject(node.eClass(), node);

				if (node != null && !cascadingNotifications)
					explore(node);
			}

		}

	}

	protected void resolveRemoveAdapter(Notification notification) {
		EContentAdapter adapter = (EContentAdapter) notification.getOldValue();
		if (adapter == null)
			return;

		EObject node = (EObject) notification.getNotifier();
		if (index.keySet().contains(node.eClass())) {
			removeObject(node.eClass(), node);
		}
	}

	private void initIndex() {
		gipsModel.getVariables().stream().filter(var -> var instanceof Type).map(var -> (Type) var).forEach(type -> {
			index.put(type.getType(), Collections.synchronizedSet(new LinkedHashSet<>()));
			typeByName.put(type.getName(), type.getType());
			Set<EClass> subclasses = type.getType().getEPackage().getEClassifiers().parallelStream()
					.filter(cls -> (cls instanceof EClass))
					.map(cls -> (EClass)cls)
					.filter(cls -> !cls.equals(type.getType()))
					.filter(cls -> cls.getEAllSuperTypes().contains(type.getType()))
					.collect(Collectors.toSet());
			class2subclass.put(type.getType(), subclasses);
			subclasses.forEach(cls -> {
				index.put(cls, Collections.synchronizedSet(new LinkedHashSet<>()));
				typeByName.put(cls.getName(), cls);
			});
		});
		

		eMoflonAPI.getModel().getResources().parallelStream().filter(r -> !r.getURI().toString().contains("trash.xmi"))
				.forEach(r -> {
					r.getContents().forEach(node -> {
						if (index.keySet().contains(node.eClass()))
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
		if (rootObj == null)
			return;

		Queue<EObject> frontier = getFrontier(rootObj);

		while (!frontier.isEmpty()) {
			frontier = frontier.parallelStream().flatMap(child -> {
				if (index.keySet().contains(child.eClass())) {
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
			if (ref.getUpperBound() == 1) {
				frontier.add((EObject) current.eGet(ref));
			} else {
				frontier.addAll((Collection<? extends EObject>) current.eGet(ref));
			}

		});
		return frontier;
	}

}
