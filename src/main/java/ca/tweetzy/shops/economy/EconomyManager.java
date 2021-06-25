package ca.tweetzy.shops.economy;

import ca.tweetzy.shops.economy.economies.PlayerPointsEconomy;
import ca.tweetzy.shops.economy.economies.RevEnchantsEconomy;
import ca.tweetzy.shops.economy.economies.VaultEconomy;
import ca.tweetzy.shops.settings.Settings;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The current file has been created by Kiran Hart
 * Date Created: June 22 2021
 * Time Created: 10:15 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class EconomyManager {

    private final JavaPlugin plugin;
    private IEconomy selectedEconomy;

    enum SupportedEconomy {
        VAULT("Vault"),
        REV_ENCHANTS("RevEnchants"),
        PLAYER_POINTS("PlayerPoints");

        @Getter
        final String economyName;

        SupportedEconomy(String economyName) {
            this.economyName = economyName;
        }
    }

    public EconomyManager(JavaPlugin plugin) {
        this.plugin = plugin;
        String preferredEconomy = Settings.ECONOMY_PROVIDER.getString();

        if (preferredEconomy.equalsIgnoreCase(SupportedEconomy.VAULT.getEconomyName())) {
            this.selectedEconomy = new VaultEconomy();
            if (!this.selectedEconomy.isEnabled()) {
                Bukkit.getPluginManager().disablePlugin(this.plugin);
                plugin.getLogger().severe("Something went wrong while trying to load the " + selectedEconomy.getHookName() + " economy!");
            }
        }

        if (preferredEconomy.equalsIgnoreCase(SupportedEconomy.PLAYER_POINTS.getEconomyName())) {
            this.selectedEconomy = new PlayerPointsEconomy();
            if (!this.selectedEconomy.isEnabled()) {
                Bukkit.getPluginManager().disablePlugin(this.plugin);
                plugin.getLogger().severe("Something went wrong while trying to load the " + selectedEconomy.getHookName() + " economy!");
            }
        }

        if (preferredEconomy.equalsIgnoreCase(SupportedEconomy.REV_ENCHANTS.getEconomyName())) {
            this.selectedEconomy = new RevEnchantsEconomy();
            if (!this.selectedEconomy.isEnabled()) {
                Bukkit.getPluginManager().disablePlugin(this.plugin);
                plugin.getLogger().severe("Something went wrong while trying to load the " + selectedEconomy.getHookName() + " economy!");
            }
        }

        this.plugin.getLogger().info("Using " + selectedEconomy.getHookName() + " as the economy provider!");
    }

    public double getBalance(Player player) {
        return this.selectedEconomy.getBalance(player);
    }

    public boolean has(Player player, double cost) {
        return this.selectedEconomy.has(player, cost);
    }

    public void withdrawPlayer(Player player, double cost) {
        this.selectedEconomy.withdraw(player, cost);
    }

    public void depositPlayer(Player player, double cost) {
        this.selectedEconomy.deposit(player, cost);
    }
}
