package ca.tweetzy.shops.menu.shopcontent;

import ca.tweetzy.shops.api.enums.MaterialSelectMode;
import ca.tweetzy.shops.api.enums.ShopItemType;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.ShopItem;
import ca.tweetzy.shops.menu.MenuMaterialSelector;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.tweety.ItemUtil;
import ca.tweetzy.tweety.conversation.TitleInput;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.button.ButtonMenu;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

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

	public MenuAddShopItem(@NonNull final Shop shop, @NonNull final ShopItem shopItem) {
		this.shop = shop;
		this.shopItem = shopItem;
		setTitle("&e" + shop.getId() + " &8> &eAdd Item");
		setSize(9 * 6);

		this.materialSelectorButton = new ButtonMenu(new MenuMaterialSelector(this.shop, MaterialSelectMode.ADD_TO_SHOP), ItemCreator
				.of(this.shopItem.getItem())
				.name("&e" + ItemUtil.bountifyCapitalized(this.shopItem.getItem().getType()))
				.lore("", "&eClick &7to change material"));


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

		return ItemCreator.of(CompMaterial.BLACK_STAINED_GLASS_PANE).name(" ").make();
	}

	private void reopen(@NonNull final Player player) {
		MenuAddShopItem.this.newInstance().displayTo(player);
	}

	@Override
	public Menu newInstance() {
		return new MenuAddShopItem(this.shop, this.shopItem);
	}
}
