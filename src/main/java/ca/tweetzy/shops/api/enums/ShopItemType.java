package ca.tweetzy.shops.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 2:47 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public enum ShopItemType {

	COMMAND("Command"),
	ITEM("Item"),
	BOTH("Item & Commands");

	@Getter
	private final String type;

	private static final ShopItemType[] states = values();

	public ShopItemType previous() {
		return states[(ordinal() - 1 + states.length) % states.length];
	}

	public ShopItemType next() {
		return states[(this.ordinal() + 1) % states.length];
	}
}
