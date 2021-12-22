package ca.tweetzy.shops.impl;

import ca.tweetzy.shops.api.enums.ShopLayout;
import ca.tweetzy.shops.api.interfaces.shop.IShopDisplay;
import ca.tweetzy.tweety.collection.SerializedMap;
import ca.tweetzy.tweety.collection.StrictList;
import ca.tweetzy.tweety.collection.StrictMap;
import ca.tweetzy.tweety.model.ConfigSerializable;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 8:55 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
@Builder
public final class ShopDisplay implements IShopDisplay, ConfigSerializable {

	private ShopLayout shopLayout;
	private CompMaterial backgroundItem;
	private StrictList<Integer> shopItemSlots;
	private StrictMap<Integer, CompMaterial> decorationItems;
	private int menuSlot;
	private int menuPage;

	@Override
	public @NonNull ShopLayout getShopLayout() {
		return this.shopLayout;
	}

	@Override
	public void setShopLayout(@NonNull ShopLayout shopLayout) {
		this.shopLayout = shopLayout;
	}

	@Override
	public @NonNull CompMaterial getBackgroundItem() {
		return this.backgroundItem;
	}

	@Override
	public void setBackgroundItem(@NonNull CompMaterial material) {
		this.backgroundItem = material;
	}

	@Override
	public @NonNull StrictList<Integer> getShopItemSlots() {
		return this.shopItemSlots;
	}

	@Override
	public @NonNull StrictMap<Integer, CompMaterial> getDecorationItems() {
		return this.decorationItems;
	}

	@Override
	public int getMenuSlot() {
		return this.menuSlot;
	}

	@Override
	public void setMenuSlot(int slot) {
		this.menuSlot = slot;
	}

	@Override
	public int getMenuPage() {
		return this.menuPage;
	}

	@Override
	public void setMenuPage(int page) {
		this.menuPage = page;
	}

	@Override
	public SerializedMap serialize() {
		return SerializedMap.ofArray(
				"layout", this.shopLayout,
				"background", this.backgroundItem,
				"item slots", this.shopItemSlots,
				"decoration items", this.decorationItems,
				"menu slot", this.menuSlot,
				"menu page", this.menuPage
		);
	}

	public static ShopDisplay deserialize(SerializedMap map) {
		return new ShopDisplay(
				map.get("layout", ShopLayout.class),
				map.getMaterial("background"),
				new StrictList<>(map.getList("item slots", Integer.class)),
				new StrictMap<>(map.getMap("decoration items", Integer.class, CompMaterial.class)),
				map.getInteger("menu slot"),
				map.getInteger("menu page")
		);
	}
}
