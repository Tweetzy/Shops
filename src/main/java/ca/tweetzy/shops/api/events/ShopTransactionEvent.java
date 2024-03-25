package ca.tweetzy.shops.api.events;

import ca.tweetzy.shops.api.Transaction;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public final class ShopTransactionEvent extends Event{

	private static final HandlerList HANDLERS_LIST = new HandlerList();

	@Getter
	private final Transaction transaction;

	public ShopTransactionEvent(Transaction transaction) {
		this.transaction = transaction;
	}

	@NotNull
	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}
}
