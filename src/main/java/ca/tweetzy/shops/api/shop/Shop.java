package ca.tweetzy.shops.api.shop;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.*;
import lombok.NonNull;

import java.util.List;

public interface Shop extends Identifiable<String>, Displayable, Trackable, Synchronize, Storeable<Shop> {

	ShopOptions getShopOptions();

	List<ShopContent> getContent();

	default void addContent(@NonNull ShopContent shopContent) {
		Shops.getShopContentManager().create(this, shopContent, created -> {});
	}
}
