package ca.tweetzy.shops.menu;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.enums.ShopLayout;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.SmartItem;
import ca.tweetzy.shops.menu.shopcontent.MenuShopContentList;
import ca.tweetzy.shops.model.TextureResolver;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.tweety.conversation.TitleInput;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 31 2021
 * Time Created: 8:22 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuMain extends MenuPagged<Shop> {

	private final Player thePlayer;

	private Map<Integer, Shop> manualShopSpots = null;
	private List<Button> manualShopButtons = null;

	private final Button cartButton;
	private final Button searchButton;

	public MenuMain(@NonNull final Player thePlayer) {
		super(Settings.DYNAMIC_FILL_MAIN_MENU ? Shops.getShopManager().getShops(thePlayer) : Shops.getShopManager().getEmptyPopulated());
		setTitle(Localization.MainMenu.TITLE);
		this.thePlayer = thePlayer;

		if (!Settings.DYNAMIC_FILL_MAIN_MENU) {
			this.manualShopSpots = new HashMap<>();
			this.manualShopButtons = new ArrayList<>();

			Shops.getShopManager().getShops().forEach(shop -> {
				if (shop.getDisplay().getShopLayout() == ShopLayout.MANUAL) {
					if (shop.getDisplay().getMenuPage() != -1 && shop.getDisplay().getMenuSlot() != -1) {
						this.manualShopSpots.put(shop.getDisplay().getMenuSlot(), shop);

						this.manualShopButtons.add(Button.makeSimple(ItemCreator.of(convertToItemStack(shop)), player -> new MenuShopContentList(shop, null).displayTo(player)));
					}
				}
			});
		}

		this.cartButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.Main.CART_BUTTON_MATERIAL).get())
				.name(Localization.MainMenu.CART_BUTTON_NAME)
				.lore(Localization.MainMenu.CART_BUTTON_LORE), player -> new MenuCart(player).displayTo(player));

		this.searchButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.Main.SEARCH_BUTTON_MATERIAL).get())
				.name(Localization.MainMenu.SEARCH_BUTTON_NAME)
				.lore(Localization.MainMenu.SEARCH_BUTTON_LORE), player -> new TitleInput(player, Localization.Prompt.ITEM_SEARCH_TITLE, Localization.Prompt.ITEM_SEARCH_SUBTITLE) {

			@Override
			public boolean onResult(String string) {
				if (string == null || string.length() < 3)
					return false;

				return true;
			}
		});
	}

	@Override
	protected List<Button> getButtonsToAutoRegister() {
		return this.manualShopButtons;
	}

	@Override
	protected ItemStack convertToItemStack(Shop shop) {
		if (shop.getId().equalsIgnoreCase("shops-plugin-empty-placeholder"))
			return this.backgroundItem();

		return ItemCreator
				.of(shop.getIcon().get())
				.name(shop.getDisplayName())
				.lore(shop.getDescription())
				.make();
	}

	@Override
	protected void onPageClick(Player player, Shop shop, ClickType click) {
		if (shop.getId().equalsIgnoreCase("shops-plugin-empty-placeholder")) return;
		new MenuShopContentList(shop, null).displayTo(player);
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if ((Settings.Menus.Main.SEARCH_BUTTON_SLOT == -1 && slot == getSize() - 9) || (Settings.Menus.Main.SEARCH_BUTTON_SLOT == -2 && slot == getSize() - 1) || slot == Settings.Menus.Main.SEARCH_BUTTON_SLOT)
			return this.searchButton.getItem();

		if (slot == getSize() - 9 + Settings.Menus.Main.CART_BUTTON_SLOT)
			return this.cartButton.getItem();

		if (slot == getPreviousButtonPosition() || slot == getNextButtonPosition())
			return super.getItemAt(slot);

		if (!Settings.DYNAMIC_FILL_MAIN_MENU && this.manualShopSpots != null) {
			if (this.manualShopSpots.containsKey(slot)) {
				if (this.manualShopSpots.get(slot).getDisplay().getMenuPage() == this.getCurrentPage())
					return convertToItemStack(this.manualShopSpots.get(slot));
			}
		}

		return Settings.DYNAMIC_FILL_MAIN_MENU ? super.getItemAt(slot) : backgroundItem();
	}

	@Override
	protected ItemStack backgroundItem() {
		return ItemCreator.of(TextureResolver.resolve(Settings.Menus.Main.BACKGROUND_ITEM)).name(" ").make();
	}

	@Override
	public Menu newInstance() {
		return new MenuMain(this.thePlayer);
	}
}
