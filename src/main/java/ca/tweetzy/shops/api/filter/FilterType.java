package ca.tweetzy.shops.api.filter;

import ca.tweetzy.shops.api.Navigable;

public enum FilterType implements Navigable<FilterType> {

	NAME,
	PRICE,
	MIN_PURCHASE;

	@Override
	public Class<FilterType> enumClass() {
		return FilterType.class;
	}
}
