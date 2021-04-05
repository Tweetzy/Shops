package ca.tweetzy.shops.listeners;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.api.events.ShopBuyEvent;
import ca.tweetzy.shops.api.events.ShopSellEvent;
import ca.tweetzy.shops.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * The current file has been created by Kiran Hart
 * Date Created: April 04 2021
 * Time Created: 6:33 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class ShopListeners implements Listener {

    @EventHandler
    public void onShopItemPurchase(ShopBuyEvent e) {
        if (Settings.DISCORD_USE.getBoolean() && Settings.DISCORD_ALERT_ON_BUY.getBoolean()) {
            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(Shops.getInstance(), () -> Settings.DISCORD_WEBHOOKS.getStringList().forEach(hook -> ShopAPI.getInstance().sendDiscordMessage(hook, null, e)), 1L);
        }
    }

    @EventHandler
    public void onShopItemSell(ShopSellEvent e) {
        if (Settings.DISCORD_USE.getBoolean() && Settings.DISCORD_ALERT_ON_SELL.getBoolean()) {
            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(Shops.getInstance(), () -> Settings.DISCORD_WEBHOOKS.getStringList().forEach(hook -> ShopAPI.getInstance().sendDiscordMessage(hook, e, null)), 1L);
        }
    }
}
