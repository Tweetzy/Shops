package ca.tweetzy.shops.impl.shop;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Filterer;
import ca.tweetzy.flight.utils.ItemUtil;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.api.shop.AbstractShopContent;
import ca.tweetzy.shops.api.shop.ShopContentDisplayType;
import ca.tweetzy.shops.api.shop.ShopContentType;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.settings.Translations;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

import static ca.tweetzy.shops.model.VariableHelper.replaceVariable;

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


	@Override
	public ItemStack generateDisplayItem(ShopContentDisplayType displayType) {
		final QuickItem genItem = QuickItem.of(this.item);

		if (displayType == ShopContentDisplayType.LIVE_SHOP) {
			List<String> baseLore = TranslationManager.list(Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_BASE_LORE,
					"left_click", TranslationManager.string(Translations.MOUSE_LEFT_CLICK),
					"right_click", TranslationManager.string(Translations.MOUSE_RIGHT_CLICK)
			);

			final List<String> buyInfo = TranslationManager.list(Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_BUY_INFO, "shop_item_buy_price", this.buyPrice);// TODO add a global format option
			final List<String> sellInfo = TranslationManager.list(Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_SELL_INFO, "shop_item_sell_price", this.sellPrice);// TODO add a global format option
			final List<String> minBuyInfo = TranslationManager.list(Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_MIN_BUY_INFO, "shop_item_purchase_qty", this.minPurchaseQty);// TODO add a global format option

			replaceVariable(baseLore, "%shop_content_buy_info%", buyInfo, !this.allowBuy);
			replaceVariable(baseLore, "%shop_content_sell_info%", sellInfo, !this.allowSell);
			replaceVariable(baseLore, "%shop_content_purchase_qty_info%", minBuyInfo, !this.allowBuy);
			replaceVariable(baseLore, "%shop_content_desc_info%", null, true);

			genItem.lore(baseLore);

		} else {
			genItem.lore(TranslationManager.list(Translations.GUI_SHOP_EDIT_ITEMS_ITEM_CONTENT_LORE,
					"is_buy_enabled", TranslationManager.string(this.allowBuy ? Translations.ENABLED : Translations.DISABLED),
					"is_sell_enabled", TranslationManager.string(this.allowSell ? Translations.ENABLED : Translations.DISABLED),
					"shop_item_buy_price", this.buyPrice,
					"shop_item_sell_price", this.sellPrice,
					"shop_item_purchase_qty", this.minPurchaseQty,
					"left_click", TranslationManager.string(Translations.MOUSE_LEFT_CLICK),
					"right_click", TranslationManager.string(Translations.MOUSE_RIGHT_CLICK)));
		}

		return genItem.make();
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
