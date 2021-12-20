package ca.tweetzy.shops.model;

import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.tweety.collection.StrictMap;
import lombok.NonNull;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 2:24 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class ShopManager {

	private final StrictMap<String, Shop> shops = new StrictMap<>();

	public boolean doesShopExists(@NonNull final String shopId) {
		return this.shops.containsKey(shopId);
	}

}
