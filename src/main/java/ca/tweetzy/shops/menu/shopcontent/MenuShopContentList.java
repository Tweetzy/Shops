package ca.tweetzy.shops.menu.shopcontent;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.enums.ShopItemQuantityType;
import ca.tweetzy.shops.api.interfaces.shop.IShopItem;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.SmartItem;
import ca.tweetzy.shops.menu.main.MenuMain;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.tweety.conversation.TitleInput;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.model.Replacer;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 31 2021
 * Time Created: 6:10 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */

public final class MenuShopContentList extends MenuPagged<IShopItem> {

	private final Shop shop;
	private final Button searchButton;
	private final Button backButton;

	public MenuShopContentList(@NonNull final Shop shop, final String keyword) {
		super(null, shop.getDisplay().getShopItemSlots().getSource(), keyword != null ? Shops.getShopManager().filterShopItems(shop, keyword) : shop.getShopItems());
		this.shop = shop;
		setTitle(keyword != null ? this.shop.getDisplayName() + "&f: &7" + keyword : this.shop.getDisplayName());
		setSize(9 * 6);
		setInactivePageButton(ItemCreator.of(shop.getDisplay().getBackgroundItem()).name(" ").clearLore().make());
		setAsyncFill();

		this.searchButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.ShopContent.SEARCH_BUTTON_MATERIAL).get())
				.name(Localization.ShopContentMenu.SEARCH_BUTTON_NAME)
				.lore(Localization.ShopContentMenu.SEARCH_BUTTON_LORE), player -> new TitleInput(player, Localization.Prompt.ITEM_SEARCH_TITLE, Localization.Prompt.ITEM_SEARCH_SUBTITLE) {

			@Override
			public boolean onResult(String string) {
				if (string == null || string.length() < 3)
					return false;

				new MenuShopContentList(MenuShopContentList.this.shop, string).displayTo(player);
				return true;
			}
		});

		this.backButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.BackButton.MATERIAL).get())
				.name(Localization.Menus.BackButton.NAME)
				.lore(Localization.Menus.BackButton.LORE), player -> new MenuMain().displayTo(player));
	}

	public MenuShopContentList(@NonNull final Shop shop) {
		this(shop, null);
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (this.shop.getDisplay().getDecorationItems().containsKey(slot))
			return ItemCreator.of(this.shop.getDisplay().getDecorationItems().get(slot)).name(" ").make();

		if ((Settings.Menus.BackButton.SLOT == -1 && slot == getSize() - 9) || (Settings.Menus.BackButton.SLOT == -2 && slot == getSize() - 1) || slot == Settings.Menus.BackButton.SLOT)
			return this.backButton.getItem();

		if ((Settings.Menus.ShopContent.SEARCH_BUTTON_SLOT == -1 && slot == getSize() - 9) || (Settings.Menus.ShopContent.SEARCH_BUTTON_SLOT == -2 && slot == getSize() - 1) || slot == Settings.Menus.ShopContent.SEARCH_BUTTON_SLOT)
			return this.searchButton.getItem();

		return super.getItemAt(slot);
	}

	@Override
	protected ItemStack convertToItemStack(IShopItem item) {
		final List<String> lore = item.getQuantityType() == ShopItemQuantityType.LIMITED ? Localization.ShopContentMenu.ShopItemLoreFormat.LIMITED : Localization.ShopContentMenu.ShopItemLoreFormat.UNLIMITED;

		if (item.getDescription().isEmpty())
			lore.remove("{item_description}");
		else {
			int descIndex = lore.indexOf("{item_description}");
			if (descIndex != -1) {
				lore.remove("{item_description}");
				lore.addAll(descIndex, item.getDescription());
			}
		}

		if (!item.canBeBought())
			lore.remove("{buy}");

		if (!item.canBeSold())
			lore.remove("{sell}");

		return ItemCreator
				.of(item.getItem())
				.lore(Replacer.replaceArray(lore,
						"stock_status", item.getStock() <= 0 && item.getQuantityType() == ShopItemQuantityType.LIMITED ? Localization.ShopContentMenu.ShopItemLores.OUT_OF_STOCK : Localization.ShopContentMenu.ShopItemLores.IN_STOCK.replace("{shop_item_stock}", String.valueOf(item.getStock())),
						"buy", replaceBuySell(Localization.ShopContentMenu.ShopItemLores.BUY, item),
						"sell", replaceBuySell(Localization.ShopContentMenu.ShopItemLores.SELL, item),
						"view", Localization.ShopContentMenu.ShopItemLores.VIEW
				))
				.make();
	}

	@Override
	protected void onPageClick(Player player, IShopItem item, ClickType click) {

	}

	@Override
	protected ItemStack backgroundItem() {
		return ItemCreator.of(CompMaterial.BLACK_STAINED_GLASS_PANE).name(" ").make();
	}

	private String replaceBuySell(@NonNull String string, @NonNull final IShopItem shopItem) {
		return string
				.replace("{shop_item_qty}", String.valueOf(shopItem.getPurchaseQuantity()))
				.replace("{shop_item_buy_cost}", String.valueOf(shopItem.getBuyPrice()))
				.replace("{shop_item_sell_cost}", String.valueOf(shopItem.getSellPrice()));
	}
}
