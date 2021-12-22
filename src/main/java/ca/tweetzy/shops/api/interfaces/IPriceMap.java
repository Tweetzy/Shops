package ca.tweetzy.shops.api.interfaces;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 21 2021
 * Time Created: 10:34 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface IPriceMap {

//	/**
//	 * Get the unique id that represents this
//	 * item within the pricing map, this is used
//	 * to link back from various other stores
//	 *
//	 * @return the uuid
//	 */
//	@NonNull UUID getUUID();

	/**
	 * Get the {@link ItemStack} being tracked
	 *
	 * @return is the {@link ItemStack}
	 */
	@NonNull ItemStack getItemStack();

	/**
	 * Get the sell price of this particular item
	 * the price is per single item
	 *
	 * @return the sell price
	 */
	double getSellPrice();

	/**
	 * Get the buy price of this particular item
	 * the price is per single item
	 *
	 * @return the buy price
	 */
	double getBuyPrice();
}
