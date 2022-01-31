package ca.tweetzy.shops.model.manager;

import ca.tweetzy.shops.api.ShopCurrency;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.model.Replacer;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 24 2022
 * Time Created: 11:32 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@UtilityClass
public final class ShopsEconomy {

	public boolean hasBalance(@NonNull final Player player, @NonNull final ShopCurrency currency, final double amount) {
		return currency.isEnabled() && currency.has(player, amount);
	}

	public boolean withdraw(@NonNull final Player player, @NonNull final ShopCurrency currency, final double amount) {
		if (currency.isEnabled() && currency.withdraw(player, amount)) {
			Common.tell(player, Replacer.replaceArray(currency.getPluginName().equalsIgnoreCase("Vault") ? Localization.Success.MoneyRemove.DOLLAR_TYPE : Localization.Success.MoneyRemove.OTHER_TYPE,
					"currency", currency.getName(),
					"currency_symbol", Localization.CURRENCY_SYMBOL,
					"amount", String.format(Settings.NUMBER_FORMAT, amount)
			));
			return true;
		}
		return false;
	}

	public boolean deposit(@NonNull final Player player, @NonNull final ShopCurrency currency, final double amount) {
		if (currency.isEnabled() && currency.deposit(player, amount)) {
			Common.tell(player, Replacer.replaceArray(currency.getPluginName().equalsIgnoreCase("Vault") ? Localization.Success.MoneyAdd.DOLLAR_TYPE : Localization.Success.MoneyAdd.OTHER_TYPE,
					"currency", currency.getName(),
					"currency_symbol", Localization.CURRENCY_SYMBOL,
					"amount", String.format(Settings.NUMBER_FORMAT, amount)
			));
		}
		return false;
	}
}
