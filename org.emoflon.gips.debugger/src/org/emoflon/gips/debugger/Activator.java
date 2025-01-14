package org.emoflon.gips.debugger;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.emoflon.gips.debugger.api.ITraceManager;
import org.emoflon.gips.debugger.imp.TraceManager;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.emoflon.gips.debugger"; //$NON-NLS-1$

	// The shared instance
	private static Activator INSTANCE;

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getInstance() {
		return INSTANCE;
	}

	private TraceManager traceManager;

	public Activator() {
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		INSTANCE = this;

		traceManager = new TraceManager();
		traceManager.initialize();

		var eclipseContext = EclipseContextFactory.getServiceContext(bundleContext);
		eclipseContext.set(ITraceManager.class, traceManager);

//		if(eclipseContext!=null) {
//			ContextInjectionFactory.inject(debugService, eclipseContext);
//		}

//		var eclipseContext = EclipseContextHelper.getActiveContext();
//		this.watcher = ContextInjectionFactory.make(EditorWatcher.class, eclipseContext);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		traceManager.dispose();

		INSTANCE = null;

		super.stop(context);
	}

	/**
	 * Shareable singleton instance
	 * 
	 * @return
	 */
	public ITraceManager getTraceManager() {
		return traceManager;
	}

}
