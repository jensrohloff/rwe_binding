package org.openhab.binding.rwe.internal.communicator.converter;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.openhab.binding.rwe.states.DimmerActuatorState;
import org.openhab.core.library.types.DecimalType;

public class DimmerActuatorStateConverter implements Converter<DecimalType, DimmerActuatorState> {

	@Override
	public void convertToBinding(DecimalType type,
			DimmerActuatorState logicalDeviceState) {
		logicalDeviceState.setDmLvl(new BigInteger(type.toString()));
	}

	@Override
	public DecimalType convertFromBinding(DimmerActuatorState logicalDeviceState) {		
		 return new DecimalType(new BigDecimal(logicalDeviceState.getDmLvl()));
	}

	

}
