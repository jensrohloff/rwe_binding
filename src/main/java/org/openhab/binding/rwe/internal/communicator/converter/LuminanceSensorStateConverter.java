package org.openhab.binding.rwe.internal.communicator.converter;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.openhab.binding.rwe.states.LuminanceSensorState;
import org.openhab.core.library.types.DecimalType;

public class LuminanceSensorStateConverter implements Converter<DecimalType, LuminanceSensorState>{

	@Override
	public void convertToBinding(DecimalType type,
			LuminanceSensorState logicalDeviceState) {
		logicalDeviceState.setLuminance(new BigInteger(type.toString()));
	}

	@Override
	public DecimalType convertFromBinding(
			LuminanceSensorState logicalDeviceState) {
		 return new DecimalType(new BigDecimal(logicalDeviceState.getLuminance()));
	}

}
