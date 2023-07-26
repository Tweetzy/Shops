package ca.tweetzy.shops.impl.shop;

import ca.tweetzy.shops.api.shop.ShopDisplay;
import ca.tweetzy.shops.api.shop.ShopOptions;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public final class ShopSettings implements ShopOptions {

	private ItemStack icon;
	private ShopDisplay display;
	private boolean open;
	private boolean usePermission;
	private boolean useCommand;
	private String permission;
	private String command;

	@Override
	public @NonNull ItemStack getDisplayIcon() {
		return this.icon;
	}

	@Override
	public void setDisplayIcon(@NonNull ItemStack icon) {
		this.icon = icon;
	}

	@Override
	public ShopDisplay getShopDisplay() {
		return this.display;
	}

	@Override
	public boolean isOpen() {
		return this.open;
	}

	@Override
	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public boolean isRequiresPermission() {
		return this.usePermission;
	}

	@Override
	public void setRequiresPermission(boolean requiresPerm) {
		this.usePermission = requiresPerm;
	}

	@Override
	public String getPermission() {
		return this.permission;
	}

	@Override
	public void setPermission(@NonNull String permission) {
		this.permission = permission;
	}

	@Override
	public boolean isUsingCommand() {
		return this.useCommand;
	}

	@Override
	public void setUsingCommand(boolean useCommand) {
		this.useCommand = useCommand;
	}

	@Override
	public String getCommand() {
		return this.command;
	}

	@Override
	public void setCommand(@NonNull String command) {
		this.command = command;
	}
}
