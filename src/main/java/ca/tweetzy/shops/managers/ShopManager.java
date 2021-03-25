package ca.tweetzy.shops.managers;

import ca.tweetzy.shops.shop.Shop;

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

    public void addShop(Shop shop) {
        Objects.requireNonNull(shop, "Shop cannot be null when adding to list");
        shops.add(shop);
    }

    public void removeShop(Shop shop) {
        Objects.requireNonNull(shop, "Shop cannot be null when removing from list");
        shops.remove(shop);
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
}
