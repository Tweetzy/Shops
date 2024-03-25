package ca.tweetzy.shops.impl.shop;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.utils.Filterer;
import ca.tweetzy.flight.utils.ItemUtil;
import ca.tweetzy.shops.api.shop.AbstractShopContent;
import ca.tweetzy.shops.api.shop.ShopContentType;
import ca.tweetzy.shops.settings.Settings;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Getter
@Setter
public final class ItemShopContent extends AbstractShopContent {

	private ItemStack item;

	public ItemShopContent(@NonNull final UUID id, @NonNull final String shopId, @NonNull final ItemStack item, final int minPurchaseQty, final double buyPrice, final double sellPrice, final String currency, final ItemStack currencyItem) {
		super(id, ShopContentType.ITEM, shopId.toLowerCase(), minPurchaseQty, buyPrice, sellPrice, true, sellPrice != 0, currency, currencyItem);
		this.item = item;
	}

	public ItemShopContent(@NonNull final UUID id, @NonNull final String shopId, @NonNull final ItemStack item, final int minPurchaseQty, final double buyPrice, final double sellPrice) {
		this(id, shopId, item, minPurchaseQty, buyPrice, sellPrice, Settings.CURRENCY_DEFAULT_SELECTED.getString(), CompMaterial.AIR.parseItem());
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
		return this.sellPrice;
	}

	@Override
	public void setSellPrice(double price) {
		this.sellPrice = price;
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

	public static ItemShopContent blank(@NonNull final String shopId, @NonNull CompMaterial material) {
		return new ItemShopContent(
				UUID.randomUUID(),
				shopId,
				material.parseItem(),
				1,
				1,
				1,
				Settings.CURRENCY_DEFAULT_SELECTED.getString(),
				CompMaterial.AIR.parseItem()
		);
	}

	public static ItemShopContent blank(@NonNull final String shopId) {
		return new ItemShopContent(
				UUID.randomUUID(),
				shopId,
				CompMaterial.CHEST.parseItem(),
				1,
				1,
				1,
				Settings.CURRENCY_DEFAULT_SELECTED.getString(),
				CompMaterial.AIR.parseItem()
		);
	}


	@Override
	public boolean isMatch(String keyword) {
		return Filterer.searchByItemInfo(keyword, this.item);
	}

	@Override
	public String getName() {
		return ItemUtil.getItemName(this.item);
	}
}
