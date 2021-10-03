package ca.tweetzy.shops.api.events;

import ca.tweetzy.shops.shop.ShopItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * The current file has been created by Kiran Hart
 * Date Created: April 04 2021
 * Time Created: 9:49 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */

@Getter
@Setter
public class ShopEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	protected boolean cancelled;

	protected Player player;
	protected ShopItem shopItem;
	protected boolean isBuy;
	protected int quantity;

	public ShopEvent(Player player, ShopItem shopItem, boolean isBuy, int quantity) {
		this.player = player;
		this.shopItem = shopItem;
		this.isBuy = isBuy;
		this.quantity = quantity;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
