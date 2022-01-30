package ca.tweetzy.shops.menu;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.impl.Checkout;
import ca.tweetzy.shops.impl.SmartItem;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.model.Replacer;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 30 2022
 * Time Created: 6:21 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuCart extends MenuPagged<Checkout> {

	private final Player player;

	private final Button backButton;


	public MenuCart(@NonNull final Player player) {
		super(null, IntStream.rangeClosed(0, 26).boxed().collect(Collectors.toList()), Shops.getCartManager().getCart(player.getUniqueId()).getCartItems());
		setTitle(Localization.Cart.TITLE);
		setSize(9 * 6);
		this.player = player;

		this.backButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.BackButton.MATERIAL).get())
				.name(Localization.Menus.BackButton.NAME)
				.lore(Localization.Menus.BackButton.LORE), user -> new MenuMain(user).displayTo(player));
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if ((Settings.Menus.BackButton.SLOT == -1 && slot == getSize() - 9) || (Settings.Menus.BackButton.SLOT == -2 && slot == getSize() - 1) || slot == Settings.Menus.BackButton.SLOT)
			return this.backButton.getItem();

		return super.getItemAt(slot);
	}

	@Override
	protected ItemStack convertToItemStack(Checkout item) {
		final ItemCreator creator = ItemCreator.of(item.getShopItem().getItem());
		Replacer.replaceArray(Localization.Cart.ITEM_LORE,
				"item_quantity", item.getPurchaseQty(),
				"item_sell_price", String.format(Settings.NUMBER_FORMAT, item.calculateSellPrice()),
				"item_buy_price", String.format(Settings.NUMBER_FORMAT, item.calculateBuyPrice())
		).forEach(creator::lore);

		return creator.make();
	}

	@Override
	protected void onPageClick(Player player, Checkout item, ClickType click) {
		Shops.getCartManager().getCart(player.getUniqueId()).getCartItems().remove(item);
		newInstance().displayTo(player);
	}

	@Override
	public Menu newInstance() {
		return new MenuCart(this.player);
	}
}
