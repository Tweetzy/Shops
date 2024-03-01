package ca.tweetzy.shops.gui.admin;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.gui.helper.InventoryBorder;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.flight.utils.input.TitleInput;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.gui.ShopsPagedGUI;
import ca.tweetzy.shops.impl.shop.ServerShop;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;
import java.util.List;

public final class AdminShopListGUI extends ShopsPagedGUI<Shop> {

	public AdminShopListGUI(Gui parent, @NonNull Player player) {
		super(parent, player, TranslationManager.string(Translations.GUI_ADMIN_SHOP_LIST_TITLE), 6, Shops.getShopManager().getValues());
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(Shop shop) {
		return QuickItem
				.of(shop.getShopOptions().getDisplayIcon())
				.name(shop.getDisplayName())
				.lore(shop.getDescription())
				.make();
	}

	@Override
	protected void drawFixed() {
		applyBackExit();

		setButton(5, 4, QuickItem
				.of(CompMaterial.LIME_DYE)
				.name(TranslationManager.string(Translations.GUI_ADMIN_SHOP_LIST_ITEMS_CREATE_NAME))
				.lore(TranslationManager.list(Translations.GUI_ADMIN_SHOP_LIST_ITEMS_CREATE_LORE))
				.make(), click -> new TitleInput(Shops.getInstance(), click.player, TranslationManager.string(Translations.PROMPT_SERVER_SHOP_ID_TITLE), TranslationManager.string(Translations.PROMPT_SERVER_SHOP_ID_SUBTITLE)) {

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(click.player, AdminShopListGUI.this);
			}

			@Override
			public boolean onResult(String string) {
				string = ChatColor.stripColor(string.toLowerCase());

				// a shop with that id already exists
				if (Shops.getShopManager().isShopIdTaken(string)) {
					Common.tell(click.player, TranslationManager.string(Translations.SHOP_ID_TAKEN));
					return false;
				}

				// create the shop
				Shops.getShopManager().create(new ServerShop(string), created -> {
					if (created)
						click.manager.showGUI(click.player, new AdminShopListGUI(new ShopsAdminMainGUI(click.player), click.player));
				});

				return true;
			}
		});
	}

	@Override
	protected void onClick(Shop shop, GuiClickEvent click) {
		click.manager.showGUI(click.player, new ShopEditGUI(player, shop));
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
