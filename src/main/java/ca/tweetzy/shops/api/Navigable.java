package ca.tweetzy.shops.api;

public interface Navigable<E extends Enum<E>> {

	E next();

	E previous();
}