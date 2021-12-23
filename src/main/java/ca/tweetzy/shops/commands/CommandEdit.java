package ca.tweetzy.shops.commands;

import ca.tweetzy.shops.api.enums.ShopListType;
import ca.tweetzy.shops.menu.MenuShopList;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 22 2021
 * Time Created: 9:57 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CommandEdit extends AbstractSubCommand{

	public CommandEdit() {
		super("edit");
		setDescription("Used to edit a specific shop");
	}

	@Override
	protected void onCommand() {
		checkConsole();

		final Player player = getPlayer();
		new MenuShopList(ShopListType.EDIT).displayTo(player);
	}
}
