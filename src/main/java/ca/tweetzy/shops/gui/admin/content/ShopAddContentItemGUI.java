package ca.tweetzy.shops.gui.admin.content;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.gui.template.MaterialPickerGUI;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.MathUtil;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.flight.utils.input.TitleInput;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.gui.ShopsBaseGUI;
import ca.tweetzy.shops.gui.admin.ShopEditGUI;
import ca.tweetzy.shops.gui.selector.CurrencyPickerGUI;
import ca.tweetzy.shops.impl.shop.ItemShopContent;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public final class ShopAddContentItemGUI extends ShopsBaseGUI {

	private final Shop shop;
	private ItemShopContent itemContent;
	private final boolean isEditing;


	public ShopAddContentItemGUI(@NonNull Player player, @NonNull final Shop shop, @NonNull final ItemShopContent itemContent, final boolean isEditing) {
		super(new ShopEditGUI(player, shop), player, TranslationManager.string(isEditing ? Translations.GUI_SHOP_EDIT_CONTENT_TITLE : Translations.GUI_SHOP_ADD_CONTENT_TITLE), 6);
		this.shop = shop;
		this.itemContent = itemContent;
		this.isEditing = isEditing;
		setAcceptsItems(true);
		draw();
	}

	@Override
	protected void draw() {
		applyBackExit();
		drawItemContent();
	}

	private void drawItemContent() {

		// sell item
		drawSellItem();

		// price buttons
		drawPriceButtons();

		// toggle buttons
		drawPriceToggleButtons();

		// min buy qty button
		drawMinPurchaseQtyButton();

		// currency button
		drawCurrencyButton();

		// add button
		drawAddButton();
	}

	private void drawCurrencyButton() {
		setButton(3, 5, QuickItem
				.of(CompMaterial.GOLD_INGOT)
				.name(Translations.string(this.player, Translations.GUI_SHOP_ADD_CONTENT_ITEMS_CURRENCY_NAME))
				.lore(Translations.list(this.player, Translations.GUI_SHOP_ADD_CONTENT_ITEMS_CURRENCY_LORE,
						"left_click", Translations.string(this.player, Translations.MOUSE_LEFT_CLICK),
						"currency", this.itemContent.getCurrencyDisplayName()))
				.make(), click -> {

			click.manager.showGUI(click.player, new CurrencyPickerGUI(this, click.player, (currency, item) -> {
				click.gui.exit();

				this.itemContent.setCurrency(currency.getStoreableName());

				if (item != null)
					this.itemContent.setCurrencyItem(item);

				click.manager.showGUI(click.player, new ShopAddContentItemGUI(click.player, ShopAddContentItemGUI.this.shop, ShopAddContentItemGUI.this.itemContent, ShopAddContentItemGUI.this.isEditing));
			}));
		});
	}

	private void drawSellItem() {
		// drop zone thing for item
		setButton(1, 4, generateItemIcon(), click -> {

			if (click.clickType == ClickType.RIGHT) {
				final ItemStack cursor = click.cursor;
				if (cursor != null && cursor.getType() != CompMaterial.AIR.parseMaterial()) {
					final ItemStack newIcon = cursor.clone();
					newIcon.setAmount(1);

					this.itemContent.setItem(newIcon);
					drawItemContent();
				}
			}

			if (click.clickType == ClickType.LEFT) {
				click.manager.showGUI(click.player, new MaterialPickerGUI(this, null, "", (event, selected) -> {
					this.itemContent.setItem(selected.parseItem());
					click.manager.showGUI(click.player, new ShopAddContentItemGUI(click.player, ShopAddContentItemGUI.this.shop, ShopAddContentItemGUI.this.itemContent, ShopAddContentItemGUI.this.isEditing));
				}));
			}
		});
	}

	private void drawPriceButtons() {
		// buy price
		setButton(2, 1, QuickItem
				.of(CompMaterial.SUNFLOWER)
				.name(TranslationManager.string(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_BUY_PRICE_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_BUY_PRICE_LORE, "shop_item_buy_price", this.itemContent.getBuyPrice()))
				.make(), click -> new TitleInput(Shops.getInstance(), click.player, TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_BUY_PRICE_TITLE), TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_BUY_PRICE_SUBTITLE)) {

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, ShopAddContentItemGUI.this);
			}

			@Override
			public boolean onResult(String input) {
				// validate if number is valid
				if (!validateIsNumber(click.player, input)) return false;

				ShopAddContentItemGUI.this.itemContent.setBuyPrice(Double.parseDouble(input));
				click.manager.showGUI(click.player, new ShopAddContentItemGUI(click.player, ShopAddContentItemGUI.this.shop, ShopAddContentItemGUI.this.itemContent, ShopAddContentItemGUI.this.isEditing));
				return true;
			}
		});

		// sell price
		setButton(2, 7, QuickItem
				.of(CompMaterial.SUNFLOWER)
				.name(TranslationManager.string(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_SELL_PRICE_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_SELL_PRICE_LORE, "shop_item_sell_price", this.itemContent.getSellPrice()))
				.make(), click -> new TitleInput(Shops.getInstance(), click.player, TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_SELL_PRICE_TITLE), TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_SELL_PRICE_SUBTITLE)) {

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, ShopAddContentItemGUI.this);
			}

			@Override
			public boolean onResult(String input) {
				// validate if number is valid
				if (!validateIsNumber(click.player, input)) return false;

				ShopAddContentItemGUI.this.itemContent.setSellPrice(Double.parseDouble(input));
				click.manager.showGUI(click.player, new ShopAddContentItemGUI(click.player, ShopAddContentItemGUI.this.shop, ShopAddContentItemGUI.this.itemContent, ShopAddContentItemGUI.this.isEditing));
				return true;
			}
		});
	}

	private void drawPriceToggleButtons() {
		final CompMaterial BUY_ICON = this.itemContent.isAllowBuy() ? CompMaterial.LIME_STAINED_GLASS_PANE : CompMaterial.RED_STAINED_GLASS_PANE;
		final CompMaterial SELL_ICON = this.itemContent.isAllowSell() ? CompMaterial.LIME_STAINED_GLASS_PANE : CompMaterial.RED_STAINED_GLASS_PANE;

		// redraw the breakdown (sell item)
		drawSellItem();

		// buy
		setButton(4, 1, QuickItem
				.of(BUY_ICON)
				.name(TranslationManager.string(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_TOGGLE_BUY_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_TOGGLE_BUY_LORE, "is_true", TranslationManager.string(this.itemContent.isAllowBuy() ? Translations.TRUE : Translations.FALSE)))
				.make(), click -> {
			this.itemContent.setAllowBuy(!this.itemContent.isAllowBuy());
			drawPriceToggleButtons();
		});

		// sell
		setButton(4, 7, QuickItem
				.of(SELL_ICON)
				.name(TranslationManager.string(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_TOGGLE_SELL_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_TOGGLE_SELL_LORE, "is_true", TranslationManager.string(this.itemContent.isAllowSell() ? Translations.TRUE : Translations.FALSE)))
				.make(), click -> {
			this.itemContent.setAllowSell(!this.itemContent.isAllowSell());
			drawPriceToggleButtons();
		});
	}

	private void drawMinPurchaseQtyButton() {
		setButton(3, 3, QuickItem
				.of(CompMaterial.LEVER)
				.name(TranslationManager.string(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_BUY_QTY_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_BUY_QTY_LORE, "shop_item_purchase_qty", this.itemContent.getMinimumPurchaseQty()))
				.make(), click -> new TitleInput(Shops.getInstance(), click.player, TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_MIN_QTY_TITLE), TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_MIN_QTY_SUBTITLE)) {

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, ShopAddContentItemGUI.this);
			}

			@Override
			public boolean onResult(String input) {
				// validate if number is valid
				if (!validateIsNumber(click.player, input)) return false;

				ShopAddContentItemGUI.this.itemContent.setMinimumPurchaseQty((int) Math.round(Double.parseDouble(input)));
				click.manager.showGUI(click.player, new ShopAddContentItemGUI(click.player, ShopAddContentItemGUI.this.shop, ShopAddContentItemGUI.this.itemContent, ShopAddContentItemGUI.this.isEditing));
				return true;
			}
		});
	}

	private void drawAddButton() {
		setButton(getRows() - 1, 4, QuickItem
				.of(CompMaterial.LIME_DYE)
				.name(TranslationManager.string(this.isEditing ? Translations.GUI_SHOP_EDIT_CONTENT_ITEMS_SAVE_NAME : Translations.GUI_SHOP_ADD_CONTENT_ITEMS_ADD_NAME))
				.lore(TranslationManager.list(this.isEditing ? Translations.GUI_SHOP_EDIT_CONTENT_ITEMS_SAVE_LORE : Translations.GUI_SHOP_ADD_CONTENT_ITEMS_ADD_LORE))
				.make(), click -> {

			if (!this.itemContent.isAllowBuy() && !this.itemContent.isAllowSell()) {
				// why the fuck would you want this
				return;
			}

			// if editing, update rather than create obv
			if (this.isEditing) {
				this.itemContent.sync(result -> {
					click.manager.showGUI(click.player, new ShopEditGUI(click.player, this.shop));
				});
				return;
			}

			// add content and go back
			Shops.getShopContentManager().create(this.shop, this.itemContent, created -> {
				if (created)
					click.manager.showGUI(click.player, new ShopEditGUI(click.player, this.shop));
			});
		});
	}

	private ItemStack generateItemIcon() {
		return QuickItem
				.of(this.itemContent.getItem())
				.lore(TranslationManager.list(this.player, Translations.GUI_SHOP_ADD_CONTENT_ITEMS_ICON_LORE,
						"is_buy_enabled", TranslationManager.string(this.itemContent.isAllowBuy() ? Translations.ENABLED : Translations.DISABLED),
						"is_sell_enabled", TranslationManager.string(this.itemContent.isAllowSell() ? Translations.ENABLED : Translations.DISABLED),
						"shop_item_buy_price", this.itemContent.getBuyPrice(),
						"shop_item_sell_price", this.itemContent.getSellPrice(),
						"shop_item_purchase_qty", this.itemContent.getMinimumPurchaseQty(),
						"shop_item_purchase_total", this.itemContent.getMinimumPurchaseQty() * this.itemContent.getBuyPrice(),
						"left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK),
						"right_click", TranslationManager.string(this.player, Translations.MOUSE_RIGHT_CLICK)))
				.make();
	}

	private boolean validateIsNumber(@NonNull final Player player, @NonNull final String input) {
		if (MathUtil.isDouble(input)) {
			// check if number is bigger than
			final double number = Double.parseDouble(input);
			if (number <= 0D) {
				Common.tell(player, TranslationManager.string(player, Translations.NUMBER_CANNOT_BE_ZERO));
				return false;
			}

			return true;
		}
		Common.tell(player, TranslationManager.string(player, Translations.NOT_A_NUMBER, "value", input));
		return false;
	}
}
