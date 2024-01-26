package ca.tweetzy.shops.api.shop;

import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.SynchronizeResult;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

@AllArgsConstructor
public abstract class AbstractShopContent implements ShopContent {

	protected final UUID id;
	protected final ShopContentType type;
	protected final String shopId;
	protected int minPurchaseQty;
	protected double buyPrice;
	protected double sellPrice;

	protected boolean allowBuy;
	protected boolean allowSell;

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

	@Override
	public void unStore(@Nullable Consumer<SynchronizeResult> syncResult) {
		Shops.getDataManager().deleteServerShopContent(this, (error, updateStatus) -> {
			if (updateStatus) {
				Shops.getShopManager().getById(this.shopId).getContent().removeIf(item -> item.getId().equals(this.id));
				Shops.getShopContentManager().remove(this);
			}

			if (syncResult != null)
				syncResult.accept(error == null ? updateStatus ? SynchronizeResult.SUCCESS : SynchronizeResult.FAILURE : SynchronizeResult.FAILURE);
		});
	}
}
