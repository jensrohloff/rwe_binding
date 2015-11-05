package org.openhab.binding.rwe.internal.communicator.converter;

import java.math.BigDecimal;

import org.openhab.binding.rwe.states.RoomTemperatureActuatorState;
import org.openhab.core.library.types.DecimalType;

public class RoomTemperatureActuatorStateConverter implements Converter<DecimalType, RoomTemperatureActuatorState>{

	@Override
	public void convertToBinding(DecimalType type,
			RoomTemperatureActuatorState logicalDeviceState) {
		logicalDeviceState.setPtTmp(type.floatValue());
		
	}

	@Override
	public DecimalType convertFromBinding(
			RoomTemperatureActuatorState logicalDeviceState) {
		 return new DecimalType(new BigDecimal(logicalDeviceState.getPtTmp()));
	}

}
