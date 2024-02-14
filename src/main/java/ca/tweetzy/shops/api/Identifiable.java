package ca.tweetzy.shops.api;

import lombok.NonNull;

public interface Identifiable<T> {

	/**
	 * The identifier for the group.
	 *
	 * @return The id of the group.
	 */
	@NonNull
	T getId();
}
