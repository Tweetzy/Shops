package ca.tweetzy.shops.api;

import lombok.NonNull;

import java.util.UUID;

public interface Identifiable {

	/**
	 * The identifier for the group.
	 *
	 * @return The id of the group.
	 */
	@NonNull
	UUID getId();
}
