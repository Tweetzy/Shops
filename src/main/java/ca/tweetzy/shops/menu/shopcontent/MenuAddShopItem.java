package ca.tweetzy.shops.menu.shopcontent;

import ca.tweetzy.shops.api.enums.MaterialSelectMode;
import ca.tweetzy.shops.api.enums.ShopItemQuantityType;
import ca.tweetzy.shops.api.enums.ShopItemType;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.ShopItem;
import ca.tweetzy.shops.menu.MenuCurrencyList;
import ca.tweetzy.shops.menu.MenuMaterialSelector;
import ca.tweetzy.shops.menu.refill.MenuRefillTimeList;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.tweety.ItemUtil;
import ca.tweetzy.tweety.conversation.TitleInput;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.button.ButtonMenu;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.menu.model.MenuClickLocation;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 05 2022
 * Time Created: 1:48 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuAddShopItem extends Menu {

	private final Shop shop;
	private final ShopItem shopItem;

	private final Button materialSelectorButton;
	private final Button buyPriceButton;
	private final Button sellPriceButton;
	private final Button typeButton;
	private final Button quantityTypeButton;
	private final Button buyQuantityButton;
	private final Button currencyButton;
	private final Button refillButton;
	private final Button stockButton;
	private final Button commandsButton;
	private final Button descriptionButton;

	private boolean selectingFromInventory;

	public MenuAddShopItem(@NonNull final Shop shop, @NonNull final ShopItem shopItem) {
		this.shop = shop;
		this.shopItem = shopItem;
		this.selectingFromInventory = false;
		setTitle("&e" + shop.getId() + " &8> &eAdd Item");
		setSize(9 * 6);

		this.materialSelectorButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				if (clickType == ClickType.LEFT) {
					if (!MenuAddShopItem.this.selectingFromInventory)
						new MenuMaterialSelector(MenuAddShopItem.this.shop, MaterialSelectMode.ADD_TO_SHOP, MenuAddShopItem.this.shopItem).displayTo(player);
					else {
						if (player.getItemOnCursor().getType() == CompMaterial.AIR.toMaterial()) return;
						MenuAddShopItem.this.shopItem.setItem(player.getItemOnCursor());
						restartMenu();
					}
				}

				if (clickType == ClickType.RIGHT) {
					MenuAddShopItem.this.selectingFromInventory = true;
					restartMenu();
				}
			}

			@Override
			public ItemStack getItem() {

				final List<String> lore = new ArrayList<>();
				lore.add("");
				if (MenuAddShopItem.this.selectingFromInventory) {
					lore.add("&eDrag on drop item here to set it");
				} else {
					lore.add("&eLeft Click &7to open material picker");
					lore.add("&eRight Click &7to select from your inventory");
				}

				return ItemCreator
						.of(MenuAddShopItem.this.shopItem.getItem())
						.name("&e" + ItemUtil.bountifyCapitalized(MenuAddShopItem.this.shopItem.getItem().getType()))
						.lore(lore).make();
			}
		};

		this.buyPriceButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				if (clickType == ClickType.LEFT) {
					new TitleInput(player, "&eShop Item Edit", "&7Enter new buy price") {

						@Override
						public boolean onResult(String string) {
							if (!NumberUtils.isNumber(string)) {
								tell(Localization.Error.NOT_A_NUMBER.replace("{value}", string));
								return false;
							}

							MenuAddShopItem.this.shopItem.setBuyPrice(Double.parseDouble(string));
							reopen(player);
							return true;
						}
					};
				}

				if (clickType == ClickType.RIGHT) {
					MenuAddShopItem.this.shopItem.setCanBeBought(!MenuAddShopItem.this.shopItem.canBeBought());
					reopen(player);
				}
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator
						.of(CompMaterial.SUNFLOWER)
						.name("&eBuy Price")
						.lore("", "&7Current&f: &a" + MenuAddShopItem.this.shopItem.getBuyPrice(), "&7Enabled&f: " + (MenuAddShopItem.this.shopItem.canBeBought() ? "&aTrue" : "&cFalse"), "", "&eLeft Click &7to change price", "&eRight Click &7to toggle value").make();
			}
		};

		this.sellPriceButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				if (clickType == ClickType.LEFT) {
					new TitleInput(player, "&eShop Item Edit", "&7Enter new sell price") {

						@Override
						public boolean onResult(String string) {
							if (!NumberUtils.isNumber(string)) {
								tell(Localization.Error.NOT_A_NUMBER.replace("{value}", string));
								return false;
							}

							MenuAddShopItem.this.shopItem.setSellPrice(Double.parseDouble(string));
							reopen(player);
							return true;
						}
					};
				}

				if (clickType == ClickType.RIGHT) {
					MenuAddShopItem.this.shopItem.setCanBeSold(!MenuAddShopItem.this.shopItem.canBeSold());
					reopen(player);
				}
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator
						.of(CompMaterial.SUNFLOWER)
						.name("&eSell Price")
						.lore("", "&7Current&f: &a" + MenuAddShopItem.this.shopItem.getSellPrice(), "&7Enabled&f: " + (MenuAddShopItem.this.shopItem.canBeSold() ? "&aTrue" : "&cFalse"), "", "&eLeft Click &7to change price", "&eRight Click &7to toggle value").make();
			}
		};

		this.typeButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				if (clickType == ClickType.LEFT) {
					MenuAddShopItem.this.shopItem.setType(MenuAddShopItem.this.shopItem.getType().previous());
					reopen(player);
				}

				if (clickType == ClickType.RIGHT) {
					MenuAddShopItem.this.shopItem.setType(MenuAddShopItem.this.shopItem.getType().next());
					reopen(player);
				}
			}

			@Override
			public ItemStack getItem() {
				final List<String> typeLore = new ArrayList<>();
				typeLore.add("");
				for (ShopItemType value : ShopItemType.values()) {
					if (MenuAddShopItem.this.shopItem.getType() == value)
						typeLore.add("&b→ " + value.getType());
					else
						typeLore.add("&f" + value.getType());

				}

				typeLore.add("");
				typeLore.add("&eLeft Click &7to go back");
				typeLore.add("&eRight Click &7to go to next");

				return ItemCreator.of(CompMaterial.REPEATER).name("&eType").lore(typeLore).make();
			}
		};

		this.quantityTypeButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				if (clickType == ClickType.LEFT) {
					MenuAddShopItem.this.shopItem.setQuantityType(MenuAddShopItem.this.shopItem.getQuantityType().previous());
					reopen(player);
				}

				if (clickType == ClickType.RIGHT) {
					MenuAddShopItem.this.shopItem.setQuantityType(MenuAddShopItem.this.shopItem.getQuantityType().next());
					reopen(player);
				}
			}

			@Override
			public ItemStack getItem() {
				final List<String> quantityTypeLore = new ArrayList<>();
				quantityTypeLore.add("");
				for (ShopItemQuantityType value : ShopItemQuantityType.values()) {
					if (MenuAddShopItem.this.shopItem.getQuantityType() == value)
						quantityTypeLore.add("&b→ " + ItemUtil.bountifyCapitalized(value));
					else
						quantityTypeLore.add("&f" + ItemUtil.bountifyCapitalized(value));

				}

				quantityTypeLore.add("");
				quantityTypeLore.add("&eLeft Click &7to go back");
				quantityTypeLore.add("&eRight Click &7to go to next");

				return ItemCreator.of(CompMaterial.PAPER).name("&eQuantity Type").lore(quantityTypeLore).make();
			}
		};

		this.buyQuantityButton = Button.makeSimple(ItemCreator
				.of(CompMaterial.NAME_TAG)
				.name("&EPurchase Quantity")
				.lore("", "&7Current&f: &e" + shopItem.getPurchaseQuantity(), "", "&eClick &7to edit purchase quantity"), player -> new TitleInput(player, "&eShop Item Edit", "&7Enter new purchase quantity") {

			@Override
			public boolean onResult(String string) {
				if (!NumberUtils.isNumber(string)) return false;
				MenuAddShopItem.this.shopItem.setPurchaseQuantity(Integer.parseInt(string));
				reopen(player);
				return true;
			}
		});

		this.currencyButton = new ButtonMenu(new MenuCurrencyList(this.shop, this.shopItem), ItemCreator.of(CompMaterial.GOLD_INGOT).name("&eCurrency").lore("", "&e" + shopItem.getCurrency().getPluginName() + "&7/&e" + shopItem.getCurrency().getName(), "", "&eClick &7to edit currency"));
		this.refillButton = new ButtonMenu(new MenuRefillTimeList(this.shop, this.shopItem), ItemCreator.of(CompMaterial.CLOCK, "&eRefill Times").lore("", "&eClick &7to edit refill times"));

		this.stockButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				new TitleInput(player, "&eShop Item Edit", "&7Enter new stock amount") {

					@Override
					public boolean onResult(String string) {
						if (!NumberUtils.isNumber(string)) {
							tell(Localization.Error.NOT_A_NUMBER.replace("{value}", string));
							return false;
						}

						MenuAddShopItem.this.shopItem.setStock(Integer.parseInt(string));
						reopen(player);
						return true;
					}
				};
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator
						.of(CompMaterial.SUNFLOWER)
						.name("&eStock")
						.lore("", "&7Current&f: &a" + MenuAddShopItem.this.shopItem.getStock(), "", "&eClick &7to change stock").make();
			}
		};

		this.commandsButton = new ButtonMenu(new MenuShopItemCommands(this.shop, this.shopItem), ItemCreator.of(CompMaterial.WRITABLE_BOOK, "&eCommands").lore("", "&eClick &7to edit commands"));

		final List<String> itemDesc = new ArrayList<>();
		itemDesc.add("");
		itemDesc.add("&7Current&f: ");
		itemDesc.addAll(shopItem.getDescription());
		itemDesc.add("");
		itemDesc.add("&eClick &7to adjust description");

		this.descriptionButton = new ButtonMenu(new MenuShopItemDesc(this.shop, this.shopItem), ItemCreator.of(CompMaterial.PAPER).name("&EDescription").lore(itemDesc));
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == 9 + 4)
			return this.materialSelectorButton.getItem();

		if (slot == 19)
			return this.buyPriceButton.getItem();

		if (slot == 20)
			return this.sellPriceButton.getItem();

		if (slot == 24)
			return this.typeButton.getItem();

		if (slot == 25)
			return this.quantityTypeButton.getItem();

		if (slot == 28)
			return this.descriptionButton.getItem();

		if (slot == 31)
			return ItemCreator.of(CompMaterial.NETHER_STAR).name("&eItem Overview")
					.lore(
							"",
							"&fx" + this.shopItem.getPurchaseQuantity() + " &ecosts &a$" + this.shopItem.getBuyPrice(),
							"&fx1&e costs &a$" + this.shopItem.getBuyPrice() / this.shopItem.getPurchaseQuantity(),
							"&fx" + this.shopItem.getPurchaseQuantity() + " &esells for &a$" + this.shopItem.getSellPrice(),
							"&fx1&e sells for &a$" + this.shopItem.getSellPrice() / this.shopItem.getPurchaseQuantity()
					)
					.make();

		if (slot == 37)
			return this.buyQuantityButton.getItem();

		if (slot == 38)
			return this.currencyButton.getItem();

		if (slot == 43 && this.shopItem.getQuantityType() == ShopItemQuantityType.LIMITED)
			return this.refillButton.getItem();

		if (slot == 34 && this.shopItem.getQuantityType() == ShopItemQuantityType.LIMITED)
			return this.stockButton.getItem();

		if (slot == 42 && (this.shopItem.getType() == ShopItemType.COMMAND || this.shopItem.getType() == ShopItemType.BOTH))
			return this.commandsButton.getItem();

		return ItemCreator.of(CompMaterial.BLACK_STAINED_GLASS_PANE).name(" ").make();
	}

	@Override
	protected boolean isActionAllowed(MenuClickLocation location, int slot, @Nullable ItemStack clicked, @Nullable ItemStack cursor) {
		return location == MenuClickLocation.PLAYER_INVENTORY;
	}

	@Override
	protected void onMenuClick(Player player, int slot, InventoryAction action, ClickType click, ItemStack cursor, ItemStack clicked, boolean cancelled) {
		if (this.selectingFromInventory)
			if (cursor != null)
				tell(ItemUtil.bountifyCapitalized(cursor.getType()));
	}

	private void reopen(@NonNull final Player player) {
		MenuAddShopItem.this.newInstance().displayTo(player);
	}

	@Override
	public Menu newInstance() {
		return new MenuAddShopItem(this.shop, this.shopItem);
	}
}
