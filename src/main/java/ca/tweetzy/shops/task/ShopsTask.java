package ca.tweetzy.shops.task;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.enums.ShopItemQuantityType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 20 2022
 * Time Created: 12:11 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class ShopsTask extends BukkitRunnable {

	public ShopsTask() {
		runTaskTimerAsynchronously(Shops.getInstance(), 0, 20);
	}

	@Override
	public void run() {
		if (!Shops.getInstance().isEnabled()) return;
		if (Shops.isReloading()) return;

		Shops.getShopManager().getShops().forEach(shop -> shop.getShopItems().forEach(shopItem -> {
			if (shopItem.getQuantityType() != ShopItemQuantityType.LIMITED && shopItem.getStock() != 0) return;

			shopItem.getRefillTimes().forEach(refillTime -> {

			});
		}));
	}
}
