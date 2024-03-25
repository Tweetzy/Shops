package ca.tweetzy.shops.model;

import ca.tweetzy.shops.settings.Settings;
import lombok.experimental.UtilityClass;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@UtilityClass
public final class NumberHelper {

	public String format(final double total) {
		return NumberFormat.getCurrencyInstance(new Locale.Builder().setLanguage(Settings.CURRENCY_FORMAT_LANGUAGE.getString()).setRegion(Settings.CURRENCY_FORMAT_COUNTRY.getString()).build()).format(total);
	}

	public String getFormattedDate(final long time) {
		Date date = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat(Settings.DATETIME_FORMAT.getString());

		return formatter.format(date);
	}
}
