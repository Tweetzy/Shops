package ca.tweetzy.shops.model;

import ca.tweetzy.shops.api.ShopsAPI;
import ca.tweetzy.shops.api.enums.ShopLayout;
import ca.tweetzy.shops.api.enums.ShopState;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.ShopDisplay;
import ca.tweetzy.shops.impl.ShopSettings;
import ca.tweetzy.shops.impl.SmartItem;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.collection.StrictList;
import ca.tweetzy.tweety.collection.StrictMap;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 2:24 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class ShopManager extends Manager {

	private final StrictMap<String, Shop> shops = new StrictMap<>();

	public boolean doesShopExists(@NonNull final String shopId) {
		return this.shops.containsKey(shopId);
	}

	public void addShop(@NonNull final Shop shop) {
		this.shops.put(shop.getId(), shop);
	}

	public void removeShop(@NonNull final String id) {
		this.shops.removeWeak(id);
	}

	public void deleteShop(@NonNull final String id) {
		this.removeShop(id);
		ShopsData.getInstance().save(new ArrayList<>(this.shops.values()));

	}

	public Shop getShop(@NonNull final String id) {
		return this.shops.getOrDefault(id, null);
	}

	public StrictList<Shop> getShops() {
		return new StrictList<>(this.shops.values());
	}

	public List<String> getShopIds() {
		return new ArrayList<>(this.shops.keySet());
	}

	public void createShop(@NonNull final String id, @NonNull final String desc) {
		final Shop shop = new Shop(
				id,
				new SmartItem(CompMaterial.CHEST.name()),
				id,
				desc,
				ShopsAPI.getCurrency("Vault"),
				new ShopDisplay(ShopLayout.AUTOMATIC, CompMaterial.BLACK_STAINED_GLASS_PANE, new StrictList<>(), new StrictMap<>(), -1, -1),
				new ShopSettings(false, ShopState.BUY_AND_SELL, false, false, false),
				new ArrayList<>()
		);

		this.addShop(shop);
		ShopsData.getInstance().save(new ArrayList<>(this.shops.values()));
	}

	@Override
	public void load() {
		ShopsData.getInstance().getShops().forEach(this::addShop);
	}
}
