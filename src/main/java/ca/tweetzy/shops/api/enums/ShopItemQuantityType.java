package ca.tweetzy.shops.api.enums;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 2:51 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public enum ShopItemQuantityType {

	LIMITED,
	UNLIMITED;

	private static final ShopItemQuantityType[] states = values();

	public ShopItemQuantityType previous() {
		return states[(ordinal() - 1 + states.length) % states.length];
	}

	public ShopItemQuantityType next() {
		return states[(this.ordinal() + 1) % states.length];
	}
}
