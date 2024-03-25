package ca.tweetzy.shops.api.cart;

import ca.tweetzy.shops.api.currency.TransactionResult;
import ca.tweetzy.shops.api.shop.ShopContent;
import ca.tweetzy.shops.impl.cart.CartItem;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.List;

public interface Cart {

	List<CartContent> getItems();

	default void addItem(@NonNull final ShopContent content) {
		getItems().add(new CartItem(content, content.getMinimumPurchaseQty()));
	}

	default void addItem(@NonNull final CartContent content) {
		getItems().add(content);
	}

	default void removeItem(@NonNull final CartContent content) {
		getItems().remove(content);
	}

	TransactionResult executePurchase(@NonNull Player player);
	TransactionResult executeSell(@NonNull Player player);

	default void clear() {
		getItems().clear();
	}
}
