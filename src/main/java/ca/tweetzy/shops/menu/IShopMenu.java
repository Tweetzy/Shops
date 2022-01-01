package ca.tweetzy.shops.menu;

import ca.tweetzy.shops.impl.Shop;
import lombok.NonNull;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 31 2021
 * Time Created: 6:11 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface IShopMenu {

	@NonNull Shop getShop();
}
