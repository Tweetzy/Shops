package ca.tweetzy.shops.api.cart;

import ca.tweetzy.shops.api.shop.ShopContent;
import ca.tweetzy.shops.impl.cart.CartItem;
import lombok.NonNull;

import java.util.List;

public interface Cart {

	List<CartContent> getItems();

	default void addItem(@NonNull final ShopContent content) {
		getItems().add(new CartItem(content, content.getMinimumPurchaseQty()));
	}
}
