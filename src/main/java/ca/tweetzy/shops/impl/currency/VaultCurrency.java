package ca.tweetzy.shops.impl.currency;

import ca.tweetzy.shops.api.interfaces.ICurrency;
import ca.tweetzy.tweety.model.HookManager;
import lombok.NonNull;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 9:00 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class VaultCurrency implements ICurrency {

	@Override
	public boolean isEnabled() {
		return HookManager.isVaultLoaded();
	}

	@Override
	public @NonNull String getPluginName() {
		return "Vault";
	}

	@Override
	public String getName() {
		return "Vault";
	}

	@Override
	public void withdraw(@NonNull Player player, double amount) {
		HookManager.withdraw(player, amount);
	}

	@Override
	public void deposit(@NonNull Player player, double amount) {
		HookManager.deposit(player, amount);
	}

	@Override
	public boolean has(@NonNull Player player, double amount) {
		return HookManager.getBalance(player) >= amount;
	}
}
