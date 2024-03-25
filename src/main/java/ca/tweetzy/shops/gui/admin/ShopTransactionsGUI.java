package ca.tweetzy.shops.gui.admin;

import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.gui.helper.InventoryBorder;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.ChatUtil;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.Transaction;
import ca.tweetzy.shops.gui.ShopsPagedGUI;
import ca.tweetzy.shops.model.NumberHelper;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class ShopTransactionsGUI extends ShopsPagedGUI<Transaction> {

	public ShopTransactionsGUI(Gui parent, @NonNull Player player) {
		super(parent, player, TranslationManager.string(player, Translations.GUI_TRANSACTIONS_TITLE), 6, new ArrayList<>(Shops.getTransactionManager().getManagerContent()));
		draw();
	}

	@Override
	protected void prePopulate() {
		this.items.sort(Comparator.comparing(Transaction::getTimeCreated).reversed());
	}

	@Override
	protected void drawFixed() {
		applyBackExit();
	}

	@Override
	protected ItemStack makeDisplayItem(Transaction transaction) {
		return QuickItem
				.of(transaction.getItem())
				.lore(TranslationManager.list(player, Translations.GUI_TRANSACTIONS_ITEMS_TRANSACTION_LORE,
						"transaction_type", ChatUtil.capitalizeFully(transaction.getType()),
						"content_type",ChatUtil.capitalizeFully(transaction.getContentType()),
						"shop_name", transaction.getKnownShopName(),
						"player_name", transaction.getUserLastKnownName(),
						"final_price", transaction.getFinalPrice(),
						"currency", transaction.getCurrency(),
						"quantity", transaction.getTransactionQuantity(),
						"transaction_date", NumberHelper.getFormattedDate(transaction.getTimeCreated())
				)).make();
	}

	@Override
	protected void onClick(Transaction object, GuiClickEvent clickEvent) {

	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(6);
	}
}
