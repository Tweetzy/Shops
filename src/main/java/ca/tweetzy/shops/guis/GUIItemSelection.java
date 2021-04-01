package ca.tweetzy.shops.guis;

import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.gui.events.GuiClickEvent;
import ca.tweetzy.core.utils.PlayerUtils;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.core.utils.nms.NBTEditor;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.custom.CustomGUIItem;
import ca.tweetzy.shops.custom.CustomGUIItemHolder;
import ca.tweetzy.shops.helpers.ConfigurationItemHelper;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.CartItem;
import ca.tweetzy.shops.shop.Shop;
import ca.tweetzy.shops.shop.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    final Shop shop;
    final ItemStack deserializedItem;

    int quantity = 1;
    double cartTotal = 0;

    int clicksToEdit = 0;
    boolean editing = false;

    public GUIItemSelection(ShopItem shopItem) {
        this.shopItem = shopItem;
        this.shop = Shops.getInstance().getShopManager().getShop(this.shopItem.getShopId());
        this.deserializedItem = ShopAPI.getInstance().deserializeItem(this.shopItem.getItem());
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
        handleEdit();

        if (this.customItems.stream().noneMatch(holders -> holders.getGuiName().equalsIgnoreCase("itemselection"))) {
            this.customItems.add(new CustomGUIItemHolder("itemselection"));
        }

        if (editing) {
            drawItems();
            return;
        }

        if (Settings.GUI_ITEM_SELECT_FILL_BG.getBoolean()) {
            IntStream.range(0, getRows() * 9).forEach(i -> setItem(i, getDefaultItem()));
        }

        if (!Settings.GUI_ITEM_SELECT_USE_DEFAULT_SLOTS.getBoolean() && this.customItems.stream().filter(holders -> holders.getGuiName().equalsIgnoreCase("itemselection")).findFirst().get().getItems().size() != 0) {
            this.customItems.stream().filter(holders -> holders.getGuiName().equalsIgnoreCase("itemselection")).findFirst().get().getItems().forEach(customGUIItem -> {
                ItemStack stack = ShopAPI.getInstance().deserializeItem(customGUIItem.getItem());
                if (!NBTEditor.contains(stack, "shops:edit:button")) {
                    setItem(customGUIItem.getSlot(), ShopAPI.getInstance().deserializeItem(customGUIItem.getItem()));
                } else {
                    switch (NBTEditor.getString(stack, "shops:edit:button")) {
                        case "item_info":
                            setItem(customGUIItem.getSlot(), infoItem());
                            break;
                        case "select_item":
                            setItem(customGUIItem.getSlot(), itemToSelect());
                            break;
                        default:
                            setButton(customGUIItem.getSlot(), ShopAPI.getInstance().deserializeItem(customGUIItem.getItem()), this::handleCustomLayoutActions);
                            break;
                    }
                }
            });
        } else {
            drawItems();
        }
    }

    private ItemStack infoItem() {
        double subTotal = this.shopItem.getBuyPrice() * this.quantity;
        double preTax = Settings.USE_TAX.getBoolean() ? Settings.TAX_AMOUNT.getDouble() : 0.0D;
        double discounts = this.shop.isUseBuyDiscount() ? subTotal * (this.shop.getBuyDiscount() / 100) : 0.0D;
        double totalTax = (subTotal - discounts) * preTax / 100;
        this.cartTotal = (subTotal - discounts) + totalTax;

        return ConfigurationItemHelper.build(Settings.GUI_SHOP_ITEM_SELECT_ITEMS_INFO_ITEM.getString(), Settings.GUI_SHOP_ITEM_SELECT_ITEMS_INFO_NAME.getString(), Settings.GUI_SHOP_ITEM_SELECT_ITEMS_INFO_LORE.getStringList(), 1, new HashMap<String, Object>() {{
            put("%item_stack_quantity%", deserializedItem.getAmount());
            put("%item_quantity%", quantity);
            put("%item_price%", String.format("%,.2f", shopItem.getBuyPrice()));
            put("%item_tax%", preTax);
            put("%item_sub_total%", String.format("%,.2f", subTotal));
            put("%item_discounts%", String.format("%,.2f", discounts));
            put("%item_total%", String.format("%,.2f", cartTotal));
        }}, "shops:edit:button;item_info");
    }

    private ItemStack itemToSelect() {
        ItemStack stack = this.deserializedItem.clone();
        stack = NBTEditor.set(stack, "select_item", "shops:edit:button");
        return stack;
    }

    private void drawItems() {
        setButton(5, 0, ConfigurationItemHelper.build(Settings.GUI_CLOSE_BTN_ITEM.getString(), Settings.GUI_CLOSE_BTN_NAME.getString(), Settings.GUI_CLOSE_BTN_LORE.getStringList(), 1, null, "shops:edit:button;close"), this::handleCustomLayoutActions);
        setButton(5, 8, ConfigurationItemHelper.build(Settings.GUI_ITEM_SELECT_ITEMS_ADD_TO_CART_ITEM.getString(), Settings.GUI_ITEM_SELECT_ITEMS_ADD_TO_CART_NAME.getString(), Settings.GUI_ITEM_SELECT_ITEMS_ADD_TO_CART_LORE.getStringList(), 1, null, "shops:edit:button;add_to_cart"), this::handleCustomLayoutActions);

        setButton(2, 2, ConfigurationItemHelper.build(Settings.GUI_ITEM_SELECT_ITEMS_DECR_ONE_ITEM.getString(), Settings.GUI_ITEM_SELECT_ITEMS_DECR_ONE_NAME.getString(), Settings.GUI_ITEM_SELECT_ITEMS_DECR_ONE_LORE.getStringList(), Math.min(Settings.GUI_ITEM_SELECT_DECR_ONE.getInt(), 1), new HashMap<String, Object>() {{
            put("%decr_one_amount%", Settings.GUI_ITEM_SELECT_DECR_ONE.getInt());
        }}, "shops:edit:button;decr_one"), this::handleCustomLayoutActions);

        setButton(3, 2, ConfigurationItemHelper.build(Settings.GUI_ITEM_SELECT_ITEMS_DECR_TWO_ITEM.getString(), Settings.GUI_ITEM_SELECT_ITEMS_DECR_TWO_NAME.getString(), Settings.GUI_ITEM_SELECT_ITEMS_DECR_TWO_LORE.getStringList(), Math.min(Settings.GUI_ITEM_SELECT_DECR_TWO.getInt(), 1), new HashMap<String, Object>() {{
            put("%decr_two_amount%", Settings.GUI_ITEM_SELECT_DECR_TWO.getInt());
        }}, "shops:edit:button;decr_two"), this::handleCustomLayoutActions);

        setButton(4, 2, ConfigurationItemHelper.build(Settings.GUI_ITEM_SELECT_ITEMS_DECR_THREE_ITEM.getString(), Settings.GUI_ITEM_SELECT_ITEMS_DECR_THREE_NAME.getString(), Settings.GUI_ITEM_SELECT_ITEMS_DECR_THREE_LORE.getStringList(), Math.min(Settings.GUI_ITEM_SELECT_DECR_THREE.getInt(), 1), new HashMap<String, Object>() {{
            put("%decr_three_amount%", Settings.GUI_ITEM_SELECT_DECR_THREE.getInt());
        }}, "shops:edit:button;decr_three"), this::handleCustomLayoutActions);

        setButton(2, 6, ConfigurationItemHelper.build(Settings.GUI_ITEM_SELECT_ITEMS_INC_ONE_ITEM.getString(), Settings.GUI_ITEM_SELECT_ITEMS_INC_ONE_NAME.getString(), Settings.GUI_ITEM_SELECT_ITEMS_INC_ONE_LORE.getStringList(), Math.min(Settings.GUI_ITEM_SELECT_INC_ONE.getInt(), 1), new HashMap<String, Object>() {{
            put("%inc_one_amount%", Settings.GUI_ITEM_SELECT_INC_ONE.getInt());
        }}, "shops:edit:button;inc_one"), this::handleCustomLayoutActions);

        setButton(3, 6, ConfigurationItemHelper.build(Settings.GUI_ITEM_SELECT_ITEMS_INC_TWO_ITEM.getString(), Settings.GUI_ITEM_SELECT_ITEMS_INC_TWO_NAME.getString(), Settings.GUI_ITEM_SELECT_ITEMS_INC_TWO_LORE.getStringList(), Math.min(Settings.GUI_ITEM_SELECT_INC_TWO.getInt(), 1), new HashMap<String, Object>() {{
            put("%inc_two_amount%", Settings.GUI_ITEM_SELECT_INC_TWO.getInt());
        }}, "shops:edit:button;inc_two"), this::handleCustomLayoutActions);

        setButton(4, 6, ConfigurationItemHelper.build(Settings.GUI_ITEM_SELECT_ITEMS_INC_THREE_ITEM.getString(), Settings.GUI_ITEM_SELECT_ITEMS_INC_THREE_NAME.getString(), Settings.GUI_ITEM_SELECT_ITEMS_INC_THREE_LORE.getStringList(), Math.min(Settings.GUI_ITEM_SELECT_INC_THREE.getInt(), 1), new HashMap<String, Object>() {{
            put("%inc_three_amount%", Settings.GUI_ITEM_SELECT_INC_THREE.getInt());
        }}, "shops:edit:button;inc_three"), this::handleCustomLayoutActions);

        setButton(4, 4, ConfigurationItemHelper.build(Settings.GUI_ITEM_SELECT_ITEMS_SELL_BUY_ITEM.getString(), Settings.GUI_ITEM_SELECT_ITEMS_SELL_BUY_NAME.getString(), Settings.GUI_ITEM_SELECT_ITEMS_SELL_BUY_LORE.getStringList(), 1, null, "shops:edit:button;sell_buy"), this::handleCustomLayoutActions);

        setItem(1, 4, itemToSelect());
        setItem(3, 4, infoItem());
    }

    private void handleCustomLayoutActions(GuiClickEvent e) {
        ItemStack item = e.clickedItem;
        if (this.editing) return;
        if (!NBTEditor.contains(item, "shops:edit:button")) return;
        String button = NBTEditor.getString(item, "shops:edit:button");

        switch (button) {
            case "close":
                e.manager.showGUI(e.player, new GUIShopContents(e.player, this.shop));
                break;
            case "add_to_cart":
                if (Shops.getInstance().getPlayerCart().containsKey(e.player.getUniqueId())) {
                    Shops.getInstance().getPlayerCart().get(e.player.getUniqueId()).add(new CartItem(this.shopItem, this.quantity));
                } else {
                    Shops.getInstance().getPlayerCart().putIfAbsent(e.player.getUniqueId(), new ArrayList<CartItem>() {{
                        add(new CartItem(shopItem, quantity));
                    }});
                }
                break;
            case "decr_one":
                handleQuantityButtons(e, Settings.GUI_ITEM_SELECT_DECR_ONE.getInt(), false);
                break;
            case "decr_two":
                handleQuantityButtons(e, Settings.GUI_ITEM_SELECT_DECR_TWO.getInt(), false);
                break;
            case "decr_three":
                handleQuantityButtons(e, Settings.GUI_ITEM_SELECT_DECR_THREE.getInt(), false);
                break;
            case "inc_one":
                handleQuantityButtons(e, Settings.GUI_ITEM_SELECT_INC_ONE.getInt(), true);
                break;
            case "inc_two":
                handleQuantityButtons(e, Settings.GUI_ITEM_SELECT_INC_TWO.getInt(), true);
                break;
            case "inc_three":
                handleQuantityButtons(e, Settings.GUI_ITEM_SELECT_INC_THREE.getInt(), true);
                break;
            case "sell_buy":
                switch (e.clickType) {
                    case LEFT:
                        if (this.shop.isSellOnly()) return;
                        if (this.shopItem.isSellOnly()) return;

                        if (!Shops.getInstance().getEconomy().has(e.player, this.cartTotal)) {
                            Shops.getInstance().getLocale().getMessage("general.not_enough_money").sendPrefixedMessage(e.player);
                            return;
                        }

                        Shops.getInstance().getEconomy().withdrawPlayer(e.player, cartTotal);
                        Shops.getInstance().getLocale().getMessage("general.money_remove").processPlaceholder("value", String.format("%,.2f", this.cartTotal)).sendPrefixedMessage(e.player);
                        for (int i = 0; i < this.quantity; i++) {
                            PlayerUtils.giveItem(e.player, this.deserializedItem);
                        }
                        break;
                    case RIGHT:
                        if (this.shop.isBuyOnly()) return;
                        if (this.shopItem.isBuyOnly()) return;
                        break;
                }
                break;
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

    private void handleEdit() {
        setPrivateDefaultAction(e -> {
            if (e.clickType == ClickType.SHIFT_RIGHT && e.player.hasPermission("shops.admin")) this.clicksToEdit++;
            if (this.clicksToEdit >= 3 && e.player.hasPermission("shops.admin")) {
                this.clicksToEdit = 0;
                this.editing = true;
                setUnlockedRange(0, 9 * getRows());
                setAcceptsItems(true);
                draw();
                Shops.getInstance().getLocale().newMessage(TextUtils.formatText("&aYou're now editing this inventory!")).sendPrefixedMessage(e.player);
            }
        });

        setOnClose(close -> {
            if (this.editing) {
                for (int i = 0; i < inventory.getSize(); i++) {
                    ItemStack slotItem = getItem(i);
                    if (slotItem != null) {
                        this.customItems.stream().filter(holders -> holders.getGuiName().equalsIgnoreCase("itemselection")).findFirst().orElse(null).getItems().add(new CustomGUIItem(i, slotItem));
                    }
                }

                if (Settings.DATABASE_USE.getBoolean()) {
                    Shops.getInstance().getDataManager().updateCustomGuiItems(this.customItems.stream().filter(holders -> holders.getGuiName().equalsIgnoreCase("itemselection")).findFirst().get());
                    Shops.getInstance().getLocale().getMessage("shop.saved_inventory_edit_for_selection").sendPrefixedMessage(close.player);
                } else {
                    ShopAPI.getInstance().saveCustomGuiItems(this.customItems.stream().filter(holders -> holders.getGuiName().equalsIgnoreCase("itemselection")).findFirst().get());
                    Shops.getInstance().getLocale().getMessage("shop.saved_inventory_edit_for_selection").sendPrefixedMessage(close.player);
                    Shops.getInstance().getShopManager().loadShops(true, Settings.DATABASE_USE.getBoolean());
                }

                this.editing = false;
                close.manager.showGUI(close.player, new GUIItemSelection(this.shopItem));
            }
        });
    }

}
