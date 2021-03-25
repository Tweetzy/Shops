package ca.tweetzy.shops.managers;

import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import ca.tweetzy.shops.custom.CustomGUIItemHolder;
import ca.tweetzy.shops.shop.Shop;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 9:44 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class ShopManager {

    private final ArrayList<Shop> shops = new ArrayList<>();
    private final ArrayList<CustomGUIItemHolder> customGuiItems = new ArrayList<>();

    public void addShop(Shop shop) {
        Objects.requireNonNull(shop, "Shop cannot be null when adding to list");
        shops.add(shop);
    }

    public void removeShop(String shopId) {
        Objects.requireNonNull(shopId, "Shop ID cannot be null when removing shop");
        shops.removeIf(shop -> shop.getId().equalsIgnoreCase(shopId));
    }

    public Shop getShop(String shopId) {
        Objects.requireNonNull(shopId, "Shop ID cannot be null when removing shop");
        return shops.stream().filter(shop -> shop.getId().equalsIgnoreCase(shopId)).findFirst().orElse(null);
    }

    public List<Shop> getShops() {
        return Collections.unmodifiableList(this.shops);
    }

    public void addCustomGuiHolder(CustomGUIItemHolder holder) {
        this.customGuiItems.add(holder);
    }

    public List<CustomGUIItemHolder> getCustomGuiItems() {
        return this.customGuiItems;
    }

    public void loadShops(boolean reload, boolean useDatabase) {
        if (reload) this.shops.clear();
        if (useDatabase) {
            Shops.getInstance().getDataManager().getShops(shop -> shop.forEach(this::addShop));
        } else {
            ConfigurationSection section = Shops.getInstance().getData().getConfigurationSection("shops");
            if (section == null || section.getKeys(false).size() == 0) return;
            section.getKeys(false).forEach(key -> addShop((Shop) ShopAPI.getInstance().convertBase64ToObject(Shops.getInstance().getData().getString("shops." + key + ".data"))));
        }
    }

    public void loadCustomGuiItems(boolean reload, boolean useDatabase) {
        if (reload) this.customGuiItems.clear();
        if (useDatabase) {
            Shops.getInstance().getDataManager().getCustomGuiItems(customGuiItem -> customGuiItem.forEach(this::addCustomGuiHolder));
        } else {
            ConfigurationSection section = Shops.getInstance().getData().getConfigurationSection("custom gui items");
            if (section == null || section.getKeys(false).size() == 0) return;
            section.getKeys(false).forEach(key -> addCustomGuiHolder((CustomGUIItemHolder) ShopAPI.getInstance().convertBase64ToObject(Shops.getInstance().getData().getString("custom gui items." + key + ".data"))));
        }
    }
}
