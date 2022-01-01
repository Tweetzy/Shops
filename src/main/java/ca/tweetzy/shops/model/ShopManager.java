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
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 2:24 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class ShopManager extends Manager<Collection<Shop>> {

	private final StrictMap<String, Shop> shops = new StrictMap<>();

	public boolean doesShopExists(@NonNull final String shopId) {
		return this.shops.containsKey(shopId.toLowerCase());
	}

	public void addShop(@NonNull final Shop shop) {
		this.shops.put(shop.getId(), shop);
	}

	public void removeShop(@NonNull final String id) {
		this.shops.removeWeak(id.toLowerCase());
	}

	public void deleteShop(@NonNull final String id) {
		this.removeShop(id.toLowerCase());
		ShopsData.getInstance().save(new ArrayList<>(this.shops.values()));

	}

	public Shop getShop(@NonNull final String id) {
		return this.shops.getOrDefault(id.toLowerCase(), null);
	}

	public StrictList<Shop> getShops() {
		return new StrictList<>(this.shops.values());
	}

	public List<String> getShopIds() {
		return new ArrayList<>(this.shops.keySet());
	}

	public void createShop(@NonNull final String id, @NonNull final String desc) {
		final Shop shop = new Shop(
				id.toLowerCase(),
				new SmartItem(CompMaterial.CHEST.name()),
				id,
				desc,
				ShopsAPI.getCurrency("Vault"),
				new ShopDisplay(ShopLayout.AUTOMATIC, CompMaterial.BLACK_STAINED_GLASS_PANE, new StrictList<>(), new StrictMap<>(), -1, -1),
				new ShopSettings(false, ShopState.BUY_AND_SELL, true, id, false, false, false, "shops.see." + id, "shops.buy." + id, "shops.sell." + id),
				new ArrayList<>()
		);

		this.addShop(shop);
		ShopsData.getInstance().save(new ArrayList<>(this.shops.values()));
	}


	@Override
	public void load(Consumer<Collection<Shop>> data) {
		ShopsData.getInstance().getShops().forEach(this::addShop);
		data.accept(this.shops.values());
	}
}
