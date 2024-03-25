package ca.tweetzy.shops.api.currency;

import org.bukkit.OfflinePlayer;

public interface Chargeable {

	boolean has(OfflinePlayer player, double amount);

	boolean withdraw(OfflinePlayer player, double amount);

	boolean deposit(OfflinePlayer player, double amount);
}
