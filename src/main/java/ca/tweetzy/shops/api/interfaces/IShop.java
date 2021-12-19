package ca.tweetzy.shops.api.interfaces;

import ca.tweetzy.shops.api.interfaces.smart.ISmartItem;
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
	 * @return the {@link ISmartItem}
	 */
	@NonNull ISmartItem getIcon();

	/**
	 * Set the shop icon
	 *
	 * @param icon is the new {@link ISmartItem}
	 */
	void setIcon(@NonNull final ISmartItem icon);

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
	 * @return the used {@link ICurrency}
	 */
	@NonNull ICurrency getCurrency();

	/**
	 * Set the currency for the shop
	 *
	 * @param currency is the new {@link ICurrency}
	 */
	void setCurrency(@NonNull final ICurrency currency);

	/**
	 * Get the items the shop contains
	 *
	 * @return a list of the shop {@link IShopItem}
	 */
	@NonNull List<IShopItem> getShopItems();
}
