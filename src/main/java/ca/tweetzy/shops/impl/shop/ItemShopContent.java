package ca.tweetzy.shops.impl.shop;

import ca.tweetzy.shops.api.shop.AbstractShopContent;
import ca.tweetzy.shops.api.shop.ShopContent;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

public final class ItemShopContent extends AbstractShopContent {

	@Getter
	private final ItemStack item;

	public ItemShopContent(@NonNull final UUID id, @NonNull final String shopId, @NonNull final ItemStack item, final double price) {
		super(id, shopId.toLowerCase(), price);
		this.item = item;
	}

	@Override
	public double getPrice() {
		return this.price;
	}

	@Override
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public void store(@NonNull Consumer<ShopContent> stored) {

	}
}
