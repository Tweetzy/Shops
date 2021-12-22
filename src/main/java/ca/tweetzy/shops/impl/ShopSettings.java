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
	private boolean requirePermissionToSee;
	private boolean requirePermissionToSell;
	private boolean requirePermissionToBuy;

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
	public SerializedMap serialize() {
		return SerializedMap.ofArray(
				"public", this.isPublic,
				"state", this.state,
				"requires see permission", this.requirePermissionToSee,
				"requires sell permission", this.requirePermissionToSell,
				"requires buy permission", this.requirePermissionToBuy
		);
	}

	public static ShopSettings deserialize(SerializedMap map) {
		return new ShopSettings(
				map.getBoolean("public"),
				map.get("state", ShopState.class),
				map.getBoolean("requires see permission"),
				map.getBoolean("requires sell permission"),
				map.getBoolean("requires buy permission")
		);
	}
}
