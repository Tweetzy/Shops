package ca.tweetzy.shops;

import ca.tweetzy.shops.api.DiscordWebhook;
import ca.tweetzy.shops.api.TransactionCompleteEvent;
import ca.tweetzy.shops.api.enums.TransactionType;
import ca.tweetzy.shops.api.interfaces.ITransaction;
import ca.tweetzy.shops.model.ItemInspect;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.tweety.Common;
import ca.tweetzy.tweety.model.Replacer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Date Created: May 09 2022
 * Time Created: 2:31 p.m.
 *
 * @author Kiran Hart
 */
public final class ShopsTransactionListener implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void onTransactionComplete(final TransactionCompleteEvent event) {
		final ITransaction transaction = event.getTransaction();
		if (!Settings.DiscordWebhook.ENABLED) return;
		if (!Settings.DiscordWebhook.SEND_ON_BUY && !Settings.DiscordWebhook.SEND_ON_SELL) return;
		if (Settings.DiscordWebhook.HOOKS.size() == 0) return;

		Common.runAsync(() -> Settings.DiscordWebhook.HOOKS.forEach(hookURL -> {

			final DiscordWebhook hook = new DiscordWebhook(hookURL);
			hook.setUsername(Settings.DiscordWebhook.USERNAME);
			hook.setAvatarUrl(Settings.DiscordWebhook.AVATAR);

			OfflinePlayer player = Bukkit.getPlayer(transaction.getPlayer());
			if (player == null)
				player = Bukkit.getOfflinePlayer(transaction.getPlayer());


			final String itemName = ChatColor.stripColor(ItemInspect.getItemName(transaction.getItemStack()));
			final String price = String.format(Settings.NUMBER_FORMAT, transaction.getTotal());
			final int qty = transaction.getQuantity();

			final String[] possibleColours = Settings.DiscordWebhook.DEFAULT_COLOUR.split("-");
			final Color colour = Settings.DiscordWebhook.USE_RANDOM_COLOUR
					? Color.getHSBColor(ThreadLocalRandom.current().nextFloat() * 360F, ThreadLocalRandom.current().nextFloat() * 101F, ThreadLocalRandom.current().nextFloat() * 101F)
					: Color.getHSBColor(Float.parseFloat(possibleColours[0]) / 360, Float.parseFloat(possibleColours[1]) / 100, Float.parseFloat(possibleColours[2]) / 100);

			final DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();
			embedObject.setTitle(transaction.getType() == TransactionType.BUY ? Settings.DiscordWebhook.BOUGHT_TITLE : Settings.DiscordWebhook.SOLD_TITLE);
			embedObject.setColor(colour);


			embedObject.addField(Settings.DiscordWebhook.Fields.PLAYER_NAME, Replacer.replaceArray(Settings.DiscordWebhook.Fields.PLAYER_VALUE, "player", player.getName()), Settings.DiscordWebhook.Fields.PLAYER_INLINE);
			embedObject.addField(Settings.DiscordWebhook.Fields.QTY_NAME, Replacer.replaceArray(Settings.DiscordWebhook.Fields.QTY_VALUE, "quantity", qty), Settings.DiscordWebhook.Fields.QTY_INLINE);
			embedObject.addField(Settings.DiscordWebhook.Fields.ITEM_NAME, Replacer.replaceArray(Settings.DiscordWebhook.Fields.ITEM_VALUE, "item", itemName), Settings.DiscordWebhook.Fields.ITEM_INLINE);
			embedObject.addField(Settings.DiscordWebhook.Fields.PRICE_NAME, Replacer.replaceArray(Settings.DiscordWebhook.Fields.PRICE_VALUE, "price", price), Settings.DiscordWebhook.Fields.PRICE_INLINE);

			hook.addEmbed(embedObject);

			try {
				hook.execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
	}
}
