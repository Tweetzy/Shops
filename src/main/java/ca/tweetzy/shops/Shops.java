package ca.tweetzy.shops;

import ca.tweetzy.shops.model.CurrencyManager;
import ca.tweetzy.shops.model.ShopManager;
import ca.tweetzy.tweety.MinecraftVersion;
import ca.tweetzy.tweety.bungee.BungeeListener;
import ca.tweetzy.tweety.model.SpigotUpdater;
import ca.tweetzy.tweety.plugin.SimplePlugin;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 12:43 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class Shops extends SimplePlugin {

	private final ShopManager shopManager = new ShopManager();
	private final CurrencyManager currencyManager = new CurrencyManager();

	@Override
	protected void onPluginStart() {

		this.currencyManager.load();
	}

	public static Shops getInstance() {
		return (Shops) SimplePlugin.getInstance();
	}

	public static ShopManager getShopManager() {
		return ((Shops) SimplePlugin.getInstance()).shopManager;
	}

	public static CurrencyManager getCurrencyManager() {
		return ((Shops) SimplePlugin.getInstance()).currencyManager;
	}

	@Override
	public MinecraftVersion.V getMinimumVersion() {
		return MinecraftVersion.V.v1_8;
	}

	@Override
	public int getMetricsPluginId() {
		return 1;
	}

	@Override
	public SpigotUpdater getUpdateCheck() {
		return super.getUpdateCheck();
	}
}
