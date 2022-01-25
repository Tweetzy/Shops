package ca.tweetzy.shops.impl;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopCurrency;
import ca.tweetzy.shops.api.interfaces.shop.IShop;
import ca.tweetzy.shops.api.interfaces.shop.IShopItem;
import ca.tweetzy.tweety.collection.SerializedMap;
import ca.tweetzy.tweety.model.ConfigSerializable;
import ca.tweetzy.tweety.model.Tuple;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 8:55 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public final class Shop implements IShop, ConfigSerializable {

	private final String id;
	private SmartItem icon;
	private String displayName;
	private String description;
	private ShopCurrency currency;
	private ShopDisplay display;
	private ShopSettings settings;
	private List<ShopItem> items;

	public static Shop empty() {
		return new Shop("shops-plugin-empty-placeholder", new SmartItem(""), "", "", Shops.getCurrencyManager().getCurrency("Vault"), ShopDisplay.empty(), ShopSettings.empty(), Collections.emptyList());
	}

	@Override
	public @NonNull String getId() {
		return this.id;
	}

	@Override
	public @NonNull SmartItem getIcon() {
		return this.icon;
	}

	@Override
	public void setIcon(@NonNull SmartItem icon) {
		this.icon = icon;
	}

	@Override
	public @NonNull String getDisplayName() {
		return this.displayName;
	}

	@Override
	public void setDisplayName(@NonNull String displayName) {
		this.displayName = displayName;
	}

	@Override
	public @NonNull String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(@NonNull String description) {
		this.description = description;
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
	public @NonNull ShopDisplay getDisplay() {
		return this.display;
	}

	@Override
	public @NonNull ShopSettings getSettings() {
		return this.settings;
	}

	@Override
	public @NonNull List<ShopItem> getShopItems() {
		return this.items;
	}

	@Override
	public SerializedMap serialize() {
		return SerializedMap.ofArray(
				"id", this.id,
				"icon", this.icon,
				"display name", this.displayName,
				"description", this.description,
				"currency", new Tuple<>(this.currency.getPluginName(), this.currency.getName()),
				"display", this.display,
				"settings", this.settings,
				"items", this.items
		);
	}

	public static Shop deserialize(SerializedMap map) {
		final Tuple<String, String> currencyValues = map.getTuple("currency", String.class, String.class);

		return new Shop(
				map.getString("id"),
				map.get("icon", SmartItem.class),
				map.getString("display name"),
				map.getString("description"),
				currencyValues.getKey().equalsIgnoreCase("UltraEconomy") ? Shops.getCurrencyManager().getCurrency(currencyValues.getKey(), currencyValues.getValue()) : Shops.getCurrencyManager().getCurrency(currencyValues.getKey()),
				map.get("display", ShopDisplay.class),
				map.get("settings", ShopSettings.class),
				new ArrayList<>(map.getList("items", ca.tweetzy.shops.impl.ShopItem.class))
		);
	}
}
