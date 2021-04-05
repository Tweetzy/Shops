package ca.tweetzy.shops.api.events;

import ca.tweetzy.shops.shop.CartItem;
import ca.tweetzy.shops.shop.ShopItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: April 04 2021
 * Time Created: 5:40 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */

@Getter
@Setter
public class ShopBuyEvent extends ShopEvent {

    private List<CartItem> cartItems;
    private boolean fromCart;

    public ShopBuyEvent(Player player, List<CartItem> cartItems) {
        super(player, cartItems.get(0), true, cartItems.get(0).getQuantity());
        this.fromCart = true;
        this.cartItems = cartItems;
        this.quantity = cartItems.stream().mapToInt(CartItem::getQuantity).sum();
    }

    public ShopBuyEvent(Player player, ShopItem shopItem, int quantity) {
        super(player, shopItem, true, quantity);
        this.fromCart = false;
        this.cartItems = null;
    }
}
