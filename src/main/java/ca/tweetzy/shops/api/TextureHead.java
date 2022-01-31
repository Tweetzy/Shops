package ca.tweetzy.shops.api;

import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 22 2021
 * Time Created: 9:35 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public abstract class TextureHead {

	public abstract boolean enabled();

	public abstract ItemStack getItem(final int id);
}
