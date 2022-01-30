package ca.tweetzy.shops.impl;

import ca.tweetzy.shops.api.interfaces.ICart;

import java.util.ArrayList;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 30 2022
 * Time Created: 1:26 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class Cart implements ICart {

	private final List<Checkout> items;

	public Cart() {
		this.items = new ArrayList<>();
	}

	@Override
	public List<Checkout> getCartItems() {
		return this.items;
	}
}
