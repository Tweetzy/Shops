package ca.tweetzy.shops.api.shop;

import ca.tweetzy.shops.api.*;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Shop extends Identifiable<String>, Displayable, Trackable, Synchronize, Storeable<Shop> {

	@NonNull ItemStack getDisplayIcon();

	void setDisplayIcon(@NonNull ItemStack icon);

	List<ShopContent> getContent();

}
