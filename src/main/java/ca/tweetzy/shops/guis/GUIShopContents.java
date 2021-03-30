package ca.tweetzy.shops.guis;

import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.core.utils.items.TItemBuilder;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.helpers.ConfigurationItemHelper;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.Shop;
import ca.tweetzy.shops.shop.ShopItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public GUIShopContents(Player player, Shop shop) {
        this.player = player;
        this.shop = shop;
        this.shopItems = this.shop.getShopItems();
        setTitle(TextUtils.formatText(Settings.GUI_SHOP_CONTENTS_TITLE.getString().replace("%shop_display_name%", this.shop.getDisplayName()).replace("%shop_id%", this.shop.getId())));
        setDefaultItem(Settings.GUI_SHOP_CONTENTS_BG_ITEM.getMaterial().parseItem());
        setUseLockedCells(Settings.GUI_SHOP_CONTENTS_FILL_BG.getBoolean());
        setAcceptsItems(false);

        if (this.shopItems.size() >= 1 && this.shopItems.size() <= 9) setRows(2);
        if (this.shopItems.size() >= 10 && this.shopItems.size() <= 18) setRows(2);
        if (this.shopItems.size() >= 19 && this.shopItems.size() <= 27) setRows(3);
        if (this.shopItems.size() >= 28 && this.shopItems.size() <= 36) setRows(4);
        if (this.shopItems.size() >= 37 && this.shopItems.size() <= 45) setRows(5);
        if (this.shopItems.size() >= 46) setRows(6);

        draw();
    }

    private void draw() {
        reset();

        pages = (int) Math.max(1, Math.ceil(this.shopItems.size() / (double) 45));
        setPrevPage(getRows() - 1, 3, new TItemBuilder(Objects.requireNonNull(Settings.GUI_BACK_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_BACK_BTN_NAME.getString()).setLore(Settings.GUI_BACK_BTN_LORE.getStringList()).toItemStack());
        setNextPage(getRows() - 1, 5, new TItemBuilder(Objects.requireNonNull(Settings.GUI_NEXT_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_NEXT_BTN_NAME.getString()).setLore(Settings.GUI_NEXT_BTN_LORE.getStringList()).toItemStack());
        setOnPage(e -> draw());

        // cart button
        setButton(getRows() - 1, 8, ConfigurationItemHelper.build(Settings.GUI_SHOP_CONTENTS_CART_ITEM.getString(), Settings.GUI_SHOP_CONTENTS_CART_NAME.getString(), Settings.GUI_SHOP_CONTENTS_CART_LORE.getStringList(), new HashMap<String, Object>() {{
            put("%shop_cart_item_count%", Shops.getInstance().getPlayerCart().containsKey(player.getUniqueId()) ? Shops.getInstance().getPlayerCart().get(player.getUniqueId()).size() : 0);
            put("%shop_cart_total%", Shops.getInstance().getPlayerCart().containsKey(player.getUniqueId()) ? Shops.getInstance().getPlayerCart().get(player.getUniqueId()).stream().mapToDouble(ShopItem::getBuyPrice).sum() : 0D);
        }}), e -> e.manager.showGUI(this.player, new GUICart(this.player)));

        int slot = 0;
        List<ShopItem> data = this.shopItems.stream().skip((page - 1) * 45L).limit(45).collect(Collectors.toList());
        for (ShopItem item : data) {
            ItemStack parsed = ShopAPI.getInstance().deserializeItem(item.getItem());
            setButton(slot++, ConfigurationItemHelper.build(parsed, Settings.GUI_SHOP_CONTENTS_ITEM_NAME.getString(), Settings.GUI_SHOP_CONTENTS_ITEM_LORE.getStringList(), new HashMap<String, Object>() {{
                put("%shop_item_name%", item.getName());
                put("%shop_item_sell_price%", item.isBuyOnly() ? "&cN/A" : String.format("%,.2f", item.getSellPrice()));
                put("%shop_item_buy_price%", item.isSellOnly() ? "&cN/A" : String.format("%,.2f", item.getBuyPrice()));
                put("%shop_item_quantity%", parsed.getAmount());
            }}), e -> e.manager.showGUI(e.player, new GUIItemSelection(item)));
        }
    }
}
