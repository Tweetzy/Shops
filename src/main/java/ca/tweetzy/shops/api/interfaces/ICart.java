package ca.tweetzy.shops.api.interfaces;

import ca.tweetzy.shops.impl.Checkout;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 25 2022
 * Time Created: 12:17 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface ICart {

	List<Checkout> getCartItems();


}
