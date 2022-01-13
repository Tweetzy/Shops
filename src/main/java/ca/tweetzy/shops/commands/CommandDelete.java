package ca.tweetzy.shops.commands;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopsAPI;
import ca.tweetzy.shops.api.enums.ShopListType;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.menu.MenuShopList;
import ca.tweetzy.shops.settings.Localization;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 9:16 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CommandDelete extends AbstractSubCommand {

	public CommandDelete() {
		super("remove|del");
		setDescription("Used to delete an existing shop");
	}

	@Override
	protected void onCommand() {
		checkConsole();

		final Player player = getPlayer();

		if (args.length == 1) {
			final Shop shop = Shops.getShopManager().getShop(args[0]);
			if (shop == null) {
				returnTell(Localization.Error.INVALID_SHOP_ID.replace("{shop_id}", args[0]));
			}

			Shops.getShopManager().deleteShop(shop.getId());
			returnTell(Localization.Success.SHOP_DELETED.replace("{shop_id}", args[0]));
		}

		new MenuShopList(ShopListType.DELETE).displayTo(player);
	}
}
