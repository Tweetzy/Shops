package ca.tweetzy.shops.gui.admin.layout;

import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.gui.helper.InventoryBorder;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.api.shop.ShopLayoutControl;
import ca.tweetzy.shops.gui.ShopsPagedGUI;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public final class ShopLayoutControlPickerGUI extends ShopsPagedGUI<ShopLayoutControl> {

	private final Player player;
	private final Consumer<ShopLayoutControl> selectedControl;

	public ShopLayoutControlPickerGUI(@NonNull final Gui parent, @NonNull final Player player, @NonNull final Consumer<ShopLayoutControl> selectedControl) {
		super(parent, player, TranslationManager.string(player, Translations.GUI_LAYOUT_CONTROL_PICKER_TITLE), 4, Arrays.asList(ShopLayoutControl.values()));
		this.player = player;
		this.selectedControl = selectedControl;
		draw();
	}

	@Override
	protected void drawFixed() {
		applyBackExit();
	}

	@Override
	protected ItemStack makeDisplayItem(ShopLayoutControl layoutControl) {
		return switch (layoutControl) {
			case EXIT_BACK_BUTTON -> QuickItem
					.of(Settings.GUI_LAYOUT_CONTROL_PICKER_ITEMS_EXIT.getItemStack())
					.name(TranslationManager.string(this.player, Translations.GUI_LAYOUT_CONTROL_PICKER_ITEMS_EXIT_NAME))
					.lore(TranslationManager.list(this.player, Translations.GUI_LAYOUT_CONTROL_PICKER_ITEMS_EXIT_LORE, "left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK)))
					.make();

			case FILTER_BUTTON -> QuickItem
					.of(Settings.GUI_LAYOUT_CONTROL_PICKER_ITEMS_FILTER.getItemStack())
					.name(TranslationManager.string(this.player, Translations.GUI_LAYOUT_CONTROL_PICKER_ITEMS_FILTER_NAME))
					.lore(TranslationManager.list(this.player, Translations.GUI_LAYOUT_CONTROL_PICKER_ITEMS_FILTER_LORE, "left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK)))
					.make();

			case PREVIOUS_PAGE_BUTTON -> QuickItem
					.of(Settings.GUI_LAYOUT_CONTROL_PICKER_ITEMS_PREV_PAGE.getItemStack())
					.name(TranslationManager.string(this.player, Translations.GUI_LAYOUT_CONTROL_PICKER_ITEMS_BACK_NAME))
					.lore(TranslationManager.list(this.player, Translations.GUI_LAYOUT_CONTROL_PICKER_ITEMS_BACK_LORE, "left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK)))
					.make();
			case NEXT_PAGE_BUTTON -> QuickItem
					.of(Settings.GUI_LAYOUT_CONTROL_PICKER_ITEMS_NEXT_PAGE.getItemStack())
					.name(TranslationManager.string(this.player, Translations.GUI_LAYOUT_CONTROL_PICKER_ITEMS_NEXT_NAME))
					.lore(TranslationManager.list(this.player, Translations.GUI_LAYOUT_CONTROL_PICKER_ITEMS_NEXT_LORE, "left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK)))
					.make();

			case SEARCH_BUTTON -> QuickItem
					.of(Settings.GUI_LAYOUT_CONTROL_PICKER_ITEMS_SEARCH.getItemStack())
					.name(TranslationManager.string(this.player, Translations.GUI_LAYOUT_CONTROL_PICKER_ITEMS_SEARCH_NAME))
					.lore(TranslationManager.list(this.player, Translations.GUI_LAYOUT_CONTROL_PICKER_ITEMS_SEARCH_LORE, "left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK)))
					.make();
		};
	}

	@Override
	protected void onClick(ShopLayoutControl layoutControl, GuiClickEvent click) {
		this.selectedControl.accept(layoutControl);
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(3);
	}
}
