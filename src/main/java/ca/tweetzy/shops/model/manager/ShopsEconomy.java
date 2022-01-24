package ca.tweetzy.shops.model.manager;

import ca.tweetzy.shops.api.ShopCurrency;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 24 2022
 * Time Created: 11:32 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@UtilityClass
public final class ShopsEconomy {

	public boolean hasBalance(@NonNull final Player player, @NonNull final ShopCurrency currency, final double amount) {
		return currency.isEnabled() && currency.has(player, amount);
	}

	public boolean withdraw(@NonNull final Player player, @NonNull final ShopCurrency currency, final double amount) {
		return currency.isEnabled() && currency.withdraw(player, amount);
	}

	public boolean deposit(@NonNull final Player player, @NonNull final ShopCurrency currency, final double amount) {
		return currency.isEnabled() && currency.deposit(player, amount);
	}
}
