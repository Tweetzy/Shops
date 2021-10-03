package ca.tweetzy.shops.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.hooks.EconomyManager;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.shop.Shop;
import ca.tweetzy.shops.shop.ShopItem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: October 02 2021
 * Time Created: 10:52 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CommandSellAll extends AbstractCommand {

	public CommandSellAll() {
		super(CommandType.PLAYER_ONLY, "sellall", "sell all");
	}

	@Override
	protected ReturnType runCommand(CommandSender sender, String... args) {
		final Player player = (Player) sender;

		Shops.newChain().async(() -> {
			double totalSellPrice = 0D;

			for (Shop shop : Shops.getInstance().getShopManager().getShops()) {
				if (!shop.isPublic()) continue;
				if (!shop.isRequiresPermissionToSee() && !player.hasPermission(shop.getSeePermission())) continue;
				if (shop.isBuyOnly()) continue;
				if (shop.isRequiresPermissionToSell() && !player.hasPermission(shop.getSellPermission())) continue;

				for (ShopItem shopItem : shop.getShopItems()) {
					if (shopItem.isBuyOnly()) continue;

					ItemStack deserializedItem = ShopAPI.getInstance().deserializeItem(shopItem.getItem());

					int allItems = ShopAPI.getInstance().getItemCountInPlayerInventory(player, deserializedItem);
					if (allItems == 0) continue;

					double allPreTotalSell = shopItem.getSellPrice() / deserializedItem.getAmount() * allItems;
					double allSellBonus = shop.isUseSellBonus() ? allPreTotalSell * (shop.getSellBonus() / 100) : 0D;

					ShopAPI.getInstance().removeSpecificItemQuantityFromPlayer(player, deserializedItem, allItems);
					totalSellPrice += (allPreTotalSell + allSellBonus);
				}
			}

			EconomyManager.deposit(player, totalSellPrice);
			Shops.getInstance().getLocale().getMessage("general.money_add").processPlaceholder("value", totalSellPrice).sendPrefixedMessage(player);

		}).execute();

		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> onTab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "shops.cmd.sellall";
	}

	@Override
	public String getSyntax() {
		return Shops.getInstance().getLocale().getMessage("commands.syntax.sell all").getMessage();
	}

	@Override
	public String getDescription() {
		return Shops.getInstance().getLocale().getMessage("commands.description.sell all").getMessage();
	}

}
