package ca.tweetzy.shops.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.managers.StorageManager;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 9:25 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CommandRemove extends AbstractCommand {

    public CommandRemove() {
        super(CommandType.PLAYER_ONLY, "remove");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length != 1) return ReturnType.SYNTAX_ERROR;

        Player player = (Player) sender;
        String shopId = args[0].toLowerCase();
        return StorageManager.getInstance().removeShop(player, shopId);
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1) {
            return Shops.getInstance().getShopManager().getShops().stream().map(Shop::getId).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "shops.cmd.remove";
    }

    @Override
    public String getSyntax() {
        return Shops.getInstance().getLocale().getMessage("commands.syntax.remove").getMessage();
    }

    @Override
    public String getDescription() {
        return Shops.getInstance().getLocale().getMessage("commands.description.remove").getMessage();
    }
}
