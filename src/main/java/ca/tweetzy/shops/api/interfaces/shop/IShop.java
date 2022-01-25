package ca.tweetzy.shops.api.interfaces.shop;

import ca.tweetzy.shops.api.ShopCurrency;
import ca.tweetzy.shops.impl.ShopDisplay;
import ca.tweetzy.shops.impl.ShopItem;
import ca.tweetzy.shops.impl.ShopSettings;
import ca.tweetzy.shops.impl.SmartItem;
import lombok.NonNull;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 1:25 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface IShop {

	/**
	 * Get the shop's unique id
	 *
	 * @return the id that of the shop
	 */
	@NonNull String getId();

	/**
	 * Get the shop icon
	 *
	 * @return the {@link SmartItem}
	 */
	@NonNull SmartItem getIcon();

	/**
	 * Set the shop icon
	 *
	 * @param icon is the new {@link SmartItem}
	 */
	void setIcon(@NonNull final SmartItem icon);

	/**
	 * Get the display name of the shop
	 *
	 * @return the display name of the shop
	 */
	@NonNull String getDisplayName();

	/**
	 * Set the shop's display name
	 *
	 * @param displayName is the new display name
	 */
	void setDisplayName(@NonNull final String displayName);

	/**
	 * Get the description of the shop
	 *
	 * @return the shop description
	 */
	@NonNull String getDescription();

	/**
	 * Set the shop description
	 *
	 * @param description is the new description
	 */
	void setDescription(@NonNull final String description);

	/**
	 * Get the currency that is used for the shop
	 *
	 * @return the used {@link ShopCurrency}
	 */
	@NonNull ShopCurrency getCurrency();

	/**
	 * Set the currency for the shop
	 *
	 * @param currency is the new {@link ShopCurrency}
	 */
	void setCurrency(@NonNull final ShopCurrency currency);

	/**
	 * Get the shop display options
	 *
	 * @return the {@link ShopDisplay}
	 */
	@NonNull ShopDisplay getDisplay();

	/**
	 * Get the shop settings
	 *
	 * @return the {@link ShopSettings}
	 */
	@NonNull ShopSettings getSettings();

	/**
	 * Get the items the shop contains
	 *
	 * @return a list of the shop {@link ShopItem}
	 */
	@NonNull List<ShopItem> getShopItems();
}
