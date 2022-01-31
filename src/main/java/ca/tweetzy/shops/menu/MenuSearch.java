package ca.tweetzy.shops.menu;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.enums.ShopItemQuantityType;
import ca.tweetzy.shops.api.interfaces.shop.IShopItem;
import ca.tweetzy.shops.impl.Checkout;
import ca.tweetzy.shops.impl.SearchedShopItem;
import ca.tweetzy.shops.impl.SmartItem;
import ca.tweetzy.shops.menu.shopcontent.MenuShopContentList;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.tweety.conversation.TitleInput;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.model.Replacer;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 30 2022
 * Time Created: 11:11 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuSearch extends MenuPagged<SearchedShopItem> {

	private final Player player;
	private final String keyword;

	private final Button cartButton;
	private final Button searchButton;
	private final Button backButton;


	public MenuSearch(@NonNull final Player player, @NonNull final String keyword) {
		super(Shops.getShopManager().searchShopItems(player, keyword));
		setTitle(Localization.SearchMenu.TITLE.replace("{keyword}", keyword));
		this.player = player;
		this.keyword = keyword;

		this.backButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.BackButton.MATERIAL).get())
				.name(Localization.Menus.BackButton.NAME)
				.lore(Localization.Menus.BackButton.LORE), thePlayer -> new MenuMain(thePlayer).displayTo(thePlayer));

		this.cartButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.Search.CART_BUTTON_MATERIAL).get())
				.name(Localization.SearchMenu.CART_BUTTON_NAME)
				.lore(Localization.SearchMenu.CART_BUTTON_LORE), thePlayer -> new MenuCart(player).displayTo(thePlayer));

		this.searchButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.Search.SEARCH_BUTTON_MATERIAL).get())
				.name(Localization.SearchMenu.SEARCH_BUTTON_NAME)
				.lore(Localization.SearchMenu.SEARCH_BUTTON_LORE), thePlayer -> new TitleInput(player, Localization.Prompt.ITEM_SEARCH_TITLE, Localization.Prompt.ITEM_SEARCH_SUBTITLE) {

			@Override
			public boolean onResult(String string) {
				if (string == null || string.length() < 3)
					return false;

				new MenuSearch(player, string).displayTo(player);
				return true;
			}
		});
	}

	@Override
	protected ItemStack convertToItemStack(SearchedShopItem item) {
		final List<String> lore = new ArrayList<>(item.getShopItem().getQuantityType() == ShopItemQuantityType.LIMITED ? Localization.ShopContentMenu.ShopItemLoreFormat.LIMITED : Localization.ShopContentMenu.ShopItemLoreFormat.UNLIMITED);

		if (!item.getShopItem().canBeBought())
			lore.remove("{buy}");

		if (!item.getShopItem().canBeSold())
			lore.remove("{sell}");

		if (item.getShopItem().getDescription().isEmpty()) {
			lore.remove("{item_description}");
		} else {
			int descIndex = lore.indexOf("{item_description}");
			if (descIndex != -1) {
				lore.remove("{item_description}");
				lore.addAll(descIndex, item.getShopItem().getDescription());
			}
		}

		return ItemCreator
				.of(item.getShopItem().getItem())
				.lore(Replacer.replaceArray(lore,
						"stock_status", item.getShopItem().getCurrentStock() <= 0 && item.getShopItem().getQuantityType() == ShopItemQuantityType.LIMITED ? Localization.ShopContentMenu.ShopItemLores.OUT_OF_STOCK : Localization.ShopContentMenu.ShopItemLores.IN_STOCK.replace("{shop_item_stock}", String.valueOf(item.getShopItem().getStock())),
						"buy", replaceBuySell(Localization.ShopContentMenu.ShopItemLores.BUY, item.getShopItem()),
						"sell", replaceBuySell(Localization.ShopContentMenu.ShopItemLores.SELL, item.getShopItem()),
						"view", Localization.ShopContentMenu.ShopItemLores.VIEW
				))
				.make();
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if ((Settings.Menus.BackButton.SLOT == -1 && slot == getSize() - 9) || (Settings.Menus.BackButton.SLOT == -2 && slot == getSize() - 1) || slot == Settings.Menus.BackButton.SLOT)
			return this.backButton.getItem();

		if ((Settings.Menus.Main.SEARCH_BUTTON_SLOT == -1 && slot == getSize() - 9) || (Settings.Menus.Main.SEARCH_BUTTON_SLOT == -2 && slot == getSize() - 1) || slot == Settings.Menus.Main.SEARCH_BUTTON_SLOT)
			return this.searchButton.getItem();

		if (slot == getSize() - 9 + Settings.Menus.Main.CART_BUTTON_SLOT)
			return this.cartButton.getItem();

		return super.getItemAt(slot);
	}

	@Override
	protected void onPageClick(Player player, SearchedShopItem item, ClickType click) {
		if (item.getShopItem().getQuantityType() == ShopItemQuantityType.LIMITED && item.getShopItem().getCurrentStock() <= 0) return;
		new MenuItemPurchase(item.getShop(), item.getShopItem(), new Checkout(item.getShop(), item.getShopItem(), 1)).displayTo(player);
	}

	@Override
	protected ItemStack backgroundItem() {
		return ItemCreator.of(new SmartItem(Settings.Menus.Main.BACKGROUND_ITEM).get()).name(" ").make();
	}


	private String replaceBuySell(@NonNull String string, @NonNull final IShopItem shopItem) {
		return string
				.replace("{shop_item_qty}", String.valueOf(shopItem.getPurchaseQuantity()))
				.replace("{shop_item_buy_cost}", String.valueOf(shopItem.getBuyPrice()))
				.replace("{shop_item_sell_cost}", String.valueOf(shopItem.getSellPrice()));
	}
}
