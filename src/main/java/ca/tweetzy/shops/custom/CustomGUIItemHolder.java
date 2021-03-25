package ca.tweetzy.shops.custom;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 25 2021
 * Time Created: 5:20 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CustomGUIItemHolder implements Serializable {

    @Getter
    private final String guiName;
    @Getter
    private final ArrayList<CustomGUIItem> items;

    public CustomGUIItemHolder(String guiName) {
        this.guiName = guiName;
        this.items = new ArrayList<>();
    }
}
