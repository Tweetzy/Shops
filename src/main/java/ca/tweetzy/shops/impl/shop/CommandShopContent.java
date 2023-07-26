package ca.tweetzy.shops.impl.shop;

import ca.tweetzy.shops.api.shop.AbstractShopContent;
import ca.tweetzy.shops.api.shop.ShopContent;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;
import java.util.function.Consumer;

public final class CommandShopContent extends AbstractShopContent {

	@Getter
	private final String command;

	public CommandShopContent(@NonNull final UUID id, @NonNull final String shopId, @NonNull final String command, final double price) {
		super(id, shopId.toLowerCase(), price, 0);
		this.command = command;
	}

	@Override
	public double getBuyPrice() {
		return this.buyPrice;
	}

	@Override
	public void setBuyPrice(double price) {
		this.buyPrice = price;
	}

	@Override
	public double getSellPrice() {
		return 0;
	}

	@Override
	public void setSellPrice(double price) {
	}

	@Override
	public void store(@NonNull Consumer<ShopContent> stored) {

	}
}
