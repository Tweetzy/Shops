package ca.tweetzy.shops.api.cart;

import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.PlayerUtil;
import ca.tweetzy.flight.utils.Replacer;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.Transaction;
import ca.tweetzy.shops.api.currency.TransactionResult;
import ca.tweetzy.shops.api.events.ShopTransactionEvent;
import ca.tweetzy.shops.api.shop.ShopContent;
import ca.tweetzy.shops.api.shop.ShopContentType;
import ca.tweetzy.shops.impl.ShopTransaction;
import ca.tweetzy.shops.impl.shop.CommandShopContent;
import ca.tweetzy.shops.impl.shop.ItemShopContent;
import ca.tweetzy.shops.model.Taxer;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface CartContent {

	ShopContent getItem();

	int getQuantity();

	void setQuantity(final int quantity);

	void addQuantity(final int quantity);

	void removeQuantity(final int quantity);

	default double getBuySubtotal() {
		return getItem().getBuyPrice() * getQuantity();
	}

	default double getSellSubtotal() {
		return getItem().getSellPrice() * getQuantity();
	}

	default double getSellSubtotal(final int actualQty) {
		return getItem().getSellPrice() * actualQty;
	}

	default double getBuySubtotal(final int actualQty) {
		return getItem().getBuyPrice() * actualQty;
	}

	default TransactionResult executeSell(@NonNull final Player player) {
		if (!(getItem() instanceof ItemShopContent itemShopContent)) {
			return TransactionResult.ERROR;
		}

		final int totalFoundInInventory = PlayerUtil.getItemCountInPlayerInventory(player, itemShopContent.getItem());
		if (totalFoundInInventory == 0)
			return TransactionResult.PLAYER_DOES_NOT_HAVE_ITEM;

		final int amountToSell = Math.min(getQuantity(), totalFoundInInventory);

		final double subTotal = getSellSubtotal(amountToSell);
		final double total = Settings.TAX_SELL.getBoolean() ? subTotal - Taxer.calculateTaxAmount(subTotal) : subTotal;

		if (getItem().isCurrencyOfItem()) {
			Shops.getCurrencyManager().deposit(player, getItem().getCurrencyItem(), (int) total);
		} else {
			final String[] currencySplit = getItem().getCurrency().split("/");
			Shops.getCurrencyManager().deposit(player, currencySplit[0], currencySplit[1], total);
//			Common.tell(player, TranslationManager.string(player, Translations.NO_MONEY));TODO show money add
		}

		// remove total qty
		PlayerUtil.removeSpecificItemQuantityFromPlayer(player, itemShopContent.getItem(), amountToSell);

		final Transaction transaction = new ShopTransaction(
				UUID.randomUUID(),
				this.getItem().getId(),
				this.getItem().getShopId(),
				this.getItem().getOwningShop().getDisplayName(),
				Transaction.TransactionType.SELL,
				this.getItem().getType(),
				player.getUniqueId(),
				player.getName(),
				this.getItem().getRawItem(),
				this.getItem().isCurrencyOfItem() ? (int) total : total,
				amountToSell,
				System.currentTimeMillis()
		);

		final ShopTransactionEvent transactionEvent = new ShopTransactionEvent(transaction);
		Shops.getInstance().getServer().getPluginManager().callEvent(transactionEvent);

		return TransactionResult.SUCCESS;
	}

	default TransactionResult executePurchase(@NonNull final Player player) {
		// check min qty first
		if (getQuantity() < getItem().getMinimumPurchaseQty()) {
			Common.tell(player, TranslationManager.string(player, Translations.CHECKOUT_NOT_MIN_QTY, "shop_item_purchase_qty", getItem().getMinimumPurchaseQty()));
			return TransactionResult.FAILED_BUY_QTY_TOO_LOW;
		}

		final double total = Taxer.getTaxedTotal(getBuySubtotal());

		if (getItem().isCurrencyOfItem()) {
			if (!Shops.getCurrencyManager().has(player, getItem().getCurrencyItem(), (int) total)) {
				Common.tell(player, TranslationManager.string(player, Translations.NO_MONEY));
				return TransactionResult.FAILED_NO_MONEY;
			} else
				Shops.getCurrencyManager().withdraw(player, getItem().getCurrencyItem(), (int) total);
		} else {
			final String[] currencySplit = getItem().getCurrency().split("/");
			if (!Shops.getCurrencyManager().has(player, currencySplit[0], currencySplit[1], total)) {
				Common.tell(player, TranslationManager.string(player, Translations.NO_MONEY));
				return TransactionResult.FAILED_NO_MONEY;
			} else
				Shops.getCurrencyManager().withdraw(player, currencySplit[0], currencySplit[1], total);
		}


		if (getItem() instanceof CommandShopContent commandShopContent)
			for (int i = 0; i < getQuantity(); i++)
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Replacer.replaceVariables(commandShopContent.getCommand(), "player", player.getName()));

		if (getItem() instanceof ItemShopContent itemShopContent)
			for (int i = 0; i < getQuantity(); i++)
				PlayerUtil.giveItem(player, itemShopContent.getItem());


		// purchase went through call transaction event
		final Transaction transaction = new ShopTransaction(
				UUID.randomUUID(),
				this.getItem().getId(),
				this.getItem().getShopId(),
				this.getItem().getOwningShop().getDisplayName(),
				Transaction.TransactionType.BUY,
				this.getItem().getType(),
				player.getUniqueId(),
				player.getName(),
				this.getItem().getRawItem(),
				this.getItem().isCurrencyOfItem() ? (int) total : total,
				this.getQuantity(),
				System.currentTimeMillis()
		);

		final ShopTransactionEvent transactionEvent = new ShopTransactionEvent(transaction);
		Bukkit.getPluginManager().callEvent(transactionEvent);

		return TransactionResult.SUCCESS;
	}
}
