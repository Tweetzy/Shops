package ca.tweetzy.shops.guis;

import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.input.ChatPrompt;
import ca.tweetzy.core.utils.NumberUtils;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.core.utils.nms.NBTEditor;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.custom.CustomGUIItemHolder;
import ca.tweetzy.shops.helpers.ConfigurationItemHelper;
import ca.tweetzy.shops.managers.StorageManager;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.event.inventory.ClickType;

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
    boolean changes = false;

    public GUIShopEdit(Shop shop) {
        this.shop = shop;
        setTitle(TextUtils.formatText(Settings.GUI_SHOP_EDIT_TITLE.getString().replace("%shop_id%", this.shop.getId())));
        setRows(6);
        setDefaultItem(Settings.GUI_SHOP_EDIT_BG_ITEM.getMaterial().parseItem());
        setAcceptsItems(false);
        setAllowDrops(false);
        draw();

        setOnClose(close -> {
            if (this.changes) StorageManager.getInstance().updateShop(close.player, this.shop);
        });
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
            setItem(2, 1, ConfigurationItemHelper.build(this.shop.isPublic() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_OFF_ITEM.getString(), this.shop.isPublic() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_OFF_NAME.getString(), this.shop.isPublic() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_OFF_LORE.getStringList(), null, "shops:edit:button;public"));
            // Sell Only
            setItem(3, 1, ConfigurationItemHelper.build(this.shop.isSellOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_OFF_ITEM.getString(), this.shop.isSellOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_OFF_NAME.getString(), this.shop.isSellOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_OFF_LORE.getStringList(), null, "shops:edit:button;sell_only"));
            // Buy Only
            setItem(4, 1, ConfigurationItemHelper.build(this.shop.isBuyOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_OFF_ITEM.getString(), this.shop.isBuyOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_OFF_NAME.getString(), this.shop.isBuyOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_OFF_LORE.getStringList(), null, "shops:edit:button;buy_only"));
            // Sell Discount
            setItem(2, 2, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_SELL_DISCOUNT_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SELL_DISCOUNT_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SELL_DISCOUNT_LORE.getStringList(), new HashMap<String, Object>() {{
                put("%shop_sell_discount%", shop.getSellDiscount());
                put("%shop_sell_discount_enable%", shop.isUseSellDiscount());
            }}, "shops:edit:button;sell_discount"));
            // Buy Discount
            setItem(3, 2, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_BUY_DISCOUNT_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_BUY_DISCOUNT_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_BUY_DISCOUNT_LORE.getStringList(), new HashMap<String, Object>() {{
                put("%shop_buy_discount%", shop.getBuyDiscount());
                put("%shop_buy_discount_enable%", shop.isUseBuyDiscount());
            }}, "shops:edit:button;buy_discount"));
            // Shop Tax
