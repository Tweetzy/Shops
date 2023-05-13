package ca.tweetzy.shops.settings;

import ca.tweetzy.flight.config.ConfigEntry;
import ca.tweetzy.flight.settings.FlightSettings;
import ca.tweetzy.shops.Shops;

public final class Settings extends FlightSettings {

	public static ConfigEntry PREFIX = create("prefix", "&8[&eShops&8]").withComment("The prefix for the plugin");
	public static ConfigEntry LANGUAGE = create("language", "en_us").withComment("The primary language of the plugin");
	public static ConfigEntry CURRENCY_ALLOW_PICK = create("settings.currency.allow user to pick", true).withComment("If true, players will be able to select which currency they want to use.");
	public static ConfigEntry CURRENCY_DEFAULT_SELECTED = create("settings.currency.default selection", "Vault/Vault").withComment("The default currency selection, PluginName/CurrencyName -> Ex. Vault/Vault");
	public static ConfigEntry CURRENCY_ITEM_DEFAULT_SELECTED = create("settings.currency.default item selection", "DIAMOND").withComment("The default currency selection if using item only mode");
	public static ConfigEntry CURRENCY_VAULT_SYMBOL = create("settings.currency.vault symbol", "$").withComment("When using default/vault currency, what symbol should be used.");
	public static ConfigEntry CURRENCY_USE_ITEM_ONLY = create("settings.currency.use item only", false).withComment("If true, Shops will only allow the usage of another item for currency.");
	public static ConfigEntry TAX_ENABLED = create("settings.tax.enabled", false).withComment("If true, will apply sales tax to the total when a user is buying an item");
	public static ConfigEntry TAX_AMOUNT = create("settings.tax.percentage", 13.0).withComment("The tax percentage. By default it's 13%");


	public static void init() {
		Shops.getCoreConfig().init();
	}
}