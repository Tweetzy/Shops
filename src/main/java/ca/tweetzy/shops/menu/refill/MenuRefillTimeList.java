package ca.tweetzy.shops.menu.refill;

import ca.tweetzy.shops.impl.RefillTime;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.impl.ShopItem;
import ca.tweetzy.shops.menu.shopcontent.MenuAddShopItem;
import ca.tweetzy.shops.menu.shopcontent.MenuShopContentEdit;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.ItemUtil;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.button.ButtonMenu;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 14 2022
 * Time Created: 11:58 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuRefillTimeList extends MenuPagged<RefillTime> {

	private final Shop shop;
	private final ShopItem shopItem;

	private final Button addButton;
	private final Button backButton;

	public MenuRefillTimeList(@Nonnull final Shop shop, @NonNull final ShopItem shopItem) {
		super(shopItem.getRefillTimes());
		this.shop = shop;
		this.shopItem = shopItem;
		setTitle("&e" + shop.getId() + " &8> &eRefills");

		this.addButton = new ButtonMenu(new MenuNewRefillTime(this.shop, this.shopItem, new RefillTime()), ItemCreator.of(CompMaterial.SLIME_BALL, "&a&lAdd Refill Time", "", "&eClick &7to add new refill time"));
		this.backButton = Button.makeSimple(ItemCreator.of(CompMaterial.IRON_DOOR).name("&eBack").lore("&eClick &7to exit/go back"), player -> new MenuAddShopItem(this.shop, this.shopItem).displayTo(player));
	}

	@Override
	protected ItemStack backgroundItem() {
		return ItemCreator.of(CompMaterial.BLACK_STAINED_GLASS_PANE).name(" ").make();
	}

	@Override
	protected List<Button> getButtonsToAutoRegister() {
		return Arrays.asList(this.addButton, this.backButton);
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == getSize() - 9)
			return this.backButton.getItem();

		if (slot == getBottomCenterSlot())
			return this.addButton.getItem();

		return super.getItemAt(slot);
	}

	@Override
	protected ItemStack convertToItemStack(RefillTime refillTime) {
		return ItemCreator
				.of(CompMaterial.CLOCK)
				.name("&e&lRefill Time")
				.lore("")
				.lore("&7Day&f: &b" + ItemUtil.bountifyCapitalized(refillTime.getDay()))
				.lore("&7Hour&f: &b" + refillTime.getHour())
				.lore("&7Minute&f: &b" + refillTime.getMinute())
				.lore("&7Period&f: &b" + ItemUtil.bountifyCapitalized(refillTime.getTimePeriod()))
				.lore("", "&ePress Drop &7to delete")
				.make();
	}

	@Override
	protected void onPageClick(Player player, RefillTime refillTime, ClickType clickType) {
		if (clickType == ClickType.DROP) {
			this.shopItem.getRefillTimes().remove(refillTime);
			ShopsData.getInstance().save();
			newInstance().displayTo(player);
		}
	}

	@Override
	public Menu newInstance() {
		return new MenuRefillTimeList(this.shop, this.shopItem);
	}
}
