package ca.tweetzy.shops.gui.admin.content;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.api.shop.ShopContentType;
import ca.tweetzy.shops.gui.ShopsBaseGUI;
import ca.tweetzy.shops.gui.admin.ShopEditGUI;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public final class ShopSelectContentTypeGUI extends ShopsBaseGUI {

	private final Consumer<ShopContentType> contentTypeConsumer;

	public ShopSelectContentTypeGUI(@NonNull Player player, @NonNull final Shop shop, @NonNull final Consumer<ShopContentType> contentTypeConsumer) {
		super(new ShopEditGUI(player, shop), player, TranslationManager.string(Translations.GUI_SHOP_SELECT_CONTENT_TYPE_TITLE), 4);
		this.contentTypeConsumer = contentTypeConsumer;
		draw();
	}

	@Override
	protected void draw() {
		applyBackExit();

		setButton(1, 2, QuickItem
				.of(CompMaterial.DIAMOND_SWORD)
				.hideTags(true)
				.name(TranslationManager.string(Translations.GUI_SHOP_SELECT_CONTENT_TYPE_ITEMS_ITEM_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_SELECT_CONTENT_TYPE_ITEMS_ITEM_LORE))
				.make(), click -> contentTypeConsumer.accept(ShopContentType.ITEM));

		setButton(1, 6, QuickItem
				.of(CompMaterial.COMMAND_BLOCK)
				.name(TranslationManager.string(Translations.GUI_SHOP_SELECT_CONTENT_TYPE_ITEMS_CMD_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_SELECT_CONTENT_TYPE_ITEMS_CMD_LORE))
				.make(), click -> contentTypeConsumer.accept(ShopContentType.COMMAND));
	}
}
