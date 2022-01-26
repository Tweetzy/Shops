package ca.tweetzy.shops.model.manager;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.Inflector;
import ca.tweetzy.shops.api.enums.ShopLayout;
import ca.tweetzy.shops.api.enums.ShopState;
import ca.tweetzy.shops.api.interfaces.shop.IShopItem;
import ca.tweetzy.shops.impl.*;
import ca.tweetzy.shops.model.ItemInspect;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.collection.StrictList;
import ca.tweetzy.tweety.collection.StrictMap;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 2:24 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class ShopManager extends Manager<Collection<Shop>> {

	private final StrictMap<String, Shop> shops = new StrictMap<>();

	public boolean doesShopExists(@NonNull final String shopId) {
		return this.shops.containsKey(shopId.toLowerCase());
	}

	public void addShop(@NonNull final Shop shop) {
		this.shops.put(shop.getId(), shop);
	}

	public void removeShop(@NonNull final String id) {
		this.shops.removeWeak(id.toLowerCase());
	}

	public void deleteShop(@NonNull final String id) {
		this.removeShop(id.toLowerCase());
		ShopsData.getInstance().save(new ArrayList<>(this.shops.values()));
	}

	public List<Shop> getEmptyPopulated() {
		final List<Shop> dynShops = new ArrayList<>(this.shops.values());
		if (dynShops.size() < getHighestDisplayPage() * (9 * 5)) {
			int difference = (getHighestDisplayPage() * (9 * 5)) - dynShops.size();
			for (int i = 0; i < difference; i++)
				dynShops.add(Shop.empty());
		}

		return dynShops;
	}

	public int getHighestDisplayPage() {
		return this.shops.values().stream().mapToInt(shop -> shop.getDisplay().getMenuPage()).max().orElse(1);
	}

	public Shop getShop(@NonNull final String id) {
		return this.shops.getOrDefault(id.toLowerCase(), null);
	}

	public StrictList<Shop> getShops() {
		return new StrictList<>(this.shops.values());
	}

	public List<String> getShopIds() {
		return new ArrayList<>(this.shops.keySet());
	}

	public void createShop(@NonNull final String id, @NonNull final String desc) {
		final Shop shop = new Shop(
				id.toLowerCase(),
				new SmartItem(CompMaterial.CHEST.name()),
				id,
				desc,
				Shops.getCurrencyManager().getCurrency("Vault"),
				new ShopDisplay(ShopLayout.AUTOMATIC, CompMaterial.BLACK_STAINED_GLASS_PANE, new StrictList<>(IntStream.rangeClosed(0, 44).boxed().collect(Collectors.toList())), new StrictMap<>(), -1, -1),
				new ShopSettings(false, ShopState.BUY_AND_SELL, true, id, false, false, false, "shops.see." + id, "shops.buy." + id, "shops.sell." + id),
				new ArrayList<>()
		);

		this.addShop(shop);
		ShopsData.getInstance().save(new ArrayList<>(this.shops.values()));
	}


	public List<ShopItem> filterShopItems(@NonNull final Shop shop, @NonNull final String keyword) {
		final List<ShopItem> originalShopItems = shop.getShopItems();
		return originalShopItems.stream().filter(shopItem -> checkSearchCriteria(keyword, shopItem)).collect(Collectors.toList());
	}

	public boolean checkSearchCriteria(@NonNull final String phrase, @NonNull final IShopItem shopItem) {
		return ItemInspect.match(phrase, ItemInspect.getItemName(shopItem.getItem())) ||
				ItemInspect.match(phrase, Inflector.getInstance().pluralize(shopItem.getItem().getType().name())) ||
				ItemInspect.match(phrase, Inflector.getInstance().singularize(shopItem.getItem().getType().name())) ||
				ItemInspect.match(phrase, ItemInspect.getItemLore(shopItem.getItem())) ||
				ItemInspect.match(phrase, ItemInspect.getItemEnchantments(shopItem.getItem()));
	}

	public int getItemCountInPlayerInventory(@NonNull final Player player, @NonNull final ItemStack stack) {
		int total = 0;
		for (ItemStack item : player.getInventory().getContents()) {
			if (item == null || !item.isSimilar(stack)) continue;
			total += item.getAmount();
		}
		return total;
	}

	public void removeSpecificItemQuantityFromPlayer(@NonNull final Player player, @NonNull final ItemStack stack, final int amount) {
		int i = amount;
		for (int j = 0; j < player.getInventory().getSize(); j++) {
			ItemStack item = player.getInventory().getItem(j);
			if (item == null) continue;
			if (!item.isSimilar(stack)) continue;

			if (i >= item.getAmount()) {
				player.getInventory().clear(j);
				i -= item.getAmount();
			} else if (i > 0) {
				item.setAmount(item.getAmount() - i);
				i = 0;
			} else {
				break;
			}
		}
	}


	@Override
	public void load(Consumer<Collection<Shop>> data) {
		this.shops.clear();
		ShopsData.getInstance().getShops().forEach(this::addShop);
		data.accept(this.shops.values());
	}
}
