package ca.tweetzy.shops.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ItemParser {

	public static Map<String, List<String>> parseString(String input) {
		Map<String, List<String>> resultMap = new HashMap<>();
		StringBuilder valueBuilder = new StringBuilder();
		boolean withinQuotes = false;
		String currentKey = null;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);

			if (c == '"') {
				withinQuotes = !withinQuotes;
			} else if (c == ' ' && !withinQuotes) {
				if (valueBuilder.length() > 0 && currentKey != null) {
					String[] values = valueBuilder.toString().split(",");
					List<String> valueList = Arrays.asList(values);
					resultMap.put(currentKey, valueList);
					valueBuilder.setLength(0); // Clear StringBuilder for next key-value pair
				}
			} else if (c == ':' && !withinQuotes) {
				currentKey = valueBuilder.toString();
				valueBuilder.setLength(0); // Clear StringBuilder for value
			} else {
				valueBuilder.append(c);
			}
		}

		// Add the last key-value pair
		if (currentKey != null && valueBuilder.length() > 0) {
			String[] values = valueBuilder.toString().split(",");
			List<String> valueList = Arrays.asList(values);
			resultMap.put(currentKey, valueList);
		}

		return resultMap;
	}
}
