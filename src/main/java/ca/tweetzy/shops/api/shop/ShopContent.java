package ca.tweetzy.shops.api.shop;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.api.Identifiable;
import ca.tweetzy.shops.api.Storeable;
import ca.tweetzy.shops.api.Synchronize;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface ShopContent extends Identifiable<UUID>, Synchronize, Storeable<ShopContent> {

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

	boolean isMatch(final String keyword);
	String getName();

	default ItemStack generateDisplayItem(final ShopContentDisplayType displayType) {
		return QuickItem.of(CompMaterial.CHEST).make();
	}
}
