package ca.tweetzy.shops.api;

public interface Matchable {

	boolean isMatch(final String keyword);

	String getName();
}
