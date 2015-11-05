package org.openhab.binding.rwe.internal;

import java.util.Dictionary;
//import org.osgi.service.cm.ConfigurationException;

import org.apache.commons.lang.StringUtils;

public class RweConfig {

	private static final String CONFIG_KEY_RWE_HOST = "host";
	private static final String CONFIG_KEY_USERNAME = "username";
	private static final String CONFIG_KEY_PASSWORD = "password";
	private static final String CONFIG_KEY_VERSION = "version";
	private static final String CONFIG_KEY_ALIVE_INTERVAL = "alive.interval";

	private static final String DEFAULT_HOST = "smarthome02";
	private static final int DEFAULT_ALIVE_INTERVAL = 300;
	private static final float DEFAULT_VERSION = 1.70f;

	private String host;

	private float version;

	private String username;

	private String password;

	private boolean valid;

	private Integer aliveInterval;

	/**
	 * Parses and validates the properties in the openhab.cfg.
	 */
	public void parse(Dictionary<String, ?> properties) {
		// throws ConfigurationException {
		valid = false;

		host = (String) properties.get(CONFIG_KEY_RWE_HOST);
		if (StringUtils.isBlank(host)) {
			host = DEFAULT_HOST;
		}
		username = (String) properties.get(CONFIG_KEY_USERNAME);
		if (StringUtils.isBlank(username)) {
//			throw new ConfigurationException("rwe",
//					"Parameter username is mandatory and must be configured. Please check your openhab.cfg!");
		}
		password = (String) properties.get(CONFIG_KEY_PASSWORD);
		if (StringUtils.isBlank(password)) {
//			throw new ConfigurationException("rwe",
//					"Parameter password is mandatory and must be configured. Please check your openhab.cfg!");
		}
		
		aliveInterval = parseInt(properties, CONFIG_KEY_ALIVE_INTERVAL,
				DEFAULT_ALIVE_INTERVAL);
		version = parseFloat(properties, CONFIG_KEY_VERSION,
				DEFAULT_VERSION);
		valid = true;
	}

	/**
	 * Parses a integer property.
	 */
	private Integer parseInt(Dictionary<String, ?> properties, String key,
			Integer defaultValue) {
		// throws ConfigurationException {
		String value = (String) properties.get(key);
		if (StringUtils.isNotBlank(value)) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException ex) {
				// throw new ConfigurationException("homematic", "Parameter " +
				// key
				// + " in wrong format. Please check your openhab.cfg!");
				return null;
			}

		} else {
			return defaultValue;
		}
	}

	/**
	 * Parses a integer property.
	 */
	private Float parseFloat(Dictionary<String, ?> properties, String key,
			Float defaultValue) {
		// throws ConfigurationException {
		String value = (String) properties.get(key);
		if (StringUtils.isNotBlank(value)) {
			try {
				return Float.parseFloat(value);
			} catch (NumberFormatException ex) {
				// throw new ConfigurationException("homematic", "Parameter " +
				// key
				// + " in wrong format. Please check your openhab.cfg!");
				return null;
			}

		} else {
			return defaultValue;
		}
	}
	public String getHost() {
		return host;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public float getVersion() {
		return version;
	}

	public Integer getAliveInterval() {
		return aliveInterval;
	}
	
	/**
	 * Returns true if this config is valid.
	 */
	public boolean isValid() {
		return valid;
	}

}
