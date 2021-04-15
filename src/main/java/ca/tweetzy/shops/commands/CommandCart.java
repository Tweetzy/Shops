package ca.tweetzy.shops.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.guis.GUICart;
import ca.tweetzy.shops.settings.Settings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: April 01 2021
 * Time Created: 5:40 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CommandCart extends AbstractCommand {

    public CommandCart() {
        super(CommandType.PLAYER_ONLY, "cart");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (!Settings.USE_CART_SYSTEM.getBoolean()) {
            Shops.getInstance().getLocale().getMessage("general.cart_disabled").sendPrefixedMessage(sender);
            return ReturnType.FAILURE;
        }
        Player player = (Player) sender;
        Shops.getInstance().getGuiManager().showGUI(player, new GUICart(player));
        return ReturnType.SUCCESS;
    }

    @Override
    public String getPermissionNode() {
        return "shops.cmd.cart";
    }

    @Override
    public String getSyntax() {
        return "cart";
    }

    @Override
    public String getDescription() {
        return "Open your cart";
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
