package ca.tweetzy.shops.impl.currency;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.shops.api.currency.IconableCurrency;
import me.TechsCode.UltraEconomy.UltraEconomy;
import me.TechsCode.UltraEconomy.objects.Account;
import me.TechsCode.UltraEconomy.objects.Currency;
import org.bukkit.OfflinePlayer;

public final class UltraEconomyCurrency extends IconableCurrency {

	private final Currency currency;

	public UltraEconomyCurrency(String currencyName) {
		super("UltraEconomy", currencyName, "", CompMaterial.PAPER.parseItem());

		this.currency = UltraEconomy.getAPI().getCurrencies().name(currencyName).orElse(null);

		if (this.currency != null) {
			setDisplayName(this.currency.getName());
		}
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		final Account account = UltraEconomy.getAPI().getAccounts().uuid(player.getUniqueId()).orElse(null);
		if (account == null)
			return false;

		return account.getBalance(this.currency).getSum() >= amount;
	}

	@Override
	public boolean withdraw(OfflinePlayer player, double amount) {
		final Account account = UltraEconomy.getAPI().getAccounts().uuid(player.getUniqueId()).orElse(null);
		if (account == null)
			return false;

		final float oldAmount = account.getBalance(this.currency).getOnBank();
		account.getBalance(this.currency).setBank(oldAmount - (float) amount);
		return true;
	}

	@Override
	public boolean deposit(OfflinePlayer player, double amount) {
		final Account account = UltraEconomy.getAPI().getAccounts().uuid(player.getUniqueId()).orElse(null);
		if (account == null)
			return false;

		account.getBalance(this.currency).addBank((float) amount);
		return true;
	}
}

