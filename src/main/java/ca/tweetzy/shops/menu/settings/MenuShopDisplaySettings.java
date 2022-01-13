package ca.tweetzy.shops.menu.settings;

import ca.tweetzy.shops.api.enums.MaterialSelectMode;
import ca.tweetzy.shops.api.enums.ShopLayout;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.menu.MenuMaterialSelector;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.ItemUtil;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.button.ButtonMenu;
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
 * Date Created: December 30 2021
 * Time Created: 9:39 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuShopDisplaySettings extends Menu {

	private final Shop shop;

	private final Button layoutButton;
	private final Button backgroundButton;
	private final Button shopItemSlotsButton;
	private final Button decorationItemsButton;

	private final Button backButton;

	public MenuShopDisplaySettings(@NonNull final Shop shop) {
		this.shop = shop;
		setTitle("&e" + shop.getId() + " &8> &eDisplay Settings");
		setSize(9 * 6);

		this.layoutButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				MenuShopDisplaySettings.this.shop.getDisplay().setShopLayout(MenuShopDisplaySettings.this.shop.getDisplay().getShopLayout().next());
				saveAndReopen(player);
			}

			@Override
			public ItemStack getItem() {
				final List<String> layoutLore = new ArrayList<>();
				layoutLore.add("");
				for (ShopLayout value : ShopLayout.values()) {
					if (MenuShopDisplaySettings.this.shop.getDisplay().getShopLayout() == value)
						layoutLore.add("&b→ " + value.getState());
					else
						layoutLore.add("&f" + value.getState());

				}

				layoutLore.add("");
				layoutLore.add("&eClick &7to go to next");

				return ItemCreator.of(CompMaterial.PAPER).name("&eLayout Mode").lore(layoutLore).make();
			}
		};

		this.backgroundButton = new ButtonMenu(new MenuMaterialSelector(this.shop, MaterialSelectMode.SHOP_BACKGROUND, null), ItemCreator.of(shop.getDisplay().getBackgroundItem()).name("&eBackground Item").glow(true).lore("", "&7Current&f: &e" + ItemUtil.bountifyCapitalized(shop.getDisplay().getBackgroundItem()), "", "&eClick &7to edit background item"));

		this.shopItemSlotsButton = new ButtonMenu(new MenuShopFillEdit(this.shop), ItemCreator
				.of(CompMaterial.WRITABLE_BOOK)
				.name("&eItem Slots")
				.lore("", "&eClick &7to edit fill slots"));

		this.decorationItemsButton = new ButtonMenu(new MenuShopDecorationEdit(this.shop), ItemCreator
				.of(CompMaterial.FLOWER_POT)
				.name("&eDecoration Slots")
				.lore("", "&eClick &7to edit decoration slots"));

		this.backButton = Button.makeSimple(ItemCreator.of(CompMaterial.IRON_DOOR).name("&eBack").lore("&eClick &7to exit/go back"), player -> new MenuShopSettings(this.shop).displayTo(player));
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == 10)
			return this.layoutButton.getItem();

		if (slot == 13)
			return this.backgroundButton.getItem();

		if (slot == 16)
			return this.shopItemSlotsButton.getItem();

		if (slot == 28)
			return this.decorationItemsButton.getItem();

		if (slot == getSize() - 9)
			return this.backButton.getItem();

		return ItemCreator.of(CompMaterial.BLACK_STAINED_GLASS_PANE).name(" ").make();
	}

	private void saveAndReopen(@NonNull final Player player) {
		ShopsData.getInstance().save();
		MenuShopDisplaySettings.this.newInstance().displayTo(player);
	}

	@Override
	public Menu newInstance() {
		return new MenuShopDisplaySettings(this.shop);
	}
}
