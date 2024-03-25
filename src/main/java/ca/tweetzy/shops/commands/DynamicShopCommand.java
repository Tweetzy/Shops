package ca.tweetzy.shops.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.gui.user.ShopContentsGUI;
import ca.tweetzy.shops.settings.Translations;
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
		if (!this.shop.getShopOptions().isOpen()) {
			tell(player, TranslationManager.string(player, Translations.SHOP_IS_CLOSED));
			return ReturnType.FAIL;
		}

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
