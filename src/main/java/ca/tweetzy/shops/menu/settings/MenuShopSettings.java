package ca.tweetzy.shops.menu.settings;

import ca.tweetzy.shops.api.enums.ShopState;
import ca.tweetzy.shops.commands.DynamicShopCommand;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.menu.settings.display.MenuShopDisplaySettings;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.conversation.TitleInput;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.button.ButtonMenu;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.remain.CompMaterial;
import ca.tweetzy.tweety.remain.Remain;
import lombok.NonNull;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 22 2021
 * Time Created: 11:01 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuShopSettings extends Menu {

	private final Shop shop;

	private final Button publicButton;
	private final Button stateButton;
	private final Button openCommandButton;
	private final Button seePermissionButton;
	private final Button sellPermissionButton;
	private final Button buyPermissionButton;
	private final Button displaySettingsButton;
	private final Button discountButton;

	private final Button backButton;

	public MenuShopSettings(@NonNull final Shop shop) {
		this.shop = shop;
		setTitle("&e" + shop.getId() + " &8> &eSettings");
		setSize(9 * 6);

		this.publicButton = Button.makeSimple(ItemCreator
				.of(this.shop.getSettings().isPublic() ? CompMaterial.LIME_STAINED_GLASS_PANE : CompMaterial.RED_STAINED_GLASS_PANE)
				.name("&EPublic")
				.lore("", "&7Current&f: " + (this.shop.getSettings().isPublic() ? "&aTrue" : "&cFalse"), "", "&eClick &7to toggle value"), player -> {

			MenuShopSettings.this.shop.getSettings().setPublic(!MenuShopSettings.this.shop.getSettings().isPublic());
			saveAndReopen(player);
		});

		this.stateButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				if (clickType == ClickType.LEFT) {
					MenuShopSettings.this.shop.getSettings().setShopState(MenuShopSettings.this.shop.getSettings().getShopState().previous());
					saveAndReopen(player);
				}

				if (clickType == ClickType.RIGHT) {
					MenuShopSettings.this.shop.getSettings().setShopState(MenuShopSettings.this.shop.getSettings().getShopState().next());
					saveAndReopen(player);
				}
			}

			@Override
			public ItemStack getItem() {
				final List<String> stateLore = new ArrayList<>();
				stateLore.add("");
				for (ShopState value : ShopState.values()) {
					if (MenuShopSettings.this.shop.getSettings().getShopState() == value)
						stateLore.add("&b→ " + value.getState());
					else
						stateLore.add("&f" + value.getState());

				}

				stateLore.add("");
				stateLore.add("&eLeft Click &7to go back");
				stateLore.add("&eRight Click &7to go to next");

				return ItemCreator.of(CompMaterial.PAPER).name("&eState").lore(stateLore).make();
			}
		};

		this.discountButton = Button.makeSimple(ItemCreator
				.of(CompMaterial.ANVIL)
				.name("&eDiscount")
				.lore("", "&7Current&f: &e" + this.shop.getSettings().getDiscount(), "", "&eClick &7to change discount"), player -> new TitleInput(player, "&eShop Edit", "&7Enter new discount") {

			@Override
			public boolean onResult(String string) {
				if (!NumberUtils.isNumber(string)) {
					tell(Localization.Error.NOT_A_NUMBER.replace("{value}", string));
					return false;
				}

				final double discount = Double.parseDouble(string);

				MenuShopSettings.this.shop.getSettings().setDiscount(Math.min(Math.max(discount, 0D), 100D));
				saveAndReopen(player);
				return true;
			}
		});

		this.openCommandButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				if (clickType == ClickType.LEFT) {
					new TitleInput(player, "&eShop Edit", "&7Enter new open command") {

						@Override
						public boolean onResult(String string) {
							Remain.unregisterCommand(MenuShopSettings.this.shop.getSettings().getOpenCommand());
							MenuShopSettings.this.shop.getSettings().setOpenCommand(string);
							Remain.registerCommand(new DynamicShopCommand(MenuShopSettings.this.shop.getSettings().getOpenCommand()));
							saveAndReopen(player);
							return true;
						}
					};
				}

				if (clickType == ClickType.RIGHT) {
					if (MenuShopSettings.this.shop.getSettings().isUseOpenCommand()) {
						MenuShopSettings.this.shop.getSettings().setUseOpenCommand(false);
						Remain.unregisterCommand(MenuShopSettings.this.shop.getSettings().getOpenCommand());
					} else {
						MenuShopSettings.this.shop.getSettings().setUseOpenCommand(true);
						Remain.registerCommand(new DynamicShopCommand(MenuShopSettings.this.shop.getSettings().getOpenCommand()));
					}

					saveAndReopen(player);
				}
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator
						.of(MenuShopSettings.this.shop.getSettings().isUseOpenCommand() ? CompMaterial.LIME_STAINED_GLASS_PANE : CompMaterial.RED_STAINED_GLASS_PANE)
						.name("&eOpen Command")
						.lore("", "&7Current&f: &e/" + MenuShopSettings.this.shop.getSettings().getOpenCommand(), "&7Enabled&f: " + (MenuShopSettings.this.shop.getSettings().isUseOpenCommand() ? "&aTrue" : "&cFalse"), "", "&eLeft Click &7to change command", "&eRight Click &7to toggle value").make();
			}
		};

		this.seePermissionButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				if (clickType == ClickType.LEFT) {
					new TitleInput(player, "&eShop Edit", "&7Enter new see permission") {

						@Override
						public boolean onResult(String string) {
							MenuShopSettings.this.shop.getSettings().setSeePermission(string);
							saveAndReopen(player);
							return true;
						}
					};
				}

				if (clickType == ClickType.RIGHT) {
					MenuShopSettings.this.shop.getSettings().setRequirePermissionToSee(!MenuShopSettings.this.shop.getSettings().isRequirePermissionToSee());
					saveAndReopen(player);
				}
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator
						.of(MenuShopSettings.this.shop.getSettings().isRequirePermissionToSee() ? CompMaterial.LIME_STAINED_GLASS_PANE : CompMaterial.RED_STAINED_GLASS_PANE)
						.name("&eSee Permission")
						.lore("", "&7Current&f: &e" + MenuShopSettings.this.shop.getSettings().getSeePermission(), "&7Enabled&f: " + (MenuShopSettings.this.shop.getSettings().isRequirePermissionToSee() ? "&aTrue" : "&cFalse"), "", "&eLeft Click &7to change permission", "&eRight Click &7to toggle value").make();
			}
		};

		this.sellPermissionButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				if (clickType == ClickType.LEFT) {
					new TitleInput(player, "&eShop Edit", "&7Enter new sell permission") {

						@Override
						public boolean onResult(String string) {
							MenuShopSettings.this.shop.getSettings().setSellPermission(string);
							saveAndReopen(player);
							return true;
						}
					};
				}

				if (clickType == ClickType.RIGHT) {
					MenuShopSettings.this.shop.getSettings().setRequirePermissionToSell(!MenuShopSettings.this.shop.getSettings().isRequirePermissionToSell());
					saveAndReopen(player);
				}
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator
						.of(MenuShopSettings.this.shop.getSettings().isRequirePermissionToSell() ? CompMaterial.LIME_STAINED_GLASS_PANE : CompMaterial.RED_STAINED_GLASS_PANE)
						.name("&eSell Permission")
						.lore("", "&7Current&f: &e" + MenuShopSettings.this.shop.getSettings().getSellPermission(), "&7Enabled&f: " + (MenuShopSettings.this.shop.getSettings().isRequirePermissionToSell() ? "&aTrue" : "&cFalse"), "", "&eLeft Click &7to change permission", "&eRight Click &7to toggle value").make();
			}
		};

		this.buyPermissionButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				if (clickType == ClickType.LEFT) {
					new TitleInput(player, "&eShop Edit", "&7Enter new buy permission") {

						@Override
						public boolean onResult(String string) {
							MenuShopSettings.this.shop.getSettings().setBuyPermission(string);
							saveAndReopen(player);
							return true;
						}
					};
				}

				if (clickType == ClickType.RIGHT) {
					MenuShopSettings.this.shop.getSettings().setRequirePermissionToBuy(!MenuShopSettings.this.shop.getSettings().isRequirePermissionToBuy());
					saveAndReopen(player);
				}
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator
						.of(MenuShopSettings.this.shop.getSettings().isRequirePermissionToBuy() ? CompMaterial.LIME_STAINED_GLASS_PANE : CompMaterial.RED_STAINED_GLASS_PANE)
						.name("&eBuy Permission")
						.lore("", "&7Current&f: &e" + MenuShopSettings.this.shop.getSettings().getBuyPermission(), "&7Enabled&f: " + (MenuShopSettings.this.shop.getSettings().isRequirePermissionToBuy() ? "&aTrue" : "&cFalse"), "", "&eLeft Click &7to change permission", "&eRight Click &7to toggle value").make();
			}
		};

		this.displaySettingsButton = new ButtonMenu(new MenuShopDisplaySettings(this.shop), ItemCreator.of(CompMaterial.COMPARATOR).name("&eDisplay Settings").lore("", "&eClick &7to edit display settings"));
		this.backButton = Button.makeSimple(ItemCreator.of(CompMaterial.IRON_DOOR).name("&eBack").lore("&eClick &7to exit/go back"), player -> new MenuShopEdit(this.shop).displayTo(player));
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == 10)
			return this.publicButton.getItem();

		if (slot == 12)
			return this.stateButton.getItem();

		if (slot == 14)
			return this.discountButton.getItem();

		if (slot == 16)
			return this.openCommandButton.getItem();

		if (slot == 28)
			return this.seePermissionButton.getItem();

		if (slot == 30)
			return this.sellPermissionButton.getItem();

		if (slot == 32)
			return this.buyPermissionButton.getItem();


		if (slot == 34)
			return this.displaySettingsButton.getItem();

		if (slot == this.getSize() - 9)
			return this.backButton.getItem();

		return ItemCreator.of(CompMaterial.BLACK_STAINED_GLASS_PANE).name(" ").make();
	}

	private void saveAndReopen(@NonNull final Player player) {
		ShopsData.getInstance().saveAll();
		MenuShopSettings.this.newInstance().displayTo(player);
	}

	@Override
	public Menu newInstance() {
		return new MenuShopSettings(this.shop);
	}
}
