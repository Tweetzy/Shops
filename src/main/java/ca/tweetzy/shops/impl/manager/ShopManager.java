package ca.tweetzy.shops.impl.manager;

import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.manager.KeyValueManager;
import ca.tweetzy.shops.api.shop.Shop;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class ShopManager extends KeyValueManager<String, Shop> {

	public ShopManager() {
		super("Shop");
	}

	public boolean isShopIdTaken(@NonNull final String suggestedId) {
		return this.managerContent.containsKey(suggestedId.toLowerCase());
	}

		/*
	=================== DATABASE CALLS ===================
	 */

	public void create(@NonNull final Shop shop, @NonNull final Consumer<Boolean> created) {
		shop.store(storedShop -> {
			if (storedShop != null) {
				add(shop.getId().toLowerCase(), shop);
				created.accept(true);
			} else {
				created.accept(false);
			}
		});
	}

	@Override
	public void load() {
		clear();

		Shops.getDataManager().getServerShops((error, result) -> {
			if (error == null)
				result.forEach(shop -> add(shop.getId().toLowerCase(), shop));
		});
	}
}
