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
import ca.tweetzy.shops.impl.shop.CommandShopContent;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public final class ShopAddContentCmdGUI extends ShopsBaseGUI {

	private final Shop shop;
	private CommandShopContent commandShopContent;


	public ShopAddContentCmdGUI(@NonNull Player player, @NonNull final Shop shop, @NonNull final CommandShopContent commandShopContent) {
		super(new ShopEditGUI(player, shop), player, TranslationManager.string(Translations.GUI_SHOP_ADD_CONTENT_TITLE), 6);
		this.shop = shop;
		this.commandShopContent = commandShopContent;
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

		// min buy qty button
		drawMinPurchaseQtyButton();

		// add button
		drawAddButton();
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
					this.commandShopContent.setIcon(selected.parseItem());
					click.manager.showGUI(click.player, new ShopAddContentCmdGUI(click.player, ShopAddContentCmdGUI.this.shop, ShopAddContentCmdGUI.this.commandShopContent));
				}));
			}
		});
	}

	private void drawCommandButton() {
		// buy price
		setButton(2, 4, QuickItem
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
				click.manager.showGUI(click.player, new ShopAddContentCmdGUI(click.player, ShopAddContentCmdGUI.this.shop, ShopAddContentCmdGUI.this.commandShopContent));
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
				click.manager.showGUI(click.player, new ShopAddContentCmdGUI(click.player, ShopAddContentCmdGUI.this.shop, ShopAddContentCmdGUI.this.commandShopContent));
				return true;
			}
		});
	}

	private void drawMinPurchaseQtyButton() {
		setButton(3, 4, QuickItem
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
				click.manager.showGUI(click.player, new ShopAddContentCmdGUI(click.player, ShopAddContentCmdGUI.this.shop, ShopAddContentCmdGUI.this.commandShopContent));
				return true;
			}
		});
	}

	private void drawAddButton() {
		setButton(getRows() - 1, 4, QuickItem
				.of(CompMaterial.LIME_DYE)
				.name(TranslationManager.string(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_ADD_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_ADD_CONTENT_ITEMS_ADD_LORE))
				.make(), click -> {

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
