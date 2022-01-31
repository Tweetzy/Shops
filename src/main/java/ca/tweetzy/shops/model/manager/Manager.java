package ca.tweetzy.shops.model.manager;

import java.util.function.Consumer;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 21 2021
 * Time Created: 11:38 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public abstract class Manager<T> {

	public abstract void load(Consumer<T> data);
}
