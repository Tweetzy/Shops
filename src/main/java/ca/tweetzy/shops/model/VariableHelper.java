package ca.tweetzy.shops.model;

import lombok.NonNull;

import java.util.List;

public final class VariableHelper {

	public static List<String> replaceVariable(@NonNull List<String> originalList, final String variable, Object replacement, boolean removeVariable) {
		final int varIndex = originalList.indexOf(variable);

		if (varIndex == -1) {
			return originalList;
		}

		if (removeVariable) {
			originalList.remove(varIndex);
			return originalList;
		}

		if (replacement instanceof String string) {
			originalList.set(varIndex, string);
			originalList.removeIf(line -> line.equalsIgnoreCase(variable));
		}

		if (replacement instanceof List) {
			originalList.addAll(varIndex, (List) replacement);
			originalList.removeIf(line -> line.equalsIgnoreCase(variable));
		}

		return originalList;
	}
}
