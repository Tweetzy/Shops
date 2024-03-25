package ca.tweetzy.shops.impl;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.Transaction;
import ca.tweetzy.shops.api.shop.ShopContentType;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

@AllArgsConstructor
public final class ShopTransaction implements Transaction {

	private final UUID id;
	private final UUID contentId;
	private final String shopId;
	private final String shopName;
	private final TransactionType transactionType;
	private final ShopContentType contentType;
	private final UUID playerUUID;
	private final String playerUsername;
	private final ItemStack item;
	private final double price;
	private final int quantity;
	private final long time;


	@Override
	public @NonNull UUID getId() {
		return this.id;
	}

	@Override
	public UUID getKnownContentId() {
		return this.contentId;
	}

	@Override
	public String getKnownShopId() {
		return this.shopId;
	}

	@Override
	public String getKnownShopName() {
		return this.shopName;
	}

	@Override
	public TransactionType getType() {
		return this.transactionType;
	}

	@Override
	public ShopContentType getContentType() {
		return this.contentType;
	}

	@Override
	public UUID getUserUUID() {
		return this.playerUUID;
	}

	@Override
	public String getUserLastKnownName() {
		return this.playerUsername;
	}

	@Override
	public ItemStack getItem() {
		return this.item;
	}

	@Override
	public double getFinalPrice() {
		return this.price;
	}

	@Override
	public int getTransactionQuantity() {
		return this.quantity;
	}

	@Override
	public long getTimeCreated() {
		return this.time;
	}

	@Override
	public long getLastUpdated() {
		return this.time;
	}

	@Override
	public void store(@NonNull Consumer<Transaction> stored) {
		Shops.getDataManager().insertTransaction(this, (error, created) -> {
			if (error == null)
				stored.accept(created);
		});
	}
}
