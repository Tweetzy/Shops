package ca.tweetzy.shops.api;

import ca.tweetzy.shops.api.shop.ShopContent;
import ca.tweetzy.shops.api.shop.ShopContentType;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface Transaction extends Identifiable<UUID>, Storeable<Transaction>, Trackable {

	UUID getKnownContentId();

	String getKnownShopId();

	String getKnownShopName();

	TransactionType getType();

	ShopContentType getContentType();

	UUID getUserUUID();

	String getUserLastKnownName();

	ItemStack getItem(); // if the content was a command item, it'll be a custom icon based on specified content values

	String getCurrency();

	double getFinalPrice();

	int getTransactionQuantity();

	enum TransactionType {

		SELL,
		BUY
	}
}
