package ca.tweetzy.shops.model.currency;

import ca.tweetzy.shops.api.currency.AbstractCurrency;
import lombok.NonNull;

import java.util.List;

public abstract class CurrencyLoader {

	protected String owningPlugin;

	public CurrencyLoader(@NonNull final String owningPlugin) {
		this.owningPlugin = owningPlugin;
	}

	public abstract List<AbstractCurrency> getCurrencies();
}
