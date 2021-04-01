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
    public static final ConfigSetting USE_TAX = new ConfigSetting(config, "tax.enabled", true, "Should tax be applied to the purchase?");
    public static final ConfigSetting TAX_AMOUNT = new ConfigSetting(config, "tax.amount", 13.0D, "Tax percentage as decimal");

    /*
    =========== DATABASE OPTIONS ===========
     */
    public static final ConfigSetting DATABASE_USE = new ConfigSetting(config, "database.use database", true, "Should the plugin use a database to store shop data?");
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
   =========== SHOPS EDIT GUI OPTIONS ===========
    */
    public static final ConfigSetting GUI_SHOP_EDIT_TITLE = new ConfigSetting(config, "guis.shop edit.title", "&eEditing %shop_id% Shop", "The name of the inventory", "Valid Placeholders", "%shop_id%");
    public static final ConfigSetting GUI_SHOP_EDIT_USE_DEFAULT_SLOTS = new ConfigSetting(config, "guis.shop edit.use default slots", true, "Should the icons/buttons be placed in their default slot? Set to false if you want to adjust it yourself");
    public static final ConfigSetting GUI_SHOP_EDIT_FILL_BG = new ConfigSetting(config, "guis.shop edit.fill background", true, "Should the empty slots of the gui be filled?");
    public static final ConfigSetting GUI_SHOP_EDIT_BG_ITEM = new ConfigSetting(config, "guis.shop edit.background item", XMaterial.BLACK_STAINED_GLASS_PANE.name(), "The item that will be used to fill");

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DISPLAY_ICON_NAME = new ConfigSetting(config, "guis.shop edit.items.display icon.name", "&eCurrent Shop Icon");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DISPLAY_ICON_LORE = new ConfigSetting(config, "guis.shop edit.items.display icon.lore", Collections.singletonList("&7Click to change the display icon"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DISPLAY_NAME_ITEM = new ConfigSetting(config, "guis.shop edit.items.display name.item", XMaterial.OAK_SIGN.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DISPLAY_NAME_NAME = new ConfigSetting(config, "guis.shop edit.items.display name.name", "%shop_display_name%");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DISPLAY_NAME_LORE = new ConfigSetting(config, "guis.shop edit.items.display name.lore", Collections.singletonList("&7Click to change the display name"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DESCRIPTION_ITEM = new ConfigSetting(config, "guis.shop edit.items.description.item", XMaterial.WRITABLE_BOOK.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DESCRIPTION_NAME = new ConfigSetting(config, "guis.shop edit.items.description.name", "&eShop Description");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_DESCRIPTION_LORE = new ConfigSetting(config, "guis.shop edit.items.description.lore", Arrays.asList(
            "%shop_description%",
            "",
            "&7Click to change the description"
    ));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_CONTENTS_ITEM = new ConfigSetting(config, "guis.shop edit.items.contents.item", XMaterial.CHEST.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_CONTENTS_NAME = new ConfigSetting(config, "guis.shop edit.items.contents.name", "&eShop Contents");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_CONTENTS_LORE = new ConfigSetting(config, "guis.shop edit.items.contents.lore", Arrays.asList(
            "&7Total Items&f: &e%shop_item_count%",
            "&7Click to change item prices to remove them."
    ));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_ON_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle public.on.item", XMaterial.LIME_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_ON_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle public.on.name", "&aShop Public");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_ON_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle public.on.lore", Collections.singletonList("&7Click to make shop &cprivate"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_OFF_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle public.off.item", XMaterial.RED_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_OFF_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle public.off.name", "&cShop Private");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_OFF_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle public.off.lore", Collections.singletonList("&7Click to make shop &apublic"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_ON_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle sell only.on.item", XMaterial.LIME_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_ON_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle sell only.on.name", "&aShop is Sell Only");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_ON_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle sell only.on.lore", Collections.singletonList("&7Click to disable sell only mode"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_OFF_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle sell only.off.item", XMaterial.RED_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_OFF_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle sell only.off.name", "&cShop isn't Sell Only");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_OFF_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle sell only.off.lore", Collections.singletonList("&7Click to enable sell only mode"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_ON_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle buy only.on.item", XMaterial.LIME_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_ON_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle buy only.on.name", "&aShop is Buy Only");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_ON_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle buy only.on.lore", Collections.singletonList("&7Click to disable buy only mode"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_OFF_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle buy only.off.item", XMaterial.RED_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_OFF_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle buy only.off.name", "&cShop isn't Buy Only");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_OFF_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle buy only.off.lore", Collections.singletonList("&7Click to enable buy only mode"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SELL_DISCOUNT_ITEM = new ConfigSetting(config, "guis.shop edit.items.sell discount.item", XMaterial.SUNFLOWER.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SELL_DISCOUNT_NAME = new ConfigSetting(config, "guis.shop edit.items.sell discount.name", "&eSell Discount");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SELL_DISCOUNT_LORE = new ConfigSetting(config, "guis.shop edit.items.sell discount.lore", Arrays.asList(
            "&7Current Discount&f: &a%shop_sell_discount%%",
            "&7Status&f: &e%shop_sell_discount_enable%",
            "",
            "&7Left-Click to adjust percentage",
            "&7Right-Click to toggle it on/off"
    ), "Valid Placeholders", "%shop_sell_discount%", "%shop_sell_discount_enable%");

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_BUY_DISCOUNT_ITEM = new ConfigSetting(config, "guis.shop edit.items.buy discount.item", XMaterial.SUNFLOWER.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_BUY_DISCOUNT_NAME = new ConfigSetting(config, "guis.shop edit.items.buy discount.name", "&eBuy Discount");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_BUY_DISCOUNT_LORE = new ConfigSetting(config, "guis.shop edit.items.buy discount.lore", Arrays.asList(
            "&7Current Discount&f: &a%shop_buy_discount%%",
            "&7Status&f: &e%shop_buy_discount_enable%",
            "",
            "&7Left-Click to adjust percentage",
            "&7Right-Click to toggle it on/off"
    ), "Valid Placeholders", "%shop_buy_discount%", "%shop_buy_discount_enable%");

//    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TAX_ITEM = new ConfigSetting(config, "guis.shop edit.items.tax.item", XMaterial.SUNFLOWER.name());
//    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TAX_NAME = new ConfigSetting(config, "guis.shop edit.items.tax.name", "&eShop Tax");
//    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TAX_LORE = new ConfigSetting(config, "guis.shop edit.items.tax.lore", Arrays.asList(
//            "&7Current Tax&f: &a%shop_tax%%",
//            "&7Status&f: &e%shop_tax_enable%",
//            "",
//            "&7Left-Click to adjust tax",
//            "&7Right-Click to toggle it on/off"
//    ), "Valid Placeholders", "%shop_tax%", "%shop_tax_enable%");

    // Permission to see toggle
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_ON_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle permission to see.on.item", XMaterial.LIME_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_ON_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle permission to see.on.name", "&aRequire Permission to See Enabled");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_ON_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle permission to see.on.lore", Collections.singletonList("&7Toggle permission to see to &cOff"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_OFF_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle permission to see.off.item", XMaterial.RED_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_OFF_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle permission to see.off.name", "&cRequire Permission to See Disabled");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_OFF_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle permission to see.off.lore", Collections.singletonList("&7Toggle permission to see to &aOn"));

    // Permission to see toggle
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_ON_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle permission to sell.on.item", XMaterial.LIME_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_ON_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle permission to sell.on.name", "&aRequire Permission to Sell Enabled");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_ON_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle permission to sell.on.lore", Collections.singletonList("&7Toggle permission to sell to &cOff"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_OFF_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle permission to sell.off.item", XMaterial.RED_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_OFF_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle permission to sell.off.name", "&cRequire Permission to Sell Disabled");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_OFF_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle permission to sell.off.lore", Collections.singletonList("&7Toggle permission to sell to &aOn"));

    // Permission to buy toggle
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_ON_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle permission to buy.on.item", XMaterial.LIME_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_ON_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle permission to buy.on.name", "&aRequire Permission to Buy Enabled");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_ON_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle permission to buy.on.lore", Collections.singletonList("&7Toggle permission to buy to &cOff"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_OFF_ITEM = new ConfigSetting(config, "guis.shop edit.items.toggle permission to buy.off.item", XMaterial.RED_WOOL.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_OFF_NAME = new ConfigSetting(config, "guis.shop edit.items.toggle permission to buy.off.name", "&cRequire Permission to Buy Disabled");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_OFF_LORE = new ConfigSetting(config, "guis.shop edit.items.toggle permission to buy.off.lore", Collections.singletonList("&7Toggle permission to buy to &aOn"));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SEE_PERMISSION_ITEM = new ConfigSetting(config, "guis.shop edit.items.see permission.item", XMaterial.PAPER.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SEE_PERMISSION_NAME = new ConfigSetting(config, "guis.shop edit.items.see permission.name", "&eSee Permission");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SEE_PERMISSION_LORE = new ConfigSetting(config, "guis.shop edit.items.see permission.lore", Arrays.asList(
            "&7Click to change the see permission",
            "",
            "&7Current Permission&f: &e%shop_see_permission%"
    ));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SELL_PERMISSION_ITEM = new ConfigSetting(config, "guis.shop edit.items.sell permission.item", XMaterial.PAPER.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SELL_PERMISSION_NAME = new ConfigSetting(config, "guis.shop edit.items.sell permission.name", "&eSell Permission");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_SELL_PERMISSION_LORE = new ConfigSetting(config, "guis.shop edit.items.sell permission.lore", Arrays.asList(
            "&7Click to change the sell permission",
            "",
            "&7Current Permission&f: &e%shop_sell_permission%"
    ));

    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_BUY_PERMISSION_ITEM = new ConfigSetting(config, "guis.shop edit.items.buy permission.item", XMaterial.PAPER.name());
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_BUY_PERMISSION_NAME = new ConfigSetting(config, "guis.shop edit.items.buy permission.name", "&eBuy Permission");
    public static final ConfigSetting GUI_SHOP_EDIT_ITEMS_BUY_PERMISSION_LORE = new ConfigSetting(config, "guis.shop edit.items.buy permission.lore", Arrays.asList(
            "&7Click to change the buy permission",
            "",
            "&7Current Permission&f: &e%shop_buy_permission%"
    ));

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

    /*
    =========== SHOP CONTENTS OPTIONS ===========
     */
    public static final ConfigSetting GUI_SHOP_CONTENTS_TITLE = new ConfigSetting(config, "guis.shop contents.title", "&e%shop_display_name%", "The name of the inventory", "Valid Placeholders", "%shop_id%", "%shop_display_name%");
    public static final ConfigSetting GUI_SHOP_CONTENTS_FILL_BG = new ConfigSetting(config, "guis.shop contents.fill background", true, "Should the empty slots of the gui be filled?");
    public static final ConfigSetting GUI_SHOP_CONTENTS_BG_ITEM = new ConfigSetting(config, "guis.shop contents.background item", XMaterial.BLACK_STAINED_GLASS_PANE.name(), "The item that will be used to fill");
    public static final ConfigSetting GUI_SHOP_CONTENTS_ITEM_NAME = new ConfigSetting(config, "guis.shop contents.item name", "%shop_item_name%", "Valid Placeholders", "%shop_item_name%");
    public static final ConfigSetting GUI_SHOP_CONTENTS_ITEM_LORE = new ConfigSetting(config, "guis.shop contents.item lore", Arrays.asList(
            "&7Left-click to purchase this item.",
            "&7Right-click to add x1 to your cart.",
            "",
            "&7Sell Price&f: &a$%shop_item_sell_price%",
            "&7Buy Price&f: &a$%shop_item_buy_price%",
            "&7Base Quantity&f: &a%shop_item_quantity%"
    ), "Valid Placeholders", "%shop_item_sell_price%", "%shop_item_buy_price%", "%shop_item_quantity%");

    public static final ConfigSetting GUI_SHOP_CONTENTS_CART_ITEM = new ConfigSetting(config, "guis.shop contents.cart item", XMaterial.CHEST.name());
    public static final ConfigSetting GUI_SHOP_CONTENTS_CART_NAME = new ConfigSetting(config, "guis.shop contents.cart name", "&eView Cart");
    public static final ConfigSetting GUI_SHOP_CONTENTS_CART_LORE = new ConfigSetting(config, "guis.shop contents.cart lore", Arrays.asList(
            "&7Click to view your cart",
            "",
            "&7Cart Items&f: &a%shop_cart_item_count%",
            "&7Cart Total&f: &a$%shop_cart_total%"
    ), "Valid Placeholders", "%shop_cart_item_count%", "%shop_cart_total%");

    /*
   =========== SHOP CONTENTS OPTIONS ===========
     */
    public static final ConfigSetting GUI_SHOP_CART_TITLE = new ConfigSetting(config, "guis.cart.title", "&eYour Cart", "The name of the inventory");
    public static final ConfigSetting GUI_SHOP_CART_FILL_BG = new ConfigSetting(config, "guis.cart.fill background", false, "Should the empty slots of the gui be filled?");
    public static final ConfigSetting GUI_SHOP_CART_BG_ITEM = new ConfigSetting(config, "guis.cart.background item", XMaterial.BLACK_STAINED_GLASS_PANE.name(), "The item that will be used to fill");
    public static final ConfigSetting GUI_SHOP_CART_BAR_ITEM = new ConfigSetting(config, "guis.cart.bar item", XMaterial.BLACK_STAINED_GLASS_PANE.name(), "The item that will be used to fill");

    public static final ConfigSetting GUI_SHOP_CART_ITEMS_CLEAR_ITEM = new ConfigSetting(config, "guis.cart.items.clear.item", XMaterial.RED_STAINED_GLASS_PANE.name());
    public static final ConfigSetting GUI_SHOP_CART_ITEMS_CLEAR_NAME = new ConfigSetting(config, "guis.cart.items.clear.name", "&cClear Cart");
    public static final ConfigSetting GUI_SHOP_CART_ITEMS_CLEAR_LORE = new ConfigSetting(config, "guis.cart.items.clear.lore", Collections.singletonList("&7Click to clear the cart"));

    public static final ConfigSetting GUI_SHOP_CART_ITEMS_CONFIRM_ITEM = new ConfigSetting(config, "guis.cart.items.confirm.item", XMaterial.LIME_STAINED_GLASS_PANE.name());
    public static final ConfigSetting GUI_SHOP_CART_ITEMS_CONFIRM_NAME = new ConfigSetting(config, "guis.cart.items.confirm.name", "&aConfirm Purchase");
    public static final ConfigSetting GUI_SHOP_CART_ITEMS_CONFIRM_LORE = new ConfigSetting(config, "guis.cart.items.confirm.lore", Collections.singletonList("&7Click to make purchase"));

    public static final ConfigSetting GUI_SHOP_CART_ITEMS_INFO_ITEM = new ConfigSetting(config, "guis.cart.items.info.item", XMaterial.PAPER.name());
    public static final ConfigSetting GUI_SHOP_CART_ITEMS_INFO_NAME = new ConfigSetting(config, "guis.cart.items.info.name", "&eCart Information");
    public static final ConfigSetting GUI_SHOP_CART_ITEMS_INFO_LORE = new ConfigSetting(config, "guis.cart.items.info.lore", Arrays.asList(
            "&7Total Items&f: &e%shop_cart_item_count%",
            "",
            "&7Sub-Total&f: &a$%shop_cart_sub_total%",
            "&7Tax&f: &a%shop_cart_tax%%",
            "&7Discounts&f: &a- $%shop_cart_discounts%",
            "",
            "&7Total&f: &a$%shop_cart_total%"
    ));

    public static void setup() {
        config.load();
        config.setAutoremove(true).setAutosave(true);
        config.saveChanges();
    }
}
