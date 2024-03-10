package ca.tweetzy.shops.settings;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.config.ConfigEntry;
import ca.tweetzy.flight.settings.FlightSettings;
import ca.tweetzy.shops.Shops;

import java.util.List;

public final class Settings extends FlightSettings {

	public static ConfigEntry PREFIX = create("prefix", "&8[&eShops&8]").withComment("The prefix for the plugin");
	public static ConfigEntry LANGUAGE = create("language", "en_us").withComment("The primary language of the plugin");
	public static ConfigEntry CURRENCY_ALLOW_PICK = create("settings.currency.allow user to pick", true).withComment("If true, players will be able to select which currency they want to use.");
	public static ConfigEntry CURRENCY_DEFAULT_SELECTED = create("settings.currency.default selection", "Vault/Vault").withComment("The default currency selection, PluginName/CurrencyName -> Ex. Vault/Vault");
	public static ConfigEntry CURRENCY_ITEM_DEFAULT_SELECTED = create("settings.currency.default item selection", "DIAMOND").withComment("The default currency selection if using item only mode");
	public static ConfigEntry CURRENCY_VAULT_SYMBOL = create("settings.currency.vault symbol", "$").withComment("When using default/vault currency, what symbol should be used.");
	public static ConfigEntry CURRENCY_USE_ITEM_ONLY = create("settings.currency.use item only", false).withComment("If true, Shops will only allow the usage of another item for currency.");
	public static ConfigEntry CURRENCY_BLACKLISTED = create("settings.currency.black listed", List.of("UltraEconomy:Gems")).withComment("A list of owning plugins & the currency to be blacklisted. Ex. UltraEconomy:Gems");
	public static ConfigEntry TAX_ENABLED = create("settings.tax.enabled", false).withComment("If true, will apply sales tax to the total when a user is buying an item");
	public static ConfigEntry TAX_AMOUNT = create("settings.tax.percentage", 13.0).withComment("The tax percentage. By default it's 13%");

	/*
	========================= GUI STUFF =========================
	 */
	public static ConfigEntry GUI_SHARED_ITEMS_BACK_BUTTON = create("gui.shared buttons.back button.item", CompMaterial.DARK_OAK_DOOR.name());
	public static ConfigEntry GUI_SHARED_ITEMS_EXIT_BUTTON = create("gui.shared buttons.exit button.item", CompMaterial.BARRIER.name());
	public static ConfigEntry GUI_SHARED_ITEMS_PREVIOUS_BUTTON = create("gui.shared buttons.previous button.item", CompMaterial.ARROW.name());
	public static ConfigEntry GUI_SHARED_ITEMS_NEXT_BUTTON = create("gui.shared buttons.next button.item", CompMaterial.ARROW.name());

	public static ConfigEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_EXIT = create("gui.layout control picker.items.exit.item", CompMaterial.BARRIER.name());
	public static ConfigEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_PREV_PAGE = create("gui.layout control picker.items.prev page.item", CompMaterial.ARROW.name());
	public static ConfigEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_NEXT_PAGE = create("gui.layout control picker.items.next page.item", CompMaterial.ARROW.name());
	public static ConfigEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_SEARCH = create("gui.layout control picker.items.search.item", CompMaterial.DARK_OAK_SIGN.name());
	public static ConfigEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_FILTER = create("gui.layout control picker.items.filter.item", CompMaterial.REPEATER.name());
	public static ConfigEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_CART = create("gui.layout control picker.items.cart.item", CompMaterial.MINECART.name());

	public static ConfigEntry GUI_SHOP_CONTENT_ITEMS_SEARCH = create("gui.shop contents.items.review.item", CompMaterial.DARK_OAK_SIGN.name());
	public static ConfigEntry GUI_SHOP_CONTENT_ITEMS_FILTER = create("gui.shop contents.items.filter.item", CompMaterial.REPEATER.name());
	public static ConfigEntry GUI_SHOP_CONTENT_ITEMS_CART = create("gui.shop contents.items.cart.item", CompMaterial.MINECART.name());


	public static ConfigEntry GUI_CHECKOUT_ITEMS_CHECKOUT = create("gui.checkout.items.checkout.item", CompMaterial.LIME_DYE.name());
	public static ConfigEntry GUI_CHECKOUT_ITEMS_BREAKDOWN = create("gui.checkout.items.breakdown.item", CompMaterial.PAPER.name());
	public static ConfigEntry GUI_CHECKOUT_ITEMS_DECREASE_ITEMS = create("gui.checkout.items.decrease.items", List.of(
			"item:RED_STAINED_GLASS_PANE quantity:1 slot:20 change:1",
			"item:RED_STAINED_GLASS_PANE quantity:5 slot:28 change:5",
			"item:RED_STAINED_GLASS_PANE quantity:10 slot:38 change:10"
	), "Format: item, slot, change amount(how much the qty will increase/decrease by)");

	public static ConfigEntry GUI_CHECKOUT_ITEMS_INCREASE_ITEMS = create("gui.checkout.items.increase.items", List.of(
			"item:LIME_STAINED_GLASS_PANE quantity:1 slot:24 change:1",
			"item:LIME_STAINED_GLASS_PANE quantity:5 slot:34 change:5",
			"item:LIME_STAINED_GLASS_PANE quantity:10 slot:42 change:10"
	), "Format: item, slot, change amount(how much the qty will increase/decrease by)");

	public static ConfigEntry GUI_CART_ITEMS_CHECKOUT = create("gui.cart.items.checkout.item", CompMaterial.LIME_DYE.name());
	public static ConfigEntry GUI_CART_ITEMS_CLEAR = create("gui.cart.items.clear.item", CompMaterial.LAVA_BUCKET.name());


	public static void init() {
		Shops.getCoreConfig().init();
	}
}