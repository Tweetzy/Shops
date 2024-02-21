package ca.tweetzy.shops.impl.cart;

import ca.tweetzy.shops.api.cart.Cart;
import ca.tweetzy.shops.api.cart.CartContent;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public final class ShoppingCart implements Cart {

	private List<CartContent> items;

	@Override
	public List<CartContent> getItems() {
		return this.items;
	}
}
