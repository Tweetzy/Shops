package ca.tweetzy.shops.impl.currency;

import ca.tweetzy.shops.api.ShopCurrency;
import ca.tweetzy.tweety.Common;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import me.TechsCode.UltraEconomy.UltraEconomy;
import me.TechsCode.UltraEconomy.objects.Account;
import me.TechsCode.UltraEconomy.objects.Currency;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 21 2021
 * Time Created: 10:20 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public final class UltraEconomyCurrency extends ShopCurrency {

	private final String currencyName;

	@Override
	public boolean isEnabled() {
		return Common.doesPluginExist("UltraEconomy");
	}

	@Override
	public @NonNull String getPluginName() {
		return "UltraEconomy";
	}

	@Override
	public String getName() {
		return this.currencyName;
	}

	@Override
	public boolean withdraw(@NonNull Player player, double amount) {
		final Account account = UltraEconomy.getAPI().getAccounts().uuid(player.getUniqueId()).orElse(null);
		if (account == null) return false;

		final Currency currency = UltraEconomy.getAPI().getCurrencies().name(this.currencyName).orElse(null);
		if (currency == null) return false;

		final float oldAmount = account.getBalance(currency).getOnBank();
		account.getBalance(currency).setBank(oldAmount - (float) amount);
		return true;
	}

	@Override
	public boolean deposit(@NonNull Player player, double amount) {
		final Account account = UltraEconomy.getAPI().getAccounts().uuid(player.getUniqueId()).orElse(null);
		if (account == null) return false;

		final Currency currency = UltraEconomy.getAPI().getCurrencies().name(this.currencyName).orElse(null);
		if (currency == null) return false;

		final float oldAmount = account.getBalance(currency).getOnBank();
		account.getBalance(currency).setBank(oldAmount + (float) amount);
		return true;
	}

	@Override
	public boolean has(@NonNull Player player, double amount) {
		final Currency currency = UltraEconomy.getAPI().getCurrencies().name(this.currencyName).orElse(null);
		if (currency == null) return false;

		final Account account = UltraEconomy.getAPI().getAccounts().uuid(player.getUniqueId()).orElse(null);
		return account != null && account.getBalance(currency).getOnBank() >= amount;
	}

}
