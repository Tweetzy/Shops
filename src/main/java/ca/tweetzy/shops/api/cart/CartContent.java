package ca.tweetzy.shops.api.cart;

import ca.tweetzy.flight.utils.PlayerUtil;
import ca.tweetzy.flight.utils.Replacer;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.currency.AbstractCurrency;
import ca.tweetzy.shops.api.currency.TransactionResult;
import ca.tweetzy.shops.api.shop.ShopContent;
import ca.tweetzy.shops.impl.manager.CurrencyManager;
import ca.tweetzy.shops.impl.shop.CommandShopContent;
import ca.tweetzy.shops.impl.shop.ItemShopContent;
import ca.tweetzy.shops.model.Taxer;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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

	default TransactionResult executePurchase(@NonNull final Player player) {
		final double total = Taxer.getTaxedTotal(getBuySubtotal());

		if (getItem().isCurrencyOfItem()) {
			if (!Shops.getCurrencyManager().has(player, getItem().getCurrencyItem(), (int) total))
				return TransactionResult.FAILED_NO_MONEY;
			else
				Shops.getCurrencyManager().withdraw(player, getItem().getCurrencyItem(), (int) total);
		} else {
			final String[] currencySplit = getItem().getCurrency().split("/");
			if (!Shops.getCurrencyManager().has(player, currencySplit[0], currencySplit[1], total))
				return TransactionResult.FAILED_NO_MONEY;
			else
				Shops.getCurrencyManager().withdraw(player, currencySplit[0], currencySplit[1], total);
		}


		if (getItem() instanceof CommandShopContent commandShopContent)
			for (int i= 0 ; i < getQuantity(); i++)
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Replacer.replaceVariables(commandShopContent.getCommand(), "player", player.getName()));

		if (getItem() instanceof ItemShopContent itemShopContent)
			for (int i= 0 ; i < getQuantity(); i++)
				PlayerUtil.giveItem(player, itemShopContent.getItem());

		return TransactionResult.SUCCESS;
	}
}
