package ca.tweetzy.shops.impl.cart;

import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.shops.api.cart.Cart;
import ca.tweetzy.shops.api.cart.CartContent;
import ca.tweetzy.shops.api.currency.TransactionResult;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.List;

@AllArgsConstructor
public final class ShoppingCart implements Cart {

	private List<CartContent> items;

	public static ShoppingCart of(@NonNull final List<CartContent> cartItems) {
		return new ShoppingCart(cartItems);
	}

	public static ShoppingCart of(@NonNull final CartContent... cartItems) {
		return new ShoppingCart(List.of(cartItems));
	}

	@Override
	public List<CartContent> getItems() {
		return this.items;
	}

	@Override
	public TransactionResult executePurchase(@NonNull Player player) {
		for (CartContent cartItem : this.items) {
			final TransactionResult transactionResult = cartItem.executePurchase(player);
			if (transactionResult == TransactionResult.FAILED_NO_MONEY)
				Common.broadcast("NO MONEY");
		}

		return TransactionResult.SUCCESS;
	}
}
