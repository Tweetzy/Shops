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
	public static Integer SHOP_TICK_TASK_SPEED;
	public static String NUMBER_FORMAT;

	private static void init() {
		pathPrefix(null);
		PREFIX = getString("Prefix");

		DYNAMIC_FILL_MAIN_MENU = getBoolean("Dynamic Fill Main Menu");
		SHOP_TICK_TASK_SPEED = getInteger("Shop Tick Task Speed");
		NUMBER_FORMAT = getString("Number Format");
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

			public static String SEARCH_BUTTON_MATERIAL;
			public static Integer SEARCH_BUTTON_SLOT;

			public static String CART_BUTTON_MATERIAL;
			public static Integer CART_BUTTON_SLOT;

			private static void init() {
				pathPrefix("Menus.Main");
				BACKGROUND_ITEM = getString("Background Item");

				SEARCH_BUTTON_MATERIAL = getString("Search Button.Material");
				SEARCH_BUTTON_SLOT = getInteger("Search Button.Slot");

				CART_BUTTON_MATERIAL = getString("Cart Button.Material");
				CART_BUTTON_SLOT = getInteger("Cart Button.Slot");
			}
		}

		public static final class ShopContent {

			public static String SEARCH_BUTTON_MATERIAL;
			public static Integer SEARCH_BUTTON_SLOT;

			public static String CART_BUTTON_MATERIAL;
			public static Integer CART_BUTTON_SLOT;

			private static void init() {
				pathPrefix("Menus.Shop Content");
				SEARCH_BUTTON_MATERIAL = getString("Search Button.Material");
				SEARCH_BUTTON_SLOT = getInteger("Search Button.Slot");

				CART_BUTTON_MATERIAL = getString("Cart Button.Material");
				CART_BUTTON_SLOT = getInteger("Cart Button.Slot");
			}
		}

		public static final class BuyItem {

			public static String BACKGROUND_ITEM;

			public static Integer INC_ONE;
			public static Integer INC_TWO;
			public static Integer INC_THREE;

			public static Integer DECR_ONE;
			public static Integer DECR_TWO;
			public static Integer DECR_THREE;

			public static String CART_BUTTON_MATERIAL;
			public static Integer CART_BUTTON_SLOT;

			public static String SELL_BUTTON_MATERIAL;
			public static Integer SELL_BUTTON_SLOT;

			public static String BUY_BUTTON_MATERIAL;
			public static Integer BUY_BUTTON_SLOT;

			public static String INFO_BUTTON_MATERIAL;
			public static Integer INFO_BUTTON_SLOT;

			public static String INC_ONE_BUTTON_MATERIAL;
			public static Integer INC_ONE_BUTTON_SLOT;

			public static String INC_TWO_BUTTON_MATERIAL;
			public static Integer INC_TWO_BUTTON_SLOT;

			public static String INC_THREE_BUTTON_MATERIAL;
			public static Integer INC_THREE_BUTTON_SLOT;

			public static String DECR_ONE_BUTTON_MATERIAL;
			public static Integer DECR_ONE_BUTTON_SLOT;

			public static String DECR_TWO_BUTTON_MATERIAL;
			public static Integer DECR_TWO_BUTTON_SLOT;

			public static String DECR_THREE_BUTTON_MATERIAL;
			public static Integer DECR_THREE_BUTTON_SLOT;

			private static void init() {
				pathPrefix("Menus.Buy Item");
				BACKGROUND_ITEM = getString("Background Item");

				INC_ONE = getInteger("Increment One");
				INC_TWO = getInteger("Increment Two");
				INC_THREE = getInteger("Increment Three");

				DECR_ONE = getInteger("Decrement One");
				DECR_TWO = getInteger("Decrement Two");
				DECR_THREE = getInteger("Decrement Three");

				CART_BUTTON_MATERIAL = getString("Cart Button.Material");
				CART_BUTTON_SLOT = getInteger("Cart Button.Slot");

				SELL_BUTTON_MATERIAL = getString("Sell Button.Material");
				SELL_BUTTON_SLOT = getInteger("Sell Button.Slot");

				BUY_BUTTON_MATERIAL = getString("Buy Button.Material");
				BUY_BUTTON_SLOT = getInteger("Buy Button.Slot");

				INFO_BUTTON_MATERIAL = getString("Info Button.Material");
				INFO_BUTTON_SLOT = getInteger("Info Button.Slot");

				INC_ONE_BUTTON_MATERIAL = getString("Increment Button One.Material");
				INC_ONE_BUTTON_SLOT = getInteger("Increment Button One.Slot");

				INC_TWO_BUTTON_MATERIAL = getString("Increment Button Two.Material");
				INC_TWO_BUTTON_SLOT = getInteger("Increment Button Two.Slot");

				INC_THREE_BUTTON_MATERIAL = getString("Increment Button Three.Material");
				INC_THREE_BUTTON_SLOT = getInteger("Increment Button Three.Slot");

				DECR_ONE_BUTTON_MATERIAL = getString("Decrement Button One.Material");
				DECR_ONE_BUTTON_SLOT = getInteger("Decrement Button One.Slot");

				DECR_TWO_BUTTON_MATERIAL = getString("Decrement Button Two.Material");
				DECR_TWO_BUTTON_SLOT = getInteger("Decrement Button Two.Slot");

				DECR_THREE_BUTTON_MATERIAL = getString("Decrement Button Three.Material");
				DECR_THREE_BUTTON_SLOT = getInteger("Decrement Button Three.Slot");
			}
		}
	}

	@Override
	protected int getConfigVersion() {
		return 1;
	}
}
