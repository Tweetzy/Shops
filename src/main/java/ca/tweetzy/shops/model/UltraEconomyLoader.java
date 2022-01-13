package ca.tweetzy.shops.model;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopsAPI;
import ca.tweetzy.shops.impl.currency.UltraEconomyCurrency;
import ca.tweetzy.tweety.Common;
import lombok.experimental.UtilityClass;
import me.TechsCode.UltraEconomy.UltraEconomy;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 21 2021
 * Time Created: 10:11 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@UtilityClass
public final class UltraEconomyLoader {

	public void loadCurrencyList() {
		if (Common.doesPluginExist("UltraEconomy"))
			UltraEconomy.getAPI().getCurrencies().forEach(currency -> {
				Common.log("&eFetched &b" + currency.getNameCommandUsage() + "&e currency from &bUltraEconomy");
				Shops.getCurrencyManager().addCurrency(new UltraEconomyCurrency(currency.getNameCommandUsage()));
			});
	}
}
