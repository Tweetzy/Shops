package ca.tweetzy.shops.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.utils.PlayerUtils;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.guis.GUIShopContents;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: April 06 2021
 * Time Created: 2:28 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CommandOpen extends AbstractCommand {

	public CommandOpen() {
		super(CommandType.CONSOLE_OK, "open");
	}

	@Override
	protected ReturnType runCommand(CommandSender sender, String... args) {
		if (args.length < 1) return ReturnType.SYNTAX_ERROR;

		if (args.length == 1 && sender instanceof Player) {
			Player player = (Player) sender;
			Shop shop = Shops.getInstance().getShopManager().getShop(args[0]);
			if (shop == null) {
				Shops.getInstance().getLocale().getMessage("shop.does_not_exists").processPlaceholder("shop_id", args[0]).sendPrefixedMessage(player);
				return ReturnType.FAILURE;
			}
			Shops.getInstance().getGuiManager().showGUI(player, new GUIShopContents(player, shop, false, true));
		}

		if (args.length == 2 && !(sender instanceof Player)) {
			Shop shop = Shops.getInstance().getShopManager().getShop(args[0]);
			if (shop == null) {
				Shops.getInstance().getLocale().getMessage("shop.does_not_exists").processPlaceholder("shop_id", args[0]).sendPrefixedMessage(sender);
				return ReturnType.FAILURE;
			}

			Player target = PlayerUtils.findPlayer(args[1]);
			if (target == null) {
				Shops.getInstance().getLocale().newMessage(TextUtils.formatText(String.format("&cCould not find the player: &e%s", args[1]))).sendPrefixedMessage(sender);
				return ReturnType.FAILURE;
			}

			Shops.getInstance().getGuiManager().showGUI(target, new GUIShopContents(target, shop, false, true));
		}

		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> onTab(CommandSender sender, String... args) {
		if (args.length == 1)
			return Shops.getInstance().getShopManager().getShops().stream().map(Shop::getId).collect(Collectors.toList());
		if (args.length == 2 && !(sender instanceof Player))
			return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "shops.cmd.open";
	}

	@Override
	public String getSyntax() {
		return Shops.getInstance().getLocale().getMessage("commands.syntax.open").getMessage();
	}

	@Override
	public String getDescription() {
		return Shops.getInstance().getLocale().getMessage("commands.description.open").getMessage();
	}
}
