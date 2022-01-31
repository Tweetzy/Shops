package ca.tweetzy.shops.model.manager;

import ca.tweetzy.shops.impl.PriceMap;
import ca.tweetzy.shops.settings.ShopsData;
import ca.tweetzy.tweety.collection.StrictList;
import lombok.Getter;
import lombok.NonNull;
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
		this.knownPrices.addIfNotExist(map);
		if (!ShopsData.getInstance().getPriceMappings().contains(map))
			ShopsData.getInstance().getPriceMappings().add(map);
	}

	public PriceMap getPriceMap(@NonNull final ItemStack itemStack) {
		return this.knownPrices.getSource().stream().filter(map -> map.getItem() == itemStack).findFirst().orElse(null);
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

	@Override
	public void load(Consumer<StrictList<PriceMap>> data) {
		if (data != null)
			data.accept(this.knownPrices);
	}
}
