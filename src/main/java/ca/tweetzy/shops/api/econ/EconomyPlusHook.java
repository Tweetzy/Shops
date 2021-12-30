package ca.tweetzy.shops.api.econ;

import ca.tweetzy.core.hooks.economies.Economy;
import me.itswagpvp.economyplus.EconomyPlus;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 29 2021
 * Time Created: 11:45 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class EconomyPlusHook extends Economy {

	@Override
	public double getBalance(OfflinePlayer player) {
		return EconomyPlus.veco.getBalance(player);
	}

	@Override
	public boolean hasBalance(OfflinePlayer player, double cost) {
		return EconomyPlus.veco.has(player, cost);
	}

	@Override
	public boolean withdrawBalance(OfflinePlayer player, double cost) {
		try {
			EconomyPlus.veco.withdrawPlayer(player, cost);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean deposit(OfflinePlayer player, double amount) {
		try {
			EconomyPlus.veco.depositPlayer(player, amount);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public String getName() {
		return "EconomyPlus";
	}

	@Override
	public boolean isEnabled() {
		return Bukkit.getPluginManager().getPlugin("EconomyPlus") != null;
	}
}
