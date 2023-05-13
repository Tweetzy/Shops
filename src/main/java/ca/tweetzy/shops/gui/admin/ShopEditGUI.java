package ca.tweetzy.shops.gui.admin;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.api.shop.ShopContent;
import ca.tweetzy.shops.gui.ShopsPagedGUI;
import ca.tweetzy.shops.impl.shop.ItemShopContent;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class ShopEditGUI extends ShopsPagedGUI<ShopContent> {

	private final Shop shop;

	public ShopEditGUI(@NonNull Player player, @NonNull final Shop shop) {
		super(new AdminShopListGUI(new ShopsAdminMainGUI(player), player), player, TranslationManager.string(Translations.GUI_SHOP_EDIT_TITLE, "shop_id", shop.getId()), 6, shop.getContent());
		this.shop = shop;
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(ShopContent shopContent) {
		if (shopContent instanceof final ItemShopContent itemShopContent)
			return QuickItem.of(itemShopContent.getItem()).make();

		return null;
	}

	@Override
	protected void drawAdditional() {
		setButton(5, 4, QuickItem
				.of(CompMaterial.LIME_DYE)
				.name(TranslationManager.string(Translations.GUI_SHOP_EDIT_ITEMS_CREATE_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_EDIT_ITEMS_CREATE_LORE,
						"left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK),
						"right_click", TranslationManager.string(this.player, Translations.MOUSE_RIGHT_CLICK)
				))
				.make(), click -> {

		});

		setButton(5, 7, QuickItem
				.of(CompMaterial.REPEATER)
				.name(TranslationManager.string(Translations.GUI_SHOP_EDIT_ITEMS_SETTINGS_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_EDIT_ITEMS_SETTINGS_LORE,
						"left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK),
						"right_click", TranslationManager.string(this.player, Translations.MOUSE_RIGHT_CLICK)
				))
				.make(), click -> click.manager.showGUI(click.player, new ShopSettingsGUI(click.player, this.shop)));
	}

	@Override
	protected void onClick(ShopContent shopContent, GuiClickEvent click) {

	}
}
