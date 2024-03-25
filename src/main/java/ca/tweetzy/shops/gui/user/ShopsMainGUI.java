package ca.tweetzy.shops.gui.user;

import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.gui.ShopsPagedGUI;
import ca.tweetzy.shops.model.ItemParser;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public final class ShopsMainGUI extends ShopsPagedGUI<Shop> {

	public ShopsMainGUI(Gui parent, @NonNull Player player) {
		super(parent, player, TranslationManager.string(player, Translations.GUI_SHOPS_MAIN_TITLE), Settings.GUI_SHOPS_MAIN_ROWS.getInt(), new ArrayList<>());
		setDefaultItem(QuickItem.bg(QuickItem.of(Settings.GUI_SHOPS_MAIN_BACKGROUND.getString()).make()));
		draw();
	}

	@Override
	protected void prePopulate() {
		if (Settings.GUI_SHOPS_MAIN_USE_AUTOFILL.getBoolean() || Settings.GUI_SHOPS_MAIN_FIXED_SHOPS.getStringList().isEmpty()) {
			this.items.addAll(Shops.getShopManager().getValues());
			this.items.sort(Comparator.comparing(Shop::getTimeCreated));
		}
	}

	@Override
	protected void drawFixed() {
		// deco
		Settings.GUI_SHOPS_MAIN_DECORATIONS.getStringList().forEach(itemLine -> {
			final Map<String, List<String>> keyed = ItemParser.parseString(itemLine);

			String item = keyed.get("item").get(0);
			if (keyed.containsKey("model"))
				item += ":" + keyed.get("model").get(0);

			List<Integer> possibleSlots = new ArrayList<>();
			final String rawSlots = keyed.get("slot").get(0);
			if (rawSlots.contains("-")) {
				final String[] slotSplit = rawSlots.split("-");
				possibleSlots.addAll(IntStream.rangeClosed(Integer.parseInt(slotSplit[0]),Integer.parseInt(slotSplit[1])).boxed().toList());
			} else {
				possibleSlots.add(Integer.parseInt(rawSlots));
			}

			for (Integer possibleSlot : possibleSlots) {
				setItem(possibleSlot, QuickItem.bg(QuickItem.of(item).make()));
			}

		});

		if (!Settings.GUI_SHOPS_MAIN_USE_AUTOFILL.getBoolean() && !Settings.GUI_SHOPS_MAIN_FIXED_SHOPS.getStringList().isEmpty()) {

			Settings.GUI_SHOPS_MAIN_FIXED_SHOPS.getStringList().forEach(line -> {
				final Map<String, List<String>> keyed = ItemParser.parseString(line);

				final String shopId = keyed.get("shop").get(0);
				final Shop shop = Shops.getShopManager().getById(shopId);
				if (shop == null) return;

				setButton(Integer.parseInt(keyed.get("slot").get(0)), makeDisplayItem(shop), click -> handleClick(shop, click));
			});
		}
	}

	@Override
	protected ItemStack makeDisplayItem(Shop shop) {
		return QuickItem.of(shop.getShopOptions().getDisplayIcon()).name(shop.getDisplayName()).lore(shop.getDescription()).make();
	}

	@Override
	protected void onClick(Shop shop, GuiClickEvent clickEvent) {
		handleClick(shop, clickEvent);
	}

	private void handleClick(Shop shop, GuiClickEvent clickEvent) {
		if (!shop.getShopOptions().isOpen()) {
			Common.tell(clickEvent.player, TranslationManager.string(clickEvent.player, Translations.SHOP_IS_CLOSED));
			return;
		}

		if (shop.getShopOptions().isRequiresPermission() && !clickEvent.player.hasPermission(shop.getShopOptions().getPermission())) {
			Common.tell(clickEvent.player, TranslationManager.string(clickEvent.player, Translations.NOT_ALLOWED_TO_USE_SHOP));
			return;
		}

		clickEvent.manager.showGUI(clickEvent.player, new ShopContentsGUI(this, clickEvent.player, shop));
	}

	@Override
	protected List<Integer> fillSlots() {
		return Settings.GUI_SHOPS_MAIN_FILL_SLOTS.getIntList();
	}

}
