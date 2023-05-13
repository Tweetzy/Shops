package ca.tweetzy.shops.api.shop;

import ca.tweetzy.shops.api.Identifiable;
import ca.tweetzy.shops.api.Storeable;
import ca.tweetzy.shops.api.Synchronize;
import lombok.NonNull;

import java.util.UUID;

public interface ShopContent extends Identifiable<UUID>, Synchronize, Storeable<ShopContent> {

	@NonNull String getShopId();

	double getPrice();

	void setPrice(final double price);
}
