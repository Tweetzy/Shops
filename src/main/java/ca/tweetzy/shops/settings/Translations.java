package ca.tweetzy.shops.settings;

import ca.tweetzy.flight.settings.TranslationEntry;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.shops.Shops;
import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;

public final class Translations extends TranslationManager {

	public Translations(@NonNull JavaPlugin plugin) {
		super(plugin);
		this.mainLanguage = Settings.LANGUAGE.getString();
	}

	/*
	=================== ERRORS ===================
	 */
	public static TranslationEntry SHOP_ID_TAKEN = create("error.shop id taken", "&cA shop with that id already exists!");
	public static TranslationEntry SHOP_NOT_FOUND = create("error.shop not found", "&cCould not find any shops with that id!");
	public static TranslationEntry NUMBER_CANNOT_BE_ZERO = create("error.number cannot be zero", "&cPlease enter a number greater than 0");
	public static TranslationEntry ONE_FILL_SLOT_REQUIRED = create("error.one fill slot required", "&cThis layout requires at least one fill slot!");
	public static TranslationEntry ITEM_CANNOT_BE_AIR = create("error.item cannot be air", "&cYou cannot add air to the shop.");

	/*
	=================== INFORMATIONAL ===================
	 */
	public static TranslationEntry ADDED_ITEM_TO_SHOP = create("info.shop.add.item", "&aSuccessfully added &e%item_name% &ato &b%shop_id%");


	/*
	=================== PROMPTS ===================
	 */
	public static TranslationEntry PROMPT_SEARCH_TITLE = create("prompts.search.title", "<GRADIENT:5CAEFF>&lSearch</GRADIENT:9F57FF>");
	public static TranslationEntry PROMPT_SEARCH_SUBTITLE = create("prompts.search.subtitle", "&fEnter search keywords into chat");

	public static TranslationEntry PROMPT_SERVER_SHOP_ID_TITLE = create("prompts.server shop id.title", "<GRADIENT:5CAEFF>&lShop Id</GRADIENT:9F57FF>");
	public static TranslationEntry PROMPT_SERVER_SHOP_ID_SUBTITLE = create("prompts.server shop id.subtitle", "&fEnter id for new shop in chat.");

	public static TranslationEntry PROMPT_SERVER_SHOP_DPN_TITLE = create("prompts.server shop display name.title", "<GRADIENT:5CAEFF>&lShop Name</GRADIENT:9F57FF>");
	public static TranslationEntry PROMPT_SERVER_SHOP_DPN_SUBTITLE = create("prompts.server shop display name.subtitle", "&fEnter the new display name in chat.");

	public static TranslationEntry PROMPT_SERVER_SHOP_DESC_TITLE = create("prompts.server shop description.title", "<GRADIENT:5CAEFF>&lShop Description</GRADIENT:9F57FF>");
	public static TranslationEntry PROMPT_SERVER_SHOP_DESC_SUBTITLE = create("prompts.server shop description.subtitle", "&fEnter the new description in chat.");

	public static TranslationEntry PROMPT_SERVER_SHOP_PERM_TITLE = create("prompts.server shop permission.title", "<GRADIENT:5CAEFF>&lShop Permission</GRADIENT:9F57FF>");
	public static TranslationEntry PROMPT_SERVER_SHOP_PERM_SUBTITLE = create("prompts.server shop permission.subtitle", "&fEnter the new permission in chat.");

	public static TranslationEntry PROMPT_SERVER_SHOP_CMD_TITLE = create("prompts.server shop command.title", "<GRADIENT:5CAEFF>&lShop Command</GRADIENT:9F57FF>");
	public static TranslationEntry PROMPT_SERVER_SHOP_CMD_SUBTITLE = create("prompts.server shop command.subtitle", "&fEnter the new command in chat.");

