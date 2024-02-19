package ca.tweetzy.shops.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.gui.user.ShopContentsGUI;
import ca.tweetzy.shops.gui.user.ShopsMainGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class ShopsCommand extends Command {

	public ShopsCommand() {
		super(AllowedExecutor.BOTH, "shop", "shops");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		if (sender instanceof final Player player) {

			if (args.length == 0) {
				Shops.getGuiManager().showGUI(player, new ShopsMainGUI(null, player));
				return ReturnType.SUCCESS;
			}

			if (args.length == 1) {
				final Shop shop = Shops.getShopManager().getById(args[0]);

				if (shop == null) return ReturnType.FAIL;
				if (!shop.getShopOptions().isOpen()) return ReturnType.FAIL;
				if (!player.hasPermission(shop.getShopOptions().getPermission()) && shop.getShopOptions().isRequiresPermission()) return ReturnType.FAIL;

				// open shop
				Shops.getGuiManager().showGUI(player, new ShopContentsGUI(null, player, shop));
			}
		}

		return ReturnType.SUCCESS;
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
