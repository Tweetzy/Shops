package ca.tweetzy.shops.api.interfaces;

import ca.tweetzy.shops.api.ShopCurrency;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 07 2022
 * Time Created: 1:51 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface IPriceMap {

	ItemStack getItem();

	double getBuyPrice();

	double getSellPrice();

	ShopCurrency getCurrency();

}
