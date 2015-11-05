package org.openhab.binding.rwe.internal.binding.config;

import org.openhab.core.binding.BindingConfig;

public class RweBindingConfig 
implements BindingConfig {
	
	private String deviceId;

	public RweBindingConfig(String id) {
		this.deviceId = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
}
