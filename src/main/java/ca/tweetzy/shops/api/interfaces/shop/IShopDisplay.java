package ca.tweetzy.shops.api.interfaces.shop;

import ca.tweetzy.shops.api.enums.ShopLayout;
import ca.tweetzy.tweety.collection.StrictList;
import ca.tweetzy.tweety.collection.StrictMap;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 2:28 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface IShopDisplay {

	/**
	 * Get the layout of the shop
	 *
	 * @return the {@link ShopLayout}
	 */
	@NonNull ShopLayout getShopLayout();

	/**
	 * Set the layout mode of the shop
	 *
	 * @param shopLayout is the new {@link ShopLayout}
	 */
	void setShopLayout(@NonNull final ShopLayout shopLayout);

	/**
	 * Get the background item of the shop
	 *
	 * @return the {@link CompMaterial} background item
	 */
	@NonNull CompMaterial getBackgroundItem();

	/**
	 * Set the new shop background
	 *
	 * @param material is the new {@link CompMaterial}
	 */
	void setBackgroundItem(@NonNull final CompMaterial material);

	/**
	 * A list of slots that will be filled with {@link IShopItem}s
	 *
	 * @return a list of slot numbers
	 */
	@NonNull StrictList<Integer> getShopItemSlots();

	/**
	 * A list of items that are placed within the shop menu that
	 * serve no purpose other than being design items
	 *
	 * @return a map of slot -> material designs
	 */
	@NonNull StrictMap<Integer, CompMaterial> getDecorationItems();

	/**
	 * The slot in which the shop will be placed in
	 * inside of the listing gui
	 *
	 * @return the slot number
	 */
	int getMenuSlot();

	/**
	 * Set the slot of the shop
	 *
	 * @param slot is the new slot
	 */
	void setMenuSlot(final int slot);

	/**
	 * Get the page the shop should appear on
	 *
	 * @return the page number
	 */
	int getMenuPage();

	/**
	 * Set the page of the shop
	 *
	 * @param page is the page
	 */
	void setMenuPage(final int page);
}
