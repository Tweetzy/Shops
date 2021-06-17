package ca.tweetzy.shops.settings;

import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.shops.Shops;

import java.util.HashMap;

/**
 * The current file has been created by Kiran Hart
 * Date Created: June 17 2021
 * Time Created: 12:16 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class LocaleSettings {

    static final HashMap<String, String> languageNodes = new HashMap<>();

    static {
        languageNodes.put("general.prefix", "&8[&eShops&8]");
        languageNodes.put("general.reloaded", "&aShops has been reloaded (%value%ms)");
        languageNodes.put("general.cart_disabled", "&cThe cart has been disabled!");
        languageNodes.put("general.percentage_change.sell_percentage", "&ePlease enter sell bonus percentage (ex. 24.50):");
        languageNodes.put("general.percentage_change.buy_percentage", "&ePlease enter buy discount percentage (ex. 24.50):");
        languageNodes.put("general.percentage_change.tax", "&ePlease enter the tax percentage (ex. 24.50):");
        languageNodes.put("general.permission_change.see", "&ePlease enter the new see permission:");
        languageNodes.put("general.permission_change.buy", "&ePlease enter the new buy permission:");
        languageNodes.put("general.permission_change.sell", "&ePlease enter the new sell permission:");
        languageNodes.put("general.enter_new_display_name", "&ePlease enter the new display name:");
        languageNodes.put("general.change_description", "&ePlease enter the new description:");
        languageNodes.put("general.change_icon", "&eClick any item in your inventory to set the icon.");
        languageNodes.put("general.change_sell_price", "&ePlease enter the new sell price:");
        languageNodes.put("general.change_buy_price", "&ePlease enter the new buy price:");
        languageNodes.put("general.not_boolean_value", "&cNot a valid true/false response!");
        languageNodes.put("general.not_double_value", "&cNot a valid number!");
        languageNodes.put("general.nothing_in_hand", "&cYou're not holding an item!");
        languageNodes.put("general.not_enough_money", "&cYou do not have enough money!");
        languageNodes.put("general.buy_only_excluded", "&cSome buy only items were excluded from your sell");
        languageNodes.put("general.money_remove", "&c- $%value%");
        languageNodes.put("general.money_add", "&a+ $%value%");
        languageNodes.put("general.permission_required.sell", "&cYou need permission to sell in this shop!");
        languageNodes.put("general.permission_required.buy", "&cYou need permission to buy in this shop!");

        languageNodes.put("set_command.invalid_set_option", "&cThat is not a valid shop setting!");
        languageNodes.put("set_command.changed_shop_visibility", "&aSet the shop visibility to &2%value%");
        languageNodes.put("set_command.changed_shop_sell_only", "&aSet the shop sell only mode to &2%value%");
        languageNodes.put("set_command.changed_shop_buy_only", "&aSet the shop buy only mode to &2%value%");
        languageNodes.put("set_command.changed_shop_use_sell_bonus", "&aSet the shop to use a sell bonus to &2%value%");
        languageNodes.put("set_command.changed_shop_use_buy_discount", "&aSet the shop to use a buy discount to &2%value%");
        languageNodes.put("set_command.changed_shop_use_see_permission", "&aSet the shop to require see perm to &2%value%");
        languageNodes.put("set_command.changed_shop_use_buy_permission", "&aSet the shop to require buy perm to &2%value%");
        languageNodes.put("set_command.changed_shop_use_sell_permission", "&aSet the shop to require sell perm to &2%value%");
        languageNodes.put("set_command.changed_shop_use_tax", "&aSet the shop tax to &2%value%%");
        languageNodes.put("set_command.changed_shop_sell_bonus", "&aSet the shop sell bonus to &2%value%%");
        languageNodes.put("set_command.changed_shop_buy_discount", "&aSet the shop buy discount to &2%value%%");
        languageNodes.put("set_command.changed_shop_tax", "&aSet the shop tax to &2%value%%");
        languageNodes.put("set_command.changed_shop_see_permission", "&aSet the shop see permission to &2%value%");
        languageNodes.put("set_command.changed_shop_sell_permission", "&aSet the shop sell permission to &2%value%");
        languageNodes.put("set_command.changed_shop_buy_permission", "&aSet the shop buy permission to &2%value%");
        languageNodes.put("set_command.changed_shop_display_name", "&aSet the shop display name to %value%");
        languageNodes.put("set_command.changed_shop_description", "&aSet the shop description to %value%");
        languageNodes.put("set_command.changed_shop_icon", "&aSet the shop icon to the item in your hand!");

        languageNodes.put("shop.already_exists", "&cA shop with the id &4%shop_id% &calready exists!");
        languageNodes.put("shop.does_not_exists", "&cCould not find any shops with the id &4%shop_id%");
        languageNodes.put("shop.created", "&aCreated a new shop with the id &2%shop_id%");
        languageNodes.put("shop.removed", "&cRemoved the shop with the id &4%shop_id%");
        languageNodes.put("shop.saved_inventory_edit_for_list", "&aSaved the inventory edits for /shops");
        languageNodes.put("shop.saved_inventory_edit_for_selection", "&aSaved the inventory edits for item selection");
        languageNodes.put("shop.updated_shop_settings", "&aUpdated the settings for shop: &2%shop_id%");
        languageNodes.put("shop.fail_updated_shop_settings", "&cCould not update the settings for shop: &2%shop_id%");
        languageNodes.put("shop.add_new_item", "&aAdded the item to the &2%shop_id% &ashop &f(&aS: $%sell_price%, B: $%buy_price% &f)");


        languageNodes.put("commands.syntax.additem", "additem <shop> <sellPrice> <buyprice> [sellOnly] [buyOnly]");
        languageNodes.put("commands.syntax.cart", "cart");
        languageNodes.put("commands.syntax.convert", "convert [confirm]");
        languageNodes.put("commands.syntax.create", "create <name>");
        languageNodes.put("commands.syntax.edit", "edit");
        languageNodes.put("commands.syntax.list", "list");
        languageNodes.put("commands.syntax.open", "open <shop>");
        languageNodes.put("commands.syntax.reload", "reload");
        languageNodes.put("commands.syntax.remove", "remove <name>");
        languageNodes.put("commands.syntax.set", "set <shop> <option> [value]");
        languageNodes.put("commands.syntax.settings", "settings");
        languageNodes.put("commands.syntax.shop", "/shops");

        languageNodes.put("commands.description.additem", "Used to add an item to a shop");
        languageNodes.put("commands.description.cart", "Open your cart");
        languageNodes.put("commands.description.convert", "Used to convert from pre 2.0.0+");
        languageNodes.put("commands.description.create", "Used to create a new shop");
        languageNodes.put("commands.description.edit", "Used to edit a shop settings.");
        languageNodes.put("commands.description.list", "Lists all the available shops");
        languageNodes.put("commands.description.open", "Used to open a specific shop");
        languageNodes.put("commands.description.reload", "Used to reload the plugin files");
        languageNodes.put("commands.description.remove", "Used to remove an existing shop");
        languageNodes.put("commands.description.set", "Used to set a specific shop setting.");
        languageNodes.put("commands.description.settings", "Used to open the settings menu.");
        languageNodes.put("commands.description.shop", "Open the shop selection menu");

    }

    public static void setup() {
        Config config = Shops.getInstance().getLocale().getConfig();

        languageNodes.keySet().forEach(key -> {
            config.setDefault(key, languageNodes.get(key));
        });

        config.setAutoremove(true).setAutosave(true);
        config.saveChanges();
    }
}
