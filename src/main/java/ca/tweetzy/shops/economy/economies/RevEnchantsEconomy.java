package ca.tweetzy.shops.economy.economies;

import ca.tweetzy.shops.economy.Economy;
import me.revils.enchants.api.PublicRevAPI;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: June 22 2021
 * Time Created: 10:20 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class RevEnchantsEconomy implements Economy {

    @Override
    public boolean has(Player player, double amount) {
        return PublicRevAPI.getTokens(player) >= amount;
    }

    @Override
    public void withdraw(Player player, double amount) {
        PublicRevAPI.setTokens(player, (long) (PublicRevAPI.getTokens(player) -  amount));
    }

    @Override
    public void deposit(Player player, double amount) {
        PublicRevAPI.setTokens(player, (long) (PublicRevAPI.getTokens(player) + amount));
    }

    @Override
    public double getBalance(Player player) {
        return (double) PublicRevAPI.getTokens(player);
    }
}
