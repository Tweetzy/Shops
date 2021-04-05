package ca.tweetzy.shops.listeners;

import ca.tweetzy.core.gui.GuiHolder;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.guis.GUIShopEdit;
import ca.tweetzy.shops.managers.StorageManager;
import ca.tweetzy.shops.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

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
            Shops.getInstance().getOutOfGuiAccess().get(e.getPlayer().getUniqueId()).setDisplayIcon(ShopAPI.getInstance().serializeItemStack(e.getItem()));
            Shops.getInstance().getGuiManager().showGUI(e.getPlayer(), new GUIShopEdit(Shops.getInstance().getOutOfGuiAccess().get(e.getPlayer().getUniqueId())));
            StorageManager.getInstance().updateShop(e.getPlayer(), Shops.getInstance().getOutOfGuiAccess().get(e.getPlayer().getUniqueId()));
            Shops.getInstance().getOutOfGuiAccess().remove(e.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!Shops.getInstance().getOutOfGuiAccess().containsKey(e.getWhoClicked().getUniqueId())) return;
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR && !(e.getClickedInventory().getHolder() instanceof GuiHolder)) {
            Player player = (Player) e.getWhoClicked();

            Shops.getInstance().getOutOfGuiAccess().get(player.getUniqueId()).setDisplayIcon(ShopAPI.getInstance().serializeItemStack(e.getCurrentItem()));
            Shops.getInstance().getGuiManager().showGUI(player, new GUIShopEdit(Shops.getInstance().getOutOfGuiAccess().get(player.getUniqueId())));
            StorageManager.getInstance().updateShop(player, Shops.getInstance().getOutOfGuiAccess().get(player.getUniqueId()));
            Shops.getInstance().getOutOfGuiAccess().remove(player.getUniqueId());
            Shops.getInstance().getShopManager().loadShops(true, Settings.DATABASE_USE.getBoolean());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(Shops.getInstance(), () -> {
            if (e.getPlayer().isOp()) {
                switch (Shops.getInstance().getUpdateStatus()) {
                    case UNRELEASED_VERSION:
                        Shops.getInstance().getLocale().getMessage(TextUtils.formatText(String.format("&dYou're running an unreleased version of Shops &f(&c%s&f)", Shops.getInstance().getDescription().getVersion()))).sendPrefixedMessage(e.getPlayer());
                        break;
                    case UPDATE_AVAILABLE:
                        Shops.getInstance().getLocale().getMessage(TextUtils.formatText(String.format("&dThere is a new version of Shops available &f(current: %s&f)", Shops.getInstance().getDescription().getVersion()))).sendPrefixedMessage(e.getPlayer());
                        break;
                }
            }
        }, 1L);
    }
}
