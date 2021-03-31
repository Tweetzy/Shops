package ca.tweetzy.shops.guis;

import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.core.utils.items.TItemBuilder;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.helpers.ConfigurationItemHelper;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.CartItem;
import com.google.common.util.concurrent.AtomicDouble;
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
 * Time Created: 5:14 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class GUICart extends Gui {

    final Player player;
    final List<CartItem> shopItems;

    public GUICart(Player player) {
        this.player = player;
        this.shopItems = Shops.getInstance().getPlayerCart().containsKey(this.player.getUniqueId()) ? Shops.getInstance().getPlayerCart().get(this.player.getUniqueId()) : new ArrayList<>();
        setTitle(TextUtils.formatText(Settings.GUI_SHOP_CART_TITLE.getString()));
        setDefaultItem(Settings.GUI_SHOP_CART_BG_ITEM.getMaterial().parseItem());
        setAcceptsItems(false);
        setRows(6);
        draw();
    }

    private void draw() {
        reset();

        pages = (int) Math.max(1, Math.ceil(this.shopItems.size() / (double) 21));
        setItems(IntStream.range(27, 54).toArray(), getDefaultItem());
        setPrevPage(4, 2, new TItemBuilder(Objects.requireNonNull(Settings.GUI_BACK_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_BACK_BTN_NAME.getString()).setLore(Settings.GUI_BACK_BTN_LORE.getStringList()).toItemStack());
        setButton(5, 0, new TItemBuilder(Objects.requireNonNull(Settings.GUI_CLOSE_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_CLOSE_BTN_NAME.getString()).setLore(Settings.GUI_CLOSE_BTN_LORE.getStringList()).toItemStack(), e -> e.manager.showGUI(e.player, new GUIShops()));
        setNextPage(4, 6, new TItemBuilder(Objects.requireNonNull(Settings.GUI_NEXT_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_NEXT_BTN_NAME.getString()).setLore(Settings.GUI_NEXT_BTN_LORE.getStringList()).toItemStack());
        setOnPage(e -> draw());

        // do price calculations ahead of time
        double cartSubTotal = this.shopItems.stream().mapToDouble(item -> item.getQuantity() * item.getBuyPrice()).sum();
        double tax = Settings.USE_TAX.getBoolean() ? Settings.TAX_AMOUNT.getDouble() : 0.0D;
        AtomicDouble discounts = new AtomicDouble(0.0);

        HashMap<String, Double> individualDiscounts = new HashMap<>();
        this.shopItems.stream().filter(cartItem -> Shops.getInstance().getShopManager().getShop(cartItem.getShopId()).isUseBuyDiscount()).forEach(cartItem -> individualDiscounts.put(cartItem.getShopId(), Shops.getInstance().getShopManager().getShop(cartItem.getShopId()).getBuyDiscount()));
        individualDiscounts.keySet().forEach(shops -> {
            discounts.getAndAdd(this.shopItems.stream().filter(cartItem -> cartItem.getShopId().equalsIgnoreCase(shops)).mapToDouble(cartItem -> (cartItem.getBuyPrice() * cartItem.getQuantity()) * (individualDiscounts.get(shops) / 100)).sum());
        });


        if (Settings.GUI_SHOP_CART_FILL_BG.getBoolean()) {
            IntStream.range(0, getRows() * 9).forEach(i -> setItem(i, getDefaultItem()));
        }

        setButton(4, 3, ConfigurationItemHelper.build(Settings.GUI_SHOP_CART_ITEMS_CLEAR_ITEM.getString(), Settings.GUI_SHOP_CART_ITEMS_CLEAR_NAME.getString(), Settings.GUI_SHOP_CART_ITEMS_CLEAR_LORE.getStringList(), null), e -> {
            Shops.getInstance().getPlayerCart().remove(this.player.getUniqueId());
            e.manager.showGUI(this.player, new GUICart(this.player));
        });

        setButton(4, 5, ConfigurationItemHelper.build(Settings.GUI_SHOP_CART_ITEMS_CONFIRM_ITEM.getString(), Settings.GUI_SHOP_CART_ITEMS_CONFIRM_NAME.getString(), Settings.GUI_SHOP_CART_ITEMS_CONFIRM_LORE.getStringList(), null), e -> {


        });

        int slot = 0;
        List<CartItem> data = this.shopItems.stream().skip((page - 1) * 21L).limit(21).collect(Collectors.toList());
        for (CartItem item : data) {
            ItemStack parsed = ShopAPI.getInstance().deserializeItem(item.getItem());
            setButton(slot++, parsed, ClickType.RIGHT, e -> {
                Shops.getInstance().getPlayerCart().get(this.player.getUniqueId()).remove(item);
                e.manager.showGUI(this.player, new GUICart(this.player));
            });
        }
    }
}
