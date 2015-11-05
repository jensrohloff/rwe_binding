package org.openhab.binding.rwe.internal.communicator.converter;

import java.math.BigDecimal;

import org.openhab.binding.rwe.states.RoomHumiditySensorState;
import org.openhab.core.library.types.PercentType;

public class RoomHumiditySensorStateConverter implements Converter<PercentType, RoomHumiditySensorState>{

	@Override
	public void convertToBinding(PercentType type,
			RoomHumiditySensorState logicalDeviceState) {
		logicalDeviceState.setHumidity(new BigDecimal(type.toString()).floatValue());
	}

	@Override
	public PercentType convertFromBinding(
			RoomHumiditySensorState logicalDeviceState) {
		// TODO Auto-generated method stub
		return new PercentType(new BigDecimal(logicalDeviceState.getHumidity()));
	}

	


}
