package ca.tweetzy.shops.api.events;

import ca.tweetzy.shops.shop.CartItem;
import ca.tweetzy.shops.shop.ShopItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: April 04 2021
 * Time Created: 5:40 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */

@Getter
@Setter
public class ShopBuyEvent extends Event implements Listener {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private Player buyer;
    private ShopItem shopItem;
    private List<CartItem> cartItems;

    private int quantity;
    private boolean fromCart;

    public ShopBuyEvent(Player buyer, ShopItem shopItem, int quantity) {
        this.buyer = buyer;
        this.shopItem = shopItem;
        this.quantity = quantity;
        this.fromCart = false;
        this.cartItems = null;
    }

    public ShopBuyEvent(Player buyer, List<CartItem> cartItems) {
        this(buyer, null, 0);
        this.cartItems = cartItems;
        this.fromCart = true;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
