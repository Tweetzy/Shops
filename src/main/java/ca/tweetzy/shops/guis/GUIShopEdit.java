package ca.tweetzy.shops.guis;

import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.shops.custom.CustomGUIItemHolder;
import ca.tweetzy.shops.shop.Shop;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 25 2021
 * Time Created: 9:59 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class GUIShopEdit extends Gui {

    Shop shop;
    List<CustomGUIItemHolder> customItems;
    int clicksToEdit = 0;
    boolean editing = false;

    public GUIShopEdit() {
        setTitle(TextUtils.formatText(Settings.))
    }

    private void draw() {
        reset();
    }
}
