package ca.tweetzy.shops.shop;

import ca.tweetzy.core.compatibility.XMaterial;
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

    private ArrayList<ShopItem> shopItems;

    public Shop(String id) {
        this.id = id;
        this.displayIcon = ShopAPI.getInstance().serializeItemStack(XMaterial.GRASS_BLOCK.parseItem());
        this.shopItems = new ArrayList<>();
    }
}
