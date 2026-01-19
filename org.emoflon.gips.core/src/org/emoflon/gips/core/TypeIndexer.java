package org.emoflon.gips.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;

public class TypeIndexer {

	final protected ResourceSet model;
	final protected GipsIntermediateModel gipsModel;
	protected TypeListener listener;
	final protected Map<EClass, Set<EClass>> class2subclass = Collections.synchronizedMap(new LinkedHashMap<>());
	final protected Map<EClass, Set<EClass>> class2superclass = Collections.synchronizedMap(new LinkedHashMap<>());
	final protected Map<EClass, Set<EObject>> index = Collections.synchronizedMap(new LinkedHashMap<>());
	final protected Map<String, EClass> typeByName = Collections.synchronizedMap(new LinkedHashMap<>());
	protected boolean cascadingNotifications = false;

	public TypeIndexer(final ResourceSet model, final GipsIntermediateModel gipsModel) {
		this.model = model;
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
		if (!class2subclass.containsKey(type))
			return query;

		for (EClass cls : class2subclass.get(type)) {
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
		Queue<EClass> pendingTypes = new ConcurrentLinkedQueue<EClass>(gipsModel.getRequiredTypes());
		Set<EClass> seenTypes = new HashSet<>(pendingTypes);
		while (!pendingTypes.isEmpty()) {
			EClass type = pendingTypes.poll();

			index.put(type, Collections.synchronizedSet(new LinkedHashSet<>()));
			typeByName.put(type.getName(), type);

			Set<EClass> allSubClasses = type.getEPackage().getEClassifiers().parallelStream() //
					.filter(e -> e instanceof EClass) //
					.map(e -> (EClass) e) //
					.filter(e -> !type.equals(e)) //
					.filter(e -> type.isSuperTypeOf(e)) //
					.collect(Collectors.toSet());

			Set<EClass> allSuperClasses = new LinkedHashSet<>(type.getEAllSuperTypes());

			class2subclass.put(type, allSubClasses);
			class2superclass.put(type, allSuperClasses);

			allSubClasses.parallelStream().filter(e -> !seenTypes.contains(e)).forEach(e -> pendingTypes.add(e));
			seenTypes.addAll(allSubClasses);

			allSuperClasses.parallelStream().filter(e -> !seenTypes.contains(e)).forEach(e -> pendingTypes.add(e));
			seenTypes.addAll(allSuperClasses);
		}

		model.getResources().parallelStream().filter(r -> !r.getURI().toString().contains("trash.xmi")).forEach(r -> {
			r.getContents().forEach(node -> {
				if (index.keySet().contains(node.eClass()))
					node = putObject(node.eClass(), node);

				explore(node);
			});
		});
	}

	private void injectListener() {
		listener = new TypeListener(this, model);
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
				// Check if EObject to add is null
				if (current.eGet(ref) != null) {
					frontier.add((EObject) current.eGet(ref));
				}
			} else {
				frontier.addAll((Collection<? extends EObject>) current.eGet(ref));
			}

		});
		return frontier;
	}

}
