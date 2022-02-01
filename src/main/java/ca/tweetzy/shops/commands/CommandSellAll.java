package ca.tweetzy.shops.commands;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.impl.PriceMap;
import ca.tweetzy.shops.model.manager.ShopsEconomy;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.tweety.remain.CompMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 01 2022
 * Time Created: 11:21 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CommandSellAll extends AbstractSubCommand {

	public CommandSellAll() {
		super("sellall");
	}

	@Override
	protected void onCommand() {
		checkConsole();

		final Player player = getPlayer();
		for (ItemStack content : player.getInventory().getContents()) {
			if (content == null || content.getType() == CompMaterial.AIR.toMaterial()) continue;

			final PriceMap map = Shops.getPriceMapManager().getPriceMap(content);
			if (map == null) continue;

			if (map.getSellPrice() <= 0D) continue;

			final int itemCount = content.getAmount();
			final double worth = map.getSellPrice() * itemCount;

			Shops.getShopManager().removeSpecificItemQuantityFromPlayer(player, content, itemCount);
			ShopsEconomy.deposit(player, map.getCurrency(), worth - (worth * Settings.TAX / 100D));
		}
	}
}
