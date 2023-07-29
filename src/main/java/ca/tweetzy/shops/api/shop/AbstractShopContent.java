package ca.tweetzy.shops.api.shop;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@AllArgsConstructor
public abstract class AbstractShopContent implements ShopContent {

	protected final UUID id;
	protected final ShopContentType type;
	protected final String shopId;
	protected int minPurchaseQty;
	protected double buyPrice;
	protected double sellPrice;

	@NotNull
	@Override
	public UUID getId() {
		return this.id;
	}

	@NotNull
	@Override
	public ShopContentType getType() {
		return this.type;
	}

	@NotNull
	@Override
	public String getShopId() {
		return this.shopId;
	}

	@Override
	public int getMinimumPurchaseQty() {
		return this.minPurchaseQty;
	}

	@Override
	public void setMinimumPurchaseQty(int qty) {
		this.minPurchaseQty = qty;
	}
}
