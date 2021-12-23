package ca.tweetzy.shops.commands;

import ca.tweetzy.shops.api.ShopsAPI;
import ca.tweetzy.shops.api.enums.ShopListType;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.menu.MenuShopEdit;
import ca.tweetzy.shops.menu.MenuShopList;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.tweety.TabUtil;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 22 2021
 * Time Created: 9:57 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CommandEdit extends AbstractSubCommand {

	public CommandEdit() {
		super("edit");
		setDescription("Used to edit a specific shop");
	}

	@Override
	protected void onCommand() {
		checkConsole();

		final Player player = getPlayer();

		if (args.length == 1) {
			final Shop shop = ShopsAPI.getShop(args[0]);
			if (shop == null) {
				returnTell(Localization.Error.INVALID_SHOP_ID.replace("{shop_id}", args[0]));
			}

			new MenuShopEdit(shop).displayTo(player);
			return;
		}

		new MenuShopList(ShopListType.EDIT).displayTo(player);
	}

	@Override
	protected List<String> tabComplete() {
		if (args.length == 1)
			return TabUtil.complete(args[0], ShopsAPI.getShopIds());
		return NO_COMPLETE;
	}
}
