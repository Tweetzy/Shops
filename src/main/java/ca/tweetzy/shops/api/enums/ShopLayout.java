package ca.tweetzy.shops.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 2:31 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public enum ShopLayout {

	MANUAL("Manual"),
	AUTOMATIC("Automatic");

	@Getter
	private final String state;

	private static final ShopLayout[] states = values();

	public ShopLayout previous() {
		return states[(ordinal() - 1 + states.length) % states.length];
	}

	public ShopLayout next() {
		return states[(this.ordinal() + 1) % states.length];
	}
}
