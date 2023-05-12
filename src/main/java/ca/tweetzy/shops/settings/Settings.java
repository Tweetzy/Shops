package ca.tweetzy.shops.settings;

import ca.tweetzy.tweety.settings.SimpleSettings;

import java.util.List;

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
	public static Boolean METRICS;

	public static Double TAX;

	private static void init() {
		pathPrefix(null);
		PREFIX = getString("Prefix");

		DYNAMIC_FILL_MAIN_MENU = getBoolean("Dynamic Fill Main Menu");
		SHOP_TICK_TASK_SPEED = getInteger("Shop Tick Task Speed");
		NUMBER_FORMAT = getString("Number Format");
		TAX = getDouble("Tax");
		METRICS = getBoolean("Metrics");
	}

	public static final class DiscordWebhook {

		public static Boolean ENABLED;
		public static Boolean SEND_ON_BUY;
		public static Boolean SEND_ON_SELL;
		public static Boolean USE_RANDOM_COLOUR;

		public static String USERNAME;
		public static String AVATAR;
		public static String DEFAULT_COLOUR;
		public static List<String> HOOKS;

		public static String SOLD_TITLE;
		public static String BOUGHT_TITLE;

		private static void init() {
			pathPrefix("Discord Webhooks");

			ENABLED = getBoolean("Enabled");
			SEND_ON_BUY = getBoolean("Send On Buy");
			SEND_ON_SELL = getBoolean("Send On Sell");
			USE_RANDOM_COLOUR = getBoolean("Use Random Colour");

			USERNAME = getString("Username");
			AVATAR = getString("Avatar");
			DEFAULT_COLOUR = getString("Default Colour");
			HOOKS = getStringList("Hooks");

			SOLD_TITLE = getString("Sold Title");
			BOUGHT_TITLE = getString("Bought Title");
		}

		public static final class Fields {

			public static String PLAYER_NAME;
			public static String PLAYER_VALUE;
			public static Boolean PLAYER_INLINE;

			public static String QTY_NAME;
			public static String QTY_VALUE;
			public static Boolean QTY_INLINE;

			public static String ITEM_NAME;
			public static String ITEM_VALUE;
			public static Boolean ITEM_INLINE;

			public static String PRICE_NAME;
			public static String PRICE_VALUE;
			public static Boolean PRICE_INLINE;

			private static void init() {
				pathPrefix("Discord Webhooks.Fields.Player");

				PLAYER_NAME = getString("Name");
				PLAYER_VALUE = getString("Value");
				PLAYER_INLINE = getBoolean("Inline");

				pathPrefix("Discord Webhooks.Fields.Quantity");
				QTY_NAME = getString("Name");
				QTY_VALUE = getString("Value");
				QTY_INLINE = getBoolean("Inline");

				pathPrefix("Discord Webhooks.Fields.Item");
				ITEM_NAME = getString("Name");
				ITEM_VALUE = getString("Value");
				ITEM_INLINE = getBoolean("Inline");

				pathPrefix("Discord Webhooks.Fields.Price");
				PRICE_NAME = getString("Name");
				PRICE_VALUE = getString("Value");
				PRICE_INLINE = getBoolean("Inline");

			}
		}
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

		public static final class PrevButton {

			public static String MATERIAL;

			private static void init() {
				pathPrefix("Menus.Prev Button");
				MATERIAL = getString("Material");
			}
		}

		public static final class NextButton {

			public static String MATERIAL;

			private static void init() {
				pathPrefix("Menus.Next Button");
				MATERIAL = getString("Material");
			}
		}

		public static final class Search {

			public static String BACKGROUND_ITEM;

			public static String SEARCH_BUTTON_MATERIAL;
			public static Integer SEARCH_BUTTON_SLOT;

			public static String CART_BUTTON_MATERIAL;
			public static Integer CART_BUTTON_SLOT;

			private static void init() {
				pathPrefix("Menus.Search");
				BACKGROUND_ITEM = getString("Background Item");

				SEARCH_BUTTON_MATERIAL = getString("Search Button.Material");
				SEARCH_BUTTON_SLOT = getInteger("Search Button.Slot");

				CART_BUTTON_MATERIAL = getString("Cart Button.Material");
				CART_BUTTON_SLOT = getInteger("Cart Button.Slot");
			}
		}

		public static final class Main {

			public static Integer SIZE;
			public static String BACKGROUND_ITEM;

			public static String SEARCH_BUTTON_MATERIAL;
			public static Integer SEARCH_BUTTON_SLOT;

			public static String CART_BUTTON_MATERIAL;
			public static Integer CART_BUTTON_SLOT;

			public static String SELL_ALL_BUTTON_MATERIAL;
			public static Integer SELL_ALL_BUTTON_SLOT;

			private static void init() {
				pathPrefix("Menus.Main");

				SIZE = getInteger("Size");
				BACKGROUND_ITEM = getString("Background Item");

				SEARCH_BUTTON_MATERIAL = getString("Search Button.Material");
				SEARCH_BUTTON_SLOT = getInteger("Search Button.Slot");

				CART_BUTTON_MATERIAL = getString("Cart Button.Material");
				CART_BUTTON_SLOT = getInteger("Cart Button.Slot");

				SELL_ALL_BUTTON_MATERIAL = getString("Sell All Button.Material");
				SELL_ALL_BUTTON_SLOT = getInteger("Sell All Button.Slot");
			}
		}

		public static final class ShopContent {

			public static String SEARCH_BUTTON_MATERIAL;
			public static Integer SEARCH_BUTTON_SLOT;

			public static String CART_BUTTON_MATERIAL;
			public static Integer CART_BUTTON_SLOT;

			public static String SELL_ALL_BUTTON_MATERIAL;
			public static Integer SELL_ALL_BUTTON_SLOT;

			private static void init() {
				pathPrefix("Menus.Shop Content");
				SEARCH_BUTTON_MATERIAL = getString("Search Button.Material");
				SEARCH_BUTTON_SLOT = getInteger("Search Button.Slot");

				CART_BUTTON_MATERIAL = getString("Cart Button.Material");
				CART_BUTTON_SLOT = getInteger("Cart Button.Slot");

				SELL_ALL_BUTTON_MATERIAL = getString("Sell All Button.Material");
				SELL_ALL_BUTTON_SLOT = getInteger("Sell All Button.Slot");
			}
		}

		public static final class Cart {

			public static String BACKGROUND_ITEM;

			public static String EMPTY_BUTTON_MATERIAL;
			public static Integer EMPTY_BUTTON_SLOT;

			public static String SELL_BUTTON_MATERIAL;
			public static Integer SELL_BUTTON_SLOT;

			public static String BUY_BUTTON_MATERIAL;
			public static Integer BUY_BUTTON_SLOT;

			public static String INFO_BUTTON_MATERIAL;
			public static Integer INFO_BUTTON_SLOT;


			private static void init() {
				pathPrefix("Menus.Cart");
				BACKGROUND_ITEM = getString("Background Item");

				EMPTY_BUTTON_MATERIAL = getString("Empty Button.Material");
				EMPTY_BUTTON_SLOT = getInteger("Empty Button.Slot");

				SELL_BUTTON_MATERIAL = getString("Sell Button.Material");
				SELL_BUTTON_SLOT = getInteger("Sell Button.Slot");

				BUY_BUTTON_MATERIAL = getString("Buy Button.Material");
				BUY_BUTTON_SLOT = getInteger("Buy Button.Slot");

				INFO_BUTTON_MATERIAL = getString("Info Button.Material");
				INFO_BUTTON_SLOT = getInteger("Info Button.Slot");
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
