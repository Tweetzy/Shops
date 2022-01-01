package ca.tweetzy.shops.api.interfaces.shop;

import ca.tweetzy.shops.api.ShopCurrency;
import ca.tweetzy.shops.api.enums.ShopItemQuantityType;
import ca.tweetzy.shops.api.enums.ShopItemType;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 1:36 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface IShopItem {

	/**
	 * The item being sold
	 *
	 * @return the {@link ItemStack} being sold
	 */
	@NonNull ItemStack getItem();

	/**
	 * A list of commands that will be executed
	 * when the user purchases this item
	 *
	 * @return a list of commands to be executed
	 */
	List<String> getCommands();

	/**
	 * Get the purchase price of the item
	 *
	 * @return the cost to buy the item
	 */
	double getBuyPrice();

	/**
	 * Set the purchase price of the item
	 *
	 * @param price is the new purchase price
	 */
	void setBuyPrice(final double price);

	/**
	 * Get the sell price of the item
	 *
	 * @return the sell amount
	 */
	double getSellPrice();

	/**
	 * Set the sell price of the item
	 *
	 * @param price is the new sell price
	 */
	void setSellPrice(final double price);

	/**
	 * Get the type of shop item
	 *
	 * @return the {@link ShopItemType}
	 */
	@NonNull ShopItemType getType();

	/**
	 * Set the shop item type
	 *
	 * @param shopItemType is the new {@link ShopItemType}
	 */
	void setType(@NonNull final ShopItemType shopItemType);

	/**
	 * Get the shop item quantity type
	 *
	 * @return the {@link ShopItemQuantityType}
	 */
	@NonNull ShopItemQuantityType getQuantityType();

	/**
	 * Set the shop item quantity type
	 *
	 * @param shopItemQuantityType is the new {@link ShopItemQuantityType}
	 */
	void setQuantityType(@NonNull final ShopItemQuantityType shopItemQuantityType);

	/**
	 * Get the currency that is used for the  item
	 *
	 * @return the used {@link ShopCurrency}
	 */
	@NonNull ShopCurrency getCurrency();

	/**
	 * Set the currency for the item
	 *
	 * @param currency is the new {@link ShopCurrency}
	 */
	void setCurrency(@NonNull final ShopCurrency currency);
}
