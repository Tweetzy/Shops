package ca.tweetzy.shops.api.shop;

import ca.tweetzy.shops.api.Identifiable;
import ca.tweetzy.shops.api.Storeable;
import ca.tweetzy.shops.api.Synchronize;
import lombok.NonNull;

import java.util.UUID;

public interface ShopContent extends Identifiable<UUID>, Synchronize, Storeable<ShopContent> {

	@NonNull String getShopId();

	@NonNull ShopContentType getType();

	int getMinimumPurchaseQty();

	void setMinimumPurchaseQty(final int qty);

	double getBuyPrice();

	void setBuyPrice(final double price);

	double getSellPrice();

	void setSellPrice(final double price);

	boolean isAllowBuy();

	boolean isAllowSell();

	void setAllowBuy(final boolean allowBuy);
	void setAllowSell(final boolean allowSell);

}
