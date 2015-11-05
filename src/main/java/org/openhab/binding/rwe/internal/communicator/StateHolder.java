package org.openhab.binding.rwe.internal.communicator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openhab.binding.rwe.internal.RweContext;
import org.openhab.binding.rwe.internal.binding.config.RweBindingConfig;
import org.openhab.binding.rwe.states.LogicalDeviceState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateHolder {
	
	private RweContext context;
	private ExecutorService reloadExecutorPool;
	private Map<RweBindingConfig, Object> refreshCache = new HashMap<RweBindingConfig, Object>();
	private Map<RweBindingConfig, LogicalDeviceState> states = new HashMap<RweBindingConfig, LogicalDeviceState>();
	
	private static final Logger logger = LoggerFactory.getLogger(StateHolder.class);
	private boolean datapointReloadInProgress;
	
	public StateHolder(RweContext context) {
		this.context = context;
	}

	public void init() {
		reloadExecutorPool = Executors.newCachedThreadPool();
	}
	
	public boolean isDatapointReloadInProgress() {
		return datapointReloadInProgress;
	}

	public void loadStates() {
		logger.info("Loading datapoints");
		context.getClient().iterateAllStates(new ValueItemIteratorCallback() {

			@Override
			public void iterate(RweBindingConfig bindingConfig, LogicalDeviceState valueItem) {
				states.put(bindingConfig, valueItem);
			}
		});
		logger.info("Finished loading {} states", states.size());
		
	}
	
	public void reloadStates() {
		reloadExecutorPool.execute(new Runnable() {
			@Override
			public void run() {				
				try {
					logger.debug("Reloading Homematic server datapoints");
					datapointReloadInProgress = true;
					context.getClient().iterateAllStates(new ValueItemIteratorCallback() {
						@Override
						public void iterate(RweBindingConfig bindingConfig, LogicalDeviceState state) {
							if (!states.containsKey(bindingConfig)) {
								logger.info("Adding new {}", bindingConfig);
								states.put(bindingConfig, state);
							} else {
								Object cachedValue = refreshCache.get(bindingConfig);
//								if (cachedValue != null) {
//									logger.debug("Value changed while refreshing from '{}' to '{}' for binding {}",
//											state.getValue(), cachedValue, bindingConfig);
//									state.setValue(cachedValue);
//								}
//
//								if (hasChanged(bindingConfig, datapoints.get(bindingConfig), hmValueItem)) {
//									states.put(bindingConfig, hmValueItem);
//									publish(bindingConfig, hmValueItem);
//								}
							}
						}
					});
					logger.debug("Finished reloading {} s", states.size());
				} finally {
					datapointReloadInProgress = false;
					refreshCache.clear();
				}

			}
		});
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
