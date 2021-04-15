package ca.tweetzy.shops.guis;

import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.input.ChatPrompt;
import ca.tweetzy.core.utils.NumberUtils;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.core.utils.items.TItemBuilder;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.helpers.ConfigurationItemHelper;
import ca.tweetzy.shops.managers.StorageManager;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.CartItem;
import ca.tweetzy.shops.shop.Shop;
import ca.tweetzy.shops.shop.ShopItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 29 2021
 * Time Created: 5:12 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class GUIShopContents extends Gui {

    final Shop shop;
    final List<ShopItem> shopItems;
    final Player player;
    final boolean isEdit;

    public GUIShopContents(Player player, Shop shop, boolean isEdit, boolean fromOpenCommand) {
        this.player = player;
        this.shop = shop;
        this.shopItems = this.shop.getShopItems();
        this.isEdit = isEdit;
        setTitle(TextUtils.formatText(Settings.GUI_SHOP_CONTENTS_TITLE.getString().replace("%shop_display_name%", this.shop.getDisplayName()).replace("%shop_id%", this.shop.getId())));
        setDefaultItem(Settings.GUI_SHOP_CONTENTS_BG_ITEM.getMaterial().parseItem());
        setAcceptsItems(false);

        if (this.shopItems.size() >= 1 && this.shopItems.size() <= 9)
            setRows(Settings.USE_CART_SYSTEM.getBoolean() ? 2 : 1);
        if (this.shopItems.size() >= 10 && this.shopItems.size() <= 18)
            setRows(Settings.USE_CART_SYSTEM.getBoolean() ? 3 : 2);
        if (this.shopItems.size() >= 19 && this.shopItems.size() <= 27)
            setRows(Settings.USE_CART_SYSTEM.getBoolean() ? 4 : 3);
        if (this.shopItems.size() >= 28 && this.shopItems.size() <= 36)
            setRows(Settings.USE_CART_SYSTEM.getBoolean() ? 5 : 6);
        if (this.shopItems.size() >= 37) setRows(6);

        draw();

        setOnClose(close -> {
            if (this.isEdit) {
                StorageManager.getInstance().updateShop(this.player, this.shop);
                close.manager.showGUI(this.player, new GUIShopEdit(this.shop));
            } else {
                if (!fromOpenCommand)
                    close.manager.showGUI(this.player, new GUIShops(this.player));
            }
        });
    }

    private void draw() {
        reset();

        pages = (int) Math.max(1, Math.ceil(this.shopItems.size() / (double) 45));

        if (Settings.GUI_SHOP_CONTENTS_FILL_BG.getBoolean()) {
            IntStream.range(0, getRows() * 9).forEach(i -> setItem(i, Settings.GUI_SHOP_CONTENTS_BG_ITEM.getMaterial().parseItem()));
        }

        setPrevPage(getRows() - 1, 3, new TItemBuilder(Objects.requireNonNull(Settings.GUI_BACK_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_BACK_BTN_NAME.getString()).setLore(Settings.GUI_BACK_BTN_LORE.getStringList()).toItemStack());
        setNextPage(getRows() - 1, 5, new TItemBuilder(Objects.requireNonNull(Settings.GUI_NEXT_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_NEXT_BTN_NAME.getString()).setLore(Settings.GUI_NEXT_BTN_LORE.getStringList()).toItemStack());
        setOnPage(e -> draw());

        // cart button
        if (Settings.USE_CART_SYSTEM.getBoolean()) {
            setButton(getRows() - 1, 8, ConfigurationItemHelper.build(Settings.GUI_SHOP_CONTENTS_CART_ITEM.getString(), Settings.GUI_SHOP_CONTENTS_CART_NAME.getString(), Settings.GUI_SHOP_CONTENTS_CART_LORE.getStringList(), new HashMap<String, Object>() {{
                put("%shop_cart_item_count%", Shops.getInstance().getPlayerCart().containsKey(player.getUniqueId()) ? Shops.getInstance().getPlayerCart().get(player.getUniqueId()).size() : 0);
                put("%shop_cart_total%", Shops.getInstance().getPlayerCart().containsKey(player.getUniqueId()) ? Shops.getInstance().getPlayerCart().get(player.getUniqueId()).stream().mapToDouble(ShopItem::getBuyPrice).sum() : 0D);
            }}), e -> e.manager.showGUI(this.player, new GUICart(this.player)));
        }

        int slot = 0;
        List<ShopItem> data = this.shopItems.stream().skip((page - 1) * 45L).limit(45).collect(Collectors.toList());
        for (ShopItem item : data) {
            ItemStack parsed = ShopAPI.getInstance().deserializeItem(item.getItem());
            setButton(slot++, ConfigurationItemHelper.build(parsed, Settings.GUI_SHOP_CONTENTS_ITEM_NAME.getString(), this.isEdit ? Settings.GUI_SHOP_CONTENTS_ITEM_LORE_EDIT.getStringList() : Settings.GUI_SHOP_CONTENTS_ITEM_LORE.getStringList(), 1, new HashMap<String, Object>() {{
                put("%shop_item_name%", item.getName());
                put("%shop_item_sell_price%", isEdit ? String.format("%,.2f", item.getSellPrice()) : item.isBuyOnly() ? "&c N/A" : String.format("%,.2f", item.getSellPrice()));
                put("%shop_item_buy_price%", isEdit ? String.format("%,.2f", item.getBuyPrice()) : item.isSellOnly() ? "&c N/A" : String.format("%,.2f", item.getBuyPrice()));
                put("%shop_item_sell_only%", item.isSellOnly());
                put("%shop_item_buy_only%", item.isBuyOnly());
                put("%shop_item_quantity%", parsed.getAmount());
            }}), e -> {
                if (!isEdit) {
                    if (e.clickType == ClickType.LEFT) {
                        e.manager.showGUI(e.player, new GUIItemSelection(item));
                    }

                    if (e.clickType == ClickType.RIGHT) {
                        if (Settings.USE_CART_SYSTEM.getBoolean()) {
                            if (this.shop.isRequiresPermissionToBuy() && !this.player.hasPermission(this.shop.getBuyPermission()))
                                return;
                            if (this.shop.isSellOnly()) return;
                            if (item.isSellOnly()) return;

                            if (Shops.getInstance().getPlayerCart().containsKey(e.player.getUniqueId())) {
                                Shops.getInstance().getPlayerCart().get(e.player.getUniqueId()).add(new CartItem(item, 1));
                            } else {
                                Shops.getInstance().getPlayerCart().putIfAbsent(e.player.getUniqueId(), new ArrayList<CartItem>() {{
                                    add(new CartItem(item, 1));
                                }});
                            }
                            draw();
                        }
                    }
                } else {
                    if (e.clickType == ClickType.SHIFT_LEFT) {
                        item.setBuyOnly(!item.isBuyOnly());
                        e.manager.showGUI(this.player, new GUIShopContents(this.player, this.shop, true, false));
                    }

                    if (e.clickType == ClickType.SHIFT_RIGHT) {
                        item.setSellOnly(!item.isSellOnly());
                        e.manager.showGUI(this.player, new GUIShopContents(this.player, this.shop, true, false));
                    }

                    if (e.clickType == ClickType.MIDDLE) {
                        this.shop.getShopItems().remove(item);
                        e.manager.showGUI(this.player, new GUIShopContents(this.player, this.shop, true, false));
                    }

                    if (e.clickType == ClickType.LEFT) {
                        e.gui.exit();
                        ChatPrompt.showPrompt(Shops.getInstance(), this.player, TextUtils.formatText(Shops.getInstance().getLocale().getMessage("general.change_buy_price").getMessage()), chat -> {
                            if (NumberUtils.isDouble(chat.getMessage().trim()))
                                item.setBuyPrice(Double.parseDouble(chat.getMessage().trim()));
                        }).setOnClose(() -> e.manager.showGUI(this.player, new GUIShopContents(this.player, this.shop, true, false))).setOnCancel(() -> e.manager.showGUI(this.player, this));
                    }

                    if (e.clickType == ClickType.RIGHT) {
                        e.gui.exit();
                        ChatPrompt.showPrompt(Shops.getInstance(), this.player, TextUtils.formatText(Shops.getInstance().getLocale().getMessage("general.change_sell_price").getMessage()), chat -> {
                            if (NumberUtils.isDouble(chat.getMessage().trim()))
                                item.setSellPrice(Double.parseDouble(chat.getMessage().trim()));
                        }).setOnClose(() -> e.manager.showGUI(this.player, new GUIShopContents(this.player, this.shop, true, false))).setOnCancel(() -> e.manager.showGUI(this.player, this));
                    }
                }
            });
        }
    }
}
