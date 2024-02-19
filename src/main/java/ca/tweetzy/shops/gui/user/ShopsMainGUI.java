package ca.tweetzy.shops.gui.user;

import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.shops.gui.ShopsBaseGUI;
import lombok.NonNull;
import org.bukkit.entity.Player;

public final class ShopsMainGUI extends ShopsBaseGUI {

	public ShopsMainGUI(Gui parent, @NonNull Player player) {
		super(parent, player, "Shops", 6);
		draw();
	}

	@Override
	protected void draw() {

	}
}
