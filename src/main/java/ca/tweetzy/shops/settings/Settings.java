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

	public static Boolean DYNAMIC_FILL_MAIN_MENU;

	private static void init() {
		pathPrefix(null);
		PREFIX = getString("Prefix");

		DYNAMIC_FILL_MAIN_MENU = getBoolean("Dynamic Fill Main Menu");
	}

	public static final class Menus {

		public static final class BackButton {

			public static String MATERIAL;
			public static Integer SLOT;

			private static void init() {
				pathPrefix("Menus.Back Button");
				MATERIAL = getString("Material");
				SLOT = getInteger("Slot");
			}
		}

		public static final class Main {

			public static String BACKGROUND_ITEM;

			private static void init() {
				pathPrefix("Menus.Main");
				BACKGROUND_ITEM = getString("Background Item");
			}
		}

		public static final class ShopContent {

			public static String SEARCH_BUTTON_MATERIAL;
			public static Integer SEARCH_BUTTON_SLOT;

			private static void init() {
				pathPrefix("Menus.Shop Content");
				SEARCH_BUTTON_MATERIAL = getString("Search Button.Material");
				SEARCH_BUTTON_SLOT = getInteger("Search Button.Slot");
			}
		}
	}

	@Override
	protected int getConfigVersion() {
		return 1;
	}
}
