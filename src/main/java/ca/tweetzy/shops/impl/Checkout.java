package ca.tweetzy.shops.impl;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.enums.ShopItemQuantityType;
import ca.tweetzy.shops.api.enums.ShopItemType;
import ca.tweetzy.shops.api.enums.TransactionType;
import ca.tweetzy.shops.api.interfaces.ICheckout;
import ca.tweetzy.shops.menu.shopcontent.MenuShopContentList;
import ca.tweetzy.shops.model.ItemInspect;
import ca.tweetzy.shops.model.manager.ShopsEconomy;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.PlayerUtil;
import ca.tweetzy.tweety.model.HookManager;
import ca.tweetzy.tweety.model.Replacer;
import ca.tweetzy.tweety.remain.Remain;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 25 2022
 * Time Created: 9:56 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public final class Checkout implements ICheckout {

	private final Shop shop;
	private final ShopItem shopItem;
	private int purchaseQty;

	@Override
	public Shop getShop() {
		return this.shop;
	}

	@Override
	public ShopItem getShopItem() {
		return this.shopItem;
	}

	@Override
	public int getPurchaseQty() {
		return this.purchaseQty;
	}

	@Override
	public void incrementQty(int amount) {
		this.purchaseQty += amount;
	}

	@Override
	public void decrementQty(int amount) {
		this.purchaseQty = Math.max(1, purchaseQty - amount);
	}

	@Override
	public void setTotalQty(int amount) {
		this.purchaseQty = amount;
	}

	@Override
	public double calculateSellPrice() {
		return this.getTaxedSaleTotal();
	}

	@Override
	public double calculateBuyPrice() {
		final double discount = this.getTaxedBuyTotal() * (this.shop.getSettings().getDiscount() / 100D);
		return this.getTaxedBuyTotal() - discount;
	}

	@Override
	public boolean executeBuy(@NonNull final Player player, final boolean omit) {
		if (!this.shopItem.canBeBought()) return true;
		if (this.shop.getSettings().isRequirePermissionToBuy() && !player.hasPermission(this.shop.getSettings().getBuyPermission())) return false;

		if (player.getInventory().firstEmpty() == -1) {
			if (!omit)
				Common.tell(player, Localization.Error.INVENTORY_FULL);
			return false;
		}

		if (this.shopItem.getQuantityType() == ShopItemQuantityType.LIMITED) {
			if (this.shopItem.getCurrentStock() <= 0) {
				new MenuShopContentList(this.shop, null).displayTo(player);
				return false;
			}

			if ((this.shopItem.getCurrentStock() - this.getPurchaseQty() <= 0)) {
				this.setTotalQty(this.shopItem.getCurrentStock());
			}
		}

		if (ShopsEconomy.hasBalance(player, this.shopItem.getCurrency(), this.calculateBuyPrice())) {
			final List<ItemStack> items = new ArrayList<>();
			for (int i = 0; i < this.getPurchaseQty() * this.shopItem.getPurchaseQuantity(); i++)
				items.add(this.shopItem.getItem().clone());

			final int userDefinedQty = this.getPurchaseQty();
			int totalQtyGave = 0;

			for (ItemStack item : items) {
				if (!PlayerUtil.addItems(player.getInventory(), item).isEmpty()) break;

				if (this.shopItem.getQuantityType() == ShopItemQuantityType.LIMITED) {
					this.shopItem.setCurrentStock(this.shopItem.getCurrentStock() - 1);
				}

				totalQtyGave++;
			}

			if (totalQtyGave != userDefinedQty) {
				this.setTotalQty(totalQtyGave / this.shopItem.getPurchaseQuantity());
			}

			// execute the permissions last
			if (this.shopItem.getType() == ShopItemType.COMMAND || this.shopItem.getType() == ShopItemType.BOTH)
				for (int i = 0; i < this.getPurchaseQty(); i++) {
					this.shopItem.getCommands().forEach(cmd -> Common.dispatchCommand(player, HookManager.replacePlaceholders(player, cmd)));
				}

			Common.tell(player, Replacer.replaceArray(
					Localization.Success.ITEM_BOUGHT,
					"qty", this.getPurchaseQty() * this.shopItem.getPurchaseQuantity(),
					"item", ItemInspect.getItemName(this.shopItem.getItem())
			));
			ShopsEconomy.withdraw(player, this.shopItem.getCurrency(), this.calculateBuyPrice());
			ShopsData.getInstance().getTransactions().add(new Transaction(
					UUID.randomUUID(),
					player.getUniqueId(),
					this.shop.getId(),
					this.shopItem.getItem().clone(),
					totalQtyGave,
					this.calculateBuyPrice(),
					TransactionType.BUY,
					System.currentTimeMillis()
			));
			return true;
		}

		Common.tell(player, Localization.Error.NO_MONEY);
		return false;
	}

	@Override
	public boolean executeBuy(@NonNull Player player) {
		return this.executeBuy(player, false);
	}

	@Override
	public boolean executeSell(@NonNull Player player, final boolean omit) {
		if (!this.shopItem.canBeSold()) return true;
		if (this.shop.getSettings().isRequirePermissionToSell() && !player.hasPermission(this.shop.getSettings().getSellPermission())) return false;
		int totalItemsSellable = Shops.getShopManager().getItemCountInPlayerInventory(player, this.shopItem.getItem().clone());

		if (totalItemsSellable == 0) {
			if (!omit)
				Common.tell(player, Localization.Error.NO_ITEMS);
			return false;
		}

		double totalSell;
		Transaction transaction;

		if (this.getPurchaseQty() * this.shopItem.getPurchaseQuantity() > totalItemsSellable) {
			final double pricePerOne = this.shopItem.getSellPrice() / this.getShopItem().getPurchaseQuantity();
			totalSell = pricePerOne * totalItemsSellable;
			totalSell = totalSell - (Settings.TAX / 100D * totalSell);

			Shops.getShopManager().removeSpecificItemQuantityFromPlayer(player, this.shopItem.getItem().clone(), totalItemsSellable);
			transaction = new Transaction(UUID.randomUUID(), player.getUniqueId(), this.shop.getId(), this.shopItem.getItem().clone(), totalItemsSellable, totalSell, TransactionType.SELL, System.currentTimeMillis());
		} else {
			totalSell = this.calculateSellPrice();
			Shops.getShopManager().removeSpecificItemQuantityFromPlayer(player, this.shopItem.getItem().clone(), this.getPurchaseQty() * this.shopItem.getPurchaseQuantity());
			transaction = new Transaction(UUID.randomUUID(), player.getUniqueId(), this.shop.getId(), this.shopItem.getItem().clone(), this.getPurchaseQty() * this.shopItem.getPurchaseQuantity(), totalSell, TransactionType.SELL, System.currentTimeMillis());
		}

		Common.tell(player, Replacer.replaceArray(
				Localization.Success.ITEM_SOLD,
				"qty", this.getPurchaseQty() * this.shopItem.getPurchaseQuantity(),
				"item", ItemInspect.getItemName(this.shopItem.getItem())
		));
		ShopsEconomy.deposit(player, this.shopItem.getCurrency(), totalSell);
		// insert transaction but don't save
		ShopsData.getInstance().getTransactions().add(transaction);
		return true;
	}

	@Override
	public boolean executeSell(@NonNull Player player) {
		return this.executeSell(player, false);
	}

	private double getTaxedBuyTotal() {
		final double pre = this.purchaseQty * this.shopItem.getBuyPrice();
		return ((Settings.TAX / 100D) * pre) + pre;
	}

	private double getTaxedSaleTotal() {
		final double pre = this.purchaseQty * this.shopItem.getSellPrice();
		return pre - ((Settings.TAX / 100D) * pre);
	}
}
