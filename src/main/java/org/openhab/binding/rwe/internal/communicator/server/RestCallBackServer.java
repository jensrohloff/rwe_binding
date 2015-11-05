package org.openhab.binding.rwe.internal.communicator.server;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.openhab.binding.rwe.internal.RweContext;
import org.openhab.binding.rwe.internal.communicator.CallbackReceiver;
import org.openhab.binding.rwe.notification.NotificationList;
import org.openhab.binding.rwe.notification.NotificationList.Notifications.LogicalDeviceStatesChangedNotification;
import org.openhab.binding.rwe.states.LogicalDeviceState;

public class RestCallBackServer implements CallbackServer {

	ScheduledExecutorService scheduledThread = Executors
			.newSingleThreadScheduledExecutor();
	
	private CallbackReceiver callbackReceiver;
	
	private RweContext context = RweContext.getInstance();

	public RestCallBackServer(CallbackReceiver receiver) {
		this.callbackReceiver = receiver;
	}

	@Override
	public void start() {
		scheduledThread.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				NotificationList updates = context.getClient().statusUpdates();
				for (LogicalDeviceStatesChangedNotification notification : updates.getNotifications().getLogicalDeviceStatesChangedNotifications()){
					callbackReceiver.event(notification.getLogicalDeviceStates().getLogicalDeviceState());
				}				
			}
		}, 0, 10, TimeUnit.SECONDS);

	}

	@Override
	public void shutdown() {
		scheduledThread.shutdownNow();
	}

}
