package ca.tweetzy.shops.settings;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.config.ConfigEntry;
import ca.tweetzy.flight.settings.FlightSettings;
import ca.tweetzy.shops.Shops;

import java.util.List;

public final class Settings extends FlightSettings {

	public static ConfigEntry PREFIX = create("prefix", "&8[&eShops&8]").withComment("The prefix for the plugin");
	public static ConfigEntry LANGUAGE = create("language", "en_us").withComment("The primary language of the plugin");
	public static ConfigEntry CURRENCY_DEFAULT_SELECTED = create("settings.currency.default selection", "Vault/Vault").withComment("The default currency selection, PluginName/CurrencyName -> Ex. Vault/Vault");
	public static ConfigEntry CURRENCY_VAULT_SYMBOL = create("settings.currency.vault symbol", "$").withComment("When using default/vault currency, what symbol should be used. Mainly for admin guis");
	public static ConfigEntry CURRENCY_USE_ITEM_ONLY = create("settings.currency.use item only", false).withComment("If true, Shops will only allow the usage of another item for currency.");
	public static ConfigEntry CURRENCY_BLACKLISTED = create("settings.currency.black listed", List.of("UltraEconomy:Gems")).withComment("A list of owning plugins & the currency to be blacklisted. Ex. UltraEconomy:Gems");
	public static ConfigEntry CURRENCY_FORMAT_LANGUAGE = create("settings.currency.format.language", "en").withComment("An ISO 639 alpha-2 or alpha-3 language code.");
	public static ConfigEntry CURRENCY_FORMAT_COUNTRY = create("settings.currency.format.country", "US").withComment("An ISO 3166 alpha-2 country code or a UN M.49 numeric-3 area code.");
	public static ConfigEntry DATETIME_FORMAT = create("settings.date time format", "MMM dd, yyyy hh:mm:ss a").withComment("How should timestamps be formatted");
	public static ConfigEntry TAX_ENABLED = create("settings.tax.enabled", false).withComment("If true, will apply sales tax to the total when a user is buying an item");
	public static ConfigEntry TAX_AMOUNT = create("settings.tax.percentage", 13.0).withComment("The tax percentage. By default it's 13%");
	public static ConfigEntry TAX_SELL = create("settings.tax.tax on sell", false).withComment("If true, selling items to a shop will remove tax percentage");
	public static ConfigEntry GLOBAL_GUI_CLICK_DELAY = create("settings.click delays.global", 1000).withComment("In milliseconds, how long should the delay between clicks be (set to -1 to disable)");
	public static ConfigEntry SEND_CLICK_DELAY_MSG = create("settings.send click delay message", false).withComment("Should shops tell the player to slow down? Or just send nothing.");

	/*
	========================= GUI STUFF =========================
	 */
	public static ConfigEntry GUI_SHARED_ITEMS_BACK_BUTTON = create("gui.shared buttons.back button.item", CompMaterial.DARK_OAK_DOOR.name());
	public static ConfigEntry GUI_SHARED_ITEMS_EXIT_BUTTON = create("gui.shared buttons.exit button.item", CompMaterial.BARRIER.name());
	public static ConfigEntry GUI_SHARED_ITEMS_PREVIOUS_BUTTON = create("gui.shared buttons.previous button.item", CompMaterial.ARROW.name());
	public static ConfigEntry GUI_SHARED_ITEMS_NEXT_BUTTON = create("gui.shared buttons.next button.item", CompMaterial.ARROW.name());

