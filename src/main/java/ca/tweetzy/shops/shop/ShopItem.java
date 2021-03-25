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
    private int row;
    private int col;

    private double sellPrice;
    private double buyPrice;

    private boolean isSellOnly;
    private boolean isBuyOnly;

    public ShopItem(String shopId, ItemStack item, double sellPrice, double buyPrice, boolean isSellOnly, boolean isBuyOnly) {
        this.shopId = shopId;
        this.item = ShopAPI.getInstance().serializeItemStack(item);
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.isSellOnly = isSellOnly;
        this.isBuyOnly = isBuyOnly;
        this.row = 0;
        this.col = 0;
    }

    public ShopItem(String shopId, ItemStack item, double sellPrice, double buyPrice) {
        this(shopId, item, sellPrice, buyPrice, false, false);
    }

    public ShopItem(String shopId, ItemStack item) {
        this(shopId, item, 1.0D, 1.0D, false, false);
    }

    public ShopItem() {
        this("default", XMaterial.PAPER.parseItem(), 1.0D, 1.0D, false, false);
    }
}
