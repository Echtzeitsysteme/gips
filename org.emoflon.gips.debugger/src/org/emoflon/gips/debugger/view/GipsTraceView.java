package org.emoflon.gips.debugger.view;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IToolTipProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;
import org.emoflon.gips.debugger.TracePlugin;
import org.emoflon.gips.debugger.api.ITraceContext;
import org.emoflon.gips.debugger.api.ITraceManager;
import org.emoflon.gips.debugger.api.event.ITraceManagerListener;
import org.emoflon.gips.debugger.api.event.TraceManagerEvent;
import org.emoflon.gips.debugger.imp.ProjectTraceContext;
import org.emoflon.gips.debugger.utility.HelperEclipse;
import org.emoflon.gips.debugger.utility.HelperErrorDialog;
import org.emoflon.gips.debugger.view.ITraceViewModel.ContextNode;
import org.emoflon.gips.debugger.view.ITraceViewModel.LinkModelNode;
import org.emoflon.gips.debugger.view.ITraceViewModel.ModelNode;
import org.emoflon.gips.debugger.view.ITraceViewModel.RootNode;

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

	private static final class TraceContentProvider implements ITreeContentProvider {

		private final Consumer<ITraceViewModel> refreshNode;

		public TraceContentProvider(Consumer<ITraceViewModel> refreshNode) {
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
						cNode.listener = event -> refreshNode.accept(cNode);
						trace.getContext(id).addListener(cNode.listener);
						return cNode;
					});
				}

				return rNode.childs.values().stream().sorted().toArray();
			}

			if (parentElement instanceof ContextNode cNode) {
				ITraceContext context = ITraceManager.getInstance().getContext(cNode.getContextId());
				return context.getAllModels().stream().sorted(String.CASE_INSENSITIVE_ORDER)
						.map(id -> new ModelNode(cNode, id)).toArray();
			}

			if (parentElement instanceof ModelNode mNode) {
				ITraceContext context = ITraceManager.getInstance().getContext(mNode.getContextId());

				var incoming = context.getSourceModels(mNode.modelId).stream().sorted(String.CASE_INSENSITIVE_ORDER)
						.map(id -> new LinkModelNode(mNode, id, LinkModelNode.Direction.BACKWARD));
				var outgoing = context.getTargetModels(mNode.modelId).stream().sorted(String.CASE_INSENSITIVE_ORDER)
						.map(id -> new LinkModelNode(mNode, id, LinkModelNode.Direction.FORWARD));

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
				ITraceContext context = ITraceManager.getInstance().getContext(cNode.getContextId());
				return !context.getAllModels().isEmpty();
			}

			if (element instanceof ModelNode mNode) {
				ITraceContext context = ITraceManager.getInstance().getContext(mNode.getContextId());
				return !context.getSourceModels(mNode.modelId).isEmpty()
						|| !context.getTargetModels(mNode.modelId).isEmpty();
			}

			return false;
		}
	}

	private static final class TraceLabelProvider extends LabelProvider implements ILabelProvider, IToolTipProvider {

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
				return switch (node.direction) {
				case FORWARD -> rightImage;
				case BACKWARD -> leftImage;
				default -> null;
				};
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

		@Override
		public String getToolTipText(Object element) {
			return "Tooltip (" + element + ")";
		}

//		@Override
//		public Point getToolTipShift(Object object) {
//			return new Point(5, 5);
//		}
//
//		@Override
//		public int getToolTipDisplayDelayTime(Object object) {
//			return 2000;
//		}
//
//		@Override
//		public int getToolTipTimeDisplayed(Object object) {
//			return 5000;
//		}
//
//		@Override
//		public void update(ViewerCell cell) {
//			cell.setText(cell.getElement().toString());
//		}

	}

	private final ITraceManagerListener traceManagerListener = this::onTraceContextChange;

	private TreeViewer viewer;
	private MenuManager menuManager;
	private Menu menu;

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

		this.viewer = new TreeViewer(parent);
		this.viewer.setContentProvider(new TraceContentProvider(this::refreshNode));
		this.viewer.setLabelProvider(new TraceLabelProvider());
