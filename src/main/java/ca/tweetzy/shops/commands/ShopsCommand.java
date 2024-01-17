package ca.tweetzy.shops.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import org.bukkit.command.CommandSender;

import java.util.List;

public final class ShopsCommand extends Command {

	public ShopsCommand() {
		super(AllowedExecutor.BOTH, "shop", "shops");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		return null;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return null;
	}

	@Override
	public String getSyntax() {
		return "/shops";
	}

	@Override
	public String getDescription() {
		return "Main command for shops";
	}
}