	public static TranslationEntry PROMPT_SERVER_SHOP_ITEM_BUY_PRICE_TITLE = create("prompts.server shop item buy price.title", "<GRADIENT:5CAEFF>&lEnter Buy Price</GRADIENT:9F57FF>");
	public static TranslationEntry PROMPT_SERVER_SHOP_ITEM_BUY_PRICE_SUBTITLE = create("prompts.server shop item buy price.subtitle", "&fEnter the price for &ex&f1 of this item.");
	public static TranslationEntry PROMPT_SERVER_SHOP_ITEM_SELL_PRICE_TITLE = create("prompts.server shop item sell price.title", "<GRADIENT:5CAEFF>&lEnter Sell Price</GRADIENT:9F57FF>");
	public static TranslationEntry PROMPT_SERVER_SHOP_ITEM_SELL_PRICE_SUBTITLE = create("prompts.server shop item sell price.subtitle", "&fEnter the price for &ex&f1 of this item.");
	public static TranslationEntry PROMPT_SERVER_SHOP_ITEM_MIN_QTY_TITLE = create("prompts.server shop item min qty.title", "<GRADIENT:5CAEFF>&lEnter Minimum Qty</GRADIENT:9F57FF>");
	public static TranslationEntry PROMPT_SERVER_SHOP_ITEM_MIN_QTY_SUBTITLE = create("prompts.server shop item min qty.subtitle", "&fEnter the minimum purchase amount.");
	public static TranslationEntry PROMPT_SERVER_SHOP_ITEM_COMMAND_TITLE = create("prompts.server shop item command.title", "<GRADIENT:5CAEFF>&lEnter Command</GRADIENT:9F57FF>");
	public static TranslationEntry PROMPT_SERVER_SHOP_ITEM_COMMAND_SUBTITLE = create("prompts.server shop item command.subtitle", "&fEnter the command for this item in chat.");
	public static TranslationEntry PROMPT_SERVER_SHOP_ITEM_COMMAND_NAME_TITLE = create("prompts.server shop item command name.title", "<GRADIENT:5CAEFF>&LCommand Name</GRADIENT:9F57FF>");
	public static TranslationEntry PROMPT_SERVER_SHOP_ITEM_COMMAND_NAME_SUBTITLE = create("prompts.server shop item command name.subtitle", "&fEnter display name for command");
	public static TranslationEntry PROMPT_SERVER_SHOP_ITEM_COMMAND_DESC_TITLE = create("prompts.server shop item command desc.title", "<GRADIENT:5CAEFF>&LCommand Description</GRADIENT:9F57FF>");
	public static TranslationEntry PROMPT_SERVER_SHOP_ITEM_COMMAND_DESC_SUBTITLE = create("prompts.server shop item command desc.subtitle", "&fEnter description for command");

	/*
	=================== GUI STUFF ===================
	 */
	public static TranslationEntry GUI_SHARED_ITEMS_BACK_BUTTON_NAME = create("gui.shared buttons.back button.name", "<GRADIENT:5CAEFF>&LGo Back</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_SHARED_ITEMS_BACK_BUTTON_LORE = create("gui.shared buttons.back button.lore",
			"&e&l%left_click% &7to go back"
	);

	public static TranslationEntry GUI_SHARED_ITEMS_EXIT_BUTTON_NAME = create("gui.shared buttons.exit button.name", "<GRADIENT:5CAEFF>&LExit</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_SHARED_ITEMS_EXIT_BUTTON_LORE = create("gui.shared buttons.exit button.lore",
			"&e&l%left_click% &7to exit menu"
	);

	public static TranslationEntry GUI_SHARED_ITEMS_PREVIOUS_BUTTON_NAME = create("gui.shared buttons.previous button.name", "<GRADIENT:5CAEFF>&lPrevious Page</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_SHARED_ITEMS_PREVIOUS_BUTTON_LORE = create("gui.shared buttons.previous button.lore",
			"&e&l%left_click% &7to go back a page"
	);

	public static TranslationEntry GUI_SHARED_ITEMS_NEXT_BUTTON_NAME = create("gui.shared buttons.next button.name", "<GRADIENT:5CAEFF>&lNext Page</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_SHARED_ITEMS_NEXT_BUTTON_LORE = create("gui.shared buttons.next button.lore",
			"&e&l%left_click% &7to go to next page"
	);

