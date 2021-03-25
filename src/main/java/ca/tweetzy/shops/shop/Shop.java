package ca.tweetzy.shops.shop;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.api.ShopAPI;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 24 2021
 * Time Created: 9:44 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */

@Getter
@Setter
public class Shop implements Serializable {

    private String id;
    private String displayName;
    private byte[] displayIcon;

    private boolean isPublic;
    private boolean isSellOnly;
    private boolean isBuyOnly;

    private boolean useSellDiscount;
    private boolean useBuyDiscount;

    private boolean requiresPermissionToSee;
    private boolean requiresPermissionToSell;
    private boolean requiresPermissionToBuy;

    private double sellDiscount;
    private double buyDiscount;

    private String seePermission;
    private String sellPermission;
    private String buyPermission;

    private ArrayList<ShopItem> shopItems;

    public Shop(String id) {
        this.id = id;
        this.displayName = id;
        this.displayIcon = ShopAPI.getInstance().serializeItemStack(XMaterial.GRASS_BLOCK.parseItem());
        this.useSellDiscount = false;
        this.useBuyDiscount = false;
        this.requiresPermissionToSee = false;
        this.requiresPermissionToSell = false;
        this.requiresPermissionToBuy = false;
        this.sellDiscount = 0.0D;
        this.buyDiscount = 0.0D;
        this.seePermission = "shops.see." + id.toLowerCase();
        this.sellPermission = "shops.sell." + id.toLowerCase();
        this.buyPermission = "shops.buy." + id.toLowerCase();
        this.shopItems = new ArrayList<>();
    }
}