	//========================= MAIN GUI CUSTOMIZATION =========================
	public static ConfigEntry GUI_SHOPS_MAIN_ROWS = create("gui.shops main.rows", 6, "Number of rows for the gui, min 2 max 6");
	public static ConfigEntry GUI_SHOPS_MAIN_BACKGROUND = create("gui.shops main.background", "WHITE_STAINED_GLASS_PANE", "You can use MATERIAL:Model Number if you want here, or textures.minecraft links");
	public static ConfigEntry GUI_SHOPS_MAIN_USE_AUTOFILL = create("gui.shops main.use auto fill", true, "If true, shops will auto fill into specified slots");
	public static ConfigEntry GUI_SHOPS_MAIN_FILL_SLOTS = create("gui.shops main.fill slots", List.of(20, 21, 22, 23, 24, 30, 31, 32), "The slots shops will be placed into if auto fill is on.");
	public static ConfigEntry GUI_SHOPS_MAIN_FIXED_SHOPS = create("gui.shops main.fixed shops", List.of("slot:10 shop:food"), "Used manually specify shop slots, auto fill must be off. format -> slot:<slotNumber> shop:<shopId>");
	public static ConfigEntry GUI_SHOPS_MAIN_DECORATIONS = create("gui.shops main.decorations", List.of(
			"slot:0-8 item:BLACK_STAINED_GLASS_PANE",
			"slot:45-53 item:BLACK_STAINED_GLASS_PANE",
			"slot:9 item:BLACK_STAINED_GLASS_PANE",
			"slot:18 item:BLACK_STAINED_GLASS_PANE",
			"slot:27 item:BLACK_STAINED_GLASS_PANE",
			"slot:36 item:BLACK_STAINED_GLASS_PANE",
			"slot:17 item:BLACK_STAINED_GLASS_PANE",
			"slot:26 item:BLACK_STAINED_GLASS_PANE",
			"slot:35 item:BLACK_STAINED_GLASS_PANE",
			"slot:44 item:BLACK_STAINED_GLASS_PANE"
	), "TBD");

	public static ConfigEntry GUI_SHOPS_QUICK_SELL_DECORATIONS = create("gui.shops quick sell.decorations", List.of(
			"slot:0-8 item:BLACK_STAINED_GLASS_PANE",
			"slot:45-53 item:BLACK_STAINED_GLASS_PANE",
			"slot:9 item:BLACK_STAINED_GLASS_PANE",
			"slot:18 item:BLACK_STAINED_GLASS_PANE",
			"slot:27 item:BLACK_STAINED_GLASS_PANE",
			"slot:36 item:BLACK_STAINED_GLASS_PANE",
			"slot:17 item:BLACK_STAINED_GLASS_PANE",
			"slot:26 item:BLACK_STAINED_GLASS_PANE",
			"slot:35 item:BLACK_STAINED_GLASS_PANE",
			"slot:44 item:BLACK_STAINED_GLASS_PANE"
	), "TBD");


	public static ConfigEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_EXIT = create("gui.layout control picker.items.exit.item", CompMaterial.BARRIER.name());
	public static ConfigEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_PREV_PAGE = create("gui.layout control picker.items.prev page.item", CompMaterial.ARROW.name());
	public static ConfigEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_NEXT_PAGE = create("gui.layout control picker.items.next page.item", CompMaterial.ARROW.name());
	public static ConfigEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_SEARCH = create("gui.layout control picker.items.search.item", CompMaterial.DARK_OAK_SIGN.name());
	public static ConfigEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_FILTER = create("gui.layout control picker.items.filter.item", CompMaterial.REPEATER.name());
	public static ConfigEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_CART = create("gui.layout control picker.items.cart.item", CompMaterial.MINECART.name());
	public static ConfigEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_SELL = create("gui.layout control picker.items.sell.item", CompMaterial.ENDER_CHEST.name());


	public static ConfigEntry GUI_SHOP_CONTENT_ITEMS_SEARCH = create("gui.shop contents.items.review.item", CompMaterial.DARK_OAK_SIGN.name());
	public static ConfigEntry GUI_SHOP_CONTENT_ITEMS_FILTER = create("gui.shop contents.items.filter.item", CompMaterial.REPEATER.name());
	public static ConfigEntry GUI_SHOP_CONTENT_ITEMS_CART = create("gui.shop contents.items.cart.item", CompMaterial.MINECART.name());
	public static ConfigEntry GUI_SHOP_CONTENT_ITEMS_SELL = create("gui.shop contents.items.sell.item", CompMaterial.ENDER_CHEST.name());


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