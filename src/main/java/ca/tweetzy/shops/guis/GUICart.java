package ca.tweetzy.shops.guis;

import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.hooks.EconomyManager;
import ca.tweetzy.core.utils.PlayerUtils;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.core.utils.items.TItemBuilder;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.api.events.ShopBuyEvent;
import ca.tweetzy.shops.helpers.ConfigurationItemHelper;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.CartItem;
import ca.tweetzy.shops.shop.Shop;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.Bukkit;
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
		setDefaultItem(Settings.GUI_SHOP_CART_BAR_ITEM.getMaterial().parseItem());
		setAcceptsItems(false);
		setRows(6);
		draw();
	}

	private void draw() {
		reset();

		pages = (int) Math.max(1, Math.ceil(this.shopItems.size() / (double) 27));
		setItems(IntStream.range(27, 54).toArray(), getDefaultItem());
		setPrevPage(4, 2, new TItemBuilder(Objects.requireNonNull(Settings.GUI_BACK_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_BACK_BTN_NAME.getString()).setLore(Settings.GUI_BACK_BTN_LORE.getStringList()).toItemStack());
		setButton(5, 0, new TItemBuilder(Objects.requireNonNull(Settings.GUI_CLOSE_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_CLOSE_BTN_NAME.getString()).setLore(Settings.GUI_CLOSE_BTN_LORE.getStringList()).toItemStack(), e -> e.manager.showGUI(e.player, new GUIShops(e.player)));
		setNextPage(4, 6, new TItemBuilder(Objects.requireNonNull(Settings.GUI_NEXT_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_NEXT_BTN_NAME.getString()).setLore(Settings.GUI_NEXT_BTN_LORE.getStringList()).toItemStack());
		setOnPage(e -> draw());

		// do price calculations ahead of time
		double cartSubTotal = this.shopItems.stream().mapToDouble(item -> item.getQuantity() * item.getBuyPrice()).sum();
		AtomicDouble discounts = new AtomicDouble(0.0);
		double preTax = Settings.USE_TAX.getBoolean() ? Settings.TAX_AMOUNT.getDouble() : 0.0D;
		double totalTax = cartSubTotal * (preTax / 100);

		HashMap<String, Double> individualDiscounts = new HashMap<>();
		this.shopItems.stream().filter(cartItem -> Shops.getInstance().getShopManager().getShop(cartItem.getShopId()).isUseBuyDiscount()).forEach(cartItem -> individualDiscounts.put(cartItem.getShopId(), Shops.getInstance().getShopManager().getShop(cartItem.getShopId()).getBuyDiscount()));
		individualDiscounts.keySet().forEach(shops -> discounts.getAndAdd(this.shopItems.stream().filter(cartItem -> cartItem.getShopId().equalsIgnoreCase(shops)).mapToDouble(cartItem -> (cartItem.getBuyPrice() * cartItem.getQuantity()) * (individualDiscounts.get(shops) / 100)).sum()));

		// calculate the total after adding up the discounts
		double cartTotal = (cartSubTotal - discounts.get()) + totalTax;

		if (Settings.GUI_SHOP_CART_FILL_BG.getBoolean()) {
			IntStream.range(0, getRows() * 9).forEach(i -> setItem(i, getDefaultItem()));
		}

		setButton(4, 3, ConfigurationItemHelper.build(Settings.GUI_SHOP_CART_ITEMS_CLEAR_ITEM.getString(), Settings.GUI_SHOP_CART_ITEMS_CLEAR_NAME.getString(), Settings.GUI_SHOP_CART_ITEMS_CLEAR_LORE.getStringList(), null), e -> {
			Shops.getInstance().getPlayerCart().remove(this.player.getUniqueId());
			e.manager.showGUI(this.player, new GUICart(this.player));
		});

		setItem(4, 4, ConfigurationItemHelper.build(Settings.GUI_SHOP_CART_ITEMS_INFO_ITEM.getString(), Settings.GUI_SHOP_CART_ITEMS_INFO_NAME.getString(), Settings.GUI_SHOP_CART_ITEMS_INFO_LORE.getStringList(), new HashMap<String, Object>() {{
			put("%shop_cart_item_count%", shopItems.size());
			put("%shop_cart_sub_total%", String.format("%,.2f", cartSubTotal));
			put("%shop_cart_tax%", preTax);
			put("%shop_cart_discounts%", String.format("%,.2f", discounts.get()));
			put("%shop_cart_total%", String.format("%,.2f", cartTotal));
		}}));

		setButton(4, 5, ConfigurationItemHelper.build(Settings.GUI_SHOP_CART_ITEMS_CONFIRM_ITEM.getString(), Settings.GUI_SHOP_CART_ITEMS_CONFIRM_NAME.getString(), Settings.GUI_SHOP_CART_ITEMS_CONFIRM_LORE.getStringList(), null), e -> {
			if (this.shopItems.size() == 0) return;

			if (e.clickType == ClickType.LEFT) {
				if (!EconomyManager.hasBalance(this.player, cartTotal)) {
					Shops.getInstance().getLocale().getMessage("general.not_enough_money").sendPrefixedMessage(this.player);
					return;
				}

				ShopBuyEvent shopBuyEvent = new ShopBuyEvent(this.player, this.shopItems);
				Bukkit.getServer().getPluginManager().callEvent(shopBuyEvent);
				if (shopBuyEvent.isCancelled()) return;

				EconomyManager.withdrawBalance(this.player, cartTotal);
				Shops.getInstance().getLocale().getMessage("general.money_remove").processPlaceholder("value", String.format("%,.2f", cartTotal)).sendPrefixedMessage(this.player);

				this.shopItems.forEach(cartItem -> {
					for (int i = 0; i < cartItem.getQuantity(); i++)
						PlayerUtils.giveItem(this.player, ShopAPI.getInstance().deserializeItem(cartItem.getItem()));
				});

				Shops.getInstance().getPlayerCart().remove(this.player.getUniqueId());

			} else {
				// handle cart sell
				boolean hasBuyOnlyItems = false;
				double sellTotal = 0.0D;

				for (CartItem cartItem : this.shopItems) {
					Shop shop = Shops.getInstance().getShopManager().getShop(cartItem.getShopId());
					if (shop == null) continue;
					if (cartItem.isBuyOnly()) {
						hasBuyOnlyItems = true;
						continue;
					}

					int quantity = cartItem.getQuantity();

					int itemCount = ShopAPI.getInstance().getItemCountInPlayerInventory(this.player, ShopAPI.getInstance().deserializeItem(cartItem.getItem()));
					if (itemCount == 0) return;
					if (itemCount < quantity) {
						quantity = itemCount;
					}

					double preTotalSell = cartItem.getSellPrice() * quantity;
					sellTotal += preTotalSell + (shop.isUseSellBonus() ? preTotalSell * (shop.getSellBonus() / 100) : 0D);
					ShopAPI.getInstance().removeSpecificItemQuantityFromPlayer(e.player, ShopAPI.getInstance().deserializeItem(cartItem.getItem()), quantity);
				}

				Shops.getInstance().getPlayerCart().remove(this.player.getUniqueId());
				EconomyManager.deposit(e.player, sellTotal);
				Shops.getInstance().getLocale().getMessage("general.money_add").processPlaceholder("value", String.format("%,.2f", sellTotal)).sendPrefixedMessage(e.player);
				if (hasBuyOnlyItems) Shops.getInstance().getLocale().getMessage("general.buy_only_excluded").sendPrefixedMessage(e.player);
			}

			e.gui.close();
		});

		int slot = 0;
		List<CartItem> data = this.shopItems.stream().skip((page - 1) * 27L).limit(27).collect(Collectors.toList());
		for (CartItem item : data) {
			ItemStack parsed = ShopAPI.getInstance().deserializeItem(item.getItem());
			setButton(slot++, new TItemBuilder(parsed).setLore(Settings.GUI_SHOP_CART_ITEM_LORE.getStringList().stream().map(line -> line.replace("%shop_item_price%", String.format("%,.2f", item.getBuyPrice())).replace("%shop_item_quantity%", String.valueOf(item.getQuantity()))).collect(Collectors.toList())).toItemStack(), ClickType.RIGHT, e -> {
				Shops.getInstance().getPlayerCart().get(this.player.getUniqueId()).remove(item);
				e.manager.showGUI(this.player, new GUICart(this.player));
			});
		}
	}
}
