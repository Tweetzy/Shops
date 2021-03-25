package ca.tweetzy.shops.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 9:25 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CommandList extends AbstractCommand {

    public CommandList() {
        super(CommandType.PLAYER_ONLY, "list");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        return ReturnType.SUCCESS;
    }

    @Override
    public String getPermissionNode() {
        return "shops.cmd.list";
    }

    @Override
    public String getSyntax() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Lists all the available shops";
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
