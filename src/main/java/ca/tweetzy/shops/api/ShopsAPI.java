package ca.tweetzy.shops.api;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.interfaces.shop.IShop;
import ca.tweetzy.shops.impl.PriceMap;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.model.manager.CurrencyManager;
import ca.tweetzy.shops.model.manager.PriceMapManager;
import ca.tweetzy.shops.model.manager.ShopManager;
import ca.tweetzy.tweety.collection.StrictList;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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
	private final PriceMapManager PRICE_MAP_MANAGER = Shops.getPriceMapManager();

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
	 * Get a {@link Shop} by its id
	 *
	 * @param id is the id of the shop
	 * @return the {@link Shop} or null if not found
	 */
	public Shop getShop(@NonNull final String id) {
		return SHOP_MANAGER.getShop(id);
	}

	/**
	 * Used to add a {@link Shop} to the storage list
	 *
	 * @param shop is the {@link Shop} being added
	 */
	public void addShop(@NonNull final Shop shop) {
		SHOP_MANAGER.addShop(shop);
	}

	/**
	 * Used to remove a {@link Shop} from the storage list
	 *
	 * @param id is the id of the shop being removed
	 */
	public void removeShop(@NonNull final String id) {
		SHOP_MANAGER.removeShop(id);
	}

	/**
	 * Used to completely delete a shop from file
	 *
	 * @param id is the id of the shop
	 */
	public void deleteShop(@NonNull final String id) {
		SHOP_MANAGER.deleteShop(id);
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
	 * @param currency is the new {@link ShopCurrency}
	 */
	public void addCurrency(@NonNull final ShopCurrency currency) {
		CURRENCY_MANAGER.addCurrency(currency);
	}

	/**
	 * Used to get a currency by its plugin name
	 *
	 * @param plugin is the owning plugin
	 * @return the {@link ShopCurrency}
	 */
	public ShopCurrency getCurrency(@NonNull final String plugin) {
		return CURRENCY_MANAGER.getCurrency(plugin);
	}

	/**
	 * Get the currency based on its owning plugin && actual name
	 *
	 * @param plugin       is the owning plugin
	 * @param currencyName is the name of the currency
	 * @return the {@link ShopCurrency}
	 */
	public ShopCurrency getCurrency(@NonNull final String plugin, @NonNull final String currencyName) {
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

	/**
	 * Used to get a list of all shop ids
	 *
	 * @return a list of shop ids
	 */
	public List<String> getShopIds() {
		return SHOP_MANAGER.getShopIds();
	}

	/**
	 * Used to get all loaded currencies
	 *
	 * @return a list of {@link ShopCurrency}s
	 */
	public StrictList<ShopCurrency> getCurrencies() {
		return CURRENCY_MANAGER.getCurrencies();
	}

	/**
	 * Get the entire price map
	 *
	 * @return a list of all {@link PriceMap}s
	 */
	public List<PriceMap> getPriceMap() {
		return PRICE_MAP_MANAGER.getPriceMap();
	}

	/**
	 * Get the price map of a specific itemstack
	 * due to the way shops is setup, STONE with a stack size 64
	 * is going to be a different price map than STONE with a stack size of 32
	 *
	 * It's recommended you use this method to find the worth of
	 * item stacks since it provides the {@link ShopCurrency} in which the item uses
	 *
	 * @return the found {@link PriceMap} or nll
	 */
	public PriceMap getPriceMap(@NonNull final ItemStack itemStack) {
		return PRICE_MAP_MANAGER.getPriceMap(itemStack);
	}

	/**
	 * Get the sell price of a specific itemstack, it doesn't take the
	 * {@link ShopCurrency} into account, if you need to know which
	 * currency the item is using, see {@link #getPriceMap(ItemStack) }
	 *
	 * @param itemStack is the {@link ItemStack} being checked
	 * @return 0 or the found sell price
	 */
	public double getSellPrice(@NonNull final ItemStack itemStack) {
		final PriceMap found = getPriceMap(itemStack);
		return found == null ? 0D : found.getSellPrice();
	}

	/**
	 * Get the buy price of a specific itemstack, it doesn't take the
	 * {@link ShopCurrency} into account, if you need to know which
	 * currency the item is using, see {@link #getPriceMap(ItemStack) }
	 *
	 * @param itemStack is the {@link ItemStack} being checked
	 * @return 0 or the found buy price
	 */
	public double getBuyPrice(@NonNull final ItemStack itemStack) {
		final PriceMap found = getPriceMap(itemStack);
		return found == null ? 0D : found.getBuyPrice();
	}
}
