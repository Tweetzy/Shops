package ca.tweetzy.shops.menu;

import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.SmartItem;
import ca.tweetzy.shops.model.InventorySafeMaterials;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.ItemUtil;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.model.InventoryDrawer;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.remain.CompMaterial;
import ca.tweetzy.tweety.slider.ColoredTextSlider;
import ca.tweetzy.tweety.slider.Slider;
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

	public MenuMaterialSelector(@NonNull final Shop shop) {
		super(InventorySafeMaterials.get());
		setTitle("&d&lSelect Icon");
		this.shop = shop;
	}

	@Override
	protected ItemStack convertToItemStack(CompMaterial compMaterial) {
		return ItemCreator.of(compMaterial).name("&d&l" + ItemUtil.bountifyCapitalized(compMaterial)).lore("&eClick &7to select this material").make();
	}

	@Override
	protected void onPageClick(Player player, CompMaterial compMaterial, ClickType clickType) {
		this.shop.setIcon(new SmartItem(compMaterial.name()));
		ShopsData.getInstance().save();
		new MenuShopEdit(this.shop).displayTo(player);
	}

	@Override
	public Menu newInstance() {
		return new MenuMaterialSelector(this.shop);
	}
}
