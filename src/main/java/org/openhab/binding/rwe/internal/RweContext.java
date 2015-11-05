package org.openhab.binding.rwe.internal;

import org.openhab.binding.rwe.internal.communicator.StateHolder;
import org.openhab.binding.rwe.internal.communicator.client.RestClient;


/**
 * Context singleton
 * @author jensrohloff
 *
 */
public class RweContext {
	
	private RestClient client;
	private StateHolder stateHolder;
	private RweConfig config = new RweConfig();
	private static RweContext instance;
	
	private RweContext(){
		
	}

	/**
	 * Create or returns the instance of this class.
	 */
	public static RweContext getInstance() {
		if (instance == null) {
			instance = new RweContext();
			instance.stateHolder = new StateHolder(instance);
		}
		return instance;
	}

	public void setClient(RestClient client) {
		this.client = client;		
	}

	public RestClient getClient() {
		return client;
	}

	public StateHolder getStateHolder() {
		return stateHolder;
	}

	public RweConfig getConfig() {
		return config;
	}
}
