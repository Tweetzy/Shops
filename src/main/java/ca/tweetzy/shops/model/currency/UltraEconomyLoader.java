package ca.tweetzy.shops.model.currency;

import ca.tweetzy.shops.api.currency.AbstractCurrency;
import ca.tweetzy.shops.impl.currency.UltraEconomyCurrency;
import me.TechsCode.UltraEconomy.UltraEconomy;
import me.TechsCode.UltraEconomy.objects.Currency;

import java.util.ArrayList;
import java.util.List;

public final class UltraEconomyLoader extends CurrencyLoader {

	public UltraEconomyLoader() {
		super("UltraEconomy");
	}


	@Override
	public List<AbstractCurrency> getCurrencies() {
		final List<AbstractCurrency> currencies = new ArrayList<>();

		for (Currency currency : UltraEconomy.getAPI().getCurrencies()) {
			currencies.add(new UltraEconomyCurrency(currency.getName()));
		}

		return currencies;
	}
}
