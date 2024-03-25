package ca.tweetzy.shops.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.gui.admin.ShopsAdminMainGUI;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.settings.Translations;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class ReloadCommand extends Command {

	public ReloadCommand() {
		super(AllowedExecutor.BOTH, "reload");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		Settings.init();
		Translations.init();
		tell(sender, "&aReloaded configuration files");
		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "shops.command.reload";
	}

	@Override
	public String getSyntax() {
		return "reload";
	}

	@Override
	public String getDescription() {
		return "Used to reload configuration files";
	}
}
