package ca.tweetzy.shops.api.events;

import ca.tweetzy.shops.shop.ShopItem;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: April 04 2021
 * Time Created: 5:31 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */

public class ShopSellEvent extends ShopEvent {

    public ShopSellEvent(Player player, ShopItem shopItem, int quantity) {
        super(player, shopItem, false, quantity);
    }
}
