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


//	@Override
//	public ItemStack generateDisplayItem(ShopContentDisplayType displayType, int purchaseQty) {
//		final QuickItem genItem = QuickItem.of(this.item);
//
//		if (displayType == ShopContentDisplayType.SHOP_EDIT) {
//			genItem.lore(TranslationManager.list(Translations.GUI_SHOP_EDIT_ITEMS_ITEM_CONTENT_LORE,
//					"is_buy_enabled", TranslationManager.string(this.allowBuy ? Translations.ENABLED : Translations.DISABLED),
//					"is_sell_enabled", TranslationManager.string(this.allowSell ? Translations.ENABLED : Translations.DISABLED),
//					"shop_item_buy_price", this.buyPrice,
//					"shop_item_sell_price", this.sellPrice,
//					"shop_item_purchase_qty", this.minPurchaseQty,
//					"left_click", TranslationManager.string(Translations.MOUSE_LEFT_CLICK),
//					"right_click", TranslationManager.string(Translations.MOUSE_RIGHT_CLICK)));
//		} else {
//			genItem.amount(purchaseQty);
//
//			final TranslationEntry baseEntry = switch (displayType) {
//				case CART -> Translations.GUI_CART_ITEMS_ITEM_CONTENT_BASE_LORE;
//				case CHECKOUT -> Translations.GUI_CHECKOUT_ITEMS_ITEM_CONTENT_BASE_LORE;
//				default -> Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_BASE_LORE;
//			};
//
//			List<String> baseLore = TranslationManager.list(baseEntry,
//					"left_click", TranslationManager.string(Translations.MOUSE_LEFT_CLICK),
//					"right_click", TranslationManager.string(Translations.MOUSE_RIGHT_CLICK)
//			);
//
//			final List<String> buyInfo = switch (displayType) {
//				case CART -> TranslationManager.list(Translations.GUI_CART_ITEMS_ITEM_CONTENT_BUY_INFO, "shop_item_buy_price", this.buyPrice);
//				case CHECKOUT -> TranslationManager.list(Translations.GUI_CHECKOUT_ITEMS_ITEM_CONTENT_BUY_INFO, "shop_item_buy_price", this.buyPrice);
//				default -> TranslationManager.list(Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_BUY_INFO, "shop_item_buy_price", this.buyPrice);
//			};
//
//			final List<String> sellInfo = switch (displayType) {
//				case CART -> TranslationManager.list(Translations.GUI_CART_ITEMS_ITEM_CONTENT_SELL_INFO, "shop_item_sell_price", this.sellPrice);
//				case CHECKOUT -> TranslationManager.list(Translations.GUI_CHECKOUT_ITEMS_ITEM_CONTENT_SELL_INFO, "shop_item_sell_price", this.sellPrice);
//				default -> TranslationManager.list(Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_SELL_INFO, "shop_item_sell_price", this.sellPrice);
//			};
//
//			final List<String> minBuyInfo = switch (displayType) {
//				case CART -> TranslationManager.list(Translations.GUI_CART_ITEMS_ITEM_CONTENT_SELECTED_QTY_INFO, "shop_item_purchase_qty", purchaseQty);
//				case CHECKOUT -> TranslationManager.list(Translations.GUI_CHECKOUT_ITEMS_ITEM_CONTENT_SELECTED_QTY_INFO, "shop_item_purchase_qty", purchaseQty);
//				default -> TranslationManager.list(Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_MIN_BUY_INFO, "shop_item_purchase_qty", this.minPurchaseQty);
//			};
//
//			final List<String> buyInfoTotal = switch (displayType) {
//				case CART -> TranslationManager.list(Translations.GUI_CART_ITEMS_ITEM_CONTENT_BUY_INFO_TOTAL, "shop_item_buy_price_total", this.buyPrice * purchaseQty, "shop_item_purchase_qty", purchaseQty);
//				case CHECKOUT -> TranslationManager.list(Translations.GUI_CHECKOUT_ITEMS_ITEM_CONTENT_BUY_INFO_TOTAL, "shop_item_buy_price_total", this.buyPrice * purchaseQty, "shop_item_purchase_qty", purchaseQty);
//				default -> TranslationManager.list(Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_MIN_BUY_INFO, "shop_item_purchase_qty", this.minPurchaseQty);
//			};
//
//			final List<String> sellInfoTotal = switch (displayType) {
//				case CART -> TranslationManager.list(Translations.GUI_CART_ITEMS_ITEM_CONTENT_SELL_INFO_TOTAL, "shop_item_sell_price_total", this.sellPrice * purchaseQty, "shop_item_purchase_qty", purchaseQty);
//				case CHECKOUT -> TranslationManager.list(Translations.GUI_CHECKOUT_ITEMS_ITEM_CONTENT_SELL_INFO_TOTAL, "shop_item_sell_price_total", this.sellPrice * purchaseQty, "shop_item_purchase_qty", purchaseQty);
//				default -> TranslationManager.list(Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_MIN_BUY_INFO, "shop_item_purchase_qty", this.minPurchaseQty);
//			};
//
//			if (displayType == ShopContentDisplayType.LIVE_SHOP) {
//				replaceVariable(baseLore, "%shop_content_buy_info%", buyInfo, !this.allowBuy);
//				replaceVariable(baseLore, "%shop_content_sell_info%", sellInfo, !this.allowSell);
//				replaceVariable(baseLore, "%shop_content_purchase_qty_info%", minBuyInfo, !this.allowBuy);
//				replaceVariable(baseLore, "%shop_content_desc_info%", null, true);
//				genItem.lore(baseLore);
//			}
//
//			if (displayType == ShopContentDisplayType.CART || displayType == ShopContentDisplayType.CHECKOUT) {
//				replaceVariable(baseLore, "%shop_content_buy_info%", buyInfo, !this.allowBuy);
//				replaceVariable(baseLore, "%shop_content_sell_info%", sellInfo, !this.allowSell);
//				replaceVariable(baseLore, "%shop_content_purchase_qty_info%", minBuyInfo, !this.allowBuy);
//
//				replaceVariable(baseLore, "%shop_content_buy_info_total%", buyInfoTotal, !this.allowBuy);
//				replaceVariable(baseLore, "%shop_content_sell_info_total%", sellInfoTotal, !this.allowSell);
//
//				replaceVariable(baseLore, "%shop_content_desc_info%", null, true);
//
//				genItem.lore(baseLore);
//			}
//
//		}
//
//		return genItem.make();
//	}

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
