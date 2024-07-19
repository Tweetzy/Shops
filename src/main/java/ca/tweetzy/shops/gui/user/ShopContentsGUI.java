package ca.tweetzy.shops.gui.user;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.ChatUtil;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.flight.utils.input.TitleInput;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.cart.Cart;
import ca.tweetzy.shops.api.filter.FilterOrder;
import ca.tweetzy.shops.api.filter.FilterType;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.api.shop.ShopContent;
import ca.tweetzy.shops.api.shop.ShopContentDisplayType;
import ca.tweetzy.shops.gui.ShopsPagedGUI;
import ca.tweetzy.shops.impl.cart.CartItem;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class ShopContentsGUI extends ShopsPagedGUI<ShopContent> {

	private final Shop shop;
	private final Cart cart;
	private String search;
	private FilterOrder filterOrder;
	private FilterType filterType;

	public ShopContentsGUI(Gui parent, @NonNull Player player, @NonNull final Shop shop, final String search) {
		super(parent, player, shop.getDisplayName(), Math.max(2, shop.getShopOptions().getShopDisplay().getRows()), shop.getContent());
		this.shop = shop;
		this.cart = Shops.getCartManager().getOrAdd(player);
		this.filterType = FilterType.NAME;
		this.filterOrder = FilterOrder.ASCENDING;
		this.search = search;
		// apply default item
		final ItemStack bg = this.shop.getShopOptions().getShopDisplay().getBackgroundItem();

		setDefaultItem(bg.getType() == CompMaterial.AIR.parseMaterial() ? null :QuickItem.bg(bg));

		draw();
	}

	public ShopContentsGUI(Gui parent, @NonNull Player player, @NonNull final Shop shop) {
		this(parent, player, shop, null);
	}

	@Override
	protected void prePopulate() {
		if (this.search != null)
			this.items = shop.getContent().stream().filter(shopContent -> shopContent.isMatch(this.search)).collect(Collectors.toList());

		if (this.filterType == FilterType.NAME)
			this.items = this.items.stream().sorted(Comparator.comparing(ShopContent::getName)).collect(Collectors.toList());
		else if (this.filterType == FilterType.PRICE)
			this.items = this.items.stream().sorted(Comparator.comparing(ShopContent::getBuyPrice)).collect(Collectors.toList());
		else
			this.items = this.items.stream().sorted(Comparator.comparing(ShopContent::getMinimumPurchaseQty)).collect(Collectors.toList());
	}

	@Override
	protected void drawFixed() {
		applyBackExit();

		// decorations
		this.shop.getShopOptions().getShopDisplay().getDecoration().forEach((slot, item) -> setItem(slot, QuickItem.bg(item)));

		// search
		setButton(this.shop.getShopOptions().getShopDisplay().getSearchButtonSlot(), QuickItem
				.of(Settings.GUI_SHOP_CONTENT_ITEMS_SEARCH.getItemStack())
				.name(TranslationManager.string(this.player, Translations.GUI_SHOP_CONTENTS_ITEMS_SEARCH_NAME))
				.lore(TranslationManager.list(this.player, Translations.GUI_SHOP_CONTENTS_ITEMS_SEARCH_LORE))
				.make(), click -> {

			if (click.clickType == ClickType.LEFT)
				new TitleInput(Shops.getInstance(), click.player, TranslationManager.string(click.player, Translations.PROMPT_SEARCH_TITLE), TranslationManager.string(click.player, Translations.PROMPT_SEARCH_SUBTITLE)) {

					@Override
					public void onExit(Player player) {
						click.manager.showGUI(click.player, ShopContentsGUI.this);
					}

					@Override
					public boolean onResult(String string) {
						click.manager.showGUI(click.player, new ShopContentsGUI(ShopContentsGUI.this.parent, click.player, ShopContentsGUI.this.shop, string));
						return true;
					}
				};

			if (click.clickType == ClickType.RIGHT)
				click.manager.showGUI(click.player, new ShopContentsGUI(ShopContentsGUI.this.parent, click.player, ShopContentsGUI.this.shop, null));

		});

		// cart
		drawCart();
		// filter
		drawFilter();
	}

	private void drawFilter() {
		setButton(this.shop.getShopOptions().getShopDisplay().getFilterButtonSlot(), QuickItem
				.of(Settings.GUI_SHOP_CONTENT_ITEMS_FILTER.getItemStack())
				.name(TranslationManager.string(this.player, Translations.GUI_SHOP_CONTENTS_ITEMS_FILTER_NAME))
				.lore(TranslationManager.list(this.player, Translations.GUI_SHOP_CONTENTS_ITEMS_FILTER_LORE,
						"shop_filter_type", ChatUtil.capitalizeFully(this.filterType),
						"shop_filter_order", ChatUtil.capitalizeFully(this.filterOrder)
				)).make(), click -> {

			if (click.clickType == ClickType.LEFT)
				this.filterType = this.filterType.next();

			draw();
		});
	}

	private void drawCart() {
		setButton(this.shop.getShopOptions().getShopDisplay().getCartButtonSlot(), QuickItem
				.of(Settings.GUI_SHOP_CONTENT_ITEMS_CART.getItemStack())
				.name(TranslationManager.string(this.player, Translations.GUI_SHOP_CONTENTS_ITEMS_CART_NAME))
				.lore(TranslationManager.list(this.player, Translations.GUI_SHOP_CONTENTS_ITEMS_CART_LORE, "shopping_cart_item_count", this.cart.getItems().size()))
				.make(), click -> click.manager.showGUI(click.player, new ShopsCartGUI(this, click.player, this.cart)));
	}


	@Override
	protected ItemStack makeDisplayItem(ShopContent content) {
		return content.generateDisplayItem(ShopContentDisplayType.LIVE_SHOP);
	}

	@Override
	protected void onClick(ShopContent content, GuiClickEvent click) {
		if (click.clickType == ClickType.LEFT)
			click.manager.showGUI(click.player, new ShopCheckoutGUI(this, player, this.shop, new CartItem(content, content.getMinimumPurchaseQty())));

		if (click.clickType == ClickType.RIGHT) {
			this.cart.addItem(content);
			drawCart();
		}
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
