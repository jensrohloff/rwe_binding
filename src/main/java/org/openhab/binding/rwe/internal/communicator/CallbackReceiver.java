package org.openhab.binding.rwe.internal.communicator;

import org.openhab.binding.rwe.states.LogicalDeviceState;

public interface CallbackReceiver {

	public void event(LogicalDeviceState logicalDeviceState);
	
	public void newDevices();
}
