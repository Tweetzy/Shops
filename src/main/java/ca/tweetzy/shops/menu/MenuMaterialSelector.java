package ca.tweetzy.shops.menu;

import ca.tweetzy.shops.api.enums.MaterialSelectMode;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.SmartItem;
import ca.tweetzy.shops.menu.settings.MenuShopDisplaySettings;
import ca.tweetzy.shops.menu.settings.MenuShopEdit;
import ca.tweetzy.shops.model.InventorySafeMaterials;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.ItemUtil;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 22 2021
 * Time Created: 11:50 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuMaterialSelector extends MenuPagged<CompMaterial> {

	private final Shop shop;
	private final MaterialSelectMode selectMode;

	public MenuMaterialSelector(@NonNull final Shop shop, @NonNull final MaterialSelectMode selectMode) {
		super(InventorySafeMaterials.get());
		setTitle("&d&lSelect Icon");
		this.shop = shop;
		this.selectMode = selectMode;
	}

	@Override
	protected ItemStack convertToItemStack(CompMaterial compMaterial) {
		return ItemCreator.of(compMaterial).name("&d&l" + ItemUtil.bountifyCapitalized(compMaterial)).lore("&eClick &7to select this material").make();
	}

	@Override
	protected void onPageClick(Player player, CompMaterial compMaterial, ClickType clickType) {
		if (this.selectMode == MaterialSelectMode.SHOP_ICON)
			this.shop.setIcon(new SmartItem(compMaterial.name()));
		else
			this.shop.getDisplay().setBackgroundItem(compMaterial);

		ShopsData.getInstance().save();

		if (this.selectMode == MaterialSelectMode.SHOP_ICON)
			new MenuShopEdit(this.shop).displayTo(player);
		else
			new MenuShopDisplaySettings(this.shop).displayTo(player);
	}

	@Override
	public Menu newInstance() {
		return new MenuMaterialSelector(this.shop, this.selectMode);
	}
}
