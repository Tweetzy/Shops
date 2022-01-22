package ca.tweetzy.shops.settings;

import ca.tweetzy.shops.impl.PriceMap;
import ca.tweetzy.shops.impl.Shop;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.collection.SerializedMap;
import ca.tweetzy.tweety.settings.YamlConfig;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 21 2021
 * Time Created: 10:40 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@Getter
public final class ShopsData extends YamlConfig {

	private List<Shop> shops;
	private List<PriceMap> priceMappings;

	@Getter
	private static final ShopsData instance = new ShopsData();

	private ShopsData() {
		this.loadConfiguration(NO_DEFAULT, "shops.yml");
	}

	@Override
	protected void onLoadFinish() {
		this.shops = getList("Shops", Shop.class);
		this.priceMappings = getList("Price Mappings", PriceMap.class);
	}

	@Override
	protected SerializedMap serialize() {
		return SerializedMap.ofArray("Shops", this.shops, "Price Mappings", this.priceMappings);
	}

	public void saveAll() {
		Common.runAsync(this::save);
	}

	public void save(@NonNull final List<Shop> shops) {
		this.shops = shops;
		this.save();
	}
}
