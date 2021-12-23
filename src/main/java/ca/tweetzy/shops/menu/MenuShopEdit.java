package ca.tweetzy.shops.menu;

import ca.tweetzy.shops.api.enums.ShopListType;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.model.input.TitleInput;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.button.ButtonMenu;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 22 2021
 * Time Created: 9:24 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuShopEdit extends Menu {

	private final Shop shop;
	private final Button iconButton;
	private final Button nameButton;
	private final Button descButton;
	private final Button itemsButton;
	private final Button currencyButton;
	private final Button settingsButton;
	private final Button backButton;

	public MenuShopEdit(@NonNull final Shop shop) {
		this.shop = shop;
		setTitle("&e" + shop.getId() + " &8> &eEdit");
		setSize(9 * 6);

		this.iconButton = new ButtonMenu(new MenuMaterialSelector(this.shop), ItemCreator.of(shop.getIcon().get()).name(shop.getDisplayName()).lore("&eClick &7to edit icon"));
		this.nameButton = Button.makeSimple(ItemCreator
				.of(CompMaterial.NAME_TAG)
				.name("&EDisplay Name")
				.lore("", "&7Current&f: " + shop.getDisplayName(), "", "&eClick &7to edit display name"), player -> new TitleInput(player, "&eShop Edit", "&7Enter new display name") {

			@Override
			public boolean onResult(String string) {
				MenuShopEdit.this.shop.setDisplayName(string);
				ShopsData.getInstance().save();
				MenuShopEdit.this.newInstance().displayTo(player);
				return true;
			}
		});

		this.descButton = Button.makeSimple(ItemCreator
				.of(CompMaterial.WRITABLE_BOOK)
				.name("&EDescription")
				.lore("", "&7Current&f: " + shop.getDescription(), "", "&eClick &7to edit description"), player -> new TitleInput(player, "&eShop Edit", "&7Enter new description") {

			@Override
			public boolean onResult(String string) {
				MenuShopEdit.this.shop.setDescription(string);
				ShopsData.getInstance().save();
				MenuShopEdit.this.newInstance().displayTo(player);
				return true;
			}
		});

		this.itemsButton = new ButtonMenu(this, ItemCreator.of(CompMaterial.CHEST).name("&eItems").lore("&eClick &7to edit items"));
		this.currencyButton = new ButtonMenu(new MenuCurrencyList(this.shop), ItemCreator.of(CompMaterial.GOLD_INGOT).name("&eCurrency").lore("", "&e" + shop.getCurrency().getPluginName() + "&7/&e" + shop.getCurrency().getName(), "", "&eClick &7to edit currency"));
		this.settingsButton = new ButtonMenu(this, ItemCreator.of(CompMaterial.REPEATER).name("&eSettings").lore("&eClick &7to view more settings"));
		this.backButton = new ButtonMenu(new MenuShopList(ShopListType.EDIT), ItemCreator.of(CompMaterial.IRON_DOOR).name("&eBack").lore("&eClick &7to exit/go back"));
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == 9 + 4)
			return this.iconButton.getItem();
		if (slot == 9 * 2 + 1)
			return this.nameButton.getItem();
		if (slot == 9 * 3 + 2)
			return this.descButton.getItem();
		if (slot == 9 * 3 + 4)
			return this.itemsButton.getItem();
		if (slot == 9 * 3 + 6)
			return this.currencyButton.getItem();
		if (slot == 9 * 2 + 7)
			return this.settingsButton.getItem();
		if (slot == this.getSize() - 9)
			return this.backButton.getItem();

		return ItemCreator.of(CompMaterial.BLACK_STAINED_GLASS_PANE).name(" ").make();
	}

	@Override
	public Menu newInstance() {
		return new MenuShopEdit(this.shop);
	}
}
