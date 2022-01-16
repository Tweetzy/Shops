package ca.tweetzy.shops.menu.main;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.enums.ShopLayout;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.menu.shopcontent.MenuShopContentList;
import ca.tweetzy.shops.model.TextureResolver;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 31 2021
 * Time Created: 8:22 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuDynamicMain extends MenuPagged<Shop> {

	public MenuDynamicMain() {
		super(Shops.getShopManager().getShops());
		setTitle(Localization.Menus.Main.TITLE);
	}

	@Override
	protected ItemStack convertToItemStack(Shop shop) {
		return ItemCreator
				.of(shop.getIcon().get())
				.name(shop.getDisplayName())
				.lore(shop.getDescription())
				.make();
	}

	@Override
	protected ItemStack backgroundItem() {
		return ItemCreator.of(TextureResolver.resolve(Settings.Menus.Main.BACKGROUND_ITEM)).name(" ").make();
	}

	@Override
	protected void onPageClick(Player player, Shop shop, ClickType click) {
		if (shop.getDisplay().getShopLayout() == ShopLayout.AUTOMATIC)
			new MenuShopContentList(shop).displayTo(player);
	}
}
