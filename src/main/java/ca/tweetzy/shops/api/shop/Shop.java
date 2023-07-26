package ca.tweetzy.shops.api.shop;

import ca.tweetzy.shops.api.*;

import java.util.List;

public interface Shop extends Identifiable<String>, Displayable, Trackable, Synchronize, Storeable<Shop> {

	ShopOptions getShopOptions();

	List<ShopContent> getContent();

}
