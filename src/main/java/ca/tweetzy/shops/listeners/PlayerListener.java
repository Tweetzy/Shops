package ca.tweetzy.shops.listeners;

import ca.tweetzy.shops.Shops;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 26 2021
 * Time Created: 3:58 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {
        if (!Shops.getInstance().getOutOfGuiAccess().containsKey(e.getPlayer().getUniqueId())) return;
        if (e.getItem() != null) {
            Bukkit.broadcastMessage("YOU CLICKED AN ITEM OUT OF GUI");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!Shops.getInstance().getOutOfGuiAccess().containsKey(e.getWhoClicked().getUniqueId())) return;
        if (e.getCurrentItem() != null) {
            Bukkit.broadcastMessage("YOU CLICKED AN ITEM INSIDE OF A GUI");
        }
    }
}
