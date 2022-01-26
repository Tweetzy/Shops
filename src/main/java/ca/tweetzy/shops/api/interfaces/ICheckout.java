package ca.tweetzy.shops.api.interfaces;

import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.ShopItem;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 25 2022
 * Time Created: 9:53 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface ICheckout {

	Shop getShop();

	ShopItem getShopItem();

	int getPurchaseQty();

	double calculateSellPrice();

	double calculateBuyPrice();

	void incrementQty(final int amount);

	void decrementQty(final int amount);
}
