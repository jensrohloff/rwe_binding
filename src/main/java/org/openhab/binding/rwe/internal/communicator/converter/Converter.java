package org.openhab.binding.rwe.internal.communicator.converter;

import org.openhab.binding.rwe.states.LogicalDeviceState;
import org.openhab.core.types.State;

public interface Converter<T extends State, V extends LogicalDeviceState> {
	
	/**
	 * Converts a openHAB type to a LogicalDeviceState object.
	 */
	public void convertToBinding(T type, V logicalDeviceState);

	/**
	 * Converts a LogicalDeviceState object to a openHAB type.
	 */
	public T convertFromBinding(V logicalDeviceState);

}