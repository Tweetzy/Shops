package ca.tweetzy.shops.api;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.interfaces.shop.IShop;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.model.CurrencyManager;
import ca.tweetzy.shops.model.ShopManager;
import ca.tweetzy.tweety.collection.StrictList;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 9:31 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@UtilityClass
public final class ShopsAPI {

	private final ShopManager SHOP_MANAGER = Shops.getShopManager();
	private final CurrencyManager CURRENCY_MANAGER = Shops.getCurrencyManager();

	/**
	 * Checks whether a shop exists by its id
	 *
	 * @param shopId is the {@link IShop} id
	 * @return true if the shop exists
	 */
	public boolean doesShopExists(@NonNull final String shopId) {
		return SHOP_MANAGER.doesShopExists(shopId);
	}

	/**
	 * Used to create a new shop and save it to the file
	 *
	 * @param id   is the id of the new shop
	 * @param desc is the desc of the new shop
	 */
	public void createShop(@NonNull final String id, @NonNull final String desc) {
		SHOP_MANAGER.createShop(id, desc);
	}

	/**
	 * Add a new currency to the currency list
	 *
	 * @param currency is the new {@link AbstractShopCurrency}
	 */
	public void addCurrency(@NonNull final AbstractShopCurrency currency) {
		CURRENCY_MANAGER.addCurrency(currency);
	}

	/**
	 * Used to get a currency by its plugin name
	 *
	 * @param plugin is the owning plugin
	 * @return the {@link AbstractShopCurrency}
	 */
	public AbstractShopCurrency getCurrency(@NonNull final String plugin) {
		return CURRENCY_MANAGER.getCurrency(plugin);
	}

	/**
	 * Get the currency based on its owning plugin && actual name
	 *
	 * @param plugin       is the owning plugin
	 * @param currencyName is the name of the currency
	 * @return the {@link AbstractShopCurrency}
	 */
	public AbstractShopCurrency getCurrency(@NonNull final String plugin, @NonNull final String currencyName) {
		return CURRENCY_MANAGER.getCurrency(plugin, currencyName);
	}

	/**
	 * Used to get all loaded shops
	 *
	 * @return a list of {@link Shop}s
	 */
	public StrictList<Shop> getShops() {
		return SHOP_MANAGER.getShops();
	}
}
