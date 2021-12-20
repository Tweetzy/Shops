package ca.tweetzy.shops.api;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.interfaces.IShop;
import ca.tweetzy.shops.model.ShopManager;
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

	/**
	 * Checks whether a shop exists by its id
	 *
	 * @param shopId is the {@link IShop} id
	 * @return true if the shop exists
	 */
	public boolean doesShopExists(@NonNull final String shopId) {
		return SHOP_MANAGER.doesShopExists(shopId);
	}
}
