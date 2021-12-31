package ca.tweetzy.shops.menu.settings;

import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 30 2021
 * Time Created: 10:36 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuShopFillEdit extends MenuPagged<Integer> {

	private final Shop shop;

	public MenuShopFillEdit(@NonNull final Shop shop) {
		super(null, IntStream.rangeClosed(0, 53).boxed().collect(Collectors.toList()), IntStream.rangeClosed(0, 53).boxed().collect(Collectors.toList()));
		setTitle("&e" + shop.getId() + " &8> &eFill Slots");
		this.shop = shop;
	}

	@Override
	protected ItemStack convertToItemStack(Integer slot) {
		return ItemCreator
				.of(this.shop.getDisplay().getShopItemSlots().contains(slot) ? CompMaterial.LIME_STAINED_GLASS_PANE : CompMaterial.WHITE_STAINED_GLASS_PANE)
				.name("&e" + slot)
				.lore("", "&7Current&f: " + (this.shop.getDisplay().getShopItemSlots().contains(slot) ? "&aFill" : "&cNo Fill"), "", "&eLeft Click &7to toggle fill state", "&eRight Click &7to save and exit")
				.make();
	}

	@Override
	protected void onPageClick(Player player, Integer slot, ClickType clickType) {
		if (clickType == ClickType.LEFT) {
			if (this.shop.getDisplay().getShopItemSlots().contains(slot)) {
				this.shop.getDisplay().getShopItemSlots().removeWeak(slot);
			} else {
				this.shop.getDisplay().getShopItemSlots().addIfNotExist(slot);
				this.shop.getDisplay().getDecorationItems().removeWeak(slot);
			}

			restartMenu();
		}

		if (clickType == ClickType.RIGHT) {
			ShopsData.getInstance().save();
			new MenuShopDisplaySettings(this.shop).displayTo(player);
		}
	}

	@Override
	public Menu newInstance() {
		return new MenuShopFillEdit(this.shop);
	}
}
