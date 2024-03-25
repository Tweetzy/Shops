package ca.tweetzy.shops.impl.manager;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.Transaction;
import ca.tweetzy.shops.api.manager.ListManager;

public final class TransactionManager extends ListManager<Transaction> {

	public TransactionManager() {
		super("Transaction");
	}

	@Override
	public void load() {
		clear();
		Shops.getDataManager().getTransactions((error, result) -> {
			if (error == null)
				addAll(result);
		});
	}
}
