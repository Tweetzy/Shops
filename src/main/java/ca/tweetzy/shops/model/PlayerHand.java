package ca.tweetzy.shops.model;

import ca.tweetzy.tweety.MinecraftVersion;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 25 2022
 * Time Created: 1:17 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@UtilityClass
public final class PlayerHand {

	public ItemStack get(@NonNull final Player player) {
		return MinecraftVersion.olderThan(MinecraftVersion.V.v1_9) ? player.getInventory().getItemInHand() : player.getInventory().getItemInMainHand();
	}
}
