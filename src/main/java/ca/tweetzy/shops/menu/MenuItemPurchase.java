package ca.tweetzy.shops.menu;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.enums.ShopItemQuantityType;
import ca.tweetzy.shops.api.enums.ShopItemType;
import ca.tweetzy.shops.api.enums.ShopState;
import ca.tweetzy.shops.impl.Checkout;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.ShopItem;
import ca.tweetzy.shops.impl.SmartItem;
import ca.tweetzy.shops.menu.shopcontent.MenuShopContentList;
import ca.tweetzy.shops.model.manager.ShopsEconomy;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.PlayerUtil;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.model.HookManager;
import ca.tweetzy.tweety.model.Replacer;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 25 2022
 * Time Created: 9:13 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuItemPurchase extends Menu {

	private final Shop shop;
	private final ShopItem shopItem;
	private final Checkout checkout;

	private final Button shopItemButton;

	private final Button decreaseOneButton;
	private final Button decreaseTwoButton;
	private final Button decreaseThreeButton;

	private final Button increaseOneButton;
	private final Button increaseTwoButton;
	private final Button increaseThreeButton;

	private final Button infoButton;
	private final Button cartButton;
	private final Button sellButton;
	private final Button buyButton;

	public MenuItemPurchase(@NonNull final Shop shop, @NonNull final ShopItem shopItem, @NonNull final Checkout checkout) {
		this.shop = shop;
		this.shopItem = shopItem;
		this.checkout = checkout;
		setTitle(Localization.BuyItemMenu.TITLE.replace("{shop_displayname}", shop.getDisplayName()));
		setSize(9 * 6);

		this.shopItemButton = Button.makeDummy(this.shopItem.getItem());

		this.decreaseOneButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.BuyItem.DECR_ONE_BUTTON_MATERIAL).get())
				.name(Localization.BuyItemMenu.DECR_ONE_NAME.replace("{decrement_one}", String.valueOf(Settings.Menus.BuyItem.DECR_ONE)))
				.lore(Replacer.replaceArray(Localization.BuyItemMenu.DECR_ONE_LORE, "decrement_one", Settings.Menus.BuyItem.DECR_ONE)), player -> {

			this.checkout.decrementQty(Settings.Menus.BuyItem.DECR_ONE);
			restartMenu();
		});

		this.decreaseTwoButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.BuyItem.DECR_TWO_BUTTON_MATERIAL).get())
				.name(Localization.BuyItemMenu.DECR_TWO_NAME.replace("{decrement_two}", String.valueOf(Settings.Menus.BuyItem.DECR_TWO)))
				.lore(Replacer.replaceArray(Localization.BuyItemMenu.DECR_TWO_LORE, "decrement_two", Settings.Menus.BuyItem.DECR_TWO)), player -> {

			this.checkout.decrementQty(Settings.Menus.BuyItem.DECR_TWO);
			restartMenu();
		});

		this.decreaseThreeButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.BuyItem.DECR_THREE_BUTTON_MATERIAL).get())
				.name(Localization.BuyItemMenu.DECR_THREE_NAME.replace("{decrement_three}", String.valueOf(Settings.Menus.BuyItem.DECR_THREE)))
				.lore(Replacer.replaceArray(Localization.BuyItemMenu.DECR_THREE_LORE, "decrement_three", Settings.Menus.BuyItem.DECR_THREE)), player -> {

			this.checkout.decrementQty(Settings.Menus.BuyItem.DECR_THREE);
			restartMenu();
		});

		this.increaseOneButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.BuyItem.INC_ONE_BUTTON_MATERIAL).get())
				.name(Localization.BuyItemMenu.INC_ONE_NAME.replace("{increment_one}", String.valueOf(Settings.Menus.BuyItem.INC_ONE)))
				.lore(Replacer.replaceArray(Localization.BuyItemMenu.INC_ONE_LORE, "increment_one", Settings.Menus.BuyItem.INC_ONE)), player -> {

			this.checkout.incrementQty(Settings.Menus.BuyItem.INC_ONE);
			restartMenu();
		});

		this.increaseTwoButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.BuyItem.INC_TWO_BUTTON_MATERIAL).get())
				.name(Localization.BuyItemMenu.INC_TWO_NAME.replace("{increment_two}", String.valueOf(Settings.Menus.BuyItem.INC_TWO)))
				.lore(Replacer.replaceArray(Localization.BuyItemMenu.INC_TWO_LORE, "increment_two", Settings.Menus.BuyItem.INC_TWO)), player -> {

			this.checkout.incrementQty(Settings.Menus.BuyItem.INC_TWO);
			restartMenu();
		});

		this.increaseThreeButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.BuyItem.INC_THREE_BUTTON_MATERIAL).get())
				.name(Localization.BuyItemMenu.INC_THREE_NAME.replace("{increment_three}", String.valueOf(Settings.Menus.BuyItem.INC_THREE)))
				.lore(Replacer.replaceArray(Localization.BuyItemMenu.INC_THREE_LORE, "increment_three", Settings.Menus.BuyItem.INC_THREE)), player -> {

			this.checkout.incrementQty(Settings.Menus.BuyItem.INC_THREE);
			restartMenu();
		});

		this.cartButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.BuyItem.CART_BUTTON_MATERIAL).get())
				.name(Localization.BuyItemMenu.CART_NAME)
				.lore(Localization.BuyItemMenu.CART_LORE), player -> {

		});

		this.sellButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.BuyItem.SELL_BUTTON_MATERIAL).get())
				.name(Localization.BuyItemMenu.SELL_NAME)
				.lore(Localization.BuyItemMenu.SELL_LORE), player -> {

			if (this.shop.getSettings().isRequirePermissionToSell() && !player.hasPermission(this.shop.getSettings().getSellPermission())) return;
			int totalItemsSellable = Shops.getShopManager().getItemCountInPlayerInventory(player, this.shopItem.getItem().clone());

			if (totalItemsSellable == 0) {
				tell(Localization.Error.NO_ITEMS);
				return;
			}

			double totalSell = 0D;

			if (this.checkout.getPurchaseQty() * this.shopItem.getPurchaseQuantity() > totalItemsSellable) {
				final double pricePerOne = this.shopItem.getSellPrice() / this.checkout.getShopItem().getPurchaseQuantity();
				totalSell = pricePerOne * totalItemsSellable;
				totalSell = totalSell - (Settings.TAX / 100D * totalSell);

				Shops.getShopManager().removeSpecificItemQuantityFromPlayer(player, this.shopItem.getItem().clone(), totalItemsSellable);
			} else {
				totalSell = this.checkout.calculateSellPrice();
				Shops.getShopManager().removeSpecificItemQuantityFromPlayer(player, this.shopItem.getItem().clone(), this.checkout.getPurchaseQty() * this.shopItem.getPurchaseQuantity());
			}

			ShopsEconomy.deposit(player, this.shopItem.getCurrency(), totalSell);
		});

		this.buyButton = Button.makeSimple(ItemCreator
				.of(new SmartItem(Settings.Menus.BuyItem.BUY_BUTTON_MATERIAL).get())
				.name(Localization.BuyItemMenu.BUY_NAME)
				.lore(Localization.BuyItemMenu.BUY_LORE), player -> {

			if (this.shop.getSettings().isRequirePermissionToBuy() && !player.hasPermission(this.shop.getSettings().getBuyPermission())) return;

			if (player.getInventory().firstEmpty() == -1) {
				tell(Localization.Error.INVENTORY_FULL);
				return;
			}

			if (this.shopItem.getQuantityType() == ShopItemQuantityType.LIMITED) {
				if (this.shopItem.getCurrentStock() <= 0) {
					new MenuShopContentList(this.shop, null).displayTo(player);
					return;
				}

				if ((this.shopItem.getCurrentStock() - this.checkout.getPurchaseQty() <= 0)) {
					this.checkout.setTotalQty(this.shopItem.getCurrentStock());
				}
			}

			if (ShopsEconomy.hasBalance(player, this.shopItem.getCurrency(), this.checkout.calculateBuyPrice())) {
				final List<ItemStack> items = new ArrayList<>();
				for (int i = 0; i < this.checkout.getPurchaseQty() * this.shopItem.getPurchaseQuantity(); i++)
					items.add(this.shopItem.getItem().clone());

				final int userDefinedQty = this.checkout.getPurchaseQty();
				int totalQtyGave = 0;

				for (ItemStack item : items) {
					if (!PlayerUtil.addItems(player.getInventory(), item).isEmpty()) break;

					if (this.shopItem.getQuantityType() == ShopItemQuantityType.LIMITED) {
						this.shopItem.setCurrentStock(this.shopItem.getCurrentStock() - 1);
					}

					totalQtyGave++;
				}

				if (totalQtyGave != userDefinedQty) {
					this.checkout.setTotalQty(totalQtyGave / this.shopItem.getPurchaseQuantity());
				}

				// execute the permissions last
				if (this.shopItem.getType() == ShopItemType.COMMAND || this.shopItem.getType() == ShopItemType.BOTH)
				for (int i = 0; i < this.checkout.getPurchaseQty(); i++) {
					this.shopItem.getCommands().forEach(cmd -> Common.dispatchCommand(player, HookManager.replacePlaceholders(player, cmd)));
				}

				ShopsEconomy.withdraw(player, this.shopItem.getCurrency(), this.checkout.calculateBuyPrice());
				return;
			}

			tell(Localization.Error.NO_MONEY);
		});

		this.infoButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
			}

			@Override
			public ItemStack getItem() {
				final List<String> lore = new ArrayList<>(Localization.BuyItemMenu.INFO_LORE);

				if (!MenuItemPurchase.this.shopItem.canBeBought() || MenuItemPurchase.this.shop.getSettings().getShopState() == ShopState.SELL_ONLY) {
					lore.remove("{buy_cost}");
				}

				if (!MenuItemPurchase.this.shopItem.canBeSold() || MenuItemPurchase.this.shop.getSettings().getShopState() == ShopState.BUY_ONLY) {
					lore.remove("{sell_cost}");
				}

				return ItemCreator
						.of(new SmartItem(Settings.Menus.BuyItem.INFO_BUTTON_MATERIAL).get())
						.name(Localization.BuyItemMenu.INFO_NAME)
						.lore(Replacer.replaceArray(lore,
								"stack_size", MenuItemPurchase.this.shopItem.getPurchaseQuantity(),
								"purchase_qty", MenuItemPurchase.this.checkout.getPurchaseQty(),
								"item_currency", MenuItemPurchase.this.shopItem.getCurrency().getName().equalsIgnoreCase("Vault") ? "$" : MenuItemPurchase.this.shopItem.getCurrency().getName(),
								"price_discount", MenuItemPurchase.this.shop.getSettings().getDiscount(),
								"buy_cost", Localization.BuyItemMenu.InfoLores.BUY_COST.replace("{purchase_cost}", String.format(Settings.NUMBER_FORMAT, MenuItemPurchase.this.checkout.calculateBuyPrice())),
								"sell_cost", Localization.BuyItemMenu.InfoLores.SELL_COST.replace("{sells_for}", String.format(Settings.NUMBER_FORMAT, MenuItemPurchase.this.checkout.calculateSellPrice()))
						))
						.make();
			}
		};
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == 9 + 4)
			return this.shopItemButton.getItem();

		if (slot == Settings.Menus.BuyItem.DECR_ONE_BUTTON_SLOT)
			return this.decreaseOneButton.getItem();

		if (slot == Settings.Menus.BuyItem.DECR_TWO_BUTTON_SLOT)
			return this.decreaseTwoButton.getItem();

		if (slot == Settings.Menus.BuyItem.DECR_THREE_BUTTON_SLOT)
			return this.decreaseThreeButton.getItem();

		if (slot == Settings.Menus.BuyItem.INC_ONE_BUTTON_SLOT)
			return this.increaseOneButton.getItem();

		if (slot == Settings.Menus.BuyItem.INC_TWO_BUTTON_SLOT)
			return this.increaseTwoButton.getItem();

		if (slot == Settings.Menus.BuyItem.INC_THREE_BUTTON_SLOT)
			return this.increaseThreeButton.getItem();

		if (slot == Settings.Menus.BuyItem.INFO_BUTTON_SLOT)
			return this.infoButton.getItem();

		if (slot == Settings.Menus.BuyItem.BUY_BUTTON_SLOT && this.shopItem.canBeBought() && this.shop.getSettings().getShopState() != ShopState.SELL_ONLY)
			return this.buyButton.getItem();

		if (slot == Settings.Menus.BuyItem.SELL_BUTTON_SLOT && this.shopItem.canBeSold() && this.shop.getSettings().getShopState() != ShopState.BUY_ONLY)
			return this.sellButton.getItem();

		if (slot == Settings.Menus.BuyItem.CART_BUTTON_SLOT)
			return this.cartButton.getItem();

		return ItemCreator.of(new SmartItem(Settings.Menus.BuyItem.BACKGROUND_ITEM).get()).name(" ").make();
	}

	@Override
	public Menu newInstance() {
		return new MenuItemPurchase(this.shop, this.shopItem, this.checkout);
	}
}
