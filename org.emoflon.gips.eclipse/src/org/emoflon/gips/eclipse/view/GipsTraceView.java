package org.emoflon.gips.eclipse.view;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;
import org.emoflon.gips.eclipse.view.model.INode;
import org.emoflon.gips.eclipse.view.model.RootNode;

/**
 * View of the internal gips trace graph.
 * 
 * <p>
 * The context menu can be extended via {@code org.eclipse.ui.menus} extension
 * point. Location URI is popup:{@value #ID}
 */
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
		this.viewer.setContentProvider(new TraceContentProvider());
		this.viewer.setLabelProvider(new DelegatingStyledCellLabelProvider(new TraceLabelProvider()));
//		ColumnViewerToolTipSupport.enableFor(this.viewer);

		this.viewModelRootNode = new RootNode(this::refreshNode);

		this.viewer.setInput(this.viewModelRootNode);
		getSite().setSelectionProvider(this.viewer);

		menuManager = new MenuManager();
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(this::fillContextMenu);
		menu = menuManager.createContextMenu(this.viewer.getControl());

		this.viewer.getControl().setMenu(menu);
		// allows context menu extensions
		getSite().registerContextMenu(menuManager, this.viewer);

//		restoreSelection(this.memento);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	@Override
	public void dispose() {
		this.viewModelRootNode.dispose();
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

	protected void refreshNode(INode node) {
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
