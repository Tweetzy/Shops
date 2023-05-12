package ca.tweetzy.shops.menu.settings.display;

import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.menu.model.MenuClickLocation;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 30 2021
 * Time Created: 10:56 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuShopDecorationEdit extends Menu {

	private final Shop shop;

	public MenuShopDecorationEdit(@NonNull final Shop shop) {
		this.shop = shop;
		setTitle("&e" + shop.getId() + " &8> &eDecoration");
		setSize(9 * 6);
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (this.shop.getDisplay().getShopItemSlots().contains(slot))
			return ItemCreator.of(CompMaterial.LIME_STAINED_GLASS_PANE).name("&eFill Slot").lore("&7This slot is already marked", "&7as an item fill slot", "", "&eRight Click &7to save and exit").make();

		if (this.shop.getDisplay().getDecorationItems().containsKey(slot))
			return ItemCreator.of(this.shop.getDisplay().getDecorationItems().get(slot)).name("&eDecoration Item").make();

		return NO_ITEM;
	}

	@Override
	protected void onMenuClick(Player player, int slot, InventoryAction action, ClickType click, ItemStack cursor, ItemStack clicked, boolean cancelled) {
		if (click == ClickType.RIGHT && this.shop.getDisplay().getShopItemSlots().contains(slot)) {
			this.shop.getDisplay().getDecorationItems().clear();
			final List<Integer> slots = IntStream.rangeClosed(0, 53).boxed().collect(Collectors.toList());
			slots.removeIf(ss -> this.shop.getDisplay().getShopItemSlots().contains(ss));

			slots.forEach(i -> {
				final ItemStack deco = this.getInventory().getItem(i);
				if (deco != null && !this.shop.getDisplay().getDecorationItems().containsKey(i))
					this.shop.getDisplay().getDecorationItems().put(i, CompMaterial.fromItem(deco));
			});

			ShopsData.getInstance().saveAll();
			new MenuShopDisplaySettings(this.shop).displayTo(player);
		}
	}

	@Override
	protected boolean isActionAllowed(MenuClickLocation location, int slot, @Nullable ItemStack clicked, @Nullable ItemStack cursor) {
		return !this.shop.getDisplay().getShopItemSlots().contains(slot) || location == MenuClickLocation.PLAYER_INVENTORY;
	}

	@Override
	public Menu newInstance() {
		return new MenuShopDecorationEdit(this.shop);
	}

	@Override
	protected boolean allowShiftClick() {
		return true;
	}
}
