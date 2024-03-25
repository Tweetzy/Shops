package ca.tweetzy.shops.impl.currency;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.currency.AbstractCurrency;
import ca.tweetzy.shops.settings.Settings;
import org.bukkit.OfflinePlayer;

public final class VaultCurrency extends AbstractCurrency {

	public VaultCurrency() {
		super("Vault", "Vault", Settings.CURRENCY_VAULT_SYMBOL.getString());
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		return Shops.getEconomy().has(player, amount);
	}

	@Override
	public boolean withdraw(OfflinePlayer player, double amount) {
		return Shops.getEconomy().withdrawPlayer(player, amount).transactionSuccess();
	}

	@Override
	public boolean deposit(OfflinePlayer player, double amount) {
		return Shops.getEconomy().depositPlayer(player, amount).transactionSuccess();
	}
}
