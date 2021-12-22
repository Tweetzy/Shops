package ca.tweetzy.shops.impl;

import ca.tweetzy.shops.api.interfaces.ISmartItem;
import ca.tweetzy.tweety.collection.SerializedMap;
import ca.tweetzy.tweety.model.ConfigSerializable;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 19 2021
 * Time Created: 2:16 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public class SmartItem implements ISmartItem, ConfigSerializable {

	private final String texture;

	@Override
	public @NonNull String getTexture() {
		return this.texture;
	}

	@Override
	public @NonNull ItemStack get() {
		return CompMaterial.AIR.toItem();
	}

	@Override
	public SerializedMap serialize() {
		return SerializedMap.ofArray("Material", this.texture);
	}

	public static SmartItem deserialize(SerializedMap map) {
		return new SmartItem(map.getString("Material"));
	}
}
