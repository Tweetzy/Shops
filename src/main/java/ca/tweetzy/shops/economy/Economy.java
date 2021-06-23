package ca.tweetzy.shops.economy;

import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: June 22 2021
 * Time Created: 10:13 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface Economy {

    boolean has(Player player, double amount);

    void withdraw(Player player, double amount);

    void deposit(Player player, double amount);

    double getBalance(Player player);
}
