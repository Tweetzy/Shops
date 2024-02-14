package ca.tweetzy.shops.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.gui.user.ShopContentsGUI;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class DynamicShopCommand extends Command {

	private final Shop shop;

	public DynamicShopCommand(@NonNull final Shop shop) {
		super(AllowedExecutor.PLAYER, shop.getShopOptions().getCommand());
		this.shop = shop;
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		final Player player = (Player) sender;
		Shops.getGuiManager().showGUI(player, new ShopContentsGUI(null, player, this.shop));
		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return this.shop.getShopOptions().isRequiresPermission() ? this.shop.getShopOptions().getPermission() : null;
	}

	@Override
	public String getSyntax() {
		return null;
	}

	@Override
	public String getDescription() {
		return this.shop.getDescription().get(0);
	}
}