	public static final TranslationEntry GUI_ADMIN_MAIN_TITLE = create("gui.admin main.title", "<GRADIENT:5CAEFF>&lShops</GRADIENT:9F57FF> &7v%plugin_version%");
	public static final TranslationEntry GUI_ADMIN_SHOP_LIST_TITLE = create("gui.admin shop list.title", "<GRADIENT:5CAEFF>&lShops</GRADIENT:9F57FF> &8> &7List");
	public static final TranslationEntry GUI_ADMIN_SHOP_LIST_ITEMS_CREATE_NAME = create("gui.admin shop list.items.create.name", "<GRADIENT:5CAEFF>&LNew Shop</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_ADMIN_SHOP_LIST_ITEMS_CREATE_LORE = create("gui.admin shop list.items.create.lore", "&7Click to create a new shop");

	public static final TranslationEntry GUI_CURRENCY_PICKER_TITLE = create("gui.currency picker.title", "<GRADIENT:5CAEFF>&lShops</GRADIENT:9F57FF> &8> &7Select Currency");
	public static final TranslationEntry GUI_CURRENCY_PICKER_ITEMS_CUSTOM_NAME = create("gui.currency picker.items.custom currency.name", "<GRADIENT:5CAEFF>&LCustom Item</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_CURRENCY_PICKER_ITEMS_CUSTOM_LORE = create("gui.currency picker.items.custom currency.lore",
			"&7If you want to use use a specific item for",
			"&7the currency, you can set that here.",
			"",
			"&b&l%right_click% &7to open a material picker &eor",
			"&e&l%left_click% &7with the item you want to use",
			"&7as the currency onto this icon."
	);

	public static final TranslationEntry GUI_CHECKOUT_TITLE = create("gui.checkout.title", "<GRADIENT:5CAEFF>&lShops</GRADIENT:9F57FF> &8> &7Checkout");
	public static final TranslationEntry GUI_CHECKOUT_ITEMS_PURCHASE_NAME = create("gui.checkout.items.checkout.name", "<GRADIENT:5CAEFF>&LConfirm Purchase</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_CHECKOUT_ITEMS_PURCHASE_LORE = create("gui.checkout.items.checkout.lore", "&7Click to complete purchase");

	public static final TranslationEntry GUI_CHECKOUT_ITEMS_INCR_NAME = create("gui.checkout.items.increment.name", "&a&l+%increment_value%");
	public static final TranslationEntry GUI_CHECKOUT_ITEMS_INCR_LORE = create("gui.checkout.items.increment.lore", "&e&lClick &7to increment qty");

	public static final TranslationEntry GUI_CHECKOUT_ITEMS_DECR_NAME = create("gui.checkout.items.decrement.name", "&c&l-%decrement_value%");
	public static final TranslationEntry GUI_CHECKOUT_ITEMS_DECR_LORE = create("gui.checkout.items.decrement.lore", "&e&lClick &7to decrement qty");

	public static final TranslationEntry GUI_CHECKOUT_ITEMS_BREAKDOWN_NAME = create("gui.checkout.items.breakdown.name", "<GRADIENT:5CAEFF>&LPricing Breakdown</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_CHECKOUT_ITEMS_BREAKDOWN_LORE = create("gui.checkout.items.breakdown.lore",
			"&8&m------------------------------",
			"&7Selected Quantity&F: &e%checkout_item_qty%",
			"",
			"&A&LPurchase",
			"&7Subtotal&F: &a$%checkout_item_buy_subtotal%",
			"&7",
			"&7&e&lTaxes & Discounts",
			"&7",
			"&7",
			"&c&lSell",
			"",
			"&8&m------------------------------"
	);


	public static final TranslationEntry GUI_SHOP_EDIT_TITLE = create("gui.shop edit.title", "<GRADIENT:5CAEFF>&lShops</GRADIENT:9F57FF> &8> &7Edit &8> &e%shop_id%");

	public static final TranslationEntry GUI_SHOP_EDIT_ITEMS_ITEM_CONTENT_LORE = create("gui.shop edit.items.item content.lore",
			"&8&m------------------------------",
			"&7Buying&f: %is_buy_enabled%",
			"&7Selling&f: %is_sell_enabled%",
			"",
			"&7Buy Price (&fx&e1&7)&f: &a%shop_item_buy_price%",
			"&7Sell Price (&fx&e1&7)&f: &a%shop_item_sell_price%",
			"",
			"&7Minimum Buy Qty&f: &e%shop_item_purchase_qty%",
			"",
			"&e&l%left_click% &7to edit shop item",
			"&c&l%right_click% &7to delete item"
	);

	public static final TranslationEntry GUI_SHOP_EDIT_ITEMS_CMD_CONTENT_LORE = create("gui.shop edit.items.command content.lore",
			"&8&m------------------------------",
			"&7Buying&f: %is_buy_enabled%",
			"&7Buy Price (&fx&e1&7)&f: &a%shop_item_buy_price%",
			"",
			"&7Minimum Buy Qty&f: &e%shop_item_purchase_qty%",
			"",
			"&e&l%left_click% &7to edit shop cmd",
			"&c&l%right_click% &7to delete item"
	);

	public static final TranslationEntry GUI_SHOP_EDIT_ITEMS_CREATE_NAME = create("gui.shop edit.items.create.name", "<GRADIENT:5CAEFF>&LAdd Item</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_EDIT_ITEMS_CREATE_LORE = create("gui.shop edit.items.create.lore",
			"&7Used to add new items to this shop.",
			"",
			"&e&l%left_click% &7to add item/command",
			"&b&l%right_click% &7to quick select material"
	);

	public static final TranslationEntry GUI_SHOP_EDIT_ITEMS_SETTINGS_NAME = create("gui.shop edit.items.settings.name", "<GRADIENT:5CAEFF>&LShop Settings</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_EDIT_ITEMS_SETTINGS_LORE = create("gui.shop edit.items.settings.lore",
			"&7Used to adjust shop specific settings",
			"",
			"&e&lClick &7to open shop settings"
	);

	public static TranslationEntry GUI_SHOP_CONTENTS_ITEMS_SEARCH_NAME = create("gui.shop contents.items.search.name", "<GRADIENT:5CAEFF>&lSearch</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_SHOP_CONTENTS_ITEMS_SEARCH_LORE = create("gui.shop contents.items.search.lore",
			"&7Used to search for items",
			"&7within this specific shop.",
			"",
			"&e&lClick &7to search in this shop."
	);

	public static TranslationEntry GUI_SHOP_CONTENTS_ITEMS_FILTER_NAME = create("gui.shop contents.items.filter.name", "<GRADIENT:5CAEFF>&lFilter</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_SHOP_CONTENTS_ITEMS_FILTER_LORE = create("gui.shop contents.items.filter.lore",
			"&7Used to filter items in shop",
			"",
			"&7Current Order&F: &e%shop_filter_order%",
			"&7Current Type&F: &e%shop_filter_type%",
			"",
			"&e&lClick &7to change filter order."
	);

	public static TranslationEntry GUI_SHOP_CONTENTS_ITEMS_CART_NAME = create("gui.shop contents.items.cart.name", "<GRADIENT:5CAEFF>&lYour Cart</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_SHOP_CONTENTS_ITEMS_CART_LORE = create("gui.shop contents.items.cart.lore",
			"&7Your shopping cart contains any items",
			"&7you may have added from other shops.",
			"",
			"&7Total Items&F: &e%shopping_cart_item_count%",
			"",
			"&e&lClick &7to view your cart."
	);

	public static final TranslationEntry GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_BASE_LORE = create("gui.shop contents.items.content.lore.base lore",
			"&8&m------------------------------",
			"%shop_content_buy_info%",
			"%shop_content_sell_info%",
			"%shop_content_purchase_qty_info%",
			"%shop_content_desc_info%",
			"",
			"&e&l%left_click% &7to buy now",
			"&b&l%right_click% &7to add to cart"
	);

	public static final TranslationEntry GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_BUY_INFO = create("gui.shop contents.items.content.lore.buy lore",
			"&7Buy Price (&fx&e1&7)&f: &a$%shop_item_buy_price%"
	);

	public static final TranslationEntry GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_SELL_INFO = create("gui.shop contents.items.content.lore.sell lore",
			"&7Sell Price (&fx&e1&7)&f: &a$%shop_item_sell_price%"
	);

	public static final TranslationEntry GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_MIN_BUY_INFO = create("gui.shop contents.items.content.lore.min buy lore",
			"",
			"&7Minimum Purchase Qty&f: x&e%shop_item_purchase_qty%"
	);

	public static final TranslationEntry GUI_SHOP_CONTENTS_ITEMS_ITEM_CONTENT_DESC_INFO = create("gui.shop contents.items.content.lore.description lore",
			"&e%shop_item_description%"
	);

	//
	public static final TranslationEntry GUI_SHOP_SETTINGS_TITLE = create("gui.shop settings.title", "<GRADIENT:5CAEFF>&lShops</GRADIENT:9F57FF> &8> &7Settings &8> &e%shop_id%");
	public static final TranslationEntry GUI_SHOP_SETTINGS_ITEMS_ICON_NAME = create("gui.shop settings.items.icon.name", "<GRADIENT:5CAEFF>&lShop Icon</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_SETTINGS_ITEMS_ICON_LORE = create("gui.shop settings.items.icon.lore",
			"&7The display icon that is shown in /shops",
			"",
			"&e&lClick &7to edit the display icon"
	);

	public static final TranslationEntry GUI_SHOP_SETTINGS_ITEMS_DPN_NAME = create("gui.shop settings.items.display name.name", "<GRADIENT:5CAEFF>&lDisplay Name</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_SETTINGS_ITEMS_DPN_LORE = create("gui.shop settings.items.display name.lore",
			"&7The display name of the shop",
			"",
			"&7Current&F: %shop_display_name%",
			"",
			"&e&lClick &7to edit display name"
	);

	public static final TranslationEntry GUI_SHOP_SETTINGS_ITEMS_DESC_NAME = create("gui.shop settings.items.description.name", "<GRADIENT:5CAEFF>&lShop Description</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_SETTINGS_ITEMS_DESC_LORE = create("gui.shop settings.items.description.lore",
			"&7The description of the shop",
			"",
			"&7Current&F:",
			"%shop_description%",
			"",
			"&e&lClick &7to edit description"
	);


	public static final TranslationEntry GUI_SHOP_SETTINGS_ITEMS_DECO_NAME = create("gui.shop settings.items.deco.name", "<GRADIENT:5CAEFF>&lShop Deco/Layout</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_SETTINGS_ITEMS_DECO_LORE = create("gui.shop settings.items.deco.lore",
			"&7How the shop content will be positioned,",
			"&7and any extra decoration items in the gui",
			"",
			"&e&lClick &7to edit the layout/decoration"
	);

	public static final TranslationEntry GUI_SHOP_SETTINGS_ITEMS_OPEN_NAME = create("gui.shop settings.items.open.name", "<GRADIENT:5CAEFF>&lOpen/Close Shop</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_SETTINGS_ITEMS_OPEN_LORE = create("gui.shop settings.items.open.lore",
			"&7Opens & Closes the shop, if closed then it will",
			"&7not appear within /shops or be accessible through commands.",
			"",
			"&7Shop Opened&f: %is_true%",
			"",
			"&e&lClick &7to toggle open status"
	);

	public static final TranslationEntry GUI_SHOP_SETTINGS_ITEMS_PERMISSION_NAME = create("gui.shop settings.items.permission.name", "<GRADIENT:5CAEFF>&lShop Permission</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_SETTINGS_ITEMS_PERMISSION_LORE = create("gui.shop settings.items.permission.lore",
			"&7The permission required to access the",
			"&7shop in /shops or through commands",
			"",
			"&7Shop Permission&f: %shop_permission%",
			"&7Enabled&f: %is_true%",
			"",
			"&e&l%left_click% &7to change shop permission",
			"&b&l%right_click% &7to toggle on/off"
	);

	public static final TranslationEntry GUI_SHOP_SETTINGS_ITEMS_CMD_NAME = create("gui.shop settings.items.command.name", "<GRADIENT:5CAEFF>&lShop Command</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_SETTINGS_ITEMS_CMD_LORE = create("gui.shop settings.items.command.lore",
			"&7The custom command that can be used",
			"&7to access the shop without going through /shops",
			"",
			"&7Shop Command&f: %shop_command%",
			"&7Enabled&f: %is_true%",
			"",
			"&e&l%left_click% &7to change shop command",
			"&b&l%right_click% &7to toggle on/off"
	);

	public static final TranslationEntry GUI_SHOP_SELECT_CONTENT_TYPE_TITLE = create("gui.shop select content type.title", "<GRADIENT:5CAEFF>&lShops</GRADIENT:9F57FF> &8> &7Select Content Type");
	public static final TranslationEntry GUI_SHOP_SELECT_CONTENT_TYPE_ITEMS_ITEM_NAME = create("gui.shop select content type.items.item.name", "<GRADIENT:5CAEFF>&lItem Content</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_SELECT_CONTENT_TYPE_ITEMS_ITEM_LORE = create("gui.shop select content type.items.item.lore",
			"&7An item content is pretty much just",
			"&7a normal item you want to sell to players.",
			"",
			"&e&lClick &7to select this content type."
	);

	public static final TranslationEntry GUI_SHOP_SELECT_CONTENT_TYPE_ITEMS_CMD_NAME = create("gui.shop select content type.items.cmd.name", "<GRADIENT:5CAEFF>&lCommand Content</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_SELECT_CONTENT_TYPE_ITEMS_CMD_LORE = create("gui.shop select content type.items.cmd.lore",
			"&7A command content is essentially a command ",
			"&7that gets executed when the player buys it.",
			"",
			"&e&lClick &7to select this content type."
	);

	//
	public static final TranslationEntry GUI_SHOP_EDIT_CONTENT_TITLE = create("gui.shop edit content.title", "<GRADIENT:5CAEFF>&lShops</GRADIENT:9F57FF> &8> &7Editing Item");
	public static final TranslationEntry GUI_SHOP_EDIT_CONTENT_ITEMS_SAVE_NAME = create("gui.shop edit content.items.add.name", "<GRADIENT:5CAEFF>&LUpdate Item</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_EDIT_CONTENT_ITEMS_SAVE_LORE = create("gui.shop edit content.items.add.lore",
			"&e&lClick &7to update item."
	);

	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_TITLE = create("gui.shop add content.title", "<GRADIENT:5CAEFF>&lShops</GRADIENT:9F57FF> &8> &7New Item");

	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_CURRENCY_NAME = create("gui.shop add content.items.currency.name", "<GRADIENT:5CAEFF>&lSwitch Currency</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_CURRENCY_LORE = create("gui.shop add content.items.currency.lore",
			"&7Used to adjust which currency",
			"&7will be used to buy/sell this item",
			"",
			"&7Current&f: &e%offer_currency%",
			"",
			"&e&lClick &7to edit currency"
	);


	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_BUY_PRICE_NAME = create("gui.shop add content.items.buy price.name", "<GRADIENT:5CAEFF>&lPurchase Price</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_BUY_PRICE_LORE = create("gui.shop add content.items.buy price.lore",
			"&7The purchase price for &fx&e1 &7of this item.",
			"",
			"&7Current Price&f: &e%shop_item_buy_price%",
			"",
			"&e&lClick &7to adjust purchase price."
	);

	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_SELL_PRICE_NAME = create("gui.shop add content.items.sell price.name", "<GRADIENT:5CAEFF>&lSell Price</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_SELL_PRICE_LORE = create("gui.shop add content.items.sell price.lore",
			"&7The sell price for &fx&e1 &7of this item.",
			"",
			"&7Current Price&f: &e%shop_item_sell_price%",
			"",
			"&e&lClick &7to adjust sell price."
	);

	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_TOGGLE_BUY_NAME = create("gui.shop add content.items.toggle buy.name", "<GRADIENT:5CAEFF>&lAllow Purchase</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_TOGGLE_BUY_LORE = create("gui.shop add content.items.toggle buy.lore",
			"&7Used to toggle whether this item can be",
			"&7purchased within this shop or not.",
			"",
			"&7Purchase Allowed&f: %is_true%",
			"",
			"&e&lClick &7to toggle on or off"
	);

	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_TOGGLE_SELL_NAME = create("gui.shop add content.items.toggle sell.name", "<GRADIENT:5CAEFF>&lAllow Sell</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_TOGGLE_SELL_LORE = create("gui.shop add content.items.toggle sell.lore",
			"&7Used to toggle whether this item can be",
			"&7sold within this shop or not.",
			"",
			"&7Sell Allowed&f: %is_true%",
			"",
			"&e&lClick &7to toggle on or off"
	);

	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_BUY_QTY_NAME = create("gui.shop add content.items.purchase qty.name", "<GRADIENT:5CAEFF>&lPurchase Quantity</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_BUY_QTY_LORE = create("gui.shop add content.items.purchase qty.lore",
			"&7The minimum amount of this item",
			"&7that a player must purchase.",
			"",
			"&7Purchase Qty&f: &e%shop_item_purchase_qty%",
			"",
			"&e&lClick &7to adjust purchase quantity"
	);

	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_ICON_LORE = create("gui.shop add content.items.icon.lore",
			"&8&m------------------------------",
			"&7Buying&f: %is_buy_enabled%",
			"&7Selling&f: %is_sell_enabled%",
			"",
			"&7Buy Price (&fx&e1&7)&f: &a%shop_item_buy_price%",
			"&7Sell Price (&fx&e1&7)&f: &a%shop_item_sell_price%",
			"",
			"&7Minimum Buy Qty&f: &e%shop_item_purchase_qty%",
			"",
			"&7This item will cost the player a total of &a%shop_item_purchase_total%",
			"&7to purchase in this shop before tax & discounts.",
			"",
			"&8&m------------------------------",
			"&e&l%left_click% &7to open material picker",
			"&b&l%right_click% &7to use item on cursor"
	);

	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_CMD_ICON_LORE = create("gui.shop add content.items.command icon.lore",
			"&8&m------------------------------",
			"&7Buy Price (&fx&e1&7)&f: &a%shop_item_buy_price%",
			"",
			"&7Minimum Buy Qty&f: &e%shop_item_purchase_qty%",
			"&7Command&f: &7/&e%shop_item_command%",
			"",
			"&7This item will cost the player a total of &a%shop_item_purchase_total%",
			"&7to purchase in this shop before tax & discounts.",
			"",
			"&8&m------------------------------",
			"&e&l%left_click% &7to open material picker",
			"&b&l%right_click% &7to use item on cursor"
	);

	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_COMMAND_NAME = create("gui.shop add content.items.command.name", "<GRADIENT:5CAEFF>&lCommand</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_COMMAND_LORE = create("gui.shop add content.items.command.lore",
			"&7The command that will be executed",
			"&7when a player purchases this item",
			"",
			"&7Command&f: &7/&e%shop_item_command%",
			"",
			"&e&lClick &7to change the command"
	);

	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_COMMAND_NAME_NAME = create("gui.shop add content.items.command name.name", "<GRADIENT:5CAEFF>&lName</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_COMMAND_NAME_LORE = create("gui.shop add content.items.command name.lore",
			"&7The display name that will be used",
			"&7when showing this command in the shop.",
			"",
			"&7Name&f: %shop_item_command_name%",
			"",
			"&e&lClick &7to change the name"
	);

	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_COMMAND_DESC_NAME = create("gui.shop add content.items.command desc.name", "<GRADIENT:5CAEFF>&lDescription</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_COMMAND_DESC_LORE = create("gui.shop add content.items.command desc.lore",
			"&7The description that will be used",
			"&7when showing this command in the shop.",
			"",
			"&7Description&f:",
			"%shop_item_command_desc%",
			"",
			"&e&lClick &7to change the description"
	);

	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_ADD_NAME = create("gui.shop add content.items.add.name", "<GRADIENT:5CAEFF>&lAdd Item</GRADIENT:9F57FF>");
	public static final TranslationEntry GUI_SHOP_ADD_CONTENT_ITEMS_ADD_LORE = create("gui.shop add content.items.add.lore",
			"&e&lClick &7to add to shop."
	);

	public static TranslationEntry GUI_LAYOUT_EDITOR_TITLE = create("gui.layout editor.title", "<GRADIENT:5CAEFF>&lShops</GRADIENT:9F57FF> &8> &7Layout Editor");
	public static TranslationEntry GUI_LAYOUT_EDITOR_ITEMS_FILL_SLOT_NAME = create("gui.layout editor.items.fill slot.name", "<GRADIENT:5CAEFF>&lFill Slot</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_LAYOUT_EDITOR_ITEMS_FILL_SLOT_LORE = create("gui.layout editor.items.fill slot.lore",
			"&7This slot will be populated by",
			"&7the contents added to the shop",
			"",
			"&e&l%left_click% &7to disable this slot"
	);

	public static TranslationEntry GUI_LAYOUT_EDITOR_ITEMS_DECO_SLOT_NAME = create("gui.layout editor.items.decoration slot.name", "<GRADIENT:5CAEFF>&lDecoration Slot</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_LAYOUT_EDITOR_ITEMS_DECO_SLOT_LORE = create("gui.layout editor.items.decoration slot.lore",
			"&7This is a decoration slot",
			"",
			"&e&l%left_click% &7to make empty slot",
			"&d&l%right_click% &7with an item on your cursor",
			"&7to change the decoration item."
	);

	public static TranslationEntry GUI_LAYOUT_EDITOR_ITEMS_EMPTY_SLOT_NAME = create("gui.layout editor.items.empty slot.name", "<GRADIENT:5CAEFF>&lEmpty Slot</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_LAYOUT_EDITOR_ITEMS_EMPTY_SLOT_LORE = create("gui.layout editor.items.empty slot.lore",
			"&7This is a free slot, you can add",
			"&7decorations here or assign it as a ",
			"&7populated slot or even move controls.",
			"",
			"&e&l%left_click% &7to set as fill slot",
			"&b&l%right_click% &7to move a control here",
			"&d&l%right_click% &7with an item on your cursor",
			"&7to decorate this slot."
	);

	public static TranslationEntry GUI_LAYOUT_EDITOR_ITEMS_FILTER_NAME = create("gui.layout editor.items.filter.name", "<GRADIENT:5CAEFF>&lFilter</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_LAYOUT_EDITOR_ITEMS_FILTER_LORE = create("gui.layout editor.items.filter.lore",
			"&7Used to adjust the filtering",
			"&7options for this shop"
	);

	public static TranslationEntry GUI_LAYOUT_EDITOR_ITEMS_SEARCH_NAME = create("gui.layout editor.items.search.name", "<GRADIENT:5CAEFF>&lSearch</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_LAYOUT_EDITOR_ITEMS_SEARCH_LORE = create("gui.layout editor.items.search.lore",
			"&7Used to search for items",
			"&7within this shop."
	);

	public static TranslationEntry GUI_LAYOUT_EDITOR_ITEMS_CART_NAME = create("gui.layout editor.items.cart.name", "<GRADIENT:5CAEFF>&lCart</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_LAYOUT_EDITOR_ITEMS_CART_LORE = create("gui.layout editor.items.cart.lore",
			"&7Your shopping cart, may contain",
			"&7items from other shops."
	);


	public static TranslationEntry GUI_LAYOUT_CONTROL_PICKER_TITLE = create("gui.layout control picker.title", "&EShops &f- &7Select Layout Control");
	public static TranslationEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_EXIT_NAME = create("gui.layout control picker.items.exit.name", "<GRADIENT:5CAEFF>&lExit Button</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_EXIT_LORE = create("gui.layout control picker.items.exit.lore",
			"&7Used to exit shop menu",
			"&e&l%left_click% &7to select this control"
	);

	public static TranslationEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_FILTER_NAME = create("gui.layout control picker.items.filter.name", "<GRADIENT:5CAEFF>&lFilter Button</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_FILTER_LORE = create("gui.layout control picker.items.filter.lore",
			"&7Used to filter the shop items",
			"&e&l%left_click% &7to select this control"
	);

	public static TranslationEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_BACK_NAME = create("gui.layout control picker.items.prev page.name", "<GRADIENT:5CAEFF>&lBack Button</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_BACK_LORE = create("gui.layout control picker.items.prev page.lore",
			"&7Used to navigate back if theres multiple pages",
			"&e&l%left_click% &7to select this control"
	);

	public static TranslationEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_NEXT_NAME = create("gui.layout control picker.items.next page.name", "<GRADIENT:5CAEFF>&lNext Button</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_NEXT_LORE = create("gui.layout control picker.items.next page.lore",
			"&7Used to navigate forward if theres multiple pages",
			"&e&l%left_click% &7to select this control"
	);

	public static TranslationEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_SEARCH_NAME = create("gui.layout control picker.items.search.name", "<GRADIENT:5CAEFF>&lSearch Button</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_SEARCH_LORE = create("gui.layout control picker.items.search.lore",
			"&7Used to search for items in the shop",
			"&e&l%left_click% &7to select this control"
	);

	public static TranslationEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_CART_NAME = create("gui.layout control picker.items.cart.name", "<GRADIENT:5CAEFF>&LCart Button</GRADIENT:9F57FF>");
	public static TranslationEntry GUI_LAYOUT_CONTROL_PICKER_ITEMS_CART_LORE = create("gui.layout control picker.items.cart.lore",
			"&7Used to view items added to your cart",
			"&e&l%left_click% &7to select this control"
	);


	public static void init() {
		new Translations(Shops.getInstance()).setup();
	}
}
