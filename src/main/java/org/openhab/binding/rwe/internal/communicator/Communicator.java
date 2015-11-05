package org.openhab.binding.rwe.internal.communicator;

import org.openhab.binding.rwe.internal.RweContext;
import org.openhab.binding.rwe.internal.binding.config.RweBindingConfig;
import org.openhab.binding.rwe.internal.communicator.client.RestClient;
import org.openhab.binding.rwe.internal.communicator.client.RweRestClient;
import org.openhab.binding.rwe.internal.communicator.server.CallbackServer;
import org.openhab.binding.rwe.internal.communicator.server.RestCallBackServer;
import org.openhab.binding.rwe.notification.NotificationList.Notifications.LogicalDeviceStatesChangedNotification.LogicalDeviceStates;
import org.openhab.binding.rwe.states.LogicalDeviceState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Communicator implements CallbackReceiver{
	
	private static final Logger logger = LoggerFactory.getLogger(Communicator.class);
	
	private long lastEventTime = System.currentTimeMillis();
	
	private RweContext context = RweContext.getInstance();
	
	private CallbackServer callbackServer;
	private RestClient client;
	
	/**
	 * Starts the communicator and initializes everything.
	 */
	public void start() {
		if (callbackServer == null) {
			logger.info("Starting RWE communicator");
			try {
				callbackServer = new RestCallBackServer(this);

			
				client = new RweRestClient();	

				context.setClient(client);				
				client.start();
				context.getStateHolder().init();
				context.getStateHolder().loadStates();

				callbackServer.start();
				client.registerCallback();

			} catch (Exception e) {
				logger.error("Could not start Homematic communicator: " + e.getMessage(), e);
				stop();
			}
		}
	}

	/**
	 * Stops the communicator.
	 */
	public void stop() {
		if (callbackServer != null) {
			logger.info("Shutting down communicator");
			try {				
				callbackServer.shutdown();
				if (client != null) {					
						client.releaseCallback();					
				}				
				if (context.getStateHolder() != null) {
					context.getStateHolder().destroy();
				}
			} finally {
				callbackServer = null;
			}
		}
	}	

	@Override
	public void newDevices() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void event(LogicalDeviceState logicalDeviceState) {
		lastEventTime = System.currentTimeMillis();
		
		
	}
	/**
	 * Returns the timestamp from the last server event.
	 */
	public long getLastEventTime() {
		return lastEventTime;
	}
}
