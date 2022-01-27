package ca.tweetzy.shops.api.interfaces.shop;

import ca.tweetzy.shops.api.enums.ShopState;
import lombok.NonNull;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 1:37 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface IShopSettings {

	/**
	 * Is the shop public
	 *
	 * @return true if the shop is public
	 */
	boolean isPublic();

	/**
	 * Set the public status
	 *
	 * @param isPublic is the new public state
	 */
	void setPublic(final boolean isPublic);

	/**
	 * Get the shop state
	 *
	 * @return is the {@link ShopState}
	 */
	@NonNull ShopState getShopState();

	/**
	 * Set the shop state
	 *
	 * @param state is the new {@link ShopState}
	 */
	void setShopState(@NonNull final ShopState state);

	/**
	 * Should the custom shop command be enabled
	 *
	 * @return true enabled
	 */
	boolean isUseOpenCommand();

	/**
	 * Set whether the custom open command is used
	 *
	 * @param use is the new value
	 */
	void setUseOpenCommand(final boolean use);

	/**
	 * The custom command that will let the user
	 * directly open this particular shop
	 *
	 * @return the open command
	 */
	@NonNull String getOpenCommand();

	/**
	 * Set the new open command for the shop
	 *
	 * @param command is the new command
	 */
	void setOpenCommand(@NonNull final String command);

	/**
	 * Does the shop require a permission to be seen
	 *
	 * @return true if the player needs a perm to see the shop
	 */
	boolean isRequirePermissionToSee();

	/**
	 * Set whether a permission is needed to see the shop
	 *
	 * @param requirePermissionToSee is if a perm is needed
	 */
	void setRequirePermissionToSee(final boolean requirePermissionToSee);

	/**
	 * Does the shop require a permission to sell items
	 *
	 * @return true if the player needs a perm to sell to shop
	 */
	boolean isRequirePermissionToSell();

	/**
	 * Set whether a permission is needed to sell to the shop
	 *
	 * @param requirePermissionToSell is if a perm is needed
	 */
	void setRequirePermissionToSell(final boolean requirePermissionToSell);

	/**
	 * Does the shop require a permission to buy items
	 *
	 * @return true if the player needs a perm to buy items from the shop
	 */
	boolean isRequirePermissionToBuy();

	/**
	 * Set whether a permission is needed to buy from the shop
	 *
	 * @param requirePermissionToBuy is if a perm is needed to buy from the shop
	 */
	void setRequirePermissionToBuy(final boolean requirePermissionToBuy);

	/**
	 * Get permission required to see the shop
	 *
	 * @return the see permission
	 */
	@NonNull String getSeePermission();

	/**
	 * Get permission required to buy from the shop
	 *
	 * @return the buy permission
	 */
	@NonNull String getBuyPermission();

	/**
	 * Get permission required to sell to the shop
	 *
	 * @return the sell permission
	 */
	@NonNull String getSellPermission();

	/**
	 * Set the see permission of the shop
	 *
	 * @param permission is the new permission
	 */
	void setSeePermission(@NonNull final String permission);

	/**
	 * Set the buy permission of the shop
	 *
	 * @param permission is the new permission
	 */
	void setBuyPermission(@NonNull final String permission);

	/**
	 * Set the sell permission of the shop
	 *
	 * @param permission is the new permission
	 */
	void setSellPermission(@NonNull final String permission);

	double getDiscount();

	void setDiscount(final double discount);

}
