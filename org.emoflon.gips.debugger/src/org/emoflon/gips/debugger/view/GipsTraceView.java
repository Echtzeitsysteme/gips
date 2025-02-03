package org.emoflon.gips.debugger.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.emoflon.gips.debugger.api.ITraceContext;
import org.emoflon.gips.debugger.api.ITraceManager;
import org.emoflon.gips.debugger.api.ITraceUpdateListener;
import org.emoflon.gips.debugger.api.event.ITraceManagerListener;
import org.emoflon.gips.debugger.api.event.TraceManagerEvent;

public class GipsTraceView extends ViewPart {

	/**
	 * View part id {@value #ID}.
	 * 
	 * Relevant extension points
	 * <ul>
	 * <li>org.eclipse.ui.views
	 * </ul>
	 */
	public static final String ID = "org.emoflon.gips.debugger.view";

	private static interface IContentNode {

	}

	private static class RootNode implements IContentNode {
		public final Map<String, ContextNode> childs = new HashMap<>();
	}

	private static class ContextNode implements IContentNode, Comparable<ContextNode> {

		public final RootNode parent;
		public final String contextId;
		public ITraceUpdateListener listener;

		public ContextNode(RootNode parent, String contextId) {
			this.parent = Objects.requireNonNull(parent, "parent");
			this.contextId = Objects.requireNonNull(contextId, "contextId");
		}

		@Override
		public String toString() {
			return contextId;
		}

		@Override
		public int compareTo(ContextNode o) {
			return String.CASE_INSENSITIVE_ORDER.compare(contextId, o.contextId);
		}
	}

	private static record ModelNode(ContextNode parent, String modelId) implements IContentNode {
		@Override
		public String toString() {
			return modelId;
		}
	}

	private static record LinkModelNode(ModelNode parent, String modelId, int direction) implements IContentNode {
		@Override
		public String toString() {
			return modelId;
		}
	}

	private static final class TraceContentProvider implements ITreeContentProvider {

		private final Consumer<IContentNode> refreshNode;

		public TraceContentProvider(Consumer<IContentNode> refreshNode) {
			this.refreshNode = Objects.requireNonNull(refreshNode, "refreshNode");
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof RootNode rNode) {
				ITraceManager trace = ITraceManager.getInstance();
				var availableContextIds = trace.getAvailableContextIds();

				rNode.childs.keySet().removeIf(trackedContextId -> !availableContextIds.contains(trackedContextId));

				for (var contextId : availableContextIds) {
					rNode.childs.computeIfAbsent(contextId, id -> {
						var cNode = new ContextNode(rNode, id);
						cNode.listener = (context, modelIds) -> refreshNode.accept(cNode);
						trace.getContext(id).addListener(cNode.listener);
						return cNode;
					});
				}

				return rNode.childs.values().stream().sorted().toArray();
			}

			if (parentElement instanceof ContextNode cNode) {
				ITraceContext context = ITraceManager.getInstance().getContext(cNode.contextId);
				return context.getAllModels().stream().sorted(String.CASE_INSENSITIVE_ORDER)
						.map(id -> new ModelNode(cNode, id)).toArray();
			}

			if (parentElement instanceof ModelNode mNode) {
				ITraceContext context = ITraceManager.getInstance().getContext(mNode.parent.contextId);

				var incoming = context.getSourceModels(mNode.modelId).stream().sorted(String.CASE_INSENSITIVE_ORDER)
						.map(id -> new LinkModelNode(mNode, id, 0));
				var outgoing = context.getTargetModels(mNode.modelId).stream().sorted(String.CASE_INSENSITIVE_ORDER)
						.map(id -> new LinkModelNode(mNode, id, 1));

				return Stream.concat(incoming, outgoing).toArray();
			}

			return null;
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof RootNode rNode)
				return null;

			if (element instanceof ContextNode cNode)
				return cNode.parent;

			if (element instanceof ModelNode mNode)
				return mNode.parent;

