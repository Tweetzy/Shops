package ca.tweetzy.shops.gui.admin;

import ca.tweetzy.shops.api.shop.Shop;
import ca.tweetzy.shops.api.shop.ShopContentType;
import ca.tweetzy.shops.gui.ShopsBaseGUI;
import lombok.NonNull;
import org.bukkit.entity.Player;

public final class ShopAddContentGUI extends ShopsBaseGUI {

	private final Shop shop;
	private final ShopContentType shopContentType;

	public ShopAddContentGUI(@NonNull Player player, @NonNull final Shop shop, @NonNull final ShopContentType shopContentType) {
		super(new ShopEditGUI(player, shop), player, "add content", 6);
		this.shop = shop;
		this.shopContentType = shopContentType;
		draw();
	}

	@Override
	protected void draw() {
		applyBackExit();


	}
}
