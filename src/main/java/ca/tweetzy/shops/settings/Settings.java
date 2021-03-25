package ca.tweetzy.shops.settings;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.core.configuration.ConfigSetting;
import ca.tweetzy.shops.Shops;

import java.util.Arrays;
import java.util.Collections;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 9:17 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class Settings {

    static final Config config = Shops.getInstance().getCoreConfig();

    public static final ConfigSetting LANG = new ConfigSetting(config, "lang", "en_US", "Default language file");

    /*
    =========== DATABASE OPTIONS ===========
     */
    public static final ConfigSetting DATABASE_USE = new ConfigSetting(config, "database.use database", false, "Should the plugin use a database to store shop data?");
    public static final ConfigSetting DATABASE_HOST = new ConfigSetting(config, "database.host", "localhost", "What is the connection url/host");
    public static final ConfigSetting DATABASE_PORT = new ConfigSetting(config, "database.port", 3306, "What is the port to database (default is 3306)");
    public static final ConfigSetting DATABASE_NAME = new ConfigSetting(config, "database.name", "plugin_dev", "What is the name of the database?");
    public static final ConfigSetting DATABASE_USERNAME = new ConfigSetting(config, "database.username", "root", "What is the name of the user connecting?");
    public static final ConfigSetting DATABASE_PASSWORD = new ConfigSetting(config, "database.password", "Password1.", "What is the password to the user connecting?");
    public static final ConfigSetting DATABASE_USE_SSL = new ConfigSetting(config, "database.use ssl", true, "Should the database connection use ssl?");

    /*
   =========== GLOBAL BUTTONS FOR GUIS ===========
    */
    public static final ConfigSetting GUI_BACK_BTN_ITEM = new ConfigSetting(config, "guis.global items.back button.item", "ARROW", "Settings for the back button");
    public static final ConfigSetting GUI_BACK_BTN_NAME = new ConfigSetting(config, "guis.global items.back button.name", "&e<< Back");
    public static final ConfigSetting GUI_BACK_BTN_LORE = new ConfigSetting(config, "guis.global items.back button.lore", Arrays.asList("&7Click the button to go", "&7back to the previous page."));

    public static final ConfigSetting GUI_CLOSE_BTN_ITEM = new ConfigSetting(config, "guis.global items.close button.item", "BARRIER", "Settings for the close button");
    public static final ConfigSetting GUI_CLOSE_BTN_NAME = new ConfigSetting(config, "guis.global items.close button.name", "&cClose");
    public static final ConfigSetting GUI_CLOSE_BTN_LORE = new ConfigSetting(config, "guis.global items.close button.lore", Collections.singletonList("&7Click to close this menu."));

    public static final ConfigSetting GUI_NEXT_BTN_ITEM = new ConfigSetting(config, "guis.global items.next button.item", "ARROW", "Settings for the next button");
    public static final ConfigSetting GUI_NEXT_BTN_NAME = new ConfigSetting(config, "guis.global items.next button.name", "&eNext >>");
    public static final ConfigSetting GUI_NEXT_BTN_LORE = new ConfigSetting(config, "guis.global items.next button.lore", Arrays.asList("&7Click the button to go", "&7to the next page."));

    public static final ConfigSetting GUI_REFRESH_BTN_ITEM = new ConfigSetting(config, "guis.global items.refresh button.item", "NETHER_STAR", "Settings for the refresh page");
    public static final ConfigSetting GUI_REFRESH_BTN_NAME = new ConfigSetting(config, "guis.global items.refresh button.name", "&6&LRefresh Page");
    public static final ConfigSetting GUI_REFRESH_BTN_LORE = new ConfigSetting(config, "guis.global items.refresh button.lore", Arrays.asList("&7Click to refresh the currently", "&7available auction listings."));

    /*
   =========== SHOPS MAIN GUI OPTIONS ===========
    */
    public static final ConfigSetting GUI_SHOPS_TITLE = new ConfigSetting(config, "guis.shops.title", "&eShops", "The name of the inventory");
    public static final ConfigSetting GUI_SHOPS_DYNAMIC = new ConfigSetting(config, "guis.shops.dynamic", true, "Should the inventory size according to the total amount of shops?");
    public static final ConfigSetting GUI_SHOPS_SIZE = new ConfigSetting(config, "guis.shops.size", 6, "If dynamic is false, it will be use this size (min 2, max 6)");
    public static final ConfigSetting GUI_SHOPS_AUTO_ARRANGE = new ConfigSetting(config, "guis.shops.auto arrange", true, "Should the items auto arrange themselves inside the gui, or should items use their specified slots?");
    public static final ConfigSetting GUI_SHOPS_FILL_BG = new ConfigSetting(config, "guis.shops.fill background", true, "Should the empty slots of the gui be filled?");
    public static final ConfigSetting GUI_SHOPS_BG_ITEM = new ConfigSetting(config, "guis.shops.background item", XMaterial.BLACK_STAINED_GLASS_PANE.name(), "The item that will be used to fill");
    public static final ConfigSetting GUI_SHOPS_ITEM_NAME = new ConfigSetting(config, "guis.shops.shop name", "%shop_display_name%", "Valid Placeholders", "%shop_display_name%", "%shop_id%");
    public static final ConfigSetting GUI_SHOPS_ITEM_LORE = new ConfigSetting(config, "guis.shops.shop lore", Arrays.asList(
            "%shop_description%",
            "&7Sell Only: &e%shop_is_sell_only%",
            "&7Buy Only: &e%shop_is_buy_only%",
            "&7Total Items: &e%shop_item_count%",
            "",
            "&7Click to buy items from this shop."
    ), "Valid Placeholders", "%shop_description%", "%shop_item_count%", "%shop_is_sell_only%", "%shop_is_buy_only%");

    /*
    =========== SHOP LIST GUI OPTIONS ===========
     */
    public static final ConfigSetting GUI_SHOP_LIST_TITLE = new ConfigSetting(config, "guis.shop list.title", "&eListing Shops", "The name of the inventory");
    public static final ConfigSetting GUI_SHOP_LIST_FILL_BG = new ConfigSetting(config, "guis.shop list.fill background", true, "Should the empty slots of the gui be filled?");
    public static final ConfigSetting GUI_SHOP_LIST_BG_ITEM = new ConfigSetting(config, "guis.shop list.background item", XMaterial.BLACK_STAINED_GLASS_PANE.name(), "The item that will be used to fill");
    public static final ConfigSetting GUI_SHOP_LIST_ITEM_NAME = new ConfigSetting(config, "guis.shop list.shop name", "%shop_display_name%", "Valid Placeholders", "%shop_display_name%", "%shop_id%");
    public static final ConfigSetting GUI_SHOP_LIST_ITEM_LORE = new ConfigSetting(config, "guis.shop list.shop lore", Arrays.asList(
            "&7ID: &e%shop_id%",
            "&7Display Name: &e%shop_display_name%",
            "&7Public: &e%shop_is_public%",
            "&7Sell Only: &e%shop_is_sell_only%",
            "&7Buy Only: &e%shop_is_buy_only%",
            "&7Total Items: &e%shop_item_count%",
            "",
            "&7Click to edit this shop"
    ), "Valid Placeholders", "%shop_display_name%", "%shop_id%", "%shop_is_public%", "%shop_item_count%", "%shop_is_sell_only%", "%shop_is_buy_only%");

    public static void setup() {
        config.load();
        config.setAutoremove(true).setAutosave(true);
        config.saveChanges();
    }
}
