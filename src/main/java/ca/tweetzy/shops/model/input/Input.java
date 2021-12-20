package ca.tweetzy.shops.model.input;

import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.remain.Remain;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

/**
 * The current file has been created by Kiran Hart
 * Date Created: November 08 2021
 * Time Created: 4:44 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public abstract class Input implements Listener, Runnable {

	private final Player player;
	private String title;
	private String subtitle;

	private final BukkitTask task;

	public Input(@NonNull final Player player) {
		this.player = player;
		Common.runLater(player::closeInventory);
		this.task = Common.runTimer(1, 1, this);
		Common.registerEvents(this);
	}

	public void onExit(final Player player) {
	}

	public abstract boolean onInput(final String input);

	public abstract String getTitle();

	public abstract String getSubtitle();

	public abstract String getActionBar();

	@Override
	public void run() {
		final String title = this.getTitle();
		final String subTitle = this.getSubtitle();
		final String actionBar = this.getActionBar();

		if (this.title == null || this.subtitle == null || !this.title.equals(title) || !this.subtitle.equals(subTitle)) {
			Remain.sendTitle(this.player, 10, 6000, 0, title, subTitle);
			this.title = title;
			this.subtitle = subTitle;
		}

		if (actionBar != null)
			Remain.sendActionBar(this.player, actionBar);
	}

	@EventHandler(
			priority = EventPriority.LOWEST
	)
	public void onChat(AsyncPlayerChatEvent e) {
		if (e.getPlayer().equals(this.player)) {
			this.onInput(e.getMessage());
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void close(PlayerQuitEvent e) {
		if (e.getPlayer().equals(this.player)) {
			this.close(false);
		}
	}

	@EventHandler
	public void close(InventoryOpenEvent e) {
		if (e.getPlayer().equals(this.player)) {
			this.close(false);
		}
	}

	public void close(boolean completed) {
		HandlerList.unregisterAll(this);
		this.task.cancel();
		if (!completed) {
			this.onExit(this.player);
		}

		Remain.resetTitle(this.player);
		Remain.sendActionBar(this.player, "");
	}
}
