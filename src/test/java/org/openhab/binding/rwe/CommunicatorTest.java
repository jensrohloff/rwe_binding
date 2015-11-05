package org.openhab.binding.rwe;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Test;
import org.openhab.binding.rwe.internal.RweContext;
import org.openhab.binding.rwe.internal.communicator.Communicator;
import org.openhab.binding.rwe.internal.communicator.converter.Converter;
import org.openhab.binding.rwe.internal.communicator.converter.ConverterFactory;
import org.openhab.binding.rwe.states.LogicalDeviceState;
import org.openhab.binding.rwe.states.RoomTemperatureSensorState;
import org.openhab.core.types.State;

public class CommunicatorTest {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void startCommunicatior() throws InterruptedException {
		Communicator communicator = new Communicator();
		RweContext context = RweContext.getInstance();
		Dictionary properties = new Hashtable();
		properties.put("username", System.getProperty("user"));
		properties.put("password", System.getProperty("pwd"));
		context.getConfig().parse(properties);
		communicator.start();
		Thread.sleep(30 * 60 * 1000);
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	public void testConverter() throws InstantiationException, IllegalAccessException{
		ConverterFactory converterFactory = new ConverterFactory();
		RoomTemperatureSensorState logicalDeviceState = new RoomTemperatureSensorState();
		logicalDeviceState.setTemperature(12.9f);
		Converter<State,LogicalDeviceState> converter = (Converter<State, LogicalDeviceState>)converterFactory.createConverter(logicalDeviceState);
		State state = converter.convertFromBinding(logicalDeviceState);
	}

}
