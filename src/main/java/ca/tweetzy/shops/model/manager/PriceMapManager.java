package ca.tweetzy.shops.model.manager;

import ca.tweetzy.shops.api.ShopCurrency;
import ca.tweetzy.shops.impl.PriceMap;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.ItemUtil;
import ca.tweetzy.tweety.collection.StrictList;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 18 2022
 * Time Created: 1:59 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class PriceMapManager extends Manager<StrictList<PriceMap>> {

	@Getter
	private final StrictList<PriceMap> knownPrices = new StrictList<>();

	public void addPriceMap(@NonNull final PriceMap map) {
		final PriceMap foundMap = getPriceMap(map.getItem().clone());

		if (foundMap != null)
			knownPrices.removeWeak(foundMap);

		knownPrices.addWeak(map);

		if (foundMap != null)
			ShopsData.getInstance().getPriceMappings().remove(foundMap);

		ShopsData.getInstance().getPriceMappings().add(map);

	}

	public PriceMap getPriceMap(@NonNull final ItemStack itemStack) {
		return this.knownPrices.getSource().stream().filter(map -> ItemUtil.isSimilar(map.getItem(), itemStack)).findFirst().orElse(null);
	}

	public List<PriceMap> getPriceMap() {
		return Collections.unmodifiableList(knownPrices.getSource());
	}

	public double getSellPrice(@NonNull final ItemStack itemStack) {
		final PriceMap found = getPriceMap(itemStack);
		return found == null ? 0D : found.getSellPrice();
	}

	public double getBuyPrice(@NonNull final ItemStack itemStack) {
		final PriceMap found = getPriceMap(itemStack);
		return found == null ? 0D : found.getBuyPrice();
	}

	public double getInventoryWorth(@NonNull final Inventory inventory) {
		double worth = 0D;
		for (ItemStack content : inventory.getContents()) {
			if (content == null || content.getType() == CompMaterial.AIR.toMaterial()) continue;
			worth += getSellPrice(content) * content.getAmount();
		}

		return worth;
	}

	public double getInventoryWorth(@NonNull final Inventory inventory, @NonNull final ShopCurrency shopCurrency) {
		double worth = 0D;
		for (ItemStack content : inventory.getContents()) {
			if (content == null || content.getType() == CompMaterial.AIR.toMaterial()) continue;

			final PriceMap map = getPriceMap(content);
			if (map == null) continue;

			if (map.getCurrency() == shopCurrency)
				worth += map.getSellPrice() * content.getAmount();
		}

		return worth;
	}

	@Override
	public void load(Consumer<StrictList<PriceMap>> data) {
		this.knownPrices.clear();
		ShopsData.getInstance().getPriceMappings().forEach(this::addPriceMap);

		if (data != null)
			data.accept(this.knownPrices);
	}
}
