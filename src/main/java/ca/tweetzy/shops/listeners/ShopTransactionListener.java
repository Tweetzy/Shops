package ca.tweetzy.shops.listeners;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.Transaction;
import ca.tweetzy.shops.api.events.ShopTransactionEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public final class ShopTransactionListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onTransaction(final ShopTransactionEvent event) {
		final Transaction transaction = event.getTransaction();

		// store transaction
		transaction.store(result -> {
			if (result != null)
				Shops.getTransactionManager().add(result);
		});

	}
}
