package org.openhab.binding.rwe.internal.communicator.converter;

import org.openhab.binding.rwe.states.SwitchActuatorState;
import org.openhab.core.library.types.OnOffType;

public class SwitchActuatorStateConverter implements
		Converter<OnOffType, SwitchActuatorState> {

	@Override
	public void convertToBinding(OnOffType type,
			SwitchActuatorState logicalDeviceState) {
		if (type.equals(OnOffType.ON)) {
			logicalDeviceState.setIsOn(Boolean.TRUE);
		} else {
			logicalDeviceState.setIsOn(Boolean.FALSE);
		}
	}

	@Override
	public OnOffType convertFromBinding(SwitchActuatorState logicalDeviceState) {
		return logicalDeviceState.isIsOn() ? OnOffType.ON : OnOffType.OFF;
	}

}
