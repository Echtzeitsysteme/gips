package org.emoflon.gips.debugger.handler;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;
import org.emoflon.gips.debugger.TracePlugin;
import org.emoflon.gips.debugger.pref.PluginPreferences;

public final class ToggleVisualisationHandler extends AbstractHandler implements IHandler, IElementUpdater {

	private final static String CMD_ID = "org.emoflon.gips.debugger.visualizer.toggle";

	private final IPropertyChangeListener listener = this::onPreferenceChange;

	private ImageDescriptor iconOn;
	private ImageDescriptor iconOff;
	private ImageDescriptor iconDisabled;

	public ToggleVisualisationHandler() {
		loadImageDescriptors();
		setupPreferenceListener();
	}

	private void loadImageDescriptors() {
		var assetsPath = new Path("assets");
		var bundle = TracePlugin.getInstance().getBundle();
		URL urlIconOn = FileLocator.find(bundle, assetsPath.append("toggle-visualisation-icon-on.on"));
		URL urlIconOff = FileLocator.find(bundle, assetsPath.append("toggle-visualisation-icon-off.png"));
//		URL urlIconDisabled = FileLocator.find(bundle, assetsPath.append("toggle-visualisation-icon-disabled.png"));

		this.iconOn = ImageDescriptor.createFromURL(urlIconOn);
		this.iconOff = ImageDescriptor.createFromURL(urlIconOff);
//		this.iconDisabled = ImageDescriptor.createFromURL(urlIconDisabled);
	}

	// https://github.com/eclipse-jdt/eclipse.jdt.ui/blob/master/org.eclipse.jdt.ui/ui/org/eclipse/jdt/internal/ui/javaeditor/ToggleMarkOccurrencesAction.java

	private void setupPreferenceListener() {
		PluginPreferences.getPreferenceStore().addPropertyChangeListener(listener);
	}

	@Override
	public void dispose() {
		PluginPreferences.getPreferenceStore().removePropertyChangeListener(listener);
	}

	private void onPreferenceChange(PropertyChangeEvent event) {
		if (PluginPreferences.PREF_TRACE_DISPLAY_ACTIVE.equals(event.getProperty()))
			PlatformUI.getWorkbench().getService(ICommandService.class).refreshElements(CMD_ID, null);
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
//		PlatformUI.getWorkbench().getService(IPreferencesService.class).getBoolean(
//				PreferenceConstants.PREFERENCE_STORE_KEY, PreferenceConstants.PREF_TRACE_DISPLAY_ACTIVE, false, null);
		var isActive = PluginPreferences.getPreferenceStore().getBoolean(PluginPreferences.PREF_TRACE_DISPLAY_ACTIVE);

		// TODO: externalize
		if (isActive) {
			element.setTooltip("Pause GIPSL visualisation");
			element.setChecked(true);
			element.setIcon(iconOn);
		} else {
			element.setTooltip("Activate GIPSL visualisation");
			element.setChecked(false);
			element.setIcon(iconOff);
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
//		ITraceManager manager = TracePlugin.getInstance().getTraceManager(); // site.getService(ITraceManager.class);
//		if (manager != null)
//			manager.toggleVisualisation();

//		var site = HandlerUtil.getActiveSiteChecked(event);
//		site.getService(ICommandService.class).refreshElements(event.getCommand().getId(), null);

		var preferenceStore = PluginPreferences.getPreferenceStore();
		var isActive = preferenceStore.getBoolean(PluginPreferences.PREF_TRACE_DISPLAY_ACTIVE);
		preferenceStore.setValue(PluginPreferences.PREF_TRACE_DISPLAY_ACTIVE, !isActive);

		try {
			preferenceStore.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
