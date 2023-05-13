package ca.tweetzy.shops.model.currency;

import ca.tweetzy.funds.api.FundsAPI;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.shops.api.currency.AbstractCurrency;
import ca.tweetzy.shops.impl.currency.FundsCurrency;

import java.util.ArrayList;
import java.util.List;

public final class FundsEconomyLoader extends CurrencyLoader {

	public FundsEconomyLoader() {
		super("Funds");
	}

	@Override
	public List<AbstractCurrency> getCurrencies() {
		final List<AbstractCurrency> currencies = new ArrayList<>();

		for (Currency currency : FundsAPI.getInstance().getCurrencies()) {
			currencies.add(new FundsCurrency(currency.getId()));
		}

		return currencies;
	}
}
