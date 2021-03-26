package ca.tweetzy.shops.guis;

import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.custom.CustomGUIItemHolder;
import ca.tweetzy.shops.helpers.ConfigurationItemHelper;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.Shop;

import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 25 2021
 * Time Created: 9:59 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class GUIShopEdit extends Gui {

    final Shop shop;
    List<CustomGUIItemHolder> customItems;
    int clicksToEdit = 0;
    boolean editing = false;

    public GUIShopEdit(Shop shop) {
        this.shop = shop;
        setTitle(TextUtils.formatText(Settings.GUI_SHOP_EDIT_TITLE.getString().replace("%shop_id%", this.shop.getId())));
        setRows(6);
        setDefaultItem(Settings.GUI_SHOP_EDIT_BG_ITEM.getMaterial().parseItem());
        setAcceptsItems(false);
        setAllowDrops(false);
        handleEdit();
        draw();
    }

    private void draw() {
        reset();
        // fill in the background first
        if (Settings.GUI_SHOP_EDIT_FILL_BG.getBoolean()) {
            IntStream.range(0, getRows() * 9).forEach(i -> setItem(i, getDefaultItem()));
        }

        if (!Settings.GUI_SHOP_EDIT_USE_DEFAULT_SLOTS.getBoolean()) {
            if (this.customItems.stream().noneMatch(holders -> holders.getGuiName().equalsIgnoreCase("edit"))) {
                this.customItems.add(new CustomGUIItemHolder("edit"));
            } else {
                this.customItems.stream().filter(holders -> holders.getGuiName().equalsIgnoreCase("edit")).findFirst().get().getItems().forEach(item -> {

                });
            }
        } else {
            // Public
            setButton(2, 1, ConfigurationItemHelper.build(this.shop.isPublic() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_OFF_ITEM.getString(), this.shop.isPublic() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_OFF_NAME.getString(), this.shop.isPublic() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_OFF_LORE.getStringList(), null, "shops:edit:button;public"), e -> handleClick());
            // Sell Only
            setButton(3, 1, ConfigurationItemHelper.build(this.shop.isSellOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_OFF_ITEM.getString(), this.shop.isSellOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_OFF_NAME.getString(), this.shop.isSellOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_OFF_LORE.getStringList(), null, "shops:edit:button;sell_only"), e -> handleClick());
            // Buy Only
            setButton(4, 1, ConfigurationItemHelper.build(this.shop.isBuyOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_OFF_ITEM.getString(), this.shop.isBuyOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_OFF_NAME.getString(), this.shop.isBuyOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_OFF_LORE.getStringList(), null, "shops:edit:button;buy_only"), e -> handleClick());
            // Sell Discount
            setButton(2, 2, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_SELL_DISCOUNT_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SELL_DISCOUNT_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SELL_DISCOUNT_LORE.getStringList(), new HashMap<String, Object>(){{
                put("%shop_sell_discount%", shop.getSellDiscount());
                put("%shop_sell_discount_enable%", shop.isUseSellDiscount());
            }}, "shops:edit:button;sell_discount"), e -> handleClick());
            // Buy Discount
            setButton(2, 2, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_BUY_DISCOUNT_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_BUY_DISCOUNT_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_BUY_DISCOUNT_LORE.getStringList(), new HashMap<String, Object>(){{
                put("%shop_buy_discount%", shop.getBuyDiscount());
                put("%shop_buy_discount_enable%", shop.isUseBuyDiscount());
            }}, "shops:edit:button;buy_discount"), e -> handleClick());

            // Display Icon
            setButton(1, 4, ConfigurationItemHelper.build(ShopAPI.getInstance().deserializeItem(this.shop.getDisplayIcon()), Settings.GUI_SHOP_EDIT_ITEMS_DISPLAY_ICON_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_DISPLAY_ICON_LORE.getStringList(), null, "shops:edit:button;display_icon"), e -> handleClick());
            // Display Name
            setButton(2, 4, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_DISPLAY_NAME_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_DISPLAY_NAME_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_DISPLAY_NAME_LORE.getStringList(), new HashMap<String, Object>(){{
                put("%shop_display_name%", shop.getDisplayName());
            }}, "shops:edit:button;display_name"), e -> handleClick());
            // Description
            setButton(3, 4, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_DESCRIPTION_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_DESCRIPTION_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_DESCRIPTION_LORE.getStringList(), new HashMap<String, Object>(){{
                put("%shop_description%", shop.getDescription());
            }}, "shops:edit:button;description"), e -> handleClick());
            // Contents
            setButton(4, 4, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_CONTENTS_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_CONTENTS_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_CONTENTS_LORE.getStringList(), new HashMap<String, Object>(){{
                put("%shop_item_count%", shop.getShopItems().size());
            }}, "shops:edit:button;contents"), e -> handleClick());

            // Toggle Require Perm to See
            setButton(2, 6, ConfigurationItemHelper.build(this.shop.isRequiresPermissionToSee() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_OFF_ITEM.getString(), this.shop.isRequiresPermissionToSee() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_OFF_NAME.getString(), this.shop.isRequiresPermissionToSee() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_OFF_LORE.getStringList(), null, "shops:edit:button;permission_to_see_toggle"), e -> handleClick());
            // Toggle Require Perm to Sell
            setButton(3, 6, ConfigurationItemHelper.build(this.shop.isRequiresPermissionToSell() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_OFF_ITEM.getString(), this.shop.isRequiresPermissionToSell() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_OFF_NAME.getString(), this.shop.isRequiresPermissionToSell() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_OFF_LORE.getStringList(), null, "shops:edit:button;permission_to_sell_toggle"), e -> handleClick());
            // Toggle Require Perm to Buy
            setButton(4, 6, ConfigurationItemHelper.build(this.shop.isRequiresPermissionToBuy() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_OFF_ITEM.getString(), this.shop.isRequiresPermissionToBuy() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_OFF_NAME.getString(), this.shop.isRequiresPermissionToBuy() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_OFF_LORE.getStringList(), null, "shops:edit:button;permission_to_buy_toggle"), e -> handleClick());
            // Permission to see
            setButton(2, 7, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_SEE_PERMISSION_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SEE_PERMISSION_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SEE_PERMISSION_LORE.getStringList(), new HashMap<String, Object>(){{
                put("%shop_see_permission%", shop.getSeePermission());
            }}, "shops:edit:button;see_permission"), e -> handleClick());
            // Permission to sell
            setButton(3, 7, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_SELL_PERMISSION_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SELL_PERMISSION_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SELL_PERMISSION_LORE.getStringList(), new HashMap<String, Object>(){{
                put("%shop_sell_permission%", shop.getSellPermission());
            }}, "shops:edit:button;sell_permission"), e -> handleClick());
            // Permission to buy
            setButton(3, 7, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_BUY_PERMISSION_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_BUY_PERMISSION_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_BUY_PERMISSION_LORE.getStringList(), new HashMap<String, Object>(){{
                put("%shop_buy_permission%", shop.getBuyPermission());
            }}, "shops:edit:button;buy_permission"), e -> handleClick());
        }
    }

    // not directly setting action to slots since the slot an item
    // is in, is not guaranteed due to the edit system
    private void handleClick() {

    }

    private void handleEdit() {
    }
}
