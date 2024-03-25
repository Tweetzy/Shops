package ca.tweetzy.shops.api.filter;

import ca.tweetzy.shops.api.Navigable;

public enum FilterOrder implements Navigable<FilterOrder> {

	ASCENDING,
	DESCENDING;

	@Override
	public Class<FilterOrder> enumClass() {
		return FilterOrder.class;
	}
}
