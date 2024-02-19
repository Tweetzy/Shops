package ca.tweetzy.shops.gui.user;

import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.flight.utils.input.TitleInput;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.api.shop.ShopContent;
import ca.tweetzy.shops.api.shop.ShopContentDisplayType;
import ca.tweetzy.shops.gui.ShopsPagedGUI;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class ShopContentsGUI extends ShopsPagedGUI<ShopContent> {

	private final Shop shop;

	public ShopContentsGUI(Gui parent, @NonNull Player player, @NonNull final Shop shop) {
		super(parent, player, shop.getDisplayName(), Math.max(2, shop.getShopOptions().getShopDisplay().getRows()), shop.getContent());
		this.shop = shop;
		draw();
	}

	@Override
	protected void drawFixed() {
		// decorations
		this.shop.getShopOptions().getShopDisplay().getDecoration().forEach(this::setItem);

		// search
		setButton(this.shop.getShopOptions().getShopDisplay().getSearchButtonSlot(), QuickItem
				.of(Settings.GUI_SHOP_CONTENT_ITEMS_SEARCH.getItemStack())
				.name(TranslationManager.string(this.player, Translations.GUI_SHOP_CONTENTS_ITEMS_SEARCH_NAME))
				.lore(TranslationManager.list(this.player, Translations.GUI_SHOP_CONTENTS_ITEMS_SEARCH_LORE))
				.make(), click -> new TitleInput(Shops.getInstance(), click.player, TranslationManager.string(click.player, Translations.PROMPT_SEARCH_TITLE), TranslationManager.string(click.player, Translations.PROMPT_SEARCH_SUBTITLE)) {

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(click.player, ShopContentsGUI.this);
			}

			@Override
			public boolean onResult(String string) {
//				click.manager.showGUI(click.player, new MarketSearchGUI(MarketCategoryViewGUI.this, click.player, MarketCategoryViewGUI.this.category, string));
				return true;
			}
		});

		// cart

		// filter
		setButton(this.shop.getShopOptions().getShopDisplay().getFilterButtonSlot(), QuickItem
				.of(Settings.GUI_SHOP_CONTENT_ITEMS_FILTER.getItemStack())
				.name(TranslationManager.string(this.player, Translations.GUI_SHOP_CONTENTS_ITEMS_FILTER_NAME))
				.lore(TranslationManager.list(this.player, Translations.GUI_SHOP_CONTENTS_ITEMS_FILTER_LORE))
				.make(), click -> {
		});
	}

	@Override
	protected ItemStack makeDisplayItem(ShopContent content) {
		return content.generateDisplayItem(ShopContentDisplayType.LIVE_SHOP);
	}

	@Override
	protected void onClick(ShopContent content, GuiClickEvent click) {
		if (click.clickType == ClickType.LEFT)
			click.manager.showGUI(click.player, new ShopCheckoutGUI(this, player, this.shop, content));
	}

	@Override
	protected List<Integer> fillSlots() {
		return this.shop.getShopOptions().getShopDisplay().getFillSlots();
	}

	@Override
	protected int getPreviousButtonSlot() {
		return this.shop.getShopOptions().getShopDisplay().getPrevPageButtonSlot();
	}

	@Override
	protected int getNextButtonSlot() {
		return this.shop.getShopOptions().getShopDisplay().getNextPageButtonSlot();
	}

	@Override
	protected int getBackExitButtonSlot() {
		return this.shop.getShopOptions().getShopDisplay().getExitButtonSlot();
	}
}
