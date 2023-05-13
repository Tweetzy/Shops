package ca.tweetzy.shops.settings;

import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.shops.Shops;
import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;

public final class Translations extends TranslationManager {

	public Translations(@NonNull JavaPlugin plugin) {
		super(plugin);
		this.mainLanguage = Settings.LANGUAGE.getString();
	}


	public static void init() {
		new Translations(Shops.getInstance()).setup();
	}
}
