package ca.tweetzy.shops.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.gui.admin.ShopsAdminMainGUI;
import ca.tweetzy.shops.gui.user.ShopsCartGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class CartCommand extends Command {

	public CartCommand() {
		super(AllowedExecutor.PLAYER, "cart");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		final Player player = (Player) sender;

		// open the cart menu
		Shops.getGuiManager().showGUI(player, new ShopsCartGUI(null, player, Shops.getCartManager().getOrAdd(player)));

		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "shops.command.cart";
	}

	@Override
	public String getSyntax() {
		return "cart";
	}

	@Override
	public String getDescription() {
		return "Used to open the cart menu";
	}
}
