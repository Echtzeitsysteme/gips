package org.emoflon.gips.eclipse;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.emoflon.gips.eclipse.api.ITraceManager;
import org.emoflon.gips.eclipse.service.ContextManager;
import org.emoflon.gips.eclipse.service.RemoteEclipseService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * The activator class controls the plug-in life cycle
 */
public final class TracePlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.emoflon.gips.eclipse"; //$NON-NLS-1$

	// synchronization lock
	private static final Object syncLock = new Object();

	// The shared instance
	private static TracePlugin INSTANCE;

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TracePlugin getInstance() {
		return TracePlugin.INSTANCE;
	}

//	public static void log(IStatus status) {
//		getInstance().getLog().log(status);
//	}

	private ContextManager contextManager;
	private ServiceRegistration<ITraceManager> traceManagerRegistration;

	private RemoteEclipseService remoteService;

	private ScopedPreferenceStore preferenceStore;

	public TracePlugin() {
		Assert.isTrue(INSTANCE == null);
		TracePlugin.INSTANCE = this;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);

		contextManager = new ContextManager();
		contextManager.initialize();

//		var eclipseContext = PlatformUI.getWorkbench().getService(IEclipseContext.class);
//		eclipseContext.set(ITraceManager.class, traceManager); 		
//		var eclipseContext = EclipseContextFactory.getServiceContext(bundleContext);
//		eclipseContext.set(ITraceManager.class, traceManager);
		traceManagerRegistration = getBundle().getBundleContext().registerService(ITraceManager.class, contextManager,
				null);

//		if(eclipseContext!=null) {
//		try {
//			var test = ContextInjectionFactory.make(InjectionTest.class, eclipseContext);
//		} catch (Exception e) {
//
//		}
//		}

//		var eclipseContext = EclipseContextHelper.getActiveContext();
//		this.watcher = ContextInjectionFactory.make(EditorWatcher.class, eclipseContext);

		remoteService = new RemoteEclipseService();
		remoteService.initialize();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		traceManagerRegistration.unregister();

		remoteService.dispose();
		contextManager.dispose();

		super.stop(context);
	}

	@Override
	public ScopedPreferenceStore getPreferenceStore() {
		if (preferenceStore == null) {
			synchronized (syncLock) {
				if (preferenceStore == null) {
					preferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, TracePlugin.PLUGIN_ID);
					preferenceStore.setSearchContexts(
							new IScopeContext[] { InstanceScope.INSTANCE, ConfigurationScope.INSTANCE });

				}
			}
		}
		return this.preferenceStore;
	}

	/**
	 * Shareable singleton instance, not intended to be accessible from outside the
	 * plugin. To access the {@link ITraceManager} from outside the plugin see
	 * {@link ITraceManager#getInstance()}
	 * 
	 * @see ITraceManager#getInstance()
	 */
	public ContextManager getContextManager() {
		return contextManager;
	}

}
