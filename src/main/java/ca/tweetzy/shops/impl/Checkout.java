package ca.tweetzy.shops.impl;

import ca.tweetzy.shops.api.interfaces.ICheckout;
import ca.tweetzy.shops.settings.Settings;
import lombok.AllArgsConstructor;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 25 2022
 * Time Created: 9:56 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public final class Checkout implements ICheckout {

	private final Shop shop;
	private final ShopItem shopItem;
	private int purchaseQty;

	@Override
	public Shop getShop() {
		return this.shop;
	}

	@Override
	public ShopItem getShopItem() {
		return this.shopItem;
	}

	@Override
	public int getPurchaseQty() {
		return this.purchaseQty;
	}

	@Override
	public void incrementQty(int amount) {
		this.purchaseQty += amount;
	}

	@Override
	public void decrementQty(int amount) {
		this.purchaseQty = Math.max(1, purchaseQty - amount);
	}

	@Override
	public void setTotalQty(int amount) {
		this.purchaseQty = amount;
	}

	@Override
	public double calculateSellPrice() {
		return this.getTaxedSaleTotal();
	}

	@Override
	public double calculateBuyPrice() {
		final double discount = this.getTaxedBuyTotal() * (this.shop.getSettings().getDiscount() / 100D);
		return this.getTaxedBuyTotal() - discount;
	}

	private double getTaxedBuyTotal() {
		final double pre = this.purchaseQty * this.shopItem.getBuyPrice();
		return ((Settings.TAX / 100D) * pre) + pre;
	}

	private double getTaxedSaleTotal() {
		final double pre = this.purchaseQty * this.shopItem.getSellPrice();
		return pre - ((Settings.TAX / 100D) * pre);
	}
}
