package ca.tweetzy.shops.api.interfaces.smart;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 1:27 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface ISmartItem {

	/**
	 * The head texture / minecraft material name
	 *
	 * @return the texture string or material name
	 */
	@NonNull String getTexture();

	/**
	 * Build the item based on its texture value
	 *
	 * @return the {@link ItemStack}
	 */
	@NonNull ItemStack get();
}
