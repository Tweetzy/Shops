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


	/*
	=================== PROMPTS ===================
	 */
	public static TranslationEntry PROMPT_SERVER_SHOP_ID_TITLE = create("prompts.server shop id.title", "<GRADIENT:5CAEFF>&lShop Id</GRADIENT:9F57FF>");
	public static TranslationEntry PROMPT_SERVER_SHOP_ID_SUBTITLE = create("prompts.server shop id.subtitle", "&fEnter id for new shop in chat.");

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

	public static final TranslationEntry GUI_SHOP_EDIT_TITLE = create("gui.shop edit.title", "<GRADIENT:5CAEFF>&lShops</GRADIENT:9F57FF> &8> &7Edit &8> &e%shop_id%");
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

	public static void init() {
		new Translations(Shops.getInstance()).setup();
	}
}
