package ca.tweetzy.shops.impl;

import ca.tweetzy.shops.api.enums.ShopState;
import ca.tweetzy.shops.api.interfaces.shop.IShopSettings;
import ca.tweetzy.tweety.collection.SerializedMap;
import ca.tweetzy.tweety.model.ConfigSerializable;
import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * The current file has been created by Kiran Hart
 * Date Created: December 21 2021
 * Time Created: 10:57 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
@AllArgsConstructor
public final class ShopSettings implements IShopSettings, ConfigSerializable {

	private boolean isPublic;
	private ShopState state;
	private boolean useOpenCommand;
	private String openCommand;
	private boolean requirePermissionToSee;
	private boolean requirePermissionToSell;
	private boolean requirePermissionToBuy;
	private String seePermission;
	private String buyPermission;
	private String sellPermission;
	private double discount;

	public static ShopSettings empty() {
		return new ShopSettings(false, ShopState.BUY_AND_SELL, false, "", false, false, false, "", "", "", 0);
	}

	@Override
	public boolean isPublic() {
		return this.isPublic;
	}

	@Override
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	@Override
	public @NonNull ShopState getShopState() {
		return this.state;
	}

	@Override
	public void setShopState(@NonNull ShopState state) {
		this.state = state;
	}

	@Override
	public boolean isUseOpenCommand() {
		return this.useOpenCommand;
	}

	@Override
	public void setUseOpenCommand(boolean useOpenCommand) {
		this.useOpenCommand = useOpenCommand;
	}

	@Override
	public @NonNull String getOpenCommand() {
		return this.openCommand;
	}

	@Override
	public void setOpenCommand(@NonNull String command) {
		this.openCommand = command;
	}

	@Override
	public boolean isRequirePermissionToSee() {
		return this.requirePermissionToSee;
	}

	@Override
	public void setRequirePermissionToSee(boolean requirePermissionToSee) {
		this.requirePermissionToSee = requirePermissionToSee;
	}

	@Override
	public boolean isRequirePermissionToSell() {
		return this.requirePermissionToSell;
	}

	@Override
	public void setRequirePermissionToSell(boolean requirePermissionToSell) {
		this.requirePermissionToSell = requirePermissionToSell;
	}

	@Override
	public boolean isRequirePermissionToBuy() {
		return this.requirePermissionToBuy;
	}

	@Override
	public void setRequirePermissionToBuy(boolean requirePermissionToBuy) {
		this.requirePermissionToBuy = requirePermissionToBuy;
	}

	@Override
	public @NonNull String getSeePermission() {
		return this.seePermission;
	}

	@Override
	public @NonNull String getBuyPermission() {
		return this.buyPermission;
	}

	@Override
	public @NonNull String getSellPermission() {
		return this.sellPermission;
	}

	@Override
	public void setSeePermission(@NonNull String permission) {
		this.seePermission = permission;
	}

	@Override
	public void setBuyPermission(@NonNull String permission) {
		this.buyPermission = permission;
	}

	@Override
	public void setSellPermission(@NonNull String permission) {
		this.sellPermission = permission;
	}

	@Override
	public double getDiscount() {
		return this.discount;
	}

	@Override
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	@Override
	public SerializedMap serialize() {
		return SerializedMap.ofArray(
				"public", this.isPublic,
				"state", this.state,
				"use open command", this.useOpenCommand,
				"open command", this.openCommand,
				"requires see permission", this.requirePermissionToSee,
				"requires sell permission", this.requirePermissionToSell,
				"requires buy permission", this.requirePermissionToBuy,
				"see permission", this.seePermission,
				"buy permission", this.buyPermission,
				"sell permission", this.sellPermission,
				"discount", this.discount
		);
	}

	public static ShopSettings deserialize(SerializedMap map) {
		return new ShopSettings(
				map.getBoolean("public"),
				map.get("state", ShopState.class),
				map.getBoolean("use open command"),
				map.getString("open command"),
				map.getBoolean("requires see permission"),
				map.getBoolean("requires sell permission"),
				map.getBoolean("requires buy permission"),
				map.getString("see permission"),
				map.getString("buy permission"),
				map.getString("sell permission"),
				map.getDouble("discount", 0D)
		);
	}
}
