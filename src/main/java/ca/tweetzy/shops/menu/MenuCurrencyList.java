package ca.tweetzy.shops.menu;

import ca.tweetzy.shops.api.AbstractShopCurrency;
import ca.tweetzy.shops.api.ShopsAPI;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.menu.Menu;
import ca.tweetzy.tweety.menu.MenuPagged;
import ca.tweetzy.tweety.menu.button.Button;
import ca.tweetzy.tweety.menu.model.ItemCreator;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 22 2021
 * Time Created: 11:15 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class MenuCurrencyList extends MenuPagged<AbstractShopCurrency> {

	private final Shop shop;
	private final Button backButton;

	public MenuCurrencyList(@NonNull final Shop shop) {
		super(ShopsAPI.getCurrencies());
		setTitle("&e" + shop.getId() + " &8> &eSelect Currency");
		this.shop = shop;
		this.backButton = Button.makeSimple(ItemCreator.of(CompMaterial.IRON_DOOR).name("&eBack").lore("&eClick &7to exit/go back"), player -> new MenuShopEdit(this.shop).displayTo(player));
	}

	@Override
	protected ItemStack convertToItemStack(AbstractShopCurrency currency) {
		return ItemCreator
				.of(CompMaterial.GOLD_INGOT)
				.name("&e" + currency.getName())
				.lore(
						"",
						"&eOwning Plugin&f: &7" + currency.getPluginName(),
						"&eCurrency Name&f: &7" + currency.getName(),
						"",
						"&eClick &7to select this currency"
				)
				.make();
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == getSize() - 9)
			return this.backButton.getItem();
		return super.getItemAt(slot);
	}

	@Override
	protected void onPageClick(Player player, AbstractShopCurrency currency, ClickType clickType) {
		shop.setCurrency(currency);
		ShopsData.getInstance().save();
		new MenuShopEdit(this.shop).displayTo(player);
	}

	@Override
	public Menu newInstance() {
		return new MenuCurrencyList(this.shop);
	}
}
