package ca.tweetzy.shops.model;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class VariableHelper {

	public static List<String> replaceVariable(@NonNull List<String> originalList, final String variable, Object replacement, boolean removeVariable) {
		List<String> result = new ArrayList<>(originalList);
		final int varIndex = result.indexOf(variable);

		if (varIndex == -1) {
			return result;
		}

		if (removeVariable) {
			result.remove(varIndex);
			return result;
		}

		if (replacement instanceof String string) {
			result.set(varIndex, string);
			result.removeIf(line -> line.equalsIgnoreCase(variable));
		}

		if (replacement instanceof List<?> list) {
			@SuppressWarnings("unchecked")
			List<String> replacementList = (List<String>) list;
			result.addAll(varIndex, replacementList);
			result.removeIf(line -> line.equalsIgnoreCase(variable));
		}

		return result;
	}
}
