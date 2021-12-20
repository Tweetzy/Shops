package ca.tweetzy.shops.api.interfaces;

import lombok.NonNull;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 1:42 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface ICurrency {

	/**
	 * Is the currency enabled
	 *
	 * @return true if enabled
	 */
	boolean isEnabled();

	/**
	 * Get the name of the currency plugin
	 *
	 * @return the owning plugin name
	 */
	@NonNull String getPluginName();

	/**
	 * If the plugin supports multiple currency types
	 * (ex. UltraEconomy), this will state it
	 *
	 * @return the currency name
	 */
	String getName();

	/**
	 * Withdraws money from a player's balance
	 *
	 * @param player is the {@link Player}
	 * @param amount is the amount being withdrawn
	 */
	void withdraw(@NonNull final Player player, final double amount);

	/**
	 * Deposit money into a player's balance
	 *
	 * @param player is the {@link Player}
	 * @param amount is the amount being deposited
	 */
	void deposit(@NonNull final Player player, final double amount);

	/**
	 * Checks if a player has the amount specified
	 *
	 * @param player is the {@link Player}
	 * @param amount is the amount being checked for
	 * @return true if they have enough
	 */
	boolean has(@NonNull final Player player, final double amount);
}
