package ca.tweetzy.shops.menu;

import ca.tweetzy.shops.api.ShopsAPI;
import ca.tweetzy.shops.api.enums.ShopListType;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.menu.settings.MenuShopEdit;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 22 2021
 * Time Created: 9:27 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuShopList extends MenuPagged<Shop> {

	private final ShopListType shopListType;

	public MenuShopList(@NonNull final ShopListType shopListType) {
		super(ShopsAPI.getShops());
		this.shopListType = shopListType;
		setTitle("&eSelect Shop");
	}

	@Override
	protected ItemStack convertToItemStack(Shop shop) {
		return ItemCreator
				.of(shop.getIcon().get())
				.name(shop.getDisplayName())
				.lore(this.shopListType == ShopListType.EDIT ? "&eClick &7to edit shop" : "&7Press &e1 &7to delete shop")
				.make();
	}

	@Override
	protected void onPageClick(Player player, Shop shop, ClickType clickType) {
		if (this.shopListType == ShopListType.EDIT)
			new MenuShopEdit(shop).displayTo(player);

		if (this.shopListType == ShopListType.DELETE && clickType == ClickType.NUMBER_KEY) {
			ShopsAPI.deleteShop(shop.getId());
			tell(Localization.Success.SHOP_DELETED.replace("{shop_id}", shop.getId()));
			newInstance().displayTo(player);
		}
	}

	@Override
	public Menu newInstance() {
		return new MenuShopList(this.shopListType);
	}
}
