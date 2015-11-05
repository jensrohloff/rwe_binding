package org.openhab.binding.rwe.internal.communicator;

import org.openhab.binding.rwe.internal.binding.config.RweBindingConfig;
import org.openhab.binding.rwe.states.LogicalDeviceState;

public interface ValueItemIteratorCallback {
	
	public void iterate(RweBindingConfig bindingConfig, LogicalDeviceState valueItem);
	
}
