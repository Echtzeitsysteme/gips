package org.emoflon.gips.eclipse.trace;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for converting {@link TraceGraph TraceGraphs} to
 * {@link TraceModelPackage Ecore trace models}
 * 
 * @see TransformGraph2Ecore
 */
public final class TransformGraph2Ecore {
	private TransformGraph2Ecore() {

	}

	public static Root buildModelFromGraph(TraceGraph graph) {
		var helper = new Helper();
		helper.add(graph);
		return helper.get();
	}

	private static final class Helper {
		private final TraceModelFactory factory = TraceModelFactory.eINSTANCE;
		private final Map<String, ModelReference> models = new HashMap<>();
		private final Root root = factory.createRoot();

		public void add(TraceGraph graph) {

			for (var gSrcRef : graph.getAllModelReferenceIds()) {
				var mSrcRef = getOrCreateModel(graph.getModelReference(gSrcRef, false));

				for (var gDstRef : graph.getTargetModelIds(gSrcRef)) {
					var mDstRef = getOrCreateModel(graph.getModelReference(gDstRef, false));

					var gModelLink = graph.getLink(gSrcRef, gDstRef);
					var mModelLink = getOrCreateModelTransformation(mSrcRef, mDstRef);

					for (var gSrcNode : gModelLink.getSourceNodeIds()) {
						var mSrcNode = getOrCreateElement(mSrcRef, gSrcNode);

						for (var gDstNode : gModelLink.resolveFromSrcToDst(gSrcNode)) {
							var mDstNode = getOrCreateElement(mDstRef, gDstNode);

							var mNodeLink = factory.createElementTransformation();
							mNodeLink.setRoot(mModelLink);
							mNodeLink.setSource(mSrcNode);
							mNodeLink.setTarget(mDstNode);
						}
					}
				}
			}
		}

		public Root get() {
			return root;
		}

		private ModelReference getOrCreateModel(TraceModelReference gRef) {
			var mRef = models.computeIfAbsent(gRef.getModelId(), key -> {
				var modelRef = factory.createModelReference();
				modelRef.setModelId(key);
				root.getModels().add(modelRef);
				return modelRef;
			});
			return mRef;
		}

		private ModelTransformation getOrCreateModelTransformation(ModelReference mSrcRef, ModelReference mDstRef) {
			for (var mt : mSrcRef.getTransformsTo()) {
				if (mt.getTarget() == mDstRef) {
					return mt;
				}
			}

			var modelTransformation = factory.createModelTransformation();
			modelTransformation.setSource(mSrcRef);
			modelTransformation.setTarget(mDstRef);
			return modelTransformation;
		}

		private ElementReference getOrCreateElement(ModelReference model, String elementId) {
			for (var element : model.getElements()) {
				if (element.getElementId().equals(elementId)) {
					return element;
				}
			}

			var element = factory.createElementReference();
			element.setElementId(elementId);
			element.setRoot(model);
			return element;
		}

	}

}
