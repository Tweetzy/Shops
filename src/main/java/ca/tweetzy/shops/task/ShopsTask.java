package ca.tweetzy.shops.task;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.enums.ShopItemQuantityType;
import ca.tweetzy.shops.api.enums.TimePeriod;
import ca.tweetzy.shops.settings.Settings;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 20 2022
 * Time Created: 12:11 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class ShopsTask extends BukkitRunnable {

	public ShopsTask() {
		runTaskTimerAsynchronously(Shops.getInstance(), 0, Settings.SHOP_TICK_TASK_SPEED);
	}

	@Override
	public void run() {
		if (!Shops.getInstance().isEnabled()) return;
		if (Shops.isReloading()) return;

		LocalDateTime dateTime = LocalDateTime.now();

		Shops.getShopManager().getShops().forEach(shop -> shop.getShopItems().forEach(shopItem -> {
			if (shopItem.getQuantityType() != ShopItemQuantityType.LIMITED) return;

			shopItem.getRefillTimes().forEach(refillTime -> {
				if (dateTime.getDayOfWeek() != refillTime.getDay()) return;
				if (dateTime.getHour() != get24Hour(refillTime.getHour(), refillTime.getTimePeriod())) return;
				if (dateTime.getMinute() != refillTime.getMinute()) return;

				shopItem.setCurrentStock(shopItem.getStock());
			});
		}));
	}

	private int get24Hour(int twelveHour, TimePeriod timePeriod) {
		if (timePeriod == TimePeriod.AM)
			return twelveHour == 12 ? 0 : twelveHour;
		return twelveHour == 12 ? 12 : twelveHour + 12;
	}
}
