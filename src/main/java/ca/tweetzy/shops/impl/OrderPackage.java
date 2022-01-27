package ca.tweetzy.shops.impl;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.interfaces.IOrderPackage;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import lombok.AllArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 27 2022
 * Time Created: 1:42 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public final class OrderPackage implements IOrderPackage {

	private final ItemStack itemStack;

	@Override
	public ItemStack getPackageItem() {
		return this.itemStack;
	}

	@Override
	public ItemStack build() {
		ItemCreator creator = ItemCreator
				.of(new SmartItem(Settings.ORDER_PACKAGE_MATERIAL).get())
				.name(Localization.OrderPackage.NAME)
				.lore(Localization.OrderPackage.LORE)
				.tag("shops-packaged-item", Arrays.toString(Shops.getShopManager().serializeItemStack(this.itemStack)))
				.hideTags(true);

		return creator.make();
	}
}
