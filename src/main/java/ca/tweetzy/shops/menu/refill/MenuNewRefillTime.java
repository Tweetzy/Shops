package ca.tweetzy.shops.menu.refill;

import ca.tweetzy.shops.api.enums.TimePeriod;
import ca.tweetzy.shops.impl.RefillTime;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.ShopItem;
import ca.tweetzy.shops.model.NextEnum;
import ca.tweetzy.tweety.ItemUtil;
import ca.tweetzy.tweety.conversation.TitleInput;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.remain.CompColor;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 14 2022
 * Time Created: 11:58 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuNewRefillTime extends Menu {

	private final Shop shop;
	private final ShopItem shopItem;
	private final RefillTime refillTime;
	private final boolean editing;

	private final Button dayButton;
	private final Button hourButton;
	private final Button minuteButton;
	private final Button periodButton;

	private final Button backButton;
	private final Button confirmButton;

	public MenuNewRefillTime(@NonNull final Shop shop, @NonNull final ShopItem shopItem, @NonNull final RefillTime refillTime, final boolean editing) {
		this.shop = shop;
		this.shopItem = shopItem;
		this.refillTime = refillTime;
		this.editing = editing;
		setTitle("&e" + shop.getId() + " &8> &eNew Refill");
		setSize(9 * 5);

		this.backButton = Button.makeSimple(ItemCreator.of(CompMaterial.IRON_DOOR).name("&eBack").lore("&eClick &7to exit/go back"), player -> new MenuRefillTimeList(this.shop, this.shopItem, this.editing).displayTo(player));

		this.dayButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				MenuNewRefillTime.this.refillTime.setDay(NextEnum.next(DayOfWeek.class, MenuNewRefillTime.this.refillTime.getDay().ordinal()));
				newInstance().displayTo(player);
			}

			@Override
			public ItemStack getItem() {
				final List<String> typeLore = new ArrayList<>();
				typeLore.add("");
				for (DayOfWeek value : DayOfWeek.values()) {
					if (MenuNewRefillTime.this.refillTime.getDay() == value)
						typeLore.add("&b→ " + ItemUtil.bountifyCapitalized(value));
					else
						typeLore.add("&7" + ItemUtil.bountifyCapitalized(value));

				}

				typeLore.add("");
				typeLore.add("&eClick &7to go to next");

				return ItemCreator.ofWool(CompColor.values()[MenuNewRefillTime.this.refillTime.getDay().ordinal()]).name("&eDay").lore(typeLore).make();
			}
		};

		this.hourButton = Button.makeSimple(ItemCreator
				.of(CompMaterial.CLOCK)
				.name("&EHour")
				.lore("", "&7Current&f: &e" + refillTime.getHour(), "", "&eClick &7to edit refill hour"), player -> new TitleInput(player, "&eRefill Edit", "&7Enter new refill hour", "&e1 - 12") {

			@Override
			public boolean onResult(String string) {
				if (!NumberUtils.isNumber(string)) return false;

				final int hour = Integer.parseInt(string);
				if (hour < 1 || hour > 12) return false;

				MenuNewRefillTime.this.refillTime.setHour(hour);
				newInstance().displayTo(player);
				return true;
			}
		});

		this.minuteButton = Button.makeSimple(ItemCreator
				.of(CompMaterial.CLOCK)
				.name("&eMinute")
				.lore("", "&7Current&f: &e" + refillTime.getMinute(), "", "&eClick &7to edit refill minute"), player -> new TitleInput(player, "&eRefill Edit", "&7Enter new refill minute", "&e0 - 59") {

			@Override
			public boolean onResult(String string) {
				if (!NumberUtils.isNumber(string)) return false;

				final int hour = Integer.parseInt(string);
				if (hour < 0 || hour > 59) return false;

				MenuNewRefillTime.this.refillTime.setMinute(hour);
				newInstance().displayTo(player);
				return true;
			}
		});

		this.periodButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				MenuNewRefillTime.this.refillTime.setTimePeriod(NextEnum.next(TimePeriod.class, MenuNewRefillTime.this.refillTime.getTimePeriod().ordinal()));
				newInstance().displayTo(player);
			}

			@Override
			public ItemStack getItem() {
				final List<String> typeLore = new ArrayList<>();
				typeLore.add("");
				for (TimePeriod value : TimePeriod.values()) {
					if (MenuNewRefillTime.this.refillTime.getTimePeriod() == value)
						typeLore.add("&b→ " + ItemUtil.bountifyCapitalized(value));
					else
						typeLore.add("&7" + ItemUtil.bountifyCapitalized(value));

				}

				typeLore.add("");
				typeLore.add("&eClick &7to go to next");

				return ItemCreator.of(CompMaterial.DAYLIGHT_DETECTOR).name("&ePeriod").lore(typeLore).make();
			}
		};

		this.confirmButton = Button.makeSimple(ItemCreator
				.of(CompMaterial.LIME_STAINED_GLASS_PANE)
				.name("&a&lConfirm")
				.lore("", "&eRefills&f: &b" + ItemUtil.bountifyCapitalized(this.refillTime.getDay()) + " " + this.refillTime.getHour() + ":" + this.refillTime.getMinute() + " " + this.refillTime.getTimePeriod(), "", "&eClick &7to add refill time"), player -> {

			this.shopItem.getRefillTimes().add(this.refillTime);
			new MenuRefillTimeList(this.shop, this.shopItem, this.editing).displayTo(player);
		});
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == 10)
			return this.dayButton.getItem();

		if (slot == 12)
			return this.hourButton.getItem();

		if (slot == 14)
			return this.minuteButton.getItem();

		if (slot == 16)
			return this.periodButton.getItem();

		if (slot == 9 * 3 + 4)
			return this.confirmButton.getItem();

		if (slot == getSize() - 9)
			return this.backButton.getItem();

		return ItemCreator.of(CompMaterial.BLACK_STAINED_GLASS_PANE).name(" ").make();
	}

	@Override
	public Menu newInstance() {
		return new MenuNewRefillTime(this.shop, this.shopItem, this.refillTime, this.editing);
	}
}
