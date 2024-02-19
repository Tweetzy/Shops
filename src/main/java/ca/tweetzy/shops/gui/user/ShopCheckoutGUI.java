package ca.tweetzy.shops.gui.user;

import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.api.shop.ShopContent;
import ca.tweetzy.shops.api.shop.ShopContentDisplayType;
import ca.tweetzy.shops.gui.ShopsBaseGUI;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;

public final class ShopCheckoutGUI extends ShopsBaseGUI {

	private final Shop shop;
	private final ShopContent shopContent;

	public ShopCheckoutGUI(Gui parent, @NonNull Player player, @NonNull final Shop shop, @NonNull ShopContent shopContent) {
		super(parent, player, TranslationManager.string(player, Translations.GUI_CHECKOUT_TITLE));
		this.shop = shop;
		this.shopContent = shopContent;
		draw();
	}

	@Override
	protected void draw() {

		setItem(1,4, this.shopContent.generateDisplayItem(ShopContentDisplayType.LIVE_SHOP));

		setButton(getRows() - 1, 4, QuickItem
				.of(Settings.GUI_CHECKOUT_ITEMS_CHECKOUT.getItemStack())
				.name(TranslationManager.string(this.player, Translations.GUI_CHECKOUT_ITEMS_CREATE_NAME))
				.lore(TranslationManager.list(this.player, Translations.GUI_CHECKOUT_ITEMS_CREATE_LORE))
				.make(), CLICK -> {

		});

		applyBackExit();
	}
}
