package ca.tweetzy.shops;

import ca.tweetzy.shops.commands.DynamicShopCommand;
import ca.tweetzy.shops.model.CurrencyManager;
import ca.tweetzy.shops.model.ShopManager;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.Messenger;
import ca.tweetzy.tweety.MinecraftVersion;
import ca.tweetzy.tweety.model.SpigotUpdater;
import ca.tweetzy.tweety.plugin.TweetyPlugin;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 12:43 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class Shops extends TweetyPlugin {

	private final ShopManager shopManager = new ShopManager();
	private final CurrencyManager currencyManager = new CurrencyManager();

	@Override
	protected void onPluginStart() {
		normalizePrefix();

		this.currencyManager.load(null);

		this.shopManager.load(loaded -> loaded.forEach(shop -> {
			if (shop.getSettings().isUseOpenCommand() && shop.getSettings().getOpenCommand().length() >= 1)
				registerCommand(new DynamicShopCommand(shop.getSettings().getOpenCommand()));
		}));

//		getServer().getMessenger().registerOutgoingPluginChannel(this, "ShopsDataTransfer");

	}

	public static Shops getInstance() {
		return (Shops) TweetyPlugin.getInstance();
	}

	public static ShopManager getShopManager() {
		return ((Shops) TweetyPlugin.getInstance()).shopManager;
	}

	public static CurrencyManager getCurrencyManager() {
		return ((Shops) TweetyPlugin.getInstance()).currencyManager;
	}

	@Override
	public MinecraftVersion.V getMinimumVersion() {
		return MinecraftVersion.V.v1_8;
	}

	@Override
	public int getMetricsPluginId() {
		return 6807;
	}

	@Override
	public SpigotUpdater getUpdateCheck() {
		return super.getUpdateCheck();
	}


	private void normalizePrefix() {
		Common.ADD_TELL_PREFIX = true;
		Common.ADD_LOG_PREFIX = true;
		Common.setTellPrefix(Settings.PREFIX);
		Common.setLogPrefix(Settings.PREFIX);
		Messenger.setInfoPrefix(Settings.PREFIX + " ");
		Messenger.setAnnouncePrefix(Settings.PREFIX + " ");
		Messenger.setErrorPrefix(Settings.PREFIX + " ");
		Messenger.setQuestionPrefix(Settings.PREFIX + " ");
		Messenger.setSuccessPrefix(Settings.PREFIX + " ");
		Messenger.setWarnPrefix(Settings.PREFIX + " ");
	}

}
