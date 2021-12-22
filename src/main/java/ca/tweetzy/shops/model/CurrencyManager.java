package ca.tweetzy.shops.model;

import ca.tweetzy.shops.api.interfaces.ICurrency;
import ca.tweetzy.shops.impl.currency.VaultCurrency;
import ca.tweetzy.tweety.collection.StrictList;
import lombok.NonNull;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 2:24 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CurrencyManager {

	private final StrictList<ICurrency> currencies = new StrictList<>();

	public void addCurrency(@NonNull final ICurrency currency) {
		this.currencies.add(currency);
	}

	public void load() {
		this.currencies.add(new VaultCurrency());

		UltraEconomyLoader.loadCurrencyList();
	}
}
