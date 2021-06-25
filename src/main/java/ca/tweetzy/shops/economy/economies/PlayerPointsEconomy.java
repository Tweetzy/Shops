package ca.tweetzy.shops.economy.economies;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.economy.IEconomy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The current file has been created by Kiran Hart
 * Date Created: June 24 2021
 * Time Created: 11:39 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class PlayerPointsEconomy implements IEconomy {

    private final PlayerPoints playerPoints;

    public PlayerPointsEconomy() {
        this.playerPoints = (PlayerPoints) Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints");
    }

    @Override
    public String getHookName() {
        return "PlayerPoints";
    }

    @Override
    public boolean isEnabled() {
        return playerPoints.isEnabled();
    }

    @Override
    public double getBalance(Player player) {
        AtomicInteger value = new AtomicInteger(0);
        playerPoints.getAPI().lookAsync(player.getUniqueId()).thenAccept(value::set);
        return value.get();
    }

    @Override
    public boolean has(Player player, double cost) {
        int total = 0;
        try {
            total = playerPoints.getAPI().lookAsync(player.getUniqueId()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return total >= convert(cost);
    }

    @Override
    public void withdraw(Player player, double cost) {
        playerPoints.getAPI().takeAsync(player.getUniqueId(), convert(cost)).thenAccept(success -> Shops.getInstance().getLogger().info("Withdrawing points from player"));
    }

    @Override
    public void deposit(Player player, double cost) {
       playerPoints.getAPI().giveAsync(player.getUniqueId(), convert(cost)).thenAccept(success -> Shops.getInstance().getLogger().info("Giving points to player"));
    }

    private int convert(double amount) {
        return (int) Math.ceil(amount);
    }
}
