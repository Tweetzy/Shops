package ca.tweetzy.shops.api;

import ca.tweetzy.shops.api.interfaces.ITransaction;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Date Created: May 09 2022
 * Time Created: 2:26 p.m.
 *
 * @author Kiran Hart
 */
public final class TransactionCompleteEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	@Getter
	private ITransaction transaction;

	public TransactionCompleteEvent(ITransaction transaction, boolean async) {
		super(async);
		this.transaction = transaction;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
