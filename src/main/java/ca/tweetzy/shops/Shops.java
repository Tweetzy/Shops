package ca.tweetzy.shops;

import ca.tweetzy.flight.FlightPlugin;
import ca.tweetzy.flight.command.CommandManager;
import ca.tweetzy.flight.database.DataMigrationManager;
import ca.tweetzy.flight.database.DatabaseConnector;
import ca.tweetzy.flight.database.SQLiteConnector;
import ca.tweetzy.flight.gui.GuiManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.shops.commands.AdminCommand;
import ca.tweetzy.shops.commands.ShopsCommand;
import ca.tweetzy.shops.database.DataManager;
import ca.tweetzy.shops.impl.manager.CurrencyManager;
import ca.tweetzy.shops.settings.Settings;
import ca.tweetzy.shops.settings.Translations;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class Shops extends FlightPlugin {

	@SuppressWarnings("FieldCanBeLocal")
	private DatabaseConnector databaseConnector;
	private DataManager dataManager;

	private final CommandManager commandManager = new CommandManager(this);
	private final GuiManager guiManager = new GuiManager(this);

	private final CurrencyManager currencyManager = new CurrencyManager();

	// default vault economy
	private Economy economy = null;

	@Override
	protected void onFlight() {
		Settings.init();
		Translations.init();

		Common.setPrefix(Settings.PREFIX.getStringOr("&8[&EShops&8]"));

		// Set up the database if enabled
		this.databaseConnector = new SQLiteConnector(this);
		this.dataManager = new DataManager(this.databaseConnector, this);

		final DataMigrationManager dataMigrationManager = new DataMigrationManager(this.databaseConnector, this.dataManager);

		// run migrations for tables
		dataMigrationManager.runMigrations();

		// setup vault
		setupEconomy();

		// gui system
		this.guiManager.init();

		// managers
//		this.marketManager.load();
//		this.playerManager.load();
		this.currencyManager.load();
//		this.offlineItemPaymentManager.load();
//		this.bankManager.load();
//		this.offerManager.load();
//		this.requestManager.load();

		// listeners
//		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
//		getServer().getPluginManager().registerEvents(new MarketTransactionListener(), this);

		// setup commands
		this.commandManager.registerCommandDynamically(new ShopsCommand()).addSubCommands(
				new AdminCommand()
		);
	}

	@Override
	protected int getBStatsId() {
		return 7689;
	}

	@Override
	protected void onSleep() {
		shutdownDataManager(this.dataManager);
	}

	public static Shops getInstance() {
		return (Shops) FlightPlugin.getInstance();
	}

	public static DataManager getDataManager() {
		return getInstance().dataManager;
	}

	public static CurrencyManager getCurrencyManager() {
		return getInstance().currencyManager;
	}

	public static GuiManager getGuiManager() {
		return getInstance().guiManager;
	}

	public static Economy getEconomy() {
		return getInstance().economy;
	}

	// helpers
	private void setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return;
		}

		final RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

		if (rsp == null) {
			return;
		}

		this.economy = rsp.getProvider();
	}
}
