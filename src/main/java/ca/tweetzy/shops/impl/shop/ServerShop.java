package ca.tweetzy.shops.impl.shop;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.SynchronizeResult;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.api.shop.ShopContent;
import ca.tweetzy.shops.api.shop.ShopOptions;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@AllArgsConstructor
public final class ServerShop implements Shop {

	private final String id;
	private String displayName;
	private List<String> description;
	private ShopOptions settings;
	private List<ShopContent> content;
	private final long createdAt;
	private long updatedAt;

	public ServerShop(@NonNull final String id) {
		this(id.toLowerCase(), id, List.of("&7Default Shop Description"), new ShopSettings(
				CompMaterial.CHEST.parseItem(),
				new ShopLayout(),
				true, false, false, "shops.access." + id.toLowerCase(), id.toLowerCase()
		), new ArrayList<>(), System.currentTimeMillis(), System.currentTimeMillis());
	}

	@Override
	public @NonNull String getId() {
		return this.id.toLowerCase();
	}

	@Override
	public @NonNull String getDisplayName() {
		return this.displayName;
	}

	@Override
	public void setDisplayName(@NonNull String displayName) {
		this.displayName = displayName;
	}

	@Override
	public @NonNull List<String> getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(@NonNull List<String> description) {
		this.description = description;
	}

	@Override
	public ShopOptions getShopOptions() {
		return this.settings;
	}

	@Override
	public List<ShopContent> getContent() {
		return this.content;
	}

	@Override
	public long getTimeCreated() {
		return this.createdAt;
	}

	@Override
	public long getLastUpdated() {
		return this.updatedAt;
	}

	@Override
	public void store(@NonNull Consumer<Shop> stored) {
		Shops.getDataManager().insertServerShop(this, (error, created) -> {
			if (error == null)
				stored.accept(created);
		});
	}

	@Override
	public void sync(@Nullable Consumer<SynchronizeResult> syncResult) {
		Shops.getDataManager().updateServerShop(this, (error, updateStatus) -> {
			if (syncResult != null)
				syncResult.accept(error == null ? updateStatus ? SynchronizeResult.SUCCESS : SynchronizeResult.FAILURE : SynchronizeResult.FAILURE);
		});
	}
}