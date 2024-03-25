package ca.tweetzy.shops.model;

import ca.tweetzy.shops.settings.Settings;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class Taxer {

	public double calculateTaxAmount(final double subtotal) {
		if (!Settings.TAX_ENABLED.getBoolean())
			return 0;

		return subtotal * Settings.TAX_AMOUNT.getDouble() / 100D;
	}

	public double getTaxedTotal(final double subtotal) {
		return subtotal + calculateTaxAmount(subtotal);
	}
}
