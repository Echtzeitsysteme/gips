package org.emoflon.gips.core.trace;

import org.emoflon.gips.eclipse.api.IRemoteEclipseService;

public class EclipseIntegrationConfig {

	private int servicePort = IRemoteEclipseService.DEFAULT_PORT;
	private boolean tracingEnabled = false;
	private boolean solutionValuesEnabled = false;

	public int getServicePort() {
		return servicePort;
	}

	public void setServicePort(int port) {
		if (port < 1023 || 65535 < port)
			throw new IllegalArgumentException("Port should be between 1023 and 65535");
	}

	public boolean isTracingEnabled() {
		return tracingEnabled;
	}

	public void setTracingEnabled(boolean value) {
		tracingEnabled = value;
	}

	public boolean isSolutionValuesCodeMiningEnabled() {
		return solutionValuesEnabled;
	}

	public void isSolutionValuesCodeMiningEnabled(boolean value) {
		solutionValuesEnabled = value;
	}

}
