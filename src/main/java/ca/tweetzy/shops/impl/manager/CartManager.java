package ca.tweetzy.shops.impl.manager;

import ca.tweetzy.shops.api.cart.Cart;
import ca.tweetzy.shops.api.manager.KeyValueManager;
import ca.tweetzy.shops.impl.cart.ShoppingCart;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public final class CartManager extends KeyValueManager<UUID, Cart> {

	public CartManager() {
		super("Cart");
	}

	public Cart getOrAdd(@NonNull final Player player) {
		if (this.managerContent.containsKey(player.getUniqueId()))
			return this.get(player.getUniqueId());

		final Cart cart = new ShoppingCart(new ArrayList<>());
		this.managerContent.put(player.getUniqueId(), cart);

		return cart;
	}

	@Override
	public void load() {
		// does not need loading since it's not saved between restarts
	}
}
