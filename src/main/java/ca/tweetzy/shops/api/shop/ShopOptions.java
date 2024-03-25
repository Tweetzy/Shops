package ca.tweetzy.shops.api.shop;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public interface ShopOptions {

	@NonNull ItemStack getDisplayIcon();

	void setDisplayIcon(@NonNull ItemStack icon);

	ShopDisplay getShopDisplay();

	boolean isOpen();

	void setOpen(boolean open);

	boolean isRequiresPermission();

	void setRequiresPermission(boolean requiresPerm);

	String getPermission();

	void setPermission(@NonNull String permission);

	boolean isUsingCommand();

	void setUsingCommand(boolean useCommand);

	String getCommand();

	void setCommand(@NonNull String command);
}
