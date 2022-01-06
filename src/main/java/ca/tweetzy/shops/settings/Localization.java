package ca.tweetzy.shops.settings;

import ca.tweetzy.tweety.settings.SimpleLocalization;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 17 2021
 * Time Created: 11:01 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class Localization extends SimpleLocalization {

	public static final class ShopCreation {

		public static String ENTER_ID_TITLE;
		public static String ENTER_ID_SUBTITLE;

		public static String ENTER_DESC_TITLE;
		public static String ENTER_DESC_SUBTITLE;

		private static void init() {
			pathPrefix("Shop Creation");

			ENTER_ID_TITLE = getString("Enter ID.Title");
			ENTER_ID_SUBTITLE = getString("Enter ID.Subtitle");

			ENTER_DESC_TITLE = getString("Enter Description.Title");
			ENTER_DESC_SUBTITLE = getString("Enter Description.Subtitle");
		}
	}

	public static final class Success {

		public static String SHOP_CREATED;
		public static String SHOP_DELETED;

		private static void init() {
			pathPrefix("Success");
			SHOP_CREATED = getString("Shop Created");
			SHOP_DELETED = getString("Shop Deleted");
		}
	}

	public static final class Error {

		public static String INVALID_SHOP_ID;
		public static String SHOP_ID_TAKEN;
		public static String NOT_A_NUMBER;

		private static void init() {
			pathPrefix("Error");
			INVALID_SHOP_ID = getString("Shop ID Invalid");
			SHOP_ID_TAKEN = getString("Shop ID Taken");
			NOT_A_NUMBER = getString("Not A Number");
		}
	}

	public static final class Menus {

		public static final class Main {

			public static String TITLE;

			private static void init() {
				pathPrefix("Menus.Main");

				TITLE = getString("Title");
			}
		}
	}

	@Override
	protected int getConfigVersion() {
		return 1;
	}
}
