package ca.tweetzy.shops.commands;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.impl.PriceMap;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.ShopItem;
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
		for (Shop shop : Shops.getShopManager().getShops(player)) {
			for (ShopItem shopItem : shop.getShopItems()) {
				if (!shopItem.canBeSold()) continue;
				if (shopItem.getSellPrice() <= 0D) continue;

				final int itemCount = Shops.getShopManager().getItemCountInPlayerInventory(player, shopItem.getItem());
				if (itemCount == 0) continue;

				final double pricePerOne = shopItem.getSellPrice() / shopItem.getPurchaseQuantity();
				final double preTax = pricePerOne * itemCount;
				final double worth = preTax - (preTax * Settings.TAX / 100D);

				Shops.getShopManager().removeSpecificItemQuantityFromPlayer(player, shopItem.getItem(), itemCount);
				ShopsEconomy.deposit(player, shopItem.getCurrency(), worth);
			}
		}
	}
}
