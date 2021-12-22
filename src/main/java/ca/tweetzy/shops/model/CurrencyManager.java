package ca.tweetzy.shops.model;

import ca.tweetzy.shops.api.ShopCurrency;
import ca.tweetzy.shops.impl.currency.VaultCurrency;
import ca.tweetzy.tweety.collection.StrictList;
import lombok.NonNull;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 2:24 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CurrencyManager extends Manager {

	private final StrictList<ShopCurrency> currencies = new StrictList<>();

	public void addCurrency(@NonNull final ShopCurrency currency) {
		this.currencies.add(currency);
	}

	public ShopCurrency getCurrency(@NonNull final String plugin) {
		return this.currencies.getSource().stream().filter(currency -> currency.getPluginName().equalsIgnoreCase(plugin)).findFirst().orElse(null);
	}

	public ShopCurrency getCurrency(@NonNull final String plugin, @NonNull final String currencyName) {
		return this.currencies.getSource().stream().filter(currency -> currency.getPluginName().equalsIgnoreCase(plugin) && currency.getName().equals(currencyName)).findFirst().orElse(null);
	}

	@Override
	public void load() {
		this.currencies.add(new VaultCurrency());

		UltraEconomyLoader.loadCurrencyList();
	}
}
