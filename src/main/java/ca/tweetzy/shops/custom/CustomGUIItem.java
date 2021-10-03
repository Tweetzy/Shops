package ca.tweetzy.shops.custom;

import ca.tweetzy.shops.api.ShopAPI;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

/**
 * The current file has been created by Kiran Hart
 * Date Created: March 25 2021
 * Time Created: 5:07 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */

@Getter
@Setter
public class CustomGUIItem implements Serializable {

	private int slot;
	private byte[] item;

	public CustomGUIItem(int slot, ItemStack stack) {
		this.slot = slot;
		this.item = ShopAPI.getInstance().serializeItemStack(stack);
	}

}
