package ca.tweetzy.shops.api;

import lombok.NonNull;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 1:42 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public abstract class AbstractShopCurrency {

	/**
	 * Is the currency enabled
	 *
	 * @return true if enabled
	 */
	public abstract boolean isEnabled();

	/**
	 * Get the name of the currency plugin
	 *
	 * @return the owning plugin name
	 */
	public abstract @NonNull String getPluginName();

	/**
	 * If the plugin supports multiple currency types
	 * (ex. UltraEconomy), this will state it
	 *
	 * @return the currency name
	 */
	public abstract String getName();

	/**
	 * Withdraws money from a player's balance
	 *
	 * @param player is the {@link Player}
	 * @param amount is the amount being withdrawn
	 */
	public abstract boolean withdraw(@NonNull final Player player, final double amount);

	/**
	 * Deposit money into a player's balance
	 *
	 * @param player is the {@link Player}
	 * @param amount is the amount being deposited
	 */
	public abstract boolean deposit(@NonNull final Player player, final double amount);

	/**
	 * Checks if a player has the amount specified
	 *
	 * @param player is the {@link Player}
	 * @param amount is the amount being checked for
	 * @return true if they have enough
	 */
	public abstract boolean has(@NonNull final Player player, final double amount);
}