			if (element instanceof LinkModelNode lmNode)
				return lmNode.parent;

			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof RootNode rNode)
				return !ITraceManager.getInstance().getAvailableContextIds().isEmpty();

			if (element instanceof ContextNode cNode) {
				ITraceContext context = ITraceManager.getInstance().getContext(cNode.contextId);
				return !context.getAllModels().isEmpty();
			}

			if (element instanceof ModelNode mNode) {
				ITraceContext context = ITraceManager.getInstance().getContext(mNode.parent.contextId);
				return !context.getSourceModels(mNode.modelId).isEmpty()
						|| !context.getTargetModels(mNode.modelId).isEmpty();
			}

			return false;
		}
	}

	private static final class TraceLabelProvider extends LabelProvider implements ILabelProvider {

		private Image projectImage;
		private Image rightImage;
		private Image leftImage;

		public TraceLabelProvider() {
			ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
			this.projectImage = sharedImages.getImage(org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT);
			this.rightImage = sharedImages.getImage(ISharedImages.IMG_TOOL_FORWARD);
			this.leftImage = sharedImages.getImage(ISharedImages.IMG_TOOL_BACK);
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof ContextNode)
				return projectImage;

			if (element instanceof LinkModelNode node) {
				if (node.direction == 0)
					return rightImage;
				else
					return leftImage;
			}

			return null;
		}

		@Override
		public String getText(Object element) {
			return element != null ? element.toString() : "???";
		}

		@Override
		public void dispose() {
			super.dispose();
		}

	}

	private final ITraceManagerListener traceManagerListener = this::onTraceContextChange;

	private TreeViewer viewer;
	private RootNode viewModelRootNode;
//	private IMemento memento;

//	@Inject
//	IWorkbench workbench;

	public GipsTraceView() {
		super();
	}

	// https://github.com/eclipse-jdt/eclipse.jdt.ui/blob/master/org.eclipse.jdt.ui/ui/org/eclipse/jdt/internal/ui/javaeditor/JavaOutlinePage.java
	// https://git.eclipse.org/r/plugins/gitiles/platform/eclipse.platform.ui/+/0876b2212986e9daafa36e153f57dcdfc8949fbd/bundles/org.eclipse.ui.ide/src/org/eclipse/ui/views/markers/internal/ProblemView.java

	// TODO: memento can be used to restore the state of a view
//	@Override
//	public void init(IViewSite site, IMemento memento) throws PartInitException {
//		super.init(site, memento);
//		this.memento = memento;
//	}

	@Override
	public void saveState(IMemento memento) {
		super.saveState(memento);
//		saveSelection(memento);
	}

	@Override
	public void createPartControl(Composite parent) {
		var layout = new FillLayout(); // new GridLayout(1, false);
		parent.setLayout(layout);

//		Label label = new Label(parent, SWT.NONE);
//		label.setText("TraceGraph Links");

//		this.getSite().getPage()
//		this.workbench.addWindowListener(new WindowListener());

		this.viewer = new TreeViewer(parent);
		this.viewer.setContentProvider(new TraceContentProvider(this::refreshNode));
		this.viewer.setLabelProvider(new TraceLabelProvider());

		ITraceManager traceManager = ITraceManager.getInstance();
		traceManager.addTraceManagerListener(traceManagerListener);

		this.viewModelRootNode = new RootNode();
		this.viewer.setInput(this.viewModelRootNode);

//		restoreSelection(this.memento);
	}

	@Override
	public void dispose() {
		ITraceManager traceManager = ITraceManager.getInstance();
		traceManager.removeTraceManagerListener(traceManagerListener);

		// dispose viewer model
		for (var child : this.viewModelRootNode.childs.values()) {
			if (traceManager.doesContextExist(child.contextId)) {
				try {
					traceManager.getContext(child.contextId).removeListener(child.listener);
					child.listener = null;
				} catch (Exception e) {

				}
			}
		}
		this.viewModelRootNode = null;

		var contentProvider = this.viewer.getContentProvider();
		if (contentProvider != null)
			contentProvider.dispose();

		var labelProvider = this.viewer.getLabelProvider();
		if (labelProvider != null)
			labelProvider.dispose();

		this.viewer = null;
	}

	private void onTraceContextChange(TraceManagerEvent event) {
		refreshNode(this.viewModelRootNode);
	}

	private void refreshNode(IContentNode node) {
		// needs to run on the UI-Thread
		Display.getDefault().asyncExec(() -> {
			if (this.viewer != null)
				this.viewer.refresh(node);
		});
	}

	@Override
	public void setFocus() {
		if (this.viewer != null)
			this.viewer.getControl().setFocus();
	}

}
