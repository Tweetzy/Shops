package ca.tweetzy.shops.guis;

import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.gui.GuiUtils;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.core.utils.items.TItemBuilder;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.helpers.ConfigurationItemHelper;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.Shop;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 11:42 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class GUIShopsList extends Gui {

    final List<Shop> shops;

    public GUIShopsList() {
        this.shops = Shops.getInstance().getShopManager().getShops();
        setTitle(TextUtils.formatText(Settings.GUI_SHOP_LIST_TITLE.getString()));
        setDefaultItem(Settings.GUI_SHOP_LIST_BG_ITEM.getMaterial().parseItem());
        setUseLockedCells(Settings.GUI_SHOP_LIST_FILL_BG.getBoolean());
        setAcceptsItems(false);
        setRows(6);
        draw();
    }

    private void draw() {
        reset();

        pages = (int) Math.max(1, Math.ceil(this.shops.size() / (double) 45));
        setPrevPage(5, 3, new TItemBuilder(Objects.requireNonNull(Settings.GUI_BACK_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_BACK_BTN_NAME.getString()).setLore(Settings.GUI_BACK_BTN_LORE.getStringList()).toItemStack());
        setButton(5, 4, new TItemBuilder(Objects.requireNonNull(Settings.GUI_REFRESH_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_REFRESH_BTN_NAME.getString()).setLore(Settings.GUI_REFRESH_BTN_LORE.getStringList()).toItemStack(), e -> e.manager.showGUI(e.player, new GUIShopsList()));
        setNextPage(5, 5, new TItemBuilder(Objects.requireNonNull(Settings.GUI_NEXT_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_NEXT_BTN_NAME.getString()).setLore(Settings.GUI_NEXT_BTN_LORE.getStringList()).toItemStack());
        setOnPage(e -> draw());

        int slot = 0;
        List<Shop> data = this.shops.stream().skip((page - 1) * 45L).limit(45).collect(Collectors.toList());
        for (Shop shop : data) {
            setItem(slot++, ConfigurationItemHelper.build(ShopAPI.getInstance().deserializeItem(shop.getDisplayIcon()), Settings.GUI_SHOP_ITEM_NAME.getString(), Settings.GUI_SHOP_ITEM_LORE.getStringList(), new HashMap<String, Object>() {{
                put("%shop_id%", shop.getId());
                put("%shop_display_name%", shop.getDisplayName());
                put("%shop_is_public%", shop.isPublic());
                put("%shop_is_sell_only%", shop.isSellOnly());
                put("%shop_is_buy_only%", shop.isBuyOnly());
                put("%shop_item_count%", shop.getShopItems().size());
            }}));
        }
    }
}
