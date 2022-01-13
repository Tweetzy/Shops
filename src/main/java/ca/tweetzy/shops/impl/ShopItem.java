package ca.tweetzy.shops.impl;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopCurrency;
import ca.tweetzy.shops.api.ShopsAPI;
import ca.tweetzy.shops.api.enums.ShopItemQuantityType;
import ca.tweetzy.shops.api.enums.ShopItemType;
import ca.tweetzy.shops.api.interfaces.shop.IShopItem;
import ca.tweetzy.tweety.collection.SerializedMap;
import ca.tweetzy.tweety.model.ConfigSerializable;
import ca.tweetzy.tweety.model.Tuple;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 31 2021
 * Time Created: 6:13 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public final class ShopItem implements IShopItem, ConfigSerializable {

	private ItemStack item;
	private ShopItemType type;
	private ShopItemQuantityType quantityType;
	private List<String> description;
	private ShopCurrency currency;
	private double buyPrice;
	private double sellPrice;
	private int purchaseQty;
	private boolean canBeBought;
	private boolean canBeSold;
	private final List<String> commands;
	private final List<RefillTime> refillTimes;

	public ShopItem() {
		this(CompMaterial.GRASS_BLOCK.toItem(), ShopItemType.ITEM, ShopItemQuantityType.UNLIMITED, Collections.emptyList(), Shops.getCurrencyManager().getCurrency("Vault"), 1.0D, 0.50D, 1, true, true, Collections.emptyList(), Collections.emptyList());
	}

	@Override
	public @NonNull ItemStack getItem() {
		return this.item;
	}

	@Override
	public void setItem(ItemStack item) {
		this.item = item;
	}

	@Override
	public List<String> getCommands() {
		return this.commands;
	}

	@Override
	public List<String> getDescription() {
		return this.description;
	}

	@Override
	public double getBuyPrice() {
		return this.buyPrice;
	}

	@Override
	public void setBuyPrice(double price) {
		this.buyPrice = price;
	}

	@Override
	public double getSellPrice() {
		return this.sellPrice;
	}

	@Override
	public void setSellPrice(double price) {
		this.sellPrice = price;
	}

	@Override
	public int getPurchaseQuantity() {
		return this.purchaseQty;
	}

	@Override
	public void setPurchaseQuantity(int qty) {
		this.purchaseQty = qty;
	}

	@Override
	public boolean canBeBought() {
		return this.canBeBought;
	}

	@Override
	public void setCanBeBought(boolean value) {
		this.canBeBought = value;
	}

	@Override
	public boolean canBeSold() {
		return this.canBeSold;
	}

	@Override
	public void setCanBeSold(boolean value) {
		this.canBeSold = value;
	}

	@Override
	public @NonNull ShopItemType getType() {
		return this.type;
	}

	@Override
	public void setType(@NonNull ShopItemType shopItemType) {
		this.type = shopItemType;
	}

	@Override
	public @NonNull ShopItemQuantityType getQuantityType() {
		return this.quantityType;
	}

	@Override
	public void setQuantityType(@NonNull ShopItemQuantityType shopItemQuantityType) {
		this.quantityType = shopItemQuantityType;
	}

	@Override
	public @NonNull ShopCurrency getCurrency() {
		return this.currency;
	}

	@Override
	public void setCurrency(@NonNull ShopCurrency currency) {
		this.currency = currency;
	}

	@Override
	public @NonNull List<RefillTime> getRefillTimes() {
		return this.refillTimes;
	}

	@Override
	public SerializedMap serialize() {
		return SerializedMap.ofArray(
				"item", this.item,
				"type", this.type,
				"quantity type", this.quantityType,
				"description", this.description,
				"currency", new Tuple<>(this.currency.getPluginName(), this.currency.getName()),
				"buy price", this.buyPrice,
				"sell price", this.sellPrice,
				"purchase quantity", this.purchaseQty,
				"can be bought", this.canBeBought,
				"can be sold", this.canBeSold,
				"commands", this.commands,
				"refill times", this.refillTimes
		);
	}

	public static ShopItem deserialize(SerializedMap map) {
		final Tuple<String, String> currencyValues = map.getTuple("currency", String.class, String.class);


		return new ShopItem(
				map.getItem("item"),
				map.get("type", ShopItemType.class),
				map.get("quantity type", ShopItemQuantityType.class),
				new ArrayList<>(map.getStringList("description")),
				currencyValues.getKey().equalsIgnoreCase("UltraEconomy") ? Shops.getCurrencyManager().getCurrency(currencyValues.getKey(), currencyValues.getValue()) : Shops.getCurrencyManager().getCurrency(currencyValues.getKey()),
				map.getDouble("buy price"),
				map.getDouble("sell price"),
				map.getInteger("purchase quantity"),
				map.getBoolean("can be bought"),
				map.getBoolean("can be sold"),
				map.getStringList("commands"),
				map.getList("refill times", RefillTime.class)
		);
	}
}
