package ca.tweetzy.shops.settings;

import ca.tweetzy.tweety.settings.SimpleSettings;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 17 2021
 * Time Created: 11:00 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class Settings extends SimpleSettings {

	public static String PREFIX;

	private static void init() {
		pathPrefix(null);
		PREFIX = getString("Prefix");
	}

	@Override
	protected int getConfigVersion() {
		return 1;
	}
}
