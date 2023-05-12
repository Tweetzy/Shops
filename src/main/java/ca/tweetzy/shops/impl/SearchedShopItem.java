package ca.tweetzy.shops.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 30 2022
 * Time Created: 11:22 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
@Getter
public final class SearchedShopItem {

	private final Shop shop;
	private final ShopItem shopItem;
}
