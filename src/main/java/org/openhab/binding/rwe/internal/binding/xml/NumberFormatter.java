package org.openhab.binding.rwe.internal.binding.xml;

import java.text.DecimalFormat;

public class NumberFormatter {

	public static String printFloatwith2Decimal(Float value) {
		return new DecimalFormat("#.00").format(value);
	}

	public static Float parseInt(String value) {
		return Float.valueOf(value);
	}
}
