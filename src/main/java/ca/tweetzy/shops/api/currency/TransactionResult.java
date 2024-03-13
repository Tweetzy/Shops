package ca.tweetzy.shops.api.currency;

public enum TransactionResult {

	SUCCESS,
	FAILED_NO_MONEY,
	FAILED_BUY_QTY_TOO_LOW,
	FAILED_OUT_OF_STOCK,
	NO_LONGER_AVAILABLE,
	ERROR
}
