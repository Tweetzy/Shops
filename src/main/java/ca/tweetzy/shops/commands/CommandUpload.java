package ca.tweetzy.shops.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 03 2021
 * Time Created: 2:13 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CommandUpload extends AbstractCommand {

	public CommandUpload() {
		super(CommandType.CONSOLE_OK, "upload");
	}

	@Override
	protected ReturnType runCommand(CommandSender sender, String... args) {
		if (!Settings.DATABASE_USE.getBoolean()) {
			Shops.getInstance().getLocale().newMessage(TextUtils.formatText("&cYou are not using MySQL")).sendPrefixedMessage(sender);
			return ReturnType.FAILURE;
		}

		Shops.getInstance().getShopManager().loadShops(true, false);
		List<Shop> shops = new ArrayList<>(Shops.getInstance().getShopManager().getShops());

		Shops.getInstance().getDataManager().createShops(shops, fail -> {
			if (!fail) {
				Shops.getInstance().getLocale().newMessage(TextUtils.formatText("&aUploaded shops to MySQL, shops will now reload itself")).sendPrefixedMessage(sender);
				Shops.getInstance().getShopManager().loadShops(true, true);
			} else {
				Shops.getInstance().getLocale().newMessage(TextUtils.formatText("&cSomething went wrong while upload shops to MySQL")).sendPrefixedMessage(sender);
			}
		});


		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> onTab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return null;
	}

	@Override
	public String getSyntax() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}
}
