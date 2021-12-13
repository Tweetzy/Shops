package ca.tweetzy.shops.guis;

import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.core.utils.items.TItemBuilder;
import ca.tweetzy.core.utils.nms.NBTEditor;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.custom.CustomGUIItem;
import ca.tweetzy.shops.custom.CustomGUIItemHolder;
import ca.tweetzy.shops.helpers.ConfigurationItemHelper;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.Shop;
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
 * Date Created: March 25 2021
 * Time Created: 12:39 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class GUIShops extends Gui {

	final Player player;
	List<Shop> shops;
	List<CustomGUIItemHolder> customItems;
	int clicksToEdit = 0;
	boolean editing = false;

	public GUIShops(Player player) {
		this.player = player;
		setTitle(TextUtils.formatText(Settings.GUI_SHOPS_TITLE.getString()));
		setDefaultItem(Settings.GUI_SHOPS_BG_ITEM.getMaterial().parseItem());
		setAcceptsItems(false);
		setAllowDrops(false);
		handleEvents();
		draw();
	}

	private void adjustSize() {
		this.shops = Shops.getInstance().getShopManager().getShops();
		this.customItems = Shops.getInstance().getShopManager().getCustomGuiItems();

		if (Settings.GUI_SHOPS_DYNAMIC.getBoolean()) {
			// TODO loop this
			if (this.shops.size() >= 1 && this.shops.size() <= 9) setRows(1);
			if (this.shops.size() >= 10 && this.shops.size() <= 18) setRows(2);
			if (this.shops.size() >= 19 && this.shops.size() <= 27) setRows(3);
			if (this.shops.size() >= 28 && this.shops.size() <= 36) setRows(4);
			if (this.shops.size() >= 37 && this.shops.size() <= 45) setRows(5);
			if (this.shops.size() >= 46) setRows(6);
		} else {
			setRows(Math.max(1, Settings.GUI_SHOPS_SIZE.getInt()));
		}

		int toDivide = (int) Math.ceil(this.shops.size() / (double) (9 * getRows()));
		pages = (int) Math.max(1, Math.ceil(this.shops.size() / getRows() == 6 ? 45 : toDivide));

		if (getRows() == 6) {
			setPrevPage(5, 3, new TItemBuilder(Objects.requireNonNull(Settings.GUI_BACK_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_BACK_BTN_NAME.getString()).setLore(Settings.GUI_BACK_BTN_LORE.getStringList()).toItemStack());
			setNextPage(5, 5, new TItemBuilder(Objects.requireNonNull(Settings.GUI_NEXT_BTN_ITEM.getMaterial().parseMaterial())).setName(Settings.GUI_NEXT_BTN_NAME.getString()).setLore(Settings.GUI_NEXT_BTN_LORE.getStringList()).toItemStack());
			setOnPage(e -> draw());
		}
	}

	private void draw() {
		reset();
		adjustSize();

		if (Settings.GUI_SHOPS_FILL_BG.getBoolean()) {
			IntStream.range(0, getRows() * 9).forEach(i -> setItem(i, getDefaultItem()));
		}

		if (!Settings.GUI_SHOPS_AUTO_ARRANGE.getBoolean()) {
			if (this.customItems.stream().noneMatch(holders -> holders.getGuiName().equalsIgnoreCase("main"))) {
				this.customItems.add(new CustomGUIItemHolder("main"));
			} else {
				this.customItems.stream().filter(holders -> holders.getGuiName().equalsIgnoreCase("main")).findFirst().get().getItems().forEach(guiItem -> setItem(guiItem.getSlot(), ShopAPI.getInstance().deserializeItem(guiItem.getItem())));
			}
		}

		int slot = 0;
		long perPage = getRows() < 6 ? (getRows() * 9L) : 45L;
		List<Shop> data = this.shops.stream().skip((page - 1) * perPage).limit(perPage).collect(Collectors.toList());
		for (Shop shop : data) {
			if (shop.isPublic()) {
				if (shop.isRequiresPermissionToSee() && this.player.hasPermission(shop.getSeePermission())) {
					setButton(Settings.GUI_SHOPS_DYNAMIC.getBoolean() ? slot++ : Settings.GUI_SHOPS_AUTO_ARRANGE.getBoolean() ? slot++ : editing ? slot++ : shop.getSlot(), getShopIcon(shop), e -> {
						if (!this.editing)
							e.manager.showGUI(e.player, new GUIShopContents(e.player, shop, false, false));
					});
				} else {
					setButton(Settings.GUI_SHOPS_DYNAMIC.getBoolean() ? slot++ : Settings.GUI_SHOPS_AUTO_ARRANGE.getBoolean() ? slot++ : editing ? slot++ : shop.getSlot(), getShopIcon(shop), e -> {
						if (!this.editing)
							e.manager.showGUI(e.player, new GUIShopContents(e.player, shop, false, false));
					});
				}
			}
		}
	}

	private void handleEvents() {
		if (!Settings.GUI_SHOPS_AUTO_ARRANGE.getBoolean()) {
			setPrivateDefaultAction(e -> {
				if (e.clickType == ClickType.SHIFT_RIGHT && e.player.hasPermission("shops.admin")) clicksToEdit++;
				if (clicksToEdit >= 3 && e.player.hasPermission("shops.admin")) {
					clicksToEdit = 0;
					editing = true;
					setUnlockedRange(0, (9 * getRows()) - 1);
					setAcceptsItems(true);
					draw();
					Shops.getInstance().getLocale().newMessage(TextUtils.formatText("&aYou're now editing this inventory!")).sendPrefixedMessage(e.player);
				}
			});
		}

		setOnClose(close -> {
			if (editing) {
				List<Shop> toUpdate = new ArrayList<>();

				for (int i = 0; i < inventory.getSize(); i++) {
					ItemStack slotItem = getItem(i);
					if (slotItem != null) {
						if (NBTEditor.contains(slotItem, "shops:shop_id")) {
							String shopId = NBTEditor.getString(slotItem, "shops:shop_id");
							Shop shop = Shops.getInstance().getShopManager().getShop(shopId);
							shop.setPage(page);
							shop.setSlot(i);
							toUpdate.add(shop);
						} else {
							this.customItems.stream().filter(holders -> holders.getGuiName().equalsIgnoreCase("main")).findFirst().orElse(null).getItems().add(new CustomGUIItem(i, slotItem));
						}
					}
				}

				if (Settings.DATABASE_USE.getBoolean()) {
					Shops.getInstance().getDataManager().updateShops(toUpdate);
					Shops.getInstance().getDataManager().updateCustomGuiItems(this.customItems.stream().filter(holders -> holders.getGuiName().equalsIgnoreCase("main")).findFirst().get());
					Shops.getInstance().getLocale().getMessage("shop.saved_inventory_edit_for_list").sendPrefixedMessage(close.player);
				} else {
					toUpdate.forEach(ShopAPI.getInstance()::createShop);
					ShopAPI.getInstance().saveCustomGuiItems(this.customItems.stream().filter(holders -> holders.getGuiName().equalsIgnoreCase("main")).findFirst().get());
					Shops.getInstance().getLocale().getMessage("shop.saved_inventory_edit_for_list").sendPrefixedMessage(close.player);
					Shops.getInstance().getShopManager().loadShops(true, Settings.DATABASE_USE.getBoolean());
				}

				close.manager.showGUI(close.player, new GUIShops(close.player));
			}
		});
	}

	private ItemStack getShopIcon(Shop shop) {
		ItemStack stack = ConfigurationItemHelper.build(ShopAPI.getInstance().deserializeItem(shop.getDisplayIcon()), Settings.GUI_SHOPS_ITEM_NAME.getString(), Settings.GUI_SHOPS_ITEM_LORE.getStringList(), 1, true, new HashMap<String, Object>() {{
			put("%shop_display_name%", shop.getDisplayName());
			put("%shop_description%", shop.getDescription());
			put("%shop_is_sell_only%", shop.isSellOnly());
			put("%shop_is_buy_only%", shop.isBuyOnly());
			put("%shop_item_count%", shop.getShopItems().size());
		}});
		stack = NBTEditor.set(stack, shop.getId(), "shops:shop_id");
		return stack;
	}
}
