package ca.tweetzy.shops.impl.manager;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.manager.ListManager;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.api.shop.ShopContent;
import lombok.NonNull;

import java.util.function.Consumer;

public final class ShopContentManager extends ListManager<ShopContent> {

	public ShopContentManager() {
		super("Shop Content");
	}


	/*
	=================== DATABASE CALLS ===================
	 */

	public void create(@NonNull final Shop shop, ShopContent shopContent, @NonNull final Consumer<Boolean> created) {
		shopContent.store(storedContent -> {
			if (storedContent != null) {
				add(shopContent);
				shop.getContent().add(storedContent);
				created.accept(true);
			} else {
				created.accept(false);
			}
		});
	}

	@Override
	public void load() {
		clear();

		Shops.getDataManager().getServerShopContents((error, result) -> {
			if (error == null){
				// add to actual shop
				//					item.get
				result.forEach(this::add);
			}
		});
	}
}
