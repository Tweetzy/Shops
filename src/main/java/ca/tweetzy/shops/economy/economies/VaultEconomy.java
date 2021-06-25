package ca.tweetzy.shops.economy.economies;

import ca.tweetzy.shops.economy.IEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * The current file has been created by Kiran Hart
 * Date Created: June 22 2021
 * Time Created: 10:15 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class VaultEconomy implements IEconomy {

    private final net.milkbowl.vault.economy.Economy vault;

    public VaultEconomy() {
        RegisteredServiceProvider<Economy> v = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (v != null) {
            this.vault = v.getProvider();
        } else {
            this.vault = null;
        }
    }

    @Override
    public boolean has(Player player, double amount) {
        return vault != null && vault.has(player, amount);
    }

    @Override
    public void withdraw(Player player, double amount) {
        if (vault == null) return;
        vault.withdrawPlayer(player, amount);
    }

    @Override
    public void deposit(Player player, double amount) {
        if (vault == null) return;
        vault.depositPlayer(player, amount);
    }

    @Override
    public double getBalance(Player player) {
        if (vault == null) return 0D;
        return vault.getBalance(player);
    }

    @Override
    public String getHookName() {
        return "Vault";
    }

    @Override
    public boolean isEnabled() {
        return vault != null;
    }
}
