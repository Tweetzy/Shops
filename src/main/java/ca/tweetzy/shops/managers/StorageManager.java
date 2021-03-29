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

    private static StorageManager instance;

    private StorageManager() {
    }

    public static StorageManager getInstance() {
        if (instance == null) {
            instance = new StorageManager();
        }
        return instance;
    }

    public AbstractCommand.ReturnType createShop(Player player, Shop shop) {
        if (Settings.DATABASE_USE.getBoolean()) {
            Shops.getInstance().getDataManager().createShop(shop, failure -> {
                if (failure) {
                    Shops.getInstance().getLocale().getMessage("shop.already_exists").processPlaceholder("shop_id", shop.getId()).sendPrefixedMessage(player);
                } else {
                    Shops.getInstance().getShopManager().addShop(shop);
                    Shops.getInstance().getShopManager().loadShops(true, Settings.DATABASE_USE.getBoolean());
                    Shops.getInstance().getLocale().getMessage("shop.created").processPlaceholder("shop_id", shop.getId()).sendPrefixedMessage(player);
                }
            });
        } else {
            if (ShopAPI.getInstance().exists(shop.getId())) {
                Shops.getInstance().getLocale().getMessage("shop.already_exists").processPlaceholder("shop_id", shop.getId()).sendPrefixedMessage(player);
                return AbstractCommand.ReturnType.FAILURE;
            }
            ShopAPI.getInstance().createShop(shop);
            Shops.getInstance().getShopManager().addShop(shop);
            Shops.getInstance().getShopManager().loadShops(true, Settings.DATABASE_USE.getBoolean());
            Shops.getInstance().getLocale().getMessage("shop.created").processPlaceholder("shop_id", shop.getId()).sendPrefixedMessage(player);
        }
        return AbstractCommand.ReturnType.SUCCESS;
    }

    public AbstractCommand.ReturnType removeShop(Player player, String shopId) {
        if (Settings.DATABASE_USE.getBoolean()) {
            Shops.getInstance().getDataManager().removeShop(shopId, failure -> {
                if (failure) {
                    Shops.getInstance().getLocale().getMessage("shop.does_not_exists").processPlaceholder("shop_id", shopId).sendPrefixedMessage(player);
                } else {
                    Shops.getInstance().getShopManager().removeShop(shopId);
                    Shops.getInstance().getShopManager().loadShops(true, Settings.DATABASE_USE.getBoolean());
                    Shops.getInstance().getLocale().getMessage("shop.removed").processPlaceholder("shop_id", shopId).sendPrefixedMessage(player);
                }
            });
        } else {
            if (!ShopAPI.getInstance().exists(shopId)) {
                Shops.getInstance().getLocale().getMessage("shop.does_not_exists").processPlaceholder("shop_id", shopId).sendPrefixedMessage(player);
                return AbstractCommand.ReturnType.FAILURE;
            }
            ShopAPI.getInstance().removeShop(shopId);
            Shops.getInstance().getShopManager().removeShop(shopId);
            Shops.getInstance().getShopManager().loadShops(true, Settings.DATABASE_USE.getBoolean());
            Shops.getInstance().getLocale().getMessage("shop.removed").processPlaceholder("shop_id", shopId).sendPrefixedMessage(player);
        }
        return AbstractCommand.ReturnType.SUCCESS;
    }

    public AbstractCommand.ReturnType updateShop(Player player, Shop shop) {
        if (Settings.DATABASE_USE.getBoolean()) {
            Shops.getInstance().getDataManager().updateShop(shop, failure -> {
                if (failure) {
                    Shops.getInstance().getLocale().getMessage("shop.fail_updated_shop_settings").processPlaceholder("shop_id", shop.getId()).sendPrefixedMessage(player);
                } else {
                    Shops.getInstance().getLocale().getMessage("shop.updated_shop_settings").processPlaceholder("shop_id", shop.getId()).sendPrefixedMessage(player);
                    Shops.getInstance().getShopManager().loadShops(true, true);
                }
            });
        } else {
            ShopAPI.getInstance().createShop(shop);
            Shops.getInstance().getLocale().getMessage("shop.updated_shop_settings").processPlaceholder("shop_id", shop.getId()).sendPrefixedMessage(player);
            Shops.getInstance().getShopManager().loadShops(true, false);
        }
        return AbstractCommand.ReturnType.SUCCESS;
    }
}
