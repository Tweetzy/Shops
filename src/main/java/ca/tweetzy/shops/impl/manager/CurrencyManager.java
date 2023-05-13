package ca.tweetzy.shops.impl.manager;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.currency.AbstractCurrency;
import ca.tweetzy.shops.api.manager.ListManager;
import ca.tweetzy.shops.impl.currency.ItemCurrency;
import ca.tweetzy.shops.impl.currency.VaultCurrency;
import ca.tweetzy.shops.model.currency.FundsEconomyLoader;
import ca.tweetzy.shops.model.currency.UltraEconomyLoader;
import ca.tweetzy.shops.settings.Settings;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public final class CurrencyManager extends ListManager<AbstractCurrency> {

	public CurrencyManager() {
		super("Currency");
	}

	public AbstractCurrency locateCurrency(@NonNull final String owningPlugin, @NonNull final String currencyName) {
		return getManagerContent().stream().filter(currency -> currency.getOwningPlugin().equals(owningPlugin) && currency.getCurrencyName().equals(currencyName)).findFirst().orElse(null);
	}

	public boolean has(@NonNull final OfflinePlayer offlinePlayer, @NonNull final String owningPlugin, @NonNull final String currencyName, final double amount) {
		if (owningPlugin.equalsIgnoreCase("vault") || currencyName.equalsIgnoreCase("vault"))
			return Shops.getEconomy().has(offlinePlayer, amount);

		return locateCurrency(owningPlugin, currencyName).has(offlinePlayer, amount);
	}

	public boolean withdraw(@NonNull final OfflinePlayer offlinePlayer, @NonNull final String owningPlugin, @NonNull final String currencyName, final double amount) {
		if (owningPlugin.equalsIgnoreCase("vault") || currencyName.equalsIgnoreCase("vault")) {
			Shops.getEconomy().withdrawPlayer(offlinePlayer, amount);
			return true;
		}

		return locateCurrency(owningPlugin, currencyName).withdraw(offlinePlayer, amount);
	}

	public boolean deposit(@NonNull final OfflinePlayer offlinePlayer, @NonNull final String owningPlugin, @NonNull final String currencyName, final double amount) {
		if (owningPlugin.equalsIgnoreCase("vault") || currencyName.equalsIgnoreCase("vault")) {
			Shops.getEconomy().depositPlayer(offlinePlayer, amount);
			return true;
		}

		return locateCurrency(owningPlugin, currencyName).deposit(offlinePlayer, amount);
	}

	public boolean has(@NonNull final OfflinePlayer offlinePlayer, @NonNull final ItemStack itemStack, final int amount) {
		return ((ItemCurrency) locateCurrency("Shops", "Item")).has(offlinePlayer, amount, itemStack);

	}

	public boolean withdraw(@NonNull final OfflinePlayer offlinePlayer, @NonNull final ItemStack itemStack, final int amount) {
		return ((ItemCurrency) locateCurrency("Shops", "Item")).withdraw(offlinePlayer, amount, itemStack);
	}

	public boolean deposit(@NonNull final OfflinePlayer offlinePlayer, @NonNull final ItemStack itemStack, final int amount) {
		return ((ItemCurrency) locateCurrency("Shops", "Item")).deposit(offlinePlayer, amount, itemStack);
	}

	@Override
	public void load() {
		clear();

		if (Settings.CURRENCY_USE_ITEM_ONLY.getBoolean()) {
			add(new ItemCurrency());
			return;
		}

		// add vault by default
		add(new VaultCurrency());
		add(new ItemCurrency());

		// load currencies from providers that allow multiple currencies
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("UltraEconomy"))
			new UltraEconomyLoader().getCurrencies().forEach(this::add);

		if (Bukkit.getServer().getPluginManager().isPluginEnabled("Funds"))
			new FundsEconomyLoader().getCurrencies().forEach(this::add);
	}
}
