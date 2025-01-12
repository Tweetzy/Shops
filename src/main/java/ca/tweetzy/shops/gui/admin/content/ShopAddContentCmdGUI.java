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
import ca.tweetzy.shops.impl.shop.CommandShopContent;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public final class ShopAddContentCmdGUI extends ShopsBaseGUI {

	private final Shop shop;
	private CommandShopContent commandShopContent;
	private final boolean isEditing;


	public ShopAddContentCmdGUI(@NonNull Player player, @NonNull final Shop shop, @NonNull final CommandShopContent commandShopContent, final boolean isEditing) {
		super(new ShopEditGUI(player, shop), player, TranslationManager.string(isEditing ? Translations.GUI_SHOP_EDIT_CONTENT_TITLE : Translations.GUI_SHOP_ADD_CONTENT_TITLE), 6);
		this.shop = shop;
		this.commandShopContent = commandShopContent;
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

		// icon
		drawIcon();

		// price buttons
		drawPriceButtons();

		// command button
		drawCommandButton();

		// name & desc buttons
		drawInfoButtons();

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
						"currency", this.commandShopContent.getCurrencyDisplayName()))
				.make(), click -> {

			click.manager.showGUI(click.player, new CurrencyPickerGUI(this, click.player, (currency, item) -> {
				click.gui.exit();

				this.commandShopContent.setCurrency(currency.getStoreableName());

				if (item != null)
					this.commandShopContent.setCurrencyItem(item);

				click.manager.showGUI(click.player, new ShopAddContentCmdGUI(click.player, ShopAddContentCmdGUI.this.shop, ShopAddContentCmdGUI.this.commandShopContent, ShopAddContentCmdGUI.this.isEditing));
			}));
		});
	}

	private void drawIcon() {
		// drop zone thing for item
		setButton(1, 4, generateItemIcon(), click -> {

			if (click.clickType == ClickType.RIGHT) {
				final ItemStack cursor = click.cursor;
				if (cursor != null && cursor.getType() != CompMaterial.AIR.parseMaterial()) {
					final ItemStack newIcon = cursor.clone();
					newIcon.setAmount(1);

					this.commandShopContent.setIcon(newIcon);
					drawItemContent();
				}
			}

			if (click.clickType == ClickType.LEFT) {
				click.manager.showGUI(click.player, new MaterialPickerGUI(this, null, "", (event, selected) -> {
					this.commandShopContent.setIcon(selected);
					click.manager.showGUI(click.player, new ShopAddContentCmdGUI(click.player, ShopAddContentCmdGUI.this.shop, ShopAddContentCmdGUI.this.commandShopContent, ShopAddContentCmdGUI.this.isEditing));
				}));
			}
		});
	}

	private void drawCommandButton() {
		// buy price
		setButton(2, 7, QuickItem
				.of(CompMaterial.PAPER)
				.name(TranslationManager.string(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_COMMAND_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_COMMAND_LORE, "shop_item_command", this.commandShopContent.getCommand()))
				.make(), click -> new TitleInput(Shops.getInstance(), click.player, TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_COMMAND_TITLE), TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_COMMAND_SUBTITLE)) {

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, ShopAddContentCmdGUI.this);
			}

			@Override
			public boolean onResult(String input) {
				if (input == null || input.length() < 3) return false;

				ShopAddContentCmdGUI.this.commandShopContent.setCommand(input);
				click.manager.showGUI(click.player, new ShopAddContentCmdGUI(click.player, ShopAddContentCmdGUI.this.shop, ShopAddContentCmdGUI.this.commandShopContent, ShopAddContentCmdGUI.this.isEditing));
				return true;
			}
		});
	}

	private void drawInfoButtons() {
		setButton(4, 1, QuickItem
				.of(CompMaterial.NAME_TAG)
				.name(TranslationManager.string(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_COMMAND_NAME_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_COMMAND_NAME_LORE, "shop_item_command_name", this.commandShopContent.getName()))
				.make(), click -> new TitleInput(Shops.getInstance(), click.player, TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_COMMAND_NAME_TITLE), TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_COMMAND_NAME_SUBTITLE)) {

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, ShopAddContentCmdGUI.this);
			}

			@Override
			public boolean onResult(String input) {
				if (input == null || input.length() < 3) return false;

				ShopAddContentCmdGUI.this.commandShopContent.setName(input);
				click.manager.showGUI(click.player, new ShopAddContentCmdGUI(click.player, ShopAddContentCmdGUI.this.shop, ShopAddContentCmdGUI.this.commandShopContent, ShopAddContentCmdGUI.this.isEditing));
				return true;
			}
		});

		setButton(4, 7, QuickItem
				.of(CompMaterial.WRITTEN_BOOK)
				.name(TranslationManager.string(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_COMMAND_DESC_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_COMMAND_DESC_LORE, "shop_item_command_desc", this.commandShopContent.getDesc()))
				.make(), click -> new TitleInput(Shops.getInstance(), click.player, TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_COMMAND_DESC_TITLE), TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_COMMAND_DESC_SUBTITLE)) {

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, ShopAddContentCmdGUI.this);
			}

			@Override
			public boolean onResult(String input) {
				if (input == null || input.length() < 3) return false;

				ShopAddContentCmdGUI.this.commandShopContent.setDesc(input);
				click.manager.showGUI(click.player, new ShopAddContentCmdGUI(click.player, ShopAddContentCmdGUI.this.shop, ShopAddContentCmdGUI.this.commandShopContent, ShopAddContentCmdGUI.this.isEditing));
				return true;
			}
		});
	}

	private void drawPriceButtons() {
		// buy price
		setButton(2, 1, QuickItem
				.of(CompMaterial.SUNFLOWER)
				.name(TranslationManager.string(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_BUY_PRICE_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_BUY_PRICE_LORE, "shop_item_buy_price", this.commandShopContent.getBuyPrice()))
				.make(), click -> new TitleInput(Shops.getInstance(), click.player, TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_BUY_PRICE_TITLE), TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_BUY_PRICE_SUBTITLE)) {

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, ShopAddContentCmdGUI.this);
			}

			@Override
			public boolean onResult(String input) {
				// validate if number is valid
				if (!validateIsNumber(click.player, input)) return false;

				ShopAddContentCmdGUI.this.commandShopContent.setBuyPrice(Double.parseDouble(input));
				click.manager.showGUI(click.player, new ShopAddContentCmdGUI(click.player, ShopAddContentCmdGUI.this.shop, ShopAddContentCmdGUI.this.commandShopContent, ShopAddContentCmdGUI.this.isEditing));
				return true;
			}
		});
	}

	private void drawMinPurchaseQtyButton() {
		setButton(3, 3, QuickItem
				.of(CompMaterial.LEVER)
				.name(TranslationManager.string(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_BUY_QTY_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_BUY_QTY_LORE, "shop_item_purchase_qty", this.commandShopContent.getMinimumPurchaseQty()))
				.make(), click -> new TitleInput(Shops.getInstance(), click.player, TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_MIN_QTY_TITLE), TranslationManager.string(click.player, Translations.PROMPT_SERVER_SHOP_ITEM_MIN_QTY_SUBTITLE)) {

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, ShopAddContentCmdGUI.this);
			}

			@Override
			public boolean onResult(String input) {
				// validate if number is valid
				if (!validateIsNumber(click.player, input)) return false;

				ShopAddContentCmdGUI.this.commandShopContent.setMinimumPurchaseQty((int) Math.round(Double.parseDouble(input)));
				click.manager.showGUI(click.player, new ShopAddContentCmdGUI(click.player, ShopAddContentCmdGUI.this.shop, ShopAddContentCmdGUI.this.commandShopContent, ShopAddContentCmdGUI.this.isEditing));
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

			// if editing, update rather than create obv
			if (this.isEditing) {
				this.commandShopContent.sync(result -> {
					click.manager.showGUI(click.player, new ShopEditGUI(click.player, this.shop));
				});
				return;
			}

			// add content and go back
			Shops.getShopContentManager().create(this.shop, this.commandShopContent, created -> {
				if (created)
					click.manager.showGUI(click.player, new ShopEditGUI(click.player, this.shop));
			});
		});
	}

	private ItemStack generateItemIcon() {
		return QuickItem
				.of(this.commandShopContent.getIcon())
				.lore(TranslationManager.list(this.player, Translations.GUI_SHOP_ADD_CONTENT_ITEMS_CMD_ICON_LORE,
						"shop_item_command", this.commandShopContent.getCommand(),
						"shop_item_buy_price", this.commandShopContent.getBuyPrice(),
						"shop_item_purchase_qty", this.commandShopContent.getMinimumPurchaseQty(),
						"shop_item_purchase_total", this.commandShopContent.getMinimumPurchaseQty() * this.commandShopContent.getBuyPrice(),
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
