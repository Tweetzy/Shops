package ca.tweetzy.shops.gui.admin;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.ChatUtil;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.shops.Shops;
import ca.tweetzy.shops.gui.ShopsBaseGUI;
import ca.tweetzy.shops.settings.Translations;
import lombok.NonNull;
import org.bukkit.entity.Player;

public final class ShopsAdminMainGUI extends ShopsBaseGUI {

	public ShopsAdminMainGUI(@NonNull Player player) {
		super(null, player, TranslationManager.string(Translations.GUI_ADMIN_MAIN_TITLE, "plugin_version", Shops.getInstance().getVersion()), 5);
		draw();
	}

	@Override
	protected void draw() {

		setButton(1, 1, QuickItem.of("https://textures.minecraft.net/texture/f2784307b892f52b92f74fa9db4984c4f0f02eb81c6752e5eba69ad67858427e")
				.name("<GRADIENT:5CAEFF>&lShops</GRADIENT:9F57FF>")
				.lore(
						"&7You can create, update, and delete shops",
						"&7here. These are normal shops that players",
						"&7can buy items from."
				)
				.make(), click -> click.manager.showGUI(click.player, new AdminShopListGUI(this, click.player)));

		setButton(1, 4, QuickItem.of("https://textures.minecraft.net/texture/f2784307b892f52b92f74fa9db4984c4f0f02eb81c6752e5eba69ad67858427e")
				.name("<GRADIENT:5CAEFF>&lVillager Trades</GRADIENT:9F57FF>")
				.lore(
						"&7You can create, update, and delete",
						"&7custom villager trades here."
				)
				.make(), click -> {

		});

		setButton(3, 6, QuickItem.of(CompMaterial.DIAMOND)
				.name("&e&lPatreon")
				.lore(
						"&8Support me on Patreon",
						"&7By supporting me on Patreon you will",
						"&7be helping me be able to continue updating",
						"&7and creating free & paid plugins.",
						"",
						"&e&lClick &8» &7To view Patreon"
				)
				.glow(true)
				.make(), click -> {

			click.gui.close();
			Common.tellNoPrefix(click.player,
					"&8&m-----------------------------------------------------",
					"",
					ChatUtil.centerMessage("&E&lKiran Hart Patreon"),
					ChatUtil.centerMessage("&bhttps://www.patreon.com/kiranhart"),
					"&8&m-----------------------------------------------------"
			);
		});
	}
}
