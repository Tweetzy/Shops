package ca.tweetzy.shops.guis;

import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.gui.events.GuiClickEvent;
import ca.tweetzy.core.input.ChatPrompt;
import ca.tweetzy.core.utils.NumberUtils;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.helpers.ConfigurationItemHelper;
import ca.tweetzy.shops.managers.StorageManager;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.event.inventory.ClickType;

import java.util.HashMap;
import java.util.stream.IntStream;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 25 2021
 * Time Created: 9:59 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class GUIShopEdit extends Gui {

    final Shop shop;
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

        // Public
        setButton(2, 1, ConfigurationItemHelper.build(this.shop.isPublic() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_OFF_ITEM.getString(), this.shop.isPublic() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_OFF_NAME.getString(), this.shop.isPublic() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PUBLIC_OFF_LORE.getStringList(), null), e -> {
            this.shop.setPublic(!this.shop.isPublic());
            this.changes = true;
            draw();
        });

        // Sell Only
        setButton(3, 1, ConfigurationItemHelper.build(this.shop.isSellOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_OFF_ITEM.getString(), this.shop.isSellOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_OFF_NAME.getString(), this.shop.isSellOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_SELL_ONLY_OFF_LORE.getStringList(), null), e -> {
            this.shop.setSellOnly(!this.shop.isSellOnly());
            this.changes = true;
            draw();
        });

        // Buy Only
        setButton(4, 1, ConfigurationItemHelper.build(this.shop.isBuyOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_OFF_ITEM.getString(), this.shop.isBuyOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_OFF_NAME.getString(), this.shop.isBuyOnly() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_BUY_ONLY_OFF_LORE.getStringList(), null), e -> {
            this.shop.setBuyOnly(!this.shop.isBuyOnly());
            this.changes = true;
            draw();
        });

        // Sell Discount
        setButton(2, 2, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_SELL_DISCOUNT_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SELL_DISCOUNT_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SELL_DISCOUNT_LORE.getStringList(), new HashMap<String, Object>() {{
            put("%shop_sell_bonus%", shop.getSellBonus());
            put("%shop_sell_bonus_enable%", shop.isUseSellBonus());
        }}), e -> {
            if (e.clickType == ClickType.LEFT) {
                handleInputRequiredSetting("sell_bonus", e);
            }

            if (e.clickType == ClickType.RIGHT) {
                this.shop.setUseSellBonus(!this.shop.isUseSellBonus());
                this.changes = true;
                draw();
            }
        });

        // Buy Discount
        setButton(3, 2, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_BUY_DISCOUNT_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_BUY_DISCOUNT_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_BUY_DISCOUNT_LORE.getStringList(), new HashMap<String, Object>() {{
            put("%shop_buy_discount%", shop.getBuyDiscount());
            put("%shop_buy_discount_enable%", shop.isUseBuyDiscount());
        }}), e -> {
            if (e.clickType == ClickType.LEFT) {
                handleInputRequiredSetting("buy_discount", e);
            }

            if (e.clickType == ClickType.RIGHT) {
                this.shop.setUseBuyDiscount(!this.shop.isUseBuyDiscount());
                this.changes = true;
                draw();
            }
        });

        // Display Icon
        setButton(1, 4, ConfigurationItemHelper.build(ShopAPI.getInstance().deserializeItem(this.shop.getDisplayIcon()), Settings.GUI_SHOP_EDIT_ITEMS_DISPLAY_ICON_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_DISPLAY_ICON_LORE.getStringList(), 1, null), e -> {
            Shops.getInstance().getOutOfGuiAccess().put(e.player.getUniqueId(), this.shop);
            Shops.getInstance().getLocale().getMessage("general.change_icon").sendPrefixedMessage(e.player);
            e.gui.exit();
        });

        // Display Name
        setButton(2, 4, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_DISPLAY_NAME_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_DISPLAY_NAME_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_DISPLAY_NAME_LORE.getStringList(), new HashMap<String, Object>() {{
            put("%shop_display_name%", shop.getDisplayName());
        }}), e -> handleInputRequiredSetting("display_name", e));

        // Description
        setButton(3, 4, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_DESCRIPTION_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_DESCRIPTION_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_DESCRIPTION_LORE.getStringList(), new HashMap<String, Object>() {{
            put("%shop_description%", shop.getDescription());
        }}), e -> handleInputRequiredSetting("description", e));

        // Contents
        setButton(4, 4, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_CONTENTS_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_CONTENTS_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_CONTENTS_LORE.getStringList(), new HashMap<String, Object>() {{
            put("%shop_item_count%", shop.getShopItems().size());
        }}), e -> {
            e.gui.exit();
            e.manager.showGUI(e.player, new GUIShopContents(e.player, this.shop, true));
        });

        // Toggle Require Perm to See
        setButton(2, 6, ConfigurationItemHelper.build(this.shop.isRequiresPermissionToSee() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_OFF_ITEM.getString(), this.shop.isRequiresPermissionToSee() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_OFF_NAME.getString(), this.shop.isRequiresPermissionToSee() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SEE_OFF_LORE.getStringList(), null), e -> {
            this.shop.setRequiresPermissionToSee(!this.shop.isRequiresPermissionToSee());
            this.changes = true;
            draw();
        });

        // Toggle Require Perm to Sell
        setButton(3, 6, ConfigurationItemHelper.build(this.shop.isRequiresPermissionToSell() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_OFF_ITEM.getString(), this.shop.isRequiresPermissionToSell() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_OFF_NAME.getString(), this.shop.isRequiresPermissionToSell() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_SELL_OFF_LORE.getStringList(), null), e -> {
            this.shop.setRequiresPermissionToSell(!this.shop.isRequiresPermissionToSell());
            this.changes = true;
            draw();
        });

        // Toggle Require Perm to Buy
        setButton(4, 6, ConfigurationItemHelper.build(this.shop.isRequiresPermissionToBuy() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_ON_ITEM.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_OFF_ITEM.getString(), this.shop.isRequiresPermissionToBuy() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_ON_NAME.getString() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_OFF_NAME.getString(), this.shop.isRequiresPermissionToBuy() ? Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_ON_LORE.getStringList() : Settings.GUI_SHOP_EDIT_ITEMS_TOGGLE_PERM_TO_BUY_OFF_LORE.getStringList(), null), e -> {
            this.shop.setRequiresPermissionToBuy(!this.shop.isRequiresPermissionToBuy());
            this.changes = true;
            draw();
        });

        // Permission to see
        setButton(2, 7, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_SEE_PERMISSION_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SEE_PERMISSION_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SEE_PERMISSION_LORE.getStringList(), new HashMap<String, Object>() {{
            put("%shop_see_permission%", shop.getSeePermission());
        }}), e -> handleInputRequiredSetting("see_permission", e));

        // Permission to sell
        setButton(3, 7, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_SELL_PERMISSION_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SELL_PERMISSION_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_SELL_PERMISSION_LORE.getStringList(), new HashMap<String, Object>() {{
            put("%shop_sell_permission%", shop.getSellPermission());
        }}), e -> handleInputRequiredSetting("sell_permission", e));

        // Permission to buy
        setButton(4, 7, ConfigurationItemHelper.build(Settings.GUI_SHOP_EDIT_ITEMS_BUY_PERMISSION_ITEM.getString(), Settings.GUI_SHOP_EDIT_ITEMS_BUY_PERMISSION_NAME.getString(), Settings.GUI_SHOP_EDIT_ITEMS_BUY_PERMISSION_LORE.getStringList(), new HashMap<String, Object>() {{
            put("%shop_buy_permission%", shop.getBuyPermission());
        }}), e -> handleInputRequiredSetting("buy_permission", e));

        // add highlighting
        if (this.shop.isPublic()) highlightItem(2, 1);
        if (this.shop.isSellOnly()) highlightItem(3, 1);
        if (this.shop.isBuyOnly()) highlightItem(4, 1);
        if (this.shop.isUseSellBonus()) highlightItem(2, 2);
        if (this.shop.isUseBuyDiscount()) highlightItem(3, 2);
        if (this.shop.isRequiresPermissionToSee()) highlightItem(2, 6);
        if (this.shop.isRequiresPermissionToSell()) highlightItem(3, 6);
        if (this.shop.isRequiresPermissionToBuy()) highlightItem(4, 6);
    }

    private void handleInputRequiredSetting(String option, GuiClickEvent e) {
        e.gui.exit();
        String sendMessage = "";

        switch (option) {
            case "sell_bonus":
                sendMessage = "general.percentage_change.sell_percentage";
                break;
            case "buy_discount":
                sendMessage = "general.percentage_change.buy_percentage";
                break;
            case "display_name":
                sendMessage = "general.enter_new_display_name";
                break;
            case "description":
                sendMessage = "general.change_description";
                break;
            case "see_permission":
                sendMessage = "general.permission_change.see";
                break;
            case "buy_permission":
                sendMessage = "general.permission_change.sell";
                break;
        }

        ChatPrompt.showPrompt(Shops.getInstance(), e.player, Shops.getInstance().getLocale().getMessage(sendMessage).getMessage(), chat -> {
            switch (option) {
                case "sell_bonus":
                    if (NumberUtils.isDouble(chat.getMessage().trim()))
                        this.shop.setSellBonus(Double.parseDouble(chat.getMessage().trim()));
                    break;
                case "buy_discount":
                    if (NumberUtils.isDouble(chat.getMessage().trim()))
                        this.shop.setBuyDiscount(Double.parseDouble(chat.getMessage().trim()));
                    break;
                case "display_name":
                    if (chat.getMessage().trim().length() != 0)
                        this.shop.setDisplayName(chat.getMessage().trim());
                    break;
                case "description":
                    if (chat.getMessage().trim().length() != 0)
                        this.shop.setDescription(chat.getMessage().trim());
                    break;
                case "see_permission":
                    if (chat.getMessage().trim().length() != 0)
                        this.shop.setSellPermission(chat.getMessage().trim());
                    break;
                case "buy_permission":
                    if (chat.getMessage().trim().length() != 0)
                        this.shop.setBuyPermission(chat.getMessage().trim());
                    break;
            }
        }).setOnClose(() -> {
            e.manager.showGUI(e.player, new GUIShopEdit(this.shop));
            StorageManager.getInstance().updateShop(e.player, this.shop);
        }).setOnCancel(() -> e.manager.showGUI(e.player, this));
    }
}
