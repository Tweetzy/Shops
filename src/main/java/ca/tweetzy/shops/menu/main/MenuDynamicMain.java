package ca.tweetzy.shops.menu.main;

import ca.tweetzy.shops.api.ShopsAPI;
import ca.tweetzy.shops.api.enums.ShopLayout;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.menu.shopcontent.MenuDynamicShop;
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
		super(ShopsAPI.getShops());
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
	protected void onPageClick(Player player, Shop shop, ClickType click) {
		if (shop.getDisplay().getShopLayout() == ShopLayout.AUTOMATIC)
			new MenuDynamicShop(shop).displayTo(player);
	}
}
