package ca.tweetzy.shops.impl.shop;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.SynchronizeResult;
import ca.tweetzy.shops.api.shop.AbstractShopContent;
import ca.tweetzy.shops.api.shop.ShopContent;
import ca.tweetzy.shops.api.shop.ShopContentType;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

public final class CommandShopContent extends AbstractShopContent {

	@Getter
	private final String command;

	public CommandShopContent(@NonNull final UUID id, @NonNull final String shopId, @NonNull final String command, final int minPurchaseQty, final double price) {
		super(id, ShopContentType.COMMAND, shopId.toLowerCase(), minPurchaseQty, price, 0);
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
		Shops.getDataManager().insertServerShopContent(this, (error, created) -> {
			if (error == null)
				stored.accept(created);
		});
	}

	@Override
	public void sync(@Nullable Consumer<SynchronizeResult> syncResult) {
		Shops.getDataManager().updateServerShopContent(this, (error, updateStatus) -> {
			if (syncResult != null)
				syncResult.accept(error == null ? updateStatus ? SynchronizeResult.SUCCESS : SynchronizeResult.FAILURE : SynchronizeResult.FAILURE);
		});
	}
}
