package ca.tweetzy.shops.economy.economies;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.economy.Economy;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: June 22 2021
 * Time Created: 10:15 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class VaultEconomy implements Economy {

    @Override
    public boolean has(Player player, double amount) {
        return Shops.getInstance().getEconomy().has(player, amount);
    }

    @Override
    public void withdraw(Player player, double amount) {
        Shops.getInstance().getEconomy().withdrawPlayer(player, amount);
    }

    @Override
    public void deposit(Player player, double amount) {
        Shops.getInstance().getEconomy().depositPlayer(player, amount);
    }

    @Override
    public double getBalance(Player player) {
        return Shops.getInstance().getEconomy().getBalance(player);
    }
}
