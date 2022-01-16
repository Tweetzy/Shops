package ca.tweetzy.shops.menu.shopcontent;

import ca.tweetzy.shops.api.interfaces.shop.IShopItem;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.remain.CompMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 31 2021
 * Time Created: 6:10 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */

public final class MenuShopList extends MenuPagged<IShopItem> {

	private final Shop shop;

	public MenuShopList(Shop shop) {
		super(null, shop.getDisplay().getShopItemSlots().getSource(), shop.getShopItems());
		this.shop = shop;
		setTitle(this.shop.getDisplayName());
		setSize(9 * 6);
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (this.shop.getDisplay().getDecorationItems().containsKey(slot))
			return ItemCreator.of(this.shop.getDisplay().getDecorationItems().get(slot)).name(" ").make();

		return super.getItemAt(slot);
	}

	@Override
	protected ItemStack convertToItemStack(IShopItem item) {
		return ItemCreator
				.of(item.getItem())

				.make();
	}

	@Override
	protected void onPageClick(Player player, IShopItem item, ClickType click) {

	}

	@Override
	protected ItemStack backgroundItem() {
		return ItemCreator.of(CompMaterial.BLACK_STAINED_GLASS_PANE).name(" ").make();
	}
}
