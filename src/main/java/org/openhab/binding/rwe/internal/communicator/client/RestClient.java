package org.openhab.binding.rwe.internal.communicator.client;

import org.openhab.binding.rwe.internal.communicator.ValueItemIteratorCallback;
import org.openhab.binding.rwe.notification.NotificationList;

public interface RestClient {

	void registerCallback();

	void releaseCallback();

	void iterateAllStates(
			ValueItemIteratorCallback valueItemIteratorCallback);

	void start();
	
	public NotificationList statusUpdates();

}
