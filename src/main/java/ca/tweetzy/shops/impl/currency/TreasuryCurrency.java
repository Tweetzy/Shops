package ca.tweetzy.shops.impl.currency;

import ca.tweetzy.shops.api.ShopCurrency;
import lombok.NonNull;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 26 2022
 * Time Created: 12:01 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class TreasuryCurrency extends ShopCurrency {

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public @NonNull String getPluginName() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean withdraw(@NonNull Player player, double amount) {
		return false;
	}

	@Override
	public boolean deposit(@NonNull Player player, double amount) {
		return false;
	}

	@Override
	public boolean has(@NonNull Player player, double amount) {
		return false;
	}
}
