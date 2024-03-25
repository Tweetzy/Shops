package ca.tweetzy.shops.gui.admin;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.gui.helper.InventoryBorder;
import ca.tweetzy.flight.gui.template.MaterialPickerGUI;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.api.SynchronizeResult;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.api.shop.ShopContent;
import ca.tweetzy.shops.api.shop.ShopContentDisplayType;
import ca.tweetzy.shops.api.shop.ShopContentType;
import ca.tweetzy.shops.gui.ShopsPagedGUI;
import ca.tweetzy.shops.gui.admin.content.ShopAddContentCmdGUI;
import ca.tweetzy.shops.gui.admin.content.ShopAddContentItemGUI;
import ca.tweetzy.shops.gui.admin.content.ShopSelectContentTypeGUI;
import ca.tweetzy.shops.impl.shop.CommandShopContent;
import ca.tweetzy.shops.impl.shop.ItemShopContent;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class ShopEditGUI extends ShopsPagedGUI<ShopContent> {

	private final Shop shop;

	public ShopEditGUI(@NonNull Player player, @NonNull final Shop shop) {
		super(new AdminShopListGUI(new ShopsAdminMainGUI(player), player), player, TranslationManager.string(Translations.GUI_SHOP_EDIT_TITLE, "shop_id", shop.getId()), 6, shop.getContent());
		this.shop = shop;
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(ShopContent shopContent) {
		return shopContent.generateDisplayItem(ShopContentDisplayType.SHOP_EDIT);

	}

	@Override
	protected void drawFixed() {
		applyBackExit();

		setButton(5, 4, QuickItem
				.of(CompMaterial.LIME_DYE)
				.name(TranslationManager.string(Translations.GUI_SHOP_EDIT_ITEMS_CREATE_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_EDIT_ITEMS_CREATE_LORE,
						"left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK),
						"right_click", TranslationManager.string(this.player, Translations.MOUSE_RIGHT_CLICK)
				))
				.make(), click -> {

			if (click.clickType == ClickType.LEFT)
				click.manager.showGUI(click.player, new ShopSelectContentTypeGUI(click.player, this.shop, selectedType -> {
					if (selectedType == ShopContentType.ITEM)
						click.manager.showGUI(click.player, new ShopAddContentItemGUI(click.player, this.shop, ItemShopContent.blank(this.shop.getId()), false));
					else
						click.manager.showGUI(click.player, new ShopAddContentCmdGUI(click.player, this.shop, CommandShopContent.blank(this.shop.getId()), false));
				}));

			// quick selector
			if (click.clickType == ClickType.RIGHT)
				click.manager.showGUI(click.player, new MaterialPickerGUI(this, null, "", (event, selected) -> {
					click.manager.showGUI(click.player, new ShopAddContentItemGUI(click.player, this.shop, ItemShopContent.blank(this.shop.getId(), selected), false));
				}));

		});

		setButton(5, 7, QuickItem
				.of(CompMaterial.REPEATER)
				.name(TranslationManager.string(Translations.GUI_SHOP_EDIT_ITEMS_SETTINGS_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_EDIT_ITEMS_SETTINGS_LORE,
						"left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK),
						"right_click", TranslationManager.string(this.player, Translations.MOUSE_RIGHT_CLICK)
				))
				.make(), click -> click.manager.showGUI(click.player, new ShopSettingsGUI(click.player, this.shop)));


		setButton(5, 2, QuickItem
				.of(CompMaterial.LAVA_BUCKET)
				.name(TranslationManager.string(Translations.GUI_SHOP_EDIT_ITEMS_DELETE_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_EDIT_ITEMS_DELETE_LORE))
				.make(), click -> {

			this.shop.unStore(result -> {
				if (result == SynchronizeResult.FAILURE) return;
				click.manager.showGUI(click.player, new AdminShopListGUI(new ShopsAdminMainGUI(click.player), click.player));
			});
		});
	}

	@Override
	protected void onClick(ShopContent shopContent, GuiClickEvent click) {
		if (click.clickType == ClickType.LEFT) {
			if (shopContent instanceof final ItemShopContent itemShopContent)
				click.manager.showGUI(click.player, new ShopAddContentItemGUI(click.player, this.shop, itemShopContent, true));
			if (shopContent instanceof final CommandShopContent commandShopContent)
				click.manager.showGUI(click.player, new ShopAddContentCmdGUI(click.player, this.shop, commandShopContent, true));
		}


		if (click.clickType == ClickType.RIGHT) {
			shopContent.unStore(result -> {
				if (result == SynchronizeResult.FAILURE) return;
				click.manager.showGUI(click.player, new ShopEditGUI(click.player, this.shop));
			});
		}
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
