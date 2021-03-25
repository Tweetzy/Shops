package ca.tweetzy.shops.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.shops.managers.StorageManager;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 9:26 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CommandCreate extends AbstractCommand {

    public CommandCreate() {
        super(CommandType.PLAYER_ONLY, "create");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length != 1) return ReturnType.SYNTAX_ERROR;

        Player player = (Player) sender;
        String shopId = args[0].toLowerCase();
        return StorageManager.getInstance().createShop(player, new Shop(shopId));
    }

    @Override
    public String getPermissionNode() {
        return "shops.cmd.create";
    }

    @Override
    public String getSyntax() {
        return "create <name>";
    }

    @Override
    public String getDescription() {
        return "Used to create a new shop";
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
