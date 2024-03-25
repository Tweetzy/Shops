package ca.tweetzy.shops.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.ItemUtil;
import ca.tweetzy.flight.utils.MathUtil;
import ca.tweetzy.flight.utils.PlayerUtil;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.SynchronizeResult;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.impl.shop.ItemShopContent;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public final class DeleteCommand extends Command {

	public DeleteCommand() {
		super(AllowedExecutor.BOTH, "delete");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		if (args.length < 1) return ReturnType.INVALID_SYNTAX;

		final Shop shop = Shops.getShopManager().getById(args[0]);
		if (shop == null) {
			tell(sender, TranslationManager.string(Translations.SHOP_NOT_FOUND));
			return ReturnType.FAIL;
		}

		shop.unStore(status -> {
			if (status == SynchronizeResult.FAILURE) {
				tell(sender, TranslationManager.string(Translations.SHOP_DELETE_ERROR));
				return;
			}

			tell(sender, TranslationManager.string(Translations.SHOP_DELETED));
		});

		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "shops.command.delete";
	}

	@Override
	public String getSyntax() {
		return "delete <shopId>";
	}

	@Override
	public String getDescription() {
		return "Used to delete a shop";
	}
}
