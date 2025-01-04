package ca.tweetzy.shops.gui.admin;

import ca.tweetzy.flight.command.CommandManager;
import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.gui.template.MaterialPickerGUI;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.flight.utils.input.TitleInput;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.SynchronizeResult;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.commands.DynamicShopCommand;
import ca.tweetzy.shops.gui.ShopsBaseGUI;
import ca.tweetzy.shops.gui.admin.layout.ShopLayoutEditorGUI;
import ca.tweetzy.shops.impl.currency.ItemCurrency;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public final class ShopSettingsGUI extends ShopsBaseGUI {

	private final Shop shop;

	public ShopSettingsGUI(@NonNull Player player, @NonNull final Shop shop) {
		super(new ShopEditGUI(player, shop), player, TranslationManager.string(Translations.GUI_SHOP_SETTINGS_TITLE, "shop_id", shop.getId()), 6);
		this.shop = shop;
		setAcceptsItems(true);
		draw();
	}

	@Override
	protected void draw() {
		applyBackExit();

		//icon
		setButton(1, 1, QuickItem
				.of(this.shop.getShopOptions().getDisplayIcon())
				.name(TranslationManager.string(Translations.GUI_SHOP_SETTINGS_ITEMS_ICON_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_SETTINGS_ITEMS_ICON_LORE))
				.make(), click -> {

			if (click.clickType == ClickType.LEFT) {
				click.manager.showGUI(click.player, new MaterialPickerGUI(this, null, null, (event, material) -> {
					if (material == null) return;

					final ItemStack oldIcon = this.shop.getShopOptions().getDisplayIcon();
					final ItemStack newIcon = material;
					assert newIcon != null;

					this.shop.getShopOptions().setDisplayIcon(newIcon);
					this.shop.sync(result -> {
						shop.getShopOptions().setDisplayIcon(result == SynchronizeResult.FAILURE ? oldIcon : newIcon);
						click.manager.showGUI(click.player, new ShopSettingsGUI(click.player, this.shop));
					});
				}));
			}

			if (click.clickType == ClickType.RIGHT) {
				final ItemStack cursor = click.cursor;
				if (cursor != null && cursor.getType() != CompMaterial.AIR.parseMaterial()) {

					final ItemStack oldIcon = this.shop.getShopOptions().getDisplayIcon();
					final ItemStack newIcon = cursor.clone();
					newIcon.setAmount(1);

					this.shop.getShopOptions().setDisplayIcon(newIcon);
					this.shop.sync(result -> {
						shop.getShopOptions().setDisplayIcon(result == SynchronizeResult.FAILURE ? oldIcon : newIcon);
						click.manager.showGUI(click.player, new ShopSettingsGUI(click.player, this.shop));
					});
				}
			}
		});

		setButton(1, 3, QuickItem
				.of(CompMaterial.NAME_TAG)
				.name(TranslationManager.string(Translations.GUI_SHOP_SETTINGS_ITEMS_DPN_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_SETTINGS_ITEMS_DPN_LORE, "shop_display_name", this.shop.getDisplayName()))
				.make(), click -> new TitleInput(Shops.getInstance(), click.player, TranslationManager.string(this.player, Translations.PROMPT_SERVER_SHOP_DPN_TITLE), TranslationManager.string(this.player, Translations.PROMPT_SERVER_SHOP_DPN_SUBTITLE)) {

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(click.player, ShopSettingsGUI.this);
			}

			@Override
			public boolean onResult(String string) {
				ShopSettingsGUI.this.shop.setDisplayName(string);
				ShopSettingsGUI.this.shop.sync(result -> click.manager.showGUI(click.player, new ShopSettingsGUI(click.player, ShopSettingsGUI.this.shop)));
				return true;
			}
		});

		setButton(1, 5, QuickItem
				.of(CompMaterial.BOOK)
				.name(TranslationManager.string(Translations.GUI_SHOP_SETTINGS_ITEMS_DESC_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_SETTINGS_ITEMS_DESC_LORE, "shop_description", this.shop.getDescription().get(0)))
				.make(), click -> new TitleInput(Shops.getInstance(), click.player, TranslationManager.string(this.player, Translations.PROMPT_SERVER_SHOP_DESC_TITLE), TranslationManager.string(this.player, Translations.PROMPT_SERVER_SHOP_DESC_SUBTITLE)) {

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(click.player, ShopSettingsGUI.this);
			}

			@Override
			public boolean onResult(String string) {
				ShopSettingsGUI.this.shop.setDescription(List.of(string));
				ShopSettingsGUI.this.shop.sync(result -> click.manager.showGUI(click.player, new ShopSettingsGUI(click.player, ShopSettingsGUI.this.shop)));
				return true;
			}
		});

		// open
		setButton(1, 7, QuickItem
				.of(this.shop.getShopOptions().isOpen() ? CompMaterial.LIME_STAINED_GLASS_PANE : CompMaterial.RED_STAINED_GLASS_PANE)
				.name(TranslationManager.string(Translations.GUI_SHOP_SETTINGS_ITEMS_OPEN_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_SETTINGS_ITEMS_OPEN_LORE, "is_true", TranslationManager.string(this.shop.getShopOptions().isOpen() ? Translations.TRUE : Translations.FALSE)))
				.make(), click -> {

			this.shop.getShopOptions().setOpen(!this.shop.getShopOptions().isOpen());
			this.shop.sync(result -> click.manager.showGUI(click.player, new ShopSettingsGUI(click.player, this.shop)));
		});

		// deco/layout
		setButton(3, 4, QuickItem
				.of(CompMaterial.CARTOGRAPHY_TABLE)
				.name(TranslationManager.string(Translations.GUI_SHOP_SETTINGS_ITEMS_DECO_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_SETTINGS_ITEMS_DECO_LORE,
						"left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK),
						"right_click", TranslationManager.string(this.player, Translations.MOUSE_RIGHT_CLICK)))
				.make(), click -> {

			if (click.clickType == ClickType.LEFT) {
				click.manager.showGUI(click.player, new ShopLayoutEditorGUI(click.player, this.shop));
				return;
			}

			if (click.clickType == ClickType.RIGHT) {
				final ItemStack cursor = click.cursor;
				if (cursor != null && cursor.getType() != CompMaterial.AIR.parseMaterial()) {
					final ItemStack newBG = cursor.clone();
					newBG.setAmount(1);
					this.shop.getShopOptions().getShopDisplay().setBackgroundItem(newBG);
				} else {
					this.shop.getShopOptions().getShopDisplay().setBackgroundItem(QuickItem.of(CompMaterial.AIR).make());
				}

				this.shop.sync(result -> {
					if (result == SynchronizeResult.SUCCESS)
						Common.tell(click.player, TranslationManager.string(click.player, Translations.UPDATED_SHOP_BG));
				});

			}
		});

		// permission
		setButton(3, 2, QuickItem
				.of(CompMaterial.PAPER)
				.name(TranslationManager.string(Translations.GUI_SHOP_SETTINGS_ITEMS_PERMISSION_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_SETTINGS_ITEMS_PERMISSION_LORE,
						"shop_permission", this.shop.getShopOptions().getPermission(),
						"is_true", TranslationManager.string(this.shop.getShopOptions().isRequiresPermission() ? Translations.TRUE : Translations.FALSE),
						"left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK),
						"right_click", TranslationManager.string(this.player, Translations.MOUSE_RIGHT_CLICK)
				)).make(), click -> {

			if (click.clickType == ClickType.LEFT) {
				new TitleInput(Shops.getInstance(), click.player, TranslationManager.string(this.player, Translations.PROMPT_SERVER_SHOP_PERM_TITLE), TranslationManager.string(this.player, Translations.PROMPT_SERVER_SHOP_PERM_SUBTITLE)) {

					@Override
					public void onExit(Player player) {
						click.manager.showGUI(click.player, ShopSettingsGUI.this);
					}

					@Override
					public boolean onResult(String string) {
						string = ChatColor.stripColor(string.toLowerCase().replace(" ", "").trim());
						ShopSettingsGUI.this.shop.getShopOptions().setPermission(string);
						ShopSettingsGUI.this.shop.sync(result -> click.manager.showGUI(click.player, new ShopSettingsGUI(click.player, ShopSettingsGUI.this.shop)));
						return true;
					}
				};
			}

			if (click.clickType == ClickType.RIGHT) {
				this.shop.getShopOptions().setRequiresPermission(!this.shop.getShopOptions().isRequiresPermission());
				this.shop.sync(result -> click.manager.showGUI(click.player, new ShopSettingsGUI(click.player, this.shop)));
			}
		});

		// command
		setButton(3, 6, QuickItem
				.of(CompMaterial.COMMAND_BLOCK)
				.name(TranslationManager.string(Translations.GUI_SHOP_SETTINGS_ITEMS_CMD_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_SETTINGS_ITEMS_CMD_LORE,
						"shop_command", this.shop.getShopOptions().getCommand(),
						"is_true", TranslationManager.string(this.shop.getShopOptions().isUsingCommand() ? Translations.TRUE : Translations.FALSE),
						"left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK),
						"right_click", TranslationManager.string(this.player, Translations.MOUSE_RIGHT_CLICK)
				)).make(), click -> {

			if (click.clickType == ClickType.LEFT) {
				new TitleInput(Shops.getInstance(), click.player, TranslationManager.string(this.player, Translations.PROMPT_SERVER_SHOP_CMD_TITLE), TranslationManager.string(this.player, Translations.PROMPT_SERVER_SHOP_CMD_SUBTITLE)) {

					@Override
					public void onExit(Player player) {
						click.manager.showGUI(click.player, ShopSettingsGUI.this);
					}

					@Override
					public boolean onResult(String string) {
						string = ChatColor.stripColor(string.toLowerCase().replace(" ", "").trim());
						ShopSettingsGUI.this.shop.getShopOptions().setCommand(string);
						ShopSettingsGUI.this.shop.sync(result -> click.manager.showGUI(click.player, new ShopSettingsGUI(click.player, ShopSettingsGUI.this.shop)));
						return true;
					}
				};
			}

			if (click.clickType == ClickType.RIGHT) {
				this.shop.getShopOptions().setUsingCommand(!this.shop.getShopOptions().isUsingCommand());

				if (this.shop.getShopOptions().isUsingCommand())
					Shops.getCommandManager().registerCommandDynamically(new DynamicShopCommand(shop));
				else
					CommandManager.unregisterCommands(Collections.singleton(Shops.getInstance().getServer().getPluginCommand(shop.getShopOptions().getCommand())));


				this.shop.sync(result -> click.manager.showGUI(click.player, new ShopSettingsGUI(click.player, this.shop)));
			}
		});
	}
}
