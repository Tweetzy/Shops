package ca.tweetzy.shops.menu;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopCurrency;
import ca.tweetzy.shops.impl.Cart;
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

import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 30 2022
 * Time Created: 6:21 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuCart extends MenuPagged<Checkout> {

	private final Player thePlayer;
	private final Cart cart;

	private final Button sellButton;
	private final Button buyButton;

	private final Button backButton;

	public MenuCart(@NonNull final Player thePlayer) {
		super(null, IntStream.rangeClosed(0, 26).boxed().collect(Collectors.toList()), Shops.getCartManager().getCart(thePlayer.getUniqueId()).getCartItems());
		setTitle(Localization.Cart.TITLE);
		setSize(9 * 6);
		this.thePlayer = thePlayer;
		this.cart = Shops.getCartManager().getCart(thePlayer.getUniqueId());

		this.backButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.BackButton.MATERIAL).get())
				.name(Localization.Menus.BackButton.NAME)
				.lore(Localization.Menus.BackButton.LORE), user -> new MenuMain(user).displayTo(thePlayer));

		this.sellButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.Cart.SELL_BUTTON_MATERIAL).get())
				.name(Localization.Cart.SELL_BUTTON_NAME)
				.lore(Localization.Cart.SELL_BUTTON_LORE), player -> {

			final Iterator<Checkout> checkoutIterator = this.cart.getCartItems().iterator();
			while (checkoutIterator.hasNext()) {
				final Checkout checkout = checkoutIterator.next();
				if (checkout.executeSell(player, true)) {
					checkoutIterator.remove();
				}
			}

			newInstance().displayTo(player);
		});

		this.buyButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.Cart.BUY_BUTTON_MATERIAL).get())
				.name(Localization.Cart.BUY_BUTTON_NAME)
				.lore(Localization.Cart.BUY_BUTTON_LORE), player -> {

			if (player.getInventory().firstEmpty() == -1) {
				tell(Localization.Error.INVENTORY_FULL);
				return;
			}

			final Iterator<Checkout> checkoutIterator = this.cart.getCartItems().iterator();
			while (checkoutIterator.hasNext()) {
				final Checkout checkout = checkoutIterator.next();
				if (checkout.executeBuy(player, true)) {
					checkoutIterator.remove();
				}
			}

			newInstance().displayTo(player);
		});
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if ((Settings.Menus.BackButton.SLOT == -1 && slot == getSize() - 9) || (Settings.Menus.BackButton.SLOT == -2 && slot == getSize() - 1) || slot == Settings.Menus.BackButton.SLOT)
			return this.backButton.getItem();

		if (Settings.Menus.Cart.SELL_BUTTON_SLOT == slot)
			return this.sellButton.getItem();

		if (Settings.Menus.Cart.BUY_BUTTON_SLOT == slot)
			return this.buyButton.getItem();

		if (Settings.Menus.Cart.INFO_BUTTON_SLOT == slot) {
			final ShopCurrency currency = this.cart.getCartItems().isEmpty() ? Shops.getCurrencyManager().getCurrency("Vault") : this.cart.getCartItems().get(0).getShopItem().getCurrency();

			return ItemCreator
					.of(new SmartItem(Settings.Menus.Cart.INFO_BUTTON_MATERIAL).get())
					.name(Localization.Cart.INFO_BUTTON_NAME)
					.lore(Replacer.replaceArray(Localization.Cart.INFO_BUTTON_LORE,
							"item_currency", currency.getName().equalsIgnoreCase("Vault") ? Localization.CURRENCY_SYMBOL : currency.getName(),
							"purchase_cost", String.format(Settings.NUMBER_FORMAT, this.cart.getCartItems().stream().mapToDouble(Checkout::calculateBuyPrice).sum()),
							"sells_for", String.format(Settings.NUMBER_FORMAT, this.cart.getCartItems().stream().mapToDouble(Checkout::calculateSellPrice).sum())
					)).make();
		}

		return super.getItemAt(slot);
	}

	@Override
	protected ItemStack convertToItemStack(Checkout item) {
		final ItemCreator creator = ItemCreator.of(item.getShopItem().getItem());
		Replacer.replaceArray(Localization.Cart.ITEM_LORE,
				"item_quantity", item.getPurchaseQty(),
				"item_sell_price", String.format(Settings.NUMBER_FORMAT, item.calculateSellPrice()),
				"item_buy_price", String.format(Settings.NUMBER_FORMAT, item.calculateBuyPrice()),
				"stack_size", item.getShopItem().getPurchaseQuantity()
		).forEach(creator::lore);

		return creator.make();
	}

	@Override
	protected void onPageClick(Player player, Checkout item, ClickType click) {
		this.cart.getCartItems().remove(item);
		newInstance().displayTo(player);
	}

	@Override
	public Menu newInstance() {
		return new MenuCart(this.thePlayer);
	}
}
