package ca.tweetzy.shops.api.interfaces.shop;

import ca.tweetzy.shops.api.enums.PriceAdjustmentType;
import lombok.NonNull;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 23 2021
 * Time Created: 12:43 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface IShopItemAdjustment {

	@NonNull PriceAdjustmentType getPriceAdjustmentType();

	void setPriceAdjustmentType(@NonNull final PriceAdjustmentType priceAdjustmentType);

	int getMinimumQuantity();

	void setMinimumQuantity(final int minimumQuantity);

	double getPercentage();

	void setPercentage(final double percentage);
}
