package ca.tweetzy.shops.api;

import ca.tweetzy.shops.Shops;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 30 2022
 * Time Created: 1:10 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class ShopsPAPIExpansion extends PlaceholderExpansion {

	@Override
	public @NotNull String getIdentifier() {
		return "shops";
	}

	@Override
	public @NotNull String getAuthor() {
		return "Kiran Hart";
	}

	@Override
	public @NotNull String getVersion() {
		return "3.0.0";
	}

	@Override
	public String onRequest(OfflinePlayer player, @NotNull String params) {
		if (params.equalsIgnoreCase("total_shops"))
			return String.valueOf(Shops.getShopManager().getShops().size());

		return null;
	}
}
