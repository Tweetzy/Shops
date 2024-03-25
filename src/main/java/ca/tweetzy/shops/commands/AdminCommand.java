package ca.tweetzy.shops.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.gui.admin.ShopsAdminMainGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class AdminCommand extends Command {

	public AdminCommand() {
		super(AllowedExecutor.PLAYER, "admin");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		final Player player = (Player) sender;

		// open the administrative menu
		Shops.getGuiManager().showGUI(player, new ShopsAdminMainGUI(player));

		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "shops.command.admin";
	}

	@Override
	public String getSyntax() {
		return "admin";
	}

	@Override
	public String getDescription() {
		return "Used to open the administrative menu";
	}
}
