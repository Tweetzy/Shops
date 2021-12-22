package ca.tweetzy.shops.impl;

import ca.tweetzy.shops.api.interfaces.*;
import ca.tweetzy.shops.api.interfaces.shop.IShop;
import ca.tweetzy.shops.api.interfaces.shop.IShopDisplay;
import ca.tweetzy.shops.api.interfaces.shop.IShopItem;
import ca.tweetzy.shops.api.interfaces.shop.IShopSettings;
import ca.tweetzy.shops.api.interfaces.ISmartItem;
import ca.tweetzy.tweety.collection.SerializedMap;
import ca.tweetzy.tweety.model.ConfigSerializable;
import lombok.AllArgsConstructor;
import lombok.NonNull;

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
	private ISmartItem icon;
	private String displayName;
	private String description;
	private ICurrency currency;
	private IShopDisplay display;
	private IShopSettings settings;
	private List<IShopItem> items;

	@Override
	public @NonNull String getId() {
		return this.id;
	}

	@Override
	public @NonNull ISmartItem getIcon() {
		return this.icon;
	}

	@Override
	public void setIcon(@NonNull ISmartItem icon) {
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
	public @NonNull ICurrency getCurrency() {
		return this.currency;
	}

	@Override
	public void setCurrency(@NonNull ICurrency currency) {
		this.currency = currency;
	}

	@Override
	public @NonNull IShopDisplay getDisplay() {
		return this.display;
	}

	@Override
	public @NonNull IShopSettings getSettings() {
		return this.settings;
	}

	@Override
	public @NonNull List<IShopItem> getShopItems() {
		return this.items;
	}

	@Override
	public SerializedMap serialize() {
		return SerializedMap.ofArray(
				"id", this.id,
				"icon", this.icon,
				"display name", this.displayName,
				"description", this.description,
				"currency", this.currency,
				"display", this.display,
				"settings", this.settings,
				"items", this.items
		);
	}

	public static Shop deserialize(SerializedMap map) {
		return new Shop(
				map.getString("id"),
				map.get("icon", ISmartItem.class),
				map.getString("display name"),
				map.getString("description"),
				map.get("currency", ICurrency.class),
				map.get("display", IShopDisplay.class),
				map.get("settings", IShopSettings.class),
				map.getList("items", IShopItem.class)
		);
	}
}
