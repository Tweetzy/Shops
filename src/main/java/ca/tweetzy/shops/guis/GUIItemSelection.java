package ca.tweetzy.shops.guis;

import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.gui.events.GuiClickEvent;
import ca.tweetzy.core.utils.PlayerUtils;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.core.utils.items.TItemBuilder;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.custom.CustomGUIItemHolder;
import ca.tweetzy.shops.helpers.ConfigurationItemHelper;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.CartItem;
import ca.tweetzy.shops.shop.ShopItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 29 2021
 * Time Created: 5:15 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class GUIItemSelection extends Gui {

    List<CustomGUIItemHolder> customItems;
    final ShopItem shopItem;
    int quantity = 1;

    public GUIItemSelection(ShopItem shopItem) {
        this.shopItem = shopItem;
        this.customItems = Shops.getInstance().getShopManager().getCustomGuiItems();
        setTitle(TextUtils.formatText(Settings.GUI_ITEM_SELECT_TITLE.getString()));
        setRows(6);
        setDefaultItem(Settings.GUI_ITEM_SELECT_BG_ITEM.getMaterial().parseItem());
        setAcceptsItems(false);
        setAllowDrops(false);
        draw();
    }

    private void draw() {
        reset();
        ItemStack deserializedItem = ShopAPI.getInstance().deserializeItem(this.shopItem.getItem());
        double subTotal = this.shopItem.getBuyPrice() * this.quantity;
        double preTax = Settings.USE_TAX.getBoolean() ? Settings.TAX_AMOUNT.getDouble() : 0.0D;
        double discounts = Shops.getInstance().getShopManager().getShop(this.shopItem.getShopId()).isUseBuyDiscount() ? subTotal * (Shops.getInstance().getShopManager().getShop(this.shopItem.getShopId()).getBuyDiscount() / 100) : 0.0D;
        double totalTax = (subTotal - discounts) * preTax / 100;
        double cartTotal = (subTotal - discounts) + totalTax;

        if (Settings.GUI_ITEM_SELECT_FILL_BG.getBoolean()) {
            IntStream.range(0, getRows() * 9).forEach(i -> setItem(i, getDefaultItem()));
        }

        if (this.customItems.stream().noneMatch(holders -> holders.getGuiName().equalsIgnoreCase("itemselection"))) {
            this.customItems.add(new CustomGUIItemHolder("itemselection"));
        }

        if (!Settings.GUI_ITEM_SELECT_USE_DEFAULT_SLOTS.getBoolean() && this.customItems.stream().filter(holders -> holders.getGuiName().equalsIgnoreCase("itemselection")).findFirst().get().getItems().size() != 0) {
            this.customItems.stream().filter(holders -> holders.getGuiName().equalsIgnoreCase("itemselection")).findFirst().get().getItems().forEach(guiItem -> setButton(guiItem.getSlot(), ShopAPI.getInstance().deserializeItem(guiItem.getItem()), null));
        } else {
            setButton(5, 0, new TItemBuilder(Objects.requireNonNull(Settings.GUI_CLOSE_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_CLOSE_BTN_NAME.getString()).setLore(Settings.GUI_CLOSE_BTN_LORE.getStringList()).toItemStack(), e -> e.manager.showGUI(e.player, new GUIShopContents(e.player, Shops.getInstance().getShopManager().getShop(this.shopItem.getShopId()))));
            setButton(5, 8, new TItemBuilder(Objects.requireNonNull(Settings.GUI_ITEM_SELECT_ITEMS_ADD_TO_CART_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_ITEM_SELECT_ITEMS_ADD_TO_CART_NAME.getString()).setLore(Settings.GUI_ITEM_SELECT_ITEMS_ADD_TO_CART_LORE.getStringList()).toItemStack(), e -> {
                if (Shops.getInstance().getPlayerCart().containsKey(e.player.getUniqueId())) {
                    Shops.getInstance().getPlayerCart().get(e.player.getUniqueId()).add(new CartItem(shopItem, this.quantity));
                } else {
                    Shops.getInstance().getPlayerCart().putIfAbsent(e.player.getUniqueId(), new ArrayList<CartItem>() {{
                        add(new CartItem(shopItem, quantity));
                    }});
                }
            });

            setButton(2, 2, ConfigurationItemHelper.build(Settings.GUI_ITEM_SELECT_ITEMS_DECR_ONE_ITEM.getString(), Settings.GUI_ITEM_SELECT_ITEMS_DECR_ONE_NAME.getString(), Settings.GUI_ITEM_SELECT_ITEMS_DECR_ONE_LORE.getStringList(), Math.min(Settings.GUI_ITEM_SELECT_DECR_ONE.getInt(), 1), new HashMap<String, Object>() {{
                put("%decr_one_amount%", Settings.GUI_ITEM_SELECT_DECR_ONE.getInt());
            }}), e -> handleQuantityButtons(e, Settings.GUI_ITEM_SELECT_DECR_ONE.getInt(), false));

            setButton(3, 2, ConfigurationItemHelper.build(Settings.GUI_ITEM_SELECT_ITEMS_DECR_TWO_ITEM.getString(), Settings.GUI_ITEM_SELECT_ITEMS_DECR_TWO_NAME.getString(), Settings.GUI_ITEM_SELECT_ITEMS_DECR_TWO_LORE.getStringList(), Math.min(Settings.GUI_ITEM_SELECT_DECR_TWO.getInt(), 1), new HashMap<String, Object>() {{
                put("%decr_two_amount%", Settings.GUI_ITEM_SELECT_DECR_TWO.getInt());
            }}), e -> handleQuantityButtons(e, Settings.GUI_ITEM_SELECT_DECR_TWO.getInt(), false));

            setButton(4, 2, ConfigurationItemHelper.build(Settings.GUI_ITEM_SELECT_ITEMS_DECR_THREE_ITEM.getString(), Settings.GUI_ITEM_SELECT_ITEMS_DECR_THREE_NAME.getString(), Settings.GUI_ITEM_SELECT_ITEMS_DECR_THREE_LORE.getStringList(), Math.min(Settings.GUI_ITEM_SELECT_DECR_THREE.getInt(), 1), new HashMap<String, Object>() {{
                put("%decr_three_amount%", Settings.GUI_ITEM_SELECT_DECR_THREE.getInt());
            }}), e -> handleQuantityButtons(e, Settings.GUI_ITEM_SELECT_DECR_THREE.getInt(), false));

            setButton(2, 6, ConfigurationItemHelper.build(Settings.GUI_ITEM_SELECT_ITEMS_INC_ONE_ITEM.getString(), Settings.GUI_ITEM_SELECT_ITEMS_INC_ONE_NAME.getString(), Settings.GUI_ITEM_SELECT_ITEMS_INC_ONE_LORE.getStringList(), Math.min(Settings.GUI_ITEM_SELECT_INC_ONE.getInt(), 1), new HashMap<String, Object>() {{
                put("%inc_one_amount%", Settings.GUI_ITEM_SELECT_INC_ONE.getInt());
            }}), e -> handleQuantityButtons(e, Settings.GUI_ITEM_SELECT_INC_ONE.getInt(), true));

            setButton(3, 6, ConfigurationItemHelper.build(Settings.GUI_ITEM_SELECT_ITEMS_INC_TWO_ITEM.getString(), Settings.GUI_ITEM_SELECT_ITEMS_INC_TWO_NAME.getString(), Settings.GUI_ITEM_SELECT_ITEMS_INC_TWO_LORE.getStringList(), Math.min(Settings.GUI_ITEM_SELECT_INC_TWO.getInt(), 1), new HashMap<String, Object>() {{
                put("%inc_two_amount%", Settings.GUI_ITEM_SELECT_INC_TWO.getInt());
            }}), e -> handleQuantityButtons(e, Settings.GUI_ITEM_SELECT_INC_TWO.getInt(), true));

            setButton(4, 6, ConfigurationItemHelper.build(Settings.GUI_ITEM_SELECT_ITEMS_INC_THREE_ITEM.getString(), Settings.GUI_ITEM_SELECT_ITEMS_INC_THREE_NAME.getString(), Settings.GUI_ITEM_SELECT_ITEMS_INC_THREE_LORE.getStringList(), Math.min(Settings.GUI_ITEM_SELECT_INC_THREE.getInt(), 1), new HashMap<String, Object>() {{
                put("%inc_three_amount%", Settings.GUI_ITEM_SELECT_INC_THREE.getInt());
            }}), e -> handleQuantityButtons(e, Settings.GUI_ITEM_SELECT_INC_THREE.getInt(), true));

            setButton(4, 4, new TItemBuilder(Objects.requireNonNull(Settings.GUI_ITEM_SELECT_ITEMS_SELL_BUY_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_ITEM_SELECT_ITEMS_SELL_BUY_NAME.getString()).setLore(Settings.GUI_ITEM_SELECT_ITEMS_SELL_BUY_LORE.getStringList()).toItemStack(), e -> {
                switch (e.clickType) {
                    case LEFT:
                        if (!Shops.getInstance().getEconomy().has(e.player, cartTotal)) {
                            Shops.getInstance().getLocale().getMessage("general.not_enough_money").sendPrefixedMessage(e.player);
                            return;
                        }
                        Shops.getInstance().getEconomy().withdrawPlayer(e.player, cartTotal);
                        Shops.getInstance().getLocale().getMessage("general.money_remove").processPlaceholder("value", cartTotal).sendPrefixedMessage(e.player);
                        for (int i = 0; i < this.quantity; i++) {
                            PlayerUtils.giveItem(e.player, deserializedItem);
                        }
                        break;
                    case RIGHT:
                        break;
                }
            });

            setItem(1, 4, deserializedItem);
            setItem(3, 4, ConfigurationItemHelper.build(Settings.GUI_SHOP_ITEM_SELECT_ITEMS_INFO_ITEM.getString(), Settings.GUI_SHOP_ITEM_SELECT_ITEMS_INFO_NAME.getString(), Settings.GUI_SHOP_ITEM_SELECT_ITEMS_INFO_LORE.getStringList(), new HashMap<String, Object>() {{
                put("%item_stack_quantity%", deserializedItem.getAmount());
                put("%item_quantity%", quantity);
                put("%item_price%", String.format("%,.2f", shopItem.getBuyPrice()));
                put("%item_tax%", preTax);
                put("%item_sub_total%", String.format("%,.2f", subTotal));
                put("%item_discounts%", String.format("%,.2f", discounts));
                put("%item_total%", String.format("%,.2f", cartTotal));
            }}));
        }
    }

    private void handleQuantityButtons(GuiClickEvent e, int amount, boolean add) {
        switch (e.clickType) {
            case LEFT:
                quantity = add ? quantity + amount : Math.max(1, quantity - amount);
                draw();
                break;
            case SHIFT_LEFT:
            case SHIFT_RIGHT:
                if (Settings.GUI_ITEM_SELECT_ALLOW_SHIFT_CLICK_STACK.getBoolean()) {
                    quantity = add ? quantity + 64 : Math.max(1, quantity - 64);
                    draw();
                }
                break;
        }
    }
}
