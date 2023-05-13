package ca.tweetzy.shops.api.shop;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@AllArgsConstructor
public abstract class AbstractShopContent implements ShopContent {

	protected final UUID id;
	protected final String shopId;
	protected double price;

	@NotNull
	@Override
	public UUID getId() {
		return this.id;
	}

	@NotNull
	@Override
	public String getShopId() {
		return this.shopId;
	}
}
