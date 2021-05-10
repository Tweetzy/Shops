package ca.tweetzy.shops.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.shops.Shops;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: April 02 2021
 * Time Created: 1:49 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CommandReload extends AbstractCommand {

    public CommandReload() {
        super(CommandType.CONSOLE_OK, "reload");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        long start = System.currentTimeMillis();
        Shops.getInstance().reloadConfig();
        Shops.getInstance().getLocale().getMessage("general.reloaded").processPlaceholder("value", System.currentTimeMillis() - start).sendPrefixedMessage(sender);
        return ReturnType.SUCCESS;
    }

    @Override
    public String getPermissionNode() {
        return "shops.cmd.reload";
    }

    @Override
    public String getSyntax() {
        return Shops.getInstance().getLocale().getMessage("commands.syntax.reload").getMessage();
    }

    @Override
    public String getDescription() {
        return Shops.getInstance().getLocale().getMessage("commands.description.reload").getMessage();
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
