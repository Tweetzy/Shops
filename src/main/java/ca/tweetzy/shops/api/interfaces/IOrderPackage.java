package ca.tweetzy.shops.api.interfaces;

import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 27 2022
 * Time Created: 1:40 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface IOrderPackage {

	ItemStack getPackageItem();

	ItemStack build();
}
