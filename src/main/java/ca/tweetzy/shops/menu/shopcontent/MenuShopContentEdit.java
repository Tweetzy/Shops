package ca.tweetzy.shops.menu.shopcontent;

import ca.tweetzy.shops.api.interfaces.shop.IShopItem;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.ShopItem;
import ca.tweetzy.shops.menu.settings.MenuShopEdit;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 31 2021
 * Time Created: 8:57 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuShopContentEdit extends MenuPagged<IShopItem> {

	private final Shop shop;

	private final Button addItemButton;
	private final Button searchButton;
	private final Button backButton;

	public MenuShopContentEdit(@NonNull final Shop shop) {
		super(shop.getShopItems());
		this.shop = shop;
		setTitle("&e" + shop.getId() + " &8> &eContents");
		setSize(9 * 6);

		this.addItemButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				if (click == ClickType.LEFT)
					new MenuAddShopItem(MenuShopContentEdit.this.shop, new ShopItem()).displayTo(player);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.SLIME_BALL, "&eAdd Item",
						"",
						"&eLeft Click &7to add item",
						"&eRight Click &7to quick select add"
				).make();
			}
		};

		this.searchButton = this.shop.getShopItems().size() == 0 ? Button.makeDummy(CompMaterial.BLACK_STAINED_GLASS_PANE, "") : Button.makeSimple(ItemCreator
				.of(CompMaterial.OAK_SIGN)
				.name("&eSearch Item").lore("", "&eClick &7to search for shop item"), player -> {

		});

		this.backButton = Button.makeSimple(ItemCreator.of(CompMaterial.IRON_DOOR).name("&eBack").lore("&eClick &7to exit/go back"), player -> new MenuShopEdit(this.shop).displayTo(player));
	}

	@Override
	protected ItemStack convertToItemStack(IShopItem shopItem) {
		final List<String> lore = new ArrayList<>();

		lore.add("");
		if (shopItem.getDescription().isEmpty())
			lore.add("&7Description&f: &cNone");
		else {
			lore.add("&7Description&f:");
			lore.addAll(shopItem.getDescription());
		}

		return ItemCreator
				.of(shopItem.getItem())
				.lore(lore)
				.lore("")
				.lore("&fx" + shopItem.getPurchaseQuantity() + " &ecosts &a$" + shopItem.getBuyPrice(),
						"&fx1&e costs &a$" + shopItem.getBuyPrice() / shopItem.getPurchaseQuantity(),
						"&fx" + shopItem.getPurchaseQuantity() + " &esells for &a$" + shopItem.getSellPrice(),
						"&fx1&e sells for &a$" + shopItem.getSellPrice() / shopItem.getPurchaseQuantity(),
						"",
						"&eLeft Click &7to edit shop item",
						"&ePress Drop &7to delete item"
				).make();
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == getSize() - 2)
			return this.addItemButton.getItem();

		if (slot == getSize() - 1)
			return this.searchButton.getItem();

		if (slot == getSize() - 9)
			return this.backButton.getItem();

		return super.getItemAt(slot);
	}

	@Override
	protected void onPageClick(Player player, IShopItem item, ClickType click) {
		if (click == ClickType.DROP) {
			this.shop.getShopItems().remove(item);
			ShopsData.getInstance().save();
			newInstance().displayTo(player);
			return;
		}

	}

	@Override
	protected ItemStack backgroundItem() {
		return ItemCreator.of(CompMaterial.BLACK_STAINED_GLASS_PANE).name(" ").make();
	}

	@Override
	public Menu newInstance() {
		return new MenuShopContentEdit(this.shop);
	}
}
