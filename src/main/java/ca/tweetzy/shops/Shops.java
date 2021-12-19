package ca.tweetzy.shops;

import ca.tweetzy.tweety.MinecraftVersion;
import ca.tweetzy.tweety.model.SpigotUpdater;
import ca.tweetzy.tweety.plugin.SimplePlugin;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 12:43 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class Shops extends SimplePlugin {

	@Override
	protected void onPluginStart() {

	}

	public static Shops getInstance() {
		return (Shops) SimplePlugin.getInstance();
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
