package org.openhab.binding.rwe.internal.communicator.converter;

import org.openhab.binding.rwe.states.WindowDoorSensorState;
import org.openhab.core.library.types.OnOffType;

public class WindowDoorSensorStateConverter implements
Converter<OnOffType, WindowDoorSensorState>{

	@Override
	public void convertToBinding(OnOffType type,
			WindowDoorSensorState logicalDeviceState) {
		if (type.equals(OnOffType.ON)) {
			logicalDeviceState.setIsOpen(Boolean.TRUE);
		} else {
			logicalDeviceState.setIsOpen(Boolean.FALSE);
		}
		
	}

	@Override
	public OnOffType convertFromBinding(WindowDoorSensorState logicalDeviceState) {
		return logicalDeviceState.isIsOpen() ? OnOffType.ON : OnOffType.OFF;
	}

}
