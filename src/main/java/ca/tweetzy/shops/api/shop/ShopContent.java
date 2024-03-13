package ca.tweetzy.shops.api.shop;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.utils.ItemUtil;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.Identifiable;
import ca.tweetzy.shops.api.Matchable;
import ca.tweetzy.shops.api.Storeable;
import ca.tweetzy.shops.api.Synchronize;
import ca.tweetzy.shops.api.currency.AbstractCurrency;
import ca.tweetzy.shops.settings.Settings;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface ShopContent extends Identifiable<UUID>, Matchable, Synchronize, Storeable<ShopContent> {

	@NonNull String getShopId();

	@NonNull ShopContentType getType();

	int getMinimumPurchaseQty();

	void setMinimumPurchaseQty(final int qty);

	double getBuyPrice();

	void setBuyPrice(final double price);

	double getSellPrice();

	void setSellPrice(final double price);

	boolean isAllowBuy();

	boolean isAllowSell();

	void setAllowBuy(final boolean allowBuy);

	void setAllowSell(final boolean allowSell);

	String getCurrency();

	void setCurrency(final String currency);

	ItemStack getCurrencyItem();

	void setCurrencyItem(final ItemStack currencyItem);

	default ItemStack generateDisplayItem(final ShopContentDisplayType displayType, final int purchaseQty) {
		return QuickItem.of(CompMaterial.CHEST).make();
	}

	default ItemStack generateDisplayItem(final ShopContentDisplayType displayType) {
		return generateDisplayItem(displayType, getMinimumPurchaseQty());
	}

	default boolean isCurrencyOfItem() {
		final String[] split = getCurrency().split("/");
		return split[1].equalsIgnoreCase("item");
	}

	default Shop getOwningShop() {
		return Shops.getShopManager().getById(getShopId());
	}

	default String getCurrencyDisplayName() {
		if (isCurrencyOfItem())
			return ItemUtil.getItemName(this.getCurrencyItem());

		final String[] split = getCurrency().split("/");

		final AbstractCurrency abstractCurrency = Shops.getCurrencyManager().locateCurrency(split[0], split[1]);

		if (!split[0].equalsIgnoreCase("vault") && !split[1].equalsIgnoreCase("vault") && abstractCurrency != null)
			return abstractCurrency.getDisplayName();

		return Settings.CURRENCY_VAULT_SYMBOL.getString();
	}
}
