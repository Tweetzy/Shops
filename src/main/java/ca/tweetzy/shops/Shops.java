package ca.tweetzy.shops;

import ca.tweetzy.flight.FlightPlugin;
import ca.tweetzy.flight.command.CommandManager;
import ca.tweetzy.flight.database.DataMigrationManager;
import ca.tweetzy.flight.database.DatabaseConnector;
import ca.tweetzy.flight.database.SQLiteConnector;
import ca.tweetzy.flight.gui.GuiManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.shops.commands.*;
import ca.tweetzy.shops.database.DataManager;
import ca.tweetzy.shops.database.migrations.*;
import ca.tweetzy.shops.impl.manager.*;
import ca.tweetzy.shops.listeners.ShopTransactionListener;
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
	private final ShopManager shopManager = new ShopManager();
	private final ShopContentManager shopContentManager = new ShopContentManager();
	private final CurrencyManager currencyManager = new CurrencyManager();
	private final CartManager cartManager = new CartManager();
	private final TransactionManager transactionManager = new TransactionManager();


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

		final DataMigrationManager dataMigrationManager = new DataMigrationManager(this.databaseConnector, this.dataManager,
				new _1_InitialMigration(),
				new _2_ShopItemMigration(),
				new _3_ShopItemBuySellToggleMigration(),
				new _4_ShopCMDIconMigration(),
				new _5_ShopCMDNameDescMigration(),
				new _6_ShopLayoutMigration(),
				new _7_ShopItemCurrencyMigration(),
				new _8_ShopTransactionMigration()
		);

		// run migrations for tables
		dataMigrationManager.runMigrations();

		// setup vault
		setupEconomy();

		// gui system
		this.guiManager.init();

		// managers
		this.currencyManager.load();
		this.shopManager.load();
		this.transactionManager.load();

		// listeners
		getServer().getPluginManager().registerEvents(new ShopTransactionListener(), this);

		// setup commands
		this.commandManager.registerCommandDynamically(new ShopsCommand()).addSubCommands(
				new AddCommand(),
				new DeleteCommand(),
				new CartCommand(),
				new AdminCommand(),
				new ReloadCommand()
		);
	}

	@Override
	protected int getBStatsId() {
		return 6807;
	}

	@Override
	protected void onSleep() {
		shutdownDataManager(this.dataManager);
	}

	public static Shops getInstance() {
		return (Shops) FlightPlugin.getInstance();
	}

	public static CommandManager getCommandManager() {
		return getInstance().commandManager;
	}

	public static DataManager getDataManager() {
		return getInstance().dataManager;
	}

	public static ShopManager getShopManager() {
		return getInstance().shopManager;
	}

	public static ShopContentManager getShopContentManager() {
		return getInstance().shopContentManager;
	}

	public static CurrencyManager getCurrencyManager() {
		return getInstance().currencyManager;
	}

	public static CartManager getCartManager() {
		return getInstance().cartManager;
	}

	public static TransactionManager getTransactionManager() {
		return getInstance().transactionManager;
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
