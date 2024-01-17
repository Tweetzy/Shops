package ca.tweetzy.shops.impl.shop;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.SynchronizeResult;
import ca.tweetzy.shops.api.shop.AbstractShopContent;
import ca.tweetzy.shops.api.shop.ShopContent;
import ca.tweetzy.shops.api.shop.ShopContentType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

@Getter
@Setter
public final class CommandShopContent extends AbstractShopContent {


	private ItemStack icon;
	private String command;
	private String name;
	private String desc;


	public CommandShopContent(@NonNull final UUID id, @NonNull final String shopId, @NonNull final ItemStack icon, @NonNull final String name, @NonNull final String desc, @NonNull final String command, final int minPurchaseQty, final double price) {
		super(id, ShopContentType.COMMAND, shopId.toLowerCase(), minPurchaseQty, price, 0, true, false);
		this.command = command;
		this.icon = icon;
		this.name = name;
		this.desc = desc;
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
	public boolean isAllowBuy() {
		return this.allowBuy;
	}

	@Override
	public boolean isAllowSell() {
		return this.allowSell;
	}

	@Override
	public void setAllowBuy(boolean allowBuy) {
		this.allowBuy = allowBuy;
	}

	@Override
	public void setAllowSell(boolean allowSell) {
		this.allowSell = allowSell;
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

	public static CommandShopContent blank(@NonNull final String shopId) {
		return new CommandShopContent(
				UUID.randomUUID(),
				shopId,
				CompMaterial.HEART_OF_THE_SEA.parseItem(),
				"Heal",
				"You will be healed",
				"heal %player%",
				1,
				1
		);
	}
}
