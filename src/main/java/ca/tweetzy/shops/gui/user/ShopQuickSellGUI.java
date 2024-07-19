package ca.tweetzy.shops.gui.user;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.gui.helper.InventoryBorder;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.PlayerUtil;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.Transaction;
import ca.tweetzy.shops.api.cart.CartContent;
import ca.tweetzy.shops.api.currency.TransactionResult;
import ca.tweetzy.shops.api.events.ShopTransactionEvent;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.gui.ShopsBaseGUI;
import ca.tweetzy.shops.impl.ShopTransaction;
import ca.tweetzy.shops.impl.cart.CartItem;
import ca.tweetzy.shops.impl.shop.ItemShopContent;
import ca.tweetzy.shops.model.ItemParser;
import ca.tweetzy.shops.model.NumberHelper;
import ca.tweetzy.shops.model.Taxer;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

public final class ShopQuickSellGUI extends ShopsBaseGUI {

	private final Shop shop;

	public ShopQuickSellGUI(@NonNull Player player, @NonNull final Shop shop) {
		super(null, player, TranslationManager.string(Translations.GUI_SHOP_QUICK_SELL_TITLE, "shop_name", shop.getDisplayName()));
		this.shop = shop;

		setAcceptsItems(true);
		InventoryBorder.getInsideBorders(6).forEach(this::setUnlocked);

		setOnClose(close -> attemptSell(close.player));

		draw();
	}

	@Override
	protected void draw() {

		Settings.GUI_SHOPS_QUICK_SELL_DECORATIONS.getStringList().forEach(itemLine -> {
			final Map<String, List<String>> keyed = ItemParser.parseString(itemLine);

			String item = keyed.get("item").get(0);
			if (keyed.containsKey("model"))
				item += ":" + keyed.get("model").get(0);

			List<Integer> possibleSlots = new ArrayList<>();
			final String rawSlots = keyed.get("slot").get(0);
			if (rawSlots.contains("-")) {
				final String[] slotSplit = rawSlots.split("-");
				possibleSlots.addAll(IntStream.rangeClosed(Integer.parseInt(slotSplit[0]), Integer.parseInt(slotSplit[1])).boxed().toList());
			} else {
				possibleSlots.add(Integer.parseInt(rawSlots));
			}

			for (Integer possibleSlot : possibleSlots) {
				setItem(possibleSlot, QuickItem.bg(QuickItem.of(item).tag("ShopsBorderDeco", "Y").make()));
			}

		});

		setButton(5, 4, QuickItem
				.of(CompMaterial.LIME_DYE)
				.name(TranslationManager.string(Translations.GUI_SHOP_QUICK_SELL_ITEMS_SELL_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHOP_QUICK_SELL_ITEMS_SELL_LORE))
				.make(), click -> attemptSell(click.player));

		setButton(getBackExitButtonSlot(), getBackButton(), click -> {
			gatherItems().forEach(item -> PlayerUtil.giveItem(click.player, item));

			click.manager.showGUI(click.player, new ShopContentsGUI(new ShopsMainGUI(null, player), player, shop));
		});
	}

	private List<ItemStack> gatherItems() {
		List<ItemStack> items = new ArrayList<>();
		InventoryBorder.getInsideBorders(6).forEach(slot -> {
			final ItemStack slotItem = getItem(slot);
			if (slotItem == null || slotItem.getType() == CompMaterial.AIR.parseMaterial()) return;
			items.add(slotItem);
		});
		return items;
	}

	private void attemptSell(@NonNull final Player player) {
		List<ItemShopContent> itemShopContents = this.shop.getContent().stream().filter(content -> content instanceof ItemShopContent).map(content -> (ItemShopContent) content).toList();
		List<ItemStack> items = gatherItems();

		InventoryBorder.getInsideBorders(6).forEach(slot -> setItem(slot, CompMaterial.AIR.parseItem()));

		items.forEach(item -> {
			final ItemShopContent shopContent = itemShopContents.stream().filter(content -> content.getItem().isSimilar(item)).findFirst().orElse(null);
			if (shopContent == null) {
				PlayerUtil.giveItem(player, item);
				return;
			}

			final int amountToSell = item.getAmount();
			final double subTotal = shopContent.getSellPrice() * amountToSell;
			final double total = Settings.TAX_SELL.getBoolean() ? subTotal - Taxer.calculateTaxAmount(subTotal) : subTotal;

			String moneyAddMsg = "";

			if (shopContent.isCurrencyOfItem()) {
				Shops.getCurrencyManager().deposit(player, shopContent.getCurrencyItem(), (int) total);
				moneyAddMsg += ((int) total) + " " + shopContent.getCurrencyDisplayName();
			} else {
				final String[] currencySplit = shopContent.getCurrency().split("/");
				Shops.getCurrencyManager().deposit(player, currencySplit[0], currencySplit[1], total);
				moneyAddMsg += NumberHelper.format(total);
			}

			// show them msg
			Common.tell(player, TranslationManager.string(player, Translations.MONEY_ADD, "currency", moneyAddMsg));

			final Transaction transaction = new ShopTransaction(
					UUID.randomUUID(),
					shopContent.getId(),
					shopContent.getShopId(),
					shopContent.getOwningShop().getDisplayName(),
					Transaction.TransactionType.SELL,
					shopContent.getType(),
					player.getUniqueId(),
					player.getName(),
					shopContent.getRawItem(),
					shopContent.getCurrencyDisplayName(),
					shopContent.isCurrencyOfItem() ? (int) total : total,
					amountToSell,
					System.currentTimeMillis()
			);

			final ShopTransactionEvent transactionEvent = new ShopTransactionEvent(transaction);
			Shops.getInstance().getServer().getPluginManager().callEvent(transactionEvent);
		});
	}
}
