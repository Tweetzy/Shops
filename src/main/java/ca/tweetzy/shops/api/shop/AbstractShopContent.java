package ca.tweetzy.shops.api.shop;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.settings.TranslationEntry;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.SynchronizeResult;
import ca.tweetzy.shops.impl.shop.CommandShopContent;
import ca.tweetzy.shops.impl.shop.ItemShopContent;
import ca.tweetzy.shops.model.NumberHelper;
import ca.tweetzy.shops.settings.Translations;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static ca.tweetzy.shops.model.VariableHelper.replaceVariable;

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

	protected String currency;
	protected ItemStack currencyItem;

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

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public ItemStack getCurrencyItem() {
		return this.currencyItem;
	}

	public void setCurrencyItem(ItemStack currencyItem) {
		this.currencyItem = currencyItem;
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

	@Override
	public ItemStack generateDisplayItem(ShopContentDisplayType displayType, int purchaseQty) {
		QuickItem genItem = QuickItem.of(CompMaterial.PAPER);

		if (this instanceof final ItemShopContent itemShopContent)
			genItem = QuickItem.of(itemShopContent.getItem());

		if (this instanceof final CommandShopContent commandShopContent) {
			genItem = QuickItem.of(commandShopContent.getIcon());
			genItem.name(commandShopContent.getName());
		}

		String formattedPriceBuy = isCurrencyOfItem() ? (int) this.buyPrice + " " + getCurrencyDisplayName() : NumberHelper.format(this.buyPrice);
		String formattedPriceBuyTotal = isCurrencyOfItem() ? (int) this.buyPrice * purchaseQty + " " + getCurrencyDisplayName() : NumberHelper.format(this.buyPrice * purchaseQty);

		String formattedPriceSell = isCurrencyOfItem() ? (int) this.sellPrice + " " + getCurrencyDisplayName() : NumberHelper.format(this.sellPrice);
		String formattedPriceSellTotal = isCurrencyOfItem() ? (int) this.sellPrice * purchaseQty + " " + getCurrencyDisplayName() : NumberHelper.format(this.sellPrice * purchaseQty);

		if (displayType == ShopContentDisplayType.SHOP_EDIT) {
			genItem.lore(TranslationManager.list(Translations.GUI_SHOP_EDIT_ITEMS_ITEM_CONTENT_LORE,
					"is_buy_enabled", TranslationManager.string(this.allowBuy ? Translations.ENABLED : Translations.DISABLED),
					"is_sell_enabled", TranslationManager.string(this.allowSell ? Translations.ENABLED : Translations.DISABLED),
					"shop_item_buy_price", this.buyPrice,
					"shop_item_sell_price", this.sellPrice,
					"shop_item_purchase_qty", this.minPurchaseQty,
					"left_click", TranslationManager.string(Translations.MOUSE_LEFT_CLICK),
					"right_click", TranslationManager.string(Translations.MOUSE_RIGHT_CLICK)));
		} else {
			genItem.amount(purchaseQty);

			final TranslationEntry baseEntry = switch (displayType) {
				case CART -> Translations.GUI_CART_ITEMS_ITEM_CONTENT_BASE_LORE;
				case CHECKOUT -> Translations.GUI_CHECKOUT_ITEMS_ITEM_CONTENT_BASE_LORE;
				default -> Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_BASE_LORE;
			};

			List<String> baseLore = TranslationManager.list(baseEntry,
					"left_click", TranslationManager.string(Translations.MOUSE_LEFT_CLICK),
					"right_click", TranslationManager.string(Translations.MOUSE_RIGHT_CLICK)
			);

			final List<String> buyInfo = switch (displayType) {
				case CART -> TranslationManager.list(Translations.GUI_CART_ITEMS_ITEM_CONTENT_BUY_INFO, "shop_item_buy_price", formattedPriceBuy);
				case CHECKOUT -> TranslationManager.list(Translations.GUI_CHECKOUT_ITEMS_ITEM_CONTENT_BUY_INFO, "shop_item_buy_price", formattedPriceBuy);
				default -> TranslationManager.list(Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_BUY_INFO, "shop_item_buy_price", formattedPriceBuy);
			};

			final List<String> sellInfo = switch (displayType) {
				case CART -> TranslationManager.list(Translations.GUI_CART_ITEMS_ITEM_CONTENT_SELL_INFO, "shop_item_sell_price", formattedPriceSell);
				case CHECKOUT -> TranslationManager.list(Translations.GUI_CHECKOUT_ITEMS_ITEM_CONTENT_SELL_INFO, "shop_item_sell_price", formattedPriceSell);
				default -> TranslationManager.list(Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_SELL_INFO, "shop_item_sell_price",formattedPriceSell);
			};

			final List<String> minBuyInfo = switch (displayType) {
				case CART -> TranslationManager.list(Translations.GUI_CART_ITEMS_ITEM_CONTENT_SELECTED_QTY_INFO, "shop_item_purchase_qty", purchaseQty);
				case CHECKOUT -> TranslationManager.list(Translations.GUI_CHECKOUT_ITEMS_ITEM_CONTENT_SELECTED_QTY_INFO, "shop_item_purchase_qty", purchaseQty);
				default -> TranslationManager.list(Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_MIN_BUY_INFO, "shop_item_purchase_qty", this.minPurchaseQty);
			};

			final List<String> buyInfoTotal = switch (displayType) {
				case CART ->
						TranslationManager.list(Translations.GUI_CART_ITEMS_ITEM_CONTENT_BUY_INFO_TOTAL, "shop_item_buy_price_total", formattedPriceBuyTotal, "shop_item_purchase_qty", purchaseQty);
				case CHECKOUT ->
						TranslationManager.list(Translations.GUI_CHECKOUT_ITEMS_ITEM_CONTENT_BUY_INFO_TOTAL, "shop_item_buy_price_total", formattedPriceBuyTotal, "shop_item_purchase_qty", purchaseQty);
				default -> TranslationManager.list(Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_MIN_BUY_INFO, "shop_item_purchase_qty", this.minPurchaseQty);
			};

			final List<String> sellInfoTotal = switch (displayType) {
				case CART ->
						TranslationManager.list(Translations.GUI_CART_ITEMS_ITEM_CONTENT_SELL_INFO_TOTAL, "shop_item_sell_price_total", formattedPriceSellTotal, "shop_item_purchase_qty", purchaseQty);
				case CHECKOUT ->
						TranslationManager.list(Translations.GUI_CHECKOUT_ITEMS_ITEM_CONTENT_SELL_INFO_TOTAL, "shop_item_sell_price_total", formattedPriceSellTotal, "shop_item_purchase_qty", purchaseQty);
				default -> TranslationManager.list(Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_MIN_BUY_INFO, "shop_item_purchase_qty", this.minPurchaseQty);
			};

			List<String> descInfo = null;
			if (this instanceof final CommandShopContent commandShopContent) {
				descInfo = switch (displayType) {
					case CART -> TranslationManager.list(Translations.GUI_CART_ITEMS_ITEM_CONTENT_DESC_INFO, "shop_item_description", commandShopContent.getDesc());
					case CHECKOUT -> TranslationManager.list(Translations.GUI_CHECKOUT_ITEMS_ITEM_CONTENT_DESC_INFO, "shop_item_description", commandShopContent.getDesc());
					default -> TranslationManager.list(Translations.GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_DESC_INFO, "shop_item_description", commandShopContent.getDesc());
				};
			}

			if (displayType == ShopContentDisplayType.LIVE_SHOP) {
				replaceVariable(baseLore, "%shop_content_buy_info%", buyInfo, !this.allowBuy);
				replaceVariable(baseLore, "%shop_content_sell_info%", sellInfo, !this.allowSell);
				replaceVariable(baseLore, "%shop_content_purchase_qty_info%", minBuyInfo, !this.allowBuy);
				replaceVariable(baseLore, "%shop_content_desc_info%", descInfo, descInfo == null);
				genItem.lore(baseLore);
			}

			if (displayType == ShopContentDisplayType.CART || displayType == ShopContentDisplayType.CHECKOUT) {
				replaceVariable(baseLore, "%shop_content_buy_info%", buyInfo, !this.allowBuy);
				replaceVariable(baseLore, "%shop_content_sell_info%", sellInfo, !this.allowSell);
				replaceVariable(baseLore, "%shop_content_purchase_qty_info%", minBuyInfo, !this.allowBuy);

				replaceVariable(baseLore, "%shop_content_buy_info_total%", buyInfoTotal, !this.allowBuy);
				replaceVariable(baseLore, "%shop_content_sell_info_total%", sellInfoTotal, !this.allowSell);

				replaceVariable(baseLore, "%shop_content_desc_info%", descInfo, descInfo == null);

				genItem.lore(baseLore);
			}

		}

		return genItem.make();
	}
}
