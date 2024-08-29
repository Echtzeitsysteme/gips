package org.emoflon.gips.core.util;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * System utility to get information of the system running GIPS.
 */
public class SystemUtil {

	/**
	 * Returns the number of threads (i.e., logical processors) of the system.
	 * 
	 * @return Number of threads.
	 */
	public static int getSystemThreads() {
		final SystemInfo systemInfo = new SystemInfo();
		final HardwareAbstractionLayer hal = systemInfo.getHardware();
		final CentralProcessor centralProcessor = hal.getProcessor();
		return centralProcessor.getLogicalProcessorCount();
	}

}
