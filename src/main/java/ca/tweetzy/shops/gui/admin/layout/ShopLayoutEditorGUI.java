package ca.tweetzy.shops.gui.admin.layout;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.api.shop.ShopDisplay;
import ca.tweetzy.shops.gui.ShopsPagedGUI;
import ca.tweetzy.shops.gui.admin.ShopSettingsGUI;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ShopLayoutEditorGUI extends ShopsPagedGUI<Integer> {

	private final Player player;
	private final Shop shop;

	public ShopLayoutEditorGUI(@NonNull final Player player, @NonNull final Shop shop) {
		super(new ShopSettingsGUI(player, shop), player, TranslationManager.string(player, Translations.GUI_LAYOUT_EDITOR_TITLE), Math.max(2, shop.getShopOptions().getShopDisplay().getRows()), IntStream.rangeClosed(0, (Math.max(2, shop.getShopOptions().getShopDisplay().getRows()) * 9) - 1).boxed().collect(Collectors.toList()));
		this.player = player;
		this.shop = shop;

		setAcceptsItems(true);
		setOnClose(close -> this.shop.sync(null));
		draw();
	}

	@Override
	protected void drawFixed() {

		final ShopDisplay layout = this.shop.getShopOptions().getShopDisplay();

		// controls
		setButton(layout.getExitButtonSlot(), getBackButton(), click -> {
			this.shop.sync(null);
			click.manager.showGUI(click.player, new ShopSettingsGUI(click.player, this.shop));
		});

		setItem(layout.getPrevPageButtonSlot(), getPreviousButton());
		setItem(layout.getNextPageButtonSlot(), getNextButton());

		setItem(layout.getFilterButtonSlot(), QuickItem
				.of(CompMaterial.REPEATER)
				.name(TranslationManager.string(this.player, Translations.GUI_LAYOUT_EDITOR_ITEMS_FILTER_NAME))
				.lore(TranslationManager.list(this.player, Translations.GUI_LAYOUT_EDITOR_ITEMS_FILTER_LORE))
				.make());

		setItem(layout.getSearchButtonSlot(), QuickItem
				.of(CompMaterial.DARK_OAK_SIGN)
				.name(TranslationManager.string(this.player, Translations.GUI_LAYOUT_EDITOR_ITEMS_SEARCH_NAME))
				.lore(TranslationManager.list(this.player, Translations.GUI_LAYOUT_EDITOR_ITEMS_SEARCH_LORE))
				.make());

		setItem(layout.getCartButtonSlot(), QuickItem
				.of(CompMaterial.MINECART)
				.name(TranslationManager.string(this.player, Translations.GUI_LAYOUT_EDITOR_ITEMS_CART_NAME))
				.lore(TranslationManager.list(this.player, Translations.GUI_LAYOUT_EDITOR_ITEMS_CART_LORE))
				.make());

		setItem(layout.getSellButtonSlot(), QuickItem
				.of(CompMaterial.ENDER_CHEST)
				.name(TranslationManager.string(this.player, Translations.GUI_LAYOUT_EDITOR_ITEMS_SELL_NAME))
				.lore(TranslationManager.list(this.player, Translations.GUI_LAYOUT_EDITOR_ITEMS_SELL_LORE))
				.make());

	}

	@Override
	protected ItemStack makeDisplayItem(Integer slot) {
		final ShopDisplay layout = this.shop.getShopOptions().getShopDisplay();

		if (layout.getFillSlots().contains(slot))
			return QuickItem
					.of(CompMaterial.LIME_STAINED_GLASS_PANE)
					.name(TranslationManager.string(this.player, Translations.GUI_LAYOUT_EDITOR_ITEMS_FILL_SLOT_NAME))
					.lore(TranslationManager.list(this.player, Translations.GUI_LAYOUT_EDITOR_ITEMS_FILL_SLOT_LORE, "left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK)))
					.make();

		if (layout.getDecoration().containsKey(slot)) {
			return QuickItem
					.of(layout.getDecoration().get(slot))
					.name(TranslationManager.string(this.player, Translations.GUI_LAYOUT_EDITOR_ITEMS_DECO_SLOT_NAME))
					.lore(TranslationManager.list(this.player, Translations.GUI_LAYOUT_EDITOR_ITEMS_DECO_SLOT_LORE,
							"left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK),
							"right_click", TranslationManager.string(this.player, Translations.MOUSE_RIGHT_CLICK)
					))
					.make();
		}


		return emptySlotItem();
	}

	@Override
	protected void onClick(Integer slot, GuiClickEvent click) {
		final ShopDisplay layout = this.shop.getShopOptions().getShopDisplay();

		// fill slots
		if (layout.getFillSlots().contains(slot)) {
			// prevent all slots from being removed from fill
			if (layout.getFillSlots().size() == 1) {
				Common.tell(click.player, TranslationManager.string(click.player, Translations.ONE_FILL_SLOT_REQUIRED));
				return;
			}

			layout.getFillSlots().remove(slot);
			draw();
			return;
		}

		// empty slots
		else if (isEmptySlot(slot)) {
			if (click.clickType == ClickType.LEFT) {
				layout.getFillSlots().add(slot);
				draw();
			}

			if (click.clickType == ClickType.RIGHT) {
				final ItemStack cursor = click.cursor;
				if (cursor != null && cursor.getType() != CompMaterial.AIR.parseMaterial()) {
					final ItemStack newIcon = cursor.clone();
					newIcon.setAmount(1);
					layout.getDecoration().put(slot, newIcon);
					draw();
					return;
				}

				click.manager.showGUI(click.player, new ShopLayoutControlPickerGUI(this, click.player, selectedControl -> {
					switch (selectedControl) {
						case EXIT_BACK_BUTTON -> layout.setExitButtonSlot(slot);
						case PREVIOUS_PAGE_BUTTON -> layout.setPrevPageButtonSlot(slot);
						case NEXT_PAGE_BUTTON -> layout.setNextPageButtonSlot(slot);
						case SEARCH_BUTTON -> layout.setSearchButtonSlot(slot);
						case FILTER_BUTTON -> layout.setFilterButtonSlot(slot);
						case CART_BUTTON -> layout.setCartButtonSlot(slot);
						case SELL_BUTTON -> layout.setSellButtonSlot(slot);
					}

					layout.getFillSlots().remove(slot);
					click.manager.showGUI(click.player, new ShopLayoutEditorGUI(this.player, this.shop));
				}));
			}
			return;
		} else if (layout.getDecoration().containsKey(slot)) {
			if (click.clickType == ClickType.LEFT) {
				layout.getDecoration().remove(slot);
				draw();
			}

			if (click.clickType == ClickType.RIGHT) {
				final ItemStack cursor = click.cursor;
				if (cursor != null && cursor.getType() != CompMaterial.AIR.parseMaterial()) {
					final ItemStack newIcon = cursor.clone();
					newIcon.setAmount(1);

					layout.getDecoration().put(slot, newIcon);
					draw();
					return;
				}
			}
		}
	}

	@Override
	protected List<Integer> fillSlots() {
		return IntStream.rangeClosed(0, 53).boxed().collect(Collectors.toList());
	}

	private boolean isEmptySlot(final int slot) {
		final ShopDisplay layout = this.shop.getShopOptions().getShopDisplay();

		return !layout.getFillSlots().contains(slot)
				&& !layout.getDecoration().containsKey(slot)
				&& layout.getExitButtonSlot() != slot
				&& layout.getNextPageButtonSlot() != slot
				&& layout.getPrevPageButtonSlot() != slot
				&& layout.getSearchButtonSlot() != slot
				&& layout.getFilterButtonSlot() != slot
				&& layout.getCartButtonSlot() != slot
				&& layout.getSellButtonSlot() != slot;
	}

	private ItemStack emptySlotItem() {
		return QuickItem
				.of(CompMaterial.WHITE_STAINED_GLASS_PANE)
				.name(TranslationManager.string(this.player, Translations.GUI_LAYOUT_EDITOR_ITEMS_EMPTY_SLOT_NAME))
				.lore(TranslationManager.list(this.player, Translations.GUI_LAYOUT_EDITOR_ITEMS_EMPTY_SLOT_LORE,
						"left_click", TranslationManager.string(this.player, Translations.MOUSE_LEFT_CLICK),
						"right_click", TranslationManager.string(this.player, Translations.MOUSE_RIGHT_CLICK)
				))
				.make();
	}

	@Override
	protected int getBackExitButtonSlot() {
		return -1;
	}
}
