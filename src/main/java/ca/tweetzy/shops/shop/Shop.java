package ca.tweetzy.shops.shop;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.shops.api.ShopAPI;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

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
    private String description;
    private byte[] displayIcon;

    private int page;
    private int slot;

    private boolean isPublic;
    private boolean isSellOnly;
    private boolean isBuyOnly;

    private boolean useSellBonus;
    private boolean useBuyDiscount;
    private boolean useTax;

    private boolean requiresPermissionToSee;
    private boolean requiresPermissionToSell;
    private boolean requiresPermissionToBuy;

    private double sellBonus;
    private double buyDiscount;
    private double tax;

    private String seePermission;
    private String sellPermission;
    private String buyPermission;

    private ArrayList<ShopItem> shopItems;

    public Shop(String id) {
        this.id = id;
        this.displayName = id;
        this.description = "Default shop description";
        this.displayIcon = ShopAPI.getInstance().serializeItemStack(XMaterial.GRASS_BLOCK.parseItem());
        this.page = 1;
        this.slot = 0;
        this.useSellBonus = false;
        this.useBuyDiscount = false;
        this.useTax = false;
        this.requiresPermissionToSee = false;
        this.requiresPermissionToSell = false;
        this.requiresPermissionToBuy = false;
        this.sellBonus = 0.0D;
        this.buyDiscount = 0.0D;
        this.tax = 0.0D;
        this.seePermission = "shops.see." + id.toLowerCase();
        this.sellPermission = "shops.sell." + id.toLowerCase();
        this.buyPermission = "shops.buy." + id.toLowerCase();
        this.shopItems = new ArrayList<>();
    }

    public Shop(String id, String displayName, ItemStack icon, boolean isPublic, boolean sellOnly, boolean buyOnly, boolean useBuyDiscount, double buyDiscount) {
        this(id);
        this.displayName = displayName;
        this.displayIcon = ShopAPI.getInstance().serializeItemStack(icon);
        this.isPublic = isPublic;
        this.isSellOnly = sellOnly;
        this.isBuyOnly = buyOnly;
        this.useBuyDiscount = useBuyDiscount;
        this.buyDiscount = buyDiscount;
    }

    // used to make the conversion attempt to prior 2.0.0 to 2.0.0
    public Shop(String id, String displayName, ItemStack icon, boolean isPublic, boolean sellOnly, boolean buyOnly, boolean useBuyDiscount, double buyDiscount, ArrayList<ShopItem> shopItems) {
        this(id, displayName, icon, isPublic, sellOnly, buyOnly, useBuyDiscount, buyDiscount);
        this.shopItems = shopItems;
    }
}
