package ca.tweetzy.shops.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.guis.GUIShopEdit;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 9:24 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CommandEdit extends AbstractCommand {

	public CommandEdit() {
		super(CommandType.PLAYER_ONLY, "edit");
	}

	@Override
	protected ReturnType runCommand(CommandSender sender, String... args) {
		if (args.length != 1) return ReturnType.SYNTAX_ERROR;

		Player player = (Player) sender;
		Shop shop = Shops.getInstance().getShopManager().getShop(args[0]);
		if (shop == null) {
			Shops.getInstance().getLocale().getMessage("shop.does_not_exists").processPlaceholder("shop_id", args[0]).sendPrefixedMessage(player);
			return ReturnType.FAILURE;
		}

		Shops.getInstance().getGuiManager().showGUI(player, new GUIShopEdit(shop));
		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> onTab(CommandSender sender, String... args) {
		if (args.length == 1)
			return Shops.getInstance().getShopManager().getShops().stream().map(Shop::getId).collect(Collectors.toList());
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "shops.cmd.edit";
	}

	@Override
	public String getSyntax() {
		return Shops.getInstance().getLocale().getMessage("commands.syntax.edit").getMessage();
	}

	@Override
	public String getDescription() {
		return Shops.getInstance().getLocale().getMessage("commands.description.edit").getMessage();
	}
}
