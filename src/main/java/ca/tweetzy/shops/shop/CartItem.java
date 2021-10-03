package ca.tweetzy.shops.shop;

import ca.tweetzy.shops.api.ShopAPI;
import lombok.Getter;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 30 2021
 * Time Created: 6:15 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CartItem extends ShopItem {

	@Getter
	private final int quantity;

	public CartItem(ShopItem shopItem, int quantity) {
		super(shopItem.getShopId(), ShopAPI.getInstance().deserializeItem(shopItem.getItem()), shopItem.getSellPrice(), shopItem.getBuyPrice(), shopItem.isSellOnly(), shopItem.isBuyOnly());
		this.quantity = quantity;
	}
}
