package ca.tweetzy.shops.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.guis.GUIShops;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 9:24 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CommandShop extends AbstractCommand {

    public CommandShop() {
        super(CommandType.PLAYER_ONLY, "shops");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Shops.getInstance().getGuiManager().showGUI(player, new GUIShops());
        }
        return ReturnType.SUCCESS;
    }

    @Override
    public String getPermissionNode() {
        return "shops.cmd";
    }

    @Override
    public String getSyntax() {
        return "/shops";
    }

    @Override
    public String getDescription() {
        return "Open the shop selection menu";
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
