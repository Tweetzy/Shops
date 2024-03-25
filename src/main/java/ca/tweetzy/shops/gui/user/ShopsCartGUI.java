package ca.tweetzy.shops.gui.user;

import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.gui.helper.InventoryBorder;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.api.cart.Cart;
import ca.tweetzy.shops.api.cart.CartContent;
import ca.tweetzy.shops.api.shop.ShopContentDisplayType;
import ca.tweetzy.shops.gui.ShopsPagedGUI;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class ShopsCartGUI extends ShopsPagedGUI<CartContent> {

	private final Cart cart;

	public ShopsCartGUI(Gui parent, @NonNull Player player, @NonNull final Cart cart) {
		super(parent, player, TranslationManager.string(player, Translations.GUI_CART_TITLE), 6, cart.getItems());
		this.cart = cart;
		draw();
	}

	@Override
	protected void drawFixed() {

		// checkout button
		setButton(getRows() - 1, 4, QuickItem
				.of(Settings.GUI_CART_ITEMS_CHECKOUT.getItemStack())
				.name(TranslationManager.string(player, Translations.GUI_CART_ITEMS_PURCHASE_NAME))
				.lore(TranslationManager.list(player, Translations.GUI_CART_ITEMS_PURCHASE_LORE,
						"left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK),
						"right_click", TranslationManager.string(this.player, Translations.MOUSE_RIGHT_CLICK)
				)).make(), click -> {

			if (click.clickType == ClickType.LEFT) {
				this.cart.executePurchase(click.player);
				click.manager.showGUI(click.player, new ShopsCartGUI(this.parent, click.player, this.cart));
			}

			if (click.clickType == ClickType.RIGHT) {
				this.cart.executeSell(click.player);
				click.manager.showGUI(click.player, new ShopsCartGUI(this.parent, click.player, this.cart));
			}
		});


		// CLEAR button
		setButton(getRows() - 1, 8, QuickItem
				.of(Settings.GUI_CART_ITEMS_CLEAR.getItemStack())
				.name(TranslationManager.string(player, Translations.GUI_CART_ITEMS_CLEAR_NAME))
				.lore(TranslationManager.list(player, Translations.GUI_CART_ITEMS_CLEAR_LORE))
				.make(), click -> {

			this.cart.clear();
			click.manager.showGUI(click.player, new ShopsCartGUI(this.parent, click.player, this.cart));
		});

		applyBackExit();
	}

	@Override
	protected ItemStack makeDisplayItem(CartContent cartContent) {
		return cartContent.getItem().generateDisplayItem(ShopContentDisplayType.CART, cartContent.getQuantity());
	}

	@Override
	protected void onClick(CartContent cartContent, GuiClickEvent click) {
		if (click.clickType == ClickType.LEFT)
			click.manager.showGUI(click.player, new ShopCheckoutGUI(this, click.player, cartContent.getItem().getOwningShop(), cartContent));

		if (click.clickType == ClickType.RIGHT) {
			this.cart.removeItem(cartContent);
			click.manager.showGUI(click.player, new ShopsCartGUI(this.parent, click.player, this.cart));
		}
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(6);
	}
}
