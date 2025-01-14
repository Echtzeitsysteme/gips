package org.emoflon.gips.debugger.handler;

import java.net.URL;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;
import org.emoflon.gips.debugger.Activator;
import org.emoflon.gips.debugger.api.ITraceManager;

public final class ToggleVisualisation extends AbstractHandler implements IHandler, IElementUpdater {

	private ImageDescriptor iconOn;
	private ImageDescriptor iconOff;
	private ImageDescriptor iconDisabled;

	public ToggleVisualisation() {
		loadImageDescriptors();
	}

	private void loadImageDescriptors() {
		var bundle = Activator.getInstance().getBundle();
		URL urlIconOn = FileLocator.find(bundle, new Path("assets").append("toggle-visualisation-icon-on.on"));
		URL urlIconOff = FileLocator.find(bundle, new Path("assets").append("toggle-visualisation-icon-off.png"));
		URL urlIconDisabled = FileLocator.find(bundle,
				new Path("assets").append("toggle-visualisation-icon-disabled.png"));

		this.iconOn = ImageDescriptor.createFromURL(urlIconOn);
		this.iconOff = ImageDescriptor.createFromURL(urlIconOff);
		this.iconDisabled = ImageDescriptor.createFromURL(urlIconDisabled);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
		ITraceManager debugService = Activator.getInstance().getTraceManager(); // element.getServiceLocator().getService(IDebugService.class);
		if (debugService != null) {
			if (debugService.isVisualisationActive()) { // TODO: externalize
				element.setTooltip("Pause GIPSL visualisation");
				element.setChecked(true);
				element.setIcon(iconOn);
			} else {
				element.setTooltip("Activate GIPSL visualisation");
				element.setChecked(false);
				element.setIcon(iconOff);
			}
		} else {
			element.setTooltip("GIPSL Debugger not found");
			element.setChecked(false);
			element.setIcon(iconDisabled);
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		var site = HandlerUtil.getActiveSiteChecked(event);

		ITraceManager manager = Activator.getInstance().getTraceManager(); // site.getService(IDebugService.class);
		if (manager != null) {
			manager.toggleVisualisation();
		}

		site.getService(ICommandService.class).refreshElements(event.getCommand().getId(), null);

		return null;
	}

}
