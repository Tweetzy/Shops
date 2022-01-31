package ca.tweetzy.shops.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 2:06 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public enum ShopState {

	SELL_ONLY("Sell Only"),
	BUY_ONLY("Buy Only"),
	BUY_AND_SELL("Sell & Buy");

	@Getter
	private final String state;

	private static final ShopState[] states = values();

	public ShopState previous() {
		return states[(ordinal() - 1 + states.length) % states.length];
	}

	public ShopState next() {
		return states[(this.ordinal() + 1) % states.length];
	}
}
