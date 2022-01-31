package ca.tweetzy.shops.model;

import ca.tweetzy.shops.impl.head.HeadDatabaseTexturedHead;
import ca.tweetzy.shops.impl.head.SkullsTexturedHead;
import ca.tweetzy.tweety.menu.model.SkullCreator;
import ca.tweetzy.tweety.remain.CompMaterial;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 22 2021
 * Time Created: 9:51 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@UtilityClass
public final class TextureResolver {

	/**
	 * Used to get a normal item stack of a custom textured head dynamically
	 *
	 * @param value is the possible item
	 * @return the created {@link ItemStack}
	 */
	public ItemStack resolve(String value) {
		if (TexturedHead.isMinecraftTextureString(value)) {
			return SkullCreator.itemFromUrl(value);
		}

		if (TexturedHead.isSkullsTexture(value)) {
			final String[] parts = value.split(":");
			if (NumberUtils.isNumber(parts[1]))
				return new SkullsTexturedHead().getItem(Integer.parseInt(parts[1]));
			else
				return CompMaterial.STONE.toItem();
		}

		if (TexturedHead.isHeadDatabaseTexture(value)) {
			final String[] parts = value.split(":");
			if (NumberUtils.isNumber(parts[1]))
				return new HeadDatabaseTexturedHead().getItem(Integer.parseInt(parts[1]));
			else
				return CompMaterial.STONE.toItem();
		}

		return CompMaterial.fromString(value.toUpperCase()).toItem();
	}
}
