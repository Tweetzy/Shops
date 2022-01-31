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

	public static String CURRENCY_SYMBOL;

	private static void init() {
		pathPrefix(null);

		CURRENCY_SYMBOL = getString("Currency Symbol");
	}

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

		public static final class MoneyRemove {

			public static String DOLLAR_TYPE;
			public static String OTHER_TYPE;

			private static void init() {
				pathPrefix("Success.Money Remove");
				DOLLAR_TYPE = getString("Dollar Type");
				OTHER_TYPE = getString("Other Type");
			}
		}

		public static final class MoneyAdd {

			public static String DOLLAR_TYPE;
			public static String OTHER_TYPE;

			private static void init() {
				pathPrefix("Success.Money Add");
				DOLLAR_TYPE = getString("Dollar Type");
				OTHER_TYPE = getString("Other Type");
			}
		}

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
		public static String AIR;
		public static String INVENTORY_FULL;
		public static String NO_MONEY;
		public static String NO_ITEMS;
		public static String CHECKOUT_FIRST;
		public static String ITEM_ALREADY_IN_CART;

		private static void init() {
			pathPrefix("Error");
			INVALID_SHOP_ID = getString("Shop ID Invalid");
			SHOP_ID_TAKEN = getString("Shop ID Taken");
			NOT_A_NUMBER = getString("Not A Number");
			AIR = getString("Air");
			INVENTORY_FULL = getString("Inventory Full");
			NO_MONEY = getString("No Money");
			NO_ITEMS = getString("No Items");
			ITEM_ALREADY_IN_CART= getString("Item Already In Cart");
			CHECKOUT_FIRST= getString("Checkout First");
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
	}

	public static final class SearchMenu {

		public static String TITLE;

		public static String SEARCH_BUTTON_NAME;
		public static List<String> SEARCH_BUTTON_LORE;

		public static String CART_BUTTON_NAME;
		public static List<String> CART_BUTTON_LORE;

		private static void init() {
			pathPrefix("Menus.Search");

			TITLE = getString("Title");
			SEARCH_BUTTON_NAME = getString("Search Button.Name");
			SEARCH_BUTTON_LORE = getStringList("Search Button.Lore");

			CART_BUTTON_NAME = getString("Cart Button.Name");
			CART_BUTTON_LORE = getStringList("Cart Button.Lore");
		}
	}

	public static final class MainMenu {

		public static String TITLE;

		public static String SEARCH_BUTTON_NAME;
		public static List<String> SEARCH_BUTTON_LORE;

		public static String CART_BUTTON_NAME;
		public static List<String> CART_BUTTON_LORE;

		private static void init() {
			pathPrefix("Menus.Main");

			TITLE = getString("Title");
			SEARCH_BUTTON_NAME = getString("Search Button.Name");
			SEARCH_BUTTON_LORE = getStringList("Search Button.Lore");

			CART_BUTTON_NAME = getString("Cart Button.Name");
			CART_BUTTON_LORE = getStringList("Cart Button.Lore");
		}
	}

	public static final class ShopContentMenu {

		public static String SEARCH_BUTTON_NAME;
		public static List<String> SEARCH_BUTTON_LORE;

		public static String CART_BUTTON_NAME;
		public static List<String> CART_BUTTON_LORE;

		private static void init() {
			pathPrefix("Menus.Shop Content");
			SEARCH_BUTTON_NAME = getString("Search Button.Name");
			SEARCH_BUTTON_LORE = getStringList("Search Button.Lore");

			CART_BUTTON_NAME = getString("Cart Button.Name");
			CART_BUTTON_LORE = getStringList("Cart Button.Lore");
		}

		public static final class ShopItemLores {

			public static String OUT_OF_STOCK;
			public static String IN_STOCK;
			public static String BUY;
			public static String SELL;
			public static String VIEW;


			private static void init() {
				pathPrefix("Menus.Shop Content.Shop Item Lores");

				OUT_OF_STOCK = getString("Out Of Stock");
				IN_STOCK = getString("In Stock");
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

	public static final class Cart {

		public static String TITLE;
		public static List<String> ITEM_LORE;

		public static String EMPTY_BUTTON_NAME;
		public static List<String> EMPTY_BUTTON_LORE;

		public static String SELL_BUTTON_NAME;
		public static List<String> SELL_BUTTON_LORE;

		public static String BUY_BUTTON_NAME;
		public static List<String> BUY_BUTTON_LORE;

		public static String INFO_BUTTON_NAME;
		public static List<String> INFO_BUTTON_LORE;

		private static void init() {
			pathPrefix("Menus.Cart");

			TITLE = getString("Title");

			ITEM_LORE = getStringList("Item Lore");

			EMPTY_BUTTON_NAME = getString("Empty Button.Name");
			EMPTY_BUTTON_LORE = getStringList("Empty Button.Lore");

			SELL_BUTTON_NAME = getString("Sell Button.Name");
			SELL_BUTTON_LORE = getStringList("Sell Button.Lore");

			BUY_BUTTON_NAME = getString("Buy Button.Name");
			BUY_BUTTON_LORE = getStringList("Buy Button.Lore");

			INFO_BUTTON_NAME = getString("Info Button.Name");
			INFO_BUTTON_LORE = getStringList("Info Button.Lore");
		}
	}

	public static final class BuyItemMenu {

		public static String TITLE;

		public static String CART_NAME;
		public static List<String> CART_LORE;

		public static String SELL_NAME;
		public static List<String> SELL_LORE;

		public static String BUY_NAME;
		public static List<String> BUY_LORE;

		public static String INFO_NAME;
		public static List<String> INFO_LORE;

		public static String INC_ONE_NAME;
		public static List<String> INC_ONE_LORE;

		public static String INC_TWO_NAME;
		public static List<String> INC_TWO_LORE;

		public static String INC_THREE_NAME;
		public static List<String> INC_THREE_LORE;

		public static String DECR_ONE_NAME;
		public static List<String> DECR_ONE_LORE;

		public static String DECR_TWO_NAME;
		public static List<String> DECR_TWO_LORE;

		public static String DECR_THREE_NAME;
		public static List<String> DECR_THREE_LORE;

		private static void init() {
			pathPrefix("Menus.Buy Item");

			TITLE = getString("Title");

			CART_NAME = getString("Cart Button.Name");
			CART_LORE = getStringList("Cart Button.Lore");

			SELL_NAME = getString("Sell Button.Name");
			SELL_LORE = getStringList("Sell Button.Lore");

			BUY_NAME = getString("Buy Button.Name");
			BUY_LORE = getStringList("Buy Button.Lore");

			INFO_NAME = getString("Info Button.Name");
			INFO_LORE = getStringList("Info Button.Lore Format");

			INC_ONE_NAME = getString("Increment Button One.Name");
			INC_ONE_LORE = getStringList("Increment Button One.Lore");

			INC_TWO_NAME = getString("Increment Button Two.Name");
			INC_TWO_LORE = getStringList("Increment Button Two.Lore");

			INC_THREE_NAME = getString("Increment Button Three.Name");
			INC_THREE_LORE = getStringList("Increment Button Three.Lore");

			DECR_ONE_NAME = getString("Decrement Button One.Name");
			DECR_ONE_LORE = getStringList("Decrement Button One.Lore");

			DECR_TWO_NAME = getString("Decrement Button Two.Name");
			DECR_TWO_LORE = getStringList("Decrement Button Two.Lore");

			DECR_THREE_NAME = getString("Decrement Button Three.Name");
			DECR_THREE_LORE = getStringList("Decrement Button Three.Lore");
		}


		public static final class InfoLores {

			public static String BUY_COST;
			public static String SELL_COST;

			private static void init() {
				pathPrefix("Menus.Buy Item.Info Button.Lores");

				BUY_COST = getString("Buy Cost");
				SELL_COST = getString("Sell Cost");
			}
		}
	}

	@Override
	protected int getConfigVersion() {
		return 1;
	}
}
