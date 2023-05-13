package ca.tweetzy.shops.gui.admin;

import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.gui.ShopsBaseGUI;
import lombok.NonNull;
import org.bukkit.entity.Player;

public final class ShopSettingsGUI extends ShopsBaseGUI {

	private final Shop shop;

	public ShopSettingsGUI(@NonNull Player player, @NonNull final Shop shop) {
		super(new ShopEditGUI(player, shop), player, "shop settings", 6);
		this.shop = shop;
		draw();
	}

	@Override
	protected void draw() {
		applyBackExit();

		// deco/layout
		// open
		// permission
		// command
		//
	}
}
