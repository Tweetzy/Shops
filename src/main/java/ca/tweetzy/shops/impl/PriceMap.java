package ca.tweetzy.shops.impl;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopCurrency;
import ca.tweetzy.shops.api.interfaces.IPriceMap;
import ca.tweetzy.tweety.collection.SerializedMap;
import ca.tweetzy.tweety.model.ConfigSerializable;
import ca.tweetzy.tweety.model.Tuple;
import lombok.AllArgsConstructor;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 18 2022
 * Time Created: 1:52 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public final class PriceMap implements IPriceMap, ConfigSerializable {

	private final ItemStack itemStack;
	private final double buyPrice;
	private final double sellPrice;
	private final ShopCurrency currency;

	@Override
	public ItemStack getItem() {
		return this.itemStack;
	}

	@Override
	public double getBuyPrice() {
		return this.buyPrice;
	}

	@Override
	public double getSellPrice() {
		return this.sellPrice;
	}

	@Override
	public ShopCurrency getCurrency() {
		return this.currency;
	}

	@Override
	public SerializedMap serialize() {
		return SerializedMap.ofArray(
				"item", this.itemStack,
				"sell price", this.sellPrice,
				"buy price", this.buyPrice,
				"currency", new Tuple<>(this.currency.getPluginName(), this.currency.getName())
		);
	}

	public static PriceMap deserialize(SerializedMap map) {
		final Tuple<String, String> currencyValues = map.getTuple("currency", String.class, String.class);

		return new PriceMap(
				map.getItem("item"),
				map.getDouble("buy price"),
				map.getDouble("sell price"), currencyValues.getKey().equalsIgnoreCase("UltraEconomy") ? Shops.getCurrencyManager().getCurrency(currencyValues.getKey(), currencyValues.getValue()) : Shops.getCurrencyManager().getCurrency(currencyValues.getKey())
		);
	}
}
