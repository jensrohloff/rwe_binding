package org.openhab.binding.rwe.internal.communicator.converter;

import java.util.HashMap;
import java.util.Map;

import org.openhab.binding.rwe.states.DimmerActuatorState;
import org.openhab.binding.rwe.states.LogicalDeviceState;
import org.openhab.binding.rwe.states.LuminanceSensorState;
import org.openhab.binding.rwe.states.RoomHumiditySensorState;
import org.openhab.binding.rwe.states.RoomTemperatureActuatorState;
import org.openhab.binding.rwe.states.RoomTemperatureSensorState;
import org.openhab.binding.rwe.states.SwitchActuatorState;
import org.openhab.binding.rwe.states.WindowDoorSensorState;

public class ConverterFactory {
	
	  private static final Map<
      Class<? extends LogicalDeviceState>,
      Class<? extends Converter<?,?>>> 
   converters = new HashMap<
		      Class<? extends LogicalDeviceState>,
		      Class<? extends Converter<?,?>>>();
	  static {
		  converters.put(DimmerActuatorState.class, DimmerActuatorStateConverter.class);
		  converters.put(LuminanceSensorState.class, LuminanceSensorStateConverter.class);
		  converters.put(RoomHumiditySensorState.class, RoomHumiditySensorStateConverter.class);
		  converters.put(RoomTemperatureActuatorState.class, RoomTemperatureActuatorStateConverter.class);
		  converters.put(RoomTemperatureSensorState.class, RoomTemperatureSensorStateConverter.class);
		  converters.put(SwitchActuatorState.class, SwitchActuatorStateConverter.class);
		  converters.put(WindowDoorSensorState.class, WindowDoorSensorStateConverter.class);		 
	  }

	public Converter <?,?> createConverter(LogicalDeviceState item) throws InstantiationException, IllegalAccessException{
		return converters.get(item.getClass()).newInstance();
		
	}
}
