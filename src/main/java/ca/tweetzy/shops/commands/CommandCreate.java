package ca.tweetzy.shops.commands;

import ca.tweetzy.shops.api.ShopsAPI;
import ca.tweetzy.shops.model.input.TitleInput;
import ca.tweetzy.shops.settings.Localization;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.Common;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 9:16 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class CommandCreate extends AbstractSubCommand {

	public CommandCreate() {
		super("create|new");
		setDescription("Used to create a new shop");
	}

	@Override
	protected void onCommand() {
		checkConsole();

		final Player player = getPlayer();

		if (args.length == 0) {
			new TitleInput(player, Localization.ShopCreation.ENTER_ID_TITLE, Localization.ShopCreation.ENTER_ID_SUBTITLE) {
				@Override
				public boolean onResult(String possibleID) {
					if (ShopsAPI.doesShopExists(possibleID)) {
						tell(Localization.Error.SHOP_ID_TAKEN.replace("{shop_id}", possibleID));
						return false;
					}

					new TitleInput(player, Localization.ShopCreation.ENTER_DESC_TITLE, Localization.ShopCreation.ENTER_DESC_SUBTITLE) {
						@Override
						public boolean onResult(String desc) {
							if (desc.length() <= 3) return false;
							ShopsAPI.createShop(possibleID, desc);
							tell(Localization.Success.SHOP_CREATED.replace("{shop_id}", possibleID));
							return true;
						}
					};
					return true;
				}
			};
		}
	}
}
