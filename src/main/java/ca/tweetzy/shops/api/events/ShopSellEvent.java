package ca.tweetzy.shops.api.events;

import ca.tweetzy.shops.shop.ShopItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * The current file has been created by Kiran Hart
 * Date Created: April 04 2021
 * Time Created: 5:31 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */

@Getter
@Setter
public class ShopSellEvent extends Event implements Listener {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private Player seller;
    private ShopItem shopItem;
    private int quantity;

    public ShopSellEvent(Player seller, ShopItem shopItem, int quantity) {
        this.seller = seller;
        this.shopItem = shopItem;
        this.quantity = quantity;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
