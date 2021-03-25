package ca.tweetzy.shops.shop;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.shops.api.ShopAPI;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 10:11 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */

@Getter
@Setter
public class ShopItem implements Serializable {

    private String shopId;
    private byte[] item;
    private double sellPrice;
    private double buyPrice;

    public ShopItem(String shopId, ItemStack item, double sellPrice, double buyPrice) {
        this.shopId = shopId;
        this.item = ShopAPI.getInstance().serializeItemStack(item);
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }

    public ShopItem() {
        this("default", XMaterial.PAPER.parseItem(), 0.0D, 0.0D);
    }
}
