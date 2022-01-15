package ca.tweetzy.shops.menu.shopcontent;

import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.ShopItem;
import ca.tweetzy.tweety.conversation.TitleInput;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 14 2022
 * Time Created: 7:27 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuShopItemDesc extends MenuPagged<String> {

	private final Shop shop;
	private final ShopItem shopItem;

	private final Button addLineButton;
	private final Button backButton;

	public MenuShopItemDesc(@NonNull final Shop shop, @NonNull final ShopItem shopItem) {
		super(shopItem.getDescription());
		this.shop = shop;
		this.shopItem = shopItem;
		setTitle("&e" + shop.getId() + " &8> &eItem Desc");

		this.addLineButton = Button.makeSimple(ItemCreator.of(CompMaterial.SLIME_BALL, "&E&lAdd Line", "", "&eClick &7to add a new lore line"), player -> new TitleInput(player, "&eShop Item Edit", "&7Enter new desc line") {

			@Override
			public boolean onResult(String line) {
				if (line == null || line.length() < 1)
					return false;

				MenuShopItemDesc.this.shopItem.getDescription().add(line);
				newInstance().displayTo(player);
				return true;
			}
		});

		this.backButton = Button.makeSimple(ItemCreator.of(CompMaterial.IRON_DOOR).name("&eBack").lore("&eClick &7to exit/go back"), player -> new MenuAddShopItem(this.shop, this.shopItem).displayTo(player));
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == getBottomCenterSlot())
			return this.addLineButton.getItem();
		if (slot == getSize() - 9)
			return this.backButton.getItem();

		return super.getItemAt(slot);
	}

	@Override
	protected List<Button> getButtonsToAutoRegister() {
		return Arrays.asList(this.addLineButton, this.backButton);
	}

	@Override
	protected ItemStack convertToItemStack(String line) {
		return ItemCreator.of(CompMaterial.PAPER).name(line).lore("&dPress Drop &7to delete line").make();
	}

	@Override
	protected void onPageClick(Player player, String s, ClickType clickType) {
		this.shopItem.getDescription().remove(s);
		newInstance().displayTo(player);
	}

	@Override
	protected ItemStack backgroundItem() {
		return ItemCreator.of(CompMaterial.BLACK_STAINED_GLASS_PANE).name(" ").make();
	}

	@Override
	public Menu newInstance() {
		return new MenuShopItemDesc(this.shop, this.shopItem);
	}
}
