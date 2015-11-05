package org.openhab.binding.rwe.internal.communicator.converter;

import java.math.BigDecimal;

import org.openhab.binding.rwe.states.RoomTemperatureSensorState;
import org.openhab.core.library.types.DecimalType;

public class RoomTemperatureSensorStateConverter implements Converter<DecimalType, RoomTemperatureSensorState>{

	@Override
	public void convertToBinding(DecimalType type,
			RoomTemperatureSensorState logicalDeviceState) {
		logicalDeviceState.setTemperature(type.floatValue());
		
	}

	@Override
	public DecimalType convertFromBinding(
			RoomTemperatureSensorState logicalDeviceState) {
		 return new DecimalType(new BigDecimal(logicalDeviceState.getTemperature()));
	}

}
