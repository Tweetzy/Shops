package ca.tweetzy.shops.economy;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.economy.economies.RevEnchantsEconomy;
import ca.tweetzy.shops.economy.economies.VaultEconomy;
import ca.tweetzy.shops.settings.Settings;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: June 22 2021
 * Time Created: 10:15 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class EconomyManager {

    final Economy economy;

    public EconomyManager() {
        switch(Settings.ECONOMY_PROVIDER.getString()) {
            case "RevEnchants":
                if (Shops.getInstance().getServer().getPluginManager().isPluginEnabled("RevEnchants")) {
                    this.economy = new RevEnchantsEconomy();
                    Shops.getInstance().getLogger().info("Using RevEnchants for the economy!");
                } else {
                    this.economy = new VaultEconomy();
                    Shops.getInstance().getLogger().info("Using Vault for the economy!");
                }
                break;
            default:
                this.economy = new VaultEconomy();
                Shops.getInstance().getLogger().info("Using Vault for the economy!");
        }
    }


    public boolean has(Player player, double amount) {
        return this.economy.has(player, amount);
    }

    public void withdrawPlayer(Player player, double amount) {
        this.economy.withdraw(player, amount);
    }

    public void depositPlayer(Player player, double amount) {
        this.economy.deposit(player, amount);
    }

    public double getBalance(Player player) {
        return this.economy.getBalance(player);
    }

}