//		ColumnViewerToolTipSupport.enableFor(this.viewer);

		ITraceManager traceManager = ITraceManager.getInstance();
		traceManager.addTraceManagerListener(traceManagerListener);

		this.viewModelRootNode = new RootNode();
		this.viewer.setInput(this.viewModelRootNode);

		menuManager = new MenuManager();
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(this::fillContextMenu);
		menu = menuManager.createContextMenu(viewer.getControl());

		viewer.getControl().setMenu(menu);
		// allows extensions
//		getSite().registerContextMenu(menuManager, viewer);

//		restoreSelection(this.memento);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

		var selection = this.viewer.getStructuredSelection();
		if (selection.getFirstElement() != null) {
			var selectedElement = selection.getFirstElement();
			if (selectedElement instanceof ContextNode cNode) {
				manager.add(new ContributionItem() {
					@Override
					public void fill(Menu menu, int index) {
						final var item = new MenuItem(menu, SWT.CHECK, index);
						item.setText("Delete all");
					}
				});
			}

			if (selectedElement instanceof final ModelNode mNode) {
				manager.add(new ContributionItem() {
					@Override
					public void fill(Menu menu, int index) {
						final var item = new MenuItem(menu, SWT.CHECK, index);
						item.setText("Delete entry");
						item.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								var manager = TracePlugin.getInstance().getTraceManager();
								manager.getContext(mNode.getContextId()).deleteModel(mNode.modelId);
							}
						});
					}
				});

				manager.add(new ContributionItem() {
					@Override
					public void fill(Menu menu, int index) {
						final var item = new MenuItem(menu, SWT.CHECK, index);
						item.setText("Open in editor");
						item.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent event) {
								IProject project = HelperEclipse.tryAndGetProject(mNode.getContextId());
								if (project == null)
									return;

								IPath filePath = IPath.EMPTY;
								for (var segment : mNode.modelId.split("/"))
									filePath = filePath.append(segment);

								IFile modelFile = project.getFile(filePath);

								try {
									IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
											.getActivePage();
									IDE.openEditor(page, modelFile);
								} catch (final PartInitException ex) {
									ex.printStackTrace();
									var error = HelperErrorDialog.createMultiStatus(ex.getLocalizedMessage(), ex);
									ErrorDialog.openError(
											PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error",
											"Unable to open file: " + modelFile, error);
								}
							}
						});
					}
				});
			}

			if (selectedElement instanceof final LinkModelNode lNode) {
				manager.add(new ContributionItem() {
					@Override
					public void fill(Menu menu, int index) {
						final var item = new MenuItem(menu, SWT.CHECK, index);
						item.setText("Delete trace");
						item.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								ProjectTraceContext context = TracePlugin.getInstance().getTraceManager()
										.getContext(lNode.getContextId());

								switch (lNode.direction) {
								case FORWARD:
									context.deleteModelLink(lNode.parent.modelId, lNode.modelId);
									break;
								case BACKWARD:
									context.deleteModelLink(lNode.modelId, lNode.parent.modelId);
									break;
								}
							}
						});
					}
				});
			}
		}
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

		if (menu != null)
			menu.dispose();
		menu = null;

		if (menuManager != null)
			menuManager.dispose();
		menuManager = null;

		if (viewer != null) {
			var contentProvider = this.viewer.getContentProvider();
			if (contentProvider != null)
				contentProvider.dispose();

			var labelProvider = this.viewer.getLabelProvider();
			if (labelProvider != null)
				labelProvider.dispose();

			this.viewer = null;
		}
	}

	private void onTraceContextChange(TraceManagerEvent event) {
		refreshNode(this.viewModelRootNode);
	}

	private void refreshNode(ITraceViewModel node) {
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
