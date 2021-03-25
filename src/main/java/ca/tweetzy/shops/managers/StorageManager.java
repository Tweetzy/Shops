package ca.tweetzy.shops.managers;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.entity.Player;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 10:06 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class StorageManager {

    private static StorageManager storageManager;

    private StorageManager() {
    }

    public static StorageManager getStorageManager() {
        if (storageManager == null) {
            storageManager = new StorageManager();
        }
        return storageManager;
    }

    public AbstractCommand.ReturnType createShop(Player player, Shop shop) {
        if (Settings.DATABASE_USE.getBoolean()) {
            Shops.getInstance().getDataManager().createShop(shop, failure -> {
                if (failure) {
                    Shops.getInstance().getLocale().getMessage("shop.already_exists").processPlaceholder("shop_id", shop.getId()).sendPrefixedMessage(player);
                } else {
                    Shops.getInstance().getShopManager().addShop(shop);
                    Shops.getInstance().getLocale().getMessage("shop.created").processPlaceholder("shop_id", shop.getId()).sendPrefixedMessage(player);
                }
            });
        } else {
            if (ShopAPI.getInstance().exists(shop.getId())) return AbstractCommand.ReturnType.FAILURE;
            ShopAPI.getInstance().createShop(shop);
            Shops.getInstance().getShopManager().addShop(shop);
            Shops.getInstance().getLocale().getMessage("shop.created").processPlaceholder("shop_id", shop.getId()).sendPrefixedMessage(player);

        }
        return AbstractCommand.ReturnType.SUCCESS;
    }
}
