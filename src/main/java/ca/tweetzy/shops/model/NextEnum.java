package ca.tweetzy.shops.model;

import lombok.experimental.UtilityClass;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 14 2022
 * Time Created: 12:17 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@UtilityClass
public final class NextEnum {

	public <T extends Enum<T>> T next(Class<T> enumType, int ordinal) {
		final T[] values = enumType.getEnumConstants();
		return values[(ordinal + 1) % values.length];
	}

	public <T extends Enum<T>> T previous(Class<T> enumType, int ordinal) {
		final T[] values = enumType.getEnumConstants();
		return values[(ordinal - 1 + values.length) % values.length];
	}
}
