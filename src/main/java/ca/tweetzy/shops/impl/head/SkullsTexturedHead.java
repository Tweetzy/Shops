package ca.tweetzy.shops.impl.head;

import ca.tweetzy.shops.api.TextureHead;
import ca.tweetzy.skulls.api.SkullsAPI;
import ca.tweetzy.skulls.impl.Skull;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.remain.CompMaterial;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 22 2021
 * Time Created: 9:35 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class SkullsTexturedHead extends TextureHead {

	@Override
	public boolean enabled() {
		return Common.doesPluginExist("Skulls");
	}

	@Override
	public ItemStack getItem(int id) {
		final Skull skull = SkullsAPI.getSkull(id);
		return enabled() && skull != null ? skull.getItemStack() : CompMaterial.PLAYER_HEAD.toItem();
	}
}
