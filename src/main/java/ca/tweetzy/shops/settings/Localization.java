package ca.tweetzy.shops.settings;

import ca.tweetzy.tweety.settings.SimpleLocalization;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 17 2021
 * Time Created: 11:01 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class Localization extends SimpleLocalization {

	public static final class Prompt {

		public static String ITEM_SEARCH_TITLE;
		public static String ITEM_SEARCH_SUBTITLE;

		private static void init() {
			pathPrefix("Prompt.Item Search");
			ITEM_SEARCH_TITLE = getString("Title");
			ITEM_SEARCH_SUBTITLE = getString("Subtitle");
		}
	}

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

		public static final class BackButton {

			public static String NAME;
			public static List<String> LORE;

			private static void init() {
				pathPrefix("Menus.Back Button");
				NAME = getString("Name");
				LORE = getStringList("Lore");
			}
		}

		public static final class Main {

			public static String TITLE;

			private static void init() {
				pathPrefix("Menus.Main");

				TITLE = getString("Title");
			}
		}
	}

	public static final class ShopContentMenu {

		public static String SEARCH_BUTTON_NAME;
		public static List<String> SEARCH_BUTTON_LORE;

		private static void init() {
			pathPrefix("Menus.Shop Content");
			SEARCH_BUTTON_NAME = getString("Search Button.Name");
			SEARCH_BUTTON_LORE = getStringList("Search Button.Lore");
		}

		public static final class ShopItemLores {

			public static String OUT_OF_STOCK;
			public static String IN_STOCK;
			public static String REFILLS_IN;
			public static String BUY;
			public static String SELL;
			public static String VIEW;


			private static void init() {
				pathPrefix("Menus.Shop Content.Shop Item Lores");

				OUT_OF_STOCK = getString("Out Of Stock");
				IN_STOCK = getString("In Stock");
				REFILLS_IN = getString("Refill In");
				BUY = getString("Buy");
				SELL = getString("Sell");
				VIEW = getString("View");
			}
		}

		public static final class ShopItemLoreFormat {

			public static List<String> LIMITED;
			public static List<String> UNLIMITED;


			private static void init() {
				pathPrefix("Menus.Shop Content.Shop Item Lore Format");

				LIMITED = getStringList("Limited");
				UNLIMITED = getStringList("Unlimited");
			}
		}
	}

	@Override
	protected int getConfigVersion() {
		return 1;
	}
}
