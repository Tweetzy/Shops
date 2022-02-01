package ca.tweetzy.shops.api.interfaces;

import ca.tweetzy.shops.api.enums.TransactionType;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: January 24 2022
 * Time Created: 11:29 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public interface ITransaction {

	UUID getID();

	UUID getPlayer();

	String getShopID();

	ItemStack getItemStack();

	int getQuantity();

	double getTotal();

	TransactionType getType();

	long getDate();
}
