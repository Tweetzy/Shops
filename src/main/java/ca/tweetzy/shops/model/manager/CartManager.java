package ca.tweetzy.shops.model.manager;

import ca.tweetzy.shops.impl.Cart;
import ca.tweetzy.shops.impl.Checkout;
import lombok.NonNull;

import java.util.HashMap;
import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 30 2022
 * Time Created: 1:40 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CartManager {

	private final HashMap<UUID, Cart> carts = new HashMap<>();

	public void createCart(@NonNull final UUID player) {
		if (!carts.containsKey(player))
			this.carts.put(player, new Cart());
	}

	public boolean addItemToCart(@NonNull final UUID player, @NonNull final Checkout checkout) {
		this.createCart(player);
		final Cart cart = this.getCart(player);

		if (cart.getCartItems().contains(checkout)) return false;
		if (cart.getCartItems().stream().anyMatch(items -> items.getShopItem().getCurrency() != checkout.getShopItem().getCurrency())) return false;

		cart.getCartItems().add(checkout);
		return true;
	}

	public void deleteCart(@NonNull final UUID player) {
		this.carts.remove(player);
	}

	public Cart getCart(@NonNull final UUID player) {
		return this.carts.getOrDefault(player, null);
	}
}