//            setItem(4, 2, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_TAX_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_TAX_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_TAX_LORE.getStringList(), new HashMap<String, Object>() {{
//                put("%shop_tax%", shop.getTax());
//                put("%shop_tax_enable%", shop.isUseTax());
//            }}, "shops:edit:button;tax"));

            // Display Icon
            setItem(1, 4, ConfigurationItemHelper.build(ShopAPI.getInstance().deserializeItem(this.shop.getDisplayIcon()), Settings.GUI_SHOP_EDIT_ITEMS_DISPLAY_ICON_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_DISPLAY_ICON_LORE.getStringList(), null, "shops:edit:button;display_icon"));
            // Display Name
            setItem(2, 4, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_DISPLAY_NAME_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_DISPLAY_NAME_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_DISPLAY_NAME_LORE.getStringList(), new HashMap<String, Object>() {{
                put("%shop_display_name%", shop.getDisplayName());
            }}, "shops:edit:button;display_name"));
            // Description
            setItem(3, 4, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_DESCRIPTION_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_DESCRIPTION_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_DESCRIPTION_LORE.getStringList(), new HashMap<String, Object>() {{
                put("%shop_description%", shop.getDescription());
            }}, "shops:edit:button;description"));
            // Contents
            setItem(4, 4, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_CONTENTS_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_CONTENTS_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_CONTENTS_LORE.getStringList(), new HashMap<String, Object>() {{
                put("%shop_item_count%", shop.getShopItems().size());
            }}, "shops:edit:button;contents"));

            // Toggle Require Perm to See
            setItem(2, 6, ConfigurationItemHelper.build(this.shop.isRequiresPermissionToSee() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_OFF_ITEM.getString(), this.shop.isRequiresPermissionToSee() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_OFF_NAME.getString(), this.shop.isRequiresPermissionToSee() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_OFF_LORE.getStringList(), null, "shops:edit:button;permission_to_see_toggle"));
            // Toggle Require Perm to Sell
            setItem(3, 6, ConfigurationItemHelper.build(this.shop.isRequiresPermissionToSell() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_OFF_ITEM.getString(), this.shop.isRequiresPermissionToSell() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_OFF_NAME.getString(), this.shop.isRequiresPermissionToSell() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_OFF_LORE.getStringList(), null, "shops:edit:button;permission_to_sell_toggle"));
            // Toggle Require Perm to Buy
            setItem(4, 6, ConfigurationItemHelper.build(this.shop.isRequiresPermissionToBuy() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_OFF_ITEM.getString(), this.shop.isRequiresPermissionToBuy() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_OFF_NAME.getString(), this.shop.isRequiresPermissionToBuy() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_OFF_LORE.getStringList(), null, "shops:edit:button;permission_to_buy_toggle"));
            // Permission to see
            setItem(2, 7, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_SEE_PERMISSION_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SEE_PERMISSION_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SEE_PERMISSION_LORE.getStringList(), new HashMap<String, Object>() {{
                put("%shop_see_permission%", shop.getSeePermission());
            }}, "shops:edit:button;see_permission"));
            // Permission to sell
            setItem(3, 7, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_SELL_PERMISSION_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SELL_PERMISSION_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SELL_PERMISSION_LORE.getStringList(), new HashMap<String, Object>() {{
                put("%shop_sell_permission%", shop.getSellPermission());
            }}, "shops:edit:button;sell_permission"));
            // Permission to buy
            setItem(4, 7, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_BUY_PERMISSION_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_BUY_PERMISSION_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_BUY_PERMISSION_LORE.getStringList(), new HashMap<String, Object>() {{
                put("%shop_buy_permission%", shop.getBuyPermission());
            }}, "shops:edit:button;buy_permission"));
        }

        handleClick();
    }

    // not directly setting action to slots since the slot an item
    // is in, is not guaranteed due to the edit system
    private void handleClick() {
        //                                  this is scuffed af
        setActionForRange(0, 53, e -> {
            if (e.clickedItem == null) return;
            if (!NBTEditor.contains(e.clickedItem, "shops:edit:button")) return;
            this.changes = true;
            switch (NBTEditor.getString(e.clickedItem, "shops:edit:button")) {
                case "permission_to_see_toggle":
                    this.shop.setRequiresPermissionToSee(!this.shop.isRequiresPermissionToSee());
                    break;
                case "permission_to_sell_toggle":
                    this.shop.setRequiresPermissionToSell(!this.shop.isRequiresPermissionToSell());
                    break;
                case "permission_to_buy_toggle":
                    this.shop.setRequiresPermissionToBuy(!this.shop.isRequiresPermissionToBuy());
                    break;
                case "public":
                    this.shop.setPublic(!this.shop.isPublic());
                    break;
                case "sell_only":
                    this.shop.setSellOnly(!this.shop.isSellOnly());
                    break;
                case "buy_only":
                    this.shop.setBuyOnly(!this.shop.isBuyOnly());
                    break;
                case "sell_discount":
                    if (e.clickType == ClickType.RIGHT)
                        this.shop.setUseSellDiscount(!this.shop.isUseSellDiscount());
                    if (e.clickType == ClickType.LEFT) {
                        e.gui.exit();
                        ChatPrompt.showPrompt(Shops.getInstance(), e.player, Shops.getInstance().getLocale().getMessage("general.percentage_change.sell_percentage").getMessage(), chat -> {
                            if (NumberUtils.isDouble(chat.getMessage().trim()))
                                this.shop.setSellDiscount(Double.parseDouble(chat.getMessage().trim()));
                        }).setOnClose(() -> e.manager.showGUI(e.player, new GUIShopEdit(this.shop))).setOnCancel(() -> e.manager.showGUI(e.player, this));
                    }
                    break;
                case "buy_discount":
                    if (e.clickType == ClickType.RIGHT) this.shop.setUseBuyDiscount(!this.shop.isUseBuyDiscount());
                    if (e.clickType == ClickType.LEFT) {
                        e.gui.exit();
                        ChatPrompt.showPrompt(Shops.getInstance(), e.player, Shops.getInstance().getLocale().getMessage("general.percentage_change.buy_percentage").getMessage(), chat -> {
                            if (NumberUtils.isDouble(chat.getMessage().trim()))
                                this.shop.setBuyDiscount(Double.parseDouble(chat.getMessage().trim()));
                        }).setOnClose(() -> e.manager.showGUI(e.player, new GUIShopEdit(this.shop))).setOnCancel(() -> e.manager.showGUI(e.player, this));
                    }
                    break;
//                case "tax":
//                    if (e.clickType == ClickType.RIGHT) this.shop.setUseTax(!this.shop.isUseTax());
//                    if (e.clickType == ClickType.LEFT) {
//                        e.gui.exit();
//                        ChatPrompt.showPrompt(Shops.getInstance(), e.player, Shops.getInstance().getLocale().getMessage("general.percentage_change.tax").getMessage(), chat -> {
//                            if (NumberUtils.isDouble(chat.getMessage().trim()))
//                                this.shop.setTax(Double.parseDouble(chat.getMessage().trim()));
//                        }).setOnClose(() -> e.manager.showGUI(e.player, new GUIShopEdit(this.shop))).setOnCancel(() -> e.manager.showGUI(e.player, this));
//                    }
//                    break;
                case "display_icon":
                    Shops.getInstance().getOutOfGuiAccess().put(e.player.getUniqueId(), this.shop);
                    Shops.getInstance().getLocale().getMessage("general.change_icon").sendPrefixedMessage(e.player);
                    e.gui.exit();
                    break;
                case "display_name":
                    e.gui.exit();
                    ChatPrompt.showPrompt(Shops.getInstance(), e.player, Shops.getInstance().getLocale().getMessage("general.enter_new_display_name").getMessage(), chat -> {
                        if (chat.getMessage().trim().length() != 0)
                            this.shop.setDisplayName(chat.getMessage().trim());
                    }).setOnClose(() -> e.manager.showGUI(e.player, new GUIShopEdit(this.shop))).setOnCancel(() -> e.manager.showGUI(e.player, this));
                    break;
                case "description":
                    e.gui.exit();
                    ChatPrompt.showPrompt(Shops.getInstance(), e.player, Shops.getInstance().getLocale().getMessage("general.change_description").getMessage(), chat -> {
                        if (chat.getMessage().trim().length() != 0)
                            this.shop.setDescription(chat.getMessage().trim());
                    }).setOnClose(() -> e.manager.showGUI(e.player, new GUIShopEdit(this.shop))).setOnCancel(() -> e.manager.showGUI(e.player, this));
                    break;
                case "see_permission":
                    e.gui.exit();
                    ChatPrompt.showPrompt(Shops.getInstance(), e.player, Shops.getInstance().getLocale().getMessage("general.permission_change.see").getMessage(), chat -> {
                        if (chat.getMessage().trim().length() != 0)
                            this.shop.setSeePermission(chat.getMessage().trim());
                    }).setOnClose(() -> e.manager.showGUI(e.player, new GUIShopEdit(this.shop))).setOnCancel(() -> e.manager.showGUI(e.player, this));
                    break;
                case "sell_permission":
                    e.gui.exit();
                    ChatPrompt.showPrompt(Shops.getInstance(), e.player, Shops.getInstance().getLocale().getMessage("general.permission_change.sell").getMessage(), chat -> {
                        if (chat.getMessage().trim().length() != 0)
                            this.shop.setSellPermission(chat.getMessage().trim());
                    }).setOnClose(() -> e.manager.showGUI(e.player, new GUIShopEdit(this.shop))).setOnCancel(() -> e.manager.showGUI(e.player, this));
                    break;
                case "buy_permission":
                    e.gui.exit();
                    ChatPrompt.showPrompt(Shops.getInstance(), e.player, Shops.getInstance().getLocale().getMessage("general.permission_change.buy").getMessage(), chat -> {
                        if (chat.getMessage().trim().length() != 0)
                            this.shop.setBuyPermission(chat.getMessage().trim());
                    }).setOnClose(() -> e.manager.showGUI(e.player, new GUIShopEdit(this.shop))).setOnCancel(() -> e.manager.showGUI(e.player, this));
                    break;
            }
            draw();
        });
    }

    //&c&kkkk&r &7Purchase Armor Here &r&c&kkkk
}
