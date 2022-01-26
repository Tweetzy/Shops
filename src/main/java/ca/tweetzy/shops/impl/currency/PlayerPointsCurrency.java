package ca.tweetzy.shops.impl.currency;

import ca.tweetzy.shops.api.ShopCurrency;
import ca.tweetzy.tweety.Common;
import lombok.NonNull;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 26 2021
 * Time Created: 5:56 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class PlayerPointsCurrency extends ShopCurrency {

	private PlayerPointsAPI playerPointsAPI;

	public PlayerPointsCurrency() {
		if (isEnabled())
			this.playerPointsAPI = PlayerPoints.getInstance().getAPI();
	}

	@Override
	public boolean isEnabled() {
		return Common.doesPluginExist("PlayerPoints");
	}

	@Override
	public @NonNull String getPluginName() {
		return "PlayerPoints";
	}

	@Override
	public String getName() {
		return "Points";
	}

	@Override
	public boolean withdraw(@NonNull Player player, double amount) {
		return this.playerPointsAPI.take(player.getUniqueId(), (int) amount);
	}

	@Override
	public boolean deposit(@NonNull Player player, double amount) {
		return this.playerPointsAPI.give(player.getUniqueId(), (int) amount);
	}

	@Override
	public boolean has(@NonNull Player player, double amount) {
		return this.playerPointsAPI.look(player.getUniqueId()) >= (int) amount;
	}
}
