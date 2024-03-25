package ca.tweetzy.shops.impl.cart;

import ca.tweetzy.shops.api.cart.CartContent;
import ca.tweetzy.shops.api.shop.ShopContent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class CartItem implements CartContent {

	private final ShopContent item;
	private int quantity;

	@Override
	public ShopContent getItem() {
		return this.item;
	}

	@Override
	public int getQuantity() {
		return this.quantity;
	}

	@Override
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public void addQuantity(int quantity) {
		this.quantity += quantity;
	}

	@Override
	public void removeQuantity(int quantity) {
		final int newQty = this.quantity - quantity;
		if (newQty <= 0)
			return;

		this.quantity = newQty;
	}
}
