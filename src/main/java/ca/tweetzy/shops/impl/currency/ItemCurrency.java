package ca.tweetzy.shops.impl.currency;

import ca.tweetzy.flight.utils.PlayerUtil;
import ca.tweetzy.shops.api.currency.AbstractCurrency;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public final class ItemCurrency extends AbstractCurrency {

	public ItemCurrency() {
		super("Shops", "Item", "&bCustom Item");
	}

	public boolean has(OfflinePlayer player, double amount, ItemStack item) {
		if (player == null || player.getPlayer() == null || !player.isOnline()) return false;
		return PlayerUtil.getItemCountInPlayerInventory(player.getPlayer(), item) >= amount;
	}

	public boolean withdraw(OfflinePlayer player, double amount, ItemStack item) {
		if (player == null || player.getPlayer() == null || !player.isOnline()) return false;
		PlayerUtil.removeSpecificItemQuantityFromPlayer(player.getPlayer(), item, (int) amount);
		return true;
	}

	public boolean deposit(OfflinePlayer player, double amount, ItemStack item) {
		if (player == null || player.getPlayer() == null || !player.isOnline()) return false;

		for (int i = 0; i < amount; i++)
			PlayerUtil.giveItem(player.getPlayer(), item);

		return true;
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		return false;
	}

	@Override
	public boolean withdraw(OfflinePlayer player, double amount) {
		return false;
	}

	@Override
	public boolean deposit(OfflinePlayer player, double amount) {
		return false;
	}
}
