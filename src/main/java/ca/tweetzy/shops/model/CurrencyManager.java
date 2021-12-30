package ca.tweetzy.shops.model;

import ca.tweetzy.shops.api.ShopCurrency;
import ca.tweetzy.shops.impl.currency.PlayerPointsCurrency;
import ca.tweetzy.shops.impl.currency.VaultCurrency;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.collection.StrictList;
import lombok.Getter;
import lombok.NonNull;

import java.util.function.Consumer;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 2:24 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CurrencyManager extends Manager<StrictList<ShopCurrency>> {

	@Getter
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
	public void load(Consumer<StrictList<ShopCurrency>> data) {
		this.currencies.add(new VaultCurrency());

		if (Common.doesPluginExist("PlayerPoints"))
			this.currencies.add(new PlayerPointsCurrency());

		UltraEconomyLoader.loadCurrencyList();

		if (data != null)
			data.accept(this.currencies);
	}
}
