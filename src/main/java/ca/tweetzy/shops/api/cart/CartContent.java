package ca.tweetzy.shops.api.cart;

import ca.tweetzy.shops.api.shop.ShopContent;

public interface CartContent {

	ShopContent getItem();

	int getQuantity();

	void setQuantity(final int quantity);
	void addQuantity(final int quantity);
	void removeQuantity(final int quantity);
}
