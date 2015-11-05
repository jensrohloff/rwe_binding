package org.openhab.binding.rwe.internal.binding.xml;

public class BooleanFormater {
	
	public static String printBoolean(Boolean value) {
		return value ? "True" : "False";
	}

	public static Boolean parseBoolean(String value) {
		return Boolean.parseBoolean(value);
	}

}
