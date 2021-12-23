package ca.tweetzy.shops.model;

import ca.tweetzy.shops.api.AbstractShopCurrency;
import ca.tweetzy.shops.impl.currency.VaultCurrency;
import ca.tweetzy.tweety.collection.StrictList;
import lombok.Getter;
import lombok.NonNull;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 2:24 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CurrencyManager extends Manager {

	@Getter
	private final StrictList<AbstractShopCurrency> currencies = new StrictList<>();

	public void addCurrency(@NonNull final AbstractShopCurrency currency) {
		this.currencies.add(currency);
	}

	public AbstractShopCurrency getCurrency(@NonNull final String plugin) {
		return this.currencies.getSource().stream().filter(currency -> currency.getPluginName().equalsIgnoreCase(plugin)).findFirst().orElse(null);
	}

	public AbstractShopCurrency getCurrency(@NonNull final String plugin, @NonNull final String currencyName) {
		return this.currencies.getSource().stream().filter(currency -> currency.getPluginName().equalsIgnoreCase(plugin) && currency.getName().equals(currencyName)).findFirst().orElse(null);
	}

	@Override
	public void load() {
		this.currencies.add(new VaultCurrency());

		UltraEconomyLoader.loadCurrencyList();
	}
}
