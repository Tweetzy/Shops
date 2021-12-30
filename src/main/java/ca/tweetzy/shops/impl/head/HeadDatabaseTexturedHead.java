package ca.tweetzy.shops.impl.head;

import ca.tweetzy.shops.api.TextureHead;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.remain.CompMaterial;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 22 2021
 * Time Created: 9:50 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class HeadDatabaseTexturedHead extends TextureHead {

	@Override
	public boolean enabled() {
		return Common.doesPluginExist("HeadDatabase");
	}

	@Override
	public ItemStack getItem(int id) {
		if (!enabled()) {
			return CompMaterial.PLAYER_HEAD.toItem();
		}

		final HeadDatabaseAPI api = new HeadDatabaseAPI();

		try {
			return api.getItemHead(String.valueOf(id));
		} catch (NullPointerException e) {
			return CompMaterial.PLAYER_HEAD.toItem();
		}
	}
}
