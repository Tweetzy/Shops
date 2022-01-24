package ca.tweetzy.shops.commands;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.menu.shopcontent.MenuShopContentList;
import ca.tweetzy.tweety.command.SimpleCommand;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 26 2021
 * Time Created: 5:05 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class DynamicShopCommand extends SimpleCommand {

	public DynamicShopCommand(String command) {
		super(command);
		setPermission(null);
	}

	@Override
	protected void onCommand() {
		checkConsole();

		final Player player = getPlayer();
		final Shop shop = Shops.getShopManager().getShop(getLabel());

		if (shop == null) return;
		if (!shop.getSettings().isPublic()) return;
		if (shop.getSettings().isRequirePermissionToSee() && !player.hasPermission(shop.getSettings().getSeePermission())) return;

		new MenuShopContentList(shop).displayTo(player);
	}
}
