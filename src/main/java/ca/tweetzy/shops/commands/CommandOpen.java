package ca.tweetzy.shops.commands;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.menu.settings.MenuShopEdit;
import ca.tweetzy.shops.menu.shopcontent.MenuShopContentEdit;
import ca.tweetzy.shops.menu.shopcontent.MenuShopContentList;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.tweety.TabUtil;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Date Created: February 07 2022
 * Time Created: 7:40 p.m.
 *
 * @author Kiran Hart
 */
public final class CommandOpen extends AbstractSubCommand {

	public CommandOpen() {
		super("open");
		setDescription("Used to open a specific shop");
	}

	@Override
	protected void onCommand() {
		if (args.length >= 1) {
			final Shop shop = Shops.getShopManager().getShop(args[0]);
			if (shop == null) {
				returnTell(Localization.Error.INVALID_SHOP_ID.replace("{shop_id}", args[0]));
			}

			if (args.length == 2) {
				final Player target = findPlayer(args[1]);
				new MenuShopContentList(shop, null).displayTo(target);
			} else {
				if (sender instanceof Player) {
					new MenuShopContentList(shop, null).displayTo((Player) sender);
				}
			}
		}
	}

	@Override
	protected List<String> tabComplete() {
		if (args.length == 1)
			return TabUtil.complete(args[0], Shops.getShopManager().getShopIds());
		return NO_COMPLETE;
	}
}
